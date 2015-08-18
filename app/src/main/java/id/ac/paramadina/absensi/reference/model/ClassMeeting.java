package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import id.ac.paramadina.absensi.reference.enumeration.ClassMeetingType;

public class ClassMeeting {
    public enum Fields {
        TYPE("type"),
        VERIFIED("verified"),

        COURSE("course"),
        LECTURER("lecturer"),
        REPORT("report"),
        SCHEDULE("schedule"),

        CREATED("created"),
        CREATED_MILLISECONDS("created_ms"),
        MODIFIED("modified"),
        MODIFIED_MILLISECONDS("modified_ms");

        private final String text;
        private Fields(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return this.text;
        }
    }

	private ClassMeetingType type;
	private boolean verified;

	private Course course;
	private User lecturer;
	private TeachingReport report;
	private Schedule schedule;

    /* Cache */
	private Attendance[] attendances;
	
	private Calendar created;
	private Calendar modified;

    public static ClassMeeting createInstance(JSONObject response) throws JSONException {
        ClassMeetingType type = ClassMeetingType.valueOf(response.getString(Fields.TYPE.toString()));
        boolean verified = response.getBoolean(Fields.VERIFIED.toString());
        long created = response.getLong(Fields.CREATED_MILLISECONDS.toString());
        long modified = response.getLong(Fields.MODIFIED_MILLISECONDS.toString());

        Course course = Course.createInstance(response.getJSONObject(Fields.COURSE.toString()));
        User lecturer = User.createInstance(response.getJSONObject(Fields.LECTURER.toString()));
        TeachingReport report = TeachingReport.createInstance(response.getJSONObject(Fields.REPORT.toString()));
        Schedule schedule = Schedule.createInstance(response.getJSONObject(Fields.SCHEDULE.toString()));
        
        ClassMeeting classMeeting = new ClassMeeting(type, verified, created, modified);
        classMeeting.setCourse(course);
        classMeeting.setLecturer(lecturer);
        classMeeting.setReport(report);
        classMeeting.setSchedule(schedule);
        return classMeeting;
    }

    public ClassMeeting(ClassMeetingType type, boolean verified, long created, long modified) {
        this.type = type;
        this.verified = verified;
        this.created = Calendar.getInstance();
        this.created.setTimeInMillis(created);
        this.modified = Calendar.getInstance();
        this.modified.setTimeInMillis(modified);
    }

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
