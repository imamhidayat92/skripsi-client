package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class TeachingReport {

    public enum Fields {
        ID("_id"),

        SUBJECT("subject"),
        DESCRIPTION("description"),

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

    private String subject;
	private String description;
	
	private Calendar created;
	private Calendar modified;

    public static TeachingReport createInstance(JSONObject response) throws JSONException {
        String subject = response.getString(Fields.SUBJECT.toString());
        String description = response.getString(Fields.DESCRIPTION.toString());
        long created = response.getLong(Fields.CREATED_MILLISECONDS.toString());
        long modified = response.getLong(Fields.MODIFIED_MILLISECONDS.toString());
        TeachingReport teachingReport = new TeachingReport(subject, description, created, modified);
        return teachingReport;
    }

	public TeachingReport(String subject, String description, long created, long modified) {
		this.subject = subject;
		this.description = description;
		this.created = Calendar.getInstance();
        this.created.setTimeInMillis(created);
		this.modified = Calendar.getInstance();
        this.modified.setTimeInMillis(modified);
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Calendar getCreated() {
		return created;
	}
	
	public Calendar getModified() {
		return modified;
	}
}
