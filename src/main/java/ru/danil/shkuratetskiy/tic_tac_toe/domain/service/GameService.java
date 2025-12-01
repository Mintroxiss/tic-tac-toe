package ru.danil.shkuratetskiy.tic_tac_toe.domain.service;

import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Winner;

import java.util.UUID;

public interface GameService {
    Game makeComputerMove(Game game);

    boolean validateField(Game game, int row, int col);

    Winner isGameOver(Game game);

    Game getGameById(UUID id);

    void saveGame(Game game);

    UUID createNewGame();

    void rmGame(UUID id);

}
