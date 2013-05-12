import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that coordinates the SSOL class, SectionFetcher class with the
 * SSOLGUI.
 * 
 * @author linanqiu
 * @uni lq2137
 */
public class SSOLController {

	private String uni;
	private String password;
	private SSOL ssol;
	private ArrayList<Section> currentSections;
	private ArrayList<String> semesterOptions;
	private String semesterChoice;
	private CourseFetcher courseFetcher;

	/**
	 * creates a SSOLController
	 */
	public SSOLController() {
		currentSections = new ArrayList<Section>();
		courseFetcher = new CourseFetcher();
		semesterChoice = "";
	}

	/**
	 * Sets the login details for SSOLController
	 * 
	 * @param uni
	 * @param password
	 */
	public void setLogin(String uni, String password) {
		this.uni = uni;
		this.password = password;
		System.out.println("setLogin: uni and password stored");

	}

	/**
	 * Tells the GUI if the SemesterChoiceDialog needs to be shown by logging in
	 * and checking if there are two or more sections available to be chosen.
	 * 
	 * @return
	 */
	public boolean semesterCheck() {
		ssol = new SSOL(this.uni, this.password);
		System.out.println("initializeSSOL: logging in using " + this.uni + " "
				+ this.password);
		ssol.login();
		ssol.goToRegistration();

		semesterOptions = ssol.semesterOptions();
		if (semesterOptions.size() == 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * gets current courses
	 */
	public ArrayList<Section> getCurrentSections() {

		ssol.currentSections();

		ArrayList<String> sectionStrings = ssol.getCurrentSections();
		ArrayList<Section> sections = new ArrayList<Section>();
		for (String i : sectionStrings) {
			Section session = null;
			try {
				session = courseFetcher.getSectionByCourseNumber(
						i.substring(0, 4), i.substring(6, 10),
						i.substring(15, 18), semesterChoice);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (session != null)
					sections.add(session);
			}
		}
		return sections;
	}

	/**
	 * sets semesterChoice. This is the user's chosen semester. GUI passes this
	 * method a String of "" if semesterCheck() gave false previously. Then this
	 * method will automatically skip that String and proceed to set
	 * semesterChoice as semesterOptions.get(0), ie. the only semesterOption
	 * available. IF there are multiple choices, then semesterCheck() would have
	 * returned true. Then, this method will be passed the semester that the
	 * user has chosen and this method will set that to be semesterChoice
	 * 
	 * @param semesterChoice
	 */
	public void setSemester(String semesterChoice) {

		if (semesterOptions.size() == 1) {
			this.semesterChoice = semesterOptions.get(0);
			ssol.chooseSemester(semesterOptions.get(0));
		} else {
			this.semesterChoice = semesterChoice;
			ssol.chooseSemester(semesterChoice);

		}
	}

	/**
	 * gets the uni
	 * 
	 * @return uni
	 */
	public String getUni() {
		return uni;
	}

	/**
	 * allows GUI to set super user
	 */
	public void setSuperUser() {
		ssol.setSuperUser();
	}

	/**
	 * gets the semesterChoice
	 * 
	 * @return semesterChoice
	 * 
	 *         the chosen semester by the user
	 */
	public String getSemesterChoice() {
		return semesterChoice;
	}

	/**
	 * @return semesterOptions
	 * 
	 *         the options available to the
	 */
	public ArrayList<String> getSemesterOptions() {
		return semesterOptions;
	}

	/**
	 * Used by a GUI SwingWorker to run SSOL
	 * 
	 * @param sections
	 *            the section the user selected
	 * @return results the results of size exactly the same as courseIDs. It
	 *         shows the result for each of those courseIDs.
	 */
	public ArrayList<Integer> runSSOL(ArrayList<Section> sections) {
		if (!ssol.killCode()) {
			ArrayList<Integer> results = new ArrayList<Integer>();
			for (Section section : sections) {
				System.out.println("runSSOL: calling new SSOL");
				ssol = new SSOL(uni, password, semesterChoice);
				System.out.println("runSSOL: logging in");
				ssol.login();
				ssol.goToRegistration();
				ssol.chooseSemester(semesterChoice);
				ssol.visaAgreement();
				int result = ssol.searchAndRegister(section.getCallNumber());
				results.add(result);
			}
			System.out.println("runSSOL: returning results");
			return results;
		} else {
			return null;
		}
	}
}
