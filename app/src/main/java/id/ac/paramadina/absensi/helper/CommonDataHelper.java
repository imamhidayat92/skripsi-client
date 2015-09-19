package id.ac.paramadina.absensi.helper;

import org.json.JSONException;
import org.json.JSONObject;

public class CommonDataHelper {
    public static boolean isPopulatedObject(JSONObject response, String field) {
        JSONObject object = null;
        try {
            object = response.getJSONObject(field);
        }
        catch (JSONException e) {
            // Expected if it's not a populated object. Just ignore it.
        }
        return object != null;
    }
}
