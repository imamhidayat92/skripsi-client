package id.ac.paramadina.absensi.fetcher;

import id.ac.paramadina.absensi.R;
import id.ac.paramadina.absensi.helper.RequestHelper;
import id.ac.paramadina.absensi.reference.spec.NewAttendanceDataSpec;

import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class NewAttendanceDataFetcher extends BaseFetcher {

    private String classMeetingId;
	private NewAttendanceDataSpec spec;
	
	public NewAttendanceDataFetcher(Activity activity, String classMeetingId, NewAttendanceDataSpec spec) {
		super(activity);
		this.spec = spec;
        this.classMeetingId = classMeetingId;
		
		this.setResourceUrl("/class_meetings/" + this.classMeetingId + "/attendances");
	}

	@Override
	protected void onPreExecute() {
		this.progress.setTitle(R.string.loading_message);
		this.progress.setMessage(this.activity.getString(R.string.data_get_message));
		this.progress.show();
		
		super.onPreExecute();
	}
	
	@Override
	protected JSONObject doInBackground(String... params) {
		RequestHelper request = new RequestHelper(params[0]);
		JSONObject response = request.post(params[1], this.getRequestQueryStrings(), this.spec.toHashMap());
		
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
