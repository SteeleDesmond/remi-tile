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
                tileManager.handleAction(display.getActionPerformed(), true);
                if(tileManager.isEndOfTurn()) {
                    System.out.println("End of human turn");
                    status.setPlayerOnesTurn(false);
                    status.setComputerPlayersTurn(true);
                    tileManager.setEndOfTurn(false);
                }
            }
        }
        else if(status.isComputerPlayersTurn()) {
//            tileManager.handleAction("tilePoolClicked", 1, false);
//            tileManager.handleAction("playerTwoHandClicked", 1, false);
//            tileManager.handleAction("playerTwoDiscardClicked", 2, false);
            if(tileManager.isEndOfTurn()) {
                System.out.println("End of computer turn");
                status.setComputerPlayersTurn(false);
                status.setPlayerOnesTurn(true);
                tileManager.setEndOfTurn(false);
            }
        }
    }
}
