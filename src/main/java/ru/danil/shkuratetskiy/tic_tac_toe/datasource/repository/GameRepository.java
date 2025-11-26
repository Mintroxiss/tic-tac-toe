package ru.danil.shkuratetskiy.tic_tac_toe.datasource.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.model.GameEntity;

import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, UUID> {
}
