package id.ac.paramadina.absensi.reference.spec;

import java.util.HashMap;

import id.ac.paramadina.absensi.reference.enumeration.ClassMeetingType;

/**
 * This is a class to create new ClassMeeting data in the server. We don't need to provide all
 * information, since this data will be modified while students tap their tag to the device.
 */
public class ClassMeetingDataSpec extends BaseSpec {

    public ClassMeetingType type;
    public String courseId;
    public String lecturerId;
    public String scheduleId;

    public ClassMeetingDataSpec(ClassMeetingType type, String courseId, String lecturerId, String scheduleId) {
        this.type = type;
        this.courseId = courseId;
        this.lecturerId = lecturerId;
        this.scheduleId = scheduleId;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> spec = new HashMap<String, String>();
        spec.put("type", this.type.toString());
        spec.put("course", this.courseId);
        spec.put("lecturer", this.lecturerId);
        spec.put("schedule", this.scheduleId);

        return spec;
    }
}
