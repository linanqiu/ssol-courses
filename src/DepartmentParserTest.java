
public class DepartmentParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DepartmentParser test = new DepartmentParser();
		for(String department : test.getDepartments()) {
			System.out.println(department);
		}

	}

}
