package id.ac.paramadina.absensi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import id.ac.paramadina.absensi.fetcher.ClassMeetingDataFetcher;
import id.ac.paramadina.absensi.helper.CommonDataHelper;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.model.ClassMeeting;


public class ClassMeetingDetailActivity extends BaseActivity {

    private String classMeetingId;

    private TextView classMeetingCourseTitle;
    private TextView classMeetingInfo;
    private TextView teachingReportSubject;
    private TextView teachingReportSummary;

    private Button viewAttendanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_meeting_detail);

        this.classMeetingCourseTitle = (TextView) findViewById(R.id.txt_course_title);
        this.classMeetingInfo = (TextView) findViewById(R.id.txt_class_meeting_info);
        this.teachingReportSubject = (TextView) findViewById(R.id.txt_teaching_report_subject);
        this.teachingReportSummary = (TextView) findViewById(R.id.txt_teaching_report_summary);
        this.viewAttendanceList = (Button) findViewById(R.id.btn_view_attendance_list);

        this.classMeetingId = getIntent().getExtras().getString("classMeetingId");

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Detail Pertemuan Kelas");

        this.getClassMeetingDetail(classMeetingId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_class_meeting_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void getClassMeetingDetail(String classMeetingId) {
        ClassMeetingDataFetcher fetcher = new ClassMeetingDataFetcher(this, classMeetingId);
        fetcher.setListener(new AsyncTaskListener<JSONObject>() {
            @Override
            public void onPreExecute() {
                // Do nothing for this time.
            }

            @Override
            public void onPostExecute(JSONObject response) {
                if (response == null) {
                    Toast.makeText(ClassMeetingDetailActivity.this, ClassMeetingDetailActivity.this.getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        if (CommonDataHelper.isValidResponse(CommonDataHelper.DataResultType.SINGLE_RESULT, response)) {
                            ClassMeeting classMeeting = ClassMeeting.createInstance(response.getJSONObject("result"));

                            ClassMeetingDetailActivity.this.setDataToView(classMeeting);
                        }
                        else {
                            Toast.makeText(ClassMeetingDetailActivity.this, "Data dari server tidak valid.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(ClassMeetingDetailActivity.this, "Tidak dapat mengolah data dari server.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String message, Object data) {

            }
        });
        fetcher.fetch();
    }

    private void setDataToView(ClassMeeting data) {
        this.classMeetingCourseTitle.setText(data.getCourse().getName());
        this.classMeetingInfo.setText("Pertemuan Ke-1");
        this.teachingReportSubject.setText(data.getReport().getSubject());
        this.teachingReportSummary.setText(data.getReport().getDescription());

        viewAttendanceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ClassMeetingDetailActivity.this, AttendanceListActivity.class);
                i.putExtra("classMeetingId", ClassMeetingDetailActivity.this.classMeetingId);

                ClassMeetingDetailActivity.this.startActivity(i);
            }
        });
    }
}
