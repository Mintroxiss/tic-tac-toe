package ru.danil.shkuratetskiy.tic_tac_toe.datasource.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.model.GameEntity;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.*;

public class GameEntityMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Game toGame(GameEntity e) {
        try {
            CellType[][] field = mapper.readValue(e.getGameFieldJson(), CellType[][].class);
            return new Game(
                    e.getId(),
                    new GameField(field),
                    e.getPlayer1Id(),
                    e.getPlayer2Id(),
                    e.getPlayer1CellType() != null ? CellType.valueOf(e.getPlayer1CellType()) : CellType.X,
                    e.getPlayer2CellType() != null ? CellType.valueOf(e.getPlayer2CellType()) : CellType.O,
                    e.getCurrentPlayerTurnId(),
                    e.getWinnerId(),
                    GameState.valueOf(e.getState())
            );
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static GameEntity toGameEntity(Game game) {
        GameEntity entity = new GameEntity();
        try {
            entity.setId(game.getId());
            entity.setGameFieldJson(mapper.writeValueAsString(game.getGameField().getField()));
            entity.setPlayer1Id(game.getPlayer1Id());
            entity.setPlayer2Id(game.getPlayer2Id());
            entity.setPlayer1CellType(game.getPlayer1CellType() != null ? game.getPlayer1CellType().name() : null);
            entity.setPlayer2CellType(game.getPlayer2CellType() != null ? game.getPlayer2CellType().name() : null);
            entity.setCurrentPlayerTurnId(game.getCurrentPlayerTurnId());
            entity.setWinnerId(game.getWinnerId());
            entity.setState(game.getState().name());
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        return entity;
    }
}
