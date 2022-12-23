package model;

import java.util.*;
import java.util.stream.Collectors;

import javax.swing.Timer;

import model.AiPlayer.Strategy;
import model.Card.Value;

public class Game extends Observable {
    
    public enum GameDirection {
        CLOCKWISE(true), COUNTER_CLOCKWISE(false);
        
        private boolean gameDirection;
        
        GameDirection(boolean gameDirection) {
            this.gameDirection = gameDirection;
        }

        public boolean getGameDirection() {
            return gameDirection;
        }
        
        public static GameDirection forValue(boolean gameDirection) {
            for (GameDirection g: values()) {
                if (g.getGameDirection() == gameDirection) return g;
            }
            return CLOCKWISE;
        }
    }
    
    public enum Flipped {
        NOT_FLIPPED(true), FLIPPED(false);
        
        private boolean flipped;
        
        Flipped(boolean flipped) {
            this.flipped = flipped;
        }

        public boolean getFlipped() {
            return flipped;
        }
        
        public static Flipped forValue(boolean flipped) {
            for (Flipped f: values()) {
                if (f.getFlipped() == flipped) return f;
            }
            return NOT_FLIPPED;
        }
    }
    
    private int currentPlayerId;
    private final int lastPlayerId;
    private Deck deck;
    private Discarded discardList;
    private List<Player> playersList;
    private List<Player> sortedPlayerList;
    private List<AiPlayer> aiPlayersList;
    private GameDirection gameDirection;
    private boolean paused = false;
    
    private static Flipped FLIPPED = Flipped.FLIPPED;
	private static Flipped NOT_FLIPPED = Flipped.NOT_FLIPPED;
    
    public boolean getPaused() {
    	return paused;
    }
    
    public void setPaused(boolean paused) {
    	this.paused = paused;
    }
    
    private AiPlayer topPlayer;
    private AiPlayer rightPlayer;
    private AiPlayer leftPlayer;
    private Player bottomPlayer;
    
    private final static int SEC_AI_PLAY = 4000;
    
    public Game(Account player) {
        bottomPlayer = new Player(player);
        rightPlayer = new AiPlayer("Right Player", Strategy.SAME_VALUE);
        topPlayer = new AiPlayer("Top Player", Strategy.SAME_COLOR);
        leftPlayer = new AiPlayer("Left Player", Strategy.USE_SPECIAL);
        
        deck = new Deck();
        discardList = new Discarded();
        
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
            a.chooseCard(a.getValidMoves(discardList.getLastDiscard()), discardList.getLastDiscard());
        }
    }
    
    private void dealCards(List<Player> playersList) {
        for (AiPlayer p : aiPlayersList) {
            p.setHandCards(new ArrayList<>(deck.getCards(1, FLIPPED)));
        }
        bottomPlayer.setHandCards(new ArrayList<>(deck.getCards(7, FLIPPED)));
        
        setChanged();
    	notifyObservers();
    }
    
    public void clearGame() {
        playersList.clear();
        aiPlayersList.clear();
        sortedPlayerList.clear();
        Player.clearIds();
    }
    
    private void startGame(Game game) {
        Card card = deck.getCard(FLIPPED);
        System.out.println(card);
        
        if (card.isWild() || card.isSpecial() || card.isWildFour()) {
            System.out.println("DISCARD NOT LEGIT");
            startGame(game);
        } else {
            System.out.println("Discard Setted: "+card);
            discardList.setDiscard(card);
        }
        System.out.println("Discarder from model game: "+discardList);
        gameDirection = Game.GameDirection.CLOCKWISE;
        
        setChanged();
    	notifyObservers();
    }
    
    public void refillDeck() {
        if (deck.isEmpty()) {
            deck.replaceDeck(discardList);
        }
    }
    
    public boolean validCardPlay(Card card) {
        if ( (card.getColor().equals(discardList.getLastDiscard().getColor()) ||
             card.getValue().equals(discardList.getLastDiscard().getValue()) ||
              card.isWild() || card.isWildFour() ) && (!winGame(sortedPlayerList.get(previousId())) ) ) {
            discardList.setDiscard(card);
            setChanged();
        	notifyObservers();
            return true;
        }
        return false;
    }
    
    public boolean aiPlay(Card rejected) {
        AiPlayer p = (AiPlayer) sortedPlayerList.get(currentPlayerId);
        System.out.println("------------------------------------");
        Timer aiPlay = new Timer(SEC_AI_PLAY, (ae)->{
            Card drawOrThrows;
            drawOrThrows = p.play(rejected);
            if (!(drawOrThrows == null)) {
                if (drawOrThrows.getValue().equals(Value.SKIP)) {
                    nextTurn();
                }
                if (drawOrThrows.getValue().equals(Value.REVERSE)) {
                    reverseTurn();
                }
                if (drawOrThrows.getValue().equals(Value.DRAW_TWO)) {
                    Player drawTwo = getNextPlayer();
                    drawTwo.drawCards(deck.getCards(2, FLIPPED));
                }
                if (drawOrThrows.isWild() || drawOrThrows.isWildFour()) {
                	drawOrThrows.setColor(p.chooseColor());
                    System.out.println("Valid color of WILD: "+drawOrThrows.getColor());
                }
                if (drawOrThrows.isWildFour()) {
                    Player drawFour = getNextPlayer();
                    drawFour.drawCards(getDeck().getCards(4, FLIPPED));
                }
                System.out.println(p.getAlias()+" Hand: "+p.getHandCards());
                System.out.println("discarded after aiPlay: "+discardList);
                discardList.setDiscard(drawOrThrows);
                nextTurn();
            } else {
                p.drawCard(deck.getCard(FLIPPED));
                System.out.println(p.getAlias()+" have to draw a card");
                nextTurn();
            }
        });
        
        if (!paused) {
        	if (!winGame(sortedPlayerList.get(previousId()))) {
                aiPlay.setRepeats(false);
                aiPlay.start(); 
                System.out.println("PREVIOUS ID IS THAT NOT WINNER: "+sortedPlayerList.get(previousId()));
                System.out.println("previus didn't win? "+!winGame(sortedPlayerList.get(previousId())));
            } else {
                System.out.println("GAME OVEEEEEEEER");
                return true;
            }
		} else {
			System.out.println("GIOCO IN PAUSAAAA");
		}
        return false;        
    }
    
    public void play(Card rejected) {
        if (rejected.getValue().equals(Value.SKIP)) {
            nextTurn();
        }
        if (rejected.getValue().equals(Value.REVERSE)) {
            reverseTurn();
        }
        if (rejected.getValue().equals(Value.DRAW_TWO)) {
            Player drawTwo = getNextPlayer();
            drawTwo.drawCards(getDeck().getCards(2, FLIPPED));
        }
        if (rejected.isWildFour()) {
            Player drawFour = getNextPlayer();
            drawFour.drawCards(getDeck().getCards(4, FLIPPED));
        }
        if (rejected.isWild() || rejected.isWildFour()) {
        	rejected.setColor(getBottomPlayer().chooseColor());
            System.out.println("Valid color of WILD: "+rejected.getColor());
        }
        
        playersList.get(0).discard(rejected);
        discardList.setDiscard(rejected);
        
        Timer unoCheck = new Timer(SEC_AI_PLAY, (ae)->{
            if (checkUno()) {
				
			}
        });
        
        unoCheck.setRepeats(false);
        unoCheck.start(); 
        nextTurn();
        
        setChanged();
    	notifyObservers();
    }
    
    public boolean legitDiscard(Card card) {
        Card lastDiscard = getDiscard().getLastDiscard();
        return (lastDiscard.isSameColor(card) ||
                lastDiscard.isSameValue(card) ||
                card.isWild() || card.isWildFour()) 
        		&& !(bottomPlayer.getHandCards().size()==1 && (card.isWild() || card.isWildFour()) );
    }
    
    public boolean checkUno() {
    	if ( getBottomPlayer().getUno() ) {
			return true;
		} else {
			return false;
		}
    }
    
    public boolean winGame(Player winner) {
    	int winnerGameId = winner.getGameId();
    	
        return sortedPlayerList.get(winnerGameId).getHandCards().isEmpty();
    }
    
    public void iWon(int winnerId) {
    	if (sortedPlayerList.get(winnerId).getHandCards().isEmpty() && winnerId==0) {
    		getBottomPlayer().getAccountInfo().addGamesWon();
		}
    	getBottomPlayer().getAccountInfo().addGamesPlayed();
    }
    
    public void nextTurn() {
        if ( !(gameDirection.getGameDirection()) ) {
            int next = currentPlayerId + 1;
            currentPlayerId = (next == playersList.size()) ? 0 : next;
        } else {
            int next = currentPlayerId - 1;
            currentPlayerId = (next == -1) ? lastPlayerId : next;
        }
        setChanged();
        notifyObservers();
    }
    
    public void reverseTurn() {
        gameDirection = GameDirection.forValue(!gameDirection.getGameDirection());
        
        setChanged();
        notifyObservers();
    }
    
    public int previousId() {
    	if ( !(gameDirection.getGameDirection()) ) {
    		int previous = currentPlayerId - 1;
            return (previous < 0) ? lastPlayerId : previous;
        } else {
            int previous = currentPlayerId + 1;
            return (previous > playersList.size()-1) ? 0 : previous;
        }
    }
    
    public int nextId() {
        if ( !(gameDirection.getGameDirection()) ) {
            int next = currentPlayerId + 1;
            return (next == playersList.size()) ? 0 : next;
        } else {
            int next = currentPlayerId - 1;
            return (next == -1) ? lastPlayerId : next;
        }
    }
    
    public Player getCurrentPlayer() {
        return sortedPlayerList.get(currentPlayerId);
    }
    
    public Player getPreviousPlayer() {
        return sortedPlayerList.get(previousId());
    }
    
    public Player getNextPlayer() {
        return sortedPlayerList.get(nextId());
    }
    
    public List<Player> getPlayers() {
        return sortedPlayerList;
    }

    public void setAI(List<Account> players) {
    }
    
    public boolean getGameDirection() {
    	return gameDirection.getGameDirection();
    }
    
    public Deck getDeck() {
        return deck;
    }
    
    public static int getSecAiPlay() {
        return SEC_AI_PLAY;
    }
    
    //////////////////////////////////////
    
    public AiPlayer getTopPlayer() {
        return topPlayer;
    }

    public AiPlayer getRightPlayer() {
        return rightPlayer;
    }

    public AiPlayer getLeftPlayer() {
        return leftPlayer;
    }

    public Player getBottomPlayer() {
        return bottomPlayer;
    }

    public Discarded getDiscard() {
        return discardList;
    }
    
    
}
