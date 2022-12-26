package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
        game.clearGame();
    }
    
    public Game getGame() {
        return game;
    }
    
    public boolean getGameDirection() {
    	return game.getGameDirection();
    }
    
    public Deck getDeck() {
        return game.getDeck();
    }

    public Discarded getDiscard() {
        return game.getDiscard();
    }
    
    public Card getLastDiscard() {
        return game.getDiscard().getLastDiscard();
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
        return game.getCurrentPlayer();
    }
	
	public int getCurrentPlayerId() {
        return game.getCurrentPlayer().getGameId();
    }
	
	public String getCurrentPlayerAlias() {
	    return game.getCurrentPlayer().getAlias();
	}
	
	public boolean aiPlay() {
		return game.aiPlay(getLastDiscard());
	}
	
	public int plays(Card card) {
		if (game.getCurrentPlayer().getGameId() == 0) {
			if (game.legitDiscard(card)) {
				boolean pausedGame = game.play(card);
				if (pausedGame) {
					return 3;
				}
			} else 
				return 0;
			return 1;
		} else 
			return 2;
	}
	
	public void setUnoSafe(boolean unoSafe) {
	    game.getBottomPlayer().setUnoSafe(unoSafe);
	}
	
	public boolean getUnoSafe() {
    	return game.getBottomPlayer().getUnoSafe();
    }
	
	public boolean getUno() {
	    return game.getBottomPlayer().getUno();
	}
	
	public boolean checkUno() {
	    return game.checkUno();
	}
	
	public boolean checkWin() {
	    return game.winGame(game.getCurrentPlayer());
	}
	
	public void iWon() {
	    game.iWon();
	}
	
	public boolean checkLoose() {
		if ( game.winGame(game.getPreviousPlayer()) && game.getCurrentPlayer().getGameId()==0) {
			return true;
		}
		return false;
	}

}
