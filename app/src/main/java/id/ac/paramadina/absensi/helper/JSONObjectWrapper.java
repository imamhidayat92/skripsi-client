package id.ac.paramadina.absensi.helper;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectWrapper {

    private JSONObject jsonObject;

    public JSONObjectWrapper(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getString(String key) {
        try {
            return this.jsonObject.getString(key);
        } catch (JSONException e) {
            return null;
        }
    }

    public Integer getInt(String key) {
        try {
            return this.jsonObject.getInt(key);
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject getJSONObject(String key) {
        try {
            return this.jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            return null;
        }
    }
}
