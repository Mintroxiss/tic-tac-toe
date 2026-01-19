package ru.danil.shkuratetskiy.tic_tac_toe.web.model;

public class MoveDto {
    private int row;
    private int col;

    public MoveDto() {}

    public MoveDto(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }

    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }

    public void setCol(int col) { this.col = col; }
}
