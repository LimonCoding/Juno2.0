package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class MarginPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3882648311336945019L;
	
	private Box.Filler topMargin;
    private Box.Filler bottomMargin;
    private Box.Filler leftMargin;
    private Box.Filler rightMargin;
    
    private Dimension lateralDimesion = new Dimension(100, 0);
    private Dimension frontDimesion = new Dimension(0, 100);
    
    public MarginPanel() {
    	rightMargin = new Box.Filler(lateralDimesion, lateralDimesion, lateralDimesion);
        leftMargin = new Box.Filler(lateralDimesion, lateralDimesion, lateralDimesion);
        topMargin = new Box.Filler(frontDimesion, frontDimesion, frontDimesion);
        bottomMargin = new Box.Filler(frontDimesion, frontDimesion, frontDimesion);
	
        setOpaque(false);
        setLayout(new BorderLayout());

        add(rightMargin, BorderLayout.LINE_END);
        add(leftMargin, BorderLayout.LINE_START);
        add(topMargin, BorderLayout.PAGE_START);
        add(bottomMargin, BorderLayout.PAGE_END);
    }
	
}
