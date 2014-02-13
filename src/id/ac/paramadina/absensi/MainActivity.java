package id.ac.paramadina.absensi;

import id.ac.paramadina.absensi.helper.RequestHelper;
import id.ac.paramadina.absensi.helper.SharedPreferenceHelper;
import id.ac.paramadina.absensi.reference.CourseAdapter;
import id.ac.paramadina.absensi.reference.model.Course;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	/* Controls */
	private ListView courseList;
	
	private CourseAdapter adapter;
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private String[] mMenuItems;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*
         * Initialize Drawer Layout
         * */
    
        mMenuItems = getResources().getStringArray(R.array.drawer_menu2);
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        
        mDrawerToggle = new ActionBarDrawerToggle(
        		this,
        		mDrawerLayout,
        		R.drawable.ic_drawer,
        		R.string.drawer_open,
        		R.string.drawer_close) {
        	
        	@Override
        	public void onDrawerClosed(View drawerView) {
        		super.onDrawerClosed(drawerView);
        		getActionBar().setTitle(getTitle());
        	}
        	
        	@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(getTitle());
			}
        	
        };
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mMenuItems));
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("id.ac.paramadina.absensi.SETTINGS", Context.MODE_PRIVATE);
        
        final String accessToken = preferences.getString("access_token", "?");
        final String lecturerId = preferences.getString("id_lecturer", "?");;
               
        final Activity thisActivity = this;
        
        final ProgressDialog progress = ProgressDialog.show(this, "Mengambil Data Mata Kuliah", "Harap tunggu..", true, false);
        
        /* Load data from server. */
        new Thread(new Runnable() {
			        	
			@Override
			public void run() {
				RequestHelper request = new RequestHelper("http://172.124.103.173/upm/api", ".json");
		        
		        HashMap<String, String> headers = new HashMap<String, String>();
		        headers.put("upm-api-access-token", accessToken);
		        
		        HashMap<String, String> data = new HashMap<String, String>();
		        data.put("lecturer_id", lecturerId);
		        
		        Log.d("Runnable Exception", "accessToken = " + accessToken);
		        Log.d("Runnable Exception", "lecturerId = " + lecturerId);
		        
		        final JSONObject results = request.post("course_lecturers", "index", new String[]{}, data, headers);
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						progress.dismiss();
						
						ArrayList<Course> courses = new ArrayList<Course>();
						
						try {
							if (results.getInt("code") == 0) {
								
								JSONArray response = results.getJSONArray("response");
								
								for (int i = 0; i < response.length(); i++) {
									JSONObject course = response.getJSONObject(i);
									
									courses.add(new Course(
							    		"Nama Prodi", 
							    		"#000000",
							    		course.getString("title"),
							    		String.valueOf(course.getInt("daycode")),
							    		course.getString("start_time"), 
							    		course.getString("end_time"),
							    		"A 2-1"
									));
								}    		        
							}
							else {
								Toast.makeText(getApplicationContext(), results.getString("message"), Toast.LENGTH_LONG).show();
							}
							
							courseList = (ListView) findViewById(R.id.course_list);
					        
					        adapter = new CourseAdapter(thisActivity, courses);
					                
					        courseList.setAdapter(adapter);
					        courseList.setOnItemClickListener(new OnItemClickListener() {
					        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					        		
					        	}
							});
						} catch (JSONException e) {
							e.printStackTrace();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
			}
		}).start();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
    	super.onPostCreate(savedInstanceState);
    	mDrawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
