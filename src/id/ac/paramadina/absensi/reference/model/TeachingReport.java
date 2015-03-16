package id.ac.paramadina.absensi.reference.Model;

import java.util.Calendar;

public class TeachingReport {
	private String subject;
	private String description;
	
	private Calendar created;
	private Calendar modified;
	
	public TeachingReport(String subject, String description, Calendar created, Calendar modified) {
		this.subject = subject;
		this.description = description;
		this.created = created;
		this.modified = modified;
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
