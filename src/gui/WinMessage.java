package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

public class WinMessage extends JFrame {

	private PanelGradient backgroungWin;
	private MarginPanel winMarginPanel;
	private JPanel winPanel;
	
	private JLabel fireworks;
	private JLabel winLabel;
	private JLabel looseLabel;
	private JButton homeButton;
	
	public WinMessage(boolean winOrLoose) {
		
		backgroungWin = new PanelGradient();
		backgroungWin.setLayout(new BorderLayout());
    	winMarginPanel = new MarginPanel();
    	
		winPanel = new JPanel();
		if (winOrLoose) {
			setWinPanel();
		} else {
			setLoosePanel();
		}
		
		
		winMarginPanel.add(winPanel, BorderLayout.CENTER);
		backgroungWin.add(winMarginPanel, BorderLayout.CENTER);
		getContentPane().add(backgroungWin);
		
		setPreferredSize(new Dimension(650,420));
		setMinimumSize(new Dimension(650,420));
		setUndecorated(true);
		setLocationRelativeTo(null);
	}
	
	public JButton getHomeButton() {
		return homeButton;
	}
	
	private void setWinPanel() {
		fireworks = new JLabel();
		winLabel = new JLabel();
		homeButton = new JButton();
		
		fireworks.setHorizontalAlignment(SwingConstants.CENTER);
		fireworks.setIcon(new ImageIcon(getClass().getResource("/icons/fireworkExplosion_96px.png"))); // NOI18N
		
		winLabel.setFont(new Font("Segoe UI", 1, 48)); // NOI18N
		winLabel.setForeground(new Color(0, 0, 0));
		winLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winLabel.setIcon(new ImageIcon(getClass().getResource("/icons/firstPlace_96px.png"))); // NOI18N
		winLabel.setText("Hai Vinto!");
		winLabel.setAutoscrolls(true);
		winLabel.setIconTextGap(2);
		
		homeButton.setIcon(new ImageIcon(getClass().getResource("/icons/home_96px.png"))); // NOI18N
		homeButton.setToolTipText("Torna alla Homepage");
		homeButton.setBorderPainted(false);
		homeButton.setContentAreaFilled(false);
		homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		homeButton.setFocusPainted(false);
		homeButton.setText("Torna alla homepage");
		homeButton.setFont(new Font("Segoe UI", 1, 20)); // NOI18N
		homeButton.setForeground(new Color(0, 0, 0));
        
		winPanel.setOpaque(false);
		winPanel.add(winLabel);
		winPanel.add(fireworks);
		winPanel.add(homeButton);
	}
	
	private void setLoosePanel() {
		looseLabel = new JLabel();
		homeButton = new JButton();
		
		looseLabel.setFont(new Font("Segoe UI", 1, 48)); // NOI18N
		looseLabel.setForeground(new Color(0, 0, 0));
		looseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		looseLabel.setIcon(new ImageIcon(getClass().getResource("/icons/crying_96px.png"))); // NOI18N
		looseLabel.setText("Hai Perso...");
		looseLabel.setAutoscrolls(true);
		looseLabel.setIconTextGap(2);
		
		homeButton.setIcon(new ImageIcon(getClass().getResource("/icons/home_96px.png"))); // NOI18N
		homeButton.setToolTipText("Torna alla Homepage");
		homeButton.setBorderPainted(false);
		homeButton.setContentAreaFilled(false);
		homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		homeButton.setFocusPainted(false);
		homeButton.setText("Torna alla homepage");
		homeButton.setFont(new Font("Segoe UI", 1, 20)); // NOI18N
		homeButton.setForeground(new Color(0, 0, 0));
        
		winPanel.setOpaque(false);
		winPanel.add(looseLabel);
		winPanel.add(homeButton);
	}
}
