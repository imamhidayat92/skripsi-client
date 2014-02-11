package id.ac.paramadina.absensi.reference.model;

public class Course {
	private String majorName;
	private String majorColorString;
	private String courseTitle;
	private String courseSubTitle;
	private String courseDay;
	private String courseStartTime;
	private String courseEndTime;
	private String courseVenue;
	
	public Course(
			String majorName,
			String majorColorString,
			String courseTitle,
			String courseDay,
			String courseStartTime,
			String courseEndTime,
			String courseVenue)
	{
		this.majorName = majorName;
		this.majorColorString = majorColorString;
		this.courseTitle = courseTitle;
		this.courseDay = courseDay;
		this.courseStartTime = courseStartTime;
		this.courseEndTime = courseEndTime;
		this.courseVenue = courseVenue;
	}
	
	/* Accessor Method */
	
	public String getMajorName() {
		return this.majorName;
	}
	
	public String getMajorColor() {
		return this.majorColorString;
	}
	
	public String getCourseTitle() {
		return this.courseTitle;
	}
	
	public String getCourseSubTitle() {
		return this.courseSubTitle;
	}
	
	public String getCourseTime() {
		return this.courseDay + ", " + this.courseStartTime + " - " + this.courseEndTime;
	}
	
	public String getCourseVenue() {
		return this.courseVenue;
	}
	
	public String getCourseInfo() {
		return "";
	}
	
	/* Mutator Method */
	
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	
	public void setMajorColor(String majorColorString) {
		this.majorColorString = majorColorString;
	}
	
	public void setCourseDay(String day) {
		this.courseDay = day;
	}
	
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	
	public void setCourseSubTitle(String courseSubTitle) {
		this.courseSubTitle = courseSubTitle;
	}
	
	public void setCourseStartTime(String courseStartTime) {
		this.courseStartTime = courseStartTime;
	}
	
	public void setCourseEndTime(String courseEndTime) {
		this.courseStartTime = courseEndTime;
	}
	
	public void setCourseVenue(String courseVenue) {
		this.courseVenue = courseVenue;
	}
}
