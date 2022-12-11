package model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Card.Color;


public class AiPlayer extends Player {
    
    public enum Strategy {
        SAME_COLOR(0), SAME_VALUE(1), USE_SPECIAL(2), USE_WILD(3);
        
        private int strategy;
        
        Strategy(int strategy) {
            this.strategy = strategy;
        }

        public int getStrategy() {
            return strategy;
        }
        
        public void setStrategy(int strategy) {
            this.strategy = strategy;
        }
        
        public static Strategy forValue(int strategy) {
            for (Strategy s: values()) {
                if (s.getStrategy() == strategy) return s;
            }
            return null;
        }
    }
    
    private Strategy aiStrategy;
    private Card reject;
    
    public AiPlayer(Account accountInfo, Strategy aiStrategy) {
        super(accountInfo);
        this.aiStrategy = aiStrategy;
    }
    
    public Card play(Card rejected) {
        System.out.println(this.getAccountInfo().getAlias()+" Hand: "+this.getHandCards());
        Card selected = chooseCard(getHandCards(), rejected);
        if (!(selected == null)) {
            System.out.println("Playable: "+selected);
            reject = selected;
            this.getHandCards().removeIf(c -> c.equals(selected));
        } else {
            System.out.println("Have to draw a card");
            reject = null;
        }
        return reject;
    }
    
    public Strategy getAiStrategy() {
        return aiStrategy;
    }
    
    public void setAiStrategy(Strategy aiStrategy) {
        this.aiStrategy = aiStrategy;
    }
    
    
    //Based on getValidMoves method, 
    //AI player can choose on cards ordered by strategy specifications
    public Card chooseCard(List<Card> validCards, Card rejected) {
        List<Card> handNoWild = validCards.stream()
                .filter(card -> !(card.getColor().equals(Color.WILD)))
                .collect(Collectors.toList());
        
        Optional<Card> validCardByWild = validCards.stream()
                .filter(card -> card.isWild())
                .findAny();
        Optional<Card> validCardBySpecial = validCards.stream()
                .filter(card -> card.getValue().equals(rejected.getValue()) && rejected.isSpecial())
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
    
}
