package id.ac.paramadina.absensi.listener;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.adapter.ScheduleListAdapter;
import id.ac.paramadina.absensi.reference.model.ClassLocation;
import id.ac.paramadina.absensi.reference.model.Course;
import id.ac.paramadina.absensi.reference.model.Major;
import id.ac.paramadina.absensi.reference.model.Schedule;

public class ScheduleListDataListener extends BaseListener {

	private Activity activity;
	private ListView listView;
	private OnItemClickListener listener;
	
	public ScheduleListDataListener(Activity activity, ListView listView) {
		this.activity = activity;
		this.listView = listView;
	}
	
	public void setListViewOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void onPreExecute() {
		// Do nothing here.
	}

	@Override
	public void onPostExecute(JSONObject response) {
		final ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		
		try {
			JSONArray results = response.getJSONArray("results");
			
			for (int i = 0; i < results.length(); i++) {
				JSONObject jsonSchedule = results.getJSONObject(i);
				Schedule schedule = Schedule.createInstance(jsonSchedule);
				schedules.add(schedule);
			}
			
			if (schedules.size() == 0) {
	        	Toast.makeText(this.activity, "Tidak ada mata kuliah untuk hari ini.", Toast.LENGTH_LONG).show();
	        }
	        
	        ScheduleListAdapter adapter = new ScheduleListAdapter(this.activity, schedules);
	        
	        this.listView.setAdapter(adapter);
	        this.listView.setOnItemClickListener(this.listener);
		}
		catch (JSONException x) {
            Toast.makeText(this.activity, "Gagal mengolah data dari server.", Toast.LENGTH_LONG).show();
			x.printStackTrace();
		}
	}
}
