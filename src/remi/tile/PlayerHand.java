package remi.tile;

import java.util.ArrayList;

/**
 * One instance for each player
 */
public class PlayerHand {

    private ArrayList<Tile> playerHand = new ArrayList<>();

    public PlayerHand() {
    }

    public Tile getTile(int index) {
        return playerHand.get(index);
    }

    public void addTile(Tile tile) {
        playerHand.add(tile);
    }

    public int size() {
        return playerHand.size();
    }

    public void removeTile(Tile tile) {
        playerHand.remove(tile);
    }

    public void removeTile(int index) {
        playerHand.remove(index);
    }

    public void addTile(int index, Tile tile) {
        playerHand.add(index, tile);
    }
}
