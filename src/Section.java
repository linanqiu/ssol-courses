import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * A description of sections, implemented by HashMap
 * 
 * @author Xingzhou He
 * @uni xh2187
 */

public class Section {

	private static final String TIME_FORMAT = "HH:mm:ss";
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			TIME_FORMAT, Locale.getDefault());

	private HashMap<String, String> data;

	/**
	 * Prevent empty section.
	 */
	@SuppressWarnings("unused")
	private Section() {
	}

	/**
	 * Create a section from parsed data.
	 * 
	 * @param data
	 *            Data to be added to section
	 */
	public Section(HashMap<String, String> data) {
		if (!data.containsKey("Term") || !data.containsKey("StartTime1")
				|| !data.containsKey("EndTime1")
				|| !data.containsKey("CallNumber")
				|| !data.containsKey("CourseTitle"))
			throw new IllegalArgumentException();
		this.data = data;
	}

	public String getDay() {
		return data.get("MeetsOn1");
	}

	public Date getStartTime() {
		try {
			return timeFormat.parse(data.get("StartTime1"));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Date getEndTime() {
		try {
			return timeFormat.parse(data.get("EndTime1"));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getInstructor() {
		return data.get("Instructor1Name");
	}

	public int getCallNumber() {
		return Integer.valueOf(data.get("CallNumber"));
	}

	public String getLocation() {
		return data.get("Room1") + " " + data.get("Building1");
	}

}
