package ru.danil.shkuratetskiy.tic_tac_toe.web.model;

import java.util.List;

public class GameDto {
    private final static String STATUS_OK = "OK";

    private List<List<String>> field;
    private String status;

    public GameDto(List<List<String>> field, String status) {
        this.field = field;
        this.status = status;
    }

    public GameDto(List<List<String>> field) {
        this(field, STATUS_OK);
    }

    public List<List<String>> getField() {
        return field;
    }

    public void setField(List<List<String>> field) {
        this.field = field;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
