import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.NoSuchElementException;

public class Register {

	private String username;
	private String password;
	private ArrayList<Integer> courses;
	private WebDriver driver;
	private String startURL;
	private long millis;
	public final String USERNAME_FIND = "u_id";
	public final String PASSWORD_FIND = "u_pw";
	public final String VISA_AGREEMENT_NAME1 = "tran[1]_agree";
	public final String VISA_AGREEMENT_NAME2 = "tran[1]_fj1";
	public final String REGISTRATION_PAGE_CLASS = "clsDataGridTitle";
	public final String REGISTRATION_LINK_TEXT = "Registration";
	public final String SEARCH_BOX_NAME = "tran[1]_ss";
	public final String REGISTRATION_ACCEPT_CLASS = "regb";
	public final String ADD_CLASS_NAME = "tran[1]_act";
	public final String SUCCESS_CSS = "div.msgs b";
	public final String FAIL_CSS = "div.msge";
	public final String SEARCH_FIND = "Search Class";
	public final String FALL_ACCEPT = " //input[@type=\"submit\" and @value=\"Continue with Fall 2013 Registration\"]";
	public final String BACK_TO_REGISTRATION = "Back To Registration";
	private Calendar cal;
	private DateFormat df;
	private int time;
	private URLConnection url;

	public Register(String username, String password,
			ArrayList<Integer> courses, String startURL, long millis)
			throws InputMismatchException {
		this.username = username;
		this.password = password;
		this.courses = courses;
		this.startURL = startURL;
		this.millis = millis;
		driver = new ChromeDriver();
		// cal = Calendar.getInstance();
		// df = new SimpleDateFormat("HHmm");
	}

	// public void timer(int time) {
	// this.time = Integer.parseInt(df.format(cal.getTime()));
	// boolean continueLoop = true;
	// while (continueLoop) {
	// if (this.time == time) {
	// run();
	// continueLoop = false;
	// } else {
	// try {
	// Thread.sleep(10000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// }

	private boolean killCode() {
		boolean kill = true;
		try {
			url = new URL(
					"http://www.columbia.edu/~lq2137/killcode/killcode.html")
					.openConnection();
			Scanner scanner = new Scanner(url.getInputStream());
			String killCode = "";
			while (scanner.hasNext()) {
				killCode = scanner.nextLine();
			}
			if (killCode.equals("false")) {
				kill = false;
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kill;
	}

	public void run() {
		if (!killCode()) {
			login();
			goToRegistration();
			for (int courseID : courses) {
				searchAndRegister(courseID);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			timer(millis);
		}
	}

	private void login() {
		driver.get(startURL);
		WebElement usernameElement = driver.findElement(By.name(USERNAME_FIND));
		usernameElement.sendKeys(username);
		WebElement passwordElement = driver.findElement(By.name(PASSWORD_FIND));
		passwordElement.sendKeys(password);
		usernameElement.submit();
	}

	private void goToRegistration() {
		WebElement registrationLink = driver.findElement(By
				.linkText(REGISTRATION_LINK_TEXT));
		registrationLink.click();

		try {
			WebElement fallElement = driver.findElement(By.xpath(FALL_ACCEPT));
			fallElement.click();
		} catch (NoSuchElementException e1) {
			System.out.println("No double option.");
		}

		try {
			WebElement agreeElement1 = driver.findElement(By
					.name(VISA_AGREEMENT_NAME1));
			agreeElement1.click();
			WebElement agreeElement2 = driver.findElement(By
					.name(VISA_AGREEMENT_NAME2));
			agreeElement2.click();
			agreeElement1.submit();
		} catch (NoSuchElementException e) {
			try {
				driver.findElement(By.className(REGISTRATION_PAGE_CLASS));

			} catch (NoSuchElementException ee) {
				System.out.println("You're really kind of stuck.");
			}
		}
	}

	private void searchAndRegister(int classID) {
		WebElement searchLink = driver.findElement(By.linkText(SEARCH_FIND));
		searchLink.click();

		WebElement searchElement = driver.findElement(By.name(SEARCH_BOX_NAME));
		searchElement.sendKeys(String.valueOf(classID));
		searchElement.submit();

		List<WebElement> registerElement = driver.findElements(By
				.className(REGISTRATION_ACCEPT_CLASS));

		if (registerElement.size() == 0) {
			System.out.println("No available class found for " + classID);
			driver.findElement(By.linkText(BACK_TO_REGISTRATION)).click();
		} else {
			registerElement.get(0).click();
			WebElement addClassElement = driver.findElement(By
					.name(ADD_CLASS_NAME));
			addClassElement.click();

			try {
				WebElement sucessElement = driver.findElement(By
						.cssSelector(SUCCESS_CSS));
			} catch (NoSuchElementException e) {
				System.out.println("Unsuccessful for " + classID);
			}
		}
	}

	private void timer(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
