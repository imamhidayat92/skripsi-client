package id.ac.paramadina.absensi.listener;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import id.ac.paramadina.absensi.CourseDetailActivity;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;

public class CourseDetailDataListener implements AsyncTaskListener<JSONObject> {

	private Activity activity;
	
	public CourseDetailDataListener(CourseDetailActivity activity) {
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
