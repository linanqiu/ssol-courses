import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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

	public RegisterNew(String username, String password) {
		baseURLString = "https://ssol.columbia.edu/";
		this.username = "lq2137";
		this.password = "Trimalchio9010";

		// enables javascript in the HtmlUnitDriver
		driver = new HtmlUnitDriver(true);
		driver.get(baseURLString);
		login();
		goToRegistration();
	}

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

	private void goToRegistration() {
		
		// registrationLink.click() does not work
		WebElement registrationLink = driver.findElement(By
				.linkText("Registration"));
		registrationLink.click();
	}

}
