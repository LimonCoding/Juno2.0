package gui;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.UIManager;

import model.Account;

public class ExampleRenderer extends DefaultListCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4809342346082391960L;
	private Map<String, ImageIcon> iconMap = new HashMap<>();
    private Color background = new Color(0, 100, 255, 15);
    private Color defaultBackground = (Color) UIManager.get("List.background");

    public ExampleRenderer() {
        iconMap.put("Primo",new ImageIcon(getClass().getResource("/icons/icons8_compose_48px.png")));
        iconMap.put("Secondo",new ImageIcon(getClass().getResource("/icons/icons8_home_48px.png")));
    	
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Account emp = (Account) value;
        this.setText(emp.getAlias());
        this.setIcon(iconMap.get(emp.getAlias()));
        if (!isSelected) {
            this.setBackground(index % 2 == 0 ? background : defaultBackground);
        }
        return this;
    }
}