package ru.danil.shkuratetskiy.tic_tac_toe.web.mapper;

import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.CellType;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.GameField;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.GameDto;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.GameStatus;

import java.util.Arrays;
import java.util.UUID;

public class GameDtoMapper {
    public static Game toGame(UUID id, GameDto gameDto) {
        return new Game(id, new GameField(gameDto.getField().stream().map(row -> row.stream().map(str -> {
            try {
                return CellType.valueOf(str);
            } catch (Exception e) {
                return CellType.EMPTY;
            }
        }).toArray(CellType[]::new)).toArray(CellType[][]::new)));
    }

    public static GameDto toGameDto(Game game) {
        return new GameDto(Arrays.stream(game.getGameField().getField()).map(row ->
                Arrays.stream(row).map(cellType -> {
                    if (cellType == CellType.EMPTY) {
                        return " ";
                    } else {
                        return cellType.toString();
                    }
                }).toList()
        ).toList());
    }

    public static GameDto toGameDto(Game game, GameStatus status) {
        GameDto gameDto = toGameDto(game);
        gameDto.setStatus(status);
        return gameDto;
    }
}
