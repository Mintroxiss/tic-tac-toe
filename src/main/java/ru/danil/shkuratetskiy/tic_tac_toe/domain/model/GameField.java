package ru.danil.shkuratetskiy.tic_tac_toe.domain.model;

import java.util.Arrays;

public class GameField {
    public final int HEIGHT = 3;
    private CellType[][] archiveField;
    private final CellType[][] field;

    public GameField() {
        this.field = initField();
        this.archiveField = initField();
    }

    public void makeFieldArchive() {
        archiveField = new CellType[HEIGHT][HEIGHT];
        for (int i = 0; i < HEIGHT; i++) {
            archiveField[i] = Arrays.copyOf(field[i], HEIGHT);
        }
    }

    public boolean validateField() {
        int counter = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (field[i][j] == archiveField[i][j]) {
                    counter++;
                }
            }
        }

        return counter == HEIGHT * HEIGHT - 1;
    }

    private CellType[][] initField() {
        CellType[][] field = new CellType[HEIGHT][HEIGHT];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                field[i][j] = CellType.EMPTY;
            }
        }
        return field;
    }

    public CellType getFieldCell(int row, int col) {
        return field[row][col];
    }

    public void setFieldCell(int row, int col, CellType value) {
        field[row][col] = value;
    }

    public Winner getWinner() {
        for (int i = 0; i < HEIGHT; i++) {
            CellType rowCell = getFieldCell(i, 0);
            CellType colCell = getFieldCell(0, i);

            boolean rowWin = rowCell != CellType.EMPTY;
            boolean colWin = colCell != CellType.EMPTY;

            for (int j = 1; j < HEIGHT; j++) {
                if (rowWin && getFieldCell(i, j) != rowCell) rowWin = false;
                if (colWin && getFieldCell(j, i) != colCell) colWin = false;
            }

            if (rowWin) return cellTypeToWinner(rowCell);
            if (colWin) return cellTypeToWinner(colCell);
        }

        CellType diagCell = getFieldCell(0, 0);
        boolean diagWin = diagCell != CellType.EMPTY;
        for (int i = 1; i < HEIGHT; i++) {
            if (diagWin && getFieldCell(i, i) != diagCell) diagWin = false;
        }
        if (diagWin) return cellTypeToWinner(diagCell);

        diagCell = getFieldCell(0, HEIGHT - 1);
        diagWin = diagCell != CellType.EMPTY;
        for (int i = 1; i < HEIGHT; i++) {
            if (diagWin && getFieldCell(i, HEIGHT - i - 1) != diagCell) diagWin = false;
        }
        if (diagWin) return cellTypeToWinner(diagCell);

        if (isFieldFullyFilled()) {
            return Winner.DRAW;
        }

        return null;
    }

    private boolean isFieldFullyFilled() {
        int counter = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (field[i][j] != CellType.EMPTY) {
                    counter++;
                }
            }
        }
        return counter >= HEIGHT * HEIGHT;
    }

    private Winner cellTypeToWinner(CellType cellType) {
        if (cellType == CellType.CROSS) return Winner.PLAYER1;
        if (cellType == CellType.ZERO) return Winner.PLAYER2;
        return null;
    }
}
