package id.ac.paramadina.absensi.reference.Model;

import java.util.ArrayList;

public class Course {
	private ArrayList<Major> majors;
	private int id;
	private String courseTitle;
	private String courseSubTitle;
	private String courseStartTime;
	private String courseEndTime;
	private String courseVenue;
	
	public Course(
			int id,
			ArrayList<Major> majors,
			String courseTitle,
			String courseSubTitle,
			String courseStartTime,
			String courseEndTime,
			String courseVenue)
	{
		this.id = id;
		this.majors = majors;
		this.courseTitle = courseTitle;
		this.courseSubTitle = courseSubTitle;
		this.courseStartTime = courseStartTime;
		this.courseEndTime = courseEndTime;
		this.courseVenue = courseVenue;
	}
	
	/* Accessor Method */
	
	public int getId() {
		return this.id;
	}
	
	public int getNumberOfMajors() {
		return this.majors.size();
	}
	
	public String getMajorName(int index) {
		return this.majors.get(index).getName();
	}
	
	public String getMajorColor(int index) {
		return this.majors.get(index).getColor();
	}
	
	public String getCourseTitle() {
		return this.courseTitle;
	}
	
	public String getCourseSubTitle() {
		return this.courseSubTitle;
	}
	
	public String getCourseVenue() {
		return this.courseVenue;
	}
	
	public String getCourseInfo() {
		return this.courseStartTime + " - " + this.courseEndTime + ", " + this.courseVenue;
	}
}
