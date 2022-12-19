package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

/**
 *
 * @author Simone
 */
public class JUnoMain {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FlatDarkLaf.setup();
				UIManager.put( "Button.arc", 40 );
				UIManager.put( "TextComponent.arc", 30 );
				UIManager.put( "Component.focusWidth", 2 );
				UIManager.put( "Component.innerFocusWidth", 1 );
				UIManager.put( "Button.innerFocusWidth", 1 );
				JUnoFrame frame = new JUnoFrame();
				frame.setVisible(true);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
		});
    }
}