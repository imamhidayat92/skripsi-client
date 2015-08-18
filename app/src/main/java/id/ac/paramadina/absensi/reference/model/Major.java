package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Major {
	private String name;
	private String color;

    public enum Fields {
        ID("_id"),

        NAME("name"),
        COLOR("color");

        private final String text;
        private Fields(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return this.text;
        }
    }

    public static Major createInstance(JSONObject response) throws JSONException {
        String name = response.getString(Fields.NAME.toString());
        String color = response.getString(Fields.COLOR.toString());

        return new Major(name, color);
    }

	public Major(String name, String color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getColor() {
		return this.color;
	}
}
