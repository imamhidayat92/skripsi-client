package id.ac.paramadina.absensi.Runner;

import id.ac.paramadina.absensi.CourseDetailActivity;
import id.ac.paramadina.absensi.reference.Model.Course;
import id.ac.paramadina.absensi.reference.Model.Major;
import id.ac.paramadina.absensi.reference.ModelAdapter.CourseAdapter;

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
	
	public CourseListRunnable(Activity activity, JSONObject response, ListView targetListView, ProgressDialog progress) {
		this.activity = activity;
		this.response = response;
		this.targetListView = targetListView;
		this.progress = progress;
	}
	
	@Override
	public void run() {
		this.progress.dismiss();
		
		ArrayList<Course> courses = new ArrayList<Course>();
		
		try {
			if (this.response.getInt("code") == 0) {
				
				JSONArray responseData = this.response.getJSONArray("response");
				
				for (int i = 0; i < responseData.length(); i++) {
					JSONObject course = responseData.getJSONObject(i);
					
					JSONArray majors = course.getJSONArray("majors");
					
					ArrayList<Major> majorArray = new ArrayList<Major>(); 
					for (int j = 0; j < majors.length(); j++) {
						majorArray.add(new Major(majors.getJSONObject(j).getString("name"), majors.getJSONObject(j).getString("color")));
					}
					
					courses.add(new Course(
						course.getInt("id_course_lecturer"),
						majorArray,
			    		course.getString("title"),
			    		"Pertemuan ke-?",
			    		course.getString("start_time"), 
			    		course.getString("end_time"),
			    		"A 2-1"
					));
					
					Log.d("id.ac.paramadina.absensi", course.toString());
				}    		        
			}
			else {
				Toast.makeText(this.activity, this.response.getString("message"), Toast.LENGTH_LONG).show();
			}
			
	        CourseAdapter adapter = new CourseAdapter(this.activity, courses);
	                
	        /* Clumsy 'forward' like declaration. -_- */
	        final ArrayList<Course> _courses = courses;
	        
	        if (courses.size() == 0) {
	        	Toast.makeText(this.activity, "Tidak ada mata kuliah untuk hari ini.", Toast.LENGTH_LONG).show();
	        }
	        
	        this.targetListView.setAdapter(adapter);
	        this.targetListView.setOnItemClickListener(new OnItemClickListener() {
	        	
	        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        		Intent i = new Intent(activity, CourseDetailActivity.class);
	        		i.putExtra("course_lecturer_id", _courses.get(position).getId());
	        		
	        		activity.startActivity(i);
	        	}
	        	
			});
	        
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
