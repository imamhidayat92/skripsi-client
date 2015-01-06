package id.ac.paramadina.absensi.Runner;

import id.ac.paramadina.absensi.Helper.RequestHelper;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ListView;

public class CourseListThread extends Thread {
	private String API_ADDRESS;
	
	private final String RESOURCE_URL = "/schedules";
	
	private Activity activity;
	private ProgressDialog progress;
	private ListView targetListView;	
	
	private HashMap<String, String> params;
	
	public CourseListThread(Activity activity, ListView targetListView, ProgressDialog progress, HashMap<String, String> params) {
		this.activity = activity;
		this.progress = progress;
		this.targetListView = targetListView;
		
		this.params = params;
		
		Log.d("skripsi-client", "CourseListThread initialized.");
	}
	
	public void setApiAddress(String url) {
		this.API_ADDRESS = url;
	}
	
	@Override
	public void run() {
		RequestHelper request = new RequestHelper(this.API_ADDRESS);
		JSONObject response = request.get(this.RESOURCE_URL, params, new HashMap<String, String>());
		
		if (response == null) {
			Log.d("skripsi-client", "Got null response from server.");
			// TODO: Put a message or do something.
		}
		else {
			try {
				if (!response.getBoolean("success")) {
					Log.d("skripsi-client", "Unsuccessful request.");
					// TODO: Put a message or do something.
				}
				else {
					CourseListRunnable runnable = new CourseListRunnable(this.activity, response, this.targetListView, progress);
					
					this.activity.runOnUiThread(runnable); 
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
