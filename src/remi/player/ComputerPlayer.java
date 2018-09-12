package remi.player;

import java.util.concurrent.ThreadLocalRandom;

public class ComputerPlayer extends Player {

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
}
