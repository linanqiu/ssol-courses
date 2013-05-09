import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;


public class CourseDisplay extends JFrame {

	private JPanel contentPane;
	private JTextField courseNumber;
	private JTextField title;
	private JLabel lblCourseNumber;
	private JLabel lblDescription;
	private JScrollPane scrollPane;
	private JTextPane description;
	private JLabel lblNewLabel;
	private JTextField credits;
	private JSeparator separator;
	private JLabel lblInstructor;
	private JTextField instructor;
	private JLabel lblCallNumber;
	private JTextField callNumber;
	private JLabel lblTime;
	private JTextField time;
	private JLabel lblLocation;
	private JTextField location;
	private JLabel lblRegistration;
	private JTextField registration;
	private JLabel full;
	private JSeparator seperator1;
	private JButton btnNewButton;

	private CourseDisplay() {
		setResizable(false);
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setTitle("Section Information");
		getContentPane().setLayout(new MigLayout("", "[116.00px,left][353px,grow,left][89.00px,right][353px,grow,right]", "[28px][16px][87.00][][][][][][][]"));
		
		lblCourseNumber = new JLabel("Course Number");
		getContentPane().add(lblCourseNumber, "cell 0 0,alignx center,aligny center");
		
		courseNumber = new JTextField();
		courseNumber.setEditable(false);
		courseNumber.setHorizontalAlignment(SwingConstants.LEFT);
		getContentPane().add(courseNumber, "cell 1 0 3 1,growx,aligny top");
		courseNumber.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title");
		getContentPane().add(lblTitle, "cell 0 1,alignx center,aligny center");
		
		title = new JTextField();
		title.setEditable(false);
		getContentPane().add(title, "cell 1 1 3 1,growx");
		title.setColumns(10);
		
		lblDescription = new JLabel("Description");
		getContentPane().add(lblDescription, "cell 0 2,alignx center,aligny center");
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 1 2 3 1,grow");
		
		description = new JTextPane();
		description.setEditable(false);
		scrollPane.setViewportView(description);
		
		lblNewLabel = new JLabel("Credits");
		getContentPane().add(lblNewLabel, "cell 0 3,alignx center,aligny center");
		
		credits = new JTextField();
		credits.setEditable(false);
		getContentPane().add(credits, "cell 1 3 3 1,growx");
		credits.setColumns(10);
		
		separator = new JSeparator();
		getContentPane().add(separator, "cell 0 4 4 1");
		
		lblInstructor = new JLabel("Instructor");
		getContentPane().add(lblInstructor, "cell 0 5,alignx center,aligny center");
		
		instructor = new JTextField();
		instructor.setEditable(false);
		getContentPane().add(instructor, "cell 1 5,growx");
		instructor.setColumns(10);
		
		lblCallNumber = new JLabel("Call Number");
		getContentPane().add(lblCallNumber, "cell 2 5,alignx trailing,aligny center");
		
		callNumber = new JTextField();
		callNumber.setEnabled(false);
		getContentPane().add(callNumber, "cell 3 5,growx");
		callNumber.setColumns(10);
		
		lblTime = new JLabel("Time");
		getContentPane().add(lblTime, "cell 0 6,alignx center,aligny center");
		
		time = new JTextField();
		time.setEditable(false);
		getContentPane().add(time, "cell 1 6,growx");
		time.setColumns(10);
		
		lblLocation = new JLabel("Location");
		getContentPane().add(lblLocation, "cell 2 6,alignx center,aligny center");
		
		location = new JTextField();
		location.setEditable(false);
		getContentPane().add(location, "cell 3 6,growx");
		location.setColumns(10);
		
		lblRegistration = new JLabel("Registration");
		getContentPane().add(lblRegistration, "cell 0 7,alignx center,aligny center");
		
		registration = new JTextField();
		registration.setEditable(false);
		registration.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(registration, "cell 1 7 2 1,growx");
		registration.setColumns(10);
		
		full = new JLabel();
		full.setOpaque(true);
		getContentPane().add(full, "cell 3 7,alignx center,aligny center");
		
		seperator1 = new JSeparator();
		getContentPane().add(seperator1, "cell 1 8");
		
		btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CourseDisplay.this.dispose();
			}
		});
		getContentPane().add(btnNewButton, "cell 0 9 4 1,alignx center,aligny center");
	}
	
	/**
	 * Create the frame with course info
	 */
	public CourseDisplay(JFrame parent, CourseText info) {
		this();
		
		courseNumber.setText(info.getCourseNumber());
		title.setText(info.getTitle());
		description.setText(info.getDescription());
		credits.setText(String.valueOf(info.getCredits()/10.0));
		
		if (info.getInstructor()!=null)
			instructor.setText(info.getInstructor());
		else
			instructor.setEnabled(false);
		
		if (info.getCallNumber()!=null)
			callNumber.setText(info.getCallNumber().toString());
		else
			callNumber.setEnabled(false);
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		if (info.getMeetDay()!=null)
			time.setText(info.getMeetDay()+" "+formatter.format(info.getStartTime())+"-"+formatter.format(info.getEndTime()));
		else
			time.setEnabled(false);
		
		if (info.getLocation()!=null)
			location.setText(info.getLocation());
		else
			location.setEnabled(false);
		
		if (info.getNumEnroll()!=null)
		{
			int numEnroll = info.getNumEnroll();
			int maxSize = info.getMaxSize();
			registration.setText(String.valueOf(numEnroll)+"/"+String.valueOf(maxSize));
			if (numEnroll >= maxSize)
			{
				full.setText("Full");
				full.setBackground(Color.RED);
			}
			else
			{
				System.out.println("Set");
				full.setText("Not Full");
				full.setBackground(Color.GREEN);
			}
		}
		else
		{
			full.setText("");
			registration.setEnabled(false);
		}
		
		this.setLocationRelativeTo(parent);
		setVisible(true);
	}
}
