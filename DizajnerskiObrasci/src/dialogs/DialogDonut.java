package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import shapes.Donut;
import shapes.Point;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class DialogDonut extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5068670400460738659L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtXCoordinate;
	private JTextField txtYCoordinate;
	private JTextField txtInnerRadius;
	private JTextField txtOuterRadius;
	private JLabel lblXCoordinate;
	private JLabel lblYCoordinate;
	private JLabel lblInnerRadius;
	private JLabel lblOuterRadius;
	private Color insideColor;
	private Color borderColor;
	private JButton btnInsideColor;
	private JButton btnBorderColor;
	private boolean ok;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogDonut dialog = new DialogDonut();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogDonut() {
		setBounds(100, 100, 521, 517);
		setTitle("Donut");
		getContentPane().setLayout(new BorderLayout());
		setModal(true);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblCenter = new JLabel("Center");
		lblCenter.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 20));
		lblXCoordinate = new JLabel("X coordinate");
		lblXCoordinate.setFont(new Font("Calibri", Font.ITALIC, 20));
		lblYCoordinate = new JLabel("Y coordinate");
		lblYCoordinate.setFont(new Font("Calibri", Font.ITALIC, 20));
		txtXCoordinate = new JTextField();
		txtXCoordinate.setFont(new Font("Calibri", Font.PLAIN, 20));
		txtXCoordinate.setColumns(10);

		txtXCoordinate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == '-' || c == '+' || c == '*' || c == '/') {
					e.consume();
					getToolkit().beep();
				}
			}
		});

		txtYCoordinate = new JTextField();
		txtYCoordinate.setFont(new Font("Calibri", Font.PLAIN, 20));
		txtYCoordinate.setColumns(10);

		txtYCoordinate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == '-' || c == '+' || c == '*' || c == '/') {
					e.consume();
					getToolkit().beep();
				}
			}
		});

		JLabel lblRadius = new JLabel("Radius");
		lblRadius.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 20));
		lblInnerRadius = new JLabel("Inner radius");
		lblInnerRadius.setFont(new Font("Calibri", Font.ITALIC, 20));
		lblOuterRadius = new JLabel("Outer radius");
		lblOuterRadius.setFont(new Font("Calibri", Font.ITALIC, 20));
		txtInnerRadius = new JTextField();
		txtInnerRadius.setFont(new Font("Calibri", Font.PLAIN, 20));
		txtInnerRadius.setColumns(10);

		txtInnerRadius.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == '-' || c == '+' || c == '*' || c == '/') {
					e.consume();
					getToolkit().beep();
				}
			}
		});

		txtOuterRadius = new JTextField();
		txtOuterRadius.setFont(new Font("Calibri", Font.PLAIN, 20));
		txtOuterRadius.setColumns(10);

		txtOuterRadius.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == '-' || c == '+' || c == '*' || c == '/') {
					e.consume();
					getToolkit().beep();
				}
			}
		});

		btnInsideColor = new JButton("Area color");
		btnInsideColor.setFont(new Font("Calibri", Font.PLAIN, 15));
		btnInsideColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insideColor = JColorChooser.showDialog(null, "Choose a color for the inside of the circle:",
						Color.BLACK);
			}
		});

		btnBorderColor = new JButton("Border color");
		btnBorderColor.setFont(new Font("Calibri", Font.PLAIN, 15));
		btnBorderColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borderColor = JColorChooser.showDialog(null, "Choose a color for the inside of the circle:",
						Color.BLACK);
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(lblInnerRadius, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblXCoordinate, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
							.addComponent(lblYCoordinate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(lblOuterRadius, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtOuterRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtYCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtXCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(233, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(160)
					.addComponent(lblCenter)
					.addContainerGap(372, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(156)
					.addComponent(lblRadius, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(415))
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addGap(301)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnBorderColor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnInsideColor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
					.addGap(157))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(31)
					.addComponent(lblCenter)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXCoordinate)
						.addComponent(txtXCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYCoordinate)
						.addComponent(txtYCoordinate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(43)
					.addComponent(lblRadius, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInnerRadius, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtOuterRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOuterRadius, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
					.addComponent(btnInsideColor, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnBorderColor, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Calibri", Font.PLAIN, 11));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							Donut d = new Donut(
									new Point(Integer.parseInt(getTxtXCoordinate()), Integer.parseInt(getTxtYCoordinate())),
									Integer.parseInt(getTxtOuterRadius()), Integer.parseInt(getTxtInnerRadius()));
							setOk(true);
							dispose();
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(new JFrame(),
									"Neispravan unos podataka. Unete vrednosti moraju biti brojevi!", "Greška",
									JOptionPane.WARNING_MESSAGE);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(new JFrame(),
									"Poluprecnici moraju da budu veci od nule i poluprecnik unutrasnjeg kruga mora da bude manji od poluprecnika velikog kruga!",
									"Greška", JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Calibri", Font.PLAIN, 11));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ok = false;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}	

	public String getTxtXCoordinate() {
		return txtXCoordinate.getText();
	}

	public void setTxtXCoordinate(String txtXCoordinate) {
		this.txtXCoordinate.setText(txtXCoordinate);
	}

	public String getTxtYCoordinate() {
		return txtYCoordinate.getText();
	}

	public void setTxtYCoordinate(String txtYCoordinate) {
		this.txtYCoordinate.setText(txtYCoordinate);
	}

	public String getTxtInnerRadius() {
		return txtInnerRadius.getText();
	}

	public void setTxtInnerRadius(String txtInnerRadius) {
		this.txtInnerRadius.setText(txtInnerRadius);
	}

	public String getTxtOuterRadius() {
		return txtOuterRadius.getText();
	}

	public void setTxtOuterRadius(String txtOuterRadius) {
		this.txtOuterRadius.setText(txtOuterRadius);
	}

	public Color getInsideColor() {
		return insideColor;
	}

	public void setInsideColor(Color insideColor) {
		this.insideColor = insideColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public void setTxtXCoordEditable(boolean b) {
		this.txtXCoordinate.setEditable(b);
	}

	public void setTxtYCoordEditable(boolean b) {
		this.txtYCoordinate.setEditable(b);
	}
}