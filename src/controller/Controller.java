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
/**
 * The `Controller` class acts as a mediator between the game model and the user interface. It contains methods for 
 * creating and interacting with a game, as well as methods for creating and interacting with a list of accounts.
 * 
 * <p>The `Controller` class maintains a reference to a `Game` object and an `AccountListDatabase` object. The `Game` object 
 * represents the current game, and the `AccountListDatabase` object represents a list of accounts that can be used to 
 * save and load game progress. The `Controller` class provides methods for interacting with these objects, such as 
 * creating a new game, pausing and resuming the game, and playing cards as discards. It also provides methods for 
 * creating and interacting with the list of accounts, such as adding new accounts, saving the list of accounts to a file,
 * and loading the list of accounts from a file.
 * @author Simone
 */
public class Controller {
	/**
	 * The `db` field represents a list of accounts that can be used to save and load game progress.
	 */
    private AccountListDatabase db;
    /**
     * The `game` field represents the current game.
     */
    private Game game;
    /**
     * Creates a new game with the specified `player` as the bottom player.
     * 
     * @param account the bottom player for the new game
     */
    public void createGame(Account account) {
        game = new Game(account);
    }
    /**
     * Resets the current game.
     */
    public void resetGame() {
        game.clearGame();
    }
    /**
     * Gets the current game.
     * 
     * @return the current game
     */
    public Game getGame() {
        return game;
    }
    /**
     * Gets the direction of play for the current game.
     * 
     * @return `true` if the direction of play is clockwise, `false` if the direction of play is counterclockwise
     */
    public boolean getGameDirection() {
    	return game.getGameDirection();
    }
    /**
     * Gets the deck of cards for the current game.
     * 
     * @return the deck of cards for the current game
     */
    public Deck getDeck() {
        return game.getDeck();
    }
    /**
     * Gets the discarded pile of cards for the current game.
     * 
     * @return the discarded pile of cards for the current game
     */
    public Discarded getDiscard() {
        return game.getDiscard();
    }
    /**
     * Gets the last card discarded in the current game.
     * 
     * @return the last card discarded in the current game
     */
    public Card getLastDiscard() {
        return game.getDiscard().getLastDiscard();
    }
    /**
     * Creates a new empty list of accounts.
     */
	public void createAccountList() {
	    db = new AccountListDatabase();
    }
	/**
	 * Gets a list of all accounts in the database.
	 * 
	 * @return a list of all accounts in the database
	 */
	public List<Account> getAccounts() {
		return db.getAccounts();
	}
	/**
	 * Gets the account at the specified index in the database.
	 * 
	 * @param id the index of the account to retrieve
	 * @return the account at the specified index in the database
	 */
	public Account getAccount(int id) {
	    return db.getAccounts().get(id);
    }
	/**
	 * Adds a new account to the database.
	 * 
	 * @param alias the nickname of the new account
	 * @param level the level of the new account
	 */
	public void addAccount(String alias, int level) {
		Account account = new Account(alias, level);
		db.addAccount(account);
	}
	/**
	 * Saves the list of accounts in the database to a file.
	 * 
	 * @throws IOException if an I/O error occurs
	 */
	public void saveToFile() throws IOException {
		File dir = new File(Game.savesPath);
		dir.mkdirs();
		File tmp = new File(dir, "saves.txt");
		tmp.createNewFile();
	    db.saveAccounts(tmp);
	}
	/**
	 * Loads the list of {@link Account} objects from a file.
	 * @throws IOException if an error occurs while reading from the file
	 */
	public void loadFromFile() throws IOException {
		File dir = new File(Game.savesPath);
		dir.mkdirs();
		File tmp = new File(dir, "saves.txt");
        db.loadAccounts(tmp);
    }
	/**
	 * Gets the current player of the game.
	 * @return the current player of the game
	 */
	public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }
	/**
	 * Gets the game id of the current player.
	 * @return the game id of the current player
	 */
	public int getCurrentPlayerId() {
        return game.getCurrentPlayer().getGameId();
    }
	/**
	 * Gets the alias of the current player.
	 * @return the alias of the current player
	 */
	public String getCurrentPlayerAlias() {
	    return game.getCurrentPlayer().getAlias();
	}
	/**
	 * Toggles the paused state of the game.
	 */
	public void pauseGame() {
		game.setPaused(!game.getPaused());
	}
	/**
	 * Makes the AI play a turn in the game.
	 * 
	 * @return true if the the previous player won the game, false otherwise
	 */
	public boolean aiPlay() {
		return game.aiPlay(getLastDiscard());
	}
	/**
	 * Attempts to play a card in the game.
	 * 
	 * @param card the card to play
	 * @return an integer indicating the result of the play:
	 *         0 - the card was not a legit discard
	 *         1 - the card was played successfully
	 *         2 - it is not the current player's turn
	 *         3 - the game was paused after the card was played
	 *         4 - the card was a wild card and a color must be chosen
	 */
	public int plays(Card card) {
		if (game.getCurrentPlayer().getGameId() == 0) {
			if (game.legitDiscard(card)) {
				boolean pausedGame = game.play(card);
				if (pausedGame) {
					return 3;
				}
				if (card.isColorWild()) {
					return 4;
				} else {
					game.getDiscard().addDiscard(card);
					game.nextTurn();
				}
			} else 
				return 0;
			return 1;
		} else 
			return 2;
	}
	/**
	 * Sets the "UNO safe" status of the bottom player in the game.
	 * 
	 * @param unoSafe the new UNO safe status
	 */
	public void setUnoSafe(boolean unoSafe) {
	    game.getBottomPlayer().setUnoSafe(unoSafe);
	}
	/**
	 * Gets the "UNO safe" status of the bottom player in the game.
	 * 
	 * @return the UNO safe status of the bottom player
	 */
	public boolean getUnoSafe() {
    	return game.getBottomPlayer().getUnoSafe();
    }
	/**
	 * Gets the "UNO" status of the bottom player in the game.
	 * 
	 * @return true if the bottom player has only one card remaining, false otherwise
	 */
	public boolean getUno() {
	    return game.getBottomPlayer().getUno();
	}
	/**
	 * Checks if any player has reached UNO status (only one card remaining).
	 * 
	 * @return true if a player has reached UNO status, false otherwise
	 */
	public boolean checkUno() {
	    return game.checkUno();
	}
	/**
	 * Indicates that the bottom player has won the game.
	 */
	public void iWon() {
	    game.iWon();
	}
	/**
	 * Checks if the current player has lost the game.
	 * 
	 * @return true if the current player has lost, false otherwise
	 */
	public boolean checkLoose() {
		if ( game.winGame(game.getPreviousPlayer()) && game.getCurrentPlayer().getGameId()==0) {
			return true;
		}
		return false;
	}
}
