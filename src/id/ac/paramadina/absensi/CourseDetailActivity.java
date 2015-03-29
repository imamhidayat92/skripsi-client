package id.ac.paramadina.absensi;

import org.json.JSONException;
import org.json.JSONObject;

import id.ac.paramadina.absensi.fetcher.CourseDetailFetcher;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CourseDetailActivity extends Activity {
	
	/* Controls */
	
	private Button btnRecordAttendance;
	private TextView txtName;
	private TextView txtDescription;
	private TextView txtEnrollmentCount;
	
	/* Event Listener */
	
	private class CourseDetailDataListener implements AsyncTaskListener<JSONObject> {

		@Override
		public void onPreExecute() {
			// Do nothing here.
		}

		@Override
		public void onPostExecute(JSONObject response) {
			try {
				if (response.getBoolean("success")) {
					
				}
				else {
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/* Overridden Methods */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_detail);

		btnRecordAttendance = (Button) findViewById(R.id.btn_record_attendance);
		
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    
	    String courseId = getIntent().getExtras().getString("courseId");
	    
	    
	    CourseDetailFetcher fetcher = new CourseDetailFetcher(this, courseId);
	    fetcher.fetch();
	}
}
