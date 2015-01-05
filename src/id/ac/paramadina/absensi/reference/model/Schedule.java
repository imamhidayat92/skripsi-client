package id.ac.paramadina.absensi.reference.Model;

public class Schedule {
	private String id;
	
	private int dayCode;
	private String startTime;
	private String endTime;
	
	private Course course;
	private ClassLocation location;
	
	private int countMeeting;
	
	public Schedule(String id,
					int dayCode, 
					String startTime, 
					String endTime, 
					Course course, 
					ClassLocation location, 
					int countMeeting) 
	{
		this.id = id;
		this.dayCode = dayCode;
		this.startTime = startTime;
		this.endTime = endTime;
		this.course = course;
		this.location = location;
		this.countMeeting = countMeeting;
	}
	
	public String getId() {
		return this.id;
	}
	
	public int getDayCode() {
		return dayCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public Course getCourse() {
		return course;
	}
	
	public ClassLocation getLocation() {
		return location;
	}
	
	public String getInfo() {
		return this.startTime + " s.d. " + this.endTime + " di " + this.location.getName();
	}

	public int getMeetingCount() {
		return countMeeting;
	}
}
