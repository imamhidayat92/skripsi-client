package id.ac.paramadina.absensi.reference;

import id.ac.paramadina.absensi.R;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CourseAdapter extends BaseAdapter {

	public static final String KEY_MAJOR_NAME = "major_name";
	public static final String KEY_MAJOR_COLOR = "major_color";
	public static final String KEY_COURSE_TITLE = "course_title";
	public static final String KEY_COURSE_TIME_INFO = "course_time_info";
		
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	
	public CourseAdapter(Activity activity, ArrayList<HashMap<String, String>> data) {
		this.activity = activity;
		this.data = data;
		
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		
		if (arg1 == null) {
			view = inflater.inflate(R.layout.list_course, null);
		}
		
		TextView majorName = (TextView) view.findViewById(R.id.lbl_major_name);
		TextView courseTitle = (TextView) view.findViewById(R.id.lbl_course_title);
		TextView courseTimeInfo = (TextView) view.findViewById(R.id.lbl_course_time_info);
		
		HashMap<String, String> datum = data.get(arg0);
		
		majorName.setText(datum.get(CourseAdapter.KEY_MAJOR_NAME));
		majorName.setBackgroundColor(Color.parseColor(datum.get(CourseAdapter.KEY_MAJOR_COLOR)));
		courseTitle.setText(datum.get(CourseAdapter.KEY_COURSE_TITLE));
		courseTimeInfo.setText(datum.get(CourseAdapter.KEY_COURSE_TIME_INFO));
		
		return view;
	}
	
}
