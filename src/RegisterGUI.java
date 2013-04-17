import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtUni;
	private JTextField txtPassword;
	private JTextField txtTimeInMilliseconds;
	private JTextField txtNumberOfTries;
	private JScrollPane scrollPane;
	private JTextArea txtrInputCourseId;
	private Register register;
	private JLabel lblUni;
	private JLabel lblPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0,
				1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 6;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);

		txtrInputCourseId = new JTextArea();
		txtrInputCourseId.setLineWrap(true);
		txtrInputCourseId.setText("Input Course ID Here, following by breaks");
		scrollPane.setViewportView(txtrInputCourseId);

		lblUni = new JLabel("UNI");
		GridBagConstraints gbc_lblUni = new GridBagConstraints();
		gbc_lblUni.insets = new Insets(0, 0, 5, 5);
		gbc_lblUni.gridx = 1;
		gbc_lblUni.gridy = 0;
		contentPane.add(lblUni, gbc_lblUni);

		txtUni = new JTextField();
		GridBagConstraints gbc_txtUni = new GridBagConstraints();
		gbc_txtUni.insets = new Insets(0, 0, 5, 0);
		gbc_txtUni.fill = GridBagConstraints.BOTH;
		gbc_txtUni.gridx = 2;
		gbc_txtUni.gridy = 0;
		contentPane.add(txtUni, gbc_txtUni);
		txtUni.setColumns(10);

		lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 1;
		contentPane.add(lblPassword, gbc_lblPassword);

		txtPassword = new JTextField();
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.insets = new Insets(0, 0, 5, 0);
		gbc_txtPassword.fill = GridBagConstraints.BOTH;
		gbc_txtPassword.gridx = 2;
		gbc_txtPassword.gridy = 1;
		contentPane.add(txtPassword, gbc_txtPassword);
		txtPassword.setColumns(10);

		JLabel lblTimeInterval = new JLabel("Time Interval");
		GridBagConstraints gbc_lblTimeInterval = new GridBagConstraints();
		gbc_lblTimeInterval.fill = GridBagConstraints.VERTICAL;
		gbc_lblTimeInterval.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeInterval.gridx = 1;
		gbc_lblTimeInterval.gridy = 2;
		contentPane.add(lblTimeInterval, gbc_lblTimeInterval);

		txtTimeInMilliseconds = new JTextField();
		GridBagConstraints gbc_txtTimeInMilliseconds = new GridBagConstraints();
		gbc_txtTimeInMilliseconds.insets = new Insets(0, 0, 5, 0);
		gbc_txtTimeInMilliseconds.fill = GridBagConstraints.BOTH;
		gbc_txtTimeInMilliseconds.gridx = 2;
		gbc_txtTimeInMilliseconds.gridy = 2;
		contentPane.add(txtTimeInMilliseconds, gbc_txtTimeInMilliseconds);
		txtTimeInMilliseconds.setColumns(10);

		JLabel lblMaximumTries = new JLabel("Maximum Tries");
		GridBagConstraints gbc_lblMaximumTries = new GridBagConstraints();
		gbc_lblMaximumTries.fill = GridBagConstraints.VERTICAL;
		gbc_lblMaximumTries.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximumTries.gridx = 1;
		gbc_lblMaximumTries.gridy = 3;
		contentPane.add(lblMaximumTries, gbc_lblMaximumTries);

		txtNumberOfTries = new JTextField();
		GridBagConstraints gbc_txtNumberOfTries = new GridBagConstraints();
		gbc_txtNumberOfTries.insets = new Insets(0, 0, 5, 0);
		gbc_txtNumberOfTries.fill = GridBagConstraints.BOTH;
		gbc_txtNumberOfTries.gridx = 2;
		gbc_txtNumberOfTries.gridy = 3;
		contentPane.add(txtNumberOfTries, gbc_txtNumberOfTries);
		txtNumberOfTries.setColumns(10);

		JButton btnGetMeMy = new JButton("Get Me My Lessons");
		btnGetMeMy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_btnGetMeMy = new GridBagConstraints();
		gbc_btnGetMeMy.gridwidth = 2;
		gbc_btnGetMeMy.fill = GridBagConstraints.BOTH;
		gbc_btnGetMeMy.gridheight = 2;
		gbc_btnGetMeMy.gridx = 1;
		gbc_btnGetMeMy.gridy = 4;
		contentPane.add(btnGetMeMy, gbc_btnGetMeMy);

		ButtonListener buttonListener = new ButtonListener();
		btnGetMeMy.addActionListener(buttonListener);
	}

	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<Integer> courses = new ArrayList<Integer>();
			String username = txtUni.getText();
			String password = txtPassword.getText();
			long millis = Long.parseLong(txtTimeInMilliseconds.getText());
			int tries = Integer.parseInt(txtNumberOfTries.getText());
			Scanner scan = new Scanner(txtrInputCourseId.getText());
			while (scan.hasNextLine() == true) {
				courses.add(Integer.parseInt(scan.nextLine()));
			}
			register = new Register(username, password, courses,
					"https://ssol.columbia.edu", millis, tries);
		}
	}

}
