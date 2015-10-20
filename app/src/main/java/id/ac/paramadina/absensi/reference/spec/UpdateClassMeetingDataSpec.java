package id.ac.paramadina.absensi.reference.spec;

import java.util.HashMap;

import id.ac.paramadina.absensi.reference.enumeration.AttendanceStatusType;

public class UpdateClassMeetingDataSpec extends BaseSpec {

    private AttendanceStatusType type;
    private Boolean verified;

    public UpdateClassMeetingDataSpec(AttendanceStatusType type, Boolean verified) {
        this.type = type;
        this.verified = verified;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> result = new HashMap<String, String>();

        if (this.type != null) {
            result.put("type", this.type.toString());
        }
        if (this.verified != null) {
            result.put("verified", String.valueOf(this.verified));
        }

        return result;
    }
}
