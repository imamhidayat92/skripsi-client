package id.ac.paramadina.absensi.reference.model;

import java.util.Calendar;

import id.ac.paramadina.absensi.reference.enumeration.AttendanceStatusType;

public class Attendance {
	private AttendanceStatusType type;
	private String remarks;
	
	private ClassMeeting classMeeting;
	private User student;
	private Schedule schedule;
	
	private Calendar created;
	private Calendar modified;
	
	public AttendanceStatusType getType() {
		return type;
	}
	
	public void setType(AttendanceStatusType type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ClassMeeting getClassMeeting() {
		return classMeeting;
	}

	public void setClassMeeting(ClassMeeting classMeeting) {
		this.classMeeting = classMeeting;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
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
