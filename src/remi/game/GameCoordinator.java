package remi.game;

import remi.display.DisplayController;
import remi.mainApp;
import remi.tile.TileManager;

public class GameCoordinator {

    private TileManager tileManager;
    private DisplayController display = mainApp.getDisplayController();

    public GameCoordinator() {
        tileManager = new TileManager();
    }


    public void start() {

        if(display.getActionClicked()) {
            tileManager.handleAction(display.getActionPerformed(), display.getClickCounter());
        }
    }
}
