package id.ac.paramadina.absensi;

import org.json.JSONException;
import org.json.JSONObject;

import id.ac.paramadina.absensi.fetcher.ScheduleDetailFetcher;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleDetailActivity extends Activity {
	
	private String courseId;
	private String scheduleId;
	
	/* Controls */
	
	private Button btnRecordAttendance;
	private TextView txtCourseName;
	private TextView txtCourseDescription;
	private TextView txtScheduleDetail;
	private TextView txtScheduleEnrollmentCount;
	
	/* Event Listener */
	
	private OnClickListener btnRecordAttendanceOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(ScheduleDetailActivity.this, DiscoverTagActivity.class);
			i.putExtra("courseId", ScheduleDetailActivity.this.courseId);
			i.putExtra("scheduleId", ScheduleDetailActivity.this.scheduleId);
			
			ScheduleDetailActivity.this.startActivity(i);
		}
	};
	
	private class ScheduleDetailDataListener implements AsyncTaskListener<JSONObject> {

		@Override
		public void onPreExecute() {
			// Do nothing here.
		}

		@Override
		public void onPostExecute(JSONObject response) {
			try {
				if (response.getBoolean("success")) {
					btnRecordAttendance.setOnClickListener(btnRecordAttendanceOnClickListener);
					
					JSONObject scheduleData = response.getJSONObject("result");
					ScheduleDetailActivity.this.updateDetailView(scheduleData);
				}
				else {
					Toast.makeText(ScheduleDetailActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/* Utility Methods */
	
	private void updateDetailView(JSONObject scheduleData) {
		JSONObject courseData;
		try {
			courseData = scheduleData.getJSONObject("course");
			JSONObject locationData = scheduleData.getJSONObject("location");
			
			this.txtCourseName.setText(courseData.getString("name"));
			this.txtCourseDescription.setText(courseData.getString("description"));
			this.txtScheduleDetail.setText(scheduleData.getString("start_time") + " s.d. " + scheduleData.getString("end_time") + " di " + locationData.getString("name"));
			this.txtScheduleEnrollmentCount.setText(String.valueOf(scheduleData.getJSONArray("enrollments").length()));
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
	
	/* Overridden Methods */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_detail);

		this.btnRecordAttendance = (Button) findViewById(R.id.btn_record_attendance);
		this.txtCourseName = (TextView) findViewById(R.id.lbl_course_title);
		this.txtCourseDescription = (TextView) findViewById(R.id.lbl_course_description);
		this.txtScheduleDetail = (TextView) findViewById(R.id.lbl_schedule_detail);
		this.txtScheduleEnrollmentCount = (TextView) findViewById(R.id.lbl_schedule_value_enrollments_count);
		
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    
	    this.courseId = getIntent().getExtras().getString("courseId");
	    this.scheduleId = getIntent().getExtras().getString("scheduleId");
	    
	    ScheduleDetailDataListener listener = new ScheduleDetailDataListener();
	    
	    ScheduleDetailFetcher fetcher = new ScheduleDetailFetcher(this, this.scheduleId);
	    fetcher.setListener(listener);
	    fetcher.fetch();
	}
}
