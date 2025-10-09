package ru.danil.shkuratetskiy.tic_tac_toe.datasource.mapper;

import ru.danil.shkuratetskiy.tic_tac_toe.datasource.model.GameEntity;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.model.GameFieldEntity;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.GameField;

public class GameEntityMapper {
    public static Game toGame(GameEntity gameEntity) {
        return new Game(
                gameEntity.getId(),
                new GameField(
                        gameEntity.getGameField().getField()
                )
        );
    }

    public static GameEntity toGameEntity(Game game) {
        return new GameEntity(
                game.getId(),
                new GameFieldEntity(
                        game.getGameField().getField()
                )
        );
    }
}
