package model;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * The class Account represents a generic account
 * @author Simone
 */
public class Account extends Object implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7593324165697695593L;
	/**
	 * static integer to increment id account every new
	 */
	private static int count = 0;
	/**
	 * integer to identify a unique account
	 */
	private int id;
	/**
	 * string to identify the account name
	 */
	private String alias;
	/**
	 * integer to identify games played by an account
	 */
	private int gamesPlayed;
	/**
	 * integer to identify games won by an account
	 */
	private int gamesWon;
	/**
	 * decimal value to identify the account level
	 * (based on number of gamesPlayed and gamesWon)
	 */
	private double level;
	/**
	 * ImageIcon to identify the account icon
	 * (not implemented yet)
	 */
	private ImageIcon accountIcon;
	
	/**
	 * Account constructor with alias field as parameter
	 * @param alias the name of account
	 */
	public Account(String alias) {
        this.alias = alias;
        
        this.id = count;
        count++;
    }

	/**
	 * Account constructor with alias and level field as parameter
	 * @param alias the name of account
	 * @param level the level of account
	 */
    public Account(String alias, double level) {
		this.alias = alias;
		this.level = level;
		if (AccountListDatabase.getAccountsSaved()!=null) {
		    this.id = AccountListDatabase.getAccountsSaved().length;
        } else {
            this.id = count;
            count++;
        }
	}
	
    /**
     * Account constructor with alias, level and accountIcon field as parameter
     * @param alias the name of account
     * @param level the level of account
     * @param accountIcon the icon of account (not implemented yet)
     */
	public Account(String alias, double level, ImageIcon accountIcon) {
		this.alias = alias;
		this.level = level;
		this.accountIcon = accountIcon;
		
		this.id = count;
		count++;
	}
	
	/**
	 * get the account's alias
	 * @return alias the account name
	 */
	public String getAlias() {
		return alias;
	}
	
	/**
	 * alias setter method
	 * @param alias the account name
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	/**
	 * get the account's level
	 * @return level the account level
	 */
	public double getLevel() {
		return level;
	}
	/**
	 * level setter method
	 * @param level the account level
	 */
	public void setLevel(double level) {
		this.level = level;
	}
	/**
	 * get the account's icon
	 * (not implemented yet)
	 * @return accountIcon the account icon
	 */
	public ImageIcon getAccountIcon() {
		return accountIcon;
	}
	/**
	 * accountIcon setter method
	 * (not implemented yet)
	 * @param accountIcon the account icon
	 */
	public void setAccountIcon(ImageIcon accountIcon) {
		this.accountIcon = accountIcon;
	}
	/**
	 * get the account's id
	 * @return id the unique integer value of account
	 */
	public int getId() {
		return id;
	}
	/**
	 * id setter method
	 * @param id the unique integer value of account
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * get the account's games won
	 * @return gamesWon the number of games won by account
	 */
	public int getGamesWon() {
		return gamesWon;
	}
	/**
	 * gamesWon setter method
	 * @param gamesWon the number of games won by account
	 */
	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}
	/**
	 * method that increment gamesWon and level values
	 */
	public void addGamesWon() {
		gamesWon = gamesWon + 1;
		level += 1.00;
	}
	/**
	 * get the account's games played
	 * @return gamesPlayed the number of games played by account
	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	/**
	 * gamesPlayed setter method
	 * @param gamesPlayed the number of games played by account
	 */
	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}
	/**
	 * method that increment gamesPlayed and level values
	 */
	public void addGamesPlayed() {
		gamesPlayed = gamesPlayed + 1;
		level += 0.50;
	}
	/**
	 * @return a string representation of account.
	 */
	@Override
    public String toString() {
        return "Account [id=" + id + ", alias=" + alias + ", level=" + level + ", accountIcon=" + accountIcon + "]";
    }
}
