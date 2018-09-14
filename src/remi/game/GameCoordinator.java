package remi.game;

import remi.display.DisplayController;
import remi.mainApp;
import remi.player.ComputerPlayer;
import remi.player.Player;
import remi.tile.TileManager;

public class GameCoordinator {

    private TileManager tm = new TileManager(mainApp.getDisplayController());
    private GameStatus status = new GameStatus();
    private GameScore score = new GameScore();
    private GameRules rules = new GameRules(tm.getPlayerOneHand(), tm.getPlayerTwoHand());
    private Player playerOne = new Player(mainApp.getDisplayController(), tm);
    private ComputerPlayer computer = new ComputerPlayer(mainApp.getDisplayController(), tm);

    public void start() {

        if(status.isPlayerOnesTurn()) {
            /* Wait for input from the player */
            if(playerOne.getAction()) {
                playerOne.handleAction(true);
                if(playerOne.isEndOfTurn()) {
                    System.out.println("End of human turn");
                    status.setPlayerOnesTurn(false);
                    status.setComputerPlayersTurn(true);
                    playerOne.setEndOfTurn(false);
                    if(rules.gameIsOver()) {
                        score.updateScore(rules.getWinner(), -50);
                        score.updateScore(rules.getLoser(), rules.getLoserScore());
                    }
                }
            }
        }
        else if(status.isComputerPlayersTurn()) {
            computer.makeAMove();
            if(computer.isEndOfTurn()) {
                System.out.println("End of computer turn");
                status.setComputerPlayersTurn(false);
                status.setPlayerOnesTurn(true);
                computer.setEndOfTurn(false);
                computer.sortHand();
                if(rules.gameIsOver()) {
                    score.updateScore(rules.getWinner(), -50);
                    score.updateScore(rules.getLoser(), rules.getLoserScore());
                }
            }
        }
    }
}
