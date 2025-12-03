package ru.danil.shkuratetskiy.tic_tac_toe.domain.model;

import java.util.UUID;

public class Game {
    private final UUID id;
    private final GameField gameField;
    private UUID player1Id;
    private UUID player2Id;
    private CellType player1CellType;
    private CellType player2CellType;
    private UUID currentPlayerTurnId;
    private UUID winnerId;
    private GameState state;

    public Game(UUID id, GameField gameField, UUID player1Id, UUID player2Id,
                CellType player1CellType, CellType player2CellType,
                UUID currentPlayerTurnId, UUID winnerId, GameState state) {
        this.id = id;
        this.gameField = gameField;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1CellType = player1CellType;
        this.player2CellType = player2CellType;
        this.currentPlayerTurnId = currentPlayerTurnId;
        this.winnerId = winnerId;
        this.state = state;
    }

    public static Game newVsComputer(UUID creatorId) {
        return new Game(
                UUID.randomUUID(), new GameField(), creatorId, null,
                CellType.X, CellType.O, creatorId, null, GameState.PLAYER_TURN
        );
    }

    public static Game newVsPlayer(UUID creatorId) {
        return new Game(
                UUID.randomUUID(), new GameField(), creatorId, null,
                CellType.X, CellType.O, creatorId, null, GameState.WAITING
        );
    }

    public UUID getId() { return id; }

    public GameField getGameField() { return gameField; }

    public UUID getPlayer1Id() { return player1Id; }

    public UUID getPlayer2Id() { return player2Id; }

    public void setPlayer2Id(UUID player2Id) { this.player2Id = player2Id; }

    public CellType getPlayer1CellType() { return player1CellType; }

    public CellType getPlayer2CellType() { return player2CellType; }

    public UUID getCurrentPlayerTurnId() { return currentPlayerTurnId; }

    public void setCurrentPlayerTurnId(UUID currentPlayerTurnId) {
        this.currentPlayerTurnId = currentPlayerTurnId;
    }

    public UUID getWinnerId() { return winnerId; }

    public void setWinnerId(UUID winnerId) { this.winnerId = winnerId; }

    public GameState getState() { return state; }

    public void setState(GameState state) { this.state = state; }
}
