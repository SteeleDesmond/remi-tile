package remi.tile;

import java.util.Stack;

/**
 * Two instances, one for each player
 */
public class DiscardPile {

    private Stack<Tile> discardPile;

    public DiscardPile() {
        discardPile = new Stack<>();
    }

    public Tile pop() {
        return discardPile.pop();
    }

    public void push(Tile tile) {
        discardPile.push(tile);
    }

    public Tile peek() {
        return discardPile.peek();
    }

    public boolean isEmpty() {
        return discardPile.isEmpty();
    }

    public void clear() {
        discardPile.clear();
    }
}
