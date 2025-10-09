package ru.danil.shkuratetskiy.tic_tac_toe.web.model;

import java.util.List;

public class MoveDto {
    private int row;
    private int col;
    private List<List<String>> field;

    public MoveDto() {}

    public MoveDto(int row, int col, List<List<String>> field) {
        this.row = row;
        this.col = col;
        this.field = field;
    }

    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }

    public List<List<String>> getField() { return field; }
    public void setField(List<List<String>> field) { this.field = field; }
}

