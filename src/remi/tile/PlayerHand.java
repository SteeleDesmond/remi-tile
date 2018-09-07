package remi.tile;

import java.util.ArrayList;

/**
 * One instance for each player
 */
public class PlayerHand {

    private Tile playerHand[] = new Tile[14];

    public PlayerHand() {
    }

    public Tile getTile(int index) {
        return playerHand[index];
    }

    public void addTile(Tile tile) {
        for(int i = 0; i < playerHand.length; i++) {
            if(playerHand[i] == null) {
                playerHand[i] = tile;
                return;
            }
        }
    }

    public int size() {
        int rv = 0;
        for(int i = 0; i < 14; i++) {
            if(playerHand[i] != null) {
                rv++;
            }
        }
        return rv;
    }
}
