package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Course {
    public enum Fields {
        ID("_id"),

        NAME("name"),
        DESCRIPTION("description"),
        CREDITS("credits"),

        MAJOR("major");

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
	private String name;
	private String description;
	private int credits;
	
	private Major major;

    public static Course createInstance(JSONObject response) throws JSONException {
        String id = response.getString("_id");
        String name = response.getString("name");
        String description = response.getString("description");
        int credits = response.getInt("credits");

        Major major = Major.createInstance(response.getJSONObject("major"));

        Course course = new Course(id, name, description, credits, major);
        return course;
    }

	public Course(String id, String name, String description, int credits, Major major) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.credits = credits;
		this.major = major;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getCredits() {
		return credits;
	}

	public Major getMajor() {
		return major;
	}
}
