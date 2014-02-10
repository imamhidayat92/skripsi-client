package id.ac.paramadina.absensi;

import id.ac.paramadina.absensi.reference.CourseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	/* Controls */
	private ListView courseList;
	
	private CourseAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /* TODO: Load course data from Web API. */
        
        ArrayList<HashMap<String, String>> courses = new ArrayList<HashMap<String,String>>();
        
        // Fill in the list with dummy data.
        HashMap<String, String> dummy1 = new HashMap<String, String>();
        dummy1.put("major_name", "Teknik Informatika");
        dummy1.put("major_color", "#FFD400");
        dummy1.put("course_title", "Algoritma & Pemrograman 1");
        dummy1.put("course_time_info", "Senin, 09.00 - 11.45 di A 1-10");
        
        HashMap<String, String> dummy2 = new HashMap<String, String>();
        
        dummy2.put("major_name", "Hubungan Internasional");
        dummy2.put("major_color", "#ED1C24");
        dummy2.put("course_title", "Diplomasi Asia Tenggara");
        dummy2.put("course_time_info", "Selasa, 09.00 - 11.45 di A 1-10");
        
        HashMap<String, String> dummy3 = new HashMap<String, String>();
        
        dummy3.put("major_name", "Manajemen dan Bisnis");
        dummy3.put("major_color", "#00ADEF");
        dummy3.put("course_title", "Ekonomi Pasar Bebas");
        dummy3.put("course_time_info", "Rabu, 09.00 - 11.45 di A 1-10");
        
        courses.add(dummy1);
        courses.add(dummy2);
        courses.add(dummy3);
        courses.add(dummy1);
        courses.add(dummy2);
        courses.add(dummy3);
        
        
        courseList = (ListView) findViewById(R.id.course_list);
        
        adapter = new CourseAdapter(this, courses);
                
        courseList.setAdapter(adapter);
        courseList.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		
        	}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
