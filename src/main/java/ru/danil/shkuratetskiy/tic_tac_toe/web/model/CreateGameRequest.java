package ru.danil.shkuratetskiy.tic_tac_toe.web.model;

public class CreateGameRequest {
    private boolean vsComputer;

    public boolean isVsComputer() { return vsComputer; }

    public void setVsComputer(boolean vsComputer) { this.vsComputer = vsComputer; }
}
