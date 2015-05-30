package id.ac.paramadina.absensi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import id.ac.paramadina.absensi.fetcher.NewTeachingReportFetcher;
import id.ac.paramadina.absensi.reference.spec.TeachingReportDataSpec;

public class AddNewTeachingReportActivity extends Activity {

    private String classMeetingId;

	private EditText txtSubject;
	private EditText txtDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_lecturer_report);
		
		/* Assigning Control */
		
		txtSubject = (EditText) findViewById(R.id.txt_subject);
		txtDescription = (EditText) findViewById(R.id.txt_description);
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
	
	private void sendTeachingReport() {
		String subject = this.txtSubject.getText().toString();
		String description = this.txtDescription.getText().toString();

        TeachingReportDataSpec spec = new TeachingReportDataSpec(subject, description);

        NewTeachingReportFetcher fetcher = new NewTeachingReportFetcher(this, this.classMeetingId, spec);

        try {
            JSONObject response = fetcher.fetchAndGet();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
