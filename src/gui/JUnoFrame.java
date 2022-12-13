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

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.Controller;
import model.Game;

/**
 *
 * @author Simone
 */
public class JUnoFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 481508119037659069L;
	/////////////////////////////////////////////////////////////////
	private PanelGradient backgroungHome;
    private PanelGradient backgroungAccount;
    private PanelGradient backgroungPlay;
    /////////////////////////////////////
	private JPanel homeMarginPanel;
    private JPanel accountMarginPanel;
    private JPanel accountPanel;
    
    private JLabel welcome;
    private JPanel welcomePanel;
    private JButton logo;
    private JLabel cliccaLogo;
	////////////////////////////////////
	private Box.Filler topMargin;
	private Box.Filler topMargin1;
    
    private JTextField accountTextField;
    private JButton addAccountButton;
    private JButton backAccountButton;
    
    private JLabel createLabel;
    private JPanel insertPanel;
    private TablePanel tablePanel;
    private JButton playButton;
    ////////////////////////////////////
    private JPanel pausePanel;
    private JButton pauseButton;
    private JButton backButton;
    private JPanel playPan;
    private DeckPanel deckPanel;
    private PlayerPanel topPlayerPanel;
    private PlayerPanel rightPlayerPanel;
    private PlayerPanel leftPlayerPanel;
    private PlayerPanel bottomCardPanel;
    private JPanel bottomPlayerPanel;
    private JButton unoButton;
    ////////////////////////////////////
    private Box.Filler rightMargin;
    private Box.Filler rightMargin1;
    private Box.Filler rightMargin2;
    private Box.Filler rightMarginLogo;
    ////////////////////////////////////
    private Box.Filler leftMargin;
    private Box.Filler leftMargin1;
    private Box.Filler leftMargin2;
    private Box.Filler leftMarginLogo;
    ////////////////////////////////////
    private Box.Filler bottomMargin;
    private Box.Filler bottomMargin1;
    private Box.Filler bottomMargin2;
    private Box.Filler bottomMargin3;
    ////////////////////////////////////
    private JLabel selectAccountLabel;
    private JPanel selectPanel;
    ////////////////////////////////////
    private JLabel selectColor;
    private JPanel chooseColorPanel;
    private JButton yellowButton;
    private JButton redButton;
    private JButton blueButton;
    private JButton greenButton;
    
    private Controller controller;
    
    private Timer aiPlayerGuiUpdate = new Timer(Game.getSecAiPlay(), (ae)->{
        topPlayerPanel.updateEnemyCard(controller.getGame().getTopPlayer().getHandCards());
        rightPlayerPanel.updateEnemyCard(controller.getGame().getRightPlayer().getHandCards());
        leftPlayerPanel.updateEnemyCard(controller.getGame().getLeftPlayer().getHandCards());
        deckPanel.getDiscardLabel().setIcon(controller.getDiscard().getLastDiscard().getFaceCard());
        SwingUtilities.updateComponentTreeUI(this);
        updateCurrentPlayer(controller);
    });
    
	private AccountListener accountListener;
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
        
        createHomeCard();
        getContentPane().add(backgroungHome, "card1");

        
        createAccountCard();
        getContentPane().add(backgroungAccount, "card2");

        pack();
        setLocationRelativeTo(null);
    }                      

    private void createHomeCard() {
    	backgroungHome = new PanelGradient();
    	homeMarginPanel = new JPanel();
        rightMargin = new Box.Filler(new Dimension(100, 0), new Dimension(100, 0), new Dimension(100, 32767));
        leftMargin = new Box.Filler(new Dimension(100, 0), new Dimension(100, 0), new Dimension(100, 32767));
        topMargin = new Box.Filler(new Dimension(0, 100), new Dimension(0, 100), new Dimension(0, 100));
        bottomMargin = new Box.Filler(new Dimension(0, 100), new Dimension(0, 100), new Dimension(0, 100));
        welcomePanel = new JPanel();
        welcome = new JLabel();
        cliccaLogo = new JLabel();
        logo = new JButton();
        rightMarginLogo = new Box.Filler(new Dimension(330, 0), new Dimension(330, 0), new Dimension(660, 0));
        leftMarginLogo = new Box.Filler(new Dimension(330, 0), new Dimension(330, 0), new Dimension(660, 0));
        
    	backgroungHome.setLayout(new BorderLayout());

        homeMarginPanel.setOpaque(false);
        homeMarginPanel.setLayout(new BorderLayout());

        homeMarginPanel.add(rightMargin, BorderLayout.LINE_END);
        homeMarginPanel.add(leftMargin, BorderLayout.LINE_START);
        homeMarginPanel.add(topMargin, BorderLayout.PAGE_START);
        homeMarginPanel.add(bottomMargin, BorderLayout.PAGE_END);

        welcomePanel.setOpaque(false);
        welcomePanel.setLayout(new BorderLayout());

        welcome.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        welcome.setForeground(new Color(0, 0, 0));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_hello_96px.png"))); // NOI18N
        welcome.setText("Benvenuto!");
        welcome.setHorizontalTextPosition(SwingConstants.RIGHT);
        welcomePanel.add(welcome, BorderLayout.PAGE_START);

        cliccaLogo.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        cliccaLogo.setForeground(new Color(0, 0, 0));
        cliccaLogo.setHorizontalAlignment(SwingConstants.CENTER);
        cliccaLogo.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_natural_user_interface_2_96px.png"))); // NOI18N
        cliccaLogo.setText("clicca il logo per iniziare");
        welcomePanel.add(cliccaLogo, BorderLayout.PAGE_END);

        logo.setIcon(new ImageIcon(getClass().getResource("/icons/Uno_logo_PNG.png"))); // NOI18N
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
        logo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                logoActionPerformed(evt);
            }
        });
        welcomePanel.add(logo, BorderLayout.CENTER);
        welcomePanel.add(rightMarginLogo, BorderLayout.LINE_END);
        welcomePanel.add(leftMarginLogo, BorderLayout.LINE_START);
        homeMarginPanel.add(welcomePanel, BorderLayout.CENTER);
        backgroungHome.add(homeMarginPanel, BorderLayout.CENTER);
	}
    
    private void createAccountCard() {
    	backgroungAccount = new PanelGradient();
        accountMarginPanel = new JPanel();
        rightMargin1 = new Box.Filler(new Dimension(100, 0), new Dimension(100, 0), new Dimension(100, 32767));
        leftMargin1 = new Box.Filler(new Dimension(100, 0), new Dimension(100, 0), new Dimension(100, 32767));
        topMargin1 = new Box.Filler(new Dimension(0, 140), new Dimension(0, 140), new Dimension(0, 140));
        bottomMargin1 = new Box.Filler(new Dimension(0, 140), new Dimension(0, 140), new Dimension(0, 140));
        accountPanel = new JPanel();
        insertPanel = new JPanel();
        createLabel = new JLabel();
        accountTextField = new JTextField();
        addAccountButton = new JButton();
        accountListener = null;
        backAccountButton = new JButton();
        bottomMargin2 = new Box.Filler(new Dimension(0, 30), new Dimension(0, 30), new Dimension(0, 30));
        tablePanel = new TablePanel();
        selectPanel = new JPanel();
        selectAccountLabel = new JLabel();
        playButton = new JButton();
        bottomMargin3 = new Box.Filler(new Dimension(0, 40), new Dimension(0, 40), new Dimension(0, 40));
        leftMargin2 = new Box.Filler(new Dimension(400, 0), new Dimension(400, 0), new Dimension(400, 0));
        rightMargin2 = new Box.Filler(new Dimension(400, 0), new Dimension(400, 0), new Dimension(400, 0));
       
        backgroungAccount.setLayout(new BorderLayout());

        accountMarginPanel.setOpaque(false);
        accountMarginPanel.setLayout(new BorderLayout());
        accountMarginPanel.add(rightMargin1, BorderLayout.LINE_END);
        accountMarginPanel.add(leftMargin1, BorderLayout.LINE_START);
        accountMarginPanel.add(topMargin1, BorderLayout.PAGE_START);
        accountMarginPanel.add(bottomMargin1, BorderLayout.PAGE_END);

        accountPanel.setOpaque(false);
        accountPanel.setLayout(new BorderLayout());

        insertPanel.setOpaque(false);
        insertPanel.setLayout(new BorderLayout());

        createLabel.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        createLabel.setForeground(new Color(0, 0, 0));
        createLabel.setHorizontalAlignment(SwingConstants.CENTER);
        createLabel.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_compose_48px.png"))); // NOI18N
        createLabel.setText("Crea un account:");
        createLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        insertPanel.add(createLabel, BorderLayout.PAGE_START);

        accountTextField.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        accountTextField.setHorizontalAlignment(JTextField.CENTER);
        accountTextField.setToolTipText("Inserisci un nome per il tuo account");
        setAccountListener(new AccountListener() {
			@Override
			public void accountEventOccurred(AccountEvent e) {
				controller.addAccount(e);
				try {
                    controller.saveToFile(new File("saves/saves.txt"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
				updateAccountGuiList(controller, tablePanel);
			}
		});
        AbstractAction action = new AbstractAction()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 21118479149967169L;

			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	if (checkBtn()) {
		    		String accountName = accountTextField.getText();
		    		AccountEvent ev = new AccountEvent(this, accountName, 0);
		    		accountListener.accountEventOccurred(ev);
		    		accountTextField.setText(null);
		    		setVisible(true);
			    }
			}
		};
		addAccountButton.setEnabled(false);
		accountTextField.addActionListener(action);
        insertPanel.add(accountTextField, BorderLayout.CENTER);
        accountTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                checkBtn();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkBtn();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                checkBtn();
            }
        });

        
        
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        backAccountButton.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_home_48px.png"))); // NOI18N
        backAccountButton.setToolTipText("Torna alla Homepage");
        backAccountButton.setBorderPainted(false);
        backAccountButton.setContentAreaFilled(false);
        backAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backAccountButton.setFocusPainted(false);
        backAccountButton.setHorizontalTextPosition(SwingConstants.CENTER);
        backAccountButton.setIconTextGap(0);
        backAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backAccountButtonActionPerformed(evt);
            }
        });
        insertPanel.add(backAccountButton, BorderLayout.LINE_START);

        bottomMargin2.setBackground(new Color(204, 255, 255));
        insertPanel.add(bottomMargin2, BorderLayout.PAGE_END);

        addAccountButton.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_add_48px.png"))); // NOI18N
        addAccountButton.setToolTipText("Aggiungi account");
        addAccountButton.setBorderPainted(false);
        addAccountButton.setContentAreaFilled(false);
        addAccountButton.setCursor(handCursor);
        addAccountButton.setFocusPainted(false);
        addAccountButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addAccountButton.setIconTextGap(0);
        addAccountButton.setPressedIcon(new ImageIcon(getClass().getResource("/icons/icons8_Plus_48px.png"))); // NOI18N
        insertPanel.add(addAccountButton, BorderLayout.LINE_END);

        accountPanel.add(insertPanel, BorderLayout.PAGE_START);

        tablePanel.setBackground(new Color(255, 204, 255));
        tablePanel.setData(controller.getAccounts());

        accountPanel.add(tablePanel, BorderLayout.CENTER);

        selectPanel.setBackground(new Color(204, 204, 255));
        selectPanel.setLayout(new BorderLayout());

        selectAccountLabel.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        selectAccountLabel.setForeground(new Color(0, 0, 0));
        selectAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectAccountLabel.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_checked_checkbox_48px.png"))); // NOI18N
        selectAccountLabel.setText("Seleziona un account e...");
        selectAccountLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        selectPanel.add(selectAccountLabel, BorderLayout.PAGE_START);

        playButton.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        playButton.setForeground(new Color(0, 0, 0));
        playButton.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_play_button_circled_96px.png"))); // NOI18N
        playButton.setText("Gioca");
        playButton.setToolTipText("Clicca per iniziare a giocare");
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setCursor(handCursor);
        playButton.setFocusPainted(false);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });
        selectPanel.add(playButton, BorderLayout.CENTER);

        bottomMargin3.setBackground(new Color(204, 255, 255));
        selectPanel.add(bottomMargin3, BorderLayout.PAGE_END);

        leftMargin2.setBackground(new Color(255, 255, 255));
        selectPanel.add(leftMargin2, BorderLayout.LINE_START);

        rightMargin2.setBackground(new Color(255, 255, 255));
        selectPanel.add(rightMargin2, BorderLayout.LINE_END);

        accountPanel.add(selectPanel, BorderLayout.PAGE_END);

        accountMarginPanel.add(accountPanel, BorderLayout.CENTER);

        backgroungAccount.add(accountMarginPanel, BorderLayout.CENTER);
    }

    private boolean checkBtn() {
        boolean value = !accountTextField.getText().trim().isEmpty();
        addAccountButton.setEnabled(value);
        return value;
    }
    
    public void setAccountListener(AccountListener accountListener) {
    	this.accountListener = accountListener;
    }
    
    public void updateAccountGuiList(Controller controller, TablePanel tablePanel) {
		tablePanel.refresh();
    }
    
    private void createGameCard(int id) {
    	backgroungPlay = new PanelGradient();
    	
        pausePanel = new JPanel();
        pauseButton = new JButton();
        backButton = new JButton();
        
        playPan = new JPanel();
//        deckPanel = new DeckPanel(controller, controller.getDiscard().getLastDiscard(), "Deck");
//        topPlayerPanel = new PlayerPanel(controller, this, controller.getGame().getTopPlayer(), -30, 15);
//        leftPlayerPanel = new PlayerPanel(controller, this, controller.getGame().getRightPlayer(), -50, 15);
//        rightPlayerPanel = new PlayerPanel(controller, this, controller.getGame().getRightPlayer(), -50, 15);
//        bottomCardPanel = new PlayerPanel(controller, this, controller.getGame().getTopPlayer(), -30, 15);
        bottomPlayerPanel = new JPanel();
        chooseColorPanel = new JPanel();
        greenButton = new JButton();
        blueButton = new JButton();
        redButton = new JButton();
        yellowButton = new JButton();
        selectColor = new JLabel();
        unoButton = new JButton();
    	
    	backgroungPlay.setLayout(new BorderLayout());

    	Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        Dimension prefSize = new Dimension(1280, 48);
    	
        pausePanel.setMaximumSize(prefSize);
        pausePanel.setMinimumSize(prefSize);
        pausePanel.setPreferredSize(prefSize);
        pausePanel.setOpaque(false);
        pausePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, -2));

        pauseButton.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_pause_button_48px.png"))); // NOI18N
        pauseButton.setContentAreaFilled(false);
        pauseButton.setCursor(handCursor);

        backButton.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_return_48px.png"))); // NOI18N
        backButton.setContentAreaFilled(false);
        backButton.setCursor(handCursor);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backGameButtonActionPermormed(evt);
            }
        });
        
        pausePanel.add(backButton);
        pausePanel.add(pauseButton);
        
        backgroungPlay.add(pausePanel, BorderLayout.PAGE_START);

        playPan.setOpaque(false);
        playPan.setLayout(new BorderLayout());

//        topPlayerPanel.setPreferredSize(new Dimension(1280, 200));
//        leftPlayerPanel.setPreferredSize(new Dimension(450, 547));
//        rightPlayerPanel.setPreferredSize(new Dimension(450, 547));
//        bottomPlayerPanel.setPreferredSize(new Dimension(1280, 200));
//        bottomPlayerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
//
//        setChooseColorPanel();
//        setUnoButton();
//        bottomPlayerPanel.add(unoButton);
//        bottomPlayerPanel.add(chooseColorPanel);
//        bottomPlayerPanel.add(bottomCardPanel);
        
//        GroupLayout bottomCardPanelLayout = new GroupLayout(bottomCardPanel);
//        bottomCardPanel.setLayout(bottomCardPanelLayout);
//        bottomCardPanelLayout.setHorizontalGroup(
//            bottomCardPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
//            .addGap(0, 692, Short.MAX_VALUE)
//        );
//        bottomCardPanelLayout.setVerticalGroup(
//            bottomCardPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
//            .addGap(0, 157, Short.MAX_VALUE)
//        );

//        GroupLayout leftPlayerPanelLayout = new GroupLayout(leftPlayerPanel);
//        leftPlayerPanel.setLayout(leftPlayerPanelLayout);
//        leftPlayerPanelLayout.setHorizontalGroup(
//            leftPlayerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
//            .addGap(0, 450, Short.MAX_VALUE)
//        );
//        leftPlayerPanelLayout.setVerticalGroup(
//            leftPlayerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
//            .addGap(0, 347, Short.MAX_VALUE)
//        );

//        GroupLayout rightPlayerPanelLayout = new GroupLayout(rightPlayerPanel);
//        rightPlayerPanel.setLayout(rightPlayerPanelLayout);
//        rightPlayerPanelLayout.setHorizontalGroup(
//            rightPlayerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
//            .addGap(0, 450, Short.MAX_VALUE)
//        );
//        rightPlayerPanelLayout.setVerticalGroup(
//            rightPlayerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
//            .addGap(0, 347, Short.MAX_VALUE)
//        );

//        playPan.add(topPlayerPanel, BorderLayout.PAGE_START);
//        playPan.add(rightPlayerPanel, BorderLayout.LINE_END);
//        playPan.add(leftPlayerPanel, BorderLayout.LINE_START);
//        playPan.add(bottomPlayerPanel, BorderLayout.PAGE_END);
//        playPan.add(deckPanel, BorderLayout.CENTER);
        
        backgroungPlay.add(playPan, BorderLayout.CENTER);

    }
    
	private void setUnoButton() {
		unoButton.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_uno_200px_2.png"))); // NOI18N
        unoButton.setBorderPainted(false);
        unoButton.setContentAreaFilled(false);
        unoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        unoButton.setFocusPainted(false);
        unoButton.setPressedIcon(new ImageIcon(getClass().getResource("/icons/icons8_uno_200px_3.png"))); // NOI18N
	}

	private void setChooseColorPanel() {
		chooseColorPanel.setMaximumSize(new Dimension(200, 200));
        chooseColorPanel.setMinimumSize(new Dimension(200, 200));
        chooseColorPanel.setOpaque(false);

        greenButton.setForeground(new Color(0, 204, 0));
        greenButton.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_green_square_48px.png"))); // NOI18N
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
        blueButton.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_blue_square_48px.png"))); // NOI18N
        blueButton.setBorderPainted(false);
        blueButton.setContentAreaFilled(false);
        blueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        blueButton.setFocusPainted(false);
        blueButton.setMaximumSize(new Dimension(60, 40));
        blueButton.setMinimumSize(new Dimension(60, 40));
        blueButton.setPreferredSize(new Dimension(60, 40));

        redButton.setForeground(new Color(204, 0, 0));
        redButton.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_red_square_48px.png"))); // NOI18N
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
        yellowButton.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_yellow_square_48px.png"))); // NOI18N
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

        selectColor.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        selectColor.setForeground(new Color(0, 0, 0));
        selectColor.setHorizontalAlignment(SwingConstants.CENTER);
        selectColor.setIcon(new ImageIcon(getClass().getResource("/icons/icons8_Color_Swatch_48px.png"))); // NOI18N
        selectColor.setText("Seleziona Colore");
        selectColor.setAutoscrolls(true);
        selectColor.setIconTextGap(2);

        GroupLayout chooseColorPanelLayout = new GroupLayout(chooseColorPanel);
        chooseColorPanel.setLayout(chooseColorPanelLayout);
        chooseColorPanelLayout.setHorizontalGroup(
            chooseColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(chooseColorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(GroupLayout.Alignment.TRAILING, chooseColorPanelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(chooseColorPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(blueButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(yellowButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(chooseColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(greenButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(redButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );
        chooseColorPanelLayout.setVerticalGroup(
            chooseColorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, chooseColorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectColor, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(chooseColorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(blueButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(redButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(chooseColorPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(greenButton, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(yellowButton, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );
	}

	private void logoActionPerformed(ActionEvent evt) {                                     
        backgroungHome.setVisible(false);
        backgroungAccount.setVisible(true);
    }                                    

    private void logoMouseEntered(MouseEvent evt) {                                  
        logo.setIcon(new ImageIcon(getClass().getResource("/icons/Uno_logo_PNG-onButton.png")));
    }                                 

    private void logoMouseExited(MouseEvent evt) {                                 
        logo.setIcon(new ImageIcon(getClass().getResource("/icons/Uno_logo_PNG.png")));
    }                                

    private void backAccountButtonActionPerformed(ActionEvent evt) {                                                  
        backgroungAccount.setVisible(false);
        backgroungHome.setVisible(true);
    }                                                 

    private void playButtonActionPerformed(ActionEvent evt) {  
    	if (!controller.getAccounts().isEmpty()) {
            JTable table = tablePanel.getTable();
            if (table.getSelectedRow() != -1) {
                Integer id = (Integer) table.getValueAt(table.getSelectedRow(), 0);
                createGameCard(id);
                getContentPane().add(backgroungPlay, "card3");
                backgroungAccount.setVisible(false);
                backgroungPlay.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, 
                        "SELECT AN ACCOUNT TO PLAY!", 
                        "SELECT ACCOUNT", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                    "INSERT AN ACCOUNT FIRST!", 
                    "INSERT ACCOUNT", JOptionPane.WARNING_MESSAGE);
        }
    }                                          

    private void backGameButtonActionPermormed(ActionEvent evt) {                                            
        backgroungAccount.setVisible(true);
        backgroungPlay.setVisible(false);
    }                                           

    private void greenButtonActionPerformed(ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void redButtonActionPerformed(ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void yellowButtonActionPerformed(ActionEvent evt) {                                             
        // TODO add your handling code here:
    }     
    
    public void updateCurrentPlayer(Controller controller) {
        aiPlayerGuiUpdate.setRepeats(false);
	    switch (controller.getGame().getCurrentPlayer().getGameId()) {
            case 0 -> {
            	bottomCardPanel.setPlayerTurn();
                rightPlayerPanel.clearTurn();
                topPlayerPanel.clearTurn();
                leftPlayerPanel.clearTurn();
                }
            case 1 -> {
            	rightPlayerPanel.setPlayerTurn();
            	bottomCardPanel.clearTurn();
                topPlayerPanel.clearTurn();
                leftPlayerPanel.clearTurn();
                controller.getGame().AiPlay(controller.getGame().getDiscard().getLastDiscard());
                
                aiPlayerGuiUpdate.start();
            }
            case 2 -> {
            	topPlayerPanel.setPlayerTurn();
            	bottomCardPanel.clearTurn();
                rightPlayerPanel.clearTurn();
                leftPlayerPanel.clearTurn();
                System.out.println("Last rejected: "+controller.getGame().getDiscard().getLastDiscard());
                controller.getGame().AiPlay(controller.getGame().getDiscard().getLastDiscard());
                
                aiPlayerGuiUpdate.start();
            }
            case 3 -> {
            	leftPlayerPanel.setPlayerTurn();
            	bottomCardPanel.clearTurn();
                rightPlayerPanel.clearTurn();
                topPlayerPanel.clearTurn();
                System.out.println("Last rejected: "+controller.getGame().getDiscard().getLastDiscard());
                controller.getGame().AiPlay(controller.getGame().getDiscard().getLastDiscard());

                aiPlayerGuiUpdate.start();
            }
        }
	    topPlayerPanel.updateEnemyCard(controller.getGame().getTopPlayer().getHandCards());
        rightPlayerPanel.updateEnemyCard(controller.getGame().getRightPlayer().getHandCards());
        leftPlayerPanel.updateEnemyCard(controller.getGame().getLeftPlayer().getHandCards());
        bottomCardPanel.setCards(controller.getGame().getBottomPlayer().getHandCards());
//        deckPanel.updateLabelTurn();
//        deckPanel.updateLabelDiscard();
	    SwingUtilities.updateComponentTreeUI(this);
    }

    public DeckPanel getDeckPanel() {
	    return deckPanel;
	}
    
    private void setFrameSettings() {
    	JFrame frame = this;
    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
              public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(frame,
                    "Do you want to Exit ?", "Exit Confirmation : ",
                    JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              }
            });
        setTitle("JUno");
        setMaximumSize(new Dimension(1920, 1080));
        setMinimumSize(new Dimension(1280, 720));
        setPreferredSize(new Dimension(1280, 720));
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

