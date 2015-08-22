package id.ac.paramadina.absensi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import id.ac.paramadina.absensi.fetcher.ClassMeetingDataFetcher;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.model.ClassMeeting;


public class ClassMeetingDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_meeting_detail);

        String classMeetingId = getIntent().getExtras().getString("classMeetingId");

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
            public void onPostExecute(JSONObject result) {
                try {
                    ClassMeeting classMeeting = ClassMeeting.createInstance(result);

                    ClassMeetingDetailActivity.this.setDataToView(classMeeting);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        fetcher.fetch();
    }

    private void setDataToView(ClassMeeting data) {

    }
}
