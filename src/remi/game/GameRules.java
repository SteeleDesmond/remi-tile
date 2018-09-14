package remi.game;

import remi.tile.PlayerHand;
import remi.tile.Tile;
import remi.tile.TilePool;

public class GameRules {

    private PlayerHand playerOneHand;
    private PlayerHand playerTwoHand;
    private String winner;
    private String loser;
    private boolean gameIsOver = false;
    private int loserScore;
    private int setSize;

    private PlayerHand dummyHand = new PlayerHand();



    public GameRules(PlayerHand playerOneHand, PlayerHand playerTwoHand) {
        this.playerOneHand = playerOneHand;
        this.playerTwoHand = playerTwoHand;

        dummyHand.addTile(new Tile(1, "Blue", false));
        dummyHand.addTile(new Tile(1, "Red", false));
        dummyHand.addTile(new Tile(1, "Green", false));
        dummyHand.addTile(new Tile(1, "Yellow", false));

        for(int i = 3; i < 13; i++) {
            dummyHand.addTile(new Tile(i, "Red", false));
        }
    }

    /*Checks if the game is over and returns whether it is or not*/
    public boolean gameIsOver() {
        checkForWin(playerOneHand);
        //checkForWin(playerTwoHand);
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

    private void calculateLoserScore(PlayerHand hand) {

    }

    /**
     * Checks if the following sequence in a section of a list is a set or not
     * Set: 3 or 4 tiles with the same value but different colors
     * @param hand a partial list of a player's hand. The first element is the second potential tile in the set
     * @return the size of the set. 3 to 4 is valid for the required rules
     */
    private int isSet(PlayerHand hand) {
        /*------------------------------------------------------*/
        System.out.printf("Set Sublist = ");
        for(int i = 0; i < hand.size(); i++) {
            System.out.printf("%d ", hand.getTile(i).getNumber());
        }
        System.out.println();
        /*------------------------------------------------------*/
        setSize = 0;
        if(hand.size() < 3) {
            return setSize;
        }

        int num0 = hand.getTile(0).getNumber();
        int num1 = hand.getTile(1).getNumber();
        int num2 = hand.getTile(2).getNumber();
        String color0 = hand.getTile(0).getColor();
        String color1 = hand.getTile(1).getColor();
        String color2 = hand.getTile(2).getColor();

        if((num0 == num1 && !color0.equals(color1))
                && (num0 == num2 && !color0.equals(color2) && !color1.equals(color2))) {
            setSize = 3;
        }
        if(hand.size() > 3) {
            int num3 = hand.getTile(3).getNumber();
            String color3 = hand.getTile(3).getColor();

            if(num3 == num0 && !color3.equals(color0) && !color3.equals(color1) && !color3.equals(color2)) {
                setSize = 4;
            }
        }
        return setSize;
    }

    /**
     * Checks if the following sequence in a section of a list is a run or not
     * Run: 3 or more tiles in sequence and of the same color
     * @param hand a partial list of a player's hand. The first element is the second potential tile in the run
     * @return the size of the run. Greater than 3 is valid for the required rules
     */
    private int isRun(PlayerHand hand) {
        /*------------------------------------------------------*/
        System.out.printf("Run Sublist = ");
        for(int i = 0; i < hand.size(); i++) {
            System.out.printf("%d ", hand.getTile(i).getNumber());
        }
        System.out.println();
        /*------------------------------------------------------*/
        int colorStreak = 0;
        setSize = 0;
        if(hand.size() < 3) {
            return setSize;
        }

        /*Get the size of same-color tiles in a row*/
        for(int i = 0; i < hand.size(); i++) {
            if(hand.getTile(i).getColor().equals(hand.getTile(0).getColor())) {
                colorStreak++;
            }
        }
        System.out.println("colorStreak = " + colorStreak);
        for(int i = 0; i < colorStreak - 1; i++) {
            if(hand.getTile(i).getNumber() == hand.getTile(i+1).getNumber() - 1) {
                setSize++;
            }
        }

        return setSize + 2;
    }

    public void checkForWin(PlayerHand hand) {
        /*------------------------------------------------------*/
        hand = dummyHand;
        System.out.println("Player's Hand: ");
        for(int i = 0; i < hand.size(); i++) {
            System.out.printf("%d  ",hand.getTile(i).getNumber());
        }
        System.out.println();
        System.out.println();
        /*------------------------------------------------------*/

        for(int i = 0; i < hand.size(); i++) {
            System.out.printf("i = %d\n", i);

            /*If true then it is a set*/
            if(isSet(hand.subList(i, hand.size())) > 2) {
                /*Skip ahead in the loop to skip over the set. Subtract 1 as i increments by 1 already*/
                System.out.println("setSize = " + setSize);
                System.out.printf("Add %d to i increment\n", setSize - 1);
                i += setSize - 1;
            }
            /*If true then it is a run*/
            else if(isRun(hand.subList(i, hand.size())) > 2) {
                /*Skip ahead in the loop to skip over the set. Subtract 1 as i increments by 1 already*/
                System.out.println("setSize = " + setSize);
                System.out.printf("Add %d to i increment\n", setSize - 1);
                i += setSize;
            }
            /*Otherwise it is not a win so return*/
            else {
                return;
            }
        }
        /*If it exits the loop without returning then it is a winning hand so set winner and calculate loser score*/
        /*Check if this was player one's hand or not by using the tile id of the first tile*/
        if(playerOneHand.getTile(0).getId() == hand.getTile(0).getId()) {
            winner = "playerOne";
            loser = "playerTwo";
            calculateLoserScore(playerTwoHand);
        }
        /*Else player two won*/
        else {
            winner = "playerTwo";
            loser = "playerOne";
            calculateLoserScore(playerOneHand);
        }
        gameIsOver = true;
    }
}
