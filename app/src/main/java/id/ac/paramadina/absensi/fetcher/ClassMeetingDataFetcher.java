package id.ac.paramadina.absensi.fetcher;

import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import id.ac.paramadina.absensi.helper.RequestHelper;

public class ClassMeetingDataFetcher extends BaseFetcher {

    public String classMeetingId;

	public ClassMeetingDataFetcher(Activity activity, String classMeetingId) {
		super(activity);

        this.classMeetingId = classMeetingId;

        this.setResourceUrl("/class_meetings/" + this.classMeetingId);
	}
	
	@Override
	protected void onPreExecute() {
        this.progress.setTitle("Harap Tunggu");
        this.progress.setMessage("Sedang mengambil data pertemuan kelas..");
        this.progress.show();

        super.onPreExecute();
	}

	@Override
	protected JSONObject doInBackground(String... params) {
        RequestHelper request = new RequestHelper(params[0]);
        JSONObject response = request.get(params[1], this.getRequestQueryStrings());

        if (response == null) {
            Log.d("skripsi-client", "Got null response from server.");
        }

        return response;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
        this.progress.dismiss();
	}
}
