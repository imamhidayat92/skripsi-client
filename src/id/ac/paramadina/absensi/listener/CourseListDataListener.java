package id.ac.paramadina.absensi.listener;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.adapter.ScheduleAdapter;
import id.ac.paramadina.absensi.reference.model.ClassLocation;
import id.ac.paramadina.absensi.reference.model.Course;
import id.ac.paramadina.absensi.reference.model.Major;
import id.ac.paramadina.absensi.reference.model.Schedule;

public class CourseListDataListener implements AsyncTaskListener<JSONObject> {

	private Activity activity;
	private ListView listView;
	private OnItemClickListener listener;
	
	public CourseListDataListener(Activity activity, ListView listView) {
		this.activity = activity;
		this.listView = listView;
	}
	
	public void setListViewOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void onPreExecute() {
		// Do nothing here.
	}

	@Override
	public void onPostExecute(JSONObject response) {
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
			x.printStackTrace();
		}
	}
}
