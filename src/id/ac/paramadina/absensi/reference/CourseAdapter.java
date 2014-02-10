package id.ac.paramadina.absensi.reference;

import id.ac.paramadina.absensi.R;
import id.ac.paramadina.absensi.reference.model.Course;

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
	
	private Activity activity;
	private ArrayList<Course> data;
	private static LayoutInflater inflater = null;
	
	public CourseAdapter(Activity activity, ArrayList<Course> data) {
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
		
		Course datum = data.get(arg0);
		
		majorName.setText(datum.getMajorName());
		majorName.setBackgroundColor(Color.parseColor(datum.getMajorColor()));
		courseTitle.setText(datum.getCourseTitle());
		courseTimeInfo.setText(datum.getCourseTime());
		
		return view;
	}
	
}
