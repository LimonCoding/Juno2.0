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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

public class SelectColor extends JFrame {

	private PanelGradient backgroungColor;
	private MarginPanel colorMarginPanel;
	private JPanel chooseColorPanel;
	
	private JButton greenButton;
	private JButton blueButton;
	private JButton redButton;
	private JButton yellowButton;
	private JLabel selectColor;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FlatDarkLaf.setup();
				UIManager.put( "Button.arc", 40 );
				UIManager.put( "TextComponent.arc", 30 );
				UIManager.put( "Component.focusWidth", 2 );
				UIManager.put( "Component.innerFocusWidth", 1 );
				UIManager.put( "Button.innerFocusWidth", 1 );
				SelectColor frame = new SelectColor();
				frame.setPreferredSize(new Dimension(550,320));
				frame.setMinimumSize(new Dimension(550,320));
				frame.setUndecorated(true);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
	
	public SelectColor() {
		

		backgroungColor = new PanelGradient();
		backgroungColor.setLayout(new BorderLayout());
    	colorMarginPanel = new MarginPanel();
    	
		chooseColorPanel = new JPanel();
		setChooseColorPanel();
		
		colorMarginPanel.add(chooseColorPanel, BorderLayout.CENTER);
		backgroungColor.add(colorMarginPanel, BorderLayout.CENTER);
		getContentPane().add(backgroungColor);
	}
	
	
	private void greenButtonActionPerformed(ActionEvent evt) {                                            
        this.dispose();
    }                                           

    private void redButtonActionPerformed(ActionEvent evt) {                                          
    	this.dispose();
    }   
    
    private void blueButtonActionPerformed(ActionEvent evt) {                                          
    	this.dispose();
    }

    private void yellowButtonActionPerformed(ActionEvent evt) {                                             
    	this.dispose();
    }     
    
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
