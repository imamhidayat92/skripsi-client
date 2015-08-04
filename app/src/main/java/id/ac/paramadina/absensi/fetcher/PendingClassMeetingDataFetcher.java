package id.ac.paramadina.absensi.fetcher;

import android.app.Activity;

import org.json.JSONObject;

import java.util.HashMap;

import id.ac.paramadina.absensi.helper.RequestHelper;

public class PendingClassMeetingDataFetcher extends BaseFetcher {

    public PendingClassMeetingDataFetcher(Activity activity) {
        super(activity);

        HashMap<String, String> additionalParams = new HashMap<String, String>();
        additionalParams.put("verified", "false");

        this.setResourceUrl("/class_meetings", additionalParams);
    }

    @Override
    protected void onPreExecute() {
        this.progress.setTitle("Harap Tunggu");
        this.progress.setMessage("Sedang mengambil data pertemuan kelas.");
        this.progress.show();

        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        RequestHelper request = new RequestHelper(params[0]);
        JSONObject response = request.get(this.getResourceUrl(), this.getRequestQueryStrings());

        return response;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        this.progress.dismiss();

        super.onPostExecute(result);
    }
}
