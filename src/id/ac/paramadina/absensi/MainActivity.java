package id.ac.paramadina.absensi;

import id.ac.paramadina.absensi.Runner.CourseListThread;
import id.ac.paramadina.absensi.reference.Model.DrawerMenuItem;
import id.ac.paramadina.absensi.reference.ModelAdapter.DrawerListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.ListView;

public class MainActivity extends Activity {

	/* Controls */
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private ArrayList<DrawerMenuItem> mMenuItems;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
        
        /* Initialize Drawer Layout */
    
        mMenuItems = new ArrayList<DrawerMenuItem>();
        
        mMenuItems.add(new DrawerMenuItem("Test 1"));
        mMenuItems.add(new DrawerMenuItem("Test 2"));
        mMenuItems.add(new DrawerMenuItem("Test 3"));
        
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

        mDrawerListView.setAdapter(new DrawerListViewAdapter(this, mMenuItems));
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        this.getCourseList();        
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

        switch (item.getItemId()) {
        	case R.id.action_refresh:
        		this.getCourseList();
        		return true;
        	default:
        		return super.onOptionsItemSelected(item);
        }
        
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /* Operations */
    
    private void getCourseList() {
    	/* Initialize and Load Settings */
        
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("id.ac.paramadina.absensi.SETTINGS", Context.MODE_PRIVATE);
        
        String accessToken = preferences.getString("access_token", "?");
        String lecturerId = preferences.getString("id_lecturer", "?");;
               
        ProgressDialog progress = ProgressDialog.show(this, "Mengambil Data Mata Kuliah", "Harap tunggu..", true, false);
        
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        
        
        /* Load data from server. */
		
        String address = preference.getString("settings_api_address", "?");
		
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("upm-api-access-token", accessToken);
        
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("lecturer_id", lecturerId);
        
        CourseListThread thread = new CourseListThread(this, (ListView) findViewById(R.id.course_list), progress, headers, data);
        thread.setApiAddress(address);
        thread.start();
    }
}
