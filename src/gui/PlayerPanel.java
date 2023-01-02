package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controller.Controller;
import model.Card;
import model.Player;
/**
 * Represents a panel that displays information about a player in a game of UNO.
 * The panel displays the player's alias and their current hand of cards.
 * The panel also observes the game and updates its display when the game state changes.
 * @author Simone
 */
@SuppressWarnings("deprecation")
public class PlayerPanel extends JPanel implements Observer {
	
	/** The serial version UID for the PlayerPanel class. */
	private static final long serialVersionUID = 1L;
	/** The inner border that displays the player's alias. */
	private TitledBorder innerBorder;
	/** The outer border for the panel. */
	private Border outerBorder;
	/** The frame that contains the PlayerPanel. */
	private JUnoFrame frame;
	/** The controller for the game of UNO. */
	private Controller controller;
	/** The layout manager for the panel. */
	private FlowLayout myCardLayout;
	/** The player whose information is displayed in the panel. */
    private Player player;
    /** Stroke size. it is recommended to set it to 1 for better view */
    protected int strokeSize = 1;
    /** Color of shadow */
    protected Color shadowColor = Color.white;
    /** Sets if it drops shadow */
    protected boolean shady = true;
    /** Sets if it has an High Quality view */
    protected boolean highQuality = true;
    /** Double values for Horizontal and Vertical radius of corner arcs */
    protected Dimension arcs = new Dimension(200, 200);
    /** The offset of shadow.  */
    protected int shadowOffset = 0;
    /** The transparency value of shadow. ( 0 - 255) */
    protected int shadowAlpha = 80;
    /**
	 * Constructs a new PlayerPanel for the specified player.
	 * 
	 * @param controller the controller for the game of UNO
	 * @param frame the frame that contains the PlayerPanel
	 * @param player the player whose information is displayed in the panel
	 * @param xSpace the horizontal space between cards in the panel
	 * @param ySpace the vertical space between cards in the panel
	 */
	public PlayerPanel(Controller controller, JUnoFrame frame, Player player, int xSpace, int ySpace) {
        this.frame = frame;
        this.controller = controller;
        this.player = player;
        
        this.controller.getGame().addObserver(this);
        
        setInnerBorder(player.getAlias());
        setOuterBorder();
        
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        
        setCardLayoutSpec(xSpace, ySpace);
        setLayout(myCardLayout);
        setOpaque(false);
    }
	/**
	 * Sets the inner border of the PlayerPanel to display the specified title.
	 * The inner border is a TitledBorder with a rounded line border and a title font.
	 * 
	 * @param title the title to display in the inner border
	 */
	public void setInnerBorder(String title) {
		LineBorder roundedLineBorder = new LineBorder(Color.BLACK, 0, true);
		innerBorder = new TitledBorder(roundedLineBorder, title);
		innerBorder.setTitleJustification(TitledBorder.CENTER);
		innerBorder.setTitleColor(Color.black);
		innerBorder.setTitleFont(new Font("Cabin Bold", 30, 30));
	}
	/**
	 * Paints the component with a shadow border if specified.
	 * The shadow border is drawn with the specified shadow color and alpha value.
	 * The component is also rendered with high quality rendering if specified.
	 * 
	 * @param g the graphics context to use for painting
	 */
	@Override
	protected void paintComponent(Graphics g) {
	       super.paintComponent(g);
	       int width = getWidth();
	       int height = getHeight();
	       Color shadowColorA = new Color(shadowColor.getRed(),
	    		   				shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
	       Graphics2D graphics = (Graphics2D) g;

	       //Sets antialiasing if HQ.
	       if (highQuality) {
	           graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	           RenderingHints.VALUE_ANTIALIAS_ON);
	       }

	       //Draws shadow borders if any.
	       if (shady) {
	           graphics.setColor(shadowColorA);
	           graphics.fillRoundRect(
	                   shadowOffset,// X position
	                   shadowOffset,// Y position
	                   width - strokeSize - shadowOffset, // width
	                   height - strokeSize - shadowOffset, // height
	                   arcs.width, arcs.height);// arc Dimension
	       }
	}
	/**
	 * Gets the player whose information is displayed in the panel.
	 * @return the player whose information is displayed in the panel
	 */
	public Player getPlayer() {
        return player;
    }
	/**
	 * Sets the shadow color of the panel to white to indicate that it is the player's turn.
	 */
    public void setPlayerTurn() {
	    shadowColor = Color.WHITE;
    }
    /**
     * Sets the shadow color of the panel back to black to indicate that it is not the player's turn.
     */
	public void clearTurn() {
	    shadowColor = Color.BLACK;
    }
	/**
	 * Sets the outer border of the panel to an empty border with 8 pixels of padding on each side.
	 * Used to make space between the frame and panel.
	 */
	public void setOuterBorder() {
		outerBorder = BorderFactory.createEmptyBorder(8, 8, 8, 8);
	}
	/**
	 * Sets the layout manager for the panel to a FlowLayout with the specified horizontal and vertical spacing.
	 * @param xSpace the horizontal space between cards in the panel
	 * @param ySpace the vertical space between cards in the panel
	 */
	public void setCardLayoutSpec(int xSpace, int ySpace) {
		myCardLayout = new FlowLayout(FlowLayout.CENTER,xSpace,ySpace);
	}
	/**
	 * Sets the cards displayed in the panel to the specified list of cards.
	 * Each card is displayed as a button with the card's face image as its icon.
	 * The buttons have action listeners that allow the player to play the card.
	 * 
	 * @param cards the list of cards to display in the panel
	 */
	public void setCards(List<Card> cards) {
	    removeAll();
        cards.stream().forEach((card) -> {
	        JButton carta = new JButton();
            carta.setIcon(card.getFaceCard());
            setCardButtonSettings(carta);
            Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
            carta.setCursor(handCursor);
            add(carta);
            carta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	switch (controller.plays(card)) {
                	case 0:
    					JOptionPane.showMessageDialog(frame, 
                                "IL COLORE O IL VALORE DELLA CARTA SELEZIONATA NON E' VALIDO", 
                                "CARTA NON VALIDA", JOptionPane.ERROR_MESSAGE);
    					break;
					case 1:
						break;
					case 2:
						JOptionPane.showMessageDialog(frame, 
	                            "ASPETTA IL TUO TURNO PER GIOCARE", 
	                            "NON E' IL TUO TURNO", JOptionPane.ERROR_MESSAGE);
						break;
					case 3:
						JOptionPane.showMessageDialog(frame, 
	                            "RIPRENDI IL GIOCO PER POTER GIOCARE", 
	                            "GIOCO IN PAUSA", JOptionPane.ERROR_MESSAGE);
						break;
					case 4:
						new SelectColor(controller, card);
						break;
					}
                }
            });
	    });
	}
	/**
	 * Draws the specified card and adds it to the panel as a button with the card's face image as its icon.
	 * The button has an action listener that allows the player to play the card.
	 * The card is also added to the player's hand.
	 * 
	 * @param card the card to draw and add to the panel
	 */
	public void drawCard(Card card) {
		JButton carta = new JButton();
		carta.setIcon(card.getFaceCard());
		setCardButtonSettings(carta);
		add(carta);
		controller.getGame().getBottomPlayer().drawCard(card);
		System.out.println(controller.getGame().getBottomPlayer().getHandCards().toString());
		carta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Card c = (Card) e.getSource();
            	switch (controller.plays(c)) {
				case 0:
					JOptionPane.showMessageDialog(frame, 
                            "IL COLORE O IL VALORE DELLA CARTA SELEZIONATA NON E' VALIDO", 
                            "CARTA NON VALIDA", JOptionPane.ERROR_MESSAGE);
					break;
				case 1:
					break;
				case 2:
					JOptionPane.showMessageDialog(frame, 
                            "ASPETTA IL TUO TURNO PER GIOCARE", 
                            "NON E' IL TUO TURNO", JOptionPane.ERROR_MESSAGE);
					break;
				case 3:
					JOptionPane.showMessageDialog(frame, 
                            "RIPRENDI IL GIOCO PER POTER GIOCARE", 
                            "GIOCO IN PAUSA", JOptionPane.ERROR_MESSAGE);
				break;
				case 4:
					new SelectColor(controller, card);
					break;
				}
    		}
        });
	}
	/**
	 * Sets the cards displayed in the panel to the specified list of cards.
	 * Each card is displayed as a label with the card's face image as its icon.
	 * 
	 * @param cards the list of cards to display in the panel
	 */
	public void setEnemyCard(List<Card> cards) {
		removeAll();
	    cards.stream().forEach((card) -> {
	    	JLabel carta = new JLabel();
            carta.setIcon(card.getFaceCard());
            setCardButtonSettings(carta);
            add(carta);
	    });
    }
	/**
	 * Sets the specified button's size, cursor, tooltip text, and other visual settings for a card button.
	 * 
	 * @param carta the button to set the visual settings for
	 */
	public void setCardButtonSettings(JButton carta) {
        carta.setPreferredSize(new Dimension(100, 150));
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		carta.setCursor(handCursor);
		carta.setToolTipText("Clicca per scartare");
		carta.setBorderPainted(false);
		carta.setContentAreaFilled(false);
		carta.setCursor(handCursor);
		carta.setFocusPainted(false);
	}
	/**
	 * Sets the specified label's size and border for an enemy card label.
	 * 
	 * @param carta the label to set the visual settings for
	 */
	public void setCardButtonSettings(JLabel carta) {
		carta.setBorder(BorderFactory.createEmptyBorder());
	    carta.setPreferredSize(new Dimension(100, 150));
	}
	/**
	 * Updates the panel to display the current state of the game and the player.
	 * If the player is an enemy, their cards are displayed as labels with the card's face images as icons.
	 * If the player is the bottom player (human), 
	 * their cards are displayed as buttons with the card's face images as icons and action listeners that allow the player to play the cards.
	 * If the player is the current player, the panel's shadow color is set to white. Otherwise, the shadow color is set to black.
	 * The panel is then updated to reflect the changes.
	 * 
	 * @param obs the Observable object
	 * @param obj the object being passed
	 */
	@Override
	public void update(Observable obs, Object obj) {
		if (player.getGameId() != 0) {
			this.setEnemyCard(player.getHandCards());
		} else {
			this.setCards(player.getHandCards());
		}
		if (controller.getCurrentPlayer().equals(this.player)) {
			setPlayerTurn();
		} else {
			clearTurn();
		}
		SwingUtilities.updateComponentTreeUI(this);
	}
}
