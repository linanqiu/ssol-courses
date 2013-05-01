import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.Frame;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JTree;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JComboBox;


public class SSOLFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtSectionSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SSOLFrame frame = new SSOLFrame();
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
	public SSOLFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 712, 602);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 55, 55, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblCurrentSections = new JLabel("Current Sections");
		GridBagConstraints gbc_lblCurrentSections = new GridBagConstraints();
		gbc_lblCurrentSections.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentSections.gridx = 0;
		gbc_lblCurrentSections.gridy = 0;
		contentPane.add(lblCurrentSections, gbc_lblCurrentSections);
		
		JLabel lblSectionListing = new JLabel("Section Listing");
		GridBagConstraints gbc_lblSectionListing = new GridBagConstraints();
		gbc_lblSectionListing.gridwidth = 2;
		gbc_lblSectionListing.insets = new Insets(0, 0, 5, 0);
		gbc_lblSectionListing.gridx = 1;
		gbc_lblSectionListing.gridy = 0;
		contentPane.add(lblSectionListing, gbc_lblSectionListing);
		
		txtSectionSearch = new JTextField();
		txtSectionSearch.setText("Section Search");
		GridBagConstraints gbc_txtSectionSearch = new GridBagConstraints();
		gbc_txtSectionSearch.insets = new Insets(0, 0, 5, 5);
		gbc_txtSectionSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSectionSearch.gridx = 1;
		gbc_txtSectionSearch.gridy = 1;
		contentPane.add(txtSectionSearch, gbc_txtSectionSearch);
		txtSectionSearch.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JList listCurrentSections = new JList();
		scrollPane.setViewportView(listCurrentSections);
		
		JButton btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearch.gridx = 2;
		gbc_btnSearch.gridy = 1;
		contentPane.add(btnSearch, gbc_btnSearch);
		
		JComboBox comboBoxDepartment = new JComboBox();
		GridBagConstraints gbc_comboBoxDepartment = new GridBagConstraints();
		gbc_comboBoxDepartment.gridwidth = 2;
		gbc_comboBoxDepartment.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxDepartment.fill = GridBagConstraints.BOTH;
		gbc_comboBoxDepartment.gridx = 1;
		gbc_comboBoxDepartment.gridy = 2;
		contentPane.add(comboBoxDepartment, gbc_comboBoxDepartment);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.gridwidth = 2;
		gbc_scrollPane_2.gridheight = 4;
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 3;
		contentPane.add(scrollPane_2, gbc_scrollPane_2);
		
		JTree tree = new JTree();
		scrollPane_2.setViewportView(tree);
		
		JLabel lblSectionsToBe = new JLabel("Sections To Be Added");
		GridBagConstraints gbc_lblSectionsToBe = new GridBagConstraints();
		gbc_lblSectionsToBe.insets = new Insets(0, 0, 5, 5);
		gbc_lblSectionsToBe.gridx = 0;
		gbc_lblSectionsToBe.gridy = 4;
		contentPane.add(lblSectionsToBe, gbc_lblSectionsToBe);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 5;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);
		
		JList listSectionsToAdd = new JList();
		scrollPane_1.setViewportView(listSectionsToAdd);
	}

}
