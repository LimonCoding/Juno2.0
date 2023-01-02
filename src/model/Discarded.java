package model;

import java.util.Stack;
/**
 * Discarded is a Stack subclass that represents a pile of discarded cards in a card game.
 * @author Simone
 */
public class Discarded extends Stack<Card> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1417022577116187699L;
	/**
	 * discarded is a Stack object representing a pile of discarded cards in a card game.
	 */
	private Stack<Card> discarded;
	/**
	 * Constructs a new instance of the discard pile for the game.
	 * @see #initDiscard()
	 */
    public Discarded() {
        this.discarded = initDiscard();
    }
    /**
     * Initializes the discard pile for the game.
     *
     * @return a new empty stack of cards to be used as the discard pile
     */
    private Stack<Card> initDiscard() {
        return new Stack<>();
    }
    /**
     * Gets the discard pile for the game.
     *
     * @return the discard pile for the game
     */
    public Stack<Card> getDiscarded() {
        return discarded;
    }
    /**
     * Adds a card to the discard pile.
     *
     * @param discard the card to be added to the discard pile
     */
    public void addDiscard(Card discard) {
        push(discard);
    }
    /**
     * Returns the last card that was added to the discard pile.
     *
     * @return the last card that was added to the discard pile
     * @see #size()
     * @see #get(int)
     * @see Card#isWild()
     */
    public Card getLastDiscard() {
        int index = (size()-1);
        if (this.get(index).isWild()) {
			System.out.println("CARTA WILD APPENA SCARTATAAAAA: "+get(index).toString());
		}
        return get(index);
    }
}
