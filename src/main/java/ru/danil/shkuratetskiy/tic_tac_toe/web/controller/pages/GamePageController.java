package ru.danil.shkuratetskiy.tic_tac_toe.web.controller.pages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.GameState;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.GameService;
import ru.danil.shkuratetskiy.tic_tac_toe.web.mapper.GameDtoMapper;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.GameDto;

import java.util.UUID;

@Controller
@RequestMapping("/game")
public class GamePageController {
    private final GameService gameService;

    @Autowired
    public GamePageController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/new")
    public RedirectView createNewGame(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");
        UUID newGameId = gameService.createNewGame(userId, true);
        return new RedirectView("/game/" + newGameId + "/view");
    }

    @GetMapping("/{gameId}/view")
    public String viewGame(@PathVariable UUID gameId, Model model) throws JsonProcessingException {
        Game game = gameService.getGameById(gameId);
        if (game == null) return "error";

        GameDto gameDto = GameDtoMapper.toGameDto(game);

        ObjectMapper mapper = new ObjectMapper();
        String fieldJson = mapper.writeValueAsString(gameDto.getField());

        model.addAttribute("gameId", gameId);
        model.addAttribute("field", gameDto.getField());
        model.addAttribute("fieldJson", fieldJson);
        model.addAttribute("state", gameDto.getState());

        return "game";
    }

    @GetMapping("/{gameId}/result")
    public String gameResult(@PathVariable UUID gameId, Model model) {
        Game game = gameService.getGameById(gameId);
        if (game == null) return "error";

        GameDto gameDto = GameDtoMapper.toGameDto(game);
        model.addAttribute("field", gameDto.getField());

        String message = switch (game.getState()) {
            case PLAYER_WIN -> game.getWinnerId() != null
                    ? "Победа игрока " + game.getWinnerId()
                    : "Поражение";
            case DRAW -> "Ничья";
            default -> "";
        };
        model.addAttribute("resultText", message + "!");

        return "game-result";
    }
}
