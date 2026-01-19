package ru.danil.shkuratetskiy.tic_tac_toe.web.model;

import java.util.List;
import java.util.UUID;

public class GameDto {
    private UUID id;
    private List<List<String>> field;
    private String state;
    private UUID player1Id;
    private UUID player2Id;
    private UUID currentPlayerTurnId;
    private UUID winnerId;

    public GameDto(UUID id, List<List<String>> field, String state, UUID player1Id, UUID player2Id,
                   UUID currentPlayerTurnId, UUID winnerId) {
        this.id = id;
        this.field = field;
        this.state = state;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.currentPlayerTurnId = currentPlayerTurnId;
        this.winnerId = winnerId;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public List<List<String>> getField() { return field; }

    public void setField(List<List<String>> field) { this.field = field; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public UUID getPlayer1Id() { return player1Id; }

    public void setPlayer1Id(UUID player1Id) { this.player1Id = player1Id; }

    public UUID getPlayer2Id() { return player2Id; }

    public void setPlayer2Id(UUID player2Id) { this.player2Id = player2Id; }

    public UUID getCurrentPlayerTurnId() { return currentPlayerTurnId; }

    public void setCurrentPlayerTurnId(UUID currentPlayerTurnId) {
        this.currentPlayerTurnId = currentPlayerTurnId;
    }

    public UUID getWinnerId() { return winnerId; }

    public void setWinnerId(UUID winnerId) { this.winnerId = winnerId; }
}
