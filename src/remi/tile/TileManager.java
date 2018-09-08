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
    private boolean endOfTurn;

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
            display.hideTile(playerTwoHand.getTile(i));
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

    /**
     * Takes an action and a clickCounter and handles its appropriate action
     * @param actionPerformed The action that was performed
     * @param clickCounter Counts if it was the first or second action to happen. Important for certain actions
     */
    public void handleAction(String actionPerformed, int clickCounter) {

        //System.out.println("actionClicked = " + display.getActionClicked() + " actionPerformed = " + display.getActionPerformed());

        if(tilePool.isEmpty()) {
            addDiscardPiles();
        }

        /* If this is the first click, save the action performed*/
        if (clickCounter == 1) {
            lastAction = actionPerformed;
            if(actionPerformed.equals("playerOneHandClicked")) {
                playerOneHand.getTile(display.getTileIndexClicked()).setSelected(true);
                lastIndexClicked = display.getTileIndexClicked();
                lastRelativeLocation = display.getRelativeTileLocation();
            }
        }
        /* If this is the first click and it's the tile pool that is clicked, then add the tile to player one's hand.
         * Reset the click counter*/
        if(actionPerformed.equals("tilePoolClicked") && clickCounter == 1) {
            //System.out.println(tilePool.size());

            display.setClickCounter(0);
            moveTile(tilePool.peek(), "tilePool", "playerOneHand");
            playerOneHand.addTile(tilePool.pop());
            display.placeTile(tilePool.peek(), "tilePool");

        }
        /* If the first click is player one's discard then add the top tile to player one's hand.
         * Reset the click counter*/
        if(actionPerformed.equals("playerOneDiscardClicked") && clickCounter == 1) {
            display.setClickCounter(0);
            moveTile(playerOneDiscard.peek(), "playerOneDiscard", "playerOneHand");
            playerOneHand.addTile((playerOneDiscard.pop()));
            if(!playerOneDiscard.isEmpty()) {
                display.placeTile(playerOneDiscard.peek(), "playerOneDiscard");
            }
        }
        /* Do the same if player two's discard is clicked first */
        if(actionPerformed.equals("playerTwoDiscardClicked") && clickCounter == 1) {
            display.setClickCounter(0);
            moveTile(playerOneDiscard.peek(), "playerTwoDiscard", "playerOneHand");
            playerOneHand.addTile(playerTwoDiscard.pop());
            if(!playerTwoDiscard.isEmpty()) {
                display.placeTile(playerTwoDiscard.peek(), "playerTwoDiscard");
            }
        }

        /* If this is the second click, reset the click counter*/
        if (clickCounter >= 2) {
            display.setClickCounter(0);
            playerOneHand.getTile(lastIndexClicked).setSelected(false);
            /* If the first click is player one's hand and the second click is player one's discard,
             * then add the first card clicked to the discard pile*/
            if(lastAction.equals("playerOneHandClicked") && actionPerformed.equals("playerOneDiscardClicked")) {
                playerOneDiscard.push(playerOneHand.getTile(display.getTileIndexClicked()));
                playerOneHand.removeTile(display.getTileIndexClicked());
                display.placeTile(playerOneDiscard.peek(), "playerOneDiscard");
                display.removeTile(playerOneDiscard.peek(), "playerOneHand");
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
        display.setActionClicked(false);
    }
}
