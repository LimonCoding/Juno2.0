package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import model.Account;

public class TablePanel extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5145208983668940173L;
	private JTable table;
    private AccountTableModel tableModel;
    private JScrollPane scrollPane;
    
    public TablePanel() {
        tableModel = new AccountTableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        fontSettings();
        table.setRowHeight(table.getRowHeight() + 30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Cabin Bold", 30, 30));
        
        ///////////////////////////////////////////////////////////////////////////////////
        ImageIcon[] avatars = {
        		new ImageIcon(getClass().getResource("/icons/compose_48px.png")),
        		new ImageIcon(getClass().getResource("/icons/home_48px.png"))
        };
        JComboBox<ImageIcon> combo = new JComboBox<>(avatars);
        combo.setModel(loadImages());
        combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, combo.getSelectedItem());
			}
		});
        
        TableColumn col = table.getColumnModel().getColumn(1);
        col.setCellEditor(new DefaultCellEditor(combo));
//        combo.setRenderer(new ExampleRenderer());
        ///////////////////////////////////////////////////////////////////////////////////

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        centerRenderer.setOpaque(false);
        table.setDefaultRenderer(Object.class, centerRenderer);
        
        table.setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private DefaultComboBoxModel<ImageIcon> loadImages() {
    	DefaultComboBoxModel<ImageIcon> dm = new DefaultComboBoxModel<>();
    	dm.addElement(new ImageIcon(getClass().getResource("/icons/compose_48px.png")));
    	dm.addElement(new ImageIcon(getClass().getResource("/icons/home_48px.png")));
    	return dm;
    }
    
    private void fontSettings() {
        table.setFont(new Font("Cabin Bold", 30, 30));
        table.setToolTipText("Seleziona un account!");
        table.setForeground(Color.BLACK);
    }

    public void setData(List<Account> db) {
        tableModel.setData(db);
    }
    
    public JTable getTable() {
        return table;
    }
    
    public void refresh() {
        tableModel.fireTableDataChanged();
    }
}
