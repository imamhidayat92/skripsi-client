package id.ac.paramadina.absensi.reference.model;

public class Course {
	private String id;
	private String name;
	private String description;
	private int credits;
	
	private Major major;

	public Course(String id, String name, String description, int credits, Major major) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.credits = credits;
		this.major = major;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getCredits() {
		return credits;
	}

	public Major getMajor() {
		return major;
	}
}
