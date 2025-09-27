package ru.danil.shkuratetskiy.tic_tac_toe.datasource.model;

import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.CellType;

public class GameFieldEntity {
    private CellType[][] archiveField;
    private CellType[][] field;

    public GameFieldEntity() {

    }

    public GameFieldEntity(CellType[][] archiveField, CellType[][] field) {
        this.archiveField = archiveField;
        this.field = field;
    }

    public CellType[][] getArchiveField() {
        return archiveField;
    }

    public void setArchiveField(CellType[][] archiveField) {
        this.archiveField = archiveField;
    }

    public CellType[][] getField() {
        return field;
    }

    public void setField(CellType[][] field) {
        this.field = field;
    }
}
