package ru.danil.shkuratetskiy.tic_tac_toe.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.repository.GameRepository;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.CellType;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.GameField;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Winner;

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
     *
     * @return обновленная сессия игры
     */
    @Override
    public Game makeComputerMove(Game game) {
        GameField gameField = game.getGameField();

        CellType computerCellType = CellType.O;

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

        gameField.setFieldCell(moveX, moveY, computerCellType);

        repository.save(game);

        return game;
    }

    /**
     * Рекурсивно оценивает ход
     *
     * @param field          игровое поле
     * @param isComputerTurn чей ход
     * @return числовая оценка
     */
    private int minimaxScore(GameField field, boolean isComputerTurn) {
        Winner winner = field.getWinner();
        if (winner != null) {
            switch (winner) {
                case PLAYER1 -> {
                    return -1;
                }
                case PLAYER2 -> {
                    return 1;
                }
                case DRAW -> {
                    return 0;
                }
            }
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
    public boolean validateField(Game game, int row, int col) {
        Game repoGame = repository.get(game.getId());
        GameField repoGameField = repoGame.getGameField();
        if (repoGameField.getFieldCell(row, col) != CellType.EMPTY) {
            return false;
        }
        return GameField.validateField(game.getGameField(), repoGame.getGameField());
    }

    @Override
    public Winner isGameOver(UUID id) {
        Game game = repository.get(id);
        GameField gameField = game.getGameField();

        return gameField.getWinner();
    }

    @Override
    public Game getGameById(UUID id) {
        return repository.get(id);
    }

    @Override
    public UUID createNewGame() {
        Game game = new Game();
        repository.save(game);
        return game.getId();
    }
}
