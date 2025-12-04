package ru.danil.shkuratetskiy.tic_tac_toe.datasource.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.model.GameEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, UUID> {
    List<GameEntity> findByState(String state);
    void deleteByPlayer1IdAndState(UUID player1Id, String state);
    void deleteByStateAndCreatedAtBefore(String state, Instant cutoff);
}
