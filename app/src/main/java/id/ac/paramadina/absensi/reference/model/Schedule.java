package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.ac.paramadina.absensi.helper.CommonDataHelper;

public class Schedule {

    public static class ScheduleExtras {
        public enum ExtrasFields {
            CLASS_MEETINGS("class_meetings");

            private final String text;
            private ExtrasFields(final String text) {
                this.text = text;
            }

            @Override
            public String toString() {
                return this.text;
            }
        }

        private List<ClassMeeting> classMeetings;

        public static ScheduleExtras createInstance(JSONObject rawExtras) throws JSONException {
            ScheduleExtras extras = null;

            if (rawExtras.has(ExtrasFields.CLASS_MEETINGS.toString())) {
                ArrayList<ClassMeeting> classMeetings = ClassMeeting.createInstances(rawExtras.getJSONArray(ExtrasFields.CLASS_MEETINGS.toString()));
                extras = new ScheduleExtras(classMeetings);
            }

            return extras;
        }

        public ScheduleExtras(List<ClassMeeting> classMeetings) {
            this.classMeetings = classMeetings;
        }

        public int getClassMeetingsCount() {
            return this.classMeetings.size();
        }
    }

	public enum Fields {
        ID("_id"),

        DAY_CODE("day_code"),
        START_TIME("start_time"),
        END_TIME("end_time"),

        COURSE("course"),
        CLASS_LOCATION("class_location"),

        EXTRAS("___extras");

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

	private int dayCode;
	private String startTime;
	private String endTime;
	
	private Course course;
	private ClassLocation location;

    private ScheduleExtras extras;

    public static Schedule createInstance(JSONObject response) throws JSONException {
        String id = response.getString(Fields.ID.toString());
        int dayCode = response.getInt(Fields.DAY_CODE.toString());
        String startTime = response.getString((Fields.START_TIME.toString()));;
        String endTime = response.getString(Fields.END_TIME.toString());

        Course course = null;
        if (response.has(Fields.COURSE.toString()) && CommonDataHelper.isPopulatedObject(response, Fields.COURSE.toString())) {
            course = Course.createInstance(response.getJSONObject(Fields.COURSE.toString()));
        }

        ClassLocation location = null;
        if (response.has(Fields.COURSE.toString()) && CommonDataHelper.isPopulatedObject(response, Fields.COURSE.toString())) {
            location = ClassLocation.createInstance(response.getJSONObject("location"));
        }

        ScheduleExtras extras = null;
        if (response.has(Fields.EXTRAS.toString())) {
            JSONObject rawExtras = response.getJSONObject(Fields.EXTRAS.toString());
            extras = ScheduleExtras.createInstance(rawExtras);
        }

        Schedule schedule = new Schedule(id, dayCode, startTime, endTime, course, location);

        if (extras != null) {
            schedule.setExtras(extras);
        }

        return schedule;
    }

	public Schedule(String id,
					int dayCode, 
					String startTime,
					String endTime,
					Course course, 
					ClassLocation location)
	{
		this.id = id;
		this.dayCode = dayCode;
		this.startTime = startTime;
		this.endTime = endTime;
		this.course = course;
		this.location = location;
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

    public ScheduleExtras getExtras() {
        return extras;
    }

    public void setExtras(ScheduleExtras extras) {
        this.extras = extras;
    }

	public int getClassMeetingCount() {
		if (this.extras != null) {
            return this.extras.getClassMeetingsCount() + 1;
        }
        else {
            return 0;
        }
	}
}
