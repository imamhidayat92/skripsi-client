package id.ac.paramadina.absensi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import id.ac.paramadina.absensi.fetcher.ClassMeetingListFetcher;
import id.ac.paramadina.absensi.helper.CommonToastMessage;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.adapter.ClassMeetingListAdapter;
import id.ac.paramadina.absensi.reference.model.ClassMeeting;

public class ClassMeetingListActivity extends Activity {

    private ListView classMeetingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_meeting_list);

        this.classMeetingListView = (ListView) findViewById(R.id.listview_class_meeting);

        this.getClassMeetingData();
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
        ClassMeetingListFetcher fetcher = new ClassMeetingListFetcher(this);

        fetcher.setListener(new AsyncTaskListener<JSONObject>() {
            @Override
            public void onPreExecute() {
                // Do nothing for this time.
            }

            @Override
            public void onPostExecute(JSONObject response) {
                try {
                    if (response.has("success") && response.getBoolean("success")) {
                        JSONArray rawClassMeetingData = response.getJSONArray("results");

                        ArrayList<ClassMeeting> data = new ArrayList<ClassMeeting>();
                        for (int i = 0; i < rawClassMeetingData.length(); i++) {
                            ClassMeeting classMeeting = ClassMeeting.createInstance(rawClassMeetingData.getJSONObject(i));
                            data.add(classMeeting);
                        }

                        ClassMeetingListActivity.this.setDataToView(data);
                    }
                    else {
                        CommonToastMessage.showErrorGettingDataFromServerMessage(ClassMeetingListActivity.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        fetcher.fetch();
    }

    private void setDataToView(ArrayList<ClassMeeting> data) {
        ClassMeetingListAdapter adapter = new ClassMeetingListAdapter(this, data);
        this.classMeetingListView.setAdapter(adapter);
        this.classMeetingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
