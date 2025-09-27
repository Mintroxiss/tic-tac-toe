package ru.danil.shkuratetskiy.tic_tac_toe.datasource.repository;

import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;

import java.util.UUID;

public interface GameRepository {
    void save(Game game);
    Game get(UUID id);
}
