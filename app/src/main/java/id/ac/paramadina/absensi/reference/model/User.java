package id.ac.paramadina.absensi.reference.model;

public class User {
	private String name;
	private String displayName;
	private String idNumber;
	private String identifier;
	private String avatar;
	
	public User(String name, String displayName, String idNumber, String identifier, String avatar) {
		this.setName(name);
		this.setDisplayName(displayName);
		this.setIdNumber(idNumber);
		this.setIdentifier(identifier);
		this.setAvatar(avatar);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
