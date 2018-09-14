package remi.game;

public class GameStatus {

    private boolean isPlayerOnesTurn = true;
    private boolean isComputerPlayersTurn = false;

    public boolean isPlayerOnesTurn() {
        return isPlayerOnesTurn;
    }

    public void setPlayerOnesTurn(boolean playerOnesTurn) {
        isPlayerOnesTurn = playerOnesTurn;
    }

    public boolean isComputerPlayersTurn() {
        return isComputerPlayersTurn;
    }

    public void setComputerPlayersTurn(boolean computerPlayersTurn) {
        isComputerPlayersTurn = computerPlayersTurn;
    }

    public void newGame() {
        isPlayerOnesTurn = true;
        isComputerPlayersTurn = false;
    }

}
