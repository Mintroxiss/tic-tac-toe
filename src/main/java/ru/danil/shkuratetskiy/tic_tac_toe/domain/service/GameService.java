package ru.danil.shkuratetskiy.tic_tac_toe.domain.service;

import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Winner;

import java.util.UUID;

public interface GameService {
    Game makeComputerMove(Game game);

    boolean validateField(Game game, int row, int col);

    Winner isGameOver(UUID id);

    Game getGameById(UUID id);

    UUID createNewGame();
}
