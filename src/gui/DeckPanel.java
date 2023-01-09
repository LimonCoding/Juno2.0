package gui;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import controller.Controller;
import model.Card;
/**
 * This class represents the panel that displays the deck, game direction, and last card discarded in a game of UNO.
 * It implements the Observer interface to update the panel when the game state changes. It has a border, a
 * flow layout, and components to display the deck of cards, the game direction, and the top card of the discard
 * pile. It also has a button to draw a card from the deck, and labels to display the current player's turn and
 * the last card discarded (this last 2 things are not implemented).
 * @author Simone
 */
@SuppressWarnings("deprecation")
public class DeckPanel extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The inner border of this panel. */
	private Border innerBorder;
	/** The outer border of this panel. */
	private Border outerBorder;
	/** The deck button of this panel. */
	private JButton deckButton;
	/** The game direction label of this panel. */
    private JLabel gameVerse;
    /** The discard label of this panel. */
    private JLabel discardLabel;
    /** The preferred size of the cards in this panel. */
	private Dimension cardDimension = new Dimension(100, 150);
	/** The layout manager of this panel. */
	private FlowLayout deckFlowLayout;
	/** The controller for the game of UNO. */
	private Controller controller;
	/** The turn label of this panel. (not implemented in this version */
	private JLabel turnLabel;
	/** The last discard label of this panel. (not implemented in this version */
	private JLabel lastDiscardLabel;
	/**
     * Constructs a new DeckPanel with the given controller and discard card. It sets the panel's border, layout,
     * and background. It initializes the deck button, game direction label, and discard label. It adds itself as
     * an observer of the game.
     *
     * @param controller the controller of the game
     * @param discard the top card of the discard pile
     */
	public DeckPanel(Controller controller, Card discard) {
		this.controller = controller;
	    setNameBorder();
		outerBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		setOpaque(false);
		setDeckLayout();

		deckButton = new JButton();
        setDeck();
        
        this.controller.getGame().addObserver(this);

        gameVerse = new JLabel();
        gameVerse.setHorizontalAlignment(SwingConstants.CENTER);
        if (controller.getGameDirection()) {
			gameVerse.setIcon(new ImageIcon(getClass().getResource("/icons/clockwise_48px.png")));
		} else {
			gameVerse.setIcon(new ImageIcon(getClass().getResource("/icons/counterClockwise_48px.png")));
		}
        discardLabel = new JLabel();
        setDiscardButton(discard.toString());
        
        add(deckButton);
        add(gameVerse);
        add(discardLabel);
	}
	/**
     * Sets the inner border of this panel.
     */
	public void setNameBorder() {
		innerBorder = BorderFactory.createEmptyBorder();
	}
	/**
     * Sets the flow layout of this panel.
     */
	public void setDeckLayout() {
		deckFlowLayout = new FlowLayout(FlowLayout.CENTER, 20, 20);
        deckFlowLayout.setAlignOnBaseline(true);
        setLayout(deckFlowLayout);
	}
	/**
     * Gets the deck button of this panel.
     * @return the deck button
     */
	public JButton getDeckButton() {
		return deckButton;
	}
	/**
	 * Gets the discard label of this panel.
	 * @return the discard label
	 */
	public JLabel getDiscardLabel() {
        return discardLabel;
    }
	/**
	 * Sets the deck button of this panel. 
	 * <p>It sets the icon to the front face of the deck, makes the button visible,
	 * removes the border, makes the button not filled, sets the cursor to a hand cursor, and sets the preferred size
	 * of the button.
	 */
	public void setDeck() {
		deckButton.setIcon(controller.getDeck().getDeckFace()); // NOI18N
		deckButton.setVisible(true);
		deckButton.setBorder(BorderFactory.createEmptyBorder());
        deckButton.setContentAreaFilled(false);
        deckButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deckButton.setPreferredSize(cardDimension);
    }
	/**
	 * Sets the discard label of this panel to the given discard card. 
	 * <p>It sets the horizontal alignment of the label
	 * to center and sets the icon to the image of the discard card.
	 *
	 * @param discard the discard card to display
	 */
	public void setDiscardButton(String discard) {
		discardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        discardLabel.setIcon(new ImageIcon(getClass().getResource("/cards/"+discard))); // NOI18N
    }
    /**
     * Updates the turn label of this panel with the current player's alias.
     * (not implemented in this version)
     */
    public void updateLabelTurn() {
        this.turnLabel.setText("It's "+controller.getCurrentPlayerAlias()+" turn.");
    }
    /**
     * Updates the last discard label of this panel with the last card discarded.
     * (not implemented in this version)
     */
    public void updateLabelDiscard() {
        this.lastDiscardLabel.setText(controller.getLastDiscard()+" it's last discard");
    }
    /**
     * Updates this panel when the game state changes. It sets the game direction label to the appropriate icon and
     * sets the discard label to the image of the last card discarded.
     *
     * @param o the observable object
     * @param arg the argument passed to the notifyObservers method
     */
	@Override
	public void update(Observable o, Object arg) {
		if (controller.getGameDirection()) {
			gameVerse.setIcon(new ImageIcon(getClass().getResource("/icons/clockwise_48px.png")));
		} else {
			gameVerse.setIcon(new ImageIcon(getClass().getResource("/icons/counterClockwise_48px.png")));
		}
		discardLabel.setIcon(controller.getLastDiscard().getFaceCard());
	}
}
