package id.ac.paramadina.absensi.reference.Model;

public class ClassLocation {
	private String name;
	private String description;
	
	public ClassLocation(String name, String description) {
		this.name = name;
		this.description  = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
