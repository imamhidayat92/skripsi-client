package id.ac.paramadina.absensi.Runner;

import id.ac.paramadina.absensi.Helper.RequestHelper;

import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ListView;

public class CourseListThread extends Thread {
	private String API_ADDRESS;
	
	private final String CONTROLLER_NAME = "course_lecturers";
	private final String ACTION_NAME = "index";
	
	private Activity activity;
	private ProgressDialog progress;
	private ListView targetListView;	
	
	private HashMap<String, String> headers;
	private HashMap<String, String> data;
	
	public CourseListThread(Activity activity, ListView targetListView, ProgressDialog progress, HashMap<String, String> headers, HashMap<String, String> data) {
		this.activity = activity;
		this.progress = progress;
		this.targetListView = targetListView;
		this.headers = headers;
		this.data = data;
	}
	
	public void setApiAddress(String url) {
		this.API_ADDRESS = url;
	}
	
	@Override
	public void run() {
		RequestHelper request = new RequestHelper(this.API_ADDRESS, ".json");
		JSONObject results = request.post(CONTROLLER_NAME, ACTION_NAME, new String[]{}, data, headers);
		
		CourseListRunnable runnable = new CourseListRunnable(this.activity, results, this.targetListView, progress);
		
		this.activity.runOnUiThread(runnable);
	}
}
