package id.ac.paramadina.absensi.reference.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.ac.paramadina.absensi.helper.CommonDataHelper;
import id.ac.paramadina.absensi.reference.enumeration.AttendanceStatusType;

public class Attendance {
    public enum Fields {
        ID("_id"),

        STATUS("status"),
        REMARKS("remarks"),
        MODE("mode"),

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
        User user = null;
        if (response.has(Fields.STUDENT.toString()) && CommonDataHelper.isPopulatedObject(response, Fields.STUDENT.toString())) {
            user = User.createInstance(response.getJSONObject(Fields.STUDENT.toString()));
        }

        Schedule schedule = null;
        if (response.has(Fields.SCHEDULE.toString()) && CommonDataHelper.isPopulatedObject(response, Fields.SCHEDULE.toString())) {
            schedule = Schedule.createInstance(response.getJSONObject(Fields.SCHEDULE.toString()));
        }

        ClassMeeting classMeeting = null;
        if (response.has(Fields.CLASS_MEETING.toString()) && CommonDataHelper.isPopulatedObject(response, Fields.CLASS_MEETING.toString())) {
            classMeeting = ClassMeeting.createInstance(response.getJSONObject(Fields.CLASS_MEETING.toString()));
        }

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

    public static ArrayList<Attendance> createInstances(JSONArray response) throws JSONException {
        ArrayList<Attendance> results = new ArrayList<Attendance>();

        for (int i = 0; i < response.length(); i++) {
            JSONObject rawAttendanceData = response.getJSONObject(i);
            Attendance attendance = Attendance.createInstance(rawAttendanceData);
            results.add(attendance);
        }

        return results;
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
