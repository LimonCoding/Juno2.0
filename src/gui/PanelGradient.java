package gui;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelGradient extends JPanel {
	
	public PanelGradient() {
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}
	
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		int width = getWidth();
		int heigth = getHeight();
		
		Color color1 = new Color(255,90,0);
		Color color2 = new Color(255,180,0);
		GradientPaint gp = new GradientPaint(0, 0, color1, width, heigth, color2);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, width, heigth);

	}
}
