package model;

import java.util.*;

import model.Card.Color;
import model.Card.Value;
/**
 * Represents a player in the game.
 *
 * <p>A player has a hand of cards, an account with information about the player,
 * an alias, and a unique game id. The player can also be in a state of "uno safe"
 * where they are protected from being called out for not calling "uno" when they
 * have only one card left in their hand.
 *
 * @see Account
 * @see Card
 */
public class Player {
	/**
	 * The player's hand of cards.
	 *
	 * <p>The hand of cards is a list of cards that the player currently holds and can
	 * play during the game.
	 *
	 * @see Card
	 */
    private List<Card> handCards;
    /**
     * The player's account information.
     *
     * <p>The account information includes details about the player such as their username,
     * email, and any other relevant information.
     *
     * @see Account
     */
    private Account accountInfo;
    /**
     * The player's alias.
     *
     * <p>The alias is a nickname that the player goes by. It can be used
     * to identify the player in the game.
     */
    private String alias;
    /**
     * A flag indicating whether the player is in a state of "uno safe".
     *
     * <p>When a player is "uno safe", they are protected from being called out for not
     * calling "uno" when they have only one card left in their hand. The default value
     * is `false`.
     */
    private boolean unoSafe = false;
    /**
     * A static counter for generating unique game ids for players.
     *
     * <p>The game id is a unique identifier for each player in the game. It is used
     * to differentiate players from one another. The counter is used to generate
     * sequential game ids for players.
     */
    private static int count = 0;
    /**
     * The player's unique game id.
     *
     * <p>The game id is a unique identifier for the player in the game. It is used
     * to differentiate the player from other players. The game id is generated using
     * the static `count` variable.
     */
    private int gameId;
    /**
     * Constructs a new player with the given account information and hand of cards.
     *
     * @param accountInfo the player's account information
     * @param handCards the player's hand of cards
     * @see Account
     * @see Card
     */
    public Player(Account accountInfo, List<Card> handCards) {
        this.accountInfo = accountInfo;
        this.alias = accountInfo.getAlias();
        this.handCards = handCards;
        
        this.gameId = count;
        count++;
    }
    /**
     * Constructs a new player with the given hand of cards.
     *
     * @param handCards the player's hand of cards
     * @see Card
     */
    public Player(List<Card> handCards) {
        this.handCards = handCards;
        
        this.gameId = count;
        count++;
    }
    /**
     * Constructs a new player with the given account information.
     *
     * @param accountInfo the player's account information
     * @see Account
     */
    public Player(Account accountInfo) {
        this.accountInfo = accountInfo;
        this.alias = accountInfo.getAlias();
        
        this.gameId = count;
        count++;
    }
    /**
     * Constructs a new player with the given alias.
     *
     * @param alias the player's nickname
     */
    public Player(String alias) {
        this.alias = alias;
        
        this.gameId = count;
        count++;
    }
    /**
     * Resets the player id counter to 0.
     *
     * <p>This method is used to reset the player id counter to 0. This is useful
     * if the game needs to be reset or if the counter needs to be reset for any
     * other reason.
     */
    public static void clearIds() {
    	count = 0;
    }
    /**
     * Gets the player's hand of cards.
     *
     * @return the player's hand of cards
     * @see Card
     */
    public List<Card> getHandCards() {
        return handCards;
    }
    /**
     * Sets the player's hand of cards.
     *
     * @param handCards the new hand of cards for the player
     * @see Card
     */
    public void setHandCards(List<Card> handCards) {
        this.handCards = handCards;
    }
    /**
     * Adds a card to the player's hand.
     *
     * @param handCard the card to add to the player's hand
     * @see Card
     */
    public void drawCard(Card handCard) {
        this.handCards.add(handCard);
    }
    /**
     * Adds a list of cards to the player's hand.
     *
     * @param handCards the list of cards to add to the player's hand
     * @see Card
     */
    public void drawCards(List<Card> handCards) {
        for (Card card : handCards) {
            this.handCards.add(card);
        }
    }
    /**
     * Returns a list of valid moves for the player.
     *
     * <p>A move is valid if the card played has the same value as the last discarded
     * card, or the same color, or it is a wild card.
     *
     * @param lastDiscard the last card discarded by another player
     * @return a list of valid cards the player can play
     * @see Card
     */
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
    /**
     * Removes a card from the player's hand.
     *
     * @param card the card to remove from the player's hand
     * @see Card
     */
    public void discard(Card card) {
        handCards.remove(card);
    }
    /**
     * Gets true if the player has only one card in their hand, false otherwise.
     *
     * @return true if the player has only one card in their hand, false otherwise
     */
    public boolean getUno() {
        if (this.getHandCards().size() == 1) {
            return true;
        }
        return false;
    }
    /**
     * Sets the uno safe flag for the player.
     *
     * <p>The uno safe flag indicates whether the player has called "UNO" and therefore
     * is protected from being called out for failing to do so.
     *
     * @param unoSafe the value to set the uno safe flag to
     */
    public void setUnoSafe(boolean unoSafe) {
    	this.unoSafe = unoSafe;
    }
    /**
     * Gets the value of the uno safe flag for the player.
     *
     * @return the value of the uno safe flag for the player
     */
    public boolean getUnoSafe() {
    	return unoSafe;
    }
    /**
     * Gets the account information of the player.
     *
     * @return the account information of the player
     * @see Account
     */
    public Account getAccountInfo() {
        return accountInfo;
    }
    /**
     * Sets the account information of the player.
     *
     * @param accountInfo the account information to set for the player
     * @see Account
     */
    public void setAccountInfo(Account accountInfo) {
        this.accountInfo = accountInfo;
    }
    /**
     * Gets the game ID of the player.
     *
     * @return the game ID of the player
     */
    public int getGameId() {
        return gameId;
    }
    /**
     * Gets the alias of the player.
     *
     * @return the alias of the player
     */
	public String getAlias() {
		return alias;
	}
	/**
	 * Gets a string representation of the player.
	 *
	 * <p>The string representation includes the game ID, alias, and hand cards of the player.
	 *
	 * @return a string representation of the player
	 */
	@Override
    public String toString() {
        return "GameID: "+this.getGameId()+" "+this.getAlias()+" [handCards=" + handCards + "]";
    }
}
