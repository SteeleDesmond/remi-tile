package remi.game;

import remi.display.DisplayController;
import remi.mainApp;

public class GameScore {

    private DisplayController display = mainApp.getDisplayController();

    public int getScore(String player) {
        return display.getScore(player);
    }

    public void updateScore(String player, int value) {
        display.updateScore(player, value);
    }

    public void newGame() {
        display.updateScore("playerOne", 0);
        display.updateScore("playerTwo", 0);
    }
}
