import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Object to implement a course
 * 
 * @author Xingzhou xh2187
 */

@SuppressWarnings("unused")
public class Course implements CourseText, Serializable {

	public Course() {

	}

	// These fields are capitalized for GSON processor
	// From data.adicu.com
	private String DepartmentCode;
	private String Course;
	private Section[] Sections;
	private int NumFixedUnits;
	private String CourseTitle;
	private String Description;

	public String toString() {
		return getCourseNumber() + " " + getTitle();
	}

	/**
	 * Delete sections not within the term. Used for post-processing
	 */
	public void postProcess(String term) {
		ArrayList<Section> termSections = new ArrayList<Section>();
		for (Section i : Sections)
			if (i.getSemester().equals(term)) {
				i.setCourse(this);
				termSections.add(i);
			}
		Sections = termSections.toArray(new Section[termSections.size()]);
	}

	public Section[] getSections() {
		return Sections;
	}

	@Override
	public String getCourseNumber() {
		return Course;
	}

	@Override
	public String getTitle() {
		return CourseTitle;
	}

	@Override
	public String getDescription() {
		return Description;
	}

	@Override
	public Integer getCredits() {
		return NumFixedUnits;
	}

	// The following are not provided by Course
	@Override
	public String getSemester() {
		return null;
	}

	@Override
	public String getInstructor() {
		return null;
	}

	@Override
	public Integer getCallNumber() {
		return null;
	}

	@Override
	public Date getStartTime() {
		return null;
	}

	@Override
	public Date getEndTime() {
		return null;
	}

	@Override
	public String getLocation() {
		return null;
	}

	@Override
	public String getMeetDay() {
		return null;
	}

	@Override
	public Integer getMaxSize() {
		return null;
	}

	@Override
	public Integer getNumEnroll() {
		return null;
	}

}
