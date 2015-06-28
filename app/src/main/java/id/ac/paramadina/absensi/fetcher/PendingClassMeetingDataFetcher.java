package id.ac.paramadina.absensi.fetcher;

import android.app.Activity;

import org.json.JSONObject;

import java.util.HashMap;

public class PendingClassMeetingDataFetcher extends BaseFetcher {

    public PendingClassMeetingDataFetcher(Activity activity) {
        super(activity);

        HashMap<String, String> additionalParams = new HashMap<String, String>();
        additionalParams.put("verified", "false");

        this.setResourceUrl("/class_meetings", additionalParams);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }
}
