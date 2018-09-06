package remi.tile;

import remi.display.DisplayController;
import remi.mainApp;

import java.util.ArrayList;

/**
 * Manage the two discard piles and the tile pool for the game
 */
public class TileManager {

    private ArrayList<Tile> tileSet;
    private DisplayController display = mainApp.getDisplayController();

    public TileManager() {
        tileSet = new ArrayList<>();
        generateTileSet();

        moveTile(tileSet.get(0), "playerOneHand", "tilePool");
    }

    private void generateTileSet() {

        for(int i = 0; i < 13; i++) {
            Tile newRedTile = new Tile(i, "Red", false);
            Tile newBlueTile = new Tile(i, "Blue", false);
            Tile newGreenTile = new Tile(i, "Green", false);
            Tile newYellowTile = new Tile(i, "Yellow", false);
            tileSet.add(newRedTile);
            tileSet.add(newBlueTile);
            tileSet.add(newGreenTile);
            tileSet.add(newYellowTile);
        }
        Tile redJokerTile = new Tile(0, "Red", true);
        Tile blueJokerTile = new Tile(0, "Blue", true);
        tileSet.add(redJokerTile);
        tileSet.add(blueJokerTile);
    }

    public void moveTile(Tile tile, String startLocation, String endLocation) {
        display.removeTile(tile, startLocation);
        display.placeTile(tile, endLocation);
    }
}
