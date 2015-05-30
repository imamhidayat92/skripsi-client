package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Student {
	private String id;
	private String displayName;
	private Major major;

    private static Student createInstance(JSONObject response) throws JSONException {
        JSONObject data = response.getJSONObject("result");

        String id = data.getString("id");
        String displayName = data.getString("id");
        Major major = new Major("", "");

        return new Student(id, displayName, major);
    }

	public Student(String id, String displayName, Major major) {
		this.id = id;
		this.displayName = displayName;
		this.major = major;
	}
	
	public String getId() {
		return id;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public Major getMajor() {
		return major;
	}
}
