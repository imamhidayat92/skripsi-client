package id.ac.paramadina.absensi;

import org.json.JSONException;
import org.json.JSONObject;

import id.ac.paramadina.absensi.fetcher.NewClassMeetingDataFetcher;
import id.ac.paramadina.absensi.fetcher.ScheduleDetailFetcher;
import id.ac.paramadina.absensi.helper.CommonDataHelper;
import id.ac.paramadina.absensi.helper.CommonToastMessage;
import id.ac.paramadina.absensi.listener.BaseListener;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.Constants;
import id.ac.paramadina.absensi.reference.enumeration.ClassMeetingType;
import id.ac.paramadina.absensi.reference.model.ClassMeeting;
import id.ac.paramadina.absensi.reference.spec.NewClassMeetingDataSpec;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleDetailActivity extends BaseActivity {
	
	private String courseId;
	private String scheduleId;

    private NewClassMeetingDataSpec spec;

	/* Controls */
	
	private Button btnRecordAttendance;
    private Spinner spnrClassMeetingType;
	private TextView txtCourseName;
	private TextView txtCourseDescription;
	private TextView txtScheduleDetail;
	private TextView txtScheduleEnrollmentCount;
	
	/* Event Listener */
	
	private OnClickListener btnRecordAttendanceOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
            NewClassMeetingDataFetcher fetcher = new NewClassMeetingDataFetcher(ScheduleDetailActivity.this, ScheduleDetailActivity.this.spec);

            fetcher.setListener(new AsyncTaskListener<JSONObject>() {
                @Override
                public void onPreExecute() {
                    // Do nothing for this time.
                }

                @Override
                public void onPostExecute(JSONObject response) {
                    if (response == null) {
                        Toast.makeText(ScheduleDetailActivity.this.getParent(), ScheduleDetailActivity.this.getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
                    }
                    else {
                        try {
                            if (CommonDataHelper.isValidResponse(CommonDataHelper.DataResultType.SINGLE_RESULT, response)) {
                                JSONObject classMeetingRawData = response.getJSONObject("result");
                                ClassMeeting classMeeting = ClassMeeting.createInstance(classMeetingRawData);

                                Intent i = new Intent(ScheduleDetailActivity.this, DiscoverTagActivity.class);
                                i.putExtra("courseId", ScheduleDetailActivity.this.courseId);
                                i.putExtra("scheduleId", ScheduleDetailActivity.this.scheduleId);
                                i.putExtra("classMeetingId", classMeeting.getId());
                                i.putExtra("status", spnrClassMeetingType.getSelectedItem().toString());

                                ScheduleDetailActivity.this.startActivity(i);
                            }
                            else {
                                Toast.makeText(ScheduleDetailActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException e) {
                            Log.d(Constants.LOGGER_TAG, "Error (JSONException): " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onError(String message, Object data) {

                }
            });

            fetcher.fetch();
        }
	};
	
	private class ScheduleDetailDataListener extends BaseListener {

		@Override
		public void onPreExecute() {
			// Do nothing here.
		}

		@Override
		public void onPostExecute(JSONObject response) {
			if (response == null) {
                Toast.makeText(ScheduleDetailActivity.this, ScheduleDetailActivity.this.getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
            }
            else {
                try {
                    if (response.has("success") && response.has("result") && response.getBoolean("success")) {
                        JSONObject scheduleData = response.getJSONObject("result");
                        ScheduleDetailActivity.this.updateDetailView(scheduleData);
                    }
                    else {
                        Toast.makeText(ScheduleDetailActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                    }
                }
                catch (JSONException e) {
                    Toast.makeText(ScheduleDetailActivity.this, ScheduleDetailActivity.this.getString(R.string.data_parse_error), Toast.LENGTH_LONG).show();
                    Log.d(Constants.LOGGER_TAG, "JSONException: " + e.getMessage());
                }
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
        btnRecordAttendance.setOnClickListener(btnRecordAttendanceOnClickListener);
        this.spnrClassMeetingType = (Spinner) findViewById(R.id.class_type_spinner);
        String[] classMeetingTypes = new String[] {
            "Harian", "Ujian Tengah Semester", "Ujian Akhir Semester"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classMeetingTypes);
		spnrClassMeetingType.setAdapter(adapter);

        this.txtCourseName = (TextView) findViewById(R.id.lbl_course_title);
		this.txtCourseDescription = (TextView) findViewById(R.id.lbl_course_description);
		this.txtScheduleDetail = (TextView) findViewById(R.id.lbl_schedule_detail);
		this.txtScheduleEnrollmentCount = (TextView) findViewById(R.id.lbl_schedule_value_enrollments_count);
		
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    
	    this.courseId = getIntent().getExtras().getString("courseId");
	    this.scheduleId = getIntent().getExtras().getString("scheduleId");

        this.spec = new NewClassMeetingDataSpec(ClassMeetingType.DEFAULT, this.courseId, this.scheduleId);

        ScheduleDetailDataListener listener = new ScheduleDetailDataListener();
	    
	    ScheduleDetailFetcher fetcher = new ScheduleDetailFetcher(this, this.scheduleId);
	    fetcher.setListener(listener);
	    fetcher.fetch();
	}
}
