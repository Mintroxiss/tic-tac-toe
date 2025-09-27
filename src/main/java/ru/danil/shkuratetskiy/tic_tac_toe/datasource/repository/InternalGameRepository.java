package ru.danil.shkuratetskiy.tic_tac_toe.datasource.repository;

import org.springframework.stereotype.Repository;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.mapper.GameEntityMapper;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.model.GameEntity;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InternalGameRepository implements GameRepository {
    ConcurrentHashMap<UUID, GameEntity> games;

    public InternalGameRepository() {
        this.games = new ConcurrentHashMap<>();
    }

    @Override
    public void save(Game game) {
        games.put(game.getId(), GameEntityMapper.toGameEntity(game));
    }

    @Override
    public Game get(UUID id) {
        GameEntity entity = games.get(id);
        if (entity == null) {
            throw new IllegalArgumentException("Game not found: " + id);
        }
        return GameEntityMapper.toGame(entity);
    }
}
