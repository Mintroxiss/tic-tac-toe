package ru.danil.shkuratetskiy.tic_tac_toe.datasource.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="games")
public class GameEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String gameFieldJson;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGameFieldJson() {
        return gameFieldJson;
    }

    public void setGameFieldJson(String gameFieldJson) {
        this.gameFieldJson = gameFieldJson;
    }
}
