package gui;

import java.util.EventObject;

import model.Account;
/**
 * Event class for representing changes to an account.
 * 
 * @author Simone
 */
public class AccountEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The alias of the account. */
	private String alias;
	/** The level of the account. */
	private int level;
	/**
     * Constructs a new AccountEvent with the specified source object.
     * 
     * @param source the source object of the event
     */
	public AccountEvent(Object source) {
		super(source);
	}
	/**
     * Constructs a new AccountEvent with the specified source object and alias.
     * 
     * @param source the source object of the event
     * @param alias the alias of the account
     */
	public AccountEvent(Object source, String alias) {
		super(source);
		this.alias = alias;
		this.level = 0;
	}
	/**
     * Sets the alias of the account.
     * 
     * @param alias the alias of the account
     */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
     * Sets the level of the account.
     * 
     * @param level the level of the account
     */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
     * Gets the alias of the account.
     * 
     * @return the alias of the account
     */
	public String getAlias() {
		return alias;
	}
	/**
     * Gets the level of the account.
     * 
     * @return the level of the account
     */
	public int getLevel() {
		return level;
	}
	/**
     * Returns a string representation of the account in the form "alias level".
     * 
     * @param account the account
     * @return a string representation of the account
     */
	public String toString(Account account) {
		return account.getAlias() + " " + account.getLevel();
	}
}
