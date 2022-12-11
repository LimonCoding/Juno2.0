package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Card.Color;
import model.Card.Value;

public class Player {

    private List<Card> handCards;
    private Account accountInfo;
    
    private static int count = 0;
    private int gameId;
    
    public Player(Account accountInfo, List<Card> handCards) {
        this.accountInfo = accountInfo;
        this.handCards = handCards;
        
        this.gameId = count;
        count++;
    }
    
    public Player(List<Card> handCards) {
        this.handCards = handCards;
        
        this.gameId = count;
        count++;
    }
    
    public Player(Account accountInfo) {
        this.accountInfo = accountInfo;
        
        this.gameId = count;
        count++;
    }
    
    public List<Card> getHandCards() {
        return handCards;
    }

    public void setHandCards(List<Card> handCards) {
        this.handCards = handCards;
    }
    
    public void drawCard(Card handCard) {
        this.handCards.add(handCard);
    }
    
    public void drawCards(List<Card> handCards) {
        for (Card card : handCards) {
            this.handCards.add(card);
        }
    }
    
    //It can be sorted by color, value, if it's a wild card or a special one
    public List<Card> getValidMoves(Card lastDiscard) {
        Value validValue = lastDiscard.getValue();
        Color validColor = lastDiscard.getColor();
        List<Card> validCards = new ArrayList<>();
        for(Card card : this.getHandCards()) {
            if(card.getValue().equals(validValue) || card.getColor().equals(validColor)
                                               || card.getColor().equals(Color.WILD)) {
                validCards.add(card);
            }
        }
        return validCards;
    }
    
    public Card.Color chooseColor() {
        List<Card> handNoWild = this.handCards.stream()
                .filter(card -> !(card.getColor().equals(Color.WILD)))
                .collect(Collectors.toList());
        
        int handColor;
        System.out.println(handNoWild);
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
    
    public void discard(Card card) {
        handCards.remove(card);
    }
    
    public boolean uno() {
        if (this.getHandCards().size() == 1) {
            return true;
        }
        return false;
    }

    public Account getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(Account accountInfo) {
        this.accountInfo = accountInfo;
    }
    
    public int getGameId() {
        return gameId;
    }
    
    @Override
    public String toString() {
        return "GameID: "+this.getGameId()+" "+accountInfo.toString()+" [handCards=" + handCards + "]";
    }

}
