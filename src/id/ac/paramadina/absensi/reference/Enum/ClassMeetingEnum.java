package id.ac.paramadina.absensi.reference.Enum;

public enum ClassMeetingEnum {
	DEFAULT("default"),
	GENERAL("general"),
	MID_TEST("mid-test"),
	FINAL_TEST("final-test");
	
	private final String text;
	
	private ClassMeetingEnum(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
}
