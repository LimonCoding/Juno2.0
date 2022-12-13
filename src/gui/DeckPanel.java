package gui;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import controller.Controller;
import model.Card;

public class DeckPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Border innerBorder;
	private Border outerBorder;
	
	private JButton deckButton;
    private JLabel gameVerse;
    private JLabel discardLabel;
	
	/* TO DO: SET AN IMAGE ICON TO SEE GAME DIRECTION IN GAME */
	private ImageIcon gameDirection;
	private Dimension cardDimension = new Dimension(100, 150);
	private FlowLayout deckFlowLayout;
	private Controller controller;
	
	private JLabel turnLabel;
	private JLabel lastDiscardLabel;
	
	public DeckPanel(Controller controller, Card discard, String borderTitle) {
		this.controller = controller;
	    setNameBorder(borderTitle);
		outerBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		setOpaque(false);
		setDeckLayout();

        setDeck();
        add(deckButton);

        gameVerse.setHorizontalAlignment(SwingConstants.CENTER);
        gameVerse.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_process_48px.png"))); // NOI18N
        add(gameVerse);

        setDiscardButton(discard.toString());
        add(discardLabel);
		

		turnLabel = new JLabel();
		turnLabel.setFont(new Font("Cabin Bold", 30, 30));
		turnLabel.setForeground(Color.BLACK);
		add(turnLabel);
		
		lastDiscardLabel = new JLabel();
		lastDiscardLabel.setFont(new Font("Cabin Bold", 30, 30));
		lastDiscardLabel.setForeground(Color.BLACK);
        add(lastDiscardLabel);
	}
	
	public void setNameBorder(String title) {
		innerBorder = BorderFactory.createTitledBorder(title);
	}
	
	public void setDeckLayout() {
		deckFlowLayout = new FlowLayout(FlowLayout.CENTER, 30, 50);
        deckFlowLayout.setAlignOnBaseline(true);
        setLayout(deckFlowLayout);
	}
	
	public JButton getDeckButton() {
		return deckButton;
	}
	
	public JLabel getDiscardLabel() {
        return discardLabel;
    }
	
	public void setDeck() {
		deckButton.setIcon(controller.getDeck().getDeckFace()); // NOI18N
		deckButton.setBorder(BorderFactory.createEmptyBorder());
        deckButton.setContentAreaFilled(false);
        deckButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deckButton.setPreferredSize(cardDimension);
    }
	
	public void setDiscardButton(String discard) {
		discardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        discardLabel.setIcon(new ImageIcon(getClass().getResource("/cards/"+discard))); // NOI18N
    }

    public ImageIcon getGameDirection() {
        return gameDirection;
    }

    public void setGameDirection(ImageIcon gameDirection) {
        this.gameDirection = gameDirection;
    }
    
    public void updateLabelTurn() {
        this.turnLabel.setText("It's "+controller.getCurrentPlayerAlias()+" turn.");
    }
    
    public void updateLabelDiscard() {
        this.lastDiscardLabel.setText(controller.getLastDiscard()+" it's last discard");
    }
}
