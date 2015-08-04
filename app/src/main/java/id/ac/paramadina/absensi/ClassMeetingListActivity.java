package id.ac.paramadina.absensi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import id.ac.paramadina.absensi.fetcher.ClassMeetingListFetcher;
import id.ac.paramadina.absensi.helper.CommonToastMessage;
import id.ac.paramadina.absensi.reference.adapter.ClassMeetingListAdapter;
import id.ac.paramadina.absensi.reference.model.ClassMeeting;


public class ClassMeetingListActivity extends Activity {

    private ListView classMeetingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_meeting_list);

        this.classMeetingListView = (ListView) findViewById(R.id.listview_class_meeting);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_class_meeting_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                this.getClassMeetingData();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getClassMeetingData() {
        try {
            ClassMeetingListFetcher fetcher = new ClassMeetingListFetcher(this);
            JSONObject response = fetcher.fetchAndGet();

            if (response.has("success") && response.getBoolean("success")) {
                JSONArray rawClassMeetingData = response.getJSONArray("results");

                ArrayList<ClassMeeting> data = new ArrayList<ClassMeeting>();
                for (int i = 0; i < rawClassMeetingData.length(); i++) {
                    ClassMeeting classMeeting = ClassMeeting.createInstance(rawClassMeetingData.getJSONObject(i));
                    data.add(classMeeting);
                }

                ClassMeetingListAdapter adapter = new ClassMeetingListAdapter(this, data);
                this.classMeetingListView.setAdapter(adapter);
            }
            else {
                CommonToastMessage.showErrorGettingDataFromServerMessage(this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}