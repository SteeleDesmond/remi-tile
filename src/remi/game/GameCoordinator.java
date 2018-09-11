package remi.game;

import remi.display.DisplayController;
import remi.mainApp;
import remi.tile.TileManager;

public class GameCoordinator {

    private TileManager tileManager;
    private DisplayController display = mainApp.getDisplayController();
    private GameStatus status = new GameStatus();
    private Player players = new Player();

    public GameCoordinator() {

        //tileManager = new TileManager();
    }


    public void start() {

        if(status.isPlayerOnesTurn()) {

            /* Wait for input from the player */
            if(display.getActionClicked()) {
                players.handleAction(true);
                if(players.isEndOfTurn()) {
                    System.out.println("End of human turn");
                    status.setPlayerOnesTurn(false);
                    status.setComputerPlayersTurn(true);
                    players.setEndOfTurn(false);
                }
            }
        }
        else if(status.isComputerPlayersTurn()) {
            players.makeAMove();
            if(players.isEndOfTurn()) {
                System.out.println("End of computer turn");
                status.setComputerPlayersTurn(false);
                status.setPlayerOnesTurn(true);
                players.setEndOfTurn(false);
            }
        }
    }
}
