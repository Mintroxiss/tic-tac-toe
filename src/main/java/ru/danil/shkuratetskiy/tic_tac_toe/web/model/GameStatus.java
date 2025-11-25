package ru.danil.shkuratetskiy.tic_tac_toe.web.model;

public enum GameStatus {
    OK(""),
    INCORRECT_MOVE("ERROR: Некорректный ход!"),
    WIN("Победа"),
    LOSS("Проигрыш"),
    DRAW("Ничья");

    private final String message;

    GameStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
