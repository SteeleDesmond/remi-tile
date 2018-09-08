package remi.game;

import remi.display.DisplayController;
import remi.mainApp;
import remi.player.ComputerPlayer;
import remi.tile.TileManager;

public class GameCoordinator {

    private ComputerPlayer cp = new ComputerPlayer();
    private TileManager tileManager;
    private DisplayController display = mainApp.getDisplayController();
    private GameStatus status = new GameStatus();

    public GameCoordinator() {
        tileManager = new TileManager();
    }


    public void start() {

        if(status.isPlayerOnesTurn()) {
            if(display.getActionClicked()) {
                tileManager.handleAction(display.getActionPerformed(), display.getClickCounter());
//                if(tileManager.endOfTurn) {
                    status.setPlayerOnesTurn(true);
                    status.setComputerPlayersTurn(true);
//                }
            }
        }
        else if(status.isComputerPlayersTurn()) {
            tileManager.handleAction("tilePoolClicked", 1);
            tileManager.handleAction("playerTwoHandClicked", 1);
            tileManager.handleAction("playerTwoDiscardClicked", 2);
            status.setComputerPlayersTurn(false);
            status.setPlayerOnesTurn(true);
        }
    }
}
