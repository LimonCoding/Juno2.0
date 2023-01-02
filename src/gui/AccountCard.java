package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

import controller.Controller;
/**
 * This class represents an account card that allows the user to select or create an account and play the game.
 * The account card consists of a panel for creating a new account, a panel that contains a table for displaying and selecting an existing account,
 * and button to start new game with the selected account.
 * 
 * The class also has a reference to a {@link Controller} object that is used to communicate with the model.
 * @author Simone
 */
public class AccountCard {
	/** Reference to the {@code Controller} object. */
	private Controller controller;
	/** Reference of the main frame of the application. */
	private JFrame frame;
	/** A panel with margins that is used to add some space around the account card. */
	private MarginPanel accountMarginPanel;
	/** A panel with a gradient background that is used as the background for the account card. */
	private PanelGradient backgroungAccount;
	/** A filler component used to add some space to the right of the select panel. */
    private Box.Filler rightMarginSelectPanel;
    /** A filler component used to add some space to the left of the select panel. */
    private Box.Filler leftMarginSelectPanel;
    /** A filler component used to add some space to the bottom of the insert panel. */
    private Box.Filler bottomMarginInsertPanel;
    /** A JLabel representing the text (Select an account) in the selectPanel panel. */
    private JLabel selectAccountLabel;
    /** A JPanel that allows the user, after selecting an account, to start playing the game. */
    private JPanel selectPanel;
    /**
     * A JPanel that contains {@code insertPanel}, {@code tablePanel}, {@code selectPanel} and 
     * allows the user to select an existing account or 
     * create a new account by entering their desired account name and play with it.
     */
    private JPanel accountPanel;
    /** A JTextField that allows the user to enter a new account name. */
    private JTextField accountTextField;
    /** A JButton that, when clicked, will add a new account using the name entered in accountTextField. */
    private JButton addAccountButton;
    /** A JButton that, when clicked, will navigate the user back to the homepage. */
    private JButton backAccountButton;
    /** A JLabel that representing the text (Create an account) in the insertPanel. */
    private JLabel createLabel;
    /** 
     * A JPanel that contains {@code createLabel}, {@code accountTextField}, {@code addAccountButton}
     * and allows the user to create a new account by entering their desired account name.
     */
    private JPanel insertPanel;
    /** A TablePanel that displays a list of accounts. */
    private TablePanel tablePanel;
    /** A JButton that, when clicked, will start a game if an account was selected. */
    private JButton playButton;
    /** An AbstractAction that will be triggered when the user presses the enter key in accountTextField. */
    private AbstractAction action;
    /** A Cursor object that represents a hand cursor and will be used to change the cursor when hovering over certain buttons */
    private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    /** An object that implements the AccountListener interface and is used to handle events related to the creation of new accounts. */
    private AccountListener accountListener;
    /**
     * Constructor for the {@code AccountCard} class.
     * Initializes the account card with a given {@code Controller} object and {@code JFrame} object.
     * @param controller the {@code Controller} object that manages the data for the account card
     * @param frame the {@code JFrame} object representing the main frame of the application
     */
	AccountCard(Controller controller, JFrame frame) {
		this.controller = controller;
		this.frame = frame;
		
		accountListener = null;
		
    	backgroungAccount = new PanelGradient();
    	backgroungAccount.setLayout(new BorderLayout());
    	
        accountMarginPanel = new MarginPanel();
        
        insertPanel = new JPanel();
        tablePanel = new TablePanel();
        selectPanel = new JPanel();
        
        createInsertPanel();
        createTablePanel();
        createSelectPanel();
        
        accountPanel = new JPanel();
        accountPanel.setOpaque(false);
        accountPanel.setLayout(new BorderLayout());
        
        accountPanel.add(insertPanel, BorderLayout.PAGE_START);
        accountPanel.add(tablePanel, BorderLayout.CENTER);
        accountPanel.add(selectPanel, BorderLayout.PAGE_END);

        accountMarginPanel.add(accountPanel, BorderLayout.CENTER);

        backgroungAccount.add(accountMarginPanel, BorderLayout.CENTER);
    }
	/**
	 * Initializes the insert panel and its components, including a label, text field, and buttons.
	 * The panel allows the user to create a new account by entering a name into the text field and clicking
	 * the "Add Account" button. The "Back" button returns the user to the Homepage.
	 *
	 */
	private void createInsertPanel() {
		createLabel = new JLabel();
        accountTextField = new JTextField();
        addAccountButton = new JButton();
        backAccountButton = new JButton();
        bottomMarginInsertPanel = new Box.Filler(new Dimension(0, 30), new Dimension(0, 30), new Dimension(0, 30));
        
		
		insertPanel.setOpaque(false);
        insertPanel.setLayout(new BorderLayout());

        createLabel.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        createLabel.setForeground(new Color(0, 0, 0));
        createLabel.setHorizontalAlignment(SwingConstants.CENTER);
        createLabel.setIcon(new ImageIcon(getClass().getResource("/icons/compose_48px.png"))); // NOI18N
        createLabel.setText("Crea un account:");
        createLabel.setHorizontalTextPosition(SwingConstants.RIGHT);

        accountTextField.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        accountTextField.setHorizontalAlignment(JTextField.CENTER);
        accountTextField.setToolTipText("Inserisci un nome per il tuo account");
        loadAccounts();
        setAccountListener(new AccountListener() {
			@Override
			public void accountEventOccurred(AccountEvent e) {
				controller.addAccount(e.getAlias(), e.getLevel());
				try {
                    controller.saveToFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
				updateAccountGuiList(controller, tablePanel);
			}
		});
        action = new AbstractAction()
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
		    		AccountEvent ev = new AccountEvent(this, accountName);
		    		accountListener.accountEventOccurred(ev);
		    		accountTextField.setText(null);
			    } else {
					JOptionPane.showMessageDialog(frame, 
		                    "INSERISCI UN NOME VALIDO PER IL TUO ACCOUNT", 
		                    "INSERISCI NOME", JOptionPane.WARNING_MESSAGE);
				}
			}
		};
		accountTextField.addActionListener(action);
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
        
        backAccountButton.setIcon(new ImageIcon(getClass().getResource("/icons/home_48px.png"))); // NOI18N
        backAccountButton.setToolTipText("Torna alla Homepage");
        backAccountButton.setBorderPainted(false);
        backAccountButton.setContentAreaFilled(false);
        backAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backAccountButton.setFocusPainted(false);
        backAccountButton.setHorizontalTextPosition(SwingConstants.CENTER);
        backAccountButton.setIconTextGap(0);
        
        addAccountButton.setIcon(new ImageIcon(getClass().getResource("/icons/add_48px.png"))); // NOI18N
        addAccountButton.setToolTipText("Aggiungi account");
        addAccountButton.setBorderPainted(false);
        addAccountButton.setContentAreaFilled(false);
        addAccountButton.setCursor(handCursor);
        addAccountButton.setFocusPainted(false);
        addAccountButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addAccountButton.setIconTextGap(0);
        addAccountButton.setPressedIcon(new ImageIcon(getClass().getResource("/icons/addClicked_48px.png"))); // NOI18N
        addAccountButton.addActionListener(action);
        
        insertPanel.add(createLabel, BorderLayout.PAGE_START);
        insertPanel.add(accountTextField, BorderLayout.CENTER);
        insertPanel.add(backAccountButton, BorderLayout.WEST);
        insertPanel.add(addAccountButton, BorderLayout.EAST);
        insertPanel.add(bottomMarginInsertPanel, BorderLayout.PAGE_END);
	}
	/**
	 * Initializes the table panel and sets its layout and background color.
	 * The table panel displays a list of accounts.
	 *
	 * @param controller the controller to use for setting the data of the table panel
	 */
	private void createTablePanel() {
		tablePanel.setBackground(new Color(255, 204, 255));
        tablePanel.setData(controller.getAccounts());
	}
	/**
	 * Initializes the select panel and sets its layout, buttons, and labels.
	 * The select panel allows, after selecting an account, to start playing the game.
	 */
	private void createSelectPanel() {
		selectAccountLabel = new JLabel();
        playButton = new JButton();
        leftMarginSelectPanel = new Box.Filler(new Dimension(400, 0), new Dimension(400, 0), new Dimension(400, 0));
        rightMarginSelectPanel = new Box.Filler(new Dimension(400, 0), new Dimension(400, 0), new Dimension(400, 0));
       
        selectPanel.setBackground(new Color(204, 204, 255));
        selectPanel.setLayout(new BorderLayout());

        selectAccountLabel.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        selectAccountLabel.setForeground(new Color(0, 0, 0));
        selectAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectAccountLabel.setIcon(new ImageIcon(getClass().getResource("/icons/checkedCheckbox_48px.png"))); // NOI18N
        selectAccountLabel.setText("Seleziona un account e...");
        selectAccountLabel.setHorizontalTextPosition(SwingConstants.RIGHT);

        playButton.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        playButton.setForeground(new Color(0, 0, 0));
        playButton.setIcon(new ImageIcon(getClass().getResource("/icons/playButton_96px.png"))); // NOI18N
        playButton.setText("Gioca");
        playButton.setToolTipText("Clicca per iniziare a giocare");
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setCursor(handCursor);
        playButton.setFocusPainted(false);
        
        selectPanel.add(selectAccountLabel, BorderLayout.PAGE_START);
        selectPanel.add(leftMarginSelectPanel, BorderLayout.LINE_START);
        selectPanel.add(rightMarginSelectPanel, BorderLayout.LINE_END);
        selectPanel.add(playButton, BorderLayout.CENTER);
	}
	/**
	 * Checks if the account text field is not empty.
	 * @return true if the text field is not empty, false otherwise
	 */
	private boolean checkBtn() {
        boolean value = !accountTextField.getText().trim().isEmpty();
        return value;
    }
	/**
	 * Sets the account listener for this panel.
	 * @param accountListener the account listener to set
	 */
    public void setAccountListener(AccountListener accountListener) {
    	this.accountListener = accountListener;
    }
    /**
     * Updates the account GUI list by refreshing the table panel.
     *
     * @param controller the controller to use for updating the GUI list
     * @param tablePanel the table panel to refresh
     */
    public void updateAccountGuiList(Controller controller, TablePanel tablePanel) {
		tablePanel.refresh();
    }
    /**
     * Loads the accounts from a file.
     */
    private void loadAccounts() {
	    try {
            controller.loadFromFile();
            updateAccountGuiList(controller, tablePanel);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    /**
     * Gets the back button.
     * @return the back button
     */
    public AbstractButton getBackButton() {
		return backAccountButton;
	}
    /**
     * Gets the play button.
     * @return the play button
     */
    public AbstractButton getPlayButton() {
		return playButton;
	}
    /**
     * Gets the table panel.
     * @return the table panel
     */
    public TablePanel getTablePanel() {
		return tablePanel;
	} 
    /**
     * Gets the background panel.
     * @return the background panel
     */
	public PanelGradient getBackground() {
		return backgroungAccount;
	}
}
