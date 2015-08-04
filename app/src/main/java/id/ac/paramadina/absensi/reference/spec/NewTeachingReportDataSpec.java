package id.ac.paramadina.absensi.reference.spec;

import java.util.HashMap;

public class NewTeachingReportDataSpec extends BaseSpec {

    private String subject;
    private String description;

    public NewTeachingReportDataSpec(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("subject", this.subject);
        data.put("description", this.description);

        return data;
    }
}
