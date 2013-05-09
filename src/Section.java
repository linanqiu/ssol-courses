import java.util.Date;


/**
 * A description of sections
 * 
 * @author Xingzhou He
 * @uni xh2187
 */

public class Section implements CourseText {

	public Section() {
		parent = null;
	}
	
	/**
	 * Set parent course
	 * @param course
	 */
	public void setCourse(Course course)
	{
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
	
	public String getSectionFull() {
		return SectionFull;
	}
	
	@Override
	public String getCourseNumber() {
		return parent.getCourseNumber();
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
