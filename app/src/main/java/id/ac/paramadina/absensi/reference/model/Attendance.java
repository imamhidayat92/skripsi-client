package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import id.ac.paramadina.absensi.reference.enumeration.AttendanceStatusType;

public class Attendance {
    public enum Fields {
        ID("_id"),

        STATUS("status"),
        REMARKS("remarks"),

        STUDENT("student"),
        SCHEDULE("schedule"),
        CLASS_MEETING("class_meeting"),

        CREATED("created"),
        MODIFIED("modified"),
        CREATED_MILLISECONDS("created_ms"),
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

    private String id;

	private AttendanceStatusType status;
	private String remarks;
	
	private ClassMeeting classMeeting;
	private User student;
	private Schedule schedule;
	
	private Calendar created;
	private Calendar modified;

    public Attendance(AttendanceStatusType status, String remarks, long created) {
        this.status = status;
        this.remarks = remarks;

        this.created = Calendar.getInstance();
        this.created.setTimeInMillis(created);
    }

    public Attendance(String id, AttendanceStatusType status, String remarks, long created) {
        this(status, remarks, created);
        this.id = id;
    }

    public static AttendanceStatusType getAttendanceStatusType(String value) {
        for (AttendanceStatusType type : AttendanceStatusType.values()) {
            if (type.toString().equals(value)) {
                return type;
            }
        }
        return null;
    }

    public static Attendance createInstance(JSONObject response) throws JSONException {
        JSONObject rawUserData = response.getJSONObject(Fields.STUDENT.toString());
        JSONObject rawScheduleData = response.getJSONObject(Fields.SCHEDULE.toString());
        JSONObject rawClassMeetingData = response.getJSONObject(Fields.CLASS_MEETING.toString());

        User user = User.createInstance(rawUserData);
        Schedule schedule = Schedule.createInstance(rawScheduleData);
        ClassMeeting classMeeting = ClassMeeting.createInstance(rawClassMeetingData);

        Attendance attendance = new Attendance(
            response.getString(Fields.ID.toString()),
            getAttendanceStatusType(response.getString(Fields.STATUS.toString())),
            response.getString(Fields.REMARKS.toString()),
            response.getLong(Fields.CREATED_MILLISECONDS.toString())
        );
        attendance.setStudent(user);
        attendance.setSchedule(schedule);
        attendance.setClassMeeting(classMeeting);

        return attendance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public AttendanceStatusType getStatus() {
		return status;
	}
	
	public void setStatus(AttendanceStatusType status) {
		this.status = status;
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

    public void setCreated(String strDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("");
        Date date = formatter.parse(strDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.created = calendar;
    }

	public Calendar getModified() {
		return modified;
	}

	public void setModified(Calendar modified) {
		this.modified = modified;
	}
}
