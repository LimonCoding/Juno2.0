package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Timer;

import model.AiPlayer.Strategy;
import model.Card.Value;

public class Game {
    
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
    
    private AiPlayer topPlayer;
    private AiPlayer rightPlayer;
    private AiPlayer leftPlayer;
    private Player bottomPlayer;
    
    private final static int SEC_AI_PLAY = 4000;
    
    public Game(Account player) {
        bottomPlayer = new Player(player);
        rightPlayer = new AiPlayer(new Account("Right Player"), Strategy.SAME_VALUE);
        topPlayer = new AiPlayer(new Account("Top Player"), Strategy.SAME_COLOR);
        leftPlayer = new AiPlayer(new Account("Left Player"), Strategy.USE_SPECIAL);
        
        deck = new Deck();
//        System.out.println(deck.toString());
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
            p.setHandCards(new ArrayList<>(deck.getCards(7, Flipped.FLIPPED)));
        }
        bottomPlayer.setHandCards(new ArrayList<>(deck.getCards(7, Flipped.FLIPPED)));
    }
    
    private void startGame(Game game) {
        Card card = deck.getCard(Flipped.FLIPPED);
        System.out.println(card);
        
        if (card.isWild() || card.isSpecial()) {
            System.out.println("DISCARD NOT LEGIT");
            startGame(game);
        } else {
            System.out.println("Discard Setted: "+card);
            discardList.setDiscard(card);
        }
        System.out.println("Discarder from model game: "+discardList);
        gameDirection = Game.GameDirection.CLOCKWISE;
    }
    
    public void refillDeck() {
        if (deck.isEmpty()) {
            deck.replaceDeck(discardList);
        }
    }
    
    public boolean validCardPlay(Card card) {
        if (card.getColor().equals(discardList.getLastDiscard().getColor()) ||
             card.getValue().equals(discardList.getLastDiscard().getValue()) ||
              card.isWild()) {
            discardList.setDiscard(card);
            return true;
        }
        return false;
    }
    
    public void AiPlay(Card rejected) {
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
                    drawTwo.drawCards(deck.getCards(2, Flipped.FLIPPED));
                }
                if (drawOrThrows.getValue().equals(Value.WILD_FOUR)) {
                    Player drawFour = getNextPlayer();
                    drawFour.drawCards(getDeck().getCards(4, Flipped.FLIPPED));
                }
                discardList.setDiscard(drawOrThrows);
                System.out.println(p.getAccountInfo().getAlias()+" Hand: "+p.getHandCards());
                System.out.println("discarded after aiPlay: "+discardList);
                if (drawOrThrows.isWild()) {
                    discardList.getLastDiscard().setColor(p.chooseColor());
                    System.out.println("Valid color of WILD: "+discardList.getLastDiscard().getColor());
                }
                nextTurn();
            } else {
                p.drawCard(deck.getCard(Flipped.FLIPPED));
                System.out.println(p.getAccountInfo().getAlias()+" have to draw a card");
                nextTurn();
            }
        });
        if (!winGame(sortedPlayerList.get(previousId()))) {
            aiPlay.setRepeats(false);
            aiPlay.start(); 
            System.out.println("Previus player hand: "+sortedPlayerList.get(previousId()).getHandCards());
        } else {
            System.out.println("GAME OVEEEEEEEER");
        }
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
            drawTwo.drawCards(getDeck().getCards(2, Flipped.FLIPPED));
        }
        if (rejected.getValue().equals(Value.WILD_FOUR)) {
            Player drawFour = getNextPlayer();
            drawFour.drawCards(getDeck().getCards(4, Flipped.FLIPPED));
        }
        if (rejected.isWild()) {
            discardList.getLastDiscard().setColor(sortedPlayerList.get(0).chooseColor());
            System.out.println("Valid color of WILD: "+rejected.getColor());
        }
    }
    
    public boolean legitDiscard(Card card) {
        Card lastDiscard = getDiscard().getLastDiscard();
        return (lastDiscard.getColor().equals(card.getColor()) ||
                lastDiscard.getValue().equals(card.getValue()) ||
                card.isWild() || lastDiscard.isWild()) && !(bottomPlayer.getHandCards().size()==1 && card.isWild());
    }
    
    public boolean winGame(Player winner) {
        return sortedPlayerList.get(winner.getGameId()).getHandCards().isEmpty();
    }
    
    public void nextTurn() {
        if (gameDirection.getGameDirection()) {
            int next = currentPlayerId + 1;
            currentPlayerId = (next == playersList.size()) ? 0 : next;
        } else {
            int next = currentPlayerId - 1;
            currentPlayerId = (next == -1) ? lastPlayerId : next;
        }
        
    }
    
    public void reverseTurn() {
        gameDirection = GameDirection.forValue(!gameDirection.getGameDirection());
    }
    
    public int previousId() {
        int previous = currentPlayerId - 1;
        return (previous == -1) ? lastPlayerId : previous;
    }
    
    public int nextId() {
        if (gameDirection.getGameDirection()) {
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
    
    public GameDirection getGameDirection() {
        return gameDirection;
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
