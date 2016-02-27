package id.ac.paramadina.absensi.reference.enumeration;

public enum AttendanceModeType {
    IDENTIFIER("identifier"),
    ID_NUMBER("id_number"),
    SYSTEM("system");

    private final String text;

    private AttendanceModeType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
