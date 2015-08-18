package id.ac.paramadina.absensi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import id.ac.paramadina.absensi.fetcher.TeachingReportDetailFetcher;
import id.ac.paramadina.absensi.helper.CommonToastMessage;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.model.TeachingReport;

public class TeachingReportDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teaching_report_detail);

        String teachingReportId = getIntent().getExtras().getString("teaching_report_id");

        TeachingReportDetailFetcher fetcher = new TeachingReportDetailFetcher(this, teachingReportId);
        fetcher.setListener(new AsyncTaskListener<JSONObject>() {
            @Override
            public void onPreExecute() {
                // Do nothing for this time.
            }

            @Override
            public void onPostExecute(JSONObject result) {
                try {
                    if (result.has("success") && result.getBoolean("success")) {
                        TeachingReport report = TeachingReport.createInstance(result.getJSONObject("result"));
                        TeachingReportDetailActivity.this.setDataToView(report);
                    }
                } catch (JSONException e) {
                    CommonToastMessage.showErrorGettingDataFromServerMessage(TeachingReportDetailActivity.this);
                }
            }
        });
        fetcher.fetch();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teaching_report_detail, menu);
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

    private void setDataToView(TeachingReport report) {

    }
}
