package id.ac.paramadina.absensi.reference.enumeration;

public enum UserIdentificationField {
    IDENTIFIER("identifier"),
    ID_NUMBER("id_number");

    private final String text;

    private UserIdentificationField(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
