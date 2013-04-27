import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.*;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class RegisterNew {

	private WebDriver driver;
	private String username;
	private String password;
	private String baseURLString;
	private boolean summerOption;
	private ArrayList<Integer> courseIDs;

	/**
	 * constructs a new Register backend using a given username and password
	 * 
	 * @param username
	 * @param password
	 */
	public RegisterNew(String username, String password) {

		System.out.println("RegisterNew object starting construction.");

		baseURLString = "https://ssol.columbia.edu/";

		// here only for testing.
		this.courseIDs = new ArrayList<Integer>();
		this.username = "lq2137";
		this.password = "Trimalchio9010";
		this.summerOption = true;

		// enables javascript in the HtmlUnitDriver
		driver = new HtmlUnitDriver(true);
		driver.get(baseURLString);

		System.out.println("RegisterNew object constructed.");

		login();
		goToRegistration();
		summerOption();
		visaAgreement();
		searchAndRegister(29567);
	}

	/**
	 * logs into the SSOL
	 */
	private void login() {

		System.out.println("login starting");

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

		System.out.println("login finished.");

	}

	/**
	 * after arriving on homepage, goes to the registration link and clicks it.
	 * did not use simple clicking here because HtmlUnitDriver does not decode
	 * URLEncoding
	 */
	private void goToRegistration() {

		System.out.println("goToRegistration starting.");

		// gets the registrationLink and takes the URL
		WebElement registrationLink = driver.findElement(By
				.linkText("Registration"));

		String registrationLinkString = decodeSSOLLink(registrationLink
				.toString());

		System.out.println("goToRegistration: link decoded: "
				+ registrationLinkString);

		// navigates to the registration page
		driver.get(registrationLinkString);

		System.out.println("goToRegistration finished. ");

	}

	/**
	 * checks for the option to select for summer school. depending on the
	 * boolean summerOption, choose accordingly. this accounts for the lack of a
	 * summer option, in which case sessionForm will not be found.
	 */
	private void summerOption() {

		System.out.println("summerOption started.");

		try {
			// finds the number of forms for "welcome". if there's summer
			// school, the size of this list will be 2.
			List<WebElement> sessionForm = driver.findElements(By
					.cssSelector("form[name=welcome]"));
			if (sessionForm.size() == 1) {

				// only one session available

				System.out.println("summerOption: 1 session available.");

				WebElement sessionSubmit = sessionForm.get(0).findElement(
						By.cssSelector("input[type=submit]"));
				sessionSubmit.submit();
			} else if (sessionForm.size() == 2) {
				if (summerOption == true) {

					System.out.println("summerOption: 2 sessions available");

					WebElement sessionSubmit = sessionForm.get(0).findElement(
							By.cssSelector("input[type=submit]"));
					sessionSubmit.submit();
				} else {
					WebElement sessionSubmit = sessionForm.get(1).findElement(
							By.cssSelector("input[type=submit]"));
					sessionSubmit.submit();
				}
			} else {

				// will never be reached
			}
		} catch (ElementNotFoundException e) {

			// if the element is not found, that means no summer session is
			// available. go on with the program.

		}

		System.out.println("summerOption finished.");
	}

	/**
	 * international students have to click on the visa agreement. this method
	 * does that automatically. if, however, the student is american, the method
	 * checks if we are already on the registration page.
	 */
	private void visaAgreement() {

		System.out.println("visaAgreement started.");

		// try and click on the visa agreement options
		try {

			System.out.println("visaAgreement: found visa agreement");

			WebElement visaAgreementElement_1 = driver.findElement(By
					.name("tran[1]_agree"));
			WebElement visaAgreementElement_2 = driver.findElement(By
					.name("tran[1]_fj1"));

			visaAgreementElement_1.click();
			visaAgreementElement_2.click();

			visaAgreementElement_2.submit();
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

		System.out.println("visaAgreement finished.");
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

		System.out.println("searcAndRegister: link decoded: "
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

			// goes back to the registration page
			System.out.println("searchAndRegister: " + courseID
					+ " is not found.");

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

			// checks if registration is successful
			try {
				WebElement sucessElement = driver.findElement(By
						.cssSelector("div.msgs b"));
			} catch (NoSuchElementException e) {
				System.out.println("Unsuccessful for " + courseID);
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

}
