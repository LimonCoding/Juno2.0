package model;

import java.util.Stack;

public class Discarded extends Stack<Card> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1417022577116187699L;
	
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
        if (this.get(index).isWild()) {
			System.out.println("CARTA WILD APPENA SCARTATAAAAA: "+get(index).toString());
		}
        return get(index);
    }
    
}
