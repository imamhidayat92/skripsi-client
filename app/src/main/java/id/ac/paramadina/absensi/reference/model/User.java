package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public enum Fields {
        ID("_id"),

        NAME("name"),
        DISPLAY_NAME("display_name"),
        ID_NUMBER("id_number"),
        IDENTIFIER("identifier"),
        DISPLAY_PICTURE("display_picture");

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
	private String displayName;
	private String idNumber;
	private String identifier;
	private String displayPicture;

    public static User createInstance(JSONObject response) throws JSONException {
        JSONObject userData = response.getJSONObject("result");

        String id = userData.getString(Fields.ID.toString());
        String name = userData.getString(Fields.NAME.toString());
        String displayName = userData.getString(Fields.DISPLAY_NAME.toString());
        String idNumber = userData.getString(Fields.ID_NUMBER.toString());
        String identifier = userData.getString(Fields.IDENTIFIER.toString());
        String displayPicture = null;
        if (userData.has(Fields.DISPLAY_PICTURE.toString())) {
            displayPicture = userData.getString(Fields.DISPLAY_PICTURE.toString());
        }

        User user = new User(id, name, displayName, idNumber, identifier, displayPicture);
        return user;
    }

	public User(String name, String displayName, String idNumber, String identifier, String displayPicture) {
		this.setName(name);
		this.setDisplayName(displayName);
		this.setIdNumber(idNumber);
		this.setIdentifier(identifier);
		this.setDisplayPicture(displayPicture);
	}

    public User(String id, String name, String displayName, String idNumber, String identifier, String displayPicture) {
        this(name, displayName, idNumber, identifier, displayPicture);
        this.id = id;
    }

    public String getId() {
        return this.id;
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
