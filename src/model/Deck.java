package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.ImageIcon;

import model.Card.Color;
import model.Card.Value;
import model.Game.Flipped;

public class Deck extends Stack<Card> {

	private Stack<Card> deck;
	private final ImageIcon deckFace = new ImageIcon("ImageLibrary/CARTE-UNO/small/RETRO.png");
	private static int numCards = 0;
	int cards = 0;
	
	private static final int numColors = Color.values().length-1;
	
    public Deck() {
		this.deck = initDeck();
		shuffle();
	}
	
	private Stack<Card> initDeck() {
		Stack<Card> deckCards = new Stack<>();
		
		for (int color = 0; color < numColors; color++) {
            
            deckCards.push(new Card(Color.forValue(color), Value.forValue(0)));
            
            for (int oneToNine = 1; oneToNine <= 9; oneToNine++) {
                deckCards.push(new Card(Color.forValue(color), Value.forValue(oneToNine)));
                deckCards.push(new Card(Color.forValue(color), Value.forValue(oneToNine)));
            }
            
            for (int special = 10; special <= 12; special++) {
                deckCards.push(new Card(Color.forValue(color), Value.forValue(special)));
                deckCards.push(new Card(Color.forValue(color), Value.forValue(special)));
            }
        }
        
        for (int wildColor = 13; wildColor <= 14; wildColor++) {
            for (int wildValue = 0; wildValue < 4; wildValue++) {
                deckCards.push(new Card(Color.WILD, Value.forValue(wildColor)));
            }
        }
		return deckCards;
	}
	
	public void shuffle() {
	    Collections.shuffle(deck);
	}
	
	public void replaceDeck(Stack<Card> deck) {
	    Collections.shuffle(deck);
	    this.deck = deck;
	}
	
	public Card getCard(Flipped flipped) {
	    if(deck.isEmpty()) {
            refillDeck(null);
            Card card = deck.pop();
            card.setCovered(flipped);
            return card;
        } else {
            Card card = deck.pop();
            card.setCovered(flipped);
            return card;
        }
	}
	
	public void refillDeck(List<Card> discarded) {
        for(Card card : discarded) {
            deck.add(card);
        }
    }

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
	
	@Override
    public String toString() {
	    String deckString = "";
	    numCards = 0;
	    for (Card card : deck) {
	        deckString += ++numCards + " " + card.toString() + "\n";
	    }
        return "Deck: \n" + deckString + "";
    }

    public ImageIcon getDeckFace() {
        return deckFace;
    }
	
}
