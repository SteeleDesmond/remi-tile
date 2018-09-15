package remi.game;

import remi.tile.PlayerHand;
import remi.tile.Tile;
import remi.tile.TilePool;

/**
 * Contains logic to check if the game has a winner or not. There are 3 code blocks commented out that can be used to
 * test win detection for a basic case. The test doesn't test the actual game pieces or scores.
 */
public class GameRules {

    private PlayerHand playerOneHand;
    private PlayerHand playerTwoHand;
    private String winner;
    private String loser;
    private boolean gameIsOver = false;
    private int loserScore;
    private int setSize;

    public GameRules(PlayerHand playerOneHand, PlayerHand playerTwoHand) {
        this.playerOneHand = playerOneHand;
        this.playerTwoHand = playerTwoHand;
    }

    /*Checks if the game is over and returns whether it is or not*/
    public boolean gameIsOver() {
        checkForWin(playerOneHand);
        checkForWin(playerTwoHand);
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

    /**
     * Calculate the loser's score based on the game's rules
     * @param hand The loser's hand
     */
    private void calculateLoserScore(PlayerHand hand) {
        for(int i = 0; i < hand.size(); i++) {
            if(hand.getTile(i).isJoker()) {
                loserScore += 25;
            }
            else {
                loserScore += hand.getTile(i).getNumber();
            }
        }
    }

    public void reset() {
        winner = null;
        loser = null;
        gameIsOver = false;
        loserScore = 0;
        setSize = 0;
    }

    /**
     * Checks if the following sequence in a section of a list is a set or not
     * Set: 3 or 4 tiles with the same value but different colors
     * @param hand a partial list of a player's hand. The first element is the second potential tile in the set
     * @return the size of the set. 3 to 4 is valid for the required rules
     */
    private int isSet(PlayerHand hand) {
//        /*Uncomment for testing win detection*/
//        /*------------------------------------------------------*/
//        System.out.printf("Set Sublist = ");
//        for(int i = 0; i < hand.size(); i++) {
//            System.out.printf("%d ", hand.getTile(i).getNumber());
//        }
//        System.out.println();
//        /*------------------------------------------------------*/

        setSize = 0;
        if(hand.size() < 3) {
            return setSize;
        }
        /*Made so that the if statements are shorter*/
        int num0 = hand.getTile(0).getNumber();
        int num1 = hand.getTile(1).getNumber();
        int num2 = hand.getTile(2).getNumber();
        String color0 = hand.getTile(0).getColor();
        String color1 = hand.getTile(1).getColor();
        String color2 = hand.getTile(2).getColor();
        boolean joker0 = hand.getTile(0).isJoker();
        boolean joker1 = hand.getTile(1).isJoker();
        boolean joker2 = hand.getTile(2).isJoker();


        /* Compare the colors and numbers of the first 3 tiles*/
        if((num0 == num1 && !color0.equals(color1))
                && (num0 == num2 && !color0.equals(color2) && !color1.equals(color2))) {
            setSize = 3;
        }
        /* If it still isn't a set check for joker cases*/
        if(setSize < 3) {
            if(joker0) {
                if(num1 == num2 && !color1.equals(color2)) {
                    setSize = 3;
                }
            }
            if(joker1) {
                if(num0 == num2 && !color0.equals(color2)) {
                    setSize = 3;
                }
            }
            if(joker2) {
                if(num0 == num1 && !color0.equals(color1)) {
                    setSize = 3;
                }
            }
        }
        /* If the first 3 are a set and there is a 4th tile then check if the 4th tile is in the set*/
        if(setSize == 3 && hand.size() > 3) {
            int num3 = hand.getTile(3).getNumber();
            String color3 = hand.getTile(3).getColor();
            boolean joker3 = hand.getTile(3).isJoker();

            if(joker3 || (num3 == num0 && !color3.equals(color0) && !color3.equals(color1) && !color3.equals(color2))) {
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
//        /*Uncomment for testing win detection*/
//        /*------------------------------------------------------*/
//        System.out.printf("Run Sublist = ");
//        for(int i = 0; i < hand.size(); i++) {
//            System.out.printf("%d ", hand.getTile(i).getNumber());
//        }
//        System.out.println();
//        /*------------------------------------------------------*/

        int colorStreak = 0;
        setSize = 1;
        if(hand.size() < 3) {
            return setSize;
        }

        /*Get the size of same-color tiles in a row*/
        for(int i = 0; i < hand.size(); i++) {
            if(hand.getTile(i).isJoker() || hand.getTile(i).getColor().equals(hand.getTile(0).getColor())) {
                colorStreak++;
            }
        }
        /*Compare each tile's number with the tile next to it and count the run size*/
        for(int i = 0; i < colorStreak - 1; i++) {
            if(hand.getTile(i).isJoker() || hand.getTile(i).getNumber() == hand.getTile(i+1).getNumber() - 1) {
                setSize++;
            }
            /*If it is a 13 followed by a 1 it is also a valid run per the rules*/
            if((hand.getTile(i).getNumber() == 13) && hand.getTile(i+1).getNumber() == 1) {
                setSize++;
            }
        }
        return setSize;
    }

    /**
     *  Check if a hand is a winning hand
     * @param hand The hand that is to be tested for a win
     */
    public void checkForWin(PlayerHand hand) {
        /*Uncomment for testing win detection*/
//        /*------------------------------------------------------*/
//        hand = makeDummyHand();
//        System.out.println("Player's Hand: ");
//        for(int i = 0; i < hand.size(); i++) {
//            System.out.printf("%d  ",hand.getTile(i).getNumber());
//        }
//        System.out.println();
//        /*------------------------------------------------------*/

        for(int i = 0; i < hand.size(); i++) {

            /*If true then it is a set*/
            if(isSet(hand.subList(i, hand.size())) > 2) {
                /*Skip ahead in the loop to skip over the set. Subtract 1 as i increments by 1 already*/
                i += setSize - 1;
            }
            /*If true then it is a run*/
            else if(isRun(hand.subList(i, hand.size())) > 2) {
                /*Skip ahead in the loop to skip over the set. Subtract 1 as i increments by 1 already*/
                i += setSize - 1;
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


    /**
     * Convenience method used for testing purposes
     * @return a PlayerHand object with a winning hand under basic rules
     */
    private PlayerHand makeDummyHand() {

        PlayerHand dummyHand = new PlayerHand();

        for(int i = 3; i < 12; i++) {
            dummyHand.addTile(new Tile(i, "Red", false));
        }
        dummyHand.addTile(new Tile(2, "Red", true));
        dummyHand.addTile(new Tile(1, "Blue", false));
        dummyHand.addTile(new Tile(1, "Red", false));
        dummyHand.addTile(new Tile(1, "Green", false));
        dummyHand.addTile(new Tile(1, "Yellow", false));
        return dummyHand;
    }
}
