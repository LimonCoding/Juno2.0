package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

//import gui.AccountEvent;
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
    
    public Game getGame() {
        return this.game;
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
	
//	public void addAccount(AccountEvent ev) {
//		String alias = ev.getAlias();
//		int level = ev.getLevel();
//		Account account = new Account(alias, level);
//		db.addAccount(account);
//	}
	
	public void saveToFile(File file) throws IOException {
	    db.saveAccounts(file);
	}
	
	public void loadToFile(File file) throws IOException {
        db.loadAccounts(file);
    }
	
	public Player getCurrentPlayer() {
        return this.game.getCurrentPlayer();
    }
	
	public String getCurrentPlayerAlias() {
	    return this.game.getCurrentPlayer().getAccountInfo().getAlias();
	}
	
	public boolean getUno() {
	    return this.game.getCurrentPlayer().uno();
	}
	
	public boolean checkWin() {
	    return this.game.winGame(this.game.getCurrentPlayer());
	}
}
