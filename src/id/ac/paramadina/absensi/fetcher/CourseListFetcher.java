package id.ac.paramadina.absensi.fetcher;

import id.ac.paramadina.absensi.helper.RequestHelper;
import id.ac.paramadina.absensi.reference.adapter.ScheduleAdapter;
import id.ac.paramadina.absensi.reference.model.ClassLocation;
import id.ac.paramadina.absensi.reference.model.Course;
import id.ac.paramadina.absensi.reference.model.Major;
import id.ac.paramadina.absensi.reference.model.Schedule;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CourseListFetcher extends BaseFetcher {	
	
	public CourseListFetcher(Activity activity) {
		super(activity);
		
		this.setResourceUrl("/schedules");
	}
	
	public void fetch() {
		this.execute(this.API_ADDRESS, this.API_RESOURCE_URL);
	}
	
	@Override
	protected void onPreExecute() {
		this.progress.setTitle("Harap Tunggu");
		this.progress.setMessage("Sedang mengambil data mata kuliah..");
		this.progress.show();
		
		super.onPreExecute();
	}
	
	@Override
	protected JSONObject doInBackground(String... params) {
		RequestHelper request = new RequestHelper(params[0]);
		JSONObject response = request.get(params[1], this.getRequestQueryStrings());
		
		if (response == null) {
			Log.d("skripsi-client", "Got null response from server.");
			// TODO: Put a message or do something.
		}
		
		return response;
	}
	
	@Override
	protected void onPostExecute(JSONObject response) {
		this.progress.dismiss();
		
		super.onPostExecute(response);
	}
}
