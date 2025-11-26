package ru.danil.shkuratetskiy.tic_tac_toe.datasource.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.model.GameEntity;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.CellType;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.GameField;

public class GameEntityMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Game toGame(GameEntity gameEntity) {
        try {
            CellType[][] field = mapper.readValue(
                    gameEntity.getGameFieldJson(),
                    CellType[][].class
            );
            return new Game(
                    gameEntity.getId(),
                    new GameField(field)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameEntity toGameEntity(Game game) {
        GameEntity gameEntity = new GameEntity();
        try {
            String json = mapper.writeValueAsString(game.getGameField().getField());
            gameEntity.setId(game.getId());
            gameEntity.setGameFieldJson(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return gameEntity;
    }
}
