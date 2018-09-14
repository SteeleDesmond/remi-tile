package remi.player;

import javafx.animation.AnimationTimer;
import remi.display.DisplayController;
import remi.mainApp;
import remi.tile.*;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Takes input from input the display and coordinates where tiles should go. Organizes tiles in the TileManager.
 * Input is taken via the handleAction() method.
 */
public class Player {

    private DisplayController display;
    private TileManager tm;
    private TilePool tilePool;
    private PlayerHand playerOneHand;
    private PlayerHand playerTwoHand;
    private DiscardPile playerOneDiscard;
    private DiscardPile playerTwoDiscard;
    private String actionPerformed;
    private String lastAction;
    private int lastIndexClicked;
    private int pcIndexClicked;
    private int clickCounter = 0;
    private boolean isHuman;
    private boolean drewATile = false;
    private boolean discardedATile = false;
    private boolean endOfTurn = false;

    public Player(DisplayController display, TileManager tm) {
        this.display = display;
        this.tm = tm;
        tilePool = tm.getTilePool();
        playerOneHand = tm.getPlayerOneHand();
        playerTwoHand = tm.getPlayerTwoHand();
        playerOneDiscard = tm.getPlayerOneDiscard();
        playerTwoDiscard = tm.getPlayerTwoDiscard();
    }

    /**
     * Takes a player and handles its appropriate action. Encompasses game logic
     * @param isHuman If it is a human player or not
     */
    public void handleAction(boolean isHuman) {
        //System.out.println("actionClicked = " + display.getActionClicked() + " actionPerformed = " + display.getActionPerformed());
        //System.out.println("lastAction = " + lastAction);

        if(isHuman) {
            actionPerformed = display.getActionPerformed();
        }
        this.isHuman = isHuman;
        clickCounter++;

        if(tilePool.isEmpty()) {
            tm.addDiscardPiles();
        }

        /* If this is the first click, save the action performed*/
        if (clickCounter == 1) {
            lastAction = actionPerformed;

            if(actionPerformed.equals("playerOneHandClicked") || actionPerformed.equals("playerTwoHandClicked")) {
                /*If player one clicked their hand first then get the index number / location and highlight the tile*/
                if(actionPerformed.equals("playerOneHandClicked") && isHuman) {
                    playerOneHand.getTile(display.getTileIndexClicked()).setSelected(true);
                    lastIndexClicked = display.getTileIndexClicked();
                }
                /* The computer player's action is always set for them and the tile index is given*/
                if(actionPerformed.equals("playerTwoHandClicked") && !isHuman) {
                    playerTwoHand.getTile(pcIndexClicked).setSelected(true);
                    lastIndexClicked = display.getTileIndexClicked();
                }
            }
        }

        /* If they clicked the tile pool or a discard pile first and they haven't drawn a card yet then handle a draw
         * action */
        if((actionPerformed.equals("tilePoolClicked") || actionPerformed.equals("playerTwoDiscardClicked")
                || actionPerformed.equals("playerOneDiscardClicked"))
                && clickCounter == 1 && !drewATile) {
            drawATile();
        }

        /* If this is the second click, reset the click counter*/
        if (clickCounter >= 2) {
            clickCounter = 0;
            if(lastIndexClicked < 14) {
                playerOneHand.getTile(lastIndexClicked).setSelected(false);
            }

            if(lastAction.equals("playerOneHandClicked") || lastAction.equals("playerTwoHandClicked")) {
                discardATile();
            }

            /* Move the location of the card in the hand*/
            if(lastAction.equals("playerOneHandClicked") && actionPerformed.equals("playerOneHandClicked")) {
                moveACardInHand();
            }
        }

        /* If the player drew a card and discarded a card then end their turn*/
        if(drewATile && discardedATile) {
            drewATile = false;
            discardedATile = false;
            endOfTurn = true;
        }
        display.setActionClicked(false);
    }

    /**
     * Handle a player drawing a tile from either the tile pool or a discard pile.
     */
    private void drawATile() {
        //System.out.println(tilePool.size());

        /* Draw from tile pool
         * If this is the first click and it's the tile pool that is clicked, then add the tile to the player's hand.
         * Reset the click counter*/
        if (actionPerformed.equals("tilePoolClicked")) {
            clickCounter = 0;
            drewATile = true;
            if (isHuman) {
                tm.moveTile(tilePool.peek(), "tilePool", "playerOneHand");
                playerOneHand.addTile(tilePool.pop());
            } else {
                tm.moveTile(tilePool.peek(), "tilePool", "playerTwoHand");
                playerTwoHand.addTile(tilePool.pop());
            }
            display.placeTile(tilePool.peek(), "tilePool");
        }

        /* Human draws from the computer's discard pile
         * If the first click is player two's discard then add the top tile to player one's hand.
         * Reset the click counter*/
        else if (actionPerformed.equals("playerTwoDiscardClicked") && isHuman) {
            clickCounter = 0;
            drewATile = true;
            tm.moveTile(playerTwoDiscard.peek(), "playerTwoDiscard", "playerOneHand");
            playerOneHand.addTile((playerTwoDiscard.pop()));
            if (!playerTwoDiscard.isEmpty()) {
                display.placeTile(playerTwoDiscard.peek(), "playerTwoDiscard");
            }
        }
        /* Computer draws from the human's discard pile
         * Do the same as previous if player one's discard is clicked by cpu */
        else if(actionPerformed.equals("playerOneDiscardClicked") && !isHuman) {
            clickCounter = 0;
            drewATile = true;
            tm.moveTile(playerOneDiscard.peek(), "playerOneDiscard", "playerTwoHand");
            playerTwoHand.addTile(playerOneDiscard.pop());
            if(!playerOneDiscard.isEmpty()) {
                display.placeTile(playerOneDiscard.peek(), "playerOneDiscard");
            }
        }

    }

    private void discardATile() {
        /* Player one's hand --> player one's discard
         * If the first click is player one's hand and the second click is player one's discard,
         * then add the first card clicked to the discard pile*/
        if(lastAction.equals("playerOneHandClicked") && actionPerformed.equals("playerOneDiscardClicked")
                                                     && !discardedATile && isHuman) {
            playerOneDiscard.push(playerOneHand.getTile(display.getTileIndexClicked()));
            playerOneHand.removeTile(display.getTileIndexClicked());
            display.placeTile(playerOneDiscard.peek(), "playerOneDiscard");
            display.removeTile(playerOneDiscard.peek(), "playerOneHand");
            discardedATile = true;
        }

        /* Player two's hand --> player two's discard
         * Do the same as for player one except use the set computer player's index */
        if(lastAction.equals("playerTwoHandClicked") && actionPerformed.equals("playerTwoDiscardClicked")
                                                     && !discardedATile && !isHuman) {
            playerTwoHand.getTile(pcIndexClicked).setSelected(false);
            playerTwoDiscard.push(playerTwoHand.getTile(pcIndexClicked));
            playerTwoHand.removeTile(pcIndexClicked);
            display.placeTile(playerTwoDiscard.peek(), "playerTwoDiscard");
            display.removeTile(playerTwoDiscard.peek(), "playerTwoHand");
            discardedATile = true;
        }
    }

    public void moveACardInHand() {
        display.setHandIndexClicked(true);
        Tile tileToMove = playerOneHand.getTile(lastIndexClicked);
        playerOneHand.removeTile(tileToMove);
        display.removeTile(tileToMove, "playerOneHand");

        /* If the player clicked on the right side or the center of the second card, place the last card to the
         * right of the second card clicked. Checks the hand's bounds */
        if(display.getRelativeTileLocation() >= 30 && display.getTileIndexClicked() < 13
                || display.getTileIndexClicked() == 0) {
            playerOneHand.addTile(display.getTileIndexClicked() + 1, tileToMove);
            //display.setTileClickedIndex(display.getTileIndexClicked() + 1);
            display.placeTile(tileToMove, "playerOneHand");
        }
        else {
            playerOneHand.addTile((display.getTileIndexClicked()) - 1, tileToMove);
//                    display.setTileClickedIndex(display.getTileIndexClicked() - 1);
            display.placeTile(tileToMove, "playerOneHand");
        }
    }

    public boolean isEndOfTurn() {
        return endOfTurn;
    }

    public void setEndOfTurn(boolean value) {
        endOfTurn = value;
    }

    public void setPcIndexClicked(int pcIndexClicked) {
        this.pcIndexClicked = pcIndexClicked;
    }

    public boolean getAction() {
        return display.getActionClicked();
    }

    public String getActionPerformed() {
        return actionPerformed;
    }

    public void setActionPerformed(String actionPerformed) {
        this.actionPerformed = actionPerformed;
    }
}
