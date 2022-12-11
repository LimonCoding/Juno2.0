package gui;

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
        FlatDarkLaf.setup();
        new JUnoFrame().setVisible(true);
    }
    
}