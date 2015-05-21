package id.ac.paramadina.absensi.reference.model;

import java.util.Calendar;

import id.ac.paramadina.absensi.reference.enumeration.ClassMeetingType;

public class ClassMeeting {
	private ClassMeetingType type;
	
	private Course course;
	private User lecturer;
	private TeachingReport report;
	private Schedule schedule;
	private Attendance[] attendances;
	
	private Calendar created;
	private Calendar modified;
	
	public ClassMeetingType getType() {
		return type;
	}
	
	public void setType(ClassMeetingType type) {
		this.type = type;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public User getLecturer() {
		return lecturer;
	}

	public void setLecturer(User lecturer) {
		this.lecturer = lecturer;
	}

	public TeachingReport getReport() {
		return report;
	}

	public void setReport(TeachingReport report) {
		this.report = report;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Attendance[] getAttendances() {
		return attendances;
	}

	public void setAttendances(Attendance[] attendances) {
		this.attendances = attendances;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Calendar getModified() {
		return modified;
	}

	public void setModified(Calendar modified) {
		this.modified = modified;
	}
}
