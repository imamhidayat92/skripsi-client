package id.ac.paramadina.absensi.reference.spec;

import java.util.HashMap;

public class UpdateAttendanceDataSpec extends BaseSpec {

    private String status;
    private String remarks;
    private Boolean verified;

    public UpdateAttendanceDataSpec(String status, String remarks, Boolean verified) {
        this.status = status;
        this.remarks = remarks;
        this.verified = verified;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> result = new HashMap<String, String>();

        if (this.status != null) {
            result.put("status", this.status);
        }
        if (this.remarks != null) {
            result.put("remarks", this.remarks);
        }
        if (this.verified != null) {
            result.put("verified", String.valueOf(this.verified));
        }

        return result;
    }
}
