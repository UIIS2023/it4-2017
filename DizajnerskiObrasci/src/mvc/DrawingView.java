package mvc;

import java.awt.Graphics;
import java.util.ListIterator;

import javax.swing.JPanel;

import shapes.Shape;

public class DrawingView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1142695136846769841L;
	private DrawingModel model = new DrawingModel(); // zbog window builder-a

	public DrawingView() {
		
	}
	
	public void setModel(DrawingModel model) {
		this.model = model;
	}

	public void paint(Graphics g) {
		super.paint(g); //da ne bi iznova crtao toogle buttons tako sto prevucem misem preko
		ListIterator<Shape> it = model.getShapes().listIterator();
		while (it.hasNext()) {
			it.next().draw(g);
		}

		// repaint();
		// System.out.println(System.currentTimeMillis());
	}

}
