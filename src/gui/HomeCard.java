package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
/**
 * A panel that serves as the home screen for the UNO game.
 * Contains a background gradient, a logo button that allows the player to create a new account for playing, and welcome messages.
 * @author Simone
 */
public class HomeCard {
	/** The main panel with a gradient background. */
	private PanelGradient backgroungHome;
    /** A panel with margin used to make margins between frame and home card panel. */
    private MarginPanel homeMarginPanel;
    /** A panel that contains the welcome message labels and logo button. */
    private JLabel welcome;
    /** A label that displays the welcome message. */
    private JPanel welcomePanel;
    /** A label that displays a message to click the logo button. */
    private JLabel cliccaLogo;
    /** A button with the UNO logo that allows the player to to create a new account for playing when clicked. */
    private JButton logo;
    /** A filler panel used to add space to the right of the logo button. */
    private Box.Filler leftMarginLogo;
    /** A filler panel used to add space to the left of the logo button. */
    private Box.Filler rightMarginLogo;
    /** Constructs a new HomeCard panel. */
	HomeCard() {
    	backgroungHome = new PanelGradient();
    	homeMarginPanel = new MarginPanel();
        welcomePanel = new JPanel();
        welcome = new JLabel();
        cliccaLogo = new JLabel();
        logo = new JButton();
        rightMarginLogo = new Box.Filler(new Dimension(330, 0), new Dimension(330, 0), new Dimension(660, 0));
        leftMarginLogo = new Box.Filler(new Dimension(330, 0), new Dimension(330, 0), new Dimension(660, 0));
        
    	backgroungHome.setLayout(new BorderLayout());


        welcomePanel.setOpaque(false);
        welcomePanel.setLayout(new BorderLayout());

        welcome.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        welcome.setForeground(new Color(0, 0, 0));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setIcon(new ImageIcon(getClass().getResource("/icons/hello_96px.png"))); // NOI18N
        welcome.setText("Benvenuto!");
        welcome.setHorizontalTextPosition(SwingConstants.RIGHT);
        welcomePanel.add(welcome, BorderLayout.PAGE_START);

        cliccaLogo.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        cliccaLogo.setForeground(new Color(0, 0, 0));
        cliccaLogo.setHorizontalAlignment(SwingConstants.CENTER);
        cliccaLogo.setIcon(new ImageIcon(getClass().getResource("/icons/handPointer_96px.png"))); // NOI18N
        cliccaLogo.setText("clicca il logo per iniziare");
        welcomePanel.add(cliccaLogo, BorderLayout.PAGE_END);

        logo.setIcon(new ImageIcon(getClass().getResource("/icons/unoLogo.png"))); // NOI18N
        logo.setToolTipText("Clicca per giocare");
        logo.setBorder(null);
        logo.setBorderPainted(false);
        logo.setContentAreaFilled(false);
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        logo.setCursor(handCursor);
        logo.setFocusPainted(false);
        logo.setHorizontalTextPosition(SwingConstants.CENTER);
        Dimension prefSize = new Dimension(420, 400);
        logo.setMaximumSize(prefSize);
        logo.setMinimumSize(prefSize);
        logo.setPreferredSize(prefSize);
        logo.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                logoMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                logoMouseExited(evt);
            }
        });
        welcomePanel.add(logo, BorderLayout.CENTER);
        welcomePanel.add(rightMarginLogo, BorderLayout.LINE_END);
        welcomePanel.add(leftMarginLogo, BorderLayout.LINE_START);
        homeMarginPanel.add(welcomePanel, BorderLayout.CENTER);
        backgroungHome.add(homeMarginPanel, BorderLayout.CENTER);
	}
	/**
	 * Handles the event when the mouse enters the logo button.
	 * Changes the logo icon to unoLogoOnButton.png.
	 * @param evt the mouse event
	 */
	private void logoMouseEntered(MouseEvent evt) {                                  
        logo.setIcon(new ImageIcon(getClass().getResource("/icons/unoLogoOnButton.png")));
    }                                 
	/**
	 * Handles the event when the mouse exits the logo button.
	 * Changes the logo icon to unoLogo.png.
	 * @param evt the mouse event
	 */
    private void logoMouseExited(MouseEvent evt) {                                 
        logo.setIcon(new ImageIcon(getClass().getResource("/icons/unoLogo.png")));
    }
    /**
     * Gets the logo button.
     * @return the logo button
     */
	public JButton getLogoButton() {
		return logo;
	} 
	/**
	 * Gets the background panel.
	 * @return the background panel
	 */
	public PanelGradient getBackground() {
		return backgroungHome;
	} 
}
