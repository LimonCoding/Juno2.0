package gui;

import java.util.EventObject;

import model.Account;

public class AccountEvent extends EventObject {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String alias;
	private int level;

	public AccountEvent(Object source) {
		super(source);
	}
	
	public AccountEvent(Object source, String alias) {
		super(source);
		this.alias = alias;
		this.level = 0;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getAlias() {
		return alias;
	}
	
	public int getLevel() {
		return level;
	}
	
	public String toString(Account account) {
		return account.getAlias() + " " + account.getLevel();
	}
}
