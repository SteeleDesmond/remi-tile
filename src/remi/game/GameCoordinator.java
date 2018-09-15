package remi.game;

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
            if(playerOne.clickedReset()) {
                tm.newGame();
                score.newGame();
                status.newGame();
                rules.reset();
                playerOne.setReset(false);
            }
            if(playerOne.getAction()) {
                playerOne.handleAction(true);
                if(playerOne.isEndOfTurn()) {
                    //System.out.println("End of human turn");
                    status.setPlayerOnesTurn(false);
                    status.setComputerPlayersTurn(true);
                    playerOne.setEndOfTurn(false);
                    /*If playerOne wins, reset the board and set the scores*/
                    if(rules.gameIsOver()) {
                        //System.out.println("Player One Wins!");
                        score.updateScore(rules.getWinner(), -50);
                        score.updateScore(rules.getLoser(), rules.getLoserScore());
                        tm.newGame();
                        rules.reset();
                        status.newGame();
                    }
                }
            }
        }
        else if(status.isComputerPlayersTurn()) {
            /*Process is same as player one except computer automatically makes a move and reorganizes its hand*/
            computer.makeAMove();
            computer.sortHand();
            if(computer.isEndOfTurn()) {
                //System.out.println("End of computer turn");
                status.setComputerPlayersTurn(false);
                status.setPlayerOnesTurn(true);
                computer.setEndOfTurn(false);
                computer.sortHand();
                if(rules.gameIsOver()) {
                    System.out.println("Computer Wins!");
                    score.updateScore(rules.getWinner(), -50);
                    score.updateScore(rules.getLoser(), rules.getLoserScore());
                    tm.newGame();
                    rules.reset();
                    status.newGame();
                }
            }
        }
    }
}
