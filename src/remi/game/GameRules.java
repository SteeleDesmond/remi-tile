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

    public boolean gameIsOver() {
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

    public void isSet() {

        for(int i = 0; i < tm.playerOneHand.size() - 1; i++) {
            if(tm.playerOneHand.getTile(i).getNumber() == tm.playerOneHand.getTile(i+1).getNumber()
                    && tm.playerOneHand.getTile(i).getNumber() == tm.playerOneHand.getTile(i-1).getNumber()) {
                System.out.println("3 in a row");
            }
        }
    }

    public void isRun() {

    }
}
