package remi.tile;

import remi.display.DisplayController;

/**
 * Manage the two discard piles, the player hands, and the tile pool for the game
 */
public class TileManager {

    private DisplayController display;
    private TilePool tilePool = new TilePool();
    private PlayerHand playerOneHand = new PlayerHand();
    private PlayerHand playerTwoHand = new PlayerHand();
    private DiscardPile playerOneDiscard = new DiscardPile();

    public TileManager(DisplayController display) {
        this.display = display;
        newGame();
    }

    public TilePool getTilePool() {
        return tilePool;
    }

    public PlayerHand getPlayerOneHand() {
        return playerOneHand;
    }

    public PlayerHand getPlayerTwoHand() {
        return playerTwoHand;
    }

    public DiscardPile getPlayerOneDiscard() {
        return playerOneDiscard;
    }

    public DiscardPile getPlayerTwoDiscard() {
        return playerTwoDiscard;
    }

    private DiscardPile playerTwoDiscard = new DiscardPile();

    /**
     * Generate new tiles and load the set of tiles into the tile pool. Deal 14 tiles to each player's hand. Show the
     * top tile in the tile pool.
     */
    public void newGame() {

        // Remove all painted tiles off from the game board
        if(tilePool.size() > 1) {
            for(int i = 0; i < playerOneHand.size(); i++) {
                display.removeTile(playerOneHand.getTile(i), "playerOneHand");
            }
            for(int i = 0; i < playerTwoHand.size(); i++) {
                display.removeTile(playerTwoHand.getTile(i), "playerTwoHand");
            }
            while(!playerOneDiscard.isEmpty()) {
                display.removeTile(playerOneDiscard.pop(), "playerOneDiscard");
            }
            while(!playerTwoDiscard.isEmpty()) {
                display.removeTile(playerTwoDiscard.pop(), "playerTwoDiscard");
            }
            playerOneHand.clear();
            playerTwoHand.clear();
            playerOneDiscard.clear();
            playerTwoDiscard.clear();
        }

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
}
