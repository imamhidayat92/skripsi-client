package id.ac.paramadina.absensi.reference.ModelAdapter;

import id.ac.paramadina.absensi.R;
import id.ac.paramadina.absensi.reference.Model.Course;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
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
			view = inflater.inflate(R.layout.list_item_course, null);
		}
		
		RelativeLayout majors = (RelativeLayout) view.findViewById(R.id.majors);
		
		TextView courseTitle = (TextView) view.findViewById(R.id.lbl_course_title);
		TextView courseSubTitle = (TextView) view.findViewById(R.id.lbl_course_subtitle);
		TextView courseTimeInfo = (TextView) view.findViewById(R.id.lbl_course_time_info);
		
		Course datum = data.get(arg0);
		
		for (int i = 0; i < datum.getNumberOfMajors(); i++) {
			RelativeLayout.LayoutParams majorTextViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			TextView major = new TextView(view.getContext());  
			
			major.setText(datum.getMajorName(i));
			major.setBackgroundColor(Color.parseColor(datum.getMajorColor(i)));
			major.setPadding(5, 5, 5, 5);
			
			if (i == 0) {
				majorTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				majorTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			}
			else {
				
			}
			
			majors.addView(major, majorTextViewParams);
		}
		
		courseTitle.setText(datum.getCourseTitle());
		courseSubTitle.setText(datum.getCourseSubTitle());
		courseTimeInfo.setText(datum.getCourseInfo());
		
		return view;
	}
	
}
