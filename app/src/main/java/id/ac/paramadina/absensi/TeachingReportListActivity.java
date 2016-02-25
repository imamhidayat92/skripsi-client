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

import id.ac.paramadina.absensi.fetcher.TeachingReportListFetcher;
import id.ac.paramadina.absensi.helper.CommonToastMessage;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.adapter.TeachingReportListAdapter;
import id.ac.paramadina.absensi.reference.model.TeachingReport;

public class TeachingReportListActivity extends BaseActivity {

	private ListView teachingReportListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teaching_report_list);

        this.teachingReportListView = (ListView) findViewById(R.id.teaching_report_listview);

        this.getTeachingReportData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teaching_report_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_refresh:
                this.getTeachingReportData();
                return true;
            default:
        }

		return super.onOptionsItemSelected(item);
	}

    private void getTeachingReportData() {
        TeachingReportListFetcher fetcher = new TeachingReportListFetcher(this);
        fetcher.setListener(new AsyncTaskListener<JSONObject>() {
            @Override
            public void onPreExecute() {
                // Do nothing for this time.
            }

            @Override
            public void onPostExecute(JSONObject result) {
                try {
                    if (result.has("success") && result.getBoolean("success")) {
                        ArrayList<TeachingReport> reports = new ArrayList<TeachingReport>();

                        JSONArray rawReports = result.getJSONArray("results");

                        for (int i = 0; i < rawReports.length(); i++) {
                            TeachingReport report = TeachingReport.createInstance(rawReports.getJSONObject(i));

                            reports.add(report);
                        }

                        TeachingReportListAdapter adapter = new TeachingReportListAdapter(TeachingReportListActivity.this, reports);
                        TeachingReportListActivity.this.teachingReportListView.setAdapter(adapter);
                        TeachingReportListActivity.this.teachingReportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        });
                    }
                } catch (JSONException e) {
                    CommonToastMessage.showErrorGettingDataFromServerMessage(TeachingReportListActivity.this);
                }
            }

            @Override
            public void onError(String message, Object data) {

            }
        });

        fetcher.fetch();
    }
}
