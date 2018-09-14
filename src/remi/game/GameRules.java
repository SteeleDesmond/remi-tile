package remi.game;

import remi.tile.PlayerHand;

public class GameRules {

    private PlayerHand playerOneHand;
    private PlayerHand playerTwoHand;
    private String winner;
    private String loser;
    private boolean gameIsOver = false;
    private int loserScore;

    public GameRules(PlayerHand playerOneHand, PlayerHand playerTwoHand) {
        this.playerOneHand = playerOneHand;
        this.playerTwoHand = playerTwoHand;
    }

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
