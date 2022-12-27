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

@SuppressWarnings("deprecation")
public class PlayerPanel extends JPanel implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TitledBorder innerBorder;
	private Border outerBorder;
	private JUnoFrame frame;
	private Controller controller;
	private FlowLayout myCardLayout;
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
    protected Dimension arcs = new Dimension(20, 20);
    /** Distance between shadow border and opaque panel border */
    protected int shadowGap = 5;
    /** The offset of shadow.  */
    protected int shadowOffset = 4;
    /** The transparency value of shadow. ( 0 - 255) */
    protected int shadowAlpha = 80;
	
	public PlayerPanel(Controller controller, JUnoFrame frame, Player player, int xSpace, int ySpace) {
        this.frame = frame;
        this.controller = controller;
        this.player = player;
        
        setInnerBorder(player.getAlias());
        setOuterBorder();
        
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        
        setCardLayoutSpec(xSpace, ySpace);
        setLayout(myCardLayout);
        setOpaque(false);
    }
	
	public void setInnerBorder(String title) {
		LineBorder roundedLineBorder = new LineBorder(Color.BLACK, 0, true);
		innerBorder = new TitledBorder(roundedLineBorder, title);
		innerBorder.setTitleJustification(TitledBorder.CENTER);
		innerBorder.setTitleColor(Color.black);
		innerBorder.setTitleFont(new Font("Cabin Bold", 30, 30));
//		innerBorder = BorderFactory.createTitledBorder(null, title, 
//				TitledBorder.CENTER, TitledBorder.TOP, 
//				new Font("Cabin Bold", 30, 30), Color.BLACK);
	}
	
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

	       //Draws the rounded opaque panel with borders.
	       graphics.setColor(getForeground());
	       graphics.setStroke(new BasicStroke(strokeSize));

	       //Sets strokes to default, is better.
	       graphics.setStroke(new BasicStroke());
	   }
	
	public Player getPlayer() {
        return player;
    }

    public void setPlayerTurn() {
	    shadowColor = Color.WHITE;
    }
	
	public void clearTurn() {
	    shadowColor = Color.BLACK;
    }
	
	public void setOuterBorder() {
		outerBorder = BorderFactory.createEmptyBorder(8, 8, 8, 8);
	}
	
	public void setCardLayoutSpec(int xSpace, int ySpace) {
		myCardLayout = new FlowLayout(FlowLayout.CENTER,xSpace,ySpace);
	}
	
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
						frame.update(controller.getGame(), cards);
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
					}
                }
            });
	    });
	}	
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
					frame.update(controller.getGame(), null);
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
				}
    		}
        });
	}
	
	public void setEnemyCard(List<Card> cards) {
		removeAll();
	    cards.stream().forEach((card) -> {
	    	JLabel carta = new JLabel();
            carta.setIcon(card.getFaceCard());
            setCardButtonSettings(carta);
            add(carta);
	    });
	    System.out.println(cards);
    }
	
	public void updateEnemyCard(List<Card> cards) {
	    removeAll();
	    cards.stream().forEach((card) -> {
	    	JLabel carta = new JLabel();
            carta.setIcon(card.getFaceCard());
            setCardButtonSettings(carta);
            add(carta);
        });
	}
	
	public void drawEnemyCard(Card card) {
		JLabel carta = new JLabel();
        carta.setIcon(card.getFaceCard());
        setCardButtonSettings(carta);
        add(carta);
    }
	
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
	
	public void setCardButtonSettings(JLabel carta) {
		carta.setBorder(BorderFactory.createEmptyBorder());
	    carta.setPreferredSize(new Dimension(100, 150));
	}

	@Override
	public void update(Observable obs, Object obj) {
		removeAll();
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
