package remi.player;

import remi.display.DisplayController;
import remi.tile.PlayerHand;
import remi.tile.TileManager;

import java.util.concurrent.ThreadLocalRandom;

public class ComputerPlayer extends Player {

    PlayerHand hand;
    DisplayController display;

    public ComputerPlayer(DisplayController display, TileManager tm) {
        super(display, tm);
        hand = tm.getPlayerTwoHand();
    }

    /*Simple computer player functionality*/
    public void makeAMove() {

        /*Draw a card*/
        setActionPerformed("tilePoolClicked");
        handleAction( false);
        /*Discard a random card*/
        setPcIndexClicked(ThreadLocalRandom.current().nextInt(0, 13));
        setActionPerformed("playerTwoHandClicked");
        handleAction(false);
        setActionPerformed("playerTwoDiscardClicked");
        handleAction(false);
    }

    public void sortHand() {
        clearHand(hand);
        hand.sort();
        fillHand(hand);
    }
}
