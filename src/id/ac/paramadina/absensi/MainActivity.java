package id.ac.paramadina.absensi;

import id.ac.paramadina.absensi.fetcher.CourseListFetcher;
import id.ac.paramadina.absensi.listener.CourseListDataListener;
import id.ac.paramadina.absensi.reference.adapter.DrawerListViewAdapter;
import id.ac.paramadina.absensi.reference.model.Course;
import id.ac.paramadina.absensi.reference.model.DrawerMenuItem;
import id.ac.paramadina.absensi.runner.CourseListThread;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

	/* Controls */
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private ArrayList<DrawerMenuItem> mMenuItems;
	
	private ListView scheduleListView;
	
	/* Navigation Drawer Menu Item Click Handler */
	
	private class DrawerItemClickListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			/* 
			 * Menu from Top to Bottom
			 * 1. Kuliah Hari Ini
			 * 2. Kuliah Pengganti
			 * 3. Laporan Mengajar
			 * 4. Keluar
			 */
			
			switch (position) {
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				default:
					break;
			}
		}
	}
	
	private class CourseItemClickListener implements AdapterView.OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Course selectedCourse = (Course) MainActivity.this.scheduleListView.getAdapter().getItem(position);
			
			Intent i = new Intent(MainActivity.this, CourseDetailActivity.class);
    		i.putExtra("courseId", selectedCourse.getId());
    		
    		MainActivity.this.startActivity(i);
		}
		
	}

	/* Main Methods */
	
	private void fetchCourseList() {
		this.scheduleListView = (ListView) findViewById(R.id.listview_schedule);
		
		CourseListDataListener listener = new CourseListDataListener(this, this.scheduleListView);
		listener.setListViewOnItemClickListener(new CourseItemClickListener());
		
		CourseListFetcher fetcher = new CourseListFetcher(this);
		fetcher.setListener(listener);
		fetcher.fetch();
	}
	
	/* Overridden Methods */
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
        /* Initialize Drawer Layout */
    
        mMenuItems = new ArrayList<DrawerMenuItem>();
        
        mMenuItems.add(new DrawerMenuItem("Kuliah Hari Ini"));
        mMenuItems.add(new DrawerMenuItem("Arsip Laporan Mengajar"));
        mMenuItems.add(new DrawerMenuItem("Keluar"));
        
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
        mDrawerListView.setOnItemClickListener(new DrawerItemClickListener());        
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Kelas Hari Ini");
        
        this.fetchCourseList();
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
        		this.fetchCourseList();
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
}
