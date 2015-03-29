package id.ac.paramadina.absensi.reference.enumeration;

public enum AttendanceStatusType {
	PRESENT("present"),
	UNKNOWN("unknown"),
	SPECIAL_PERSMISSION("special_permission");
	
	private final String text;
	
	private AttendanceStatusType(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
}
