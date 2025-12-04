package ru.danil.shkuratetskiy.tic_tac_toe.domain.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.mapper.GameEntityMapper;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.repository.GameRepository;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.*;

import java.util.List;
import java.util.UUID;

@Service
public class MinimaxGameService implements GameService {
    private final GameRepository repository;

    @Autowired
    public MinimaxGameService(GameRepository repository) {
        this.repository = repository;
    }

    /**
     * Делает ход компьютера по алгоритму "Минимакс"
     */
    @Override
    public Game makeComputerMove(Game game) {
        GameField gameField = game.getGameField();
        CellType computerCellType = game.getPlayer2CellType();

        int bestScore = Integer.MIN_VALUE;
        int moveX = -1;
        int moveY = -1;

        int n = GameField.HEIGHT;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (gameField.getFieldCell(i, j) == CellType.EMPTY) {
                    gameField.setFieldCell(i, j, computerCellType);
                    int score = minimaxScore(gameField, false);
                    gameField.setFieldCell(i, j, CellType.EMPTY);

                    if (score > bestScore) {
                        bestScore = score;
                        moveX = i;
                        moveY = j;
                    }
                }
            }
        }

        if (moveX >= 0) {
            gameField.setFieldCell(moveX, moveY, computerCellType);
        }

        return game;
    }

    private int minimaxScore(GameField field, boolean isComputerTurn) {
        Winner winner = field.getWinner();
        if (winner != null) {
            return switch (winner) {
                case PLAYER1 -> -1;
                case PLAYER2 -> 1;
                case DRAW -> 0;
            };
        }

        int n = GameField.HEIGHT;

        if (isComputerTurn) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (field.getFieldCell(i, j) == CellType.EMPTY) {
                        field.setFieldCell(i, j, CellType.O);
                        int score = minimaxScore(field, false);
                        field.setFieldCell(i, j, CellType.EMPTY);
                        maxScore = Math.max(maxScore, score);
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (field.getFieldCell(i, j) == CellType.EMPTY) {
                        field.setFieldCell(i, j, CellType.X);
                        int score = minimaxScore(field, true);
                        field.setFieldCell(i, j, CellType.EMPTY);
                        minScore = Math.min(minScore, score);
                    }
                }
            }
            return minScore;
        }
    }

    @Override
    public UUID createNewGame(UUID userId, boolean vsComputer) {
        Game game = vsComputer ? Game.newVsComputer(userId) : Game.newVsPlayer(userId);
        saveGame(game);
        return game.getId();
    }

    @Override
    public Game getGameById(UUID id) {
        return repository.findById(id)
                .map(GameEntityMapper::toGame)
                .orElseThrow(() -> new IllegalArgumentException("Game not found: " + id));
    }

    @Transactional
    @Override
    public void saveGame(Game game) {
        repository.save(GameEntityMapper.toGameEntity(game));
    }

    @Transactional
    @Override
    public void rmGame(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void cancelWaitingGames(UUID userId) {
        repository.deleteByPlayer1IdAndState(userId, GameState.WAITING.name());
    }

    @Override
    public List<Game> getAvailableGames() {
        return repository.findByState(GameState.WAITING.name())
                .stream()
                .map(GameEntityMapper::toGame)
                .toList();
    }

    @Override
    public Game joinGame(UUID gameId, UUID userId) {
        Game game = getGameById(gameId);
        if (game.getState() != GameState.WAITING) {
            throw new IllegalStateException("Game is not in WAITING state");
        }
        if (userId != null && userId.equals(game.getPlayer1Id())) {
            throw new IllegalStateException("Cannot join your own game");
        }
        game.setPlayer2Id(userId);
        game.setState(GameState.PLAYER_TURN);
        saveGame(game);
        return game;
    }

    @Override
    public Game processMove(UUID gameId, UUID userId, int row, int col) {
        Game game = getGameById(gameId);

        if (game.getState() != GameState.PLAYER_TURN) return null;
        // Если currentPlayerTurnId не задан (гостевая игра) — проверку хода пропускаем.
        // Иначе для веб-страницы userId может быть null — подставляем player1Id.
        if (game.getCurrentPlayerTurnId() != null) {
            if (userId == null && game.getPlayer2Id() == null) {
                userId = game.getPlayer1Id();
            }
            if (userId == null || !userId.equals(game.getCurrentPlayerTurnId())) return null;
        }
        if (game.getGameField().getFieldCell(row, col) != CellType.EMPTY) return null;

        CellType cellType = userId.equals(game.getPlayer1Id())
                ? game.getPlayer1CellType()
                : game.getPlayer2CellType();
        game.getGameField().setFieldCell(row, col, cellType);

        Winner winner = game.getGameField().getWinner();
        if (winner == Winner.DRAW) {
            game.setState(GameState.DRAW);
            saveGame(game);
            return game;
        }
        if (winner != null) {
            game.setState(GameState.PLAYER_WIN);
            game.setWinnerId(winner == Winner.PLAYER1 ? game.getPlayer1Id() : game.getPlayer2Id());
            saveGame(game);
            return game;
        }

        if (game.getPlayer2Id() == null) {
            // vs computer — make computer move
            makeComputerMove(game);
            Winner computerWinner = game.getGameField().getWinner();
            if (computerWinner == Winner.DRAW) {
                game.setState(GameState.DRAW);
            } else if (computerWinner != null) {
                // computer won: winnerId remains null (no UUID for computer)
                game.setState(GameState.PLAYER_WIN);
            }
        } else {
            // vs player — switch turns
            UUID nextPlayer = userId.equals(game.getPlayer1Id())
                    ? game.getPlayer2Id()
                    : game.getPlayer1Id();
            game.setCurrentPlayerTurnId(nextPlayer);
        }

        saveGame(game);
        return game;
    }
}
