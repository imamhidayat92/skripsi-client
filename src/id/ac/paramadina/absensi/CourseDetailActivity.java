package id.ac.paramadina.absensi;

import android.app.Activity;
import android.os.Bundle;

public class CourseDetailActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_detail);
		
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
