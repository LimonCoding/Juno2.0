package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
/**
 * A panel with margins on all sides to make space between frame and the useful panel.
 * 
 * @author Simone
 */
public class MarginPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3882648311336945019L;
	/** The top margin filler. */
	private Box.Filler topMargin;
	/** The bottom margin filler. */
    private Box.Filler bottomMargin;
    /** The left margin filler. */
    private Box.Filler leftMargin;
    /** The right margin filler. */
    private Box.Filler rightMargin;
    /** The lateral margin dimension. */
    private Dimension lateralDimesion = new Dimension(100, 0);
    /** The front margin dimension. */
    private Dimension frontDimesion = new Dimension(0, 100);
    /**
    * Constructs a new MarginPanel.
    */
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
