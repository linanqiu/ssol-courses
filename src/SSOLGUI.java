import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;


import java.awt.Color;
import java.awt.SystemColor;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import java.awt.Dimension;

public class SSOLGUI {

	private JFrame frame;
	private JTextField txtSearch;
	private SSOLController ssolController;
	private LoginDialog loginDialog;
	private SemesterChoiceDialog semesterChoiceDialog;
	private JButton btnStart;
	private JButton buttonAddSection;
	private JButton btnStop;
	private JButton btnSearch;
	private JButton buttonRemoveSection;
	private JLabel lblUserUni;
	private JLabel lblSemesterChoice;
	private JLabel lblBlockStatusIndicator;
	public static final int[] KONAMI = { KeyEvent.VK_UP, KeyEvent.VK_UP,
			KeyEvent.VK_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
			KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
			KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_ENTER };
	private JLabel lblHugeShoutoutsTo;
	private JLabel lblLinanQiu;
	private JLabel lblXingzhouHe;
	private JPanel panelCredits;
	private JList listExistingSections;
	private DefaultListModel sectionsToAddModel;
	private ArrayList<Section> sectionsToAddList;
	private JComboBox comboBoxDepartment;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SSOLGUI window = new SSOLGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SSOLGUI() {
		SwingWorker departmentSwingWorker = new DepartmentSwingWorker();
		departmentSwingWorker.execute();
		sectionsToAddModel = new DefaultListModel();
		initialize();
		ssolController = new SSOLController();
		loginAndSemesterChoice();
		SwingWorker<Void, Void> blockCheck = new BlockCheckSwingWorker();
		blockCheck.execute();
		buildCurrentSections();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		loginDialog = new LoginDialog();
		semesterChoiceDialog = new SemesterChoiceDialog();

		frame = new JFrame();
		frame.setMinimumSize(new Dimension(640, 480));
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 189, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JPanel panelPersonalInformation = new JPanel();
		panelPersonalInformation.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		GridBagConstraints gbc_panelPersonalInformation = new GridBagConstraints();
		gbc_panelPersonalInformation.insets = new Insets(10, 10, 10, 10);
		gbc_panelPersonalInformation.fill = GridBagConstraints.BOTH;
		gbc_panelPersonalInformation.gridx = 0;
		gbc_panelPersonalInformation.gridy = 0;
		frame.getContentPane().add(panelPersonalInformation,
				gbc_panelPersonalInformation);
		GridBagLayout gbl_panelPersonalInformation = new GridBagLayout();
		gbl_panelPersonalInformation.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelPersonalInformation.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelPersonalInformation.columnWeights = new double[] { 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_panelPersonalInformation.rowWeights = new double[] { 0.0, 1.0, 1.0,
				1.0, Double.MIN_VALUE };
		panelPersonalInformation.setLayout(gbl_panelPersonalInformation);

		JLabel lblPersonalInformation = new JLabel("Personal Information");
		GridBagConstraints gbc_lblPersonalInformation = new GridBagConstraints();
		gbc_lblPersonalInformation.gridwidth = 2;
		gbc_lblPersonalInformation.insets = new Insets(0, 0, 5, 0);
		gbc_lblPersonalInformation.gridx = 0;
		gbc_lblPersonalInformation.gridy = 0;
		panelPersonalInformation.add(lblPersonalInformation,
				gbc_lblPersonalInformation);

		JLabel lblUni = new JLabel("UNI:");
		GridBagConstraints gbc_lblUni = new GridBagConstraints();
		gbc_lblUni.anchor = GridBagConstraints.EAST;
		gbc_lblUni.insets = new Insets(0, 0, 5, 5);
		gbc_lblUni.gridx = 0;
		gbc_lblUni.gridy = 1;
		panelPersonalInformation.add(lblUni, gbc_lblUni);

		lblUserUni = new JLabel("User UNI");
		GridBagConstraints gbc_lblUserUni = new GridBagConstraints();
		gbc_lblUserUni.insets = new Insets(0, 0, 5, 0);
		gbc_lblUserUni.gridx = 1;
		gbc_lblUserUni.gridy = 1;
		panelPersonalInformation.add(lblUserUni, gbc_lblUserUni);

		JLabel lblSemester = new JLabel("Semester:");
		GridBagConstraints gbc_lblSemester = new GridBagConstraints();
		gbc_lblSemester.anchor = GridBagConstraints.EAST;
		gbc_lblSemester.insets = new Insets(0, 0, 5, 5);
		gbc_lblSemester.gridx = 0;
		gbc_lblSemester.gridy = 2;
		panelPersonalInformation.add(lblSemester, gbc_lblSemester);

		lblSemesterChoice = new JLabel("Semester Choice");
		GridBagConstraints gbc_lblSemesterChoice = new GridBagConstraints();
		gbc_lblSemesterChoice.insets = new Insets(0, 0, 5, 0);
		gbc_lblSemesterChoice.gridx = 1;
		gbc_lblSemesterChoice.gridy = 2;
		panelPersonalInformation.add(lblSemesterChoice, gbc_lblSemesterChoice);

		JLabel lblBlockStatus = new JLabel("Block Status:");
		GridBagConstraints gbc_lblBlockStatus = new GridBagConstraints();
		gbc_lblBlockStatus.anchor = GridBagConstraints.EAST;
		gbc_lblBlockStatus.insets = new Insets(0, 0, 0, 5);
		gbc_lblBlockStatus.gridx = 0;
		gbc_lblBlockStatus.gridy = 3;
		panelPersonalInformation.add(lblBlockStatus, gbc_lblBlockStatus);

		lblBlockStatusIndicator = new JLabel("Block Status");
		GridBagConstraints gbc_lblBlockStatusIndicator = new GridBagConstraints();
		gbc_lblBlockStatusIndicator.gridx = 1;
		gbc_lblBlockStatusIndicator.gridy = 3;
		panelPersonalInformation.add(lblBlockStatusIndicator,
				gbc_lblBlockStatusIndicator);

		JPanel panelExistingSections = new JPanel();
		panelExistingSections.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		GridBagConstraints gbc_panelExistingSections = new GridBagConstraints();
		gbc_panelExistingSections.insets = new Insets(10, 10, 10, 10);
		gbc_panelExistingSections.fill = GridBagConstraints.BOTH;
		gbc_panelExistingSections.gridx = 1;
		gbc_panelExistingSections.gridy = 0;
		frame.getContentPane().add(panelExistingSections,
				gbc_panelExistingSections);
		GridBagLayout gbl_panelExistingSections = new GridBagLayout();
		gbl_panelExistingSections.columnWidths = new int[] { 0, 0 };
		gbl_panelExistingSections.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelExistingSections.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_panelExistingSections.rowWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		panelExistingSections.setLayout(gbl_panelExistingSections);

		JLabel lblExistingSections = new JLabel("Existing Sections");
		GridBagConstraints gbc_lblExistingSections = new GridBagConstraints();
		gbc_lblExistingSections.insets = new Insets(0, 0, 5, 0);
		gbc_lblExistingSections.gridx = 0;
		gbc_lblExistingSections.gridy = 0;
		panelExistingSections.add(lblExistingSections, gbc_lblExistingSections);

		JScrollPane scrollPaneExistingSections = new JScrollPane();
		GridBagConstraints gbc_scrollPaneExistingSections = new GridBagConstraints();
		gbc_scrollPaneExistingSections.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneExistingSections.gridx = 0;
		gbc_scrollPaneExistingSections.gridy = 1;
		panelExistingSections.add(scrollPaneExistingSections,
				gbc_scrollPaneExistingSections);

		listExistingSections = new JList();
		scrollPaneExistingSections.setViewportView(listExistingSections);

		panelCredits = new JPanel();
		panelCredits.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		GridBagConstraints gbc_panelCredits = new GridBagConstraints();
		gbc_panelCredits.insets = new Insets(10, 10, 10, 10);
		gbc_panelCredits.fill = GridBagConstraints.BOTH;
		gbc_panelCredits.gridx = 2;
		gbc_panelCredits.gridy = 0;
		frame.getContentPane().add(panelCredits, gbc_panelCredits);
		GridBagLayout gbl_panelCredits = new GridBagLayout();
		gbl_panelCredits.columnWidths = new int[] { 120, 0 };
		gbl_panelCredits.rowHeights = new int[] { 16, 0, 0, 0, 0 };
		gbl_panelCredits.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelCredits.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		panelCredits.setLayout(gbl_panelCredits);

		JLabel lblCreatedBy = new JLabel("Created By:");
		lblCreatedBy.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblCreatedBy = new GridBagConstraints();
		gbc_lblCreatedBy.anchor = GridBagConstraints.NORTH;
		gbc_lblCreatedBy.fill = GridBagConstraints.BOTH;
		gbc_lblCreatedBy.insets = new Insets(0, 0, 5, 0);
		gbc_lblCreatedBy.gridx = 0;
		gbc_lblCreatedBy.gridy = 0;
		panelCredits.add(lblCreatedBy, gbc_lblCreatedBy);

		lblXingzhouHe = new JLabel(
				"<html><center> Xingzhou He <br> xh2187@columbia.edu </center> </html>");
		GridBagConstraints gbc_lblXingzhouHe = new GridBagConstraints();
		gbc_lblXingzhouHe.insets = new Insets(0, 0, 5, 0);
		gbc_lblXingzhouHe.gridx = 0;
		gbc_lblXingzhouHe.gridy = 1;
		panelCredits.add(lblXingzhouHe, gbc_lblXingzhouHe);

		lblLinanQiu = new JLabel(
				"<html><center> Linan Qiu <br> lq2137@columbia.edu </center> </html>");
		GridBagConstraints gbc_lblLinanQiu = new GridBagConstraints();
		gbc_lblLinanQiu.insets = new Insets(0, 0, 5, 0);
		gbc_lblLinanQiu.gridx = 0;
		gbc_lblLinanQiu.gridy = 2;
		panelCredits.add(lblLinanQiu, gbc_lblLinanQiu);

		lblHugeShoutoutsTo = new JLabel(
				"<html> <center> With Help From <br> Swap and Morris </center> </html>");
		GridBagConstraints gbc_lblHugeShoutoutsTo = new GridBagConstraints();
		gbc_lblHugeShoutoutsTo.gridx = 0;
		gbc_lblHugeShoutoutsTo.gridy = 3;
		panelCredits.add(lblHugeShoutoutsTo, gbc_lblHugeShoutoutsTo);

		JPanel panelSectionDirectory = new JPanel();
		panelSectionDirectory.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		GridBagConstraints gbc_panelSectionDirectory = new GridBagConstraints();
		gbc_panelSectionDirectory.insets = new Insets(10, 10, 10, 10);
		gbc_panelSectionDirectory.fill = GridBagConstraints.BOTH;
		gbc_panelSectionDirectory.gridx = 0;
		gbc_panelSectionDirectory.gridy = 1;
		frame.getContentPane().add(panelSectionDirectory,
				gbc_panelSectionDirectory);
		GridBagLayout gbl_panelSectionDirectory = new GridBagLayout();
		gbl_panelSectionDirectory.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelSectionDirectory.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panelSectionDirectory.columnWeights = new double[] { 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelSectionDirectory.rowWeights = new double[] { 0.0, 0.0, 0.0,
				1.0, 1.0, Double.MIN_VALUE };
		panelSectionDirectory.setLayout(gbl_panelSectionDirectory);

		JLabel lblSectionDirectory = new JLabel("Section Directory");
		GridBagConstraints gbc_lblSectionDirectory = new GridBagConstraints();
		gbc_lblSectionDirectory.gridwidth = 2;
		gbc_lblSectionDirectory.insets = new Insets(0, 0, 5, 0);
		gbc_lblSectionDirectory.gridx = 0;
		gbc_lblSectionDirectory.gridy = 0;
		panelSectionDirectory.add(lblSectionDirectory, gbc_lblSectionDirectory);

		comboBoxDepartment = new JComboBox();
		GridBagConstraints gbc_comboBoxDepartment = new GridBagConstraints();
		gbc_comboBoxDepartment.gridwidth = 2;
		gbc_comboBoxDepartment.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxDepartment.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxDepartment.gridx = 0;
		gbc_comboBoxDepartment.gridy = 1;
		panelSectionDirectory.add(comboBoxDepartment, gbc_comboBoxDepartment);

		txtSearch = new JTextField();
		txtSearch.setText("Search");
		GridBagConstraints gbc_txtSearch = new GridBagConstraints();
		gbc_txtSearch.insets = new Insets(0, 0, 5, 5);
		gbc_txtSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSearch.gridx = 0;
		gbc_txtSearch.gridy = 2;
		panelSectionDirectory.add(txtSearch, gbc_txtSearch);
		txtSearch.setColumns(10);

		btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 2;
		panelSectionDirectory.add(btnSearch, gbc_btnSearch);

		JScrollPane scrollPaneSectionTree = new JScrollPane();
		GridBagConstraints gbc_scrollPaneSectionTree = new GridBagConstraints();
		gbc_scrollPaneSectionTree.gridheight = 2;
		gbc_scrollPaneSectionTree.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneSectionTree.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneSectionTree.gridx = 0;
		gbc_scrollPaneSectionTree.gridy = 3;
		panelSectionDirectory.add(scrollPaneSectionTree,
				gbc_scrollPaneSectionTree);

		JTree treeSectionTree = new JTree();
		scrollPaneSectionTree.setViewportView(treeSectionTree);

		buttonAddSection = new JButton("+");
		GridBagConstraints gbc_buttonAddSection = new GridBagConstraints();
		gbc_buttonAddSection.fill = GridBagConstraints.BOTH;
		gbc_buttonAddSection.insets = new Insets(0, 0, 5, 0);
		gbc_buttonAddSection.gridx = 1;
		gbc_buttonAddSection.gridy = 3;
		panelSectionDirectory.add(buttonAddSection, gbc_buttonAddSection);

		buttonRemoveSection = new JButton("-");
		GridBagConstraints gbc_buttonRemoveSection = new GridBagConstraints();
		gbc_buttonRemoveSection.fill = GridBagConstraints.BOTH;
		gbc_buttonRemoveSection.gridx = 1;
		gbc_buttonRemoveSection.gridy = 4;
		panelSectionDirectory.add(buttonRemoveSection, gbc_buttonRemoveSection);

		JPanel panelSectionsToAdd = new JPanel();
		panelSectionsToAdd.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		GridBagConstraints gbc_panelSectionsToAdd = new GridBagConstraints();
		gbc_panelSectionsToAdd.insets = new Insets(10, 10, 10, 10);
		gbc_panelSectionsToAdd.fill = GridBagConstraints.BOTH;
		gbc_panelSectionsToAdd.gridx = 1;
		gbc_panelSectionsToAdd.gridy = 1;
		frame.getContentPane().add(panelSectionsToAdd, gbc_panelSectionsToAdd);
		GridBagLayout gbl_panelSectionsToAdd = new GridBagLayout();
		gbl_panelSectionsToAdd.columnWidths = new int[] { 0, 0 };
		gbl_panelSectionsToAdd.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelSectionsToAdd.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_panelSectionsToAdd.rowWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		panelSectionsToAdd.setLayout(gbl_panelSectionsToAdd);

		JLabel lblSectionsToAdd = new JLabel("Sections To Add");
		GridBagConstraints gbc_lblSectionsToAdd = new GridBagConstraints();
		gbc_lblSectionsToAdd.insets = new Insets(0, 0, 5, 0);
		gbc_lblSectionsToAdd.gridx = 0;
		gbc_lblSectionsToAdd.gridy = 0;
		panelSectionsToAdd.add(lblSectionsToAdd, gbc_lblSectionsToAdd);

		JScrollPane scrollPaneSectionsToAdd = new JScrollPane();
		GridBagConstraints gbc_scrollPaneSectionsToAdd = new GridBagConstraints();
		gbc_scrollPaneSectionsToAdd.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneSectionsToAdd.gridx = 0;
		gbc_scrollPaneSectionsToAdd.gridy = 1;
		panelSectionsToAdd.add(scrollPaneSectionsToAdd,
				gbc_scrollPaneSectionsToAdd);

		JList listSectionsToAdd = new JList();
		listSectionsToAdd.setModel(sectionsToAddModel);
		scrollPaneSectionsToAdd.setViewportView(listSectionsToAdd);

		JPanel panelRun = new JPanel();
		panelRun.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		GridBagConstraints gbc_panelRun = new GridBagConstraints();
		gbc_panelRun.insets = new Insets(10, 10, 10, 10);
		gbc_panelRun.fill = GridBagConstraints.BOTH;
		gbc_panelRun.gridx = 2;
		gbc_panelRun.gridy = 1;
		frame.getContentPane().add(panelRun, gbc_panelRun);
		GridBagLayout gbl_panelRun = new GridBagLayout();
		gbl_panelRun.columnWidths = new int[] { 0, 0 };
		gbl_panelRun.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panelRun.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelRun.rowWeights = new double[] { 0.0, 1.0, 1.0,
				Double.MIN_VALUE };
		panelRun.setLayout(gbl_panelRun);

		JLabel lblRun = new JLabel("Run");
		GridBagConstraints gbc_lblRun = new GridBagConstraints();
		gbc_lblRun.insets = new Insets(0, 0, 5, 0);
		gbc_lblRun.gridx = 0;
		gbc_lblRun.gridy = 0;
		panelRun.add(lblRun, gbc_lblRun);

		btnStart = new JButton("Start");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.BOTH;
		gbc_btnStart.insets = new Insets(0, 0, 5, 0);
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 1;
		panelRun.add(btnStart, gbc_btnStart);

		btnStop = new JButton("Stop");
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.fill = GridBagConstraints.BOTH;
		gbc_btnStop.gridx = 0;
		gbc_btnStop.gridy = 2;
		panelRun.add(btnStop, gbc_btnStop);

		btnStop.setEnabled(false);
		frame.addMouseListener(new FrameListener());
		frame.addKeyListener(new KonamiListener());
		frame.setFocusable(true);
		StartStopListener startStopListener = new StartStopListener();
		btnStart.addActionListener(startStopListener);
		btnStop.addActionListener(startStopListener);
	}

	private void loginAndSemesterChoice() {
		loginDialog.setLocationRelativeTo(null);
		loginDialog.setModal(true);
		loginDialog.setVisible(true);
		lblUserUni.setText(loginDialog.getUni());
		ssolController
				.setLogin(loginDialog.getUni(), loginDialog.getPassword());

		System.out.println("loginAndSemesterChoice: initializing SSOL with "
				+ loginDialog.getUni() + " " + loginDialog.getPassword());

		if (ssolController.semesterCheck() == true) {
			semesterChoiceDialog.setSemesterOptions(ssolController
					.getSemesterOptions());
			System.out
					.println("loginAndSemesterChoice: semesterOptions sent to semesterChoiceDialog");
			semesterChoiceDialog.setLocationRelativeTo(null);
			semesterChoiceDialog.setModal(true);
			semesterChoiceDialog.setVisible(true);
			ssolController
					.setSemester(semesterChoiceDialog.getSemesterChoice());
			lblSemesterChoice.setText(semesterText(semesterChoiceDialog
					.getSemesterChoice()));
			System.out.println("loginAndSemesterChoice: "
					+ semesterChoiceDialog.getSemesterChoice() + " chosen");
		} else {
			ssolController.setSemester(null);
			lblSemesterChoice.setText(semesterText(ssolController
					.getSemesterChoice()));
		}
	}

	private String semesterText(String semester) {
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
		return textSemester;
	}

	private void buildCurrentSections() {
		System.out.println("buildCurrentSections started");

		ArrayList<String> currentSections = ssolController.getCurrentSections();

		DefaultListModel currentSectionsModel = new DefaultListModel();

		for (String currentSection : currentSections) {
			String element = "<html>";
			String[] lines = currentSection.split("\n");
			for (int i = 0; i < lines.length; i++) {
				element = element + lines[i];
				if (i != lines.length - 1) {
					element = element + "<br>";
				}
			}
			element = element + "</html>";
			currentSectionsModel.addElement(element);
		}

		listExistingSections.setModel(currentSectionsModel);
	}

	public ArrayList<Integer> getCourseIDs() {
		System.out.println("getCourseIDs started");
		// ArrayList<Integer> courseIDs = new ArrayList<Integer>();
		// for (Section section : sectionsToAddList) {
		// System.out.println("getCourseIDs: " + section.getCallNumber() +
		// " added");
		// courseIDs.add(section.getCallNumber());
		// }
		// System.out.println("getCourseIDs finished");
		//
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(52125);
		temp.add(12345);
		return temp;
	}

	private class StartStopListener implements ActionListener {
		SwingWorker<Void, Void> ssolWorker;

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (arg0.getSource() == btnStart) {
				System.out
						.println("StartStopListener: calling new RunSSOLSwingWorker");
				ssolWorker = new RunSSOLSwingWorker();
				System.out.println("StartStopListener: executing ssolWorker");
				ssolWorker.execute();
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);

			} else if (arg0.getSource() == btnStop) {
				System.out.println("StartStopListener: canceling ssolWorker");
				ssolWorker.cancel(true);
				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
			}

		}
	}

	private class RunSSOLSwingWorker extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			System.out.println("RunSSOLWorker started");
			ArrayList<Integer> courseIDs = SSOLGUI.this.getCourseIDs();
			System.out.println("RunSSOLWorker: courseIDs fetched with size "
					+ courseIDs.size());
			while (true) {
				ArrayList<Integer> results = ssolController.runSSOL(courseIDs);
				for (int i = results.size() - 1; i >= 0; i--) {

					if (results.get(i) == SSOL.REGISTRATION_SUCCESSFUL) {
						courseIDs.remove(i);
						System.out.println("RunSSOLWorker: Result DONE");
					} else if (results.get(i) == SSOL.SECTION_NOT_FOUND) {
						System.out.println("RunSSOLWorker: Result NOT FOUND");
					} else if (results.get(i) == SSOL.REGISTRATION_UNSUCCESSFUL) {
						System.out.println("RunSSOLWorker: Result UNSUCCESSFUL");
					}
				}
				System.out
						.println("RunSSOLWorker: One round of fetching complete");
				System.out.println("RunSSOLWorker: Sleeping");
				Thread.sleep(50000);
			}
		}

	}

	private class KonamiListener implements KeyListener {

		private boolean record;
		private int[] code = new int[11];
		private int index = 0;

		@Override
		public void keyPressed(KeyEvent arg0) {

			if (konamiCheck(arg0)) {
				SSOLGUI.this.ssolController.setSuperUser();
				panelCredits.setBackground(Color.gray);
				lblHugeShoutoutsTo
						.setText("SWAP");
				lblLinanQiu
						.setText("SWAP");
				lblXingzhouHe
						.setText("SWAP");

				System.out.println("SSOLGUI: konami code successful");
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {

		}

		@Override
		public void keyTyped(KeyEvent arg0) {

		}

		private boolean konamiCheck(KeyEvent key) {
			code[index] = key.getKeyCode();

			if (code[index] == KONAMI[index]) {
				if (index == 10) {
					return true;
				} else {
					index++;
					return false;
				}
			} else {
				for (int i = 0; i < KONAMI.length; i++) {
					code[i] = -1;
				}
				index = 0;
				return false;
			}
		}
	}

	private class FrameListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			frame.requestFocus();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
	
	private class DepartmentSwingWorker extends SwingWorker<ArrayList<String>, Void> {

		@Override
		protected ArrayList<String> doInBackground() throws Exception {
			DepartmentParser departmentParser = new DepartmentParser();
			ArrayList<String> departments = departmentParser.getDepartments();
			return departments;
		}
		
		protected void done() {
			try {
				ArrayList<String> departments = get();
				for (String department:departments) {
					comboBoxDepartment.addItem(department);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	private class BlockCheckSwingWorker extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			while (true) {
				System.out.println("BlockCheckSwingWorker started");
				WebDriver driver = new HtmlUnitDriver(true);

				driver.get("https://ssol.columbia.edu");

				// u_idField is the field for username
				WebElement u_idField = driver.findElement(By
						.cssSelector("input[name=u_id]"));

				// u_pwField is the field for password
				WebElement u_pwField = driver.findElement(By
						.cssSelector("input[name=u_pw]"));

				// sends username and password over and submits username and
				// password
				u_idField.sendKeys(loginDialog.getUni());
				u_pwField.sendKeys(loginDialog.getPassword());
				u_pwField.submit();

				if (driver.getPageSource().toLowerCase().indexOf("ip blocked") > -1) {
					System.out.println("BlockCheckSwingWorker: blocked");
					SSOLGUI.this.lblBlockStatusIndicator.setText("Blocked");
					SSOLGUI.this.lblBlockStatusIndicator
							.setForeground(Color.RED);
					Thread.sleep(30000);

				} else {
					System.out.println("BlockCheckSwingWorker: not blocked");
					SSOLGUI.this.lblBlockStatusIndicator.setText("Not Blocked");
					SSOLGUI.this.lblBlockStatusIndicator
							.setForeground(Color.GREEN);
					Thread.sleep(60000);
				}
			}
		}
	}
}
