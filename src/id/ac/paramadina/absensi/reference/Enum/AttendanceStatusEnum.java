package id.ac.paramadina.absensi.reference.Enum;

public enum AttendanceStatusEnum {
	PRESENT("present"),
	UNKNOWN("unknown"),
	SPECIAL_PERSMISSION("special_permission");
	
	private final String text;
	
	private AttendanceStatusEnum(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
}
