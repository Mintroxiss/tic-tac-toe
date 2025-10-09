package ru.danil.shkuratetskiy.tic_tac_toe.datasource.model;

import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.CellType;

public class GameFieldEntity {
    private CellType[][] field;

    public GameFieldEntity() {

    }

    public GameFieldEntity(CellType[][] field) {
        this.field = field;
    }

    public CellType[][] getField() {
        return field;
    }

    public void setField(CellType[][] field) {
        this.field = field;
    }
}
