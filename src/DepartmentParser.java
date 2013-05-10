import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DepartmentParser {

	private static String baseURL = "http://www.columbia.edu/cu/bulletin/uwb/sel/dept-";
	private static String[] alphabets = { "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	public DepartmentParser() {
		System.out.println("DepartmentParser object called");
		System.out.println("DepartmentParser object constructed");
	}

	public ArrayList<String> getDepartments() {
		ArrayList<String> allDepartments = new ArrayList<String>();

		allDepartments.add("(All)"); // Add "unlimited"
		System.out.println("DepartmentParser: getting departments");
		ArrayList<SwingWorker> swingWorkers = new ArrayList<SwingWorker>();
		for (String alphabet : alphabets) {
			SwingWorker fetcherSwingWorker = new FetcherSwingWorker(alphabet);
			fetcherSwingWorker.execute();
			swingWorkers.add(fetcherSwingWorker);
		}
		
		for(SwingWorker fetcherSwingWorker: swingWorkers) {
			try {
				allDepartments.addAll((Collection<? extends String>) fetcherSwingWorker.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("DepartmentParser: departments done");
		return allDepartments;
	}

	private class FetcherSwingWorker extends
			SwingWorker<ArrayList<String>, Void> {
		private String alphabet;
		private WebDriver driver;
		public FetcherSwingWorker(String alphabet) {
			this.alphabet = alphabet;
			driver = new HtmlUnitDriver(true);
		}
		
		
		@Override
		protected ArrayList<String> doInBackground() throws Exception {
			System.out.println("Fetching " + alphabet);
			ArrayList<String> departments = new ArrayList<String>();
			
			String deptURL = baseURL + alphabet + ".html";
			driver = new HtmlUnitDriver(true);
			driver.get(deptURL);
			List<WebElement> rows = driver.findElements(By
					.cssSelector("tr[valign=top]"));
			for (WebElement row : rows) {

				try {
					WebElement link = row
							.findElement(By.cssSelector("a[href]"));
					String shortDept = link.getAttribute("href").replaceAll(
							"http://www.columbia.edu/cu/bulletin/uwb/sel/", "");
					shortDept = shortDept.substring(0, shortDept.indexOf("_"));

					WebElement name = row.findElement(By
							.cssSelector("td[bgcolor=\"#DADADA\"]"));
					String longDept = name.getText();

					String fullDept = shortDept + "-" + longDept;

					departments.add(fullDept);

				} catch (NoSuchElementException e) {

				}
			}
			return departments;
		}

	}
}
