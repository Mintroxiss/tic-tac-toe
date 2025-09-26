package ru.danil.shkuratetskiy.tic_tac_toe.domain.service;

import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Winner;

import java.util.UUID;

public interface GameService {
    Game makeComputerMove(UUID id);

    boolean validateField(UUID id);

    Winner isGameOver(UUID id);
}
