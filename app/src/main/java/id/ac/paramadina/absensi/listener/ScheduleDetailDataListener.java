package id.ac.paramadina.absensi.listener;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import id.ac.paramadina.absensi.ScheduleDetailActivity;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;

public class ScheduleDetailDataListener implements AsyncTaskListener<JSONObject> {

	private Activity activity;
	
	public ScheduleDetailDataListener(ScheduleDetailActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onPreExecute() {
		// Do nothing.
	}

	@Override
	public void onPostExecute(JSONObject response) {
		try {
			if (response.getBoolean("success")) {
				
			}
			else {
				
			}
		} catch (JSONException e) {
			// Something bad happened.
			e.printStackTrace();
		}
 	}
	
}
