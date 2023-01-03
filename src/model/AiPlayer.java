package model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Card.Color;

/**
 * The class AiPlayer extends the class Player 
 * and represents a generic virtual player in a card game.
 * @author Simone
 */
public class AiPlayer extends Player {
	/**
	 * The enumeration Strategy represents 
	 * possible strategy of virtual player
	 * @author Simone
	 */
    public enum Strategy {
    	/** Strategy that plays a card of the same color as the current discard. */
        SAME_COLOR(0),
        /** Strategy that plays a card of the same value as the current discard. */
        SAME_VALUE(1),
        /** Strategy that plays a special card, such as a Skip, Reverse or Draw Two card of the same color as the current discard. */
        USE_SPECIAL(2),
        /** Strategy that plays a Wild card. */
        USE_WILD(3);
    	/**
		 * integer value to identify strategy of virtual player
		 */
        private int strategy;
        /**
		 * Strategy constructor with strategy field as parameter
		 * @param strategy the integer value of strategy enumeration
		 */
        Strategy(int strategy) {
            this.strategy = strategy;
        }
        /**
		 * get the player's strategy as integer value
		 * @return strategy the integer value of strategy enumeration
		 */
        public int getStrategy() {
            return strategy;
        }
        /**
		 * get the player's enumeration strategy based on integer value
		 * @param strategy the integer value of strategy enumeration
		 * @return s the enumeration strategy based on integer value
		 */
        public static Strategy forValue(int strategy) {
            for (Strategy s: values()) {
                if (s.getStrategy() == strategy) return s;
            }
            return null;
        }
    }
    /**
	 * Strategy enumeration value to identify strategy of virtual player
	 */
    private Strategy aiStrategy;
    /**
	 * Card variable to identify rejected card of virtual player
	 */
    private Card reject;
    /**
	 * AiPlayer constructor with aiName and aiStrategy field as parameter
	 * @param aiName the string name of virtual player
	 * @param aiStrategy the Strategy enumeration of virtual player
	 */
    public AiPlayer(String aiName, Strategy aiStrategy) {
        super(aiName);
        this.aiStrategy = aiStrategy;
    }
    /**
     * Auto plays a card in a card game.
     * @param rejected a card that has been the last rejected
     * @return the card played, or null if no card can be played (card must be drawn)
     */
    public Card play(Card rejected) {
        Card selected = autoChooseCard(getHandCards(), rejected);
        if (!(selected == null)) {
            reject = selected;
            this.getHandCards().removeIf(c -> c.equals(selected));
        } else {
            reject = null;
        }
        return reject;
    }
    /**
     * Gets the current strategy for a virtual player.
     * @return the player's current strategy
     */
    public Strategy getAiStrategy() {
        return aiStrategy;
    }
    /**
     * Sets the strategy for a virtual player.
     * @param aiStrategy the new strategy for virtual player
     */
    public void setAiStrategy(Strategy aiStrategy) {
        this.aiStrategy = aiStrategy;
    }
    /**
     * Chooses a card to play based on the player's strategy and the rejected card.
     *
     * @param validCards a list of cards that are valid to play
     * @param rejected the card that was rejected and cannot be played
     * @return the chosen card to play, or null if no card can be played
     */
    public Card autoChooseCard(List<Card> validCards, Card rejected) {
        List<Card> handNoWild = validCards.stream()
                .filter(card -> !(card.getColor().equals(Color.WILD)))
                .collect(Collectors.toList());
        
        Optional<Card> validCardByWild = validCards.stream()
                .filter(card -> card.isWild() || card.isWildFour())
                .findAny();
        Optional<Card> validCardBySpecial = validCards.stream()
                .filter(card -> ( card.getColor().equals(rejected.getColor()) || 
                		card.getValue().equals(rejected.getValue()) )  && card.isSpecial())
                .findAny();
        Optional<Card> validCardByColor = handNoWild.stream()
                .filter(card -> card.getColor().equals(rejected.getColor()))
                .findAny();
        Optional<Card> validCardByValue = handNoWild.stream()
                .filter(card -> card.getValue().equals(rejected.getValue()))
                .findAny();
        
        if(aiStrategy.equals(Strategy.SAME_COLOR)) {
            //ORDERING BASED ON SAME COLOR CARD OF LAST DISCARD
            if (!validCardByColor.isEmpty()) {
                return validCardByColor.get();
            } else if (!validCardByValue.isEmpty()) {
                return validCardByValue.get();
            } else if (!validCardByWild.isEmpty()) {
                return validCardByWild.get();
            }
        }
        if(aiStrategy.equals(Strategy.SAME_VALUE)) {
            //ORDERING BASED ON SAME VALUE CARD OF LAST DISCARD
            if (!validCardByValue.isEmpty()) {
                return validCardByValue.get();
            } else if (!validCardByColor.isEmpty()) {
                return validCardByColor.get();
            } else if (!validCardByWild.isEmpty()) {
                return validCardByWild.get();
            }
            
        }
        if(aiStrategy.equals(Strategy.USE_SPECIAL)) {
            //ORDERING BASED ON SAME COLOR OF LAST DISCARD BUT WITH SPECIAL CARDS VALUE FIRST
            if (!validCardBySpecial.isEmpty()) {
                return validCardBySpecial.get();
            } else if (!validCardByColor.isEmpty()) {
                return validCardByColor.get();
            } else if (!validCardByValue.isEmpty()) {
                return validCardByValue.get();
            } else if (!validCardByWild.isEmpty()) {
                return validCardByWild.get();
            }
        }
        if(aiStrategy.equals(Strategy.USE_WILD)) {
            //ORDERING BASED ON WILD COLOR CARDS
            if (!validCardByWild.isEmpty()) {
                return validCardByWild.get();
                
            } else if (!validCardByColor.isEmpty()) {
                return validCardByColor.get();
                
            } else if (!validCardByValue.isEmpty()) {
                return validCardByValue.get();
                
            } else if (!validCardBySpecial.isEmpty()) {
                return validCardBySpecial.get();
            } 
        }
        return null;
    }
    /**
     * Automatically chooses a color for a wild card based on the player's hand.
     *
     * <p>If the player's hand contains no cards other than wild cards, a random color
     * is chosen. Otherwise, the color with the most representation in the player's
     * hand is chosen.
     *
     * @return the chosen color
     * @see Color
     * @see Card#isWild()
     * @see Card#isWildFour()
     */
    public Card.Color autoChooseColor() {
        List<Card> handNoWild = this.getHandCards().stream()
                .filter(card -> !(card.isWild() || card.isWildFour()))
                .collect(Collectors.toList());
        
        int handColor;
        System.out.println("carte in mano senza wild: "+handNoWild);
        if (handNoWild.isEmpty()) {
            return Color.forValue((int)(Math.random()*(3-0+1)+0));
        } else {
            handColor = handNoWild.stream()
                    .collect(Collectors.groupingBy(         // creating an intermediate Map<Integer, Long>
                            Card::getColor,                 // map's key
                            Collectors.counting()           // value
                        ))
                        .entrySet().stream()                // creating a stream over the map's entries
                        .max(Map.Entry.comparingByValue())  // picking the entry with the highest value -> result: Optional<Map.Entry<Integer, Long>>
                        .map(Map.Entry::getKey)             // transforming the optional result Optional<Integer> 
                        .get().getColor(); 
            System.out.println("hand color:     "+handColor);
        }
        switch (handColor) {
            case 0 : return Card.Color.BLUE;
            case 1 : return Card.Color.GREEN;
            case 2 : return Card.Color.RED;
            case 3 : return Card.Color.YELLOW;
        }
        return Color.forValue((int)(Math.random()*(3-0+1)+0));
    }
}
