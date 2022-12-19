package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

import controller.Controller;

public class AccountCard {
	
	private Controller controller;
	private JFrame frame;
	
	private MarginPanel accountMarginPanel;
	private PanelGradient backgroungAccount;
	
    private Box.Filler rightMarginSelectPanel;
    private Box.Filler leftMarginSelectPanel;
    ////////////////////////////////////
    private Box.Filler bottomMarginInsertPanel;
    ////////////////////////////////////
    private JLabel selectAccountLabel;
    private JPanel selectPanel;
    
    private JPanel accountPanel;
    
	////////////////////////////////////
	
    
    private JTextField accountTextField;
    private JButton addAccountButton;
    private JButton backAccountButton;
    
    private JLabel createLabel;
    private JPanel insertPanel;
    private TablePanel tablePanel;
    private JButton playButton;
    
    private AbstractAction action;
    private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    
    private AccountListener accountListener;

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
                    controller.saveToFile(new File("saves/saves.txt"));
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

	private void createTablePanel() {
		tablePanel.setBackground(new Color(255, 204, 255));
        tablePanel.setData(controller.getAccounts());
	}
	
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

	private boolean checkBtn() {
        boolean value = !accountTextField.getText().trim().isEmpty();
        return value;
    }
    
    public void setAccountListener(AccountListener accountListener) {
    	this.accountListener = accountListener;
    }
    
    public void updateAccountGuiList(Controller controller, TablePanel tablePanel) {
		tablePanel.refresh();
    }
    
    private void loadAccounts() {
	    try {
            File file = new File("saves/saves.txt");
            System.out.println(file);
            controller.loadToFile(file);
            updateAccountGuiList(controller, tablePanel);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    public AbstractButton getBackButton() {
		return backAccountButton;
	}
    
    public AbstractButton getPlayButton() {
		return playButton;
	}
    
    public TablePanel getTablePanel() {
		return tablePanel;
	} 
	
	public PanelGradient getBackground() {
		return backgroungAccount;
	}
}
