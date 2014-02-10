package id.ac.paramadina.absensi;

import id.ac.paramadina.absensi.reference.CourseAdapter;
import id.ac.paramadina.absensi.reference.model.Course;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        
        
        /* TODO: Load course data from Web API. */
        
        ArrayList<Course> courses = new ArrayList<Course>();
        
        // Fill in the list with dummy data.
                
        courses.add(new Course(
    		"Teknik Informatika",
    		"#FFD400",
    		"Algoritma & Pemrograman I",
    		"Selasa",
    		"09:45",
    		"12:15",
    		"A 2-1"
		));    
        
        courses.add(new Course(
    		"Manajemen & Bisnis",
    		"#00ADEF",
    		"Manajemen Keuangan",
    		"Senin",
    		"09:45",
    		"12:15",
    		"A 1-10"
		));
        
        courses.add(new Course(
    		"Hubungan Internasional",
    		"#F9344C",
    		"Studi Kawasan Asia Tenggara",
    		"Senin",
    		"09:45",
    		"12:15",
    		"A 2-2"
		));
        
        courseList = (ListView) findViewById(R.id.course_list);
        
        adapter = new CourseAdapter(this, courses);
                
        courseList.setAdapter(adapter);
        courseList.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		
        	}
		});
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
