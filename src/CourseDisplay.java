import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JSeparator;


public class CourseDisplay extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblCourseNumber;
	private JLabel lblDescription;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private JLabel lblNewLabel;
	private JTextField textField_2;
	private JSeparator separator;
	private JLabel lblInstructor;
	private JTextField textField_3;
	private JLabel lblCallNumber;
	private JTextField textField_4;
	private JLabel lblTime;
	private JTextField textField_5;
	private JLabel lblLocation;
	private JTextField textField_6;
	private JLabel lblRegistration;
	private JTextField textField_7;
	private JLabel lblNewLabel_1;
	private JSeparator separator_1;
	private JButton btnNewButton;

	@SuppressWarnings("unused")
	private CourseDisplay() {
		setTitle("Section Information");
		getContentPane().setLayout(new MigLayout("", "[116.00px,left][353px,grow,left][89.00px,right][353px,grow,right]", "[28px][16px][87.00][][][][][][][]"));
		
		lblCourseNumber = new JLabel("Course Number");
		getContentPane().add(lblCourseNumber, "cell 0 0,alignx center,aligny center");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 1 0 3 1,growx,aligny top");
		textField.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title");
		getContentPane().add(lblTitle, "cell 0 1,alignx center,aligny center");
		
		textField_1 = new JTextField();
		getContentPane().add(textField_1, "cell 1 1 3 1,growx");
		textField_1.setColumns(10);
		
		lblDescription = new JLabel("Description");
		getContentPane().add(lblDescription, "cell 0 2,alignx center,aligny center");
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 1 2 3 1,grow");
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		lblNewLabel = new JLabel("Credits");
		getContentPane().add(lblNewLabel, "cell 0 3,alignx center,aligny center");
		
		textField_2 = new JTextField();
		getContentPane().add(textField_2, "cell 1 3 3 1,growx");
		textField_2.setColumns(10);
		
		separator = new JSeparator();
		getContentPane().add(separator, "cell 0 4 4 1");
		
		lblInstructor = new JLabel("Instructor");
		getContentPane().add(lblInstructor, "cell 0 5,alignx center,aligny center");
		
		textField_3 = new JTextField();
		getContentPane().add(textField_3, "cell 1 5,growx");
		textField_3.setColumns(10);
		
		lblCallNumber = new JLabel("Call Number");
		getContentPane().add(lblCallNumber, "cell 2 5,alignx trailing,aligny center");
		
		textField_4 = new JTextField();
		getContentPane().add(textField_4, "cell 3 5,growx");
		textField_4.setColumns(10);
		
		lblTime = new JLabel("Time");
		getContentPane().add(lblTime, "cell 0 6,alignx center,aligny center");
		
		textField_5 = new JTextField();
		getContentPane().add(textField_5, "cell 1 6,growx");
		textField_5.setColumns(10);
		
		lblLocation = new JLabel("Location");
		getContentPane().add(lblLocation, "cell 2 6,alignx center,aligny center");
		
		textField_6 = new JTextField();
		getContentPane().add(textField_6, "cell 3 6,growx");
		textField_6.setColumns(10);
		
		lblRegistration = new JLabel("Registration");
		getContentPane().add(lblRegistration, "cell 0 7,alignx center,aligny center");
		
		textField_7 = new JTextField();
		getContentPane().add(textField_7, "cell 1 7 2 1,growx");
		textField_7.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Full");
		getContentPane().add(lblNewLabel_1, "cell 3 7,alignx center,aligny center");
		
		separator_1 = new JSeparator();
		getContentPane().add(separator_1, "cell 1 8");
		
		btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		getContentPane().add(btnNewButton, "cell 0 9 4 1,alignx center,aligny center");
	}
	
	/**
	 * Create the frame.
	 */
	public CourseDisplay(JFrame parent, CourseText info) {
		setTitle("Section Information\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
	}
}
