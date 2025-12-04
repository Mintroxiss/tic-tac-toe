package ru.danil.shkuratetskiy.ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.danil.shkuratetskiy.ui.model.GameDto;
import ru.danil.shkuratetskiy.ui.service.ApiClient;

import java.util.List;
import java.util.Map;

@Controller
public class GamePageController {
    private final ApiClient apiClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public GamePageController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        model.addAttribute("userId", session.getAttribute("userId"));
        return "index";
    }

    @GetMapping("/game/new")
    public String createGame(@RequestParam(defaultValue = "true") boolean vsComputer,
                             HttpSession session) {
        String authHeader = (String) session.getAttribute("authHeader");
        String gameId = apiClient.createGame(vsComputer, authHeader);
        if (gameId == null) return "error";
        return "redirect:/game/" + gameId + "/view";
    }

    @GetMapping("/game/available")
    public String availableGames(Model model) {
        List<GameDto> games = apiClient.getAvailableGames();
        model.addAttribute("games", games);
        return "game-list";
    }

    @PostMapping("/game/{gameId}/join")
    public String joinGame(@PathVariable String gameId, HttpSession session) {
        String authHeader = (String) session.getAttribute("authHeader");
        GameDto game = apiClient.joinGame(gameId, authHeader);
        if (game == null) return "redirect:/game/available?error=cannot_join";
        return "redirect:/game/" + gameId + "/view";
    }

    @GetMapping("/game/{gameId}/view")
    public String viewGame(@PathVariable String gameId, HttpSession session, Model model) {
        GameDto game = apiClient.getGame(gameId);
        if (game == null) return "error";
        try {
            String fieldJson = objectMapper.writeValueAsString(game.getField());
            model.addAttribute("gameId", gameId);
            model.addAttribute("field", game.getField());
            model.addAttribute("fieldJson", fieldJson);
            model.addAttribute("state", game.getState());
            model.addAttribute("currentPlayerTurnId", game.getCurrentPlayerTurnId());
            model.addAttribute("userId", session.getAttribute("userId"));
        } catch (Exception e) {
            return "error";
        }
        return "game";
    }

    @PostMapping(value = "/game/{gameId}/move", produces = "application/json")
    @ResponseBody
    public GameDto move(@PathVariable String gameId, @RequestBody Map<String, Integer> body,
                        HttpSession session) {
        String authHeader = (String) session.getAttribute("authHeader");
        return apiClient.makeMove(gameId, body.get("row"), body.get("col"), authHeader);
    }

    @GetMapping(value = "/game/{gameId}/state", produces = "application/json")
    @ResponseBody
    public GameDto getGameState(@PathVariable String gameId) {
        return apiClient.getGame(gameId);
    }

    @GetMapping("/game/{gameId}/result")
    public String gameResult(@PathVariable String gameId, HttpSession session, Model model) {
        GameDto game = apiClient.getGame(gameId);
        if (game == null) return "error";
        model.addAttribute("field", game.getField());

        String sessionUserId = (String) session.getAttribute("userId");
        String message;
        if ("DRAW".equals(game.getState())) {
            message = "Ничья";
        } else if ("PLAYER_WIN".equals(game.getState())) {
            String winnerId = game.getWinnerId();
            if (sessionUserId != null && sessionUserId.equals(winnerId)) {
                message = "Победа";
            } else if (winnerId != null) {
                message = "Поражение (победил " + winnerId + ")";
            } else {
                message = "Поражение";
            }
        } else {
            message = "Игра завершена";
        }
        model.addAttribute("resultText", message + "!");
        return "game-result";
    }
}
