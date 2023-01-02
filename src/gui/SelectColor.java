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

import controller.Controller;
import model.Card;
/**
 * A GUI class that allows a user to select a color for a Card object.
 * The class extends the JFrame class and contains various buttons and panels for
 * selecting and displaying the selected color.
 * 
 * @author Simone
 */
public class SelectColor extends JFrame {
	/** A unique serial version identifier for this class. */
	private static final long serialVersionUID = 1228748442706314780L;
	/** A PanelGradient object that serves as the background of the frame. */
	private PanelGradient backgroungColor;
	/** A MarginPanel object that serves as a margin around the chooseColorPanel. */
	private MarginPanel colorMarginPanel;
	/** A JPanel object that contains buttons for selecting a color. */
	private JPanel chooseColorPanel;
	/** A JButton object for selecting the color green. */
	private JButton greenButton;
	/** A JButton object for selecting the color blue. */
	private JButton blueButton;
	/** A JButton object for selecting the color red. */
	private JButton redButton;
	/** A JButton object for selecting the color yellow. */
	private JButton yellowButton;
	/** A JLabel object for displaying the text "Select a color:" */
	private JLabel selectColor;
	/** A Card object that represents the card whose color is being selected. */
	private Card card;
	/** A Controller object that serves as the controller for the program. */
	private Controller controller;
	/**
	 * Constructs a new SelectColor object with the specified controller and card.
	 * 
	 * @param controller The controller for the program.
	 * @param card The card whose color is being selected.
	 */
	public SelectColor(Controller controller, Card card) {
		this.card = card;
		this.controller = controller;
		backgroungColor = new PanelGradient();
		backgroungColor.setLayout(new BorderLayout());
    	colorMarginPanel = new MarginPanel();
    	
		chooseColorPanel = new JPanel();
		setChooseColorPanel();
		
		colorMarginPanel.add(chooseColorPanel, BorderLayout.CENTER);
		backgroungColor.add(colorMarginPanel, BorderLayout.CENTER);
		getContentPane().add(backgroungColor);
		
		setPreferredSize(new Dimension(550,320));
		setMinimumSize(new Dimension(550,320));
		setUndecorated(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	/**
	 * Handles the action performed when the green button is clicked.
	 * Sets the color of the wild card to green and adds the card to the discard pile.
	 * Then, ends the current turn and closes the window.
	 *
	 * @param evt the action event that triggered this method
	 */
	private void greenButtonActionPerformed(ActionEvent evt) {                                            
		card.setColor(model.Card.Color.GREEN);
		controller.getGame().getDiscard().addDiscard(card);
		controller.getGame().nextTurn();
		this.dispose();
    }                                           
	/**
	 * Handles the action performed when the red button is clicked.
	 * Sets the color of the wild card to red and adds the card to the discard pile.
	 * Then, ends the current turn and closes the window.
	 *
	 * @param evt the action event that triggered this method
	 */
    private void redButtonActionPerformed(ActionEvent evt) {  
    	card.setColor(model.Card.Color.RED);
    	controller.getGame().getDiscard().addDiscard(card);
		controller.getGame().nextTurn();
		this.dispose();
    }   
    /**
     * Handles the action performed when the blue button is clicked.
     * Sets the color of the wild card to blue and adds the card to the discard pile.
     * Then, ends the current turn and closes the window.
     *
     * @param evt the action event that triggered this method
     */
    private void blueButtonActionPerformed(ActionEvent evt) {   
    	card.setColor(model.Card.Color.BLUE);
    	controller.getGame().getDiscard().addDiscard(card);
		controller.getGame().nextTurn();
    	this.dispose();
    }
    /**
     * Handles the action performed when the yellow button is clicked.
     * Sets the color of the wild card to yellow and adds the card to the discard pile.
     * Then, ends the current turn and closes the window.
     *
     * @param evt the action event that triggered this method
     */
    private void yellowButtonActionPerformed(ActionEvent evt) { 
    	card.setColor(model.Card.Color.YELLOW);
    	controller.getGame().getDiscard().addDiscard(card);
		controller.getGame().nextTurn();
    	this.dispose();
    }     
    /**
     * Gets the current color of the card.
     * @return the current color of the card
     */
    public model.Card.Color getColor() {
    	return card.getColor();
    }
    /**
     * Initializes the chooseColorPanel and sets its layout, buttons, and labels.
     * The chooseColorPanel allows the user to select a color for the card.
     */
    private void setChooseColorPanel() {
		greenButton = new JButton();
        blueButton = new JButton();
        redButton = new JButton();
        yellowButton = new JButton();
        selectColor = new JLabel();
        
		chooseColorPanel.setMaximumSize(new Dimension(200, 200));
        chooseColorPanel.setMinimumSize(new Dimension(200, 200));
        chooseColorPanel.setOpaque(false);

        greenButton.setForeground(new Color(0, 204, 0));
        greenButton.setIcon(new ImageIcon(getClass().getResource("/icons/greenSquare_48px.png"))); // NOI18N
        greenButton.setBorderPainted(false);
        greenButton.setContentAreaFilled(false);
        greenButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        greenButton.setFocusPainted(false);
        greenButton.setMaximumSize(new Dimension(60, 40));
        greenButton.setMinimumSize(new Dimension(60, 40));
        greenButton.setPreferredSize(new Dimension(60, 40));
        greenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                greenButtonActionPerformed(evt);
            }
        });

        blueButton.setForeground(new Color(0, 0, 204));
        blueButton.setIcon(new ImageIcon(getClass().getResource("/icons/blueSquare_48px.png"))); // NOI18N
        blueButton.setBorderPainted(false);
        blueButton.setContentAreaFilled(false);
        blueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        blueButton.setFocusPainted(false);
        blueButton.setMaximumSize(new Dimension(60, 40));
        blueButton.setMinimumSize(new Dimension(60, 40));
        blueButton.setPreferredSize(new Dimension(60, 40));
        blueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                blueButtonActionPerformed(evt);
            }
        });

        redButton.setForeground(new Color(204, 0, 0));
        redButton.setIcon(new ImageIcon(getClass().getResource("/icons/redSquare_48px.png"))); // NOI18N
        redButton.setBorderPainted(false);
        redButton.setContentAreaFilled(false);
        redButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        redButton.setFocusPainted(false);
        redButton.setMaximumSize(new Dimension(60, 40));
        redButton.setMinimumSize(new Dimension(60, 40));
        redButton.setPreferredSize(new Dimension(60, 40));
        redButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                redButtonActionPerformed(evt);
            }
        });

        yellowButton.setForeground(new Color(255, 255, 0));
        yellowButton.setIcon(new ImageIcon(getClass().getResource("/icons/yellowSquare_48px.png"))); // NOI18N
        yellowButton.setBorderPainted(false);
        yellowButton.setContentAreaFilled(false);
        yellowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        yellowButton.setFocusPainted(false);
        yellowButton.setMaximumSize(new Dimension(60, 40));
        yellowButton.setMinimumSize(new Dimension(60, 40));
        yellowButton.setPreferredSize(new Dimension(60, 40));
        yellowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                yellowButtonActionPerformed(evt);
            }
        });

        selectColor.setFont(new Font("Segoe UI", 1, 28)); // NOI18N
        selectColor.setForeground(new Color(0, 0, 0));
        selectColor.setHorizontalAlignment(SwingConstants.CENTER);
        selectColor.setIcon(new ImageIcon(getClass().getResource("/icons/colorSwatch_48px.png"))); // NOI18N
        selectColor.setText("Seleziona un Colore");
        selectColor.setAutoscrolls(true);
        selectColor.setIconTextGap(2);
        
        chooseColorPanel.add(selectColor);
        chooseColorPanel.add(greenButton);
        chooseColorPanel.add(blueButton);
        chooseColorPanel.add(yellowButton);
        chooseColorPanel.add(redButton);
	}
}
