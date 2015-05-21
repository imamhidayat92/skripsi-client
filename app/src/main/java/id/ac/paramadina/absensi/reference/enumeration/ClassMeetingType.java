package id.ac.paramadina.absensi.reference.enumeration;

public enum ClassMeetingType {
	DEFAULT("default"),
	GENERAL("general"),
	MID_TEST("mid-test"),
	FINAL_TEST("final-test");
	
	private final String text;
	
	private ClassMeetingType(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
}
