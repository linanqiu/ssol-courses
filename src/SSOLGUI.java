import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.SystemColor;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
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
	private JList listSectionsToAdd;
	private SectionListModel sectionsToAddModel;
	private ArrayList<Section> sectionsToAddList;
	private JComboBox comboBoxDepartment;
	private JTree treeSectionTree;
	
	private HashMap<Section, Integer> registrationStatus;
	
	private Culpa culpaInfo;
	
	private CourseFetcher courseFetcher;
	private JLabel lblAppointment;
	private JLabel lblAppointmentindicator;

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
		// Add a courseFetcher
		courseFetcher = new CourseFetcher();
		sectionsToAddModel = new SectionListModel();

		registrationStatus = new HashMap<Section, Integer>();
		
		SwingWorker culpaSwingWorker = new CulpaSwingWorker();
		SwingWorker departmentSwingWorker = new DepartmentSwingWorker();
		culpaSwingWorker.execute();
		departmentSwingWorker.execute();
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
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 284, 150, 0, 0 };
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
		gbl_panelPersonalInformation.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panelPersonalInformation.columnWeights = new double[] { 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_panelPersonalInformation.rowWeights = new double[] { 0.0, 1.0, 1.0,
				1.0, 1.0, Double.MIN_VALUE };
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
		gbc_lblBlockStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblBlockStatus.gridx = 0;
		gbc_lblBlockStatus.gridy = 3;
		panelPersonalInformation.add(lblBlockStatus, gbc_lblBlockStatus);

		lblBlockStatusIndicator = new JLabel("Block Status");
		GridBagConstraints gbc_lblBlockStatusIndicator = new GridBagConstraints();
		gbc_lblBlockStatusIndicator.insets = new Insets(0, 0, 5, 0);
		gbc_lblBlockStatusIndicator.gridx = 1;
		gbc_lblBlockStatusIndicator.gridy = 3;
		panelPersonalInformation.add(lblBlockStatusIndicator,
				gbc_lblBlockStatusIndicator);

		lblAppointment = new JLabel("Reg Appointment:");
		GridBagConstraints gbc_lblAppointment = new GridBagConstraints();
		gbc_lblAppointment.anchor = GridBagConstraints.EAST;
		gbc_lblAppointment.insets = new Insets(0, 0, 0, 5);
		gbc_lblAppointment.gridx = 0;
		gbc_lblAppointment.gridy = 4;
		panelPersonalInformation.add(lblAppointment, gbc_lblAppointment);

		lblAppointmentindicator = new JLabel("AppointmentIndicator");
		GridBagConstraints gbc_lblAppointmentindicator = new GridBagConstraints();
		gbc_lblAppointmentindicator.gridx = 1;
		gbc_lblAppointmentindicator.gridy = 4;
		panelPersonalInformation.add(lblAppointmentindicator,
				gbc_lblAppointmentindicator);

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
		comboBoxDepartment.setPrototypeDisplayValue("");
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
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// disable search button
				btnSearch.setEnabled(false);
				btnSearch.setText("Searching");

				// call new swingworker
				SwingWorker searchSwingWorker = new SearchSwingWorker();
				searchSwingWorker.execute();
			}
		});
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

		treeSectionTree = new JTree(new CourseTreeModel());
		treeSectionTree.setRootVisible(false);
		treeSectionTree.setToggleClickCount(0); // disable expand; will listen
												// manually
		MouseListener treeMouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = treeSectionTree.getRowForLocation(e.getX(),
						e.getY());
				TreePath selPath = treeSectionTree.getPathForLocation(e.getX(),
						e.getY());
				if (selRow != -1) {
					if (e.getClickCount() == 1) {
						if (treeSectionTree.isExpanded(selPath))
							treeSectionTree.collapsePath(selPath);
						else
							treeSectionTree.expandPath(selPath);
					} else if (e.getClickCount() == 2) {
						CourseText toShow = (CourseText) selPath
								.getLastPathComponent();
						CourseDisplay display = new CourseDisplay(frame, culpaInfo, toShow);
					}
				}
			}
		};
		treeSectionTree.addMouseListener(treeMouseListener);
		scrollPaneSectionTree.setViewportView(treeSectionTree);

		buttonAddSection = new JButton("+");
		buttonAddSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath[] selects = treeSectionTree.getSelectionPaths();
				if (selects == null)
					return;
				for (TreePath select : selects) {
					if (select.getLastPathComponent() instanceof Section)
						((SectionListModel) listSectionsToAdd.getModel())
								.add((Section) select.getLastPathComponent());
				}
			}
		});
		GridBagConstraints gbc_buttonAddSection = new GridBagConstraints();
		gbc_buttonAddSection.fill = GridBagConstraints.BOTH;
		gbc_buttonAddSection.insets = new Insets(0, 0, 5, 0);
		gbc_buttonAddSection.gridx = 1;
		gbc_buttonAddSection.gridy = 3;
		panelSectionDirectory.add(buttonAddSection, gbc_buttonAddSection);

		buttonRemoveSection = new JButton("-");
		buttonRemoveSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] selected = listSectionsToAdd.getSelectedValues();
				SectionListModel currentModel = (SectionListModel) listSectionsToAdd
						.getModel();
				for (Object i : selected)
					currentModel.remove((Section) i);

			}
		});
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

		listSectionsToAdd = new JList();
		listSectionsToAdd.setModel(sectionsToAddModel);
		listSectionsToAdd.addMouseListener(new SectionMouseListener());
		listSectionsToAdd.setCellRenderer(new SectionRenderer());
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
		ArrayList<Section> currentSections = ssolController
				.getCurrentSections();
		// Set Data model, mouse listener and renderer.
		SectionListModel currentSectionsModel = new SectionListModel(
				currentSections);
		listExistingSections.addMouseListener(new SectionMouseListener());
		listExistingSections.setModel(currentSectionsModel);
		listExistingSections.setCellRenderer(new SectionRenderer());
	}

	public ArrayList<Section> getCourses() {
		return sectionsToAddModel.getElements();

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
				registrationStatus.clear();
				ssolWorker.execute();
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
				buttonAddSection.setEnabled(false);
				buttonRemoveSection.setEnabled(false);

			} else if (arg0.getSource() == btnStop) {
				System.out.println("StartStopListener: canceling ssolWorker");
				ssolWorker.cancel(true);
				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
				buttonAddSection.setEnabled(true);
				buttonRemoveSection.setEnabled(true);
			}

		}
	}

	private class RunSSOLSwingWorker extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			System.out.println("RunSSOLWorker started");
			ArrayList<Section> courseIDs = SSOLGUI.this.getCourses();
			System.out.println("RunSSOLWorker: courseIDs fetched with size "
					+ courseIDs.size());
			while (true) {
				ArrayList<Integer> results = ssolController.runSSOL(courseIDs);
				for (int i = results.size() - 1; i >= 0; i--) {

					registrationStatus.put(courseIDs.get(i), results.get(i));
					if (results.get(i) == SSOL.REGISTRATION_SUCCESSFUL) {
						courseIDs.remove(i);
						System.out.println("RunSSOLWorker: Result DONE");
					} else if (results.get(i) == SSOL.SECTION_NOT_FOUND) {
						courseIDs.remove(i);
						System.out.println("RunSSOLWorker: Result NOT FOUND");
					} else if (results.get(i) == SSOL.REGISTRATION_UNSUCCESSFUL) {
						System.out
								.println("RunSSOLWorker: Result UNSUCCESSFUL");
					}

				}

				publish(); // Update the color

				System.out
						.println("RunSSOLWorker: One round of fetching complete");

				if (courseIDs.isEmpty())
					return null;
				System.out.println("RunSSOLWorker: Sleeping");
				Thread.sleep(50000);
			}
		}

		/**
		 * Repaint the list. Use process() so this is thread safe.
		 */
		protected void process(List<Void> chunks) {
			listSectionsToAdd.repaint();
		}

		protected void done() {
			System.out.println("RunSSOL Worker: ssolWorker Done");
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
			buttonAddSection.setEnabled(true);
			buttonRemoveSection.setEnabled(true);
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
				lblHugeShoutoutsTo.setText("SWAP");
				lblLinanQiu.setText("SWAP");
				lblXingzhouHe.setText("SWAP");

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

	private class FrameListener extends MouseAdapter {

		public void mouseClicked(MouseEvent arg0) {
			frame.requestFocus();
		}

	}
	
	private class CulpaSwingWorker extends SwingWorker<Void, Void>
	{
		@Override
		protected Void doInBackground() throws Exception {
			culpaInfo = new Culpa();
			return null;
		}
	}
	
	private class DepartmentSwingWorker extends 
			SwingWorker<ArrayList<String>, Void> {
		@Override
		protected ArrayList<String> doInBackground() throws Exception {
			DepartmentParser departmentParser = new DepartmentParser();
			ArrayList<String> departments = departmentParser.getDepartments();
			return departments;
		}

		protected void done() {
			try {
				ArrayList<String> departments = get();
				for (String department : departments) {
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

		private boolean blocked;
		private boolean registrationAppointment;

		@Override
		protected Void doInBackground() throws Exception {
			blocked = false;
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
					blocked = true;
					publish();

					Thread.sleep(45000);
				} else {
					blocked = false;

					System.out
							.println("BlockCheckSwingWorker: checking appointment time");

					// gets the registrationLink and takes the URL
					WebElement registrationLink = driver.findElement(By
							.linkText("Registration"));

					String registrationLinkString = decodeSSOLLink(registrationLink
							.toString());

					// navigates to the registration page
					driver.get(registrationLinkString);

					List<WebElement> semesterOptionFields = driver
							.findElements(By.cssSelector("fieldset"));

					if (semesterOptionFields.size() == 1) {

						System.out
								.println("BlockCheckSwingWorker: only 1 semester found");

						WebElement sessionForm = driver.findElement(By
								.cssSelector("form[name=welcome]"));

						WebElement sessionFormSubmit = sessionForm
								.findElement(By
										.cssSelector("input[name = \"tran[1]_act\"]"));

						sessionFormSubmit.submit();

					} else {

						System.out
								.println("BlockCheckSwingWorker: multiple semesters found");

						List<WebElement> sessionForm = driver.findElements(By
								.cssSelector("form[name=welcome]"));

						WebElement sessionFormSubmit = null;

						for (WebElement sessionFormElement : sessionForm) {
							WebElement sessionFormId = sessionFormElement
									.findElement(By
											.cssSelector("input[name=\"tran[1]_term_id\"]"));

							if (sessionFormId.getAttribute("value").equals(
									semesterChoiceDialog.getSemesterChoice())) {

								System.out
										.println("BlockCheckSwingWorker: found matching session");

								sessionFormSubmit = sessionFormElement
										.findElement(By
												.cssSelector("input[name = \"tran[1]_act\"]"));
							}
						}

						sessionFormSubmit.submit();
					}
					
					if(driver.getPageSource().toLowerCase().indexOf("no registration appointment") > -1) {
						System.out.println("BlockCheckSwingWorker: appointment unavailable");
						registrationAppointment = false;
					} else {
						System.out.println("BlockCheckSwingWorker: appointment available");
						registrationAppointment = true;
					}
					
					publish();

					Thread.sleep(45000);
				}

			}

		}

		protected void process(List<Void> chunks) {
			if (blocked) {
				System.out.println("BlockCheckSwingWorker: blocked");
				SSOLGUI.this.lblBlockStatusIndicator.setText("Blocked");
				SSOLGUI.this.lblBlockStatusIndicator.setBackground(Color.RED);
				SSOLGUI.this.lblBlockStatusIndicator.setOpaque(true);
				SSOLGUI.this.lblBlockStatusIndicator.repaint();

			} else {
				System.out.println("BlockCheckSwingWorker: not blocked");
				SSOLGUI.this.lblBlockStatusIndicator.setText("Not Blocked");
				SSOLGUI.this.lblBlockStatusIndicator.setBackground(Color.GREEN);
				SSOLGUI.this.lblBlockStatusIndicator.setOpaque(true);
				SSOLGUI.this.lblBlockStatusIndicator.repaint();
			}
			
			if (registrationAppointment) {
				System.out.println("BlockCheckSwingWorker: appointment available");
				SSOLGUI.this.lblAppointmentindicator.setText("Available");
				SSOLGUI.this.lblAppointmentindicator.setBackground(Color.GREEN);
				SSOLGUI.this.lblAppointmentindicator.setOpaque(true);
				SSOLGUI.this.lblAppointmentindicator.repaint();
			} else {
				System.out.println("BlockCheckSwingWorker: appointment unavailable");
				SSOLGUI.this.lblAppointmentindicator.setText("Unavailable");
				SSOLGUI.this.lblAppointmentindicator.setBackground(Color.RED);
				SSOLGUI.this.lblAppointmentindicator.setOpaque(true);
				SSOLGUI.this.lblAppointmentindicator.repaint();
			}
		}

		/**
		 * takes raw html of a link on the SSOL site, removes the trash, and
		 * decodes the URL encoding
		 * 
		 * @param encodedLink
		 * @return decoded URL String
		 */
		private String decodeSSOLLink(String encodedLink) {

			String decodedLink = encodedLink;

			// gets the url starting with cgi-bin
			Pattern pattern = Pattern
					.compile(".*contentReplace\\(\\'\\/(.*)\\'\\).*");
			Matcher matcher = pattern.matcher(decodedLink);

			// trims away all the rest of the rubbish and replaces %. with %,
			// standardizing the URL encoding
			if (matcher.find()) {
				decodedLink = matcher.group(1);
				decodedLink = SSOL.baseURLString + decodedLink;
				decodedLink = decodedLink.replaceAll("%.", "%");
			}

			// decode the bloody URL
			try {
				decodedLink = URLDecoder.decode(decodedLink, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return decodedLink;
		}
	}

	private class SectionListModel extends AbstractListModel {
		private ArrayList<Section> sections;

		public SectionListModel() {
			sections = new ArrayList<Section>();
		}

		/**
		 * Get all elements in the list
		 * 
		 * @return an array list of all elements in the list.
		 */
		public ArrayList<Section> getElements() {
			return (ArrayList<Section>) sections.clone();
		}

		/**
		 * Remove a section from the list
		 * 
		 * @param section
		 *            Section to be removed
		 */
		public void remove(Section section) {
			int id = sections.indexOf(section);
			if (id == -1)
				throw new IllegalArgumentException();
			sections.remove(id);
			fireIntervalRemoved(this, id, id);
		}

		/**
		 * Add a section to the list
		 * 
		 * @param section
		 *            Section to be added
		 */
		public void add(Section section) {
			if (!sections.contains(section)) {
				sections.add(section);
				fireIntervalAdded(this, sections.size() - 1,
						sections.size() - 1);
			}
		}

		@SuppressWarnings("unchecked")
		public SectionListModel(ArrayList<Section> sections) {
			if (sections == null)
				throw new IllegalArgumentException();
			this.sections = (ArrayList<Section>) sections.clone(); // Prevent
																	// Problems
																	// with
																	// changing
																	// sections
		}

		@Override
		public int getSize() {
			return sections.size();
		}

		@Override
		public Object getElementAt(int index) {
			return sections.get(index);
		}
	}

	private class SearchSwingWorker extends SwingWorker<Course[], Void> {

		@Override
		protected Course[] doInBackground() throws Exception {

			// Fetch the courses
			Course[] courses = null;
			try {
				String dept = (String) comboBoxDepartment.getSelectedItem();
				if (dept == "(All)")
					dept = null;
				else
					dept = dept.substring(0, 4); // Parse code

				courses = courseFetcher
						.getCoursesByKeyword(dept, txtSearch.getText(),
								ssolController.getSemesterChoice());
				if (courses.length == 0)
					JOptionPane.showMessageDialog(null, "No courses found",
							"Message", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null,
						"Search operation failed: no network connection.",
						"Error", JOptionPane.ERROR_MESSAGE);
				courses = new Course[0];
			}

			return courses;
		}

		protected void done() {
			// Fit into the classes
			try {
				((CourseTreeModel) treeSectionTree.getModel())
						.setCourses(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			btnSearch.setEnabled(true);
			btnSearch.setText("Search");
		}
	}

	private class SectionMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent evt) {
			JList list = (JList) evt.getSource();
			int index = 0;
			if (evt.getClickCount() >= 2) {
				index = list.locationToIndex(evt.getPoint());
			} else
				return;
			CourseDisplay display = new CourseDisplay(frame, culpaInfo, (Section) list
					.getModel().getElementAt(index));
		}
	}

	private class SectionRenderer extends JLabel implements ListCellRenderer {

		public SectionRenderer() {
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
				if (registrationStatus.containsKey(value))
					switch (registrationStatus.get(value)) {
					case SSOL.REGISTRATION_SUCCESSFUL:
						setBackground(Color.GREEN);
						break;
					case SSOL.REGISTRATION_UNSUCCESSFUL:
						setBackground(Color.YELLOW);
						break;
					case SSOL.SECTION_NOT_FOUND:
						setBackground(Color.RED);
					}
			}
			Section section = (Section) value;
			setText("<html><body><p>"
					+ section.getCourseNumber().substring(0, 4) + " "
					+ section.getCourseNumber().substring(4, 9) + " sec "
					+ section.getCourseNumber().substring(9) + "<br>"
					+ section.getTitle() + "</p></body></html>");
			return this;
		}

	}

	private class CourseTreeModel implements TreeModel {

		private Course[] courses;
		private Vector<TreeModelListener> treeModelListeners = new Vector<TreeModelListener>();

		public CourseTreeModel() {
			courses = new Course[0];
		}

		public void setCourses(Course[] courses) {
			if (courses == null)
				System.out.println("Wrong");
			Course[] oldCourses = this.courses;
			this.courses = courses;
			fireTreeStructureChanged(oldCourses);
		}

		@Override
		public Object getRoot() {
			return courses;
		}

		@Override
		public Object getChild(Object parent, int index) {
			if (parent instanceof Course) {
				Section[] sections = ((Course) parent).getSections();
				return sections[index];
			} else { // Must be array of Courses
				Course[] courses = (Course[]) parent;
				return courses[index];
			}
		}

		@Override
		public int getChildCount(Object parent) {
			if (parent instanceof Section)
				return 0;
			else if (parent instanceof Course)
				return ((Course) parent).getSections().length;
			else
				return ((Course[]) parent).length;
		}

		@Override
		public boolean isLeaf(Object node) {
			if (node instanceof Section)
				return true;
			return false;
		}

		/**
		 * Not used. Do nothing.
		 */
		@Override
		public void valueForPathChanged(TreePath path, Object newValue) {
		}

		@Override
		public int getIndexOfChild(Object parent, Object child) {
			if (parent instanceof Course[]) {
				for (int i = 0; i < ((Course[]) parent).length; i++)
					if ((Course) child == ((Course[]) parent)[i])
						return i;
				return -1;
			} else {
				Section[] list = ((Course) parent).getSections();
				for (int i = 0; i < list.length; i++)
					if (list[i] == (Section) child)
						return i;
				return -1;
			}

		}

		protected void fireTreeStructureChanged(Course[] oldRoot) {
			int len = treeModelListeners.size();
			TreeModelEvent e = new TreeModelEvent(this,
					new Object[] { oldRoot });
			for (TreeModelListener tml : treeModelListeners) {
				tml.treeStructureChanged(e);
			}
		}

		@Override
		public void addTreeModelListener(TreeModelListener l) {
			treeModelListeners.addElement(l);
		}

		@Override
		public void removeTreeModelListener(TreeModelListener l) {
			treeModelListeners.removeElement(l);
		}

	}
}
