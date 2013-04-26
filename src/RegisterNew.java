import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.*;

public class RegisterNew {

	private WebDriver driver;
	private String username;
	private String password;
	private String baseURLString;
	private boolean summerOption;

	/**
	 * constructs a new Register backend using a given username and password
	 * 
	 * @param username
	 * @param password
	 */
	public RegisterNew(String username, String password) {
		baseURLString = "https://ssol.columbia.edu/";

		this.username = "lq2137";
		this.password = "Trimalchio9010";
		this.summerOption = true;

		// enables javascript in the HtmlUnitDriver
		driver = new HtmlUnitDriver(true);
		driver.get(baseURLString);
		login();
		goToRegistration();
		summerOption();
	}

	/**
	 * logs into the SSOL
	 */
	private void login() {

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
	}

	/**
	 * after arriving on homepage, goes to the registration link and clicks it.
	 * did not use simple clicking here because HtmlUnitDriver does not decode
	 * URLEncoding
	 */
	private void goToRegistration() {

		// gets the registrationLink and takes the URL
		WebElement registrationLink = driver.findElement(By
				.linkText("Registration"));

		String registrationLinkString = decodeSSOLLink(registrationLink
				.toString());

		// navigates to the registration page
		driver.get(registrationLinkString);

	}

	/**
	 * checks for the option to select for summer school. depending on the
	 * boolean summerOption, choose accordingly.
	 * 
	 * THIS IS STILL WORK IN PROGRESS. HAVEN'T ACCOUNTED FOR NO SUMMER OPTION
	 * YET. LET ME CONTINUE WORKING ON THIS.
	 */
	private void summerOption() {

		// finds the number of forms for "welcome". if there's summer school,
		// the size of this list will be 2.
		List<WebElement> sessionForm = driver.findElements(By
				.cssSelector("form[name=welcome]"));

		if (sessionForm.size() == 1) {

			// only one session available
			WebElement sessionSubmit = sessionForm.get(0).findElement(
					By.cssSelector("input[type=submit]"));
			sessionSubmit.submit();
		} else if (sessionForm.size() == 2) {
			if (summerOption == true) {
				WebElement sessionSubmit = sessionForm.get(0).findElement(
						By.cssSelector("input[type=submit]"));
				sessionSubmit.submit();
			} else {
				WebElement sessionSubmit = sessionForm.get(1).findElement(
						By.cssSelector("input[type=submit]"));
				sessionSubmit.submit();
			}
		} else {
			System.out.println("You shouldn't be here.");
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
