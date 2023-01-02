package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import model.Account;
/**
 * A panel that displays a table of accounts.
 * 
 * @author Simone
 */
public class TablePanel extends JPanel {
    
    /** The serial version UID. */
	private static final long serialVersionUID = 5145208983668940173L;
	/** A table for displaying accounts. */
	private JTable table;
	/** The table model for the accounts table. */
    private AccountTableModel tableModel;
    /** A scroll pane for the accounts table. */
    private JScrollPane scrollPane;
    /**
     * Constructs a new TablePanel.
     */
    public TablePanel() {
        tableModel = new AccountTableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        fontSettings();
        table.setRowHeight(table.getRowHeight() + 30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Cabin Bold", 30, 30));
        
        table.setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        centerRenderer.setOpaque(false);
        table.setDefaultRenderer(Object.class, centerRenderer);
        
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
    /**
     * Sets the font for the table and its cells.
     */
    private void fontSettings() {
        table.setFont(new Font("Cabin Bold", 30, 30));
        table.setToolTipText("Seleziona un account!");
        table.setForeground(Color.BLACK);
    }
    /**
     * Sets the data for the table to display.
     * 
     * @param db The list of accounts to display in the table
     */
    public void setData(List<Account> db) {
        tableModel.setData(db);
    }
    /**
     * Gets the table being displayed in the panel.
     * 
     * @return The table being displayed in the panel
     */
    public JTable getTable() {
        return table;
    }
    /**
     * Refreshes the data displayed in the table.
     */
    public void refresh() {
        tableModel.fireTableDataChanged();
    }
}
