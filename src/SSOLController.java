import java.util.ArrayList;

public class SSOLController {

	private String uni;
	private String password;
	private SSOL ssol;
	private ArrayList<Section> currentSections;
	private ArrayList<String> semesterOptions;
	private String semesterChoice;

	public SSOLController() {
		currentSections = new ArrayList<Section>();
		semesterChoice = "";
	}

	public void setLogin(String uni, String password) {
		this.uni = uni;
		this.password = password;
		System.out.println("setLogin: uni and password stored");

	}

	public boolean semesterCheck() {
		ssol = new SSOL(this.uni, this.password);
		System.out.println("initializeSSOL: logging in using " + this.uni + " "
				+ this.password);
		ssol.login();
		ssol.goToRegistration();

		semesterOptions = ssol.semesterOptions();
		if (semesterOptions.size() == 1) {
			// no need to choose semester
			semesterChoice = semesterOptions.get(0);
		} else {
			return true;
		}
		return false;
	}

	public void getCurrentCourses() {
		
		// TODO: waiting for SectionFetcher implementation
		ssol.getCurrentSections();
	}

	public void setSemester(String semesterChoice) {

		if (this.semesterChoice.length() == 5) {
			
		} else {
			this.semesterChoice = semesterChoice;
		}
	}
	
	public void endSSOL() {
		ssol = null;
	}

	public String getUni() {
		return uni;
	}

	public String getSemesterChoice() {
		return semesterChoice;
	}

	public ArrayList<String> getSemesterOptions() {
		return semesterOptions;
	}

}
