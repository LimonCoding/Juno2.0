package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class HomeCard {
	
	private PanelGradient backgroungHome;
    
    private MarginPanel homeMarginPanel;
    
    
    private JLabel welcome;
    private JPanel welcomePanel;
    private JButton logo;
    private JLabel cliccaLogo;
    
    private Box.Filler leftMarginLogo;
    private Box.Filler rightMarginLogo;

	HomeCard() {
    	backgroungHome = new PanelGradient();
    	homeMarginPanel = new MarginPanel();
        welcomePanel = new JPanel();
        welcome = new JLabel();
        cliccaLogo = new JLabel();
        logo = new JButton();
        rightMarginLogo = new Box.Filler(new Dimension(330, 0), new Dimension(330, 0), new Dimension(660, 0));
        leftMarginLogo = new Box.Filler(new Dimension(330, 0), new Dimension(330, 0), new Dimension(660, 0));
        
    	backgroungHome.setLayout(new BorderLayout());


        welcomePanel.setOpaque(false);
        welcomePanel.setLayout(new BorderLayout());

        welcome.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        welcome.setForeground(new Color(0, 0, 0));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setIcon(new ImageIcon(getClass().getResource("/icons/hello_96px.png"))); // NOI18N
        welcome.setText("Benvenuto!");
        welcome.setHorizontalTextPosition(SwingConstants.RIGHT);
        welcomePanel.add(welcome, BorderLayout.PAGE_START);

        cliccaLogo.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        cliccaLogo.setForeground(new Color(0, 0, 0));
        cliccaLogo.setHorizontalAlignment(SwingConstants.CENTER);
        cliccaLogo.setIcon(new ImageIcon(getClass().getResource("/icons/handPointer_96px.png"))); // NOI18N
        cliccaLogo.setText("clicca il logo per iniziare");
        welcomePanel.add(cliccaLogo, BorderLayout.PAGE_END);

        logo.setIcon(new ImageIcon(getClass().getResource("/icons/unoLogo.png"))); // NOI18N
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
        welcomePanel.add(logo, BorderLayout.CENTER);
        welcomePanel.add(rightMarginLogo, BorderLayout.LINE_END);
        welcomePanel.add(leftMarginLogo, BorderLayout.LINE_START);
        homeMarginPanel.add(welcomePanel, BorderLayout.CENTER);
        backgroungHome.add(homeMarginPanel, BorderLayout.CENTER);
	}
	
	private void logoMouseEntered(MouseEvent evt) {                                  
        logo.setIcon(new ImageIcon(getClass().getResource("/icons/unoLogoOnButton.png")));
    }                                 

    private void logoMouseExited(MouseEvent evt) {                                 
        logo.setIcon(new ImageIcon(getClass().getResource("/icons/unoLogo.png")));
    }

	public JButton getLogoButton() {
		return logo;
	} 
	
	public PanelGradient getBackground() {
		return backgroungHome;
	} 
}
