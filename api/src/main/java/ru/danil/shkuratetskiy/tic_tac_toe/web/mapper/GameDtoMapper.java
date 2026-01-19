package ru.danil.shkuratetskiy.tic_tac_toe.web.mapper;

import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.CellType;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.GameDto;

import java.util.Arrays;

public class GameDtoMapper {
    public static GameDto toGameDto(Game game) {
        return new GameDto(
                game.getId(),
                Arrays.stream(game.getGameField().getField()).map(row ->
                        Arrays.stream(row).map(cellType ->
                                cellType.toString()
                        ).toList()
                ).toList(),
                game.getState().name(),
                game.getPlayer1Id(),
                game.getPlayer2Id(),
                game.getCurrentPlayerTurnId(),
                game.getWinnerId()
        );
    }
}
