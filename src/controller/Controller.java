package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gui.AccountEvent;

import model.Account;
import model.AccountListDatabase;
import model.Card;
import model.Deck;
import model.Discarded;
import model.Game;
import model.Player;

public class Controller {
    
    private AccountListDatabase db;
    private Game game;
    
    public void createGame(Account player) {
        game = new Game(player);
    }
    
    public void resetGame() {
        this.game.clearGame();
    }
    
    public Game getGame() {
        return this.game;
    }
    
    public boolean getGameDirection() {
    	return this.game.getGameDirection();
    }
    
    public Deck getDeck() {
        return this.game.getDeck();
    }

    public Discarded getDiscard() {
        return this.game.getDiscard();
    }
    
    public Card getLastDiscard() {
        return this.game.getDiscard().getLastDiscard();
    }
    
	public void createAccountList() {
	    db = new AccountListDatabase();
    }
	
	public List<Account> getAccounts() {
		return db.getAccounts();
	}
	
	public Account getAccount(int id) {
	    return db.getAccounts().get(id);
    }
	
	public void addAccount(String alias, int level) {
		Account account = new Account(alias, level);
		db.addAccount(account);
	}
	
	public void saveToFile(File file) throws IOException {
	    db.saveAccounts(file);
	}
	
	public void loadToFile(File file) throws IOException {
        db.loadAccounts(file);
    }
	
	public Player getCurrentPlayer() {
        return this.game.getCurrentPlayer();
    }
	
	public int getCurrentPlayerId() {
        return this.game.getCurrentPlayer().getGameId();
    }
	
	public String getCurrentPlayerAlias() {
	    return this.game.getCurrentPlayer().getAccountInfo().getAlias();
	}
	
	public boolean aiPlay() {
		return this.game.aiPlay(getLastDiscard());
	}
	
	public int plays(Card card) {
		if (game.getCurrentPlayer().getGameId() == 0) {
			if (game.legitDiscard(card)) {
				game.play(card);
			} else 
				return 0;
			return 1;
		} else 
			return 2;
	}
	
	public void setUno() {
	    this.game.getBottomPlayer().setUno();
	}
	
	public boolean getUno() {
	    return this.game.getBottomPlayer().getUno();
	}
	
	public boolean checkWin() {
	    return this.game.winGame(this.game.getCurrentPlayer());
	}

}
