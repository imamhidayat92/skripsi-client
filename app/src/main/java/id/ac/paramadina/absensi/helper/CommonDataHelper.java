package id.ac.paramadina.absensi.helper;

import org.json.JSONException;
import org.json.JSONObject;

public class CommonDataHelper {
    public enum DataResultType {
        SINGLE_RESULT,
        MULTIPLE_RESULTS
    }

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

    public static boolean isValidResponse(DataResultType type, JSONObject response) throws JSONException {
        switch (type) {
            case SINGLE_RESULT:
                return response.has("success") && response.has("result") && response.getBoolean("success");
            case MULTIPLE_RESULTS:
                return response.has("success") && response.has("results") && response.getBoolean("success");
        }
        return false;
    }
}
