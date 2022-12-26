package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.Box.*;
import javax.swing.event.*;

import controller.Controller;
import model.Game;
import model.Player;
import model.Game.Flipped;

/**
 *
 * @author Simone
 */
public class JUnoFrame extends JFrame implements Observer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 481508119037659069L;
	/////////////////////////////////////////////////////////////////
	private HomeCard homeCard;
	private AccountCard accountCard;
	
    private PanelGradient backgroungPlay;
    ////////////////////////////////////
    private JPanel pausePanel;
    private JButton pauseButton;
    private JButton backButton;
    private JPanel playPan;
    private DeckPanel deckPanel;
    private JPanel topPlayerPanel;
    private PlayerPanel topCardPanel;
    private PlayerPanel rightPlayerPanel;
    private PlayerPanel leftPlayerPanel;
    private PlayerPanel bottomCardPanel;
    private List<PlayerPanel> playerList;
    private JPanel bottomPlayerPanel;
    private JButton unoButton;
    ////////////////////////////////////
    private JLabel selectColor;
    private JPanel chooseColorPanel;
    private JButton yellowButton;
    private JButton redButton;
    private JButton blueButton;
    private JButton greenButton;
    
    private Controller controller;
    
    private Timer aiPlayerGuiUpdate = new Timer(Game.getSecAiPlay(), (ae)->{
        update(null, null);
        SwingUtilities.updateComponentTreeUI(this);
    });
    
	// End of variables declaration
	
	/**
     * Creates new form JUnoFrame
     */
    public JUnoFrame() {
    	controller = new Controller();
    	controller.createAccountList();
        initComponents();
    }

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

    @SuppressWarnings("deprecation")
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
        
        controller.getGame().addObserver(topCardPanel);
        controller.getGame().addObserver(leftPlayerPanel);
        controller.getGame().addObserver(rightPlayerPanel);
        controller.getGame().addObserver(bottomCardPanel);
        controller.getGame().addObserver(deckPanel);
        
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
//        chooseColorPanel = new JPanel();
        unoButton = new JButton();
    	
    	backgroungPlay.setLayout(new BorderLayout());
    	
    	update(bottomCardPanel.getPlayer(), null);

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

//        setChooseColorPanel();
        setUnoButton();
        
        
        deckPanel.getDeckButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Current player id: "+controller.getGame().getCurrentPlayer().getGameId());
			    if ( controller.getCurrentPlayerId() == 0 ) {
			    	bottomCardPanel.drawCard(controller.getDeck().getCard(Flipped.FLIPPED));
	                controller.getGame().nextTurn();
	                update(bottomCardPanel.getPlayer(), null);
	                setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, 
                        "Wait your turn!", 
                        "Not your turn!", JOptionPane.ERROR_MESSAGE);
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
    
    private Object lock = new Object();
    private java.awt.event.ActionListener pauseResume =
            new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                		controller.getGame().setPaused(!controller.getGame().getPaused());
                        ImageIcon pauseImage = new ImageIcon(getClass().getResource("/icons/pauseButton_48px.png"));
                        ImageIcon resumeImage = new ImageIcon(getClass().getResource("/icons/playButton_48px.png"));
                        pauseButton.setIcon(controller.getGame().getPaused()?resumeImage:pauseImage);
                    synchronized(lock) {
                        lock.notifyAll();
                    }
                }
            };
    
    public DeckPanel getDeckPanel() {
	    return deckPanel;
	}
    
    @Override
	public void update(Observable o, Object arg) {
    	deckPanel.getDiscardLabel().setIcon(controller.getLastDiscard().getFaceCard());
    	aiPlayerGuiUpdate.setRepeats(false);
    	System.out.println("DI CHI E' IL TURNOOOOOOO???"+controller.getCurrentPlayerAlias());
        int currentPlayerId = controller.getCurrentPlayerId();
	    if (currentPlayerId != 0) {
	    	boolean gameOver = controller.aiPlay();
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
		                    controller.saveToFile(new File("saves/saves.txt"));
		                } catch (IOException e1) {
		                    e1.printStackTrace();
		                }
						homeAccountButtonActionPerformed(e);
						gameOv.dispose();
					}
				});
			} else {
				aiPlayerGuiUpdate.start();
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
		                    controller.saveToFile(new File("saves/saves.txt"));
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
    
	private void setUnoButton() {
		unoButton.setIcon(new ImageIcon(getClass().getResource("/icons/unoButton_200px.png"))); // NOI18N
        unoButton.setBorderPainted(false);
        unoButton.setContentAreaFilled(false);
        unoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        unoButton.setFocusPainted(false);
	}
	
	private void checkUno() {
		if ( !(controller.getUno()) ) {
			unoButton.setIcon(new ImageIcon(getClass().getResource("/icons/unoButton_200px.png"))); // NOI18N
		}
	}
	
	private void unoButtonActionPerformed(ActionEvent evt) {  
		controller.setUnoSafe(false);
		if ( (controller.checkUno()) ) {
			unoButton.setIcon(new ImageIcon(getClass().getResource("/icons/unoButtonClicked_200px.png"))); // NOI18N
			controller.setUnoSafe(true);
		} 
	} 
	
	private void logoActionPerformed(ActionEvent evt) {                                     
		homeCard.getBackground().setVisible(false);
		accountCard.getBackground().setVisible(true);
    }                                    

    private void backAccountButtonActionPerformed(ActionEvent evt) {                                                  
        accountCard.getBackground().setVisible(false);
        homeCard.getBackground().setVisible(true);
    }       
    
    public void homeAccountButtonActionPerformed(ActionEvent evt) { 
    	backgroungPlay.setVisible(false);        
    	controller.resetGame();
        accountCard.getBackground().setVisible(true);
    } 
    
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

