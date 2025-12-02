package ru.danil.shkuratetskiy.tic_tac_toe.domain.service;

import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;

import java.util.List;
import java.util.UUID;

public interface GameService {
    Game makeComputerMove(Game game);

    Game getGameById(UUID id);

    void saveGame(Game game);

    UUID createNewGame(UUID userId, boolean vsComputer);

    void rmGame(UUID id);

    List<Game> getAvailableGames();

    Game joinGame(UUID gameId, UUID userId);

    Game processMove(UUID gameId, UUID userId, int row, int col);
}
