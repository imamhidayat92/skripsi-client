package id.ac.paramadina.absensi.fetcher;

import id.ac.paramadina.absensi.helper.RequestHelper;

import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class LecturerReportListFetcher extends BaseFetcher {

	public LecturerReportListFetcher(Activity activity) {
		super(activity);
		
		this.setResourceUrl("/lecturer_reports");
	}

	@Override
	protected void onPreExecute() {
		
		
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
		
		super.onPostExecute(result);
	}

}
