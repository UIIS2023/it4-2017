package adapter;

import shapes.AreaShape;
import shapes.Point;

import java.awt.Color;
import java.awt.Graphics;
import hexagon.Hexagon;

public class HexagonAdapter extends AreaShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2741236507045445427L;
	private Hexagon hexagon;

	public HexagonAdapter() {

	}

	public HexagonAdapter(Point center, int r, Color insideColor, Color outsideColor) {
		this.hexagon = new Hexagon(center.getX(), center.getY(), r);
		this.hexagon.setAreaColor(insideColor);
		this.hexagon.setBorderColor(outsideColor);
	}

	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Point center, int r, Color insideColor, Color outsideColor) {
		this.hexagon = new Hexagon(center.getX(), center.getY(), r);
		this.hexagon.setAreaColor(insideColor);
		this.hexagon.setBorderColor(outsideColor);
		hexagon.setSelected(true); // ostaje selektovan nakon modifikacijee
	}

	@Override
	public void moveBy(int byX, int byY) {
		// fali mi getCenter metoda u Hexagon klasi :(
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof HexagonAdapter) {
			HexagonAdapter h = (HexagonAdapter) o;
			return (int) (hexagon.getR() - h.getHexagon().getR());
		} else
			return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
	}

	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter hexagonAdapter = (HexagonAdapter) obj;
			Point center1 = new Point(hexagon.getX(), hexagon.getY());
			Point center2 = new Point(hexagonAdapter.hexagon.getX(), hexagonAdapter.hexagon.getY());
			if (center1.equals(center2) && hexagon.getR() == hexagonAdapter.getHexagon().getR())
				return true;
			else
				return false;

		} else
			return false;
	}

	// Source-->override/implement methods-->Shape
	@Override
	public boolean isSelected() {
		return hexagon.isSelected();
	}

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		hexagon.setSelected(selected);
	}

	// Source-->generate delegate methods
	public Color getAreaColor() {
		return hexagon.getAreaColor();
	}

	public Color getBorderColor() {
		return hexagon.getBorderColor();
	}

	public int getR() {
		return hexagon.getR();
	}

	public int getX() {
		return hexagon.getX();
	}

	public int getY() {
		return hexagon.getY();
	}

	public Point getCenter() {
		return new Point(hexagon.getX(), hexagon.getY());
	}

	public void setAreaColor(Color areaColor) {
		hexagon.setAreaColor(areaColor);
	}

	public void setBorderColor(Color borderColor) {
		hexagon.setBorderColor(borderColor);
	}

	public void setR(int r) {
		hexagon.setR(r);
	}

	public void setX(int x) {
		hexagon.setX(x);
	}

	public void setY(int y) {
		hexagon.setY(y);
	}

	public String toString() {
		return "Hexagon: Center (" + hexagon.getX() + ", " + hexagon.getY() + ") radius = " + hexagon.getR()
				+ "; inner color {" + Integer.toString(hexagon.getAreaColor().getRGB()) + "} outside color {"
				+ Integer.toString(hexagon.getBorderColor().getRGB()) + "}";

	}

	public double area() {
		return hexagon.getR() * hexagon.getR() * Math.PI;
	}

	public HexagonAdapter clone(HexagonAdapter h) {
		h.getHexagon().setX(hexagon.getX());
		h.getHexagon().setY(hexagon.getY());
		h.getHexagon().setR(hexagon.getR());
		h.getHexagon().setBorderColor(hexagon.getBorderColor());
		h.getHexagon().setAreaColor(hexagon.getAreaColor());
		return h;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(hexagon.getAreaColor());
	}
}