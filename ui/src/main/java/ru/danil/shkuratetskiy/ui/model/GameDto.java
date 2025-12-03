package ru.danil.shkuratetskiy.ui.model;

import java.util.List;

public class GameDto {
    private String id;
    private List<List<String>> field;
    private String state;
    private String player1Id;
    private String player2Id;
    private String currentPlayerTurnId;
    private String winnerId;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public List<List<String>> getField() { return field; }

    public void setField(List<List<String>> field) { this.field = field; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getPlayer1Id() { return player1Id; }

    public void setPlayer1Id(String player1Id) { this.player1Id = player1Id; }

    public String getPlayer2Id() { return player2Id; }

    public void setPlayer2Id(String player2Id) { this.player2Id = player2Id; }

    public String getCurrentPlayerTurnId() { return currentPlayerTurnId; }

    public void setCurrentPlayerTurnId(String currentPlayerTurnId) {
        this.currentPlayerTurnId = currentPlayerTurnId;
    }

    public String getWinnerId() { return winnerId; }

    public void setWinnerId(String winnerId) { this.winnerId = winnerId; }
}
