package mvc;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.Dimension;
import java.awt.ComponentOrientation;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.ScrollPaneConstants;

public class DrawingFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5812470532038572158L;
	private DrawingView view = new DrawingView();
	private DrawingController controller;

	private JToggleButton btnPoint;
	private JToggleButton btnLine;
	private JToggleButton btnRect;
	private JToggleButton btnHexa;
	private JToggleButton btnCircle;
	private JToggleButton btnDonut;

	private JButton btnFront;
	private JButton btnBack;
	private JButton btnToFront;
	private JButton btnToBack;
	private JToggleButton btnSelect;
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnUndo;
	private JButton btnRedo;
	private JPanel northPanel;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem savePainting;
	private JMenuItem saveLog;
	private JMenuItem loadPainting;
	private JMenuItem loadLog;
	private JButton btnOutsideCol;
	private JButton btnInsideCol;
	private JButton btnExecuteLog;
	private JSeparator separator;
	private JSeparator separator_1;
	private JButton btnClearScreen;

	public JToggleButton getBtnPoint() {
		return btnPoint;
	}

	public JToggleButton getBtnLine() {
		return btnLine;
	}

	public JToggleButton getBtnRect() {
		return btnRect;
	}

	public JToggleButton getBtnHexa() {
		return btnHexa;
	}

	public JToggleButton getBtnCircle() {
		return btnCircle;
	}

	public JToggleButton getBtnDonut() {
		return btnDonut;
	}

	public JButton getBtnFront() {
		return btnFront;
	}

	public JButton getBtnBack() {
		return btnBack;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public JToggleButton getBtnSelect() {
		return btnSelect;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnEdit() {
		return btnEdit;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public JMenuItem getSavePainting() {
		return savePainting;
	}

	public JMenuItem getSaveLog() {
		return saveLog;
	}

	public JMenuItem getLoadPainting() {
		return loadPainting;
	}

	public JMenuItem getLoadLog() {
		return loadLog;
	}

	public JButton getBtnOutsideCol() {
		return btnOutsideCol;
	}

	public JButton getBtnInsideCol() {
		return btnInsideCol;
	}

	public JButton getBtnExecuteLog() {
		return btnExecuteLog;
	}

	public DrawingFrame() {
		setTitle("IT4-2017 Nina Kozma");
		view.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

				controller.mouseClicked(arg0);

			}
		});
		getContentPane().add(view, BorderLayout.CENTER);
		view.setLayout(new BorderLayout(0, 0));

		ButtonGroup buttonGroup = new ButtonGroup();

		JToolBar toolBar = new JToolBar();
		toolBar.setMaximumSize(new Dimension(67, 2));
		toolBar.setOrientation(SwingConstants.VERTICAL);
		view.add(toolBar, BorderLayout.WEST);

		btnPoint = new JToggleButton("Point");
		btnPoint.setPreferredSize(new Dimension(75, 25));
		btnPoint.setMinimumSize(new Dimension(75, 25));
		btnPoint.setMaximumSize(new Dimension(75, 25));
		btnPoint.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toolBar.add(btnPoint);
		buttonGroup.add(btnPoint);

		btnLine = new JToggleButton("Line");
		btnLine.setPreferredSize(new Dimension(75, 25));
		btnLine.setMinimumSize(new Dimension(75, 25));
		btnLine.setMaximumSize(new Dimension(75, 25));
		toolBar.add(btnLine);
		buttonGroup.add(btnLine);

		btnRect = new JToggleButton("Rectangle");
		btnRect.setPreferredSize(new Dimension(75, 25));
		btnRect.setMinimumSize(new Dimension(75, 25));
		btnRect.setMaximumSize(new Dimension(75, 25));
		toolBar.add(btnRect);
		buttonGroup.add(btnRect);

		btnHexa = new JToggleButton("Hexagon");
		btnHexa.setPreferredSize(new Dimension(75, 25));
		btnHexa.setMinimumSize(new Dimension(75, 25));
		btnHexa.setMaximumSize(new Dimension(75, 25));
		toolBar.add(btnHexa);
		buttonGroup.add(btnHexa);

		btnCircle = new JToggleButton("Circle");
		btnCircle.setPreferredSize(new Dimension(75, 25));
		btnCircle.setMinimumSize(new Dimension(75, 25));
		btnCircle.setMaximumSize(new Dimension(75, 25));
		toolBar.add(btnCircle);
		buttonGroup.add(btnCircle);

		btnDonut = new JToggleButton("Donut");
		btnDonut.setPreferredSize(new Dimension(75, 25));
		btnDonut.setMinimumSize(new Dimension(75, 25));
		btnDonut.setMaximumSize(new Dimension(75, 25));
		toolBar.add(btnDonut);
		buttonGroup.add(btnDonut);
		
		separator_1 = new JSeparator();
		toolBar.add(separator_1);
		
		btnClearScreen = new JButton("Clear screen");
		btnClearScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clearScreen();
			}
		});
		toolBar.add(btnClearScreen);

		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setMinimumSize(new Dimension(67, 25));
		toolBar_1.setMaximumSize(new Dimension(67, 25));
		toolBar_1.setOrientation(SwingConstants.VERTICAL);
		view.add(toolBar_1, BorderLayout.EAST);

		btnFront = new JButton("Front");
		btnFront.setPreferredSize(new Dimension(75, 25));
		btnFront.setMinimumSize(new Dimension(75, 25));
		btnFront.setMaximumSize(new Dimension(75, 25));
		btnFront.setEnabled(false);
		btnFront.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.front();
				controller.enablingMovingButtons();
			}
		});
		toolBar_1.add(btnFront);

		btnBack = new JButton("Back");
		btnBack.setPreferredSize(new Dimension(75, 25));
		btnBack.setMinimumSize(new Dimension(75, 25));
		btnBack.setMaximumSize(new Dimension(75, 25));
		btnBack.setEnabled(false);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.back();
				controller.enablingMovingButtons();
			}
		});
		toolBar_1.add(btnBack);

		btnToFront = new JButton("To Front");
		btnToFront.setPreferredSize(new Dimension(75, 25));
		btnToFront.setMinimumSize(new Dimension(75, 25));
		btnToFront.setMaximumSize(new Dimension(75, 25));
		btnToFront.setEnabled(false);
		btnToFront.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
				controller.enablingMovingButtons();
			}
		});
		toolBar_1.add(btnToFront);

		btnToBack = new JButton("To Back");
		btnToBack.setPreferredSize(new Dimension(75, 25));
		btnToBack.setMinimumSize(new Dimension(75, 25));
		btnToBack.setMaximumSize(new Dimension(75, 25));
		btnToBack.setEnabled(false);
		btnToBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
				controller.enablingMovingButtons();
			}
		});
		toolBar_1.add(btnToBack);

		separator = new JSeparator();
		toolBar_1.add(separator);

		btnExecuteLog = new JButton("Execute Log");
		btnExecuteLog.setEnabled(false);
		btnExecuteLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.executeLog();
			}
		});
		toolBar_1.add(btnExecuteLog);

		JPanel panel = new JPanel();
		view.add(panel, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setPreferredSize(new Dimension(400, 1000));
		textArea.setEditable(false);

		northPanel = new JPanel();
		northPanel.setMinimumSize(new Dimension(100, 100));
		view.add(northPanel, BorderLayout.NORTH);

		btnSelect = new JToggleButton("Select");
		btnSelect.setPreferredSize(new Dimension(70, 25));
		btnSelect.setMinimumSize(new Dimension(70, 25));
		btnSelect.setMaximumSize(new Dimension(70, 25));
		northPanel.add(btnSelect);
		buttonGroup.add(btnSelect);

		btnEdit = new JButton("Edit");
		btnEdit.setPreferredSize(new Dimension(60, 25));
		btnEdit.setMinimumSize(new Dimension(60, 25));
		btnEdit.setMaximumSize(new Dimension(60, 25));
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.edit(e);
			}
		});
		northPanel.add(btnEdit);
		buttonGroup.add(btnEdit);

		btnDelete = new JButton("Delete");
		btnDelete.setPreferredSize(new Dimension(75, 25));
		btnDelete.setMinimumSize(new Dimension(75, 25));
		btnDelete.setMaximumSize(new Dimension(75, 25));
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.delete(e);
			}
		});
		northPanel.add(btnDelete);
		buttonGroup.add(btnDelete);

		btnUndo = new JButton("Undo");
		btnUndo.setEnabled(false);
		btnUndo.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				controller.undo();
				btnRedo.setEnabled(true);
			}
		});
		northPanel.add(btnUndo);

		btnRedo = new JButton("Redo");
		btnRedo.setEnabled(false);
		btnRedo.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				controller.redo();
				btnUndo.setEnabled(true);
			}
		});
		northPanel.add(btnRedo);

		btnInsideCol = new JButton("Inside color");
		btnInsideCol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnInsideCol.setBackground(JColorChooser.showDialog(null, "Choose inside color", Color.white));
				if (btnInsideCol.getBackground().equals(Color.BLACK)) {
					btnInsideCol.setForeground(Color.WHITE);
				}
			}
		});
		btnInsideCol.setBackground(SystemColor.control);
		northPanel.add(btnInsideCol);

		btnOutsideCol = new JButton("Outside color");
		btnOutsideCol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOutsideCol.setBackground(JColorChooser.showDialog(null, "Choose outside color", Color.black));
				if (btnOutsideCol.getBackground().equals(Color.white)) {
					btnOutsideCol.setForeground(Color.black);
				}
			}
		});
		btnOutsideCol.setForeground(Color.WHITE);
		btnOutsideCol.setBackground(Color.BLACK);
		northPanel.add(btnOutsideCol);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setAutoscrolls(true);
		scrollPane.setPreferredSize(new Dimension(20, 64));
		scrollPane.setViewportView(textArea);
		view.add(scrollPane, BorderLayout.SOUTH);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menu = new JMenu("Working with files");
		menuBar.add(menu);

		savePainting = new JMenuItem("Save drawing");
		savePainting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveDrawing();
			}
		});
		menu.add(savePainting);

		saveLog = new JMenuItem("Save log");
		saveLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		menu.add(saveLog);

		loadPainting = new JMenuItem("Load drawing");
		loadPainting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadDrawing();
			}
		});
		menu.add(loadPainting);

		loadLog = new JMenuItem("Load log");
		loadLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadLog();
			}
		});
		menu.add(loadLog);
	}

	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
}