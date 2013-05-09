import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DepartmentParser {

	private WebDriver driver;
	private static String baseURL = "http://www.columbia.edu/cu/bulletin/uwb/sel/dept-";
	private static String[] alphabets = { "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	public DepartmentParser() {
		System.out.println("DepartmentParser object called");
		driver = new HtmlUnitDriver(true);
		System.out.println("DepartmentParser object constructed");
	}

	public ArrayList<String> getDepartments() {
		System.out.println("DepartmentParser: getting departments");
		ArrayList<String> departments = new ArrayList<String>();
		for(String alphabet : alphabets) {
			String deptURL = baseURL + alphabet + ".html";
			driver.get(deptURL);
			List<WebElement> rows = driver.findElements(By.cssSelector("tr[valign=top]"));
			for(WebElement row : rows) {
				
				try {
					WebElement link = row.findElement(By.cssSelector("a[href]"));
					String shortDept = link.getAttribute("href").replaceAll("http://www.columbia.edu/cu/bulletin/uwb/sel/", "");
					shortDept = shortDept.substring(0, shortDept.indexOf("_"));
					
					WebElement name = row.findElement(By.cssSelector("td[bgcolor=\"#DADADA\"]"));
					String longDept = name.getText();
					
					String fullDept = shortDept + "-" + longDept;
					
					departments.add(fullDept);
					
				} catch (NoSuchElementException e) {
					
				}
			}
		}
		System.out.println("DepartmentParser: departments done");
		return departments;
	}
}
