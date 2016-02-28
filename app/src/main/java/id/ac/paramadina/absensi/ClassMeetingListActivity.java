package id.ac.paramadina.absensi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.ac.paramadina.absensi.fetcher.ClassMeetingListFetcher;
import id.ac.paramadina.absensi.helper.CommonDataHelper;
import id.ac.paramadina.absensi.helper.CommonToastMessage;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.adapter.ClassMeetingListAdapter;
import id.ac.paramadina.absensi.reference.model.ClassMeeting;

public class ClassMeetingListActivity extends BaseActivity {

    private ListView classMeetingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_meeting_list);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Daftar Pertemuan Kelas");

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
                if (response == null) {
                    Toast.makeText(ClassMeetingListActivity.this, ClassMeetingListActivity.this.getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        if (CommonDataHelper.isValidResponse(CommonDataHelper.DataResultType.MULTIPLE_RESULTS, response)) {
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
                        Toast.makeText(ClassMeetingListActivity.this, "Gagal mengolah data dari server.", Toast.LENGTH_LONG).show();
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

    private void setDataToView(ArrayList<ClassMeeting> data) {
        ClassMeetingListAdapter adapter = new ClassMeetingListAdapter(this, data);
        this.classMeetingListView.setAdapter(adapter);
        this.classMeetingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ClassMeeting selectedClassMeeting = (ClassMeeting) ClassMeetingListActivity.this.classMeetingListView.getAdapter().getItem(position);

                Intent i = new Intent(ClassMeetingListActivity.this, ClassMeetingDetailActivity.class);
                i.putExtra("classMeetingId", selectedClassMeeting.getId());
                ClassMeetingListActivity.this.startActivity(i);
            }
        });
    }
}
