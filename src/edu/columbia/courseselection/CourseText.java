package edu.columbia.courseselection;
import java.util.Date;

/**
 * Interface for displaying course/section information
 * @author Derek Xingzhou He (xh2187)
 */
public interface CourseText {

	// Course & Section
	String getCourseNumber();
	String getTitle();
	String getDescription();
	Integer getCredits();
	
	// Section only
	String getSemester();
	String getInstructor();
	Integer getCallNumber();
	Date getStartTime();
	Date getEndTime();
	String getMeetDay();
	String getLocation();
	Integer getMaxSize();
	Integer getNumEnroll();
	
	
}
