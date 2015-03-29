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
	
	private ListView listView;
	private OnItemClickListener listener;
	
	public CourseListFetcher(Activity activity, ListView listView) {
		super(activity);
		this.listView = listView;
		
		this.setResourceUrl("/schedules");
	}
	
	public void fetchAndAttachListViewFunction(OnItemClickListener listener) {
		this.listener = listener;
		this.execute(this.API_ADDRESS, this.API_RESOURCE_URL);
	}
	
	@Override
	protected void onPreExecute() {
		this.progress.setTitle("Harap Tunggu");
		this.progress.setMessage("Sedang mengambil data mata kuliah..");
		this.progress.show();
	}
	
	@Override
	protected JSONObject doInBackground(String... params) {
		RequestHelper request = new RequestHelper(params[0]);
		JSONObject response = request.get(params[1], this.getRequestParams());
		
		if (response == null) {
			Log.d("skripsi-client", "Got null response from server.");
			// TODO: Put a message or do something.
		}
		
		return response;
	}
	
	@Override
	protected void onPostExecute(JSONObject response) {
		this.progress.dismiss();
		
		final ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		
		try {
			JSONArray results = response.getJSONArray("results");
			
			for (int i = 0; i < results.length(); i++) {
				JSONObject jsonSchedule = results.getJSONObject(i);
				JSONObject jsonCourse = results.getJSONObject(i).getJSONObject("course");
				JSONObject jsonMajor = results.getJSONObject(i).getJSONObject("course").getJSONObject("major");
				
				Major major = new Major( jsonMajor.getString("name"),
										 jsonMajor.getString("color"));
				
				ClassLocation location = new ClassLocation(	jsonSchedule.getJSONObject("location").getString("name"),
															jsonSchedule.getJSONObject("location").getString("description"));
				
				Course course = new Course(	jsonCourse.getString("_id"),
											jsonCourse.getString("name"), 
											jsonCourse.getString("description"), 
											jsonCourse.getInt("credits"), 
											major);
				
				Schedule schedule = new Schedule( 	jsonSchedule.getString("_id"),
													jsonSchedule.getInt("day_code"),
													jsonSchedule.getString("start_time"),
													jsonSchedule.getString("end_time"),
													course,
													location,
													jsonSchedule.getJSONArray("meetings").length() + 1);
				
				schedules.add(schedule);
			}
			
			if (schedules.size() == 0) {
	        	Toast.makeText(this.activity, "Tidak ada mata kuliah untuk hari ini.", Toast.LENGTH_LONG).show();
	        }
	        
	        ScheduleAdapter adapter = new ScheduleAdapter(this.activity, schedules);
	        
	        this.listView.setAdapter(adapter);
	        this.listView.setOnItemClickListener(this.listener);
			
		}
		catch (JSONException x) {
			// TODO: Show message or do something.
		}
	}
}
