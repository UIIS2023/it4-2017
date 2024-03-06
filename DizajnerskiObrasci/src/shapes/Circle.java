package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends AreaShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2946824216759282827L;
	private Point center;
	private int radius;
	//private Color insideColor;
	//private Color outsideColor;

	public Circle() {

	}

	public Circle(Point center, int radius) {
		setCenter(center);
		setRadius(radius);
	}

	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		setSelected(selected);
	}

	public Circle(Point center, int radius, Color insideColor, Color outsideColor) {
		this.center = center;
		this.radius = radius;
		setInsideCol(insideColor);
		setOutsideColor(outsideColor);
	}

	public Circle(Point center, int radius, Color insideColor, Color outsideColor, boolean selected) {
		this(center, radius, insideColor, outsideColor);
		setSelected(selected);
	}

	public double area() {
		return radius * radius * Math.PI;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	/*public Color getInsideColor() {
		return insideColor;
	}

	public void setInsideColor(Color insideColor) {
		this.insideColor = insideColor;
	}

	public Color getOutsideColor() {
		return outsideColor;
	}

	public void setOutsideColor(Color outsideColor) {
		this.outsideColor = outsideColor;
	}*/

	public String toString() {
		return "Circle: (" + center.getX() + ", " + center.getY() + ") " + "radius = " + radius + "; inside color {"
				+ Integer.toString(getInsideCol().getRGB()) + "}, outside color {"
				+ Integer.toString(getOutsideColor().getRGB()) + "}";
	}

	@Override
	public void moveBy(int byX, int byY) {
		center.moveBy(byX, byY);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Circle) {
			return (this.radius - ((Circle) o).radius);
		}
		return 0;
	}

	@Override
	public void draw(Graphics g) {

		if (getInsideCol() != null) {
			g.setColor(getInsideCol());
			/*
			 * g.fillOval(this.getCenter().getX() - this.radius, getCenter().getY() -
			 * getRadius(), this.getRadius() * 2, this.getRadius() * 2);
			 */
			fill(g);
		}

		if (getOutsideColor() != null)
			g.setColor(getOutsideColor());
		else
			g.setColor(Color.BLACK);

		g.drawOval(this.getCenter().getX() - this.radius, getCenter().getY() - getRadius(), this.getRadius() * 2,
				this.getRadius() * 2);
		g.setColor(Color.BLACK);

		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(getCenter().getX() - 3, getCenter().getY() - 3, 6, 6);
			g.drawRect(getCenter().getX() + getRadius() - 3, getCenter().getY() - 3, 6, 6);
			g.drawRect(getCenter().getX() - getRadius() - 3, getCenter().getY() - 3, 6, 6);
			g.drawRect(getCenter().getX() - 3, getCenter().getY() + getRadius() - 3, 6, 6);
			g.drawRect(getCenter().getX() - 3, getCenter().getY() - getRadius() - 3, 6, 6);
			g.setColor(Color.BLACK);
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return this.getCenter().distance(x, y) <= radius;
	}

	public boolean contains(Point p) {
		return center.distance(p.getX(), p.getY()) <= radius;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle c = (Circle) obj;
			if (this.center.equals(c.getCenter()) && this.radius == c.getRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Circle clone(Circle c) {

		c.setCenter(this.getCenter());
		c.setRadius(this.getRadius());
		c.setOutsideColor(this.getOutsideColor());
		c.setInsideCol(this.getInsideCol());

		return c;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInsideCol());
		g.fillOval(this.getCenter().getX() - this.radius, getCenter().getY() - getRadius(), this.getRadius() * 2,
				this.getRadius() * 2);
	}
}
