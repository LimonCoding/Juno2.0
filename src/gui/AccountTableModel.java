package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Account;

public class AccountTableModel extends AbstractTableModel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -4793731552947909903L;
	private List<Account> db;
    private String[] colNames = {"ID","AVATAR", "ALIAS", "LEVEL"};

    public void setData(List<Account> db) {
        this.db = db;
    }
    
    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Account account = db.get(rowIndex);
        switch (columnIndex) {
            case 0: 
                return account.getId();
//            case 1: 
//            	ImageIcon[] avatars = {
//                		new ImageIcon(getClass().getResource("/icons/icons8_compose_48px.png")),
//                		new ImageIcon(getClass().getResource("/icons/icons8_home_48px.png"))
//                };
//                JComboBox<ImageIcon> combo = new JComboBox<>(avatars);
//                combo.setModel(loadImages());
//                combo.addActionListener(new ActionListener() {
//        			@Override
//        			public void actionPerformed(ActionEvent e) {
//        				JOptionPane.showMessageDialog(null, combo.getSelectedItem());
//        			}
//        		});
//                return new DefaultCellEditor(combo);
            case 1: 
                return account.getAccountIcon();
            case 2: 
                return account.getAlias();
            case 3: 
                return account.getLevel();
        }
        return null;
    }
}
