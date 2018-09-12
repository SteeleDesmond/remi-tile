package remi.game;

import remi.player.ComputerPlayer;
import remi.player.Player;

public class GameCoordinator {

    private GameStatus status = new GameStatus();
    private GameScore score = new GameScore();
    private GameRules rules = new GameRules();
    private Player playerOne = new Player();
    private ComputerPlayer computer = new ComputerPlayer();

    public void start() {

        if(status.isPlayerOnesTurn()) {
            /* Wait for input from the player */
            if(playerOne.getAction()) {
                playerOne.handleAction(true);
                if(playerOne.isEndOfTurn()) {
                    System.out.println("End of human turn");
                    rules.isSet();
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
                if(rules.gameIsOver()) {
                    score.updateScore(rules.getWinner(), -50);
                    score.updateScore(rules.getLoser(), rules.getLoserScore());
                }
            }
        }
    }
}
