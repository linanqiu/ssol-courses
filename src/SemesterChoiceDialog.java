import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SemesterChoiceDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private ArrayList<String> semesterOptions;
	private JComboBox comboBox;
	private JButton okButton;
	private String semesterChoice;

	/**
	 * Create the dialog.
	 */
	public SemesterChoiceDialog() {
		setAlwaysOnTop(true);

		semesterChoice = "";

		setBounds(100, 100, 355, 103);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblSemesterChoice = new JLabel("Semester Choice:");
			GridBagConstraints gbc_lblSemesterChoice = new GridBagConstraints();
			gbc_lblSemesterChoice.insets = new Insets(0, 0, 0, 5);
			gbc_lblSemesterChoice.anchor = GridBagConstraints.EAST;
			gbc_lblSemesterChoice.gridx = 0;
			gbc_lblSemesterChoice.gridy = 0;
			contentPanel.add(lblSemesterChoice, gbc_lblSemesterChoice);
		}
		{
			comboBox = new JComboBox();
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 0;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		okButton.addActionListener(new ButtonListener());
	}

	/**
	 * allows this dialog to be fed the options for the semester
	 * 
	 * @param semesterOptions
	 *            the options that the user has
	 */
	public void setSemesterOptions(ArrayList<String> semesterOptions) {
		this.semesterOptions = semesterOptions;
		for (String semester : semesterOptions) {
			String textSemester = "";
			if (semester.substring(4, 5).equals("2")) {
				textSemester = semester + " - " + semester.substring(0, 4)
						+ " Summer";
			} else if (semester.substring(4, 5).equals("3")) {
				textSemester = semester + " - " + semester.substring(0, 4)
						+ " Fall";
			} else if (semester.substring(4, 5).equals("1")) {
				textSemester = semester + " - " + semester.substring(0, 4)
						+ " Spring";
			}

			comboBox.addItem(textSemester);
			System.out.println("setSemesterOptions: " + semester + " added");
		}
	}

	/**
	 * @return semesterChoice the choice that the user picked
	 */
	public String getSemesterChoice() {
		return semesterChoice;
	}

	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == okButton) {
				semesterChoice = semesterOptions.get(comboBox
						.getSelectedIndex());
				System.out.println("semesterChoiceDialog: " + semesterChoice
						+ " selected");
				SemesterChoiceDialog.this.setVisible(false);
			}
		}

	}
}
