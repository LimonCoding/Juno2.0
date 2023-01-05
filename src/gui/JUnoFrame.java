package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box.Filler;
import javax.swing.*;

import controller.Controller;
import model.Card.Flipped;

/**
 * The main frame for the UNO game. 
 * This frame displays the home screen, the account selection screen, and the game screen.
 * @author Simone
 */
@SuppressWarnings("deprecation")
public class JUnoFrame extends JFrame implements Observer {

    /**
	 * a serialization identifier for the class
	 */
	private static final long serialVersionUID = 481508119037659069L;
	/**
	 * an object that extends JPanel representing the home screen of the game
	 */
	private HomeCard homeCard;
	/**
	 * an object that extends JPanel representing the account selection screen of the game
	 */
	private AccountCard accountCard;
	/**
	 * an object that extends JPanel representing the background of the game screen
	 */
    private PanelGradient backgroungPlay;
    /**
     * a panel containing the pause and back buttons.
     */
    private JPanel pausePanel;
    /**
     * a button that toggles the paused state of the game
     */
    private JButton pauseButton;
    /**
     * a button that takes the user back to the account selection screen
     */
    private JButton backButton;
    /**
     * a panel containing the game elements (e.g. deck, players)
     */
    private JPanel playPan;
    /**
     * a panel representing the game's draw, discard piles and game direction
     */
    private DeckPanel deckPanel;
    /** a panel representing the top player in the game */
    private JPanel topPlayerPanel;
    /** a panel containing the top player's hand card and his name */
    private PlayerPanel topCardPanel;
    /** a panel containing the right player's hand card and his name */
    private PlayerPanel rightPlayerPanel;
    /** a panel containing the left player's hand card and his name */
    private PlayerPanel leftPlayerPanel;
    /** a panel containing the bottom player's hand card and his name */
    private PlayerPanel bottomCardPanel;
    /** a list of all player panels in the game */
    private List<PlayerPanel> playerList;
    /** a panel representing the bottom player in the game with UNO button */
    private JPanel bottomPlayerPanel;
    /** a button that allows the player to declare UNO */
    private JButton unoButton;
    /** an object that controls the game's logic and data */
    private Controller controller;
    /**
     * Constructs a new UNO game frame.
     * Initializes a Controller object and creates the list of accounts.
     * Calls the initComponents() method to initialize the frame's components.
     */
    public JUnoFrame() {
    	controller = new Controller();
    	controller.createAccountList();
        initComponents();
    }
    /**
     * Initializes the components of the UNO game frame.
     * Sets the frame's properties and layout.
     * Adds the home screen, account selection screen, and game screen to the content pane.
     * Packs the frame and sets its location relative to the center of the screen.
     */
    private void initComponents() {
    	setFrameSettings();
        getContentPane().setLayout(new CardLayout());
        homeCard = new HomeCard();
        homeCard.getLogoButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                logoActionPerformed(evt);
            }
        });
        getContentPane().add(homeCard.getBackground(), "card1");
        
        accountCard = new AccountCard(controller, this);
        accountCard.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backAccountButtonActionPerformed(evt);
            }
        });
        accountCard.getPlayButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });
        getContentPane().add(accountCard.getBackground(), "card2");

        pack();
        setLocationRelativeTo(null);
    }                      
    /**
     * Initializes the components of the game screen.
     * 
     * @param id the id of the account selected to play with
     */
	private void createGameCard(int id) {
    	backgroungPlay = new PanelGradient();
    	
        pausePanel = new JPanel();
        pauseButton = new JButton();
        backButton = new JButton();
        
        JUnoFrame frame = this;
        
        deckPanel = new DeckPanel(controller, controller.getDiscard().getLastDiscard());
        
        topCardPanel = new PlayerPanel(controller, frame, controller.getGame().getTopPlayer(), -30, 20);
        leftPlayerPanel = new PlayerPanel(controller, frame, controller.getGame().getLeftPlayer(), -50, 50);
        rightPlayerPanel = new PlayerPanel(controller, frame, controller.getGame().getRightPlayer(), -50, 50);
        bottomCardPanel = new PlayerPanel(controller, frame, controller.getGame().getBottomPlayer(), -30, 20);
        playerList = new ArrayList<>(Arrays.asList(topCardPanel, leftPlayerPanel, rightPlayerPanel, bottomCardPanel));
        
        controller.getGame().addObserver(this);
        
        for (PlayerPanel playerPanel : playerList) {
        	if (playerPanel.getPlayer().getGameId() != 0) {
        		playerPanel.setEnemyCard(playerPanel.getPlayer().getHandCards());
    		} else {
    			playerPanel.setCards(playerPanel.getPlayer().getHandCards());
    		}
		}
        
      for (PlayerPanel player : playerList) {
  		player.clearTurn();
		}
	    switch (controller.getCurrentPlayerId()) {
          case 0 -> bottomCardPanel.setPlayerTurn();
          case 1 -> rightPlayerPanel.setPlayerTurn();
          case 2 -> topCardPanel.setPlayerTurn();
          case 3 -> leftPlayerPanel.setPlayerTurn();
      }
        
        playPan = new JPanel();
        topPlayerPanel = new JPanel();
        bottomPlayerPanel = new JPanel();
        unoButton = new JButton();
    	
    	backgroungPlay.setLayout(new BorderLayout());
    	
    	Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        Dimension prefSize = new Dimension(1280, 48);
    	
        pausePanel.setMaximumSize(prefSize);
        pausePanel.setMinimumSize(prefSize);
        pausePanel.setPreferredSize(prefSize);
        pausePanel.setOpaque(false);
        pausePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, -2));

        pauseButton.setIcon(new ImageIcon(getClass().getResource("/icons/pauseButton_48px.png"))); // NOI18N
        pauseButton.setContentAreaFilled(false);
        pauseButton.setCursor(handCursor);
        pauseButton.addActionListener(pauseResume);

        backButton.setIcon(new ImageIcon(getClass().getResource("/icons/return_48px.png"))); // NOI18N
        backButton.setContentAreaFilled(false);
        backButton.setCursor(handCursor);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backGameButtonActionPermormed(evt);
            }
        });
        
        playPan.setOpaque(false);
        playPan.setLayout(new BorderLayout());

        topPlayerPanel.setPreferredSize(new Dimension(1280, 250));
        topPlayerPanel.setPreferredSize(new Dimension(1280, 250));
        topPlayerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        topPlayerPanel.setOpaque(false);
        topCardPanel.setPreferredSize(new Dimension(900, 250));
        Dimension dim = getPreferredSize();
		dim.width = 550;
		dim.height = 700;
        leftPlayerPanel.setPreferredSize(dim);
        rightPlayerPanel.setPreferredSize(dim);
        bottomPlayerPanel.setPreferredSize(new Dimension(1280, 250));
        bottomPlayerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        bottomPlayerPanel.setOpaque(false);
        bottomCardPanel.setPreferredSize(new Dimension(900, 250));

        setUnoButton();
        
        
        deckPanel.getDeckButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    if ( controller.getCurrentPlayerId() == 0 ) {
			    	if ( !controller.getGame().getPaused() ) {
			    		bottomCardPanel.drawCard(controller.getDeck().getCard(Flipped.FLIPPED, controller.getDiscard()));
					} else {
						JOptionPane.showMessageDialog(frame, 
	                            "RIPRENDI IL GIOCO PER POTER GIOCARE", 
	                            "GIOCO IN PAUSA", JOptionPane.ERROR_MESSAGE);
					}
                } else {
                	JOptionPane.showMessageDialog(frame, 
                            "ASPETTA IL TUO TURNO PER GIOCARE", 
                            "NON E' IL TUO TURNO", JOptionPane.ERROR_MESSAGE);
                }
			    
			}
		});
        
        pausePanel.add(backButton);
        pausePanel.add(pauseButton);
        
        backgroungPlay.add(pausePanel, BorderLayout.PAGE_START);
        
        Dimension fillerDimension = new Dimension(200,200);
        Filler leftMarginBottomPlayer = new Filler(fillerDimension,fillerDimension,fillerDimension);
        bottomPlayerPanel.add(leftMarginBottomPlayer);
        bottomPlayerPanel.add(bottomCardPanel);
        bottomPlayerPanel.add(unoButton);
        
        fillerDimension = new Dimension(200,200);
        Filler leftMarginTopPlayer = new Filler(fillerDimension,fillerDimension,fillerDimension);
        Filler rightMarginTopPlayer = new Filler(fillerDimension,fillerDimension,fillerDimension);
        topPlayerPanel.add(leftMarginTopPlayer);
        topPlayerPanel.add(topCardPanel);
        topPlayerPanel.add(rightMarginTopPlayer);
        
        playPan.add(topPlayerPanel, BorderLayout.PAGE_START);
        playPan.add(rightPlayerPanel, BorderLayout.LINE_END);
        playPan.add(leftPlayerPanel, BorderLayout.LINE_START);
        playPan.add(bottomPlayerPanel, BorderLayout.PAGE_END);
        playPan.add(deckPanel, BorderLayout.CENTER);
        
        backgroungPlay.add(playPan, BorderLayout.CENTER);
        
        getContentPane().add(backgroungPlay, "card3");
    }
	/**
	 * An object used to synchronize access to resources.
	 */
    private Object lock = new Object();
    /**
     * Action listener to pause and resume the game. 
     * When the pause button is clicked, the game is paused or resumed.
     * The image on the button is also changed to reflect the current state of the game (paused or not paused).
     * When the game is paused, the lock object is notified to unblock any threads that may be waiting on it.
     */
    private java.awt.event.ActionListener pauseResume =
            new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                		controller.pauseGame();
                        ImageIcon pauseImage = new ImageIcon(getClass().getResource("/icons/pauseButton_48px.png"));
                        ImageIcon resumeImage = new ImageIcon(getClass().getResource("/icons/playButton_48px.png"));
                        pauseButton.setIcon(controller.getGame().getPaused()?resumeImage:pauseImage);
                    synchronized(lock) {
                        lock.notifyAll();
                    }
                }
            };
    /**
     * Gets the {@link DeckPanel} object associated with this JUnoFrame.
     * @return the {@link DeckPanel} object associated with this JUnoFrame
     */
    public DeckPanel getDeckPanel() {
	    return deckPanel;
	}
    /**
     * update method receives updates from the game and updates the game accordingly.
     * This method first checks if it is the AI's turn to play. If it is, the AI plays a card and if the game is over, the game is finished.
     * If it is not the AI's turn, the method checks if the player has lost the game. If the player has lost, the game is finished.
     * This method also adds an action listener to the uno button and updates the game's UI.
     * @param o the Observable object that triggered the update
     * @param arg the object that is passed as an argument
     */
    @Override
	public void update(Observable o, Object arg) {
	    if (controller.getCurrentPlayerId() != 0) {
	    	boolean gameOver = false;
	    	gameOver = controller.aiPlay();
	    	if (gameOver) {
	    		controller.getGame().winGame(controller.getGame().getPreviousPlayer());
	    		boolean winOrLoose = false;
	    		if (controller.getGame().getPreviousPlayer().getGameId() == 0) {
	    			winOrLoose = true;
				} 
	    		WinMessage gameOv = new WinMessage(winOrLoose);
	    		gameOv.setVisible(true);
	    		gameOv.getHomeButton().addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						controller.iWon();
						try {
		                    controller.saveToFile();
		                } catch (IOException e1) {
		                    e1.printStackTrace();
		                }
						homeAccountButtonActionPerformed(e);
						gameOv.dispose();
					}
				});
			}
		} else {
			if ( controller.checkLoose() ) {
	        	boolean winOrLoose = false;
				if (controller.getGame().getPreviousPlayer().getGameId() == 0) {
	    			winOrLoose = true;
				} 
				WinMessage gameOv = new WinMessage(winOrLoose);
				gameOv.setVisible(true);
	    		gameOv.getHomeButton().addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						controller.iWon();
						try {
		                    controller.saveToFile();
		                } catch (IOException e1) {
		                    e1.printStackTrace();
		                }
						homeAccountButtonActionPerformed(e);
						gameOv.dispose();
					}
				});
			}
		}
	    unoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unoButtonActionPerformed(e);
			}
		});
	    checkUno();
	    SwingUtilities.updateComponentTreeUI(this);
	}
    /**
     * This method sets the icon, border painting, content area filling, 
     * cursor and focus painting of the UNO button.
     */
	private void setUnoButton() {
		unoButton.setIcon(new ImageIcon(getClass().getResource("/icons/unoButton_200px.png"))); // NOI18N
        unoButton.setBorderPainted(false);
        unoButton.setContentAreaFilled(false);
        unoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        unoButton.setFocusPainted(false);
	}
	/**
	 * This method checks if the bottom player has only one card in their hand and
	 * if they haven't, sets the blank "UNO" button on the game screen.
	 */
	private void checkUno() {
		if ( !(controller.getUno()) ) {
			unoButton.setIcon(new ImageIcon(getClass().getResource("/icons/unoButton_200px.png"))); // NOI18N
		}
	}
	/**
	 * The unoButtonActionPerformed method is a private method that is called when the unoButton is clicked.
	 * 
	 * @param evt the ActionEvent object that is triggered when the unoButton is clickedt
	 */
	private void unoButtonActionPerformed(ActionEvent evt) {  
		controller.setUnoSafe(false);
		 // If the checkUno method in the controller object returns true
		  if ( (controller.checkUno()) ) {
		  	// Set the icon of the unoButton to the unoButtonClicked image
		  	unoButton.setIcon(new ImageIcon(getClass().getResource("/icons/unoButtonClicked_200px.png"))); // NOI18N
		  	
		  	// Set the unoSafe field in the controller object to true
		  	controller.setUnoSafe(true);
		  } 
	} 
	/**
	 * The logoActionPerformed method is a private method that is called when the logo button is clicked.
	 * 
	 * @param evt the ActionEvent object that is triggered when the logo button is clicked
	 */
	private void logoActionPerformed(ActionEvent evt) {                                     
		homeCard.getBackground().setVisible(false);
		accountCard.getBackground().setVisible(true);
    }                                    
	/**
	 * The backAccountButtonActionPerformed method is a private method that is called when the backAccountButton is clicked.
	 * 
	 * @param evt the ActionEvent object that is triggered when the backAccountButton is clicked
	 */
    private void backAccountButtonActionPerformed(ActionEvent evt) {                                                  
        accountCard.getBackground().setVisible(false);
        homeCard.getBackground().setVisible(true);
    }       
    /**
     * The homeAccountButtonActionPerformed method is a public method that is called when the homeAccountButton is clicked.
     * 
     * @param evt the ActionEvent object that is triggered when the homeAccountButton is clicked
     */
    public void homeAccountButtonActionPerformed(ActionEvent evt) { 
    	backgroungPlay.setVisible(false);        
    	controller.resetGame();
        accountCard.getBackground().setVisible(true);
    } 
    /**
     * This method is called when the play button is clicked. It checks if the list of accounts is empty. If it is not
     * empty, it retrieves the selected row from the table and gets the account ID from the table. It then creates a
     * new game with the selected account and displays the game screen. If the list of accounts is empty, it shows a
     * warning message.
     *
     * @param evt the event that triggers this method
     */
    private void playButtonActionPerformed(ActionEvent evt) {  
    	if (!controller.getAccounts().isEmpty()) {
            JTable table = accountCard.getTablePanel().getTable();
            if (table.getSelectedRow() != -1) {
                Integer id = (Integer) table.getValueAt(table.getSelectedRow(), 0);
                controller.createGame(controller.getAccount(id));
                createGameCard(id);
                
                accountCard.getBackground().setVisible(false);
                backgroungPlay.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, 
                        "SELEZIONA UN ACCOUNT PER INIZIARE A GIOCARE", 
                        "SELEZIONA UN ACCOUNT", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                    "DEVI INSERIRE UN ACCOUNT PER POTER GIOCARE", 
                    "INSERISCI UN ACCOUNT", JOptionPane.WARNING_MESSAGE);
        }
    }                                          
    /**
     * This method is called when the back game button is clicked. It prompts the user to confirm if they want to go
     * back to the previous screen. If the user confirms, it resets the current game and displays the previous screen.
     *
     * @param evt the event that triggers this method
     */
    private void backGameButtonActionPermormed(ActionEvent evt) {  
    	int result = JOptionPane.showConfirmDialog(this,
                "Sei sicuro di voler tornare indietro?\nPerderai i progressi di questa partita!",
                "Conferma uscita: ",
                JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
            	controller.resetGame();
            	accountCard.getBackground().setVisible(true);
                backgroungPlay.setVisible(false);
            }
    }
    /**
     * This method sets the frame settings for the application window. 
     * <p>It sets the default close operation to do nothing when the close button is clicked. 
     * It adds a window listener to prompt the user to confirm if they want to exit
     * the application when the close button is clicked. It sets the title of the window, packs the window, sets the
     * minimum size, maximizes the window, makes the window resizable, centers the window on the screen, and makes the
     * window visible.
     */
    private void setFrameSettings() {
    	JFrame frame = this;
    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
              public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(frame,
                	"Sei sicuro di voler uscire?",
                    "Conferma uscita: ",
                    JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              }
            });
        setTitle("JUno");
		pack();
		setMinimumSize(new Dimension(1500, 900));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

