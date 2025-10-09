package ru.danil.shkuratetskiy.tic_tac_toe.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.GameService;
import ru.danil.shkuratetskiy.tic_tac_toe.web.mapper.GameDtoMapper;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.GameDto;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.MoveDto;

import java.util.UUID;

@Controller
public class GamePlayController {
    private final GameService gameService;

    @Autowired
    public GamePlayController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/game/new")
    public RedirectView createNewGame() {
        UUID newGameId = gameService.createNewGame();
        return new RedirectView("/game/" + newGameId + "/view");
    }

    @GetMapping("/game/{id}/view")
    public String viewGame(@PathVariable UUID id, Model model) throws JsonProcessingException {
        Game game = gameService.getGameById(id);
        if (game == null) return "error";

        GameDto gameDto = GameDtoMapper.toGameDto(game);

        ObjectMapper mapper = new ObjectMapper();
        String fieldJson = mapper.writeValueAsString(gameDto.getField());

        model.addAttribute("gameId", id);
        model.addAttribute("field", gameDto.getField());
        model.addAttribute("fieldJson", fieldJson);
        model.addAttribute("status", gameDto.getStatus());

        return "game";
    }

    @PostMapping("/game/{id}")
    public ResponseEntity<GameDto> move(
            @PathVariable(name = "id") UUID id,
            @RequestBody MoveDto moveDto
    ) {
        GameDto gameDto = new GameDto(moveDto.getField());
        Game game = GameDtoMapper.toGame(id, gameDto);
        if (gameService.validateField(game, moveDto.getRow(), moveDto.getCol())) {
            return ResponseEntity.ok(GameDtoMapper.toGameDto(gameService.makeComputerMove(game)));
        } else {
            return ResponseEntity.badRequest().body(GameDtoMapper.toGameDto(
                    gameService.getGameById(id),
                    "ERROR: Некорректный ход!")
            );
        }
    }
}
