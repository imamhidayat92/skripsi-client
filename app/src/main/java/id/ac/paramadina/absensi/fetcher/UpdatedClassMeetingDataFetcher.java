package id.ac.paramadina.absensi.fetcher;

import android.app.Activity;
import android.util.Log;

import org.json.JSONObject;

import id.ac.paramadina.absensi.helper.RequestHelper;
import id.ac.paramadina.absensi.reference.Constants;
import id.ac.paramadina.absensi.reference.spec.UpdateClassMeetingDataSpec;

public class UpdatedClassMeetingDataFetcher extends BaseFetcher {

    UpdateClassMeetingDataSpec spec;

    public UpdatedClassMeetingDataFetcher(Activity activity, String classMeetingId, UpdateClassMeetingDataSpec spec) {
        super(activity);
        this.spec = spec;

        this.setResourceUrl("/class_meetings/" + classMeetingId);
    }

    @Override
    protected void onPreExecute() {
        progress.setTitle("Harap Tunggu");
        progress.setMessage("Sedang memperbarui data pertemuan..");
        progress.show();

        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        RequestHelper request = new RequestHelper(params[0]);
        JSONObject response = request.put(params[1], this.getRequestQueryStrings(), this.spec.toHashMap());

        if (response == null) {
            Log.d(Constants.LOGGER_TAG, "Got null response from server.");
        }

        return response;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        this.progress.dismiss();

        super.onPostExecute(result);
    }
}
