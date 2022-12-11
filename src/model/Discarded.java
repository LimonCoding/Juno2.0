package model;

import java.util.Stack;

public class Discarded extends Stack<Card> {

    private Stack<Card> discarded;
    
    public Discarded() {
        this.discarded = initDiscard();
    }
    
    private Stack<Card> initDiscard() {
        return new Stack<>();
    }
    
    public Stack<Card> getDiscarded() {
        return discarded;
    }
    
    public void setDiscard(Card discard) {
        push(discard);
    }
    
    public Card getLastDiscard() {
        int index = (size()-1);
        return get(index);
    }
    
}
