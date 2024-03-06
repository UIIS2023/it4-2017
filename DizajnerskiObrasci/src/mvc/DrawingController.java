package mvc;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import adapter.HexagonAdapter;
import command.CmdBringToBack;
import command.CmdBringToFront;
import command.CmdCircleAdd;
import command.CmdCircleRemove;
import command.CmdCircleUpdate;
import command.CmdShapeDeselect;
import command.CmdDonutAdd;
import command.CmdDonutRemove;
import command.CmdDonutUpdate;
import command.CmdHexagonAdd;
import command.CmdHexagonRemove;
import command.CmdHexagonUpdate;
import command.CmdLineAdd;
import command.CmdLineRemove;
import command.CmdLineUpdate;
import command.CmdPointAdd;
import command.CmdPointRemove;
import command.CmdPointUpdate;
import command.CmdRectangleAdd;
import command.CmdRectangleRemove;
import command.CmdRectangleUpdate;
import command.CmdShapeSelect;
import command.CmdShapeAdd;
import command.CmdShapeRemove;
import command.CmdToBack;
import command.CmdToFront;
import command.CmdUndoRedo;
import dialogs.DialogCircle;
import dialogs.DialogDonut;
import dialogs.DialogHexagon;
import dialogs.DialogLine;
import dialogs.DialogPoint;
import dialogs.DialogRectangle;
import observer.EnablingButtons;
import observer.EnablingButtonsUpdate;
import shapes.Circle;
import shapes.Donut;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;
import strategy.LoadLog;
import strategy.LoadManager;
import strategy.LoadShapes;
import strategy.SaveLog;
import strategy.SaveManager;
import strategy.SaveShapes;

public class DrawingController {

	private DrawingModel model;
	private DrawingFrame frame;

	private DialogPoint dialogPoint = new DialogPoint();
	private DialogLine dialogLine = new DialogLine();
	private DialogRectangle dialogRectangle = new DialogRectangle();
	private DialogHexagon dialogHexagon = new DialogHexagon();
	private DialogCircle dialogCircle = new DialogCircle();
	private DialogDonut dialogDonut = new DialogDonut();

	// Prilikom crtanja linije
	private Point startPoint;

	// private List<Shape> selectedShapes = new ArrayList<Shape>();

	private Shape selectedShape;

	private EnablingButtons buttonsObserver = new EnablingButtons();
	private EnablingButtonsUpdate buttonsObserverUpdate;

	private SaveManager saveManager;
	private SaveManager saveLog;
	private LoadManager loadManager;

	private JFileChooser fileChooser;

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		buttonsObserverUpdate = new EnablingButtonsUpdate(frame);
		buttonsObserver.addPropertyChangeListener(buttonsObserverUpdate);
	}

	private CmdUndoRedo cmdDeque = new CmdUndoRedo();

	// Potrebno za import loga
	private String logLine = "";
	private ArrayList<String> logList = new ArrayList<String>();
	private int logCounter = 0;
	private Shape shape = null;

	public void mouseClicked(MouseEvent arg0) {

		// SELEKCIJA
		if (frame.getBtnSelect().isSelected()) {
			Point p = new Point(arg0.getX(), arg0.getY()); // tacka klika
			ListIterator<Shape> it = model.getShapes().listIterator();

			while (it.hasNext()) {
				selectedShape = it.next();
				if (selectedShape.contains(p.getX(), p.getY())) {
					if (selectedShape.isSelected() == false) { // oblik nije vec selektovan -> selektuj
						CmdShapeSelect cmdSelect = new CmdShapeSelect(model, selectedShape);
						cmdSelect.execute();
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
						// selectedShapes.add(selectedShape); --> ovo je premesteno u execute metodu
						// CmdSelect klase!
						cmdDeque.getUndoDeque().offerLast(cmdSelect);
						frame.getTextArea().append(cmdSelect.log() + "\n");

					} else { // oblik je vec selektovan --> deselect
						CmdShapeDeselect cmdDeselect = new CmdShapeDeselect(model, selectedShape);
						cmdDeselect.execute();
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
						// selectedShapes.remove(selectedShape); --> ovo je premesteno u execute metodu
						// CmdDeselect klase!
						cmdDeque.getUndoDeque().offerLast(cmdDeselect);
						frame.getTextArea().append(cmdDeselect.log() + "\n");
					}
				}
				frame.getView().repaint();
			}
			/****************** CRTANJE OBLIKA ***************************/
		} else if (frame.getBtnPoint().isSelected()) {
			dialogPoint.setTxtXEdt(false);
			dialogPoint.setTxtYEdt(false);
			dialogPoint.setTxtX(Integer.toString(arg0.getX()));
			dialogPoint.setTxtY(Integer.toString(arg0.getY()));
			dialogPoint.setColor(frame.getBtnOutsideCol().getBackground());
			// dialogPoint.pack();
			dialogPoint.setVisible(true);
			if (dialogPoint.isOk()) {
				Point p = new Point(arg0.getX(), arg0.getY(), dialogPoint.getColor());
				CmdPointAdd cmd = new CmdPointAdd(model, p);
				cmd.execute();
				cmdDeque.getUndoDeque().offerLast(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				frame.getTextArea().append(cmd.log() + "\n");
				frame.getBtnOutsideCol().setBackground(p.getOutsideColor());
				frame.getView().repaint();
			}
		} else if (frame.getBtnLine().isSelected()) {
			// Prvi klik na panel oznacava pocetnu tacku linije
			if (startPoint == null) {
				startPoint = new Point(arg0.getX(), arg0.getY());
			} else {
				// Sledeci klik oznacava krajnju tacku linije, jer je pocetna tacka vec
				// inicijalizovana
				Point endPoint = new Point(arg0.getX(), arg0.getY());
				dialogLine.setTxtStartPointXEdt(false);
				dialogLine.setTxtStartPointYEdt(false);
				dialogLine.setTxtEndPointXEdt(false);
				dialogLine.setTxtEndPointYEdt(false);
				dialogLine.setTxtStartPointX(Integer.toString(startPoint.getX()));
				dialogLine.setTxtStartPointY(Integer.toString(startPoint.getY()));
				dialogLine.setTxtEndPointX(Integer.toString(endPoint.getX()));
				dialogLine.setTxtEndPointY(Integer.toString(endPoint.getY()));
				dialogLine.setCol(frame.getBtnOutsideCol().getBackground());
				// dialogLine.pack();
				dialogLine.setVisible(true);
				if (dialogLine.isOk()) {
					Line l = new Line(startPoint, endPoint, dialogLine.getCol());
					CmdLineAdd cmd = new CmdLineAdd(model, l);
					cmd.execute();
					cmdDeque.getUndoDeque().offerLast(cmd);
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					frame.getTextArea().append(cmd.log() + "\n");
					frame.getBtnOutsideCol().setBackground(l.getOutsideColor());
					frame.getView().repaint();
				}
			}
		} else if (frame.getBtnRect().isSelected()) {
			dialogRectangle.setTxtXKoordinataEnabled(false);
			dialogRectangle.setTxtYKoordinataEnabled(false);
			dialogRectangle.setTxtXCoordinate(Integer.toString(arg0.getX()));
			dialogRectangle.setTxtYCoordinate(Integer.toString(arg0.getY()));
			dialogRectangle.setTxtHeight("");
			dialogRectangle.setTxtWidth("");
			dialogRectangle.setBorderColor(frame.getBtnOutsideCol().getBackground());
			dialogRectangle.setInsideColor(frame.getBtnInsideCol().getBackground());
			dialogRectangle.pack();
			dialogRectangle.setVisible(true);
			try {
				if (dialogRectangle.isOk()) {
					int width = Integer.parseInt(dialogRectangle.getTxtWidth());
					int height = Integer.parseInt(dialogRectangle.getTxtHeight());
					Rectangle r = new Rectangle(new Point(arg0.getX(), arg0.getY()), height, width,
							dialogRectangle.getInsideColor(), dialogRectangle.getBorderColor());
					CmdRectangleAdd cmd = new CmdRectangleAdd(model, r);
					cmd.execute();
					cmdDeque.getUndoDeque().offerLast(cmd);
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					frame.getTextArea().append(cmd.log() + "\n");
					frame.getBtnInsideCol().setBackground(r.getInsideCol());
					frame.getBtnOutsideCol().setBackground(r.getOutsideColor());
					frame.getView().repaint();
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Wrong entry!", "Error", JOptionPane.WARNING_MESSAGE);
			}
		} else if (frame.getBtnCircle().isSelected()) {
			dialogCircle.setTxtKoordXEdt(false);
			dialogCircle.setTxtKoordYEdt(false);
			dialogCircle.setTxtXCoordinate(Integer.toString(arg0.getX()));
			dialogCircle.setTxtYCoordinate(Integer.toString(arg0.getY()));
			dialogCircle.setTxtRadius("");
			dialogCircle.setBorderColor(frame.getBtnOutsideCol().getBackground());
			dialogCircle.setInsideColor(frame.getBtnInsideCol().getBackground());
			dialogCircle.pack();
			dialogCircle.setVisible(true);
			try {
				if (dialogCircle.isOk()) {
					int radius = Integer.parseInt(dialogCircle.getTxtRadius());
					Circle c = new Circle(new Point(arg0.getX(), arg0.getY()), radius);
					c.setInsideCol(dialogCircle.getInsideColor());
					c.setOutsideColor(dialogCircle.getBorderColor());
					CmdCircleAdd cmd = new CmdCircleAdd(model, c);
					cmd.execute();
					cmdDeque.getUndoDeque().offerLast(cmd);
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					frame.getTextArea().append(cmd.log() + "\n");
					frame.getBtnInsideCol().setBackground(c.getInsideCol());
					frame.getBtnOutsideCol().setBackground(c.getOutsideColor());
					frame.getView().repaint();
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Wrong entry!", "Error", JOptionPane.WARNING_MESSAGE);
			}
		} else if (frame.getBtnDonut().isSelected()) {
			dialogDonut.setTxtXCoordEditable(false);
			dialogDonut.setTxtYCoordEditable(false);
			dialogDonut.setTxtXCoordinate(Integer.toString(arg0.getX()));
			dialogDonut.setTxtYCoordinate(Integer.toString(arg0.getY()));
			dialogDonut.setTxtInnerRadius("");
			dialogDonut.setTxtOuterRadius("");
			dialogDonut.setBorderColor(frame.getBtnOutsideCol().getBackground());
			dialogDonut.setInsideColor(frame.getBtnInsideCol().getBackground());
			// dialogDonut.pack();
			dialogDonut.setVisible(true);
			try {
				if (dialogDonut.isOk()) {
					int radius = Integer.parseInt(dialogDonut.getTxtOuterRadius());
					int innerRadius = Integer.parseInt(dialogDonut.getTxtInnerRadius());
					Donut d = new Donut(new Point(arg0.getX(), arg0.getY()), radius, innerRadius);
					d.setInsideCol(dialogDonut.getInsideColor());
					d.setOutsideColor(dialogDonut.getBorderColor());
					CmdDonutAdd cmd = new CmdDonutAdd(model, d);
					cmd.execute();
					cmdDeque.getUndoDeque().offerLast(cmd);
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					frame.getTextArea().append(cmd.log() + "\n");
					frame.getBtnInsideCol().setBackground(d.getInsideCol());
					frame.getBtnOutsideCol().setBackground(d.getOutsideColor());
					frame.getView().repaint();
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Wrong entry!", "Error", JOptionPane.WARNING_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Inner radius shoud be less than outer radius!", "Error",
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (frame.getBtnHexa().isSelected()) {
			dialogHexagon.setTxtKoordXEdt(false);
			dialogHexagon.setTxtKoordYEdt(false);
			dialogHexagon.setTxtXCoordinate(Integer.toString(arg0.getX()));
			dialogHexagon.setTxtYCoordinate(Integer.toString(arg0.getY()));
			dialogHexagon.setTxtRadius("");
			dialogHexagon.setBorderColor(frame.getBtnOutsideCol().getBackground());
			dialogHexagon.setInsideColor(frame.getBtnInsideCol().getBackground());
			dialogHexagon.pack();
			dialogHexagon.setVisible(true);
			if (dialogHexagon.isOk()) {
				int radius = Integer.parseInt(dialogHexagon.getTxtRadius());
				HexagonAdapter h = new HexagonAdapter(new Point(arg0.getX(), arg0.getY()), radius,
						dialogHexagon.getInsideColor(), dialogHexagon.getBorderColor());
				CmdHexagonAdd cmd = new CmdHexagonAdd(model, h);
				cmd.execute();
				cmdDeque.getUndoDeque().offerLast(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				frame.getTextArea().append(cmd.log() + "\n");
				frame.getBtnInsideCol().setBackground(h.getAreaColor());
				frame.getBtnOutsideCol().setBackground(h.getBorderColor());
				frame.getView().repaint();
			}
		}
		redoCleaner();
		enablingEditAndDeleteButtons();

	}

	// BRISANJE
	public void delete(ActionEvent arg0) {
		if (model.getSelectedShapes().size() != 0) {
			if (JOptionPane.showConfirmDialog(new JFrame(), "Please confirm deletion.", "Confirm",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				for (int i = model.getSelectedShapes().size() - 1; i >= 0; i--) {
					if (model.getSelectedShapes().get(i) instanceof Point) {
						CmdPointRemove cmd = new CmdPointRemove(model, (Point) model.getSelectedShapes().get(i));
						cmd.execute();
						cmdDeque.getUndoDeque().offerLast(cmd);
						frame.getTextArea().append(cmd.log() + "\n");
					} else if (model.getSelectedShapes().get(i) instanceof Line) {
						CmdLineRemove cmd = new CmdLineRemove(model, (Line) model.getSelectedShapes().get(i));
						cmd.execute();
						cmdDeque.getUndoDeque().offerLast(cmd);
						frame.getTextArea().append(cmd.log() + "\n");
					} else if (model.getSelectedShapes().get(i) instanceof Rectangle) {
						CmdRectangleRemove cmd = new CmdRectangleRemove(model,
								(Rectangle) model.getSelectedShapes().get(i));
						cmd.execute();
						cmdDeque.getUndoDeque().offerLast(cmd);
						frame.getTextArea().append(cmd.log() + "\n");
					} else if (model.getSelectedShapes().get(i) instanceof Circle) {
						CmdCircleRemove cmd = new CmdCircleRemove(model, (Circle) model.getSelectedShapes().get(i));
						cmd.execute();
						cmdDeque.getUndoDeque().offerLast(cmd);
						frame.getTextArea().append(cmd.log() + "\n");
					} else if (model.getSelectedShapes().get(i) instanceof Donut) {
						CmdDonutRemove cmd = new CmdDonutRemove(model, (Donut) model.getSelectedShapes().get(i));
						cmd.execute();
						cmdDeque.getUndoDeque().offerLast(cmd);
						frame.getTextArea().append(cmd.log() + "\n");
					} else if (model.getSelectedShapes().get(i) instanceof HexagonAdapter) {
						CmdHexagonRemove cmd = new CmdHexagonRemove(model,
								(HexagonAdapter) model.getSelectedShapes().get(i));
						cmd.execute();
						cmdDeque.getUndoDeque().offerLast(cmd);
						frame.getTextArea().append(cmd.log() + "\n");
					}
					redoCleaner();
					frame.getView().repaint();
					model.getSelectedShapes().remove(i);
					enablingEditAndDeleteButtons();
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
				}
			}
		}
	}

	// MODIFIKACIJA
	public void edit(ActionEvent arg0) {
		if (model.getSelectedShapes().get(0) instanceof Point) {
			Point oldState = (Point) model.getSelectedShapes().get(0);
			dialogPoint.setTxtXEdt(true);
			dialogPoint.setTxtYEdt(true);
			dialogPoint.setTxtX(Integer.toString(oldState.getX()));
			dialogPoint.setTxtY(Integer.toString(oldState.getX()));
			dialogPoint.setColor(oldState.getOutsideColor());
			// dialogPoint.pack();
			dialogPoint.setVisible(true);

			if (dialogPoint.isOk()) {
				Point newState = new Point(Integer.parseInt(dialogPoint.getTxtX()),
						Integer.parseInt(dialogPoint.getTxtY()), dialogPoint.getColor());
				CmdPointUpdate cmd = new CmdPointUpdate(oldState, newState);
				cmd.execute();
				cmdDeque.getUndoDeque().offerLast(cmd);
				frame.getBtnOutsideCol().setBackground(newState.getOutsideColor());
				frame.getTextArea().append(cmd.log() + "\n");
			}
		} else if (model.getSelectedShapes().get(0) instanceof Line) {
			Line oldState = (Line) model.getSelectedShapes().get(0);
			dialogLine.setTxtStartPointXEdt(true);
			dialogLine.setTxtStartPointYEdt(true);
			dialogLine.setTxtEndPointXEdt(true);
			dialogLine.setTxtEndPointYEdt(true);
			dialogLine.setTxtStartPointX(Integer.toString(oldState.getStartPoint().getX()));
			dialogLine.setTxtStartPointY(Integer.toString(oldState.getStartPoint().getY()));
			dialogLine.setTxtEndPointX(Integer.toString(oldState.getEndPoint().getX()));
			dialogLine.setTxtEndPointY(Integer.toString(oldState.getEndPoint().getY()));
			dialogLine.setCol(oldState.getOutsideColor());
			// dialogLine.pack();
			dialogLine.setVisible(true);

			if (dialogLine.isOk()) {
				Line newState = new Line(
						new Point(Integer.parseInt(dialogLine.getTxtStartPointX()),
								Integer.parseInt(dialogLine.getTxtStartPointY())),
						new Point(Integer.parseInt(dialogLine.getTxtEndPointX()),
								Integer.parseInt(dialogLine.getTxtEndPointY())),
						dialogLine.getCol());
				CmdLineUpdate cmd = new CmdLineUpdate(oldState, newState);
				cmd.execute();
				cmdDeque.getUndoDeque().offerLast(cmd);
				frame.getBtnOutsideCol().setBackground(newState.getOutsideColor());
				frame.getTextArea().append(cmd.log() + "\n");
			}
		} else if (model.getSelectedShapes().get(0) instanceof Rectangle) {
			Rectangle oldState = (Rectangle) model.getSelectedShapes().get(0);
			dialogRectangle.setTxtXKoordinataEnabled(true);
			dialogRectangle.setTxtYKoordinataEnabled(true);
			dialogRectangle.setTxtXCoordinate(Integer.toString(oldState.getUpperLeftPoint().getX()));
			dialogRectangle.setTxtYCoordinate(Integer.toString(oldState.getUpperLeftPoint().getY()));
			dialogRectangle.setTxtHeight(Integer.toString(oldState.getHeight()));
			dialogRectangle.setTxtWidth(Integer.toString(oldState.getWidth()));
			dialogRectangle.setBorderColor(oldState.getOutsideColor());
			dialogRectangle.setInsideColor(oldState.getInsideCol());
			dialogRectangle.pack();
			dialogRectangle.setVisible(true);

			if (dialogRectangle.isOk()) {
				Rectangle newState = new Rectangle(
						new Point(Integer.parseInt(dialogRectangle.getTxtXCoordinate()),
								Integer.parseInt(dialogRectangle.getTxtYCoordinate())),
						Integer.parseInt(dialogRectangle.getTxtHeight()),
						Integer.parseInt(dialogRectangle.getTxtWidth()), dialogRectangle.getInsideColor(),
						dialogRectangle.getBorderColor());
				CmdRectangleUpdate cmd = new CmdRectangleUpdate(oldState, newState);
				cmd.execute();
				cmdDeque.getUndoDeque().offerLast(cmd);
				frame.getBtnOutsideCol().setBackground(newState.getOutsideColor());
				frame.getBtnInsideCol().setBackground(newState.getInsideCol());
				frame.getTextArea().append(cmd.log() + "\n");
			}
			// Napomena --> ovde mi je izbacivao dijalog za circle, tako da mora prvo da ide
			// provera da li je u pitanju donut, pa tek onda circle, ako nije
		} else if (model.getSelectedShapes().get(0) instanceof Donut) {
			Donut oldState = (Donut) model.getSelectedShapes().get(0);
			dialogDonut.setTxtXCoordEditable(true);
			dialogDonut.setTxtYCoordEditable(true);
			dialogDonut.setTxtXCoordinate(Integer.toString(oldState.getCenter().getX()));
			dialogDonut.setTxtYCoordinate(Integer.toString(oldState.getCenter().getY()));
			dialogDonut.setTxtInnerRadius(Integer.toString(oldState.getInnerRadius()));
			dialogDonut.setTxtOuterRadius(Integer.toString(oldState.getRadius()));
			dialogDonut.setBorderColor(oldState.getOutsideColor());
			dialogDonut.setInsideColor(oldState.getInsideCol());
			// dialogDonut.pack();
			dialogDonut.setVisible(true);

			if (dialogDonut.isOk()) {
				try {
					Donut newState = new Donut(
							new Point(Integer.parseInt(dialogDonut.getTxtXCoordinate()),
									Integer.parseInt(dialogDonut.getTxtYCoordinate())),
							Integer.parseInt(dialogDonut.getTxtOuterRadius()),
							Integer.parseInt(dialogDonut.getTxtInnerRadius()));
					newState.setOutsideColor(dialogDonut.getBorderColor());
					newState.setInsideCol(dialogDonut.getInsideColor());
					CmdDonutUpdate cmd = new CmdDonutUpdate(oldState, newState);
					cmd.execute();
					cmdDeque.getUndoDeque().offerLast(cmd);
					frame.getBtnOutsideCol().setBackground(newState.getOutsideColor());
					frame.getBtnInsideCol().setBackground(newState.getInsideCol());
					frame.getTextArea().append(cmd.log() + "\n");
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(new JFrame(), "Wrong entry!", "Error", JOptionPane.WARNING_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Inner radius shoud be less than outer radius!",
							"Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		} else if (model.getSelectedShapes().get(0) instanceof Circle) {
			Circle oldState = (Circle) model.getSelectedShapes().get(0);
			dialogCircle.setTxtKoordXEdt(true);
			dialogCircle.setTxtKoordYEdt(true);
			dialogCircle.setTxtXCoordinate(Integer.toString(oldState.getCenter().getX()));
			dialogCircle.setTxtYCoordinate(Integer.toString(oldState.getCenter().getY()));
			dialogCircle.setTxtRadius(Integer.toString(oldState.getRadius()));
			dialogCircle.setInsideColor(oldState.getInsideCol());
			dialogCircle.setBorderColor(oldState.getOutsideColor());
			dialogCircle.pack();
			dialogCircle.setVisible(true);

			if (dialogCircle.isOk()) {
				Circle newState = new Circle(
						new Point(Integer.parseInt(dialogCircle.getTxtXCoordinate()),
								Integer.parseInt(dialogCircle.getTxtYCoordinate())),
						Integer.parseInt(dialogCircle.getTxtRadius()), dialogCircle.getInsideColor(),
						dialogCircle.getBorderColor());
				CmdCircleUpdate cmd = new CmdCircleUpdate(oldState, newState);
				cmd.execute();
				cmdDeque.getUndoDeque().offerLast(cmd);
				frame.getBtnOutsideCol().setBackground(newState.getOutsideColor());
				frame.getBtnInsideCol().setBackground(newState.getInsideCol());
				frame.getTextArea().append(cmd.log() + "\n");
			}
		} else if (model.getSelectedShapes().get(0) instanceof HexagonAdapter) {
			HexagonAdapter oldState = (HexagonAdapter) model.getSelectedShapes().get(0);
			dialogHexagon.setTxtKoordXEdt(true);
			dialogHexagon.setTxtKoordYEdt(true);
			dialogHexagon.setTxtXCoordinate(Integer.toString(oldState.getHexagon().getX()));
			dialogHexagon.setTxtYCoordinate(Integer.toString(oldState.getHexagon().getY()));
			dialogHexagon.setTxtRadius(Integer.toString(oldState.getHexagon().getR()));
			dialogHexagon.setBorderColor(oldState.getHexagon().getBorderColor());
			dialogHexagon.setInsideColor(oldState.getHexagon().getAreaColor());
			dialogHexagon.pack();
			dialogHexagon.setVisible(true);

			if (dialogHexagon.isOk()) {
				HexagonAdapter newState = new HexagonAdapter(
						new Point(Integer.parseInt(dialogHexagon.getTxtXCoordinate()),
								Integer.parseInt(dialogHexagon.getTxtYCoordinate())),
						Integer.parseInt(dialogHexagon.getTxtRadius()), dialogHexagon.getInsideColor(),
						dialogHexagon.getBorderColor());

				CmdHexagonUpdate cmd = new CmdHexagonUpdate(oldState, newState);
				cmd.execute();
				cmdDeque.getUndoDeque().offerLast(cmd);
				frame.getBtnOutsideCol().setBackground(newState.getBorderColor());
				frame.getBtnInsideCol().setBackground(newState.getAreaColor());
				frame.getTextArea().append(cmd.log() + "\n");
			}
		}
		redoCleaner();
		frame.getView().repaint();
		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);
	}

	// toBack
	public void back() {
		CmdToBack cmd = new CmdToBack(model);
		cmd.execute();
		cmdDeque.getUndoDeque().offerLast(cmd);
		frame.getTextArea().append(cmd.log() + "\n");
		redoCleaner();
		frame.getView().repaint();
	}

	// toFront
	public void front() {
		CmdToFront cmd = new CmdToFront(model);
		cmd.execute();
		cmdDeque.getUndoDeque().offerLast(cmd);
		frame.getTextArea().append(cmd.log() + "\n");
		redoCleaner();
		frame.getView().repaint();
	}

	// bringToBack
	public void toBack() {
		CmdBringToBack cmd = new CmdBringToBack(model);
		cmd.execute();
		cmdDeque.getUndoDeque().offerLast(cmd);
		frame.getTextArea().append(cmd.log() + "\n");
		redoCleaner();
		frame.getView().repaint();
	}

	// bringToFront
	public void toFront() {
		CmdBringToFront cmd = new CmdBringToFront(model);
		cmd.execute();
		cmdDeque.getUndoDeque().offerLast(cmd);
		frame.getTextArea().append(cmd.log() + "\n");
		redoCleaner();
		frame.getView().repaint();
	}

	// Edit and delete buttons enabled/disabled
	// Brisanje je moguce kada god postoji neki shape i moguce je brisanje vise
	// oblika odjednom
	// Modifikacija je moguca za samo jedan shape
	public void enablingEditAndDeleteButtons() {
		if (model.getSelectedShapes().size() != 0) {
			// System.out.println(model.getSelectedShapes().size());
			if (model.getSelectedShapes().size() == 1) {
				buttonsObserver.setEditEnabled(true);
				enablingMovingButtons();
			} else {
				buttonsObserver.setEditEnabled(false);

				buttonsObserver.setBringToBackEnabled(false);
				buttonsObserver.setBringToFrontEnabled(false);
				buttonsObserver.setToBackEnabled(false);
				buttonsObserver.setToFrontEnabled(false);
			}
			buttonsObserver.setDeleteEnabled(true);
		} else {
			buttonsObserver.setDeleteEnabled(false);
			buttonsObserver.setEditEnabled(false);

			buttonsObserver.setBringToBackEnabled(false);
			buttonsObserver.setBringToFrontEnabled(false);
			buttonsObserver.setToBackEnabled(false);
			buttonsObserver.setToFrontEnabled(false);
		}
	}

	// Pomeranje po z osi -- enabling/disabling buttons
	public void enablingMovingButtons() {
		ListIterator<Shape> it = model.getShapes().listIterator();
		while (it.hasNext()) {
			Shape selectedShape = it.next();
			if (selectedShape.isSelected()) {
				if (model.getShapes().size() != 1) {
					if (selectedShape.equals(model.get(model.getShapes().size() - 1))) { // najvisa pozicija
						buttonsObserver.setBringToFrontEnabled(false);
						buttonsObserver.setToFrontEnabled(false);
						buttonsObserver.setBringToBackEnabled(true);
						buttonsObserver.setToBackEnabled(true);
					} else if (selectedShape.equals(model.get(0))) { // najniza pozicija
						buttonsObserver.setBringToFrontEnabled(true);
						buttonsObserver.setToFrontEnabled(true);
						buttonsObserver.setBringToBackEnabled(false);
						buttonsObserver.setToBackEnabled(false);
					} else {
						buttonsObserver.setBringToFrontEnabled(true);
						buttonsObserver.setToFrontEnabled(true);
						buttonsObserver.setBringToBackEnabled(true);
						buttonsObserver.setToBackEnabled(true);
					}
				}
			}
		}
	}

	// Undo
	public void undo() {
		if (!cmdDeque.getUndoDeque().isEmpty()) {

			String undoLog = "Undo: " + cmdDeque.getUndoDeque().peekLast().log();
			frame.getTextArea().append(undoLog + "\n");
			cmdDeque.unexecute();

//			if (logCounter != 0) {
//				if (undoLog.contains("Undo: Deleted:")) {
//					CmdShapeSelect cmd = new CmdShapeSelect(model, shape);
//					cmd.execute();
//					frame.getView().repaint();
//				}
//			}

//			try {
//				int end = frame.getTextArea().getDocument().getLength();
//				int start = Utilities.getRowStart(frame.getTextArea(), end);
//				while (start == end) {
//					end--;
//					start = Utilities.getRowStart(frame.getTextArea(), end);
//				}
//
//				String text = frame.getTextArea().getText(start, end - start);
//				frame.getTextArea().append("Undo: " + text + "\n");
//			} catch (BadLocationException e1) { // TODO Auto-generated catch block
//				e1.printStackTrace();
//			}

			enablingEditAndDeleteButtons();
			redoCleaner();
			frame.getView().repaint();

			if (cmdDeque.getUndoDeque().size() == 0) {
				frame.getBtnUndo().setEnabled(false);
			}
		}
	}

	// Redo
	public void redo() {
		if (!cmdDeque.getRedoDeque().isEmpty()) {

			String redoLog = "Redo: " + cmdDeque.getRedoDeque().peekLast().log();
			frame.getTextArea().append(redoLog + "\n");
			cmdDeque.execute();

//			try {
//			int end = frame.getTextArea().getDocument().getLength();
//			int start = Utilities.getRowStart(frame.getTextArea(), end);
//			while (start == end) {
//				end--;
//				start = Utilities.getRowStart(frame.getTextArea(), end);
//			}
//
//			String text = frame.getTextArea().getText(start, end - start);
//			frame.getTextArea().append("Undo: " + text + "\n");
//		} catch (BadLocationException e1) { // TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

			enablingEditAndDeleteButtons();
			frame.getView().repaint();

			if (cmdDeque.getRedoDeque().size() == 0) {
				frame.getBtnRedo().setEnabled(false);
			}
		}
	}

	public void redoCleaner() {
		if (frame.getTextArea().getLineCount() > 2) { // izvrsene barem tri metode
			// Pokupi sve lines iz text area i smesti u array
			String[] lines = frame.getTextArea().getText().split("\n");
			ArrayList<String> linesAsAL = new ArrayList<String>();
			for (String line : lines) {
				linesAsAL.add(line);
			}

			String commandBeforeLast = linesAsAL.get(linesAsAL.size() - 2); // pretposlednja komanda - Undo
			// System.out.println(commandBeforeLast);
			boolean isCommandUndo = commandBeforeLast.startsWith("Undo");

			boolean lastCommand = linesAsAL.get(linesAsAL.size() - 1).contains("Undo:"); // poslednja komanda -
																							// razlicita od Undo
			// System.out.println(lastCommand);

			if (lastCommand == false && isCommandUndo == true) {
				cmdDeque.getRedoDeque().clear();
				System.out.println("Redo: " + cmdDeque.getRedoDeque().size());
			}
		}
	}

	// Save drawing
	public void saveDrawing() {

		ArrayList<Object> shapes = new ArrayList<Object>(); // ovde cuvam sve shapes sa panela
		shapes.add(model.getShapes());

		// Kada cuvam drawing, cuva se i log
		saveManager = new SaveManager(new SaveShapes());
		saveLog = new SaveManager(new SaveLog());

		fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); // File explorer

		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile(); // mesto koje je korisnik kliknuo da zeli da mu se tamo
																// sacuva
			String drawingPath = selectedFile.getAbsolutePath() + ".bin";
			String logPath = selectedFile.getAbsolutePath() + ".txt";

			if (drawingPath != null && logPath != null) {
				saveManager.saveData(shapes, drawingPath);
				saveLog.saveData(frame.getTextArea().getText(), logPath);
			}
		}

	}

	// Save Log
	public void saveLog() {

		String log = frame.getTextArea().getText();

		// saveManager = new SaveManager(new SaveShapes());
		saveLog = new SaveManager(new SaveLog());

		fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); // file explorer
		fileChooser.setAcceptAllFileFilterUsed(false);

		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String logPath = selectedFile.getAbsolutePath() + ".txt";

			if (logPath != null) {
				saveLog.saveData(log, logPath);
			}
		}
	}

	// Load drawing
	@SuppressWarnings("unchecked")
	public void loadDrawing() {

		frame.getTextArea().setText("");
		model.getShapes().clear();
		model.getSelectedShapes().clear();
		cmdDeque.getUndoDeque().clear();
		cmdDeque.getRedoDeque().clear();
		frame.getBtnUndo().setEnabled(false);
		frame.getBtnRedo().setEnabled(false);

		enablingEditAndDeleteButtons();
		enablingMovingButtons();

		frame.getBtnInsideCol().setBackground(SystemColor.control);
		frame.getBtnOutsideCol().setBackground(Color.black);

		loadManager = new LoadManager(new LoadShapes());
		fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		// Posto je drawing tipa .bin --> filtriramo prikaz
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Bin files", "bin");
		fileChooser.setFileFilter(filter);

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String drawingPath = selectedFile.getAbsolutePath();

			@SuppressWarnings({})
			ArrayList<Object> shapes = (ArrayList<Object>) loadManager.loadData(drawingPath);
			for (Shape s : (ArrayList<Shape>) shapes.get(0)) {
				model.add(s);
			}
		}
		frame.getView().repaint();
	}

	// Load log
	public void loadLog() {

		frame.getTextArea().setText("");
		model.getShapes().clear();
		model.getSelectedShapes().clear();
		cmdDeque.getUndoDeque().clear();
		cmdDeque.getRedoDeque().clear();
		frame.getBtnUndo().setEnabled(false);
		frame.getBtnRedo().setEnabled(false);

		enablingMovingButtons();
		enablingEditAndDeleteButtons();

		frame.getBtnExecuteLog().setEnabled(true);

		frame.getBtnInsideCol().setBackground(SystemColor.control);
		frame.getBtnOutsideCol().setBackground(Color.black);

		loadManager = new LoadManager(new LoadLog());
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
		fileChooser.setFileFilter(filter);

		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			String logPath = fileChooser.getSelectedFile().getAbsolutePath();
			// File Reader cita karaktere fajla i prevodi ih iz binarnog jezika u stringove
			// A BufferedReaderu prosledjujemo fileReader i oni rade ZAJEDNO
			FileReader fileReader = (FileReader) loadManager.loadData(logPath);
			BufferedReader bufferLog = new BufferedReader(fileReader);

			try {
				while ((logLine = bufferLog.readLine()) != null) {
					System.out.println(logLine);
					logList.add(logLine);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void executeLog() {
		if (logCounter < logList.size()) {
			logLine = logList.get(logCounter);

			if (logLine.contains("Point")) {
				int x = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
				int y = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
				String color = logLine.substring(logLine.lastIndexOf("(") + 1, logLine.lastIndexOf(")"));
				Color col = new Color(Integer.parseInt(color));
				shape = new Point(x, y, col);
				// CmdPointAdd cmd = new CmdPointAdd(model, (Point) shape); cmd.execute();
			} else if (logLine.contains("Line")) {
				int xS = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
				int yS = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
				int xE = Integer.parseInt(logLine.substring(logLine.indexOf(";") + 3, logLine.lastIndexOf(",")));
				int yE = Integer.parseInt(logLine.substring(logLine.lastIndexOf(",") + 2, logLine.lastIndexOf(")")));
				String color = logLine.substring(logLine.indexOf("{") + 1, logLine.indexOf("}"));
				Color col = new Color(Integer.parseInt(color));
				shape = new Line(new Point(xS, yS), new Point(xE, yE), col);
				// CmdLineAdd cmd = new CmdLineAdd(model, (Line) shape); cmd.execute();
			} else if (logLine.contains("Rectangle")) {
				int x = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
				int y = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
				int h = Integer.parseInt(logLine.substring(logLine.indexOf("=") + 2, logLine.indexOf(";")));
				int w = Integer.parseInt(logLine.substring(logLine.lastIndexOf("=") + 2, logLine.lastIndexOf(";")));
				String insideColor = logLine.substring(logLine.indexOf("{") + 1, logLine.indexOf("}"));
				String outsideColor = logLine.substring(logLine.lastIndexOf("{") + 1, logLine.lastIndexOf("}"));
				Color insCol = new Color(Integer.parseInt(insideColor));
				Color outCol = new Color(Integer.parseInt(outsideColor));
				shape = new Rectangle(new Point(x, y), h, w, insCol, outCol);
				// CmdRectangleAdd cmd = new CmdRectangleAdd(model, (Rectangle) shape);
				// cmd.execute();
			} else if (logLine.contains("Circle")) {
				int x = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
				int y = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
				int r = Integer.parseInt(logLine.substring(logLine.indexOf("=") + 2, logLine.indexOf(";")));
				String insideColor = logLine.substring(logLine.indexOf("{") + 1, logLine.indexOf("}"));
				String outsideColor = logLine.substring(logLine.lastIndexOf("{") + 1, logLine.lastIndexOf("}"));
				Color insCol = new Color(Integer.parseInt(insideColor));
				Color outCol = new Color(Integer.parseInt(outsideColor));
				shape = new Circle(new Point(x, y), r, insCol, outCol);
				// CmdCircleAdd cmd = new CmdCircleAdd(model, (Circle) shape); cmd.execute();
			} else if (logLine.contains("Donut")) {
				int x = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
				int y = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
				int outerR = Integer.parseInt(logLine.substring(logLine.indexOf("=") + 2, logLine.indexOf(";")));
				int innerR = Integer
						.parseInt(logLine.substring(logLine.lastIndexOf("=") + 2, logLine.lastIndexOf(";")));
				String insideColor = logLine.substring(logLine.indexOf("{") + 1, logLine.indexOf("}"));
				String outsideColor = logLine.substring(logLine.lastIndexOf("{") + 1, logLine.lastIndexOf("}"));
				Color insCol = new Color(Integer.parseInt(insideColor));
				Color outCol = new Color(Integer.parseInt(outsideColor));
				try {
					shape = new Donut(new Point(x, y), outerR, innerR, insCol, outCol);
					// CmdDonutAdd cmd = new CmdDonutAdd(model, (Donut) shape); cmd.execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (logLine.contains("Hexagon")) {
				int x = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
				int y = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
				int r = Integer.parseInt(logLine.substring(logLine.indexOf("=") + 2, logLine.indexOf(";")));
				String insideColor = logLine.substring(logLine.indexOf("{") + 1, logLine.indexOf("}"));
				String outsideColor = logLine.substring(logLine.lastIndexOf("{") + 1, logLine.lastIndexOf("}"));
				Color insCol = new Color(Integer.parseInt(insideColor));
				Color outCol = new Color(Integer.parseInt(outsideColor));
				shape = new HexagonAdapter(new Point(x, y), r, insCol, outCol);
				// CmdHexagonAdd cmd = new CmdHexagonAdd(model, (HexagonAdapter) shape);
				// cmd.execute();
			}

			if (logLine.startsWith("Redo")) {
				// System.out.println(cmdDeque.getUndoDeque().size());
				redo();
			} else if (logLine.startsWith("Undo")) {
				// System.out.println(cmdDeque.getUndoDeque().size());
				undo();
				// Ovaj deo je potreban kako bi obezbedili da se nakon ponovnog dodavanja
				// obrisanog oblika pomocu undo obezbedi i njegova selekcija kada se ponovo
				// iscrta na panelu!
				if (cmdDeque.getRedoDeque().peekLast().log().contains("Deleted")) {
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShapes().get(i).equals(shape)) {
							CmdShapeSelect cmd = new CmdShapeSelect(model, model.getShapes().get(i));
							cmd.execute();
						}
					}

				} else if (cmdDeque.getRedoDeque().peekLast().log().contains("Selected")) {
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShapes().get(i).equals(shape)) {
							CmdShapeDeselect cmd = new CmdShapeDeselect(model, model.getShapes().get(i));
							cmd.execute();
						}
					}
				}
			} else if (logLine.contains("Added")) {
				CmdShapeAdd cmd = new CmdShapeAdd(model, shape);
				cmd.execute();

				frame.getTextArea().append(logLine + "\n");
				cmdDeque.getUndoDeque().offerLast(cmd);
			} else if (logLine.contains("Selected")) {
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (shape.equals(model.getShapes().get(i))) {
						shape = model.getShapes().get(i);
					}
				}
				CmdShapeSelect cmd = new CmdShapeSelect(model, shape);
				cmd.execute();

				frame.getTextArea().append(logLine + "\n");
				cmdDeque.getUndoDeque().offerLast(cmd);
			} else if (logLine.contains("Deselected")) {
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (shape.equals(model.getShapes().get(i))) {
						shape = model.getShapes().get(i);
					}
				}
				CmdShapeDeselect cmd = new CmdShapeDeselect(model, shape);
				cmd.execute();

				frame.getTextArea().append(logLine + "\n");
				cmdDeque.getUndoDeque().offerLast(cmd);
			} else if (logLine.contains("Deleted")) {

				CmdShapeRemove cmdRemove = new CmdShapeRemove(model, shape);
				cmdRemove.execute();

				frame.getTextArea().append(logLine + "\n");
				cmdDeque.getUndoDeque().offerLast(cmdRemove);
			} else if (logLine.contains("Edited")) {
				if (shape instanceof Point) {
					Point oldState = (Point) model.getSelectedShapes().get(0);
					int x = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
					int y = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
					String color = logLine.substring(logLine.lastIndexOf("(") + 1, logLine.lastIndexOf(")"));
					Color col = new Color(Integer.parseInt(color));
					Point newState = new Point(x, y, col);

					CmdPointUpdate cmd = new CmdPointUpdate(oldState, newState);
					cmd.execute();

					frame.getTextArea().append(logLine + "\n");
					cmdDeque.getUndoDeque().offerLast(cmd);
				} else if (shape instanceof Line) {
					Line oldState = (Line) model.getSelectedShapes().get(0);
					int xS = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
					int yS = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
					int xE = Integer.parseInt(logLine.substring(logLine.indexOf(";") + 3, logLine.lastIndexOf(",")));
					int yE = Integer
							.parseInt(logLine.substring(logLine.lastIndexOf(",") + 2, logLine.lastIndexOf(")")));
					String color = logLine.substring(logLine.indexOf("{") + 1, logLine.indexOf("}"));
					Color col = new Color(Integer.parseInt(color));
					Line newState = new Line(new Point(xS, yS), new Point(xE, yE), col);

					CmdLineUpdate cmd = new CmdLineUpdate(oldState, newState);
					cmd.execute();

					frame.getTextArea().append(logLine + "\n");
					cmdDeque.getUndoDeque().offerLast(cmd);
				} else if (shape instanceof Rectangle) {
					Rectangle oldState = (Rectangle) model.getSelectedShapes().get(0);
					int x = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
					int y = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
					int h = Integer.parseInt(logLine.substring(logLine.indexOf("=") + 2, logLine.indexOf(";")));
					int w = Integer.parseInt(logLine.substring(logLine.lastIndexOf("=") + 2, logLine.lastIndexOf(";")));
					String insideColor = logLine.substring(logLine.indexOf("{") + 1, logLine.indexOf("}"));
					String outsideColor = logLine.substring(logLine.lastIndexOf("{") + 1, logLine.lastIndexOf("}"));
					Color insCol = new Color(Integer.parseInt(insideColor));
					Color outCol = new Color(Integer.parseInt(outsideColor));
					Rectangle newState = new Rectangle(new Point(x, y), h, w, insCol, outCol);

					CmdRectangleUpdate cmd = new CmdRectangleUpdate(oldState, newState);
					cmd.execute();

					frame.getTextArea().append(logLine + "\n");
					cmdDeque.getUndoDeque().offerLast(cmd);
				} else if (shape instanceof Donut) {
					Donut oldState = (Donut) model.getSelectedShapes().get(0);
					int x = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
					int y = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
					int outerR = Integer.parseInt(logLine.substring(logLine.indexOf("=") + 2, logLine.indexOf(";")));
					int innerR = Integer
							.parseInt(logLine.substring(logLine.lastIndexOf("=") + 2, logLine.lastIndexOf(";")));
					String insideColor = logLine.substring(logLine.indexOf("{") + 1, logLine.indexOf("}"));
					String outsideColor = logLine.substring(logLine.lastIndexOf("{") + 1, logLine.lastIndexOf("}"));
					Color insCol = new Color(Integer.parseInt(insideColor));
					Color outCol = new Color(Integer.parseInt(outsideColor));

					try {
						Donut newState = new Donut(new Point(x, y), outerR, innerR, insCol, outCol);
						CmdDonutUpdate cmd = new CmdDonutUpdate(oldState, newState);
						cmd.execute();
						System.out.println("Boja" + newState.getOutsideColor());

						frame.getTextArea().append(logLine + "\n");
						cmdDeque.getUndoDeque().offerLast(cmd);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (shape instanceof Circle) {
					Circle oldState = (Circle) model.getSelectedShapes().get(0);
					int x = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
					int y = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
					int r = Integer.parseInt(logLine.substring(logLine.indexOf("=") + 2, logLine.indexOf(";")));
					String insideColor = logLine.substring(logLine.indexOf("{") + 1, logLine.indexOf("}"));
					String outsideColor = logLine.substring(logLine.lastIndexOf("{") + 1, logLine.lastIndexOf("}"));

					Color insCol = new Color(Integer.parseInt(insideColor));
					Color outCol = new Color(Integer.parseInt(outsideColor));
					Circle newState = new Circle(new Point(x, y), r, insCol, outCol);

					CmdCircleUpdate cmd = new CmdCircleUpdate(oldState, newState);
					cmd.execute();

					frame.getTextArea().append(logLine + "\n");
					cmdDeque.getUndoDeque().offerLast(cmd);
				} else if (shape instanceof HexagonAdapter) {
					HexagonAdapter oldState = (HexagonAdapter) model.getSelectedShapes().get(0);
					int x = Integer.parseInt(logLine.substring(logLine.indexOf("(") + 1, logLine.indexOf(",")));
					int y = Integer.parseInt(logLine.substring(logLine.indexOf(",") + 2, logLine.indexOf(")")));
					int r = Integer.parseInt(logLine.substring(logLine.indexOf("=") + 2, logLine.indexOf(";")));
					String insideColor = logLine.substring(logLine.indexOf("{") + 1, logLine.indexOf("}"));
					String outsideColor = logLine.substring(logLine.lastIndexOf("{") + 1, logLine.lastIndexOf("}"));
					Color insCol = new Color(Integer.parseInt(insideColor));
					Color outCol = new Color(Integer.parseInt(outsideColor));
					HexagonAdapter newState = new HexagonAdapter(new Point(x, y), r, insCol, outCol);

					CmdHexagonUpdate cmd = new CmdHexagonUpdate(oldState, newState);
					cmd.execute();

					frame.getTextArea().append(logLine + "\n");
					cmdDeque.getUndoDeque().offerLast(cmd);
				}
			} else if (logLine.contains("Backward for one position")) {
				CmdToBack cmd = new CmdToBack(model);
				cmd.execute();

				frame.getTextArea().append(logLine + "\n");
				cmdDeque.getUndoDeque().offerLast(cmd);
			} else if (logLine.contains("Forward for one position")) {
				CmdToFront cmd = new CmdToFront(model);
				cmd.execute();

				frame.getTextArea().append(logLine + "\n");
				cmdDeque.getUndoDeque().offerLast(cmd);
			} else if (logLine.contains("Bring to the back")) {
				CmdBringToBack cmd = new CmdBringToBack(model);
				cmd.execute();

				frame.getTextArea().append(logLine + "\n");
				cmdDeque.getUndoDeque().offerLast(cmd);
			} else if (logLine.contains("Bring to the front")) {
				CmdBringToFront cmd = new CmdBringToFront(model);
				cmd.execute();

				frame.getTextArea().append(logLine + "\n");
				cmdDeque.getUndoDeque().offerLast(cmd);
			}
			enablingEditAndDeleteButtons();

			frame.getView().repaint();
			logCounter += 1; // prelazak na sledecu liniju

			if (logCounter == logList.size()) {
				frame.getBtnExecuteLog().setEnabled(false);
			}
		}

	}
	
	public void clearScreen() {
		model.getShapes().clear();
		model.getSelectedShapes().clear();
		cmdDeque.getUndoDeque().clear();
		cmdDeque.getRedoDeque().clear();
		frame.getTextArea().setText("");
		enablingEditAndDeleteButtons();
		frame.getBtnUndo().setEnabled(false);
		frame.getBtnRedo().setEnabled(false);
		frame.getBtnInsideCol().setBackground(SystemColor.control);
		frame.getBtnOutsideCol().setBackground(Color.black);
		frame.getView().repaint();
	}

	public CmdUndoRedo getCmdDeque() {
		return cmdDeque;
	}

//	public List<Shape> getSelectedShapes() {
//		return selectedShapes;
//	}
//
//	public void setSelectedShapes(List<Shape> selectedShapes) {
//		this.selectedShapes = selectedShapes;
//	}
}