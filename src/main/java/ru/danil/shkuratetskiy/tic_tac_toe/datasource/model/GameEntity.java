package ru.danil.shkuratetskiy.tic_tac_toe.datasource.model;

import java.util.UUID;

public class GameEntity {
    private UUID id;
    private GameFieldEntity gameField;

    public GameEntity() {

    }

    public GameEntity(UUID id, GameFieldEntity gameField) {
        this.id = id;
        this.gameField = gameField;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GameFieldEntity getGameField() {
        return gameField;
    }

    public void setGameField(GameFieldEntity gameField) {
        this.gameField = gameField;
    }
}
