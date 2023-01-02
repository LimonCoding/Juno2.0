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

/**
 * The class AccountListDatabase represents a list of accounts
 * @author Simone
 */
public class AccountListDatabase {
	/**
	 * ArrayList of Accounts
	 */
	private ArrayList<Account> listAccount;
	/**
	 * Array of saved Accounts 
	 */
	private static Account[] accountsSaved;
	/**
	 * AccountListDatabase constructor that create an empty list of Accounts
	 */
	public AccountListDatabase() {
		listAccount = new ArrayList<Account>();
	}
	/**
	 * method to add an account to the list
	 * @param account to add to the ArrayList
	 */
	public void addAccount(Account account) {
		listAccount.add(account);
	}
	/**
	 * get the accounts list
	 * @return listAccount the list of accounts
	 */
	public List<Account> getAccounts() {
		return listAccount;
	}
	/**
	 * Saves a list of accounts to the specified file.
	 * @param file the file to save the accounts to
	 * @throws IOException if an I/O error occurs
	 */
	public void saveAccounts(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        accountsSaved = listAccount.toArray(new Account[listAccount.size()]);
        oos.writeObject(accountsSaved);
        oos.close();
    }
	/**
	 * Loads the saved accounts from the specified file and adds them to the list of accounts.
	 *
	 * @param file the file from which to load the saved accounts
	 * @throws IOException if an I/O error occurs
	 */
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
	/**
	 * get the saved accounts list
	 * @return accountsSaved the list of saved accounts
	 */
	public static Account[] getAccountsSaved() {
	    return accountsSaved;
	}
}
