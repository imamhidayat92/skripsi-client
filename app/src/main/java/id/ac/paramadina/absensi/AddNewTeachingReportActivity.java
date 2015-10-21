package id.ac.paramadina.absensi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.ac.paramadina.absensi.fetcher.AttendanceDataFetcher;
import id.ac.paramadina.absensi.fetcher.ClassMeetingDataFetcher;
import id.ac.paramadina.absensi.fetcher.NewTeachingReportDataFetcher;
import id.ac.paramadina.absensi.helper.CommonDataHelper;
import id.ac.paramadina.absensi.helper.SmsHelper;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.Constants;
import id.ac.paramadina.absensi.reference.enumeration.AttendanceStatusType;
import id.ac.paramadina.absensi.reference.model.Attendance;
import id.ac.paramadina.absensi.reference.model.ClassMeeting;
import id.ac.paramadina.absensi.reference.model.User;
import id.ac.paramadina.absensi.reference.spec.NewTeachingReportDataSpec;

public class AddNewTeachingReportActivity extends BaseActivity {

    private String classMeetingId;

    private TextView txtCourseTitle;
	private EditText txtSubject;
	private EditText txtDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_teaching_report);
		
		/* Assigning Control */

        txtCourseTitle = (TextView) findViewById(R.id.txt_course_title);
		txtSubject = (EditText) findViewById(R.id.txt_subject);
		txtDescription = (EditText) findViewById(R.id.txt_description);

        this.classMeetingId = getIntent().getExtras().getString("classMeetingId");

        this.getClassMeetingData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_teaching_report, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_send:
				this.sendTeachingReport();
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}

    private void getClassMeetingData() {
        ClassMeetingDataFetcher fetcher = new ClassMeetingDataFetcher(this, this.classMeetingId);

        fetcher.setListener(new AsyncTaskListener<JSONObject>() {
            @Override
            public void onPreExecute() {
                // Do nothing for this time.
            }

            @Override
            public void onPostExecute(JSONObject response) {
                if (response != null) {
                    try {
                        if (CommonDataHelper.isValidResponse(CommonDataHelper.DataResultType.SINGLE_RESULT, response)) {
                            ClassMeeting classMeeting = ClassMeeting.createInstance(response.getJSONObject("result"));
                            AddNewTeachingReportActivity.this.setDataToView(classMeeting);
                        }
                        else {
                            Toast.makeText(AddNewTeachingReportActivity.this, R.string.data_get_error, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(AddNewTeachingReportActivity.this, R.string.data_parse_error, Toast.LENGTH_LONG);
                    }
                }
                else {
                    Toast.makeText(AddNewTeachingReportActivity.this, AddNewTeachingReportActivity.this.getString(R.string.data_get_error), Toast.LENGTH_LONG).show();
                }
            }
        });

        fetcher.fetch();
    }

	private void sendTeachingReport() {
		String subject = this.txtSubject.getText().toString();
		String description = this.txtDescription.getText().toString();

        if (subject.trim().length() == 0) {
            Toast.makeText(AddNewTeachingReportActivity.this, "Topik pengajaran belum diisi.", Toast.LENGTH_LONG).show();
        }
        else if (description.trim().length() == 0) {
            Toast.makeText(AddNewTeachingReportActivity.this, "Ringkasan pengajaran belum diisi.", Toast.LENGTH_LONG).show();
        }
        else {
            NewTeachingReportDataSpec spec = new NewTeachingReportDataSpec(subject, description);
            NewTeachingReportDataFetcher newTeachingReportFetcher = new NewTeachingReportDataFetcher(this, this.classMeetingId, spec);

            final AttendanceDataFetcher attendanceDataFetcher = new AttendanceDataFetcher(this, this.classMeetingId);

            newTeachingReportFetcher.setListener(new AsyncTaskListener<JSONObject>() {
                @Override
                public void onPreExecute() {
                    // Do nothing for this time.
                }

                @Override
                public void onPostExecute(JSONObject response) {
                    if (response == null) {
                        Toast.makeText(AddNewTeachingReportActivity.this.getParent(), AddNewTeachingReportActivity.this.getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
                    }
                    else {
                        try {
                            if (CommonDataHelper.isValidResponse(CommonDataHelper.DataResultType.SINGLE_RESULT, response)) {
                                Toast.makeText(AddNewTeachingReportActivity.this, "Laporan mengajar berhasil disimpan.", Toast.LENGTH_LONG).show();
                                attendanceDataFetcher.fetch();
                            }
                            else {
                                Toast.makeText(AddNewTeachingReportActivity.this, R.string.data_get_error, Toast.LENGTH_LONG).show();
                                Log.d(Constants.LOGGER_TAG, "Error on parsing response from server.");
                            }
                        } catch (JSONException e) {
                            Toast.makeText(AddNewTeachingReportActivity.this, R.string.data_parse_error, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }
            });

            attendanceDataFetcher.setListener(new AsyncTaskListener<JSONObject>() {
                @Override
                public void onPreExecute() {
                    // Do nothing for this time.
                }

                @Override
                public void onPostExecute(JSONObject response) {
                    if (response == null) {
                        Toast.makeText(AddNewTeachingReportActivity.this.getParent(), AddNewTeachingReportActivity.this.getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
                    }
                    else {
                        try {
                            if (CommonDataHelper.isValidResponse(CommonDataHelper.DataResultType.MULTIPLE_RESULTS, response)) {
                                JSONArray rawAttendancesData = response.getJSONArray("results");
                                ArrayList<Attendance> attendances = Attendance.createInstances(rawAttendancesData);

                                SmsHelper smsHelper = new SmsHelper();

                                int smsSent = 0;
                                for (Attendance attendance : attendances) {
                                    if (attendance.getStatus() == AttendanceStatusType.UNKNOWN) {
                                        User user = attendance.getStudent();

                                        // TODO: Need revisions on how to save user contact info data.
                                        String message = "Anak lu nggak masuk ya?";
                                        if (smsHelper.sendMessage(user, message)) {
                                            smsSent++;
                                        }
                                    }
                                }

                                if (smsSent > 0) {
                                    Toast.makeText(AddNewTeachingReportActivity.this, smsSent + " SMS terkirim.", Toast.LENGTH_LONG).show();
                                }

                                AddNewTeachingReportActivity.this.finishOperation();
                            }
                            else {
                                Log.d(Constants.LOGGER_TAG, "Error on parsing response from server.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            newTeachingReportFetcher.fetch();
        }
    }

    private void setDataToView(ClassMeeting classMeeting) {
        txtCourseTitle.setText(classMeeting.getCourse().getName());
    }

    private void finishOperation() {
        // TODO: It's better if after the report has been sent successfully,
        // TODO: the lecturer will get a summary about all processed operations.

        Intent i = new Intent(AddNewTeachingReportActivity.this, MainActivity.class);
        this.startActivity(i);
        this.finish();
    }

}
