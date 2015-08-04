package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public enum FIELDS {
        NAME("name"),
        DISPLAY_NAME("display_name"),
        ID_NUMBER("id_number"),
        IDENTIFIER("identifier"),
        DISPLAY_PICTURE("display_picture");

        private final String text;

        private FIELDS(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return this.text;
        }
    }

	private String name;
	private String displayName;
	private String idNumber;
	private String identifier;
	private String displayPicture;

    public static User createInstance(JSONObject response) throws JSONException {
        JSONObject userData = response.getJSONObject("result");

        String name = userData.getString(FIELDS.NAME.toString());
        String displayName = userData.getString(FIELDS.DISPLAY_NAME.toString());
        String idNumber = userData.getString("id_number");
        String identifier = userData.getString("identifier");
        String displayPicture = null;
        if (userData.has("display_picture")) {
            displayPicture = userData.getString("display_picture");
        }

        return new User(name, displayName, idNumber, identifier, displayPicture);
    }

	public User(String name, String displayName, String idNumber, String identifier, String displayPicture) {
		this.setName(name);
		this.setDisplayName(displayName);
		this.setIdNumber(idNumber);
		this.setIdentifier(identifier);
		this.setDisplayPicture(displayPicture);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDisplayPicture() {
		return displayPicture;
	}

	public void setDisplayPicture(String displayPicture) {
		this.displayPicture = displayPicture;
	}
}
