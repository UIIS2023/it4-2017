package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends AreaShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4329998684484548507L;
	private Point upperLeftPoint;
	private int width;
	private int height;
	//private Color outsideColor;
	//private Color insideColor;

	public Rectangle() {

	}

	public Rectangle(Point upperLeftPoint, int height, int width) {
		this.upperLeftPoint = upperLeftPoint;
		this.height = height;
		this.width = width;
	}

	public Rectangle(Point upperLeftPoint, int height, int width, Color insideColor, Color outsideColor) {
		this.upperLeftPoint = upperLeftPoint;
		setHeight(height);
		setWidth(width);
		setOutsideColor(outsideColor);
		setInsideCol(insideColor);
	}

	public Rectangle(Point upperLeftPoint, int height, int width, boolean selected) {
		this(upperLeftPoint, height, width);
		setSelected(selected);
	}

	@Override
	public void draw(Graphics g) {
		if (getInsideCol() != null) {
			g.setColor(getInsideCol());
			//g.fillRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.getWidth(), this.height);
			fill(g);
		}

		if (getOutsideColor() != null)
			g.setColor(getOutsideColor());
		else
			g.setColor(Color.BLACK);

		g.drawRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.getWidth(), this.height);
		g.setColor(Color.BLACK);

		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(getUpperLeftPoint().getX() - 3, getUpperLeftPoint().getY() - 3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() - 3 + getWidth(), this.getUpperLeftPoint().getY() - 3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() - 3 + getHeight(), 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() + getWidth() - 3,
					this.getUpperLeftPoint().getY() + getHeight() - 3, 6, 6);
			g.setColor(Color.BLACK);
		}
	}

	public boolean contains(Point p) {
		if (this.getUpperLeftPoint().getX() <= p.getX() && p.getX() <= this.getUpperLeftPoint().getX() + width
				&& this.getUpperLeftPoint().getY() <= p.getY()
				&& p.getY() <= this.getUpperLeftPoint().getY() + height) {
			return true;
		} else {
			return false;
		}
	}

	public int area() {
		return width * height;
	}

	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point point) {
		this.upperLeftPoint = point;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;

	}

	/*public Color getOutsideColor() {
		return outsideColor;
	}

	public void setOutsideColor(Color outsideColor) {
		this.outsideColor = outsideColor;
	}

	public Color getInsideColor() {
		return insideColor;
	}

	public void setInsideColor(Color insideColor) {
		this.insideColor = insideColor;
	}*/

	public String toString() {
		return "Rectangle: (" + upperLeftPoint.getX() + ", " + upperLeftPoint.getY() + "), " + "height = " + height
				+ "; width = " + width + "; inside color {" + Integer.toString(getInsideCol().getRGB()) + "} "
				+ "outside color {" + Integer.toString(getOutsideColor().getRGB()) + "}";
	}

	@Override
	public void moveBy(int byX, int byY) {
		upperLeftPoint.moveBy(byX, byY);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Rectangle) {
			return (this.area() - ((Rectangle) o).area());
		}
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		if (this.getUpperLeftPoint().getX() <= x && x <= this.getUpperLeftPoint().getX() + width
				&& this.getUpperLeftPoint().getY() <= y && y <= this.getUpperLeftPoint().getY() + height) {
			return true;
		} else {
			return false;
		}
	}

	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle r = (Rectangle) obj;
			if (this.upperLeftPoint.equals(r.getUpperLeftPoint()) && this.height == r.getHeight()
					&& this.width == r.getWidth()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Rectangle clone(Rectangle r) {

		r.setUpperLeftPoint(this.getUpperLeftPoint());
		r.setHeight(this.getHeight());
		r.setWidth(this.getWidth());
		r.setOutsideColor(this.getOutsideColor());
		r.setInsideCol(this.getInsideCol());

		return r;
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getInsideCol());
		g.fillRect(getUpperLeftPoint().getX(), getUpperLeftPoint().getY(), getWidth(), getHeight());
	}
}
