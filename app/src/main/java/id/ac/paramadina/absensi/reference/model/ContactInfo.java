package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class ContactInfo {

    public enum Fields {
        ID("_id"),

        FATHER_NAME("father_name"),
        MOTHER_NAME("mother_name"),
        REPRESENTATIVE_NAME("representative_name"),
        ADDRESS("address"),
        PHONE("phone"),
        EMAIL("email"),
        MOBILE_PHONE("mobile_phone"),

        STUDENT("student"),

        CREATED("created"),
        MODIFIED("modified"),
        CREATED_MS("created_ms"),
        MODIFIED_MS("modified_ms")
        ;

        private final String text;

        private Fields(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return this.text;
        }
    }

    private String id;

    private String fatherName;
    private String motherName;
    private String representativeName;
    private String address;
    private String phone;
    private String email;
    private String mobilePhone;

    private User student;
    private String studentId;

    private Calendar created;
    private Calendar modified;

    public static ContactInfo createInstance(JSONObject response) throws JSONException {
        String fatherName = response.getString(Fields.FATHER_NAME.toString());
        String motherName = response.getString(Fields.MOTHER_NAME.toString());
        String representativeName = response.getString(Fields.REPRESENTATIVE_NAME.toString());
        String address = response.getString(Fields.ADDRESS.toString());
        String phone = response.getString(Fields.PHONE.toString());
        String email = response.getString(Fields.EMAIL.toString());
        String mobilePhone = response.getString(Fields.MOBILE_PHONE.toString());

        ContactInfo contactInfo = new ContactInfo();

        contactInfo.setFatherName(fatherName);
        contactInfo.setMotherName(motherName);
        contactInfo.setRepresentativeName(representativeName);
        contactInfo.setAddress(address);
        contactInfo.setPhone(phone);
        contactInfo.setEmail(email);
        contactInfo.setMobilePhone(mobilePhone);

        return contactInfo;
    }

    public ContactInfo() {

    }

    public String getId() {
        return this.id;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getModified() {
        return modified;
    }

    public void setModified(Calendar modified) {
        this.modified = modified;
    }
}
