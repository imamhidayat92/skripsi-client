package id.ac.paramadina.absensi.reference.spec;

import java.util.HashMap;

import id.ac.paramadina.absensi.reference.enumeration.UserIdentificationField;

public class NewAttendanceDataSpec extends BaseSpec {
	private String tagId;
    private String userIdNumber;
    private UserIdentificationField field;

	public NewAttendanceDataSpec(String id, UserIdentificationField field) {
		this.field = field;
        switch (field) {
            case IDENTIFIER:
                this.tagId = id;
            case ID_NUMBER:
                this.userIdNumber = id;
        }
	}
	
	public String getTagId() {
		return this.tagId;
	}

    public String getUserIdNumber() {
        return this.userIdNumber;
    }

	@Override
	public HashMap<String, String> toHashMap() {
		HashMap<String, String> data = new HashMap<String, String>();
		switch (this.field) {
            case IDENTIFIER:
                data.put("identifier", this.tagId);
            case ID_NUMBER:
                data.put("id_number", this.userIdNumber);
        }

		return data;
	}
}
