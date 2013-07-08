import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A description of sections
 * 
 * @author Xingzhou He
 * @uni xh2187
 */

public class Section implements CourseText, Serializable {

	public Section() {
		parent = null;
	}

	/**
	 * Set parent course
	 * 
	 * @param course
	 */
	public void setCourse(Course course) {
		parent = course;
	}

	private Course parent;
	private String Term;
	private String Instructor1Name;
	private String Room1, Building1;
	private Integer CallNumber;
	private Date StartTime1;
	private Date EndTime1;
	private String MeetsOn1;
	private Integer MaxSize;
	private Integer NumEnrolled;
	private String SectionFull;

	public boolean equals(Section that) {
		if (that == null || getCallNumber() != that.getCallNumber())
			return false;
		return true;
	}

	public String toString() {
		return "Sec " + getCourseNumber().substring(9) + ":" + getInstructor()
				+ ", " + getTime();
	}

	/**
	 * Get parsed time
	 * 
	 * @return Time as in a string, ready to be displayed
	 */
	public String getTime() {
		if (getMeetDay() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			return getMeetDay() + " " + formatter.format(getStartTime()) + "-"
					+ formatter.format(getEndTime());
		} else {
			return "n/a";
		}

	}

	public String getSectionFull() {
		return SectionFull;
	}

	@Override
	public String getCourseNumber() {
		return SectionFull;
	}

	@Override
	public String getTitle() {
		return parent.getTitle();
	}

	@Override
	public String getDescription() {
		return parent.getDescription();
	}

	@Override
	public Integer getCredits() {
		return parent.getCredits();
	}

	@Override
	public String getSemester() {
		return Term;
	}

	@Override
	public String getInstructor() {
		return Instructor1Name;
	}

	@Override
	public Integer getCallNumber() {
		return CallNumber;
	}

	@Override
	public Date getStartTime() {
		return StartTime1;
	}

	@Override
	public Date getEndTime() {
		return EndTime1;
	}

	@Override
	public String getLocation() {
		return Room1 + " " + Building1;
	}

	@Override
	public String getMeetDay() {
		return MeetsOn1;
	}

	@Override
	public Integer getMaxSize() {
		return MaxSize;
	}

	@Override
	public Integer getNumEnroll() {
		return NumEnrolled;
	}

}
