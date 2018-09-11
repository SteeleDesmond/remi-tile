package remi.tile;

import remi.display.DisplayController;
import remi.mainApp;

import static java.util.Collections.shuffle;

/**
 * Manage the two discard piles, the player hands, and the tile pool for the game
 */
public class TileManager {

    private DisplayController display = mainApp.getDisplayController();

    public TilePool tilePool = new TilePool();
    public PlayerHand playerOneHand = new PlayerHand();
    public PlayerHand playerTwoHand = new PlayerHand();
    public DiscardPile playerOneDiscard = new DiscardPile();
    public DiscardPile playerTwoDiscard = new DiscardPile();

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
}
