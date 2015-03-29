package id.ac.paramadina.absensi.reference.model;

public class Student {
	private String id;
	private String displayName;
	private Major major;
	
	public Student(String id, String displayName, Major major) {
		this.id = id;
		this.displayName = displayName;
		this.major = major;
	}
	
	public String getId() {
		return id;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public Major getMajor() {
		return major;
	}
}
