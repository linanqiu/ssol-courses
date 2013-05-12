import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.*;
import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * A class to manipulate SSOL
 * 
 * @author linanqiu
 * @uni lq2137
 */
public class SSOL {

	private WebDriver driver;
	private String username;
	private String password;
	public static final String baseURLString = "https://ssol.columbia.edu/";
	private String semesterChoice;
	private ArrayList<Integer> courseIDs;
	private ArrayList<String> currentSections;
	public static final int REGISTRATION_SUCCESSFUL = 1;
	public static final int REGISTRATION_UNSUCCESSFUL = 2;
	public static final int SECTION_NOT_FOUND = 3;
	private boolean superUser = false;

	/**
	 * constructs a new SSOL backend using a given username and password
	 * 
	 * @param username
	 * @param password
	 */
	public SSOL(String username, String password) {

		System.out.println("SSOL object called");
		this.username = username;
		this.password = password;

		currentSections = new ArrayList<String>();

		// enables javascript in the HtmlUnitDriver
		driver = new HtmlUnitDriver(true);

		System.out.println("SSOL object constructed");
	}

	public SSOL(String username, String password, String semesterChoice) {

		System.out.println("SSOL object called using thread constructor");
		this.username = username;
		this.password = password;
		this.semesterChoice = semesterChoice;

		driver = new HtmlUnitDriver(true);

		System.out.println("SSOL object constructed using thread constructor");
	}

	/**
	 * logs into the SSOL
	 */
	public void login() throws NoSuchElementException {
		driver.get(baseURLString);

		System.out.println(driver.getCurrentUrl());

		System.out.println("login started");

		// u_idField is the field for username
		WebElement u_idField = driver.findElement(By
				.cssSelector("input[name=u_id]"));

		// u_pwField is the field for password
		WebElement u_pwField = driver.findElement(By
				.cssSelector("input[name=u_pw]"));

		// sends username and password over and submits username and password
		u_idField.sendKeys(username);
		u_pwField.sendKeys(password);
		u_pwField.submit();

		System.out.println("login finished");

	}

	/**
	 * goes to the student schedule page and returns an ArrayList<String> of the
	 * course description of the courses the student currently has. this is only
	 * accurate as of the previous day.
	 * 
	 * @return ArrayList<String> currentCourses
	 */
	public ArrayList<String> currentSections() throws NoSuchElementException {

		System.out.println("currentCourses started");

		// gets the schedule link and takes the URL
		WebElement scheduleLink = driver.findElement(By.linkText("Schedule"));

		String scheduleLinkString = decodeSSOLLink(scheduleLink.toString());

		System.out.println("currentCourses: link decoded: "
				+ scheduleLinkString);

		// navigates to the student schedule page
		driver.get(scheduleLinkString);

		Select semesterSelect = new Select(driver.findElement(By
				.cssSelector("select[name=\"tran[1]_term_id\"]")));
		try {
			semesterSelect.selectByValue(semesterChoice);
		} catch (Exception e) {
			System.out.println("User doesn't have a schedule. No classes are registered yet.");
			return currentSections;
		}
		WebElement semesterUpdateView = driver.findElement(By
				.cssSelector("input[value=\"Update View\"]"));
		semesterUpdateView.click();

		// gets the list of WebElements for courses
		WebElement semesterDataGrid = driver.findElements(
				By.cssSelector("table.DataGrid")).get(0);
		List<WebElement> courseTrs = semesterDataGrid.findElements(By
				.cssSelector("tr"));

		// starts from 3 because the first 3 rows are no use. headers and other
		// shit. so we start from the 4th one. counts till size() - 1 because
		// the last row is useless too. then we add the text of each of
		// them into the arraylist to be returned
		
		for (int i = 3; i < courseTrs.size() - 1; i++) {
			WebElement courseTd = courseTrs.get(i)
					.findElements(By.cssSelector("td")).get(0);
			String currentSection = courseTd.getText();
			currentSections.add(currentSection);
			System.out.println("currentCourses: " + courseTd.getText()
					+ " added");
			
		}

		System.out.println("currentCourses finished");

		return currentSections;
	}

	/**
	 * after arriving on homepage, goes to the registration link and clicks it.
	 * did not use simple clicking here because HtmlUnitDriver does not decode
	 * URLEncoding
	 */
	public void goToRegistration() throws NoSuchElementException {

		System.out.println("goToRegistration started");

		System.out.println("goToRegistration: current URL: "
				+ driver.getCurrentUrl());

		// gets the registrationLink and takes the URL
		WebElement registrationLink = driver.findElement(By
				.linkText("Registration"));

		String registrationLinkString = decodeSSOLLink(registrationLink
				.toString());

		System.out.println("goToRegistration: link decoded: "
				+ registrationLinkString);

		// navigates to the registration page
		driver.get(registrationLinkString);

		System.out.println("goToRegistration started ");

	}

	/*
	 * gets available semesters and returns them as a ArrayList<String>. This
	 * will be in the form YYYYS, where YYYY is the year, and S is the number
	 * for the semester. For example, Summer 2013 will be 20132, while Fall 2012
	 * will be 20123.
	 */
	public ArrayList<String> semesterOptions() throws NoSuchElementException {

		// declares the ArrayList which will contain the semester options
		ArrayList<String> semesterOptions = new ArrayList<String>();

		System.out.println("semesterOptions started");

		// each semester occurs in a fieldset. hence we find the fieldset.
		List<WebElement> semesterOptionFields = driver.findElements(By
				.cssSelector("fieldset"));

		System.out.println("semesterOptions: " + semesterOptionFields.size()
				+ " semesters available");

		if (semesterOptionFields.size() == 1) {

			// if there is only one session available, only add the single
			// sessionId to the semesterOptions ArrayList
			WebElement sessionForm = driver.findElement(By
					.cssSelector("form[name=welcome]"));

			WebElement sessionId = sessionForm.findElement(By
					.cssSelector("input[name=\"tran[1]_term_id\"]"));

			String semesterOption = sessionId.getAttribute("value");

			semesterOptions.add(semesterOption);

			System.out.println("semesterOptions: sessionId " + semesterOption
					+ " is available");

		} else {

			// or else, find the multiple sessionForms, and for each of them,
			// find their sessionIds and add them to the ArrayList
			List<WebElement> sessionForm = driver.findElements(By
					.cssSelector("form[name=welcome]"));

			for (WebElement sessionFormElement : sessionForm) {
				WebElement sessionId = sessionFormElement.findElement(By
						.cssSelector("input[name=\"tran[1]_term_id\"]"));

				semesterOptions.add(sessionId.getAttribute("value"));
			}

			for (String semesterOption : semesterOptions) {
				System.out.println("semesterOptions: sessionId "
						+ semesterOption + " is available");
			}

		}

		// returns the answer
		System.out.println("semesterOptions finished");

		return semesterOptions;
	}

	/**
	 * chooses the right semester
	 **/
	public void chooseSemester(String semesterChoice)
			throws NoSuchElementException {

		this.semesterChoice = semesterChoice;

		System.out.println("chooseSemester started");

		System.out.println("chooseSemester: current URL: "
				+ driver.getCurrentUrl());

		List<WebElement> semesterOptionFields = driver.findElements(By
				.cssSelector("fieldset"));

		if (semesterOptionFields.size() == 1) {

			System.out.println("chooseSemester: only 1 semester found");

			WebElement sessionForm = driver.findElement(By
					.cssSelector("form[name=welcome]"));

			WebElement sessionFormSubmit = sessionForm.findElement(By
					.cssSelector("input[name = \"tran[1]_act\"]"));

			sessionFormSubmit.submit();

		} else {

			System.out.println("chooseSemester: multiple semesters found");

			List<WebElement> sessionForm = driver.findElements(By
					.cssSelector("form[name=welcome]"));

			WebElement sessionFormSubmit = null;

			for (WebElement sessionFormElement : sessionForm) {
				WebElement sessionFormId = sessionFormElement.findElement(By
						.cssSelector("input[name=\"tran[1]_term_id\"]"));

				if (sessionFormId.getAttribute("value").equals(semesterChoice)) {

					System.out
							.println("chooseSemester: found matching session");

					sessionFormSubmit = sessionFormElement.findElement(By
							.cssSelector("input[name = \"tran[1]_act\"]"));
				}
			}

			sessionFormSubmit.submit();

			System.out.println("chooseSemester: " + semesterChoice + " chosen");

		}

		System.out.println("chooseSemester: current URL: "
				+ driver.getCurrentUrl());

		System.out.println("chooseSemester finished");
	}

	/**
	 * international students have to click on the visa agreement. this method
	 * does that automatically. if, however, the student is american, the method
	 * checks if we are already on the registration page.
	 */
	public void visaAgreement() throws NoSuchElementException {

		System.out.println("visaAgreement started");

		// try and click on the visa agreement options
		try {
			WebElement visaAgreementElement_1 = driver.findElement(By
					.name("tran[1]_agree"));
			WebElement visaAgreementElement_2 = driver.findElement(By
					.name("tran[1]_fj1"));

			visaAgreementElement_1.click();
			visaAgreementElement_2.click();

			visaAgreementElement_2.submit();

			System.out.println("visaAgreement: found visa agreement");

		} catch (NoSuchElementException e) {

			// in case these elements don't exist, we check if we are already on
			// the registration page.
			try {

				System.out
						.println("visaAgreement: already on registration page");

				driver.findElement(By.className("clsDataGridTitle"));
			} catch (NoSuchElementException e1) {

			}
		}

		System.out.println("visaAgreement started");
	}

	/**
	 * registers for the specified course with callnumber courseID. this method
	 * is intended to be run multiple times according to the ArrayList<Integer
	 * courseIDs
	 * 
	 * @param courseID
	 */
	public int searchAndRegister(int courseID) throws NoSuchElementException {

		System.out.println("searchAndRegister started");

		// finds the link for search class and clicks it
		WebElement searchLink = driver.findElement(By.linkText("Search Class"));

		String searchLinkString = decodeSSOLLink(searchLink.toString());

		System.out.println("searchAndRegister: link decoded: "
				+ searchLinkString);

		driver.get(searchLinkString);

		// searches for the specified courseID
		WebElement searchElement = driver.findElement(By.name("tran[1]_ss"));
		searchElement.sendKeys(String.valueOf(courseID));
		searchElement.submit();

		System.out.println("searchAndRegister: courseID sent");

		// searches for the register button
		List<WebElement> registerElement = driver.findElements(By
				.className("regb"));

		// if the button is not found, no available class is found
		if (registerElement.size() == 0) {

			// TODO: account for case where course conflicts / already exists

			// goes back to the registration page
			System.out.println("searchAndRegister: " + courseID
					+ " is not found");

			WebElement backToRegistrationLink = driver.findElement(By
					.linkText("Back To Registration"));

			String backToRegistrationLinkString = decodeSSOLLink(backToRegistrationLink
					.toString());

			driver.get(backToRegistrationLinkString);

			System.out.println("searchAndRegister: back to registration page");

			return SECTION_NOT_FOUND;

		} else {

			// registers for the class
			try {
			registerElement.get(0).click();
			WebElement addClassElement = driver.findElement(By
					.name("tran[1]_act"));
			addClassElement.click();
			} catch (Exception e) {
				System.out.println("searchAndRegister: page not correct. perhaps system is closed");
				return REGISTRATION_UNSUCCESSFUL;
			}
			System.out.println("searchAndRegister: registration request sent");

			// checks if registration is successful
			try {
				WebElement successElement = driver.findElement(By
						.cssSelector("div.msgs b"));
				System.out.println("searchAndRegister: " + courseID
						+ " registration successful");

				return REGISTRATION_SUCCESSFUL;

			} catch (NoSuchElementException e) {
				System.out.println("searchAndRegister: " + courseID
						+ " registration is unsuccessful");
				return REGISTRATION_UNSUCCESSFUL;
			}
		}
	}

	/**
	 * takes raw html of a link on the SSOL site, removes the trash, and decodes
	 * the URL encoding
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
			decodedLink = baseURLString + decodedLink;
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

	/**
	 * gets the semesterChoice of this ssol
	 * 
	 * @return semesterChoice the semester chosen by the user
	 */
	public String getSemesterChoice() {
		return semesterChoice;
	}

	/**
	 * gets currentSections, the sections that the user is currently enrolled in
	 * as an ArrayList<String>
	 * 
	 * @return currentSections
	 */
	public ArrayList<String> getCurrentSections() {
		return currentSections;
	}

	/**
	 * implements a killcode that can stop users from signing up for courses
	 * 
	 * @return true or false depending on the killCode status
	 */
	public boolean killCode() {
		if (superUser) {
			return false;
		} else {
			WebDriver killCodeDriver = new HtmlUnitDriver(true);
			killCodeDriver
					.get("http://columbia.edu/~lq2137/killcode/killcode.html");
			if (killCodeDriver.getPageSource().toLowerCase().indexOf("false") > -1) {
				return false;
			} else {
				System.out.println("killCode: kill code is on");
				return true;
			}
		}
	}

	/**
	 * makes the user a superUser, ignoring the killCode
	 */
	public void setSuperUser() {
		superUser = true;
	}
}
