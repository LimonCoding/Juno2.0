package gui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import controller.Controller;
import model.Card;
import model.Player;

public class PlayerPanel extends JPanel {
	
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
	
	public PlayerPanel(Controller controller, JUnoFrame frame, Player player, int space, int nCards) {
		this.frame = frame;
		this.controller = controller;
	    this.player = player;
	    setInnerBorder(player.getAccountInfo().getAlias());
		setOuterBorder();
		
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		setCardLayoutSpec(space, nCards);
		setLayout(myCardLayout);
		setOpaque(false);
	}
	
	public PlayerPanel(Controller controller, JUnoFrame frame, String alias, int space, int nCards) {
        this.frame = frame;
        this.controller = controller;
        setInnerBorder(alias);
        setOuterBorder();
        
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        
        setCardLayoutSpec(space, nCards);
        setLayout(myCardLayout);
        setOpaque(false);
    }
	
	public void setInnerBorder(String title) {
		innerBorder = BorderFactory.createTitledBorder(null, title, 
				TitledBorder.CENTER, TitledBorder.TOP, 
				new Font("Cabin Bold", 30, 30), Color.BLACK);
	}
	
	public Player getPlayer() {
        return player;
    }

    public void setPlayerTurn() {
	    innerBorder.setTitleColor(Color.RED);
	    setBackground(new Color(255,255,255,60));
	    setOpaque(true);
    }
	
	public void clearTurn() {
	    innerBorder.setTitleColor(Color.BLACK);
	    setBackground(new Color(0,0,0,30));
	    setOpaque(true);
    }
	
	public void setOuterBorder() {
		outerBorder = BorderFactory.createEmptyBorder(8, 8, 8, 8);
	}
	
	public void setCardLayoutSpec(int space, int nCards) {
		myCardLayout = new FlowLayout(FlowLayout.CENTER,space,nCards);
	}
	
	public void setCards(List<Card> cards) {
	    removeAll();
	    controller.getGame().getBottomPlayer().setHandCards(cards);
        cards.stream().forEach((card) -> {
	        JButton carta = new JButton();
            carta.setIcon(card.getFaceCard());
            setCardButtonSettings(carta);
            add(carta);
            carta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (controller.getGame().getCurrentPlayer().equals(player)) {
                        if (controller.getGame().legitDiscard(card)) {
                            controller.getGame().getBottomPlayer().discard(card);
                            controller.getDiscard().setDiscard(card);
                            JButton buttonThatWasClicked = (JButton)e.getSource();
                            Container parent = buttonThatWasClicked.getParent();
                            parent.remove(buttonThatWasClicked);
                            parent.revalidate();
                            parent.repaint();
                            controller.getGame().play(card);
                            controller.getGame().nextTurn();
                            frame.setVisible(true);
                            frame.getDeckPanel().getDiscardLabel().setVisible(true);
                            frame.getDeckPanel().getDiscardLabel().setIcon(carta.getIcon());
                            frame.updateCurrentPlayer(controller);
                            } else {
                                System.out.println(player);
                                System.out.println(controller.getGame().getCurrentPlayer());
                                JOptionPane.showMessageDialog(frame, 
                                    "This card is not legit to throw.", 
                                    "Unlegit discard!", JOptionPane.ERROR_MESSAGE);
                            }
                    } else {
                        System.out.println(controller.getGame().getCurrentPlayer());
                        JOptionPane.showMessageDialog(frame, 
                            "Wait your turn!", 
                            "Not your turn!", JOptionPane.ERROR_MESSAGE);
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
                if (controller.getGame().legitDiscard(card)) {
                    controller.getGame().getBottomPlayer().discard(card);
                    controller.getDiscard().setDiscard(card);
                    frame.getDeckPanel().getDiscardLabel().setVisible(true);
                    frame.getDeckPanel().getDiscardLabel().setIcon(carta.getIcon());
                    controller.getGame().play(card);
                    controller.getGame().nextTurn();
                    frame.updateCurrentPlayer(controller);
                    JButton buttonThatWasClicked = (JButton)e.getSource();
                    Container parent = buttonThatWasClicked.getParent();
                    parent.remove(buttonThatWasClicked);
                    parent.revalidate();
                    parent.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, 
                            "This card is not legit to throw.", 
                            "Unlegit discard!", JOptionPane.ERROR_MESSAGE);
                }
    		}
        });
	}
	
	public void setEnemyCard(List<Card> cards) {
	    cards.stream().forEach((card) -> {
	        JButton carta = new JButton();
            carta.setIcon(card.getFaceCard());
            setCardButtonSettings(carta);
            add(carta);
	    });
	    System.out.println(cards);
    }
	
	public void updateEnemyCard(List<Card> cards) {
	    removeAll();
	    cards.stream().forEach((card) -> {
            JButton carta = new JButton();
            carta.setIcon(card.getFaceCard());
            setCardButtonSettings(carta);
            add(carta);
        });
	}
	
	public void drawEnemyCard(Card card) {
        JButton carta = new JButton();
        carta.setIcon(card.getFaceCard());
        setCardButtonSettings(carta);
        add(carta);
    }
	
	public void setCardButtonSettings(JButton carta) {
	    carta.setBorder(BorderFactory.createEmptyBorder());
        carta.setContentAreaFilled(false);
        carta.setPreferredSize(new Dimension(100, 150));
	}
    
}
