package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Account;
/**
 * Table model for displaying a list of accounts in a table.
 * @author Simone
 */
public class AccountTableModel extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4793731552947909903L;
	/** The list of accounts to display in the table. */
	private List<Account> db;
	/** The names of the columns in the table. */
    private String[] colNames = {"ID", "ALIAS","PLAYED", "WON", "LEVEL"};
	/**
     * Sets the data for this table model.
     * 
     * @param db the list of accounts to display in the table
     */
    public void setData(List<Account> db) {
        this.db = db;
    }
    /**
     * Gets the number of rows in the table (equal to the number of accounts in the list).
     * 
     * @return the number of rows in the table
     */
    @Override
    public int getRowCount() {
        return db.size();
    }
    /**
     * Gets the number of columns in the table (equal to the number of elements in the `colNames` array).
     * 
     * @return the number of columns in the table
     */
    @Override
    public int getColumnCount() {
        return colNames.length;
    }
    /**
     * Gets the name of the column at the given index.
     * 
     * @param column the index of the column
     * @return the name of the column
     */
    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }
    /**
     * Gets the value at the given row and column indices.
     * 
     * @param rowIndex the index of the row
     * @param columnIndex the index of the column
     * @return the value at the given row and column indices
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Account account = db.get(rowIndex);
        switch (columnIndex) {
            case 0: 
                return account.getId();
            case 1: 
                return account.getAlias();
            case 2: 
                return account.getGamesPlayed();
            case 3: 
                return account.getGamesWon();
            case 4: 
                return account.getLevel();
        }
        return null;
    }
}
