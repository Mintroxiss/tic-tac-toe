package ru.danil.shkuratetskiy.tic_tac_toe.datasource.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String gameFieldJson;

    @Column
    private UUID player1Id;

    @Column
    private UUID player2Id;

    @Column
    private String player1CellType;

    @Column
    private String player2CellType;

    @Column
    private UUID currentPlayerTurnId;

    @Column
    private UUID winnerId;

    @Column(nullable = false)
    private String state;

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public String getGameFieldJson() { return gameFieldJson; }

    public void setGameFieldJson(String gameFieldJson) { this.gameFieldJson = gameFieldJson; }

    public UUID getPlayer1Id() { return player1Id; }

    public void setPlayer1Id(UUID player1Id) { this.player1Id = player1Id; }

    public UUID getPlayer2Id() { return player2Id; }

    public void setPlayer2Id(UUID player2Id) { this.player2Id = player2Id; }

    public String getPlayer1CellType() { return player1CellType; }

    public void setPlayer1CellType(String player1CellType) { this.player1CellType = player1CellType; }

    public String getPlayer2CellType() { return player2CellType; }

    public void setPlayer2CellType(String player2CellType) { this.player2CellType = player2CellType; }

    public UUID getCurrentPlayerTurnId() { return currentPlayerTurnId; }

    public void setCurrentPlayerTurnId(UUID currentPlayerTurnId) {
        this.currentPlayerTurnId = currentPlayerTurnId;
    }

    public UUID getWinnerId() { return winnerId; }

    public void setWinnerId(UUID winnerId) { this.winnerId = winnerId; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }
}
