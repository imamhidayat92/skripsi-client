package id.ac.paramadina.absensi.reference.spec;

import java.util.HashMap;

public class TeachingReportDataSpec extends BaseSpec {

    private String subject;
    private String description;

    public TeachingReportDataSpec(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap data = new HashMap<String, String>();
        data.put("subject", this.subject);
        data.put("description", this.description);

        return data;
    }
}
