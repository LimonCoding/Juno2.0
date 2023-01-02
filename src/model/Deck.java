package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.ImageIcon;

import model.Card.Color;
import model.Card.Value;
import model.Game.Flipped;

/**
 * Represents a deck of playing cards.
 *
 * This class extends the `Stack` class and uses the `Card` class as its type parameter.
 * It therefore has all the methods and characteristics of a `Stack`, but is specifically
 * intended to hold and manage a collection of `Card` objects.
 * @author Simone
 */
public class Deck extends Stack<Card> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 544603031132300649L;
	/**
	 * A stack of cards representing a deck.
	 */
	private Stack<Card> deck;
	/**
	 * An image icon representing the face of the deck.
	 */
	private final ImageIcon deckFace = new ImageIcon(getClass().getResource("/cards/RETRO.png"));
	/**
	 * The number of cards in the deck.
	 */
	private static int numCards = 0;
	/**
	 * The number of colors in the deck.
	 */
	private static final int numColors = Color.values().length-1;
	/**
	 * Constructs a new deck of cards.
	 *
	 * This constructor initializes the `deck` field with a new deck of cards and shuffles the cards.
	 */
    public Deck() {
		this.deck = initDeck();
		Collections.shuffle(deck);
	}
    /**
     * Initializes a new deck of cards.
     *
     * @return a stack of cards representing the new deck
     */
	private Stack<Card> initDeck() {
		Stack<Card> deckCards = new Stack<>();
		// Create standard cards with all colors and values.
		for (int color = 0; color < numColors; color++) {
	        // Add zero value card.
	        deckCards.push(new Card(Color.forValue(color), Value.forValue(0)));
	        // Add cards with values 1 to 9.
	        for (int oneToNine = 1; oneToNine <= 9; oneToNine++) {
	            deckCards.push(new Card(Color.forValue(color), Value.forValue(oneToNine)));
	            deckCards.push(new Card(Color.forValue(color), Value.forValue(oneToNine)));
	        }
	        // Add special cards.
	        for (int special = 10; special <= 12; special++) {
	            deckCards.push(new Card(Color.forValue(color), Value.forValue(special)));
	            deckCards.push(new Card(Color.forValue(color), Value.forValue(special)));
	        }
	    }
	    // Create wild cards with all values.
	    for (int wildColor = 13; wildColor <= 14; wildColor++) {
	        for (int wildValue = 0; wildValue < 4; wildValue++) {
	            deckCards.push(new Card(Color.WILD, Value.forValue(wildColor)));
	        }
	    }
		return deckCards;
	}
	/**
	 * Replaces the current deck of cards with a new deck.
	 * The new deck is created by shuffling the discarded cards.
	 *
	 * @param discardedDeck a stack of cards that have been discarded and will be used to create the new deck
	 */
	public void replaceDeck(Stack<Card> discardedDeck) {
	    Collections.shuffle(discardedDeck);
	    this.deck = discardedDeck;
	}
	/**
	 * Gets a card from the deck.
	 *
	 * If the deck is empty, the deck is refilled with the discarded cards.
	 *
	 * @param flipped an object representing the flipped cards
	 * @param discard an object representing the discarded cards
	 * @return a card from the deck
	 */
	public Card getCard(Flipped flipped, Discarded discard) {
	    if(deck.isEmpty()) {
            refillDeck(discard);
            Card card = deck.pop();
            card.setCovered(flipped);
            return card;
        } else {
            Card card = deck.pop();
            card.setCovered(flipped);
            return card;
        }
	}
	/**
	 * Refills the deck with the discarded cards.
	 *
	 * @param discarded a list of cards that have been discarded and will be added to the deck
	 */
	public void refillDeck(List<Card> discarded) {
        for(Card card : discarded) {
            deck.add(card);
        }
    }
	/**
	 * Gets a specified number of cards from the deck.
	 *
	 * @param nCards the number of cards to get from the deck
	 * @param covered a Flipped enumeration indicating whether the cards should be covered or not
	 * @return a list of cards that were drawn from the deck
	 */
    public ArrayList<Card> getCards(int nCards, Flipped covered) {
	    ArrayList<Card> cardsDrawed = new ArrayList<>(nCards);
        for (int i = 0; i < nCards; i++) {
            Card card = deck.pop();
            card.setCovered(covered);
            cardsDrawed.add(card);
        }
        for (Card c : cardsDrawed) {
            c.setCovered(covered);
        }
        return cardsDrawed;
    }
    /**
     * Returns a string representation of the Deck object.
     * @return a string representation of the Deck object
     */
	@Override
    public String toString() {
	    String deckString = "";
	    numCards = 0;
	    for (Card card : deck) {
	        deckString += ++numCards + " " + card.toString() + "\n";
	    }
        return "Deck: \n" + deckString + "";
    }
	/**
	 * Gets the ImageIcon object representing the face of the deck.
	 * @return the ImageIcon object representing the face of the deck
	 */
    public ImageIcon getDeckFace() {
        return deckFace;
    }
}
