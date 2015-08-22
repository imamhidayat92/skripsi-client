package id.ac.paramadina.absensi;

import id.ac.paramadina.absensi.fetcher.ScheduleListFetcher;
import id.ac.paramadina.absensi.listener.ScheduleListDataListener;
import id.ac.paramadina.absensi.reference.adapter.DrawerListViewAdapter;
import id.ac.paramadina.absensi.reference.model.DrawerMenuItem;
import id.ac.paramadina.absensi.reference.model.Schedule;
import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
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
			 * 1. Jadwal Hari Ini
			 * 2. Pertemuan yang Tertunda
			 * 3. Pertemuan Kelas
			 */
            Intent i;
			
			switch (position) {
				case 0:
                    i = new Intent(MainActivity.this, MainActivity.class);
                    MainActivity.this.startActivity(i);
                    break;
				case 1:
                    i = new Intent(MainActivity.this, PendingClassMeetingListActivity.class);
                    MainActivity.this.startActivity(i);                    break;
				case 2:
                    i = new Intent(MainActivity.this, ClassMeetingListActivity.class);
                    MainActivity.this.startActivity(i);
					break;
			}
		}
	}
	
	private class ScheduleItemClickListener implements AdapterView.OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Schedule selectedSchedule = (Schedule) MainActivity.this.scheduleListView.getAdapter().getItem(position);
			
			Intent i = new Intent(MainActivity.this, ScheduleDetailActivity.class);
    		i.putExtra("courseId", selectedSchedule.getCourse().getId());
    		i.putExtra("scheduleId", selectedSchedule.getId());
    		
    		MainActivity.this.startActivity(i);
		}
		
	}

	/* Main Methods */
	
	private void fetchCourseList() {
		this.scheduleListView = (ListView) findViewById(R.id.listview_schedule);
		
		ScheduleListDataListener listener = new ScheduleListDataListener(this, this.scheduleListView);
		listener.setListViewOnItemClickListener(new ScheduleItemClickListener());
		
		ScheduleListFetcher fetcher = new ScheduleListFetcher(this);
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
        mMenuItems.add(new DrawerMenuItem("Arsip Data Pertemuan"));
        
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
