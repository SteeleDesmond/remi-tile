package remi.game;

import remi.tile.TileManager;

public class GameCoordinator {

    private TileManager tileManager;

    public void start() {
        tileManager = new TileManager();
    }
}
