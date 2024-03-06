package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {


	/**
	 * 
	 */
	private static final long serialVersionUID = -343297656286754187L;
	private int x;
	private int y;
	//private Color color;

	public Point() {

	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(int x, int y, boolean selected) {
		this(x, y);
		setSelected(selected);
	}

	public Point(int x, int y, Color color) {
		this(x, y);
		setOutsideColor(color);
	}

	public Point(int x, int y, Color color, boolean selected) {
		this(x, y, color);
		setSelected(selected);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/*public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}*/

	public String toString() {
		return "Point: (" + x + ", " + y + "), color: (" + Integer.toString(getOutsideColor().getRGB()) + ")";
	}

	public double distance(int x2, int y2) {
		double dx = this.x - x2;
		double dy = this.y - y2;
		double d = Math.sqrt(dx * dx + dy * dy);
		return d;
	}

	@Override
	public boolean contains(int x, int y) {
		return this.distance(x, y) <= 3;
	}

	@Override
	public void draw(Graphics g) {
		if (getOutsideColor() != null)
			g.setColor(getOutsideColor());
		else
			g.setColor(Color.BLACK);

		g.drawLine(this.x - 2, this.y, this.x + 2, this.y);
		g.drawLine(this.x, this.y - 2, this.x, this.y + 2);
		g.setColor(Color.BLACK);
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.x - 3, this.y - 3, 6, 6);
			g.setColor(Color.BLACK);
		}
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.x += byX;
		this.y += byY;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Point) {
			Point start = new Point(0, 0);
			//rastojanje moje tacke od koordinatnog pocetka - rastojanje druge tacke od koordinatnog pocetka
			return (int) (this.distance(start.getX(), start.getY()) - ((Point) o).distance(start.getX(), start.getY()));
		}
		return 0;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point p = (Point) obj;
			if (this.x == p.getX() && this.y == p.getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Point clone(Point p) {

		p.setX(this.getX());
		p.setY(this.getY());
		p.setOutsideColor(this.getOutsideColor());

		return p;
	}

}
