package shapes;

import java.awt.Color;
import java.awt.Graphics;

public abstract class AreaShape extends Shape {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6147276081720377493L;
	private Color insideCol;

	public abstract void fill(Graphics g);

	public Color getInsideCol() {
		return insideCol;
	}

	public void setInsideCol(Color insideCol) {
		this.insideCol = insideCol;
	}

}
