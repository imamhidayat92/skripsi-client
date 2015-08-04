package id.ac.paramadina.absensi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import id.ac.paramadina.absensi.fetcher.PendingClassMeetingDataFetcher;
import id.ac.paramadina.absensi.reference.adapter.ClassMeetingListAdapter;
import id.ac.paramadina.absensi.reference.model.ClassMeeting;


public class PendingClassMeetingListActivity extends Activity {

    private ListView pendingClassMeetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_class_meeting);

        this.pendingClassMeetings = (ListView) findViewById(R.id.listview_pending_class_meeting);

        getPendingClassMeetingData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pending_class_meeting_list, menu);
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

    private void getPendingClassMeetingData() {
        PendingClassMeetingDataFetcher fetcher = new PendingClassMeetingDataFetcher(this);
        try {
            JSONObject response = fetcher.fetchAndGet();
            if (response.getBoolean("success")) {
                ArrayList<ClassMeeting> listViewData = new ArrayList<ClassMeeting>();
                JSONArray rawClassMeetingData = response.getJSONArray("results");
                for (int i = 0; i < rawClassMeetingData.length(); i++) {
                    JSONObject rawClassMeeting = rawClassMeetingData.getJSONObject(i);
                    ClassMeeting classMeeting = ClassMeeting.createInstance(rawClassMeeting);
                    listViewData.add(classMeeting);
                }

                ClassMeetingListAdapter adapter = new ClassMeetingListAdapter(this, listViewData);
                this.pendingClassMeetings.setAdapter(adapter);

                if (rawClassMeetingData.length() == 0) {
                    Toast.makeText(this, "Tidak ada data.", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this, "Gagal mengambil data.", Toast.LENGTH_LONG).show();
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
