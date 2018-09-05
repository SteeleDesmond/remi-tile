package remi.display;

import remi.display.DisplayController;
import remi.display.Tile;

import java.util.ArrayList;

public class GameTiles extends DisplayController {

    private ArrayList<Tile> tileSet;

    public GameTiles() {
        tileSet = new ArrayList<>();
        //generateTiles();

        for(int i = 0; i < 14; i++) {
            placeTile(tileSet.get(i), "playerOneHand");
        }
        for(int i = 15; i < 28; i++) {
            placeTile(tileSet.get(i), "playerTwoHand");
        }
    }

//    private void generateTiles() {
//
//        for(int i = 0; i < 13; i++) {
//                Tile newRedTile = new Tile(i, "Red", false);
//                Tile newBlueTile = new Tile(i, "Blue", false);
//                Tile newGreenTile = new Tile(i, "Green", false);
//                Tile newYellowTile = new Tile(i, "Yellow", false);
//                tileSet.add(newRedTile);
//                tileSet.add(newBlueTile);
//                tileSet.add(newGreenTile);
//                tileSet.add(newYellowTile);
//        }
//        Tile redJokerTile = new Tile(0, "Red", true);
//        Tile blueJokerTile = new Tile(0, "Blue", true);
//        tileSet.add(redJokerTile);
//        tileSet.add(blueJokerTile);
//    }
}
