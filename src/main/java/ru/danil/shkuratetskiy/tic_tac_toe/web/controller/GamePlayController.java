package ru.danil.shkuratetskiy.tic_tac_toe.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Winner;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.GameService;
import ru.danil.shkuratetskiy.tic_tac_toe.web.mapper.GameDtoMapper;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.GameDto;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.GameStatus;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.MoveDto;

import java.util.UUID;

@Controller
@RequestMapping("/api/game")
public class GamePlayController {
    private final GameService gameService;

    @Autowired
    public GamePlayController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/new")
    public ResponseEntity<UUID> createNewGame() {
        UUID newGameId = gameService.createNewGame();
        return ResponseEntity.ok(newGameId);
    }

    @PostMapping("/{id}")
    public ResponseEntity<GameDto> move(
            @PathVariable(name = "id") UUID id,
            @RequestBody MoveDto moveDto
    ) {
        GameDto gameDto = new GameDto(moveDto.getField());
        Game game = GameDtoMapper.toGame(id, gameDto);
        if (gameService.validateField(game, moveDto.getRow(), moveDto.getCol())) {
            Winner winner = gameService.isGameOver(game);
            if (winner != null) {
                gameService.saveGame(game);
                switch (winner) {
                    case PLAYER1 -> {
                        return ResponseEntity.ok(GameDtoMapper.toGameDto(game, GameStatus.WIN));
                    }
                    case DRAW -> {
                        return ResponseEntity.ok(GameDtoMapper.toGameDto(game, GameStatus.DRAW));
                    }
                }
            }
            game = gameService.makeComputerMove(game);
            winner = gameService.isGameOver(game);
            if (winner == Winner.PLAYER2) {
                return ResponseEntity.ok(GameDtoMapper.toGameDto(game, GameStatus.LOSS));
            }
            return ResponseEntity.ok(GameDtoMapper.toGameDto(game));
        } else {
            return ResponseEntity.badRequest().body(GameDtoMapper.toGameDto(
                    gameService.getGameById(id),
                    GameStatus.INCORRECT_MOVE)
            );
        }
    }
}
