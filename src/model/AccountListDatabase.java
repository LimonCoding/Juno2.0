package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountListDatabase {

	private ArrayList<Account> listAccount;
	private static Account[] accountsSaved;
	
	public AccountListDatabase() {
		listAccount = new ArrayList<Account>();
	}
	
	public void addAccount(Account account) {
		listAccount.add(account);
	}
	
	public List<Account> getAccounts() {
		return listAccount;
	}
	
	public void saveAccounts(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        
        accountsSaved = listAccount.toArray(new Account[listAccount.size()]);
        
        oos.writeObject(accountsSaved);
        oos.close();
    }
	
	public void loadAccounts(File file) throws IOException {
	    FileInputStream fis = new FileInputStream(file);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    try {
	        accountsSaved = (Account[])ois.readObject();
            listAccount.clear();
            listAccount.addAll(Arrays.asList(accountsSaved));
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
	    System.out.println(listAccount);
	    ois.close();
	}
	
	public static Account[] getAccountsSaved() {
	    return accountsSaved;
	}
}
