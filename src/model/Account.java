package model;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Account extends Object implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7593324165697695593L;

	private static int count = 0;

	private int id;
	private String alias;
	private int level = 0;
	private ImageIcon accountIcon;
	
	public Account(String alias) {
        this.alias = alias;
        
        this.id = count;
        count++;
    }

    public Account(String alias, int level) {
		this.alias = alias;
		this.level = level;
		if (AccountListDatabase.getAccountsSaved()!=null) {
		    this.id = AccountListDatabase.getAccountsSaved().length;
        } else {
            this.id = count;
            count++;
        }
	}
	
	public Account(String alias, int level, ImageIcon accountIcon) {
		this.alias = alias;
		this.level = level;
		this.accountIcon = accountIcon;
		
		this.id = count;
		count++;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public ImageIcon getAccountIcon() {
		return accountIcon;
	}
	public void setAccountIcon(ImageIcon accountIcon) {
		this.accountIcon = accountIcon;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
    public String toString() {
        return "Account [id=" + id + ", alias=" + alias + ", level=" + level + ", accountIcon=" + accountIcon + "]";
    }
}
