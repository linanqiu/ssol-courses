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
	private String baseURLString;
	private String semesterOption;
	private ArrayList<Integer> courseIDs;
	private ArrayList<String> currentCourses;

	/**
	 * constructs a new Register backend using a given username and password
	 * 
	 * @param username
	 * @param password
	 */
	public SSOL(String username, String password) {

		System.out.println("RegisterNew object called");

		baseURLString = "https://ssol.columbia.edu/";

		currentCourses = new ArrayList<String>();

		// here only for testing.
		this.courseIDs = new ArrayList<Integer>();
		this.username = "lq2137";
		this.password = "Trimalchio9010";
		this.semesterOption = "20132";

		// enables javascript in the HtmlUnitDriver
		driver = new HtmlUnitDriver(true);
		driver.get(baseURLString);

		System.out.println("RegisterNew object constructed");

		// this should be done prior to section selection
		login();
		currentCourses();
		goToRegistration();
		semesterOptions();

		// this should be done after section selection
		chooseSemester(semesterOption);
		visaAgreement();
		searchAndRegister(29567);
	}

	/**
	 * logs into the SSOL
	 */
	private void login() {

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

		System.out.println("login started");

	}

	/**
	 * goes to the student schedule page and returns an ArrayList<String> of the
	 * course description of the courses the student currently has. this is only
	 * accurate as of the previous day.
	 * 
	 * @return ArrayList<String> currentCourses
	 */
	private ArrayList<String> currentCourses() {

		System.out.println("currentCourses started");

		// gets the schedule link and takes the URL
		WebElement scheduleLink = driver.findElement(By
				.linkText("Student Schedule"));

		String scheduleLinkString = decodeSSOLLink(scheduleLink.toString());

		System.out.println("currentCourses: link decoded: "
				+ scheduleLinkString);

		// navigates to the student schedule page
		driver.get(scheduleLinkString);

		Select semesterSelect = new Select(driver.findElement(By
				.cssSelector("select[name=\"tran[1]_term_id\"]")));
		semesterSelect.selectByValue(semesterOption);
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
			currentCourses.add(courseTd.getText());
			System.out.println("currentCourses: " + courseTd.getText()
					+ " added");
		}

		return currentCourses;
	}

	/**
	 * after arriving on homepage, goes to the registration link and clicks it.
	 * did not use simple clicking here because HtmlUnitDriver does not decode
	 * URLEncoding
	 */
	private void goToRegistration() {

		System.out.println("goToRegistration started");

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
	private ArrayList<String> semesterOptions() {

		// declares the ArrayList which will contain the semester options
		ArrayList<String> semesterOptions = new ArrayList<String>();

		System.out.println("semesterChoice started");

		// each semester occurs in a fieldset. hence we find the fieldset.
		List<WebElement> semesterOptionFields = driver.findElements(By
				.cssSelector("fieldset"));

		System.out.println("semesterChoice: " + semesterOptionFields.size()
				+ " semesters available");

		if (semesterOptionFields.size() == 1) {

			// if there is only one session available, only add the single
			// sessionId to the semesterOptions ArrayList
			WebElement sessionForm = driver.findElement(By
					.cssSelector("form[name=welcome]"));

			WebElement sessionId = sessionForm.findElement(By
					.cssSelector("input[name=\"tran[1]_term_id\"]"));

			semesterOption = sessionId.getAttribute("value");

			semesterOptions.add(semesterOption);

			System.out.println("semesterChoice: sessionId " + semesterOption
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
				System.out.println("semesterChoice: sessionId "
						+ semesterOption + " is available");
			}

		}

		// returns the answer
		System.out.println("semesterChoice finished");

		return semesterOptions;
	}

	/**
	 * chooses the right semester
	 **/
	private void chooseSemester(String semesterChoice) {

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

				if (sessionFormId.getAttribute("value").equals(semesterOption)) {

					System.out
							.println("chooseSemester: found matching session");

					sessionFormSubmit = sessionFormElement.findElement(By
							.cssSelector("input[name = \"tran[1]_act\"]"));
				}
			}

			sessionFormSubmit.submit();

			System.out.println("chooseSemester: " + semesterOption + " chosen");

		}

		System.out.println("chooseSemester: current URL: "
				+ driver.getCurrentUrl());

		System.out.println("chooseSemester started");
	}

	/**
	 * international students have to click on the visa agreement. this method
	 * does that automatically. if, however, the student is american, the method
	 * checks if we are already on the registration page.
	 */
	private void visaAgreement() {

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
	private void searchAndRegister(int courseID) {

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
		} else {

			// registers for the class
			registerElement.get(0).click();
			WebElement addClassElement = driver.findElement(By
					.name("tran[1]_act"));
			addClassElement.click();

			System.out.println("searchAndRegister: registration request sent");

			// checks if registration is successful
			try {
				WebElement sucessElement = driver.findElement(By
						.cssSelector("div.msgs b"));
				System.out.println("searchAndRegister: " + courseID
						+ " registration successful");
			} catch (NoSuchElementException e) {
				System.out.println("searchAndRegister: " + courseID
						+ " registration is unsuccessful");
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

	public String getSemesterOption() {
		return semesterOption;
	}

	public ArrayList<String> getCurrentCourses() {
		return currentCourses;
	}
}
