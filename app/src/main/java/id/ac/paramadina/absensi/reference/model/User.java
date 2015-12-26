package id.ac.paramadina.absensi.reference.model;

import org.json.JSONException;
import org.json.JSONObject;

import id.ac.paramadina.absensi.helper.CommonDataHelper;
import id.ac.paramadina.absensi.reference.enumeration.UserRoleType;

public class User {

    public enum Fields {
        ID("_id"),

        NAME("name"),
        DISPLAY_NAME("display_name"),
        ID_NUMBER("id_number"),
        EMAIL("email"),
        IDENTIFIER("identifier"),
        DISPLAY_PICTURE("display_picture"),
        PHONE("phone"),
        ADDRESS("address"),

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
	private String displayName;
    private String email;
	private String idNumber;
	private String identifier;
	private String displayPicture;
	private String phone;
    private String address;

    private Major major;

    public static User createInstance(JSONObject response) throws JSONException {
        String id = response.getString(Fields.ID.toString());
        String name = response.getString(Fields.NAME.toString());
        String displayName = response.getString(Fields.DISPLAY_NAME.toString());
        String idNumber = response.getString(Fields.ID_NUMBER.toString());
        String identifier = response.getString(Fields.IDENTIFIER.toString());
        String email = null;
        String phone = null;
        String address = null;
        String displayPicture = null;

        if (response.has(Fields.EMAIL.toString())) {
            email = response.getString(Fields.EMAIL.toString());
        }
        if (response.has(Fields.PHONE.toString())) {
            phone = response.getString(Fields.PHONE.toString());
        }
        if (response.has(Fields.ADDRESS.toString())) {
            address = response.getString(Fields.ADDRESS.toString());
        }
        if (response.has(Fields.DISPLAY_PICTURE.toString())) {
            displayPicture = response.getString(Fields.DISPLAY_PICTURE.toString());
        }

        Major major = null;
        if (response.has(Fields.MAJOR.toString()) && CommonDataHelper.isPopulatedObject(response, Fields.MAJOR.toString())) {
            major = Major.createInstance(response.getJSONObject(Fields.MAJOR.toString()));
        }

        User user = new User(id, name, displayName, idNumber, identifier, displayPicture);
        user.setMajor(major);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        return user;
    }

    public static UserRoleType getRoleType(String value) {
        for (UserRoleType type : UserRoleType.values()) {
            if (type.toString().equals(value)) {
                return type;
            }
        }
        return null;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Major getMajor() {
        return this.major;
    }
}
