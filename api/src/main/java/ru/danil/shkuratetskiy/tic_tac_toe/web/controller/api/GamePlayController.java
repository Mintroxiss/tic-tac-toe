package ru.danil.shkuratetskiy.tic_tac_toe.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.GameService;
import ru.danil.shkuratetskiy.tic_tac_toe.web.mapper.GameDtoMapper;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.CreateGameRequest;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.GameDto;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.MoveDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GamePlayController {
    private final GameService gameService;

    @Autowired
    public GamePlayController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/new")
    public ResponseEntity<UUID> createNewGame(@RequestBody CreateGameRequest request) {
        UUID gameId = gameService.createNewGame(getCurrentUserId(), request.isVsComputer());
        return ResponseEntity.ok(gameId);
    }

    @GetMapping("/available")
    public ResponseEntity<List<GameDto>> getAvailableGames() {
        List<Game> games = gameService.getAvailableGames();
        return ResponseEntity.ok(games.stream().map(GameDtoMapper::toGameDto).toList());
    }

    @PostMapping("/{gameId}/join")
    public ResponseEntity<GameDto> joinGame(@PathVariable UUID gameId) {
        Game game = gameService.joinGame(gameId, getCurrentUserId());
        return ResponseEntity.ok(GameDtoMapper.toGameDto(game));
    }

    @PostMapping("/{gameId}/move")
    public ResponseEntity<GameDto> move(@PathVariable UUID gameId, @RequestBody MoveDto moveDto) {
        Game game = gameService.processMove(gameId, getCurrentUserId(), moveDto.getRow(), moveDto.getCol());
        if (game == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(GameDtoMapper.toGameDto(game));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDto> getGame(@PathVariable UUID gameId) {
        return ResponseEntity.ok(GameDtoMapper.toGameDto(gameService.getGameById(gameId)));
    }

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UUID uuid) {
            return uuid;
        }
        return null;
    }
}
