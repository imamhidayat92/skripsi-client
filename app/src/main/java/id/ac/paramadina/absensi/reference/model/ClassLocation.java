package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ClassLocation {
	public enum Fields {
        NAME("name"),
        DESCRIPTION("description");

        private final String text;
        private Fields(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return this.text;
        }
    }

    private String name;
	private String description;

    public static ClassLocation createInstance(JSONObject response) throws JSONException {
        String name = response.getString(Fields.NAME.toString());
        String description = response.getString(Fields.DESCRIPTION.toString());

        ClassLocation location = new ClassLocation(name, description);
        return location;
    }

	public ClassLocation(String name, String description) {
		this.name = name;
		this.description  = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
