package ru.danil.shkuratetskiy.tic_tac_toe.domain.model;


import java.util.UUID;

public class Game {
    private final UUID id;
    private final GameField gameField;

    public Game(UUID id, GameField gameField) {
        this.id = id;
        this.gameField = gameField;
    }

    public UUID getId() {
        return id;
    }

    public GameField getGameField() {
        return gameField;
    }
}
