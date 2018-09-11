package remi.tile;

import remi.display.DisplayController;
import remi.mainApp;

import static java.util.Collections.shuffle;

/**
 * Manage the two discard piles, the player hands, and the tile pool for the game
 */
public class TileManager {

    private DisplayController display = mainApp.getDisplayController();

    private TilePool tilePool = new TilePool();
    private PlayerHand playerOneHand = new PlayerHand();
    private PlayerHand playerTwoHand = new PlayerHand();
    private DiscardPile playerOneDiscard = new DiscardPile();
    private DiscardPile playerTwoDiscard = new DiscardPile();
    private String lastAction;
    private int lastIndexClicked;
    private int lastRelativeLocation;
    private int pcIndexClicked;
    private int clickCounter = 0;
    private boolean drewACard = false;
    private boolean discardedACard = false;
    private boolean endOfTurn = false;

    public TileManager() {
        newGame();
    }

    /**
     * Generate new tiles and load the set of tiles into the tile pool. Deal 14 tiles to each player's hand. Show the
     * top tile in the tile pool.
     */
    public void newGame() {

        // Note: Might have to remove all painted tiles off of the game board for this to work.
        tilePool.newTileSet();

        for (int i = 0; i < 14; i++) {
            playerOneHand.addTile(tilePool.pop());
            playerTwoHand.addTile(tilePool.pop());
            display.placeTile(playerOneHand.getTile(i), "playerOneHand");
            display.placeTile(playerTwoHand.getTile(i), "playerTwoHand");
            //display.hideTile(playerTwoHand.getTile(i));
        }
        display.placeTile(tilePool.peek(), "tilePool");
        //System.out.println(tilePool.size());
    }

    /**
     * Remove a tile from one location and place it in a new location
     * Locations are defined as "playerOneDiscard, playerTwoDiscard, tilePool, and playerOneHand
     * @param tile The tile to move
     * @param startLocation The location the tile is
     * @param endLocation The location to move to
     */
    public void moveTile(Tile tile, String startLocation, String endLocation) {
        display.removeTile(tile, startLocation);
        display.placeTile(tile, endLocation);
    }

    /**
     * Moves the discard piles to the tile pool. Clears the top display card in the discard piles.
     */
    public void addDiscardPiles() {
        if(!playerOneDiscard.isEmpty()) {
            display.removeTile(playerOneDiscard.peek(), "playerOneDiscard");
            while (!playerOneDiscard.isEmpty()) {
                tilePool.push(playerOneDiscard.pop());
            }
        }
        if(!playerTwoDiscard.isEmpty()) {
            display.removeTile(playerTwoDiscard.peek(), "playerTwoDiscard");
            while (!playerTwoDiscard.isEmpty()) {
                tilePool.push(playerTwoDiscard.pop());
            }
        }
        display.placeTile(tilePool.peek(), "tilePool");
    }

    public boolean isEndOfTurn() {
        return endOfTurn;
    }

    public void setEndOfTurn(boolean value) {
        endOfTurn = value;
    }

    public int getPcIndexClicked() {
        return pcIndexClicked;
    }

    public void setPcIndexClicked(int pcIndexClicked) {
        this.pcIndexClicked = pcIndexClicked;
    }

    /**
     * Takes an action and a player and handles its appropriate action
     * @param actionPerformed The action that was performed
     * @param isHuman If it is a human player or not
     */
    public void handleAction(String actionPerformed, boolean isHuman) {
        //System.out.println("actionClicked = " + display.getActionClicked() + " actionPerformed = " + display.getActionPerformed());
        //System.out.println("lastAction = " + lastAction);

        clickCounter++;
        if(tilePool.isEmpty()) {
            addDiscardPiles();
        }
        /* If this is the first click, save the action performed*/
        if (clickCounter == 1) {
            lastAction = actionPerformed;

            /*If player one clicked their hand first then get the index number / location and highlight the tile*/
            if(actionPerformed.equals("playerOneHandClicked") && isHuman) {
                playerOneHand.getTile(display.getTileIndexClicked()).setSelected(true);
                lastIndexClicked = display.getTileIndexClicked();
                lastRelativeLocation = display.getRelativeTileLocation();
            }
            /* The computer player's action is always set for them and the tile index is given*/
            if(actionPerformed.equals("playerTwoHandClicked") && !isHuman) {
                playerTwoHand.getTile(pcIndexClicked).setSelected(true);
                lastIndexClicked = display.getTileIndexClicked();
                lastRelativeLocation = display.getRelativeTileLocation();
            }
        }

        /* Draw from tile pool
         * If this is the first click and it's the tile pool that is clicked, then add the tile to the player's hand.
         * Reset the click counter*/
        if(actionPerformed.equals("tilePoolClicked") && clickCounter == 1 && (!drewACard)) {
            //System.out.println(tilePool.size());
            clickCounter = 0;
            drewACard = true;
            if(isHuman) {
                moveTile(tilePool.peek(), "tilePool", "playerOneHand");
                playerOneHand.addTile(tilePool.pop());
            }
            else {
                moveTile(tilePool.peek(), "tilePool", "playerTwoHand");
                playerTwoHand.addTile(tilePool.pop());
            }
            display.placeTile(tilePool.peek(), "tilePool");
        }

        /* Human draws from the computer's discard pile
         * If the first click is player two's discard then add the top tile to player one's hand.
         * Reset the click counter*/
        if(actionPerformed.equals("playerTwoDiscardClicked") && clickCounter == 1 && isHuman) {
            clickCounter = 0;
            drewACard = true;
            moveTile(playerTwoDiscard.peek(), "playerTwoDiscard", "playerOneHand");
            playerOneHand.addTile((playerTwoDiscard.pop()));
            if(!playerTwoDiscard.isEmpty()) {
                display.placeTile(playerTwoDiscard.peek(), "playerTwoDiscard");
            }
        }
        /* Computer draws from the computer's discard pile
         * Do the same as previous if player one's discard is clicked by cpu */
        if(actionPerformed.equals("playerOneDiscardClicked") && clickCounter == 1 && !isHuman) {
            clickCounter = 0;
            drewACard = true;
            moveTile(playerOneDiscard.peek(), "playerOneDiscard", "playerTwoHand");
            playerTwoHand.addTile(playerOneDiscard.pop());
            if(!playerOneDiscard.isEmpty()) {
                display.placeTile(playerOneDiscard.peek(), "playerOneDiscard");
            }
        }

        /* If this is the second click, reset the click counter*/
        if (clickCounter >= 2) {
            clickCounter = 0;
            playerOneHand.getTile(lastIndexClicked).setSelected(false);

            /* Player one's hand --> player one's discard
             * If the first click is player one's hand and the second click is player one's discard,
             * then add the first card clicked to the discard pile*/
            if(lastAction.equals("playerOneHandClicked") && actionPerformed.equals("playerOneDiscardClicked")
                                                         && !discardedACard && isHuman) {
                playerOneDiscard.push(playerOneHand.getTile(display.getTileIndexClicked()));
                playerOneHand.removeTile(display.getTileIndexClicked());
                display.placeTile(playerOneDiscard.peek(), "playerOneDiscard");
                display.removeTile(playerOneDiscard.peek(), "playerOneHand");
                discardedACard = true;
            }

            /* Player two's hand --> player two's discard
             * Do the same as for player one except use the set computer player's index */
            if(lastAction.equals("playerTwoHandClicked") && actionPerformed.equals("playerTwoDiscardClicked")
                    && !discardedACard && !isHuman) {
                playerTwoHand.getTile(pcIndexClicked).setSelected(false);
                playerTwoDiscard.push(playerTwoHand.getTile(pcIndexClicked));
                playerTwoHand.removeTile(pcIndexClicked);
                display.placeTile(playerTwoDiscard.peek(), "playerTwoDiscard");
                display.removeTile(playerTwoDiscard.peek(), "playerTwoHand");
                discardedACard = true;
            }

//            if(lastAction.equals("playerOneHandClicked") && actionPerformed.equals("playerOneHandClicked")) {
//
//                Tile tempTile = playerOneHand.getTile(lastIndexClicked);
//                display.removeTile(playerOneHand.getTile(lastIndexClicked), "playerOneHand");
//                playerOneHand.removeTile(lastIndexClicked);
//
//                if(display.getRelativeTileLocation() >= 30) {
//                    playerOneHand.addTile(display.getTileIndexClicked() + 1, tempTile);
//                    display.setTileClickedIndex(display.getTileIndexClicked() + 1);
//                    display.placeTile(tempTile, "playerOneHand");
//                }
//                else {
//                    playerOneHand.addTile((display.getTileIndexClicked()) - 1, tempTile);
//                    display.setTileClickedIndex(display.getTileIndexClicked() - 1);
//                    display.placeTile(tempTile, "playerOneHand");
//                }
//            }
        }

        /* If the player drew a card and discarded a card then end their turn*/
        if(drewACard && discardedACard) {
            drewACard = false;
            discardedACard = false;
            endOfTurn = true;
        }
        display.setActionClicked(false);
    }
}
