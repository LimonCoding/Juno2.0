package model;

import java.util.*;
import java.util.stream.Collectors;

import javax.swing.Timer;

import model.AiPlayer.Strategy;
import model.Card.Value;
import model.Card.Flipped;
/**
 * The Game class represents a game in the UNO game.
 *
 * <p>A Game object is an observable object. Its state changes when the game state changes, and registered
 * observers are notified of these changes.
 *
 * @see Observable
 * @see Observer
 */
@SuppressWarnings("deprecation")
public class Game extends Observable {
	/**
	 * The GameDirection enumeration represents the direction of play in the UNO game.
	 *
	 * <p>There are two possible values: CLOCKWISE and COUNTER_CLOCKWISE.
	 */
    public enum GameDirection {
    	/**
         * Represents the direction of play in the UNO game.
         */
        CLOCKWISE(true),
        /**
         * Represents the direction of play in the UNO game.
         */
        COUNTER_CLOCKWISE(false);
        /**
         * Boolean value to identify the game direction
         * 
         * <p>When true indicate that the game direction is clockwise, 
         * counter clockwise if false.
         */
        private boolean gameDirection;
        /**
         * Constructs a GameDirection enumeration with the given game direction.
         *
         * @param gameDirection the game direction
         */
        GameDirection(boolean gameDirection) {
            this.gameDirection = gameDirection;
        }
        /**
         * Gets the game direction.
         *
         * @return the game direction
         */
        public boolean getGameDirection() {
            return gameDirection;
        }
        /**
         * Returns the GameDirection enumeration with the given boolean value of game direction.
         *
         * @param gameDirection the boolean value of game direction
         * @return the GameDirection enumeration with the given game direction
         */
        public static GameDirection forValue(boolean gameDirection) {
            for (GameDirection g: values()) {
                if (g.getGameDirection() == gameDirection) return g;
            }
            return CLOCKWISE;
        }
    }
    /**
     * The ID of the current player.
     */
    private int currentPlayerId;
    /**
     * The ID of the last player. This value is final and cannot be changed.
     */
    private final int lastPlayerId;
    /**
     * The deck of cards for this game.
     */
    private Deck deck;
    /**
     * The list of discarded cards.
     */
    private Discarded discardPile;
    /**
     * The list of players in this game.
     */
    private List<Player> playersList;
    /**
     * The list of players in this game, sorted in the order they will play.
     */
    private List<Player> sortedPlayerList;
    /**
     * The list of AI players in this game.
     */
    private List<AiPlayer> aiPlayersList;
    /**
     * The direction of play for this game.
     */
    private GameDirection gameDirection;
    /**
     * A flag indicating whether or not the "UNO safe" rule is in effect for this game.
     */
    private boolean unoSafe;
    /**
     * A flag indicating whether or not the game is paused.
     */
    private boolean paused = false;
    /**
     * A constant representing a "flipped" state.
     */
    private static Flipped FLIPPED = Flipped.FLIPPED;
    /**
     * A constant representing a "not flipped" state.
     */
	private static Flipped NOT_FLIPPED = Flipped.NOT_FLIPPED;
	/**
	 * The file path where game saves are stored.
	 */
	public static String savesPath = "saves";
	/**
	 * Gets the paused state of the game.
	 * 
	 * @return the paused state of the game
	 */
    public boolean getPaused() {
    	return paused;
    }
    /**
     * Sets the paused state of the game.
     * Notify all observers if your status has changed.
     * 
     * @param paused the new paused state of the game
     */
    public void setPaused(boolean paused) {
    	this.paused = paused;
    	setChanged();
    	notifyObservers();
    }
    /**
     * The AI player at the top of the game board.
     */
    private AiPlayer topPlayer;
    /**
     * The AI player to the right of the game board.
     */
    private AiPlayer rightPlayer;
    /**
     * The AI player to the left of the game board.
     */
    private AiPlayer leftPlayer;
    /**
     * The human player at the bottom of the game board.
     */
    private Player bottomPlayer;
    /**
     * The account associated with the human player.
     */
    private Account myAccount;
    /**
     * The number of seconds that the AI players will take to make a move.
     */
    private final static int SEC_AI_PLAY = 4000;
    /**
     * Constructs a new game with the given account.
     * 
     * @param account the account of the human player
     */
    public Game(Account account) {
    	myAccount = account;
        bottomPlayer = new Player(account);
        rightPlayer = new AiPlayer("Right Player", Strategy.SAME_VALUE);
        topPlayer = new AiPlayer("Top Player", Strategy.SAME_COLOR);
        leftPlayer = new AiPlayer("Left Player", Strategy.USE_SPECIAL);
        
        deck = new Deck();
        discardPile = new Discarded();
        
        playersList = new ArrayList<>(Arrays.asList(bottomPlayer, topPlayer, rightPlayer, leftPlayer));
        aiPlayersList = new ArrayList<>(Arrays.asList(topPlayer, rightPlayer, leftPlayer));
        sortedPlayerList = playersList.stream()
                                      .sorted(Comparator.comparing(Player::getGameId))
                                          .collect(Collectors.toList());
        currentPlayerId = 0;
        dealCards(sortedPlayerList);
        lastPlayerId = playersList.size()-1;
        startGame(this);
        
        System.out.println();
        for (AiPlayer a : aiPlayersList) {
            a.autoChooseCard(a.getValidMoves(discardPile.getLastDiscard()), discardPile.getLastDiscard());
        }
    }
    /**
     * Clears the state of the game, including the list of players and the player IDs.
     */
    public void clearGame() {
        playersList.clear();
        aiPlayersList.clear();
        sortedPlayerList.clear();
        Player.clearIds();
    }
    /**
     * Deals cards to the players in the given list.
     * 
     * @param playersList the list of players to deal cards to
     */
    private void dealCards(List<Player> playersList) {
        for (AiPlayer p : aiPlayersList) {
            p.setHandCards(new ArrayList<>(deck.getCards(7, NOT_FLIPPED)));
        }
        bottomPlayer.setHandCards(new ArrayList<>(deck.getCards(7, FLIPPED)));
    }
    /**
     * Start the game by drawing the initial card
     * that is set as before in the discard pile 
     * and set the game direction.
     * 
     * @param game the game to start, if the initial card is a special or wild one have to redraw a card
     */
    private void startGame(Game game) {
        Card card = deck.getCard(FLIPPED, discardPile);
        if (card.isWild() || card.isSpecial() || card.isWildFour()) {
            startGame(game);
        } else {
            discardPile.addDiscard(card);
        }
        gameDirection = Game.GameDirection.CLOCKWISE;
    }
    /**
     * Allows an AI player to play a card as a discard and performs any necessary actions as a result.
     * 
     * <p>A timer is used to delay the AI player's turn by the specified number of seconds. If the game is not paused, and the
     * previous player has not won the game, the AI player chooses a card to play as a discard or draw a card from the 
     * deck. If the AI player chooses to play a card, any necessary actions are performed. These actions include skipping
     * the next player's turn if the card is a "SKIP" card, reversing the direction of play if the card is a "REVERSE" card,
     * causing the next player to draw two cards if the card is a "DRAW_TWO" card, and causing the next player to draw 
     * four cards if the card is a wild draw four card. If the card played is a wild card, the AI player automatically
     * chooses a valid color for the wild card. If the bottom player has not called "UNO" before playing their last card,
     * they are required to draw two cards from the deck as a penalty.
     * 
     * <p>If the game is paused, a message is printed to the console indicating that the game is paused and the method 
     * returns true.
     * 
     * @param rejected the card that the AI player must play as a discard or draw
     * @return true if the game is paused, false otherwise
     */
    public boolean aiPlay(Card rejected) {
        AiPlayer p = (AiPlayer) sortedPlayerList.get(currentPlayerId);
        Timer aiPlay = new Timer(SEC_AI_PLAY, (ae)->{
            Card drawOrThrows;
            drawOrThrows = p.play(rejected);
            if (!(drawOrThrows == null)) {
                if (drawOrThrows.getValue().equals(Value.SKIP)) {
                    skipTurn();
                }
                if (drawOrThrows.getValue().equals(Value.REVERSE)) {
                    reverseTurn();
                }
                if (drawOrThrows.getValue().equals(Value.DRAW_TWO)) {
                    Player drawTwo = getNextPlayer();
                    if (drawTwo.equals(bottomPlayer)) {
                    	drawTwo.drawCards(deck.getCards(2, FLIPPED));
					} else {
						drawTwo.drawCards(deck.getCards(2, NOT_FLIPPED));
					}
                }
                if (drawOrThrows.isWild() || drawOrThrows.isWildFour()) {
                	drawOrThrows.setColor(p.autoChooseColor());
                    System.out.println("Valid color of WILD: "+drawOrThrows.getColor());
                }
                if (drawOrThrows.isWildFour()) {
                    Player drawFour = getNextPlayer();
                    if (drawFour.equals(bottomPlayer)) {
                    	drawFour.drawCards(getDeck().getCards(4, FLIPPED));
					} else {
						drawFour.drawCards(getDeck().getCards(4, NOT_FLIPPED));
					}
                }
                drawOrThrows.setCovered(FLIPPED);
                discardPile.addDiscard(drawOrThrows);
                nextTurn();
            } else {
                p.drawCard(deck.getCard(NOT_FLIPPED, discardPile));
                nextTurn();
            }
        });
        
        
        
        if (!paused) {
        	if (!winGame(sortedPlayerList.get(previousId()))) {
                aiPlay.setRepeats(false);
                aiPlay.start(); 
            } else {
                return true;
            }
		} else {
			System.out.println("GIOCO IN PAUSAAAA");
		}
        return false;        
    }
    /**
     * Plays a card as a discard and performs any necessary actions as a result.
     * 
     * <p>If the game is not paused, and the previous player has not won the game, the card is played as a discard and any
     * necessary actions are performed. These actions include skipping the next player's turn if the card is a "SKIP" card,
     * reversing the direction of play if the card is a "REVERSE" card, causing the next player to draw two cards if the
     * card is a "DRAW_TWO" card, and causing the next player to draw four cards if the card is a wild draw four card.
     * <p>If the bottom player has not called "UNO" before playing their last card, they are required to draw two cards from
     * the deck as a penalty.
     * 
     * <p>If the game is paused, a message is printed to the console indicating that the game is paused and the method returns
     * true.
     * 
     * @param rejected the card to be played as a discard
     * @return true if the game is paused, false otherwise
     */
    public boolean play(Card rejected) {
    	if (!paused) {
        	if (!winGame(sortedPlayerList.get(previousId()))) {
        		if (rejected.getValue().equals(Value.SKIP)) {
        			skipTurn();
                }
                if (rejected.getValue().equals(Value.REVERSE)) {
                    reverseTurn();
                }
                if (rejected.getValue().equals(Value.DRAW_TWO)) {
                    Player drawTwo = getNextPlayer();
                    drawTwo.drawCards(getDeck().getCards(2, NOT_FLIPPED));
                }
                if (rejected.isWildFour()) {
                    Player drawFour = getNextPlayer();
                    drawFour.drawCards(getDeck().getCards(4, NOT_FLIPPED));
                }
                
                playersList.get(0).discard(rejected);
                
                Timer unoCheck = new Timer(SEC_AI_PLAY+2000, (ae)->{
                    if ( checkUno() && !(bottomPlayer.getUnoSafe()) ) {
                    	bottomPlayer.drawCards(getDeck().getCards(2, FLIPPED));
        			} 
                });
                
                unoCheck.setRepeats(false);
                unoCheck.start(); 
            }
		} else {
			System.out.println("GIOCO IN PAUSAAAA");
			return true;
		}
    	
    	return false; 
    }
    /**
     * Determines if the specified card can be legally played as a discard.
     * 
     * <p>A card can be legally played as a discard if it is the same color as the last card played, the same value as the
     * last card played, or is a wild or wild draw four card. However, if the bottom player has only one card in their
     * hand and the card being played is a wild or wild draw four card, it is not legal to play the card as a discard.
     * 
     * @param card the card to be checked for legal play as a discard
     * @return true if the card can be legally played as a discard, false otherwise
     */
    public boolean legitDiscard(Card card) {
        Card lastDiscard = getDiscard().getLastDiscard();
        return (lastDiscard.isSameColor(card) ||
                lastDiscard.isSameValue(card) ||
                card.isWild() || card.isWildFour()) 
        		&& !(bottomPlayer.getHandCards().size()==1 && (card.isWild() || card.isWildFour()) );
    }
    /**
     * Determines if the bottom player has called "UNO" before playing their last card.
     * 
     * <p>If the bottom player has called "UNO", the unoSafe field is set to true and the method returns true. Otherwise, the
     * unoSafe field is set to false and the method returns false.
     * 
     * @return true if the bottom player has called "UNO", false otherwise
     */
    public boolean checkUno() {
    	if ( getBottomPlayer().getUno() ) {
    		unoSafe = true;
			return unoSafe;
		} else {
			unoSafe = false;
			return unoSafe;
		}
    }
    /**
     * Determines if the specified player has won the game.
     * 
     * @param winner the player to check for winning the game
     * @return true if the player has won the game, false otherwise
     */
    public boolean winGame(Player winner) {
    	int winnerGameId = winner.getGameId();
        return sortedPlayerList.get(winnerGameId).getHandCards().isEmpty();
    }
    /**
     * Updates the account's statistics to reflect that the player has won a game.
     * 
     * <p>If the bottom player's hand is empty, the player's number of games won is incremented. The player's number of games 
     * played is also incremented.
     */
    public void iWon() {
    	if (bottomPlayer.getHandCards().isEmpty()) {
    		myAccount.addGamesWon();
		}
    	myAccount.addGamesPlayed();
    }
    /**
     * Advances the game to the next player's turn.
     * 
     * <p>If the game direction is not set to true, the current player's ID is updated to the next player's ID by adding 1 to 
     * the current player's ID. If the result is equal to the size of the list of players, then the current player's ID is 
     * set to 0 instead, to indicate that the next player is the first player in the list.
     * 
     * <p>If the game direction is set to true, the current player's ID is updated to the next player's ID by subtracting 1 
     * from the current player's ID. If the result is -1, then the current player's ID is set to the ID of the last player 
     * in the list instead, to indicate that the next player is the last player in the list.
     * 
     * <p>This method also notifies any registered observers of the update to the current player's ID.
     */
    public void nextTurn() {
        if ( !(gameDirection.getGameDirection()) ) {
            int next = currentPlayerId + 1;
            currentPlayerId = (next == playersList.size()) ? 0 : next;
        } else {
            int next = currentPlayerId - 1;
            currentPlayerId = (next == -1) ? lastPlayerId : next;
        }
        setChanged();
        notifyObservers(currentPlayerId);
    }
    /**
     * Skips the current player's turn.
     * 
     * <p>If the game direction is not set to true, the current player's ID is updated to the next player's ID by adding 1 to 
     * the current player's ID. If the result is equal to the size of the list of players, then the current player's ID is 
     * set to 0 instead, to indicate that the next player is the first player in the list.
     * 
     * <p>If the game direction is set to true, the current player's ID is updated to the next player's ID by subtracting 1 
     * from the current player's ID. If the result is -1, then the current player's ID is set to the ID of the last player 
     * in the list instead, to indicate that the next player is the last player in the list.
     */
    public void skipTurn() {
    	if ( !(gameDirection.getGameDirection()) ) {
            int next = currentPlayerId + 1;
            currentPlayerId = (next == playersList.size()) ? 0 : next;
        } else {
            int next = currentPlayerId - 1;
            currentPlayerId = (next == -1) ? lastPlayerId : next;
        }
    }
    /**
     * Reverses the direction of play in the game.
     * 
     * If the game direction is currently set to true, it is changed to false. If the game direction is currently set to 
     * false, it is changed to true.
     */
    public void reverseTurn() {
        gameDirection = GameDirection.forValue(!gameDirection.getGameDirection());
    }
    /**
     * Returns the ID of the previous player in the game.
     * 
     * <p>If the game direction is not set to true, the previous player's ID is calculated by subtracting 1 from the current 
     * player's ID. If the result is less than 0, then the ID of the last player in the list is returned instead, to 
     * indicate that the previous player is the last player in the list. If the last card played was a "SKIP" card, then 
     * the previous player's ID is calculated by subtracting 2 from the current player's ID.
     * 
     * <p>If the game direction is set to true, the previous player's ID is calculated by adding 1 to the current player's ID. 
     * If the result is greater than the size of the list of players, then 0 is returned instead, to indicate that the 
     * previous player is the first player in the list. If the last card played was a "SKIP" card, then the previous 
     * player's ID is calculated by adding 2 to the current player's ID.
     * 
     * @return the ID of the previous player in the game
     */
    public int previousId() {
    	if ( !(gameDirection.getGameDirection()) ) {
    		int previous = currentPlayerId - 1;
    		if (discardPile.getLastDiscard().getValue().equals(Value.SKIP)) {
				previous = previous - 1;  
			}
            return (previous < 0) ? lastPlayerId : previous;
        } else {
            int previous = currentPlayerId + 1;
            if (discardPile.getLastDiscard().getValue().equals(Value.SKIP)) {
				previous = previous + 1;  
			}
            return (previous > playersList.size()-1) ? 0 : previous;
        }
    }
    /**
     * Returns the ID of the next player in the game.
     * 
     * <p>If the game direction is not set to true, the next player's ID is calculated by adding 1 to the current player's ID.
     * If the result is equal to the size of the list of players, then 0 is returned instead, to indicate that the next 
     * player is the first player in the list.
     * 
     * <p>If the game direction is set to true, the next player's ID is calculated by subtracting 1 from the current player's 
     * ID. If the result is -1, then the ID of the last player in the list is returned instead, to indicate that the next 
     * player is the last player in the list.
     * 
     * @return the ID of the next player in the game
     */
    public int nextId() {
        if ( !(gameDirection.getGameDirection()) ) {
            int next = currentPlayerId + 1;
            return (next == playersList.size()) ? 0 : next;
        } else {
            int next = currentPlayerId - 1;
            return (next == -1) ? lastPlayerId : next;
        }
    }
    /**
     * Gets the current player.
     * 
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return sortedPlayerList.get(currentPlayerId);
    }
    /**
     * Gets the previous player.
     * 
     * @return the previous player
     */
    public Player getPreviousPlayer() {
        return sortedPlayerList.get(previousId());
    }
    /**
     * Gets the next player.
     * 
     * @return the next player
     */
    public Player getNextPlayer() {
        return sortedPlayerList.get(nextId());
    }
    /**
     * Gets the sorted list of players in the game.
     * 
     * @return the list of players in the game
     */
    public List<Player> getPlayers() {
        return sortedPlayerList;
    }
    /**
     * Gets the direction of play for this game.
     * 
     * @return the direction of play for this game
     */
    public boolean getGameDirection() {
    	return gameDirection.getGameDirection();
    }
    /**
     * Gets the deck of cards for this game.
     * 
     * @return the deck of cards for this game
     */
    public Deck getDeck() {
        return deck;
    }
    /**
     * Gets the number of seconds that the AI players will take to make a move.
     * 
     * @return the number of seconds that the AI players will take to make a move
     */
    public static int getSecAiPlay() {
        return SEC_AI_PLAY;
    }
    /**
     * Gets the AI player at the top of the game board.
     * 
     * @return the AI player at the top of the game board
     */
    public AiPlayer getTopPlayer() {
        return topPlayer;
    }
    /**
     * Gets the AI player to the right of the game board.
     * 
     * @return the AI player to the right of the game board
     */
    public AiPlayer getRightPlayer() {
        return rightPlayer;
    }
    /**
     * Gets the AI player to the left of the game board.
     * 
     * @return the AI player to the left of the game board
     */
    public AiPlayer getLeftPlayer() {
        return leftPlayer;
    }
    /**
     * Gets the human player at the bottom of the game board.
     * 
     * @return the human player at the bottom of the game board
     */
    public Player getBottomPlayer() {
        return bottomPlayer;
    }
    /**
     * Gets the discarded cards pile.
     * 
     * @return the discarded cards pile
     */
    public Discarded getDiscard() {
        return discardPile;
    }
}
