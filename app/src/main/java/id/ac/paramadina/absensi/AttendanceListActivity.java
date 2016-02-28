package id.ac.paramadina.absensi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.ac.paramadina.absensi.fetcher.AttendanceDataFetcher;
import id.ac.paramadina.absensi.helper.CommonDataHelper;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.Constants;
import id.ac.paramadina.absensi.reference.adapter.ClassMeetingAttendanceListAdapter;
import id.ac.paramadina.absensi.reference.model.Attendance;


public class AttendanceListActivity extends Activity {

    private String classMeetingId;

    private ClassMeetingAttendanceListAdapter adapter;
    private ArrayList<Attendance> attendanceData = new ArrayList<Attendance>();

    private ListView attendanceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        this.adapter = new ClassMeetingAttendanceListAdapter(this, this.attendanceData);

        this.attendanceListView = (ListView) findViewById(R.id.listview_class_meeting_attendance);
        this.attendanceListView.setAdapter(this.adapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Daftar Kehadiran");

        this.classMeetingId = getIntent().getExtras().getString("classMeetingId");

        this.getAttendanceData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attendance_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                this.getAttendanceData();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAttendanceData() {
        AttendanceDataFetcher fetcher = new AttendanceDataFetcher(this, this.classMeetingId);

        fetcher.setListener(new AsyncTaskListener<JSONObject>() {
            @Override
            public void onPreExecute() {
                // Do nothing for this time.
            }

            @Override
            public void onPostExecute(JSONObject response) {
                if (response == null) {
                    Toast.makeText(AttendanceListActivity.this, AttendanceListActivity.this.getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        if (CommonDataHelper.isValidResponse(CommonDataHelper.DataResultType.MULTIPLE_RESULTS, response)) {
                            JSONArray rawAttendancesData = response.getJSONArray("results");
                            ArrayList<Attendance> attendances = new ArrayList<Attendance>();
                            for (int i = 0; i < rawAttendancesData.length(); i++) {
                                Attendance attendance = Attendance.createInstance(rawAttendancesData.getJSONObject(i));
                                attendances.add(attendance);
                            }
                            AttendanceListActivity.this.adapter.reset();
                            AttendanceListActivity.this.adapter.pushNewEntries(attendances);
                        }
                        else {
                            Toast.makeText(AttendanceListActivity.this, AttendanceListActivity.this.getString(R.string.data_get_error), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Log.d(Constants.LOGGER_TAG, e.getMessage());
                    }
                }
            }

            @Override
            public void onError(String message, Object data) {

            }
        });

        fetcher.fetch();
    }
}
