import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class BlockChecker implements Runnable {
	private String uni;
	private String password;
	private WebDriver driver;

	public BlockChecker(String uni, String password) {
		this.uni = uni;
		this.password = password;
		driver = new HtmlUnitDriver(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		driver.get("https://ssol.columbia.edu");
		// u_idField is the field for username
		WebElement u_idField = driver.findElement(By
				.cssSelector("input[name=u_id]"));

		// u_pwField is the field for password
		WebElement u_pwField = driver.findElement(By
				.cssSelector("input[name=u_pw]"));

		// sends username and password over and submits username and password
		u_idField.sendKeys(uni);
		u_pwField.sendKeys(password);
		u_pwField.submit();
		
		if(driver.getPageSource().toLowerCase().indexOf("ip blocked") > -1) {
			
		}
	}
}
