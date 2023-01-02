package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 * JFrame for displaying a message when the game is won or lost.
 * 
 * @author Simone
 */
public class WinMessage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -468103537745421425L;
	/** The panel for the gradient background. */
	private PanelGradient backgroungWin;
	/** The margin panel. */
	private MarginPanel winMarginPanel;
	/** The panel for the win message. */
	private JPanel winPanel;
	/** The label for the fireworks icon. */
	private JLabel fireworks;
	/** The label for the win message. */
	private JLabel winLabel;
	/** The label for the lose message. */
	private JLabel looseLabel;
	/** The button for returning to the homepage. */
	private JButton homeButton;
	/**
     * Constructs a new WinMessage with the specified win or lose status.
     * 
     * @param winOrLoose whether the game was won (`true`) or lost (`false`)
     */
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
	/**
     * Gets the home button.
     * 
     * @return the home button
     */
	public JButton getHomeButton() {
		return homeButton;
	}
	/**
     * Sets up the panel for the win message.
     */
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
	/**
	 * Sets up the panel for the lose message.
	 */
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
