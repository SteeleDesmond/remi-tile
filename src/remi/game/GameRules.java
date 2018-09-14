package remi.game;

import remi.player.Player;
import remi.tile.Tile;
import remi.tile.TileManager;

public class GameRules {

    private TileManager tm = Player.getGameTiles();
    private String winner;
    private String loser;
    private boolean gameIsOver = false;
    private int loserScore;

    /*Checks if the game is over and returns whether it is or not*/
    public boolean gameIsOver() {
        checkForWin();
        return gameIsOver;
    }

    public String getWinner() {
        return winner;
    }

    public String getLoser() {
        return loser;
    }

    public int getLoserScore() {
        return loserScore;
    }

    public void calculateLoserScore() {

    }

    public void isSet() {


    }

    public void isRun() {

    }

    public void checkForWin() {

    }
}
