package id.ac.paramadina.absensi.fetcher;

import android.app.Activity;
import android.util.Log;

import org.json.JSONObject;

import id.ac.paramadina.absensi.helper.RequestHelper;
import id.ac.paramadina.absensi.reference.spec.NewClassMeetingDataSpec;

public class NewClassMeetingDataFetcher extends BaseFetcher {

    NewClassMeetingDataSpec spec;

    public NewClassMeetingDataFetcher(Activity activity, NewClassMeetingDataSpec spec) {
        super(activity);
        this.spec = spec;

        this.setResourceUrl("/class_meetings");
    }

    @Override
    protected void onPreExecute() {
        this.progress.setTitle("Harap Tunggu");
        this.progress.setMessage("Sedang membuat data kelas pertemuan baru.");
        this.progress.show();

        super.onPreExecute();

    }

    @Override
    protected JSONObject doInBackground(String... params) {
        RequestHelper request = new RequestHelper(params[0]);
        JSONObject response = request.post(this.getResourceUrl(), this.getRequestQueryStrings(), this.spec.toHashMap());

        if (response == null) {
            Log.d("skripsi-client", "Got null response from server.");
        }

        return response;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        this.progress.dismiss();

        super.onPostExecute(result);
    }
}
