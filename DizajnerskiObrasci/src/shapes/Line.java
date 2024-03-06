package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5309391729041985254L;
	private Point startPoint = new Point();
	private Point endPoint = new Point();
	//private Color color;

	public Line() {

	}

	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Line(Point startPoint, Point endPoint, Color color) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		setOutsideColor(color);
	}

	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		setSelected(selected);
	}

	public Line(Point startPoint, Point endPoint, Color color, boolean selected) {
		this(startPoint, endPoint, color);
		setSelected(selected);
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	/*public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}*/

	@Override
	public void moveBy(int byX, int byY) {
		startPoint.moveBy(byX, byY);
		endPoint.moveBy(byX, byY);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Line) {
			return (int) (this.length() - ((Line) o).length());
		}
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		if ((startPoint.distance(x, y) + endPoint.distance(x, y)) - length() <= 0.05)
			return true;
		else
			return false;
	}

	public Point middleOfLine() {
		int middleByX = (this.getStartPoint().getX() + this.getEndPoint().getX()) / 2;
		int middleByY = (this.getStartPoint().getY() + this.getEndPoint().getY()) / 2;
		Point p = new Point(middleByX, middleByY);
		return p;
	}

	@Override
	public void draw(Graphics g) {
		if (this.getOutsideColor() != null)
			g.setColor(this.getOutsideColor());
		else
			g.setColor(Color.BLACK);

		g.drawLine(this.getStartPoint().getX(), getStartPoint().getY(), this.getEndPoint().getX(),
				this.getEndPoint().getY());
		g.setColor(Color.BLACK);
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(getStartPoint().getX() - 3, getStartPoint().getY() - 3, 6, 6);
			g.drawRect(getEndPoint().getX() - 3, getEndPoint().getY() - 3, 6, 6);
			g.drawRect(middleOfLine().getX() - 3, middleOfLine().getY() - 3, 6, 6);
			g.setColor(Color.BLACK);
		}
	}

	public boolean equals(Object obj) {
		if (obj instanceof Line) {
			Line l = (Line) obj;
			if (this.startPoint.equals(l.getStartPoint()) && this.endPoint.equals(l.getEndPoint())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public double length() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
	}

	public String toString() {
		return "Line: " + "(" + startPoint.getX() + ", " + startPoint.getY() + "); (" + endPoint.getX() + ", "
				+ endPoint.getY() + ") " + "color {" + Integer.toString(getOutsideColor().getRGB()) + "}";
	}

	public Line clone(Line l) {

		l.setStartPoint(this.getStartPoint());
		l.setEndPoint(this.getEndPoint());
		l.setOutsideColor(this.getOutsideColor());

		return l;
	}

}
