package remi.tile;

import java.util.ArrayList;

import static java.util.Collections.shuffle;

public class TilePool {

    private ArrayList<Tile> tilePool = new ArrayList<>();

    public TilePool() {
    }

    public Tile peek() {
        return tilePool.get(tilePool.size() - 1);
    }

    public Tile pop() {

        Tile tile = tilePool.get(tilePool.size() - 1);
        tilePool.remove(tilePool.size() - 1);
        return tile;
    }

    public void push(Tile tile) {
        tilePool.add(tile);
    }

    /**
     * Generate and shuffle a new set of tiles.
     */
    public void newTileSet() {

        tilePool.clear();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 13; i++) {
                Tile newRedTile = new Tile(i, "Red", false);
                Tile newBlueTile = new Tile(i, "Blue", false);
                Tile newGreenTile = new Tile(i, "Green", false);
                Tile newYellowTile = new Tile(i, "Yellow", false);
                tilePool.add(newRedTile);
                tilePool.add(newBlueTile);
                tilePool.add(newGreenTile);
                tilePool.add(newYellowTile);
            }
            Tile redJokerTile = new Tile(0, "Red", true);
            Tile blueJokerTile = new Tile(0, "Blue", true);
            tilePool.add(redJokerTile);
            tilePool.add(blueJokerTile);
        }

        shuffle(tilePool);
        //System.out.println("Size of tilePool " + tilePool.size());
    }

    public boolean isEmpty() {
        return tilePool.isEmpty();
    }

    public int size() {
        return tilePool.size();
    }
}
