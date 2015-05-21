package id.ac.paramadina.absensi.runner;

import id.ac.paramadina.absensi.ScheduleDetailActivity;
import id.ac.paramadina.absensi.reference.adapter.CourseAdapter;
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
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CourseListRunnable implements Runnable {
		
	private JSONObject response;
	
	private Activity activity;
	private ListView targetListView;
	private ProgressDialog progress;
	
	final private ArrayList<Schedule> schedules;
	
	public CourseListRunnable(	Activity activity, 
								JSONObject response, 
								ListView targetListView, 
								ProgressDialog progress) 
	{
		this.activity = activity;
		this.response = response;
		this.targetListView = targetListView;
		this.progress = progress;
		
		this.schedules = new ArrayList<Schedule>();
		
		try {
			JSONArray results = this.response.getJSONArray("results");
			
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
				
				this.schedules.add(schedule);
			}
		}
		catch (JSONException x) {
			// TODO: Show message or do something.
		}
	}
	
	@Override
	public void run() {
		this.progress.dismiss();
		
		try {
	        if (this.schedules.size() == 0) {
	        	Toast.makeText(this.activity, "Tidak ada mata kuliah untuk hari ini.", Toast.LENGTH_LONG).show();
	        }
	        
	        ScheduleAdapter adapter = new ScheduleAdapter(this.activity, this.schedules);
	        
	        this.targetListView.setAdapter(adapter);
	        this.targetListView.setOnItemClickListener(new OnItemClickListener() {
	        	
	        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        		Intent i = new Intent(activity, ScheduleDetailActivity.class);
	        		i.putExtra("schedule", schedules.get(position).getId());
	        		
	        		activity.startActivity(i);
	        	}
	        	
			});
	        
		} catch (Exception e) {
			// TODO: Show message or do something.
		}
		
	}
}
