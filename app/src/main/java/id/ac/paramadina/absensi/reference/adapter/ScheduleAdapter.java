package id.ac.paramadina.absensi.reference.adapter;

import id.ac.paramadina.absensi.R;
import id.ac.paramadina.absensi.reference.model.Schedule;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScheduleAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<Schedule> data;
	private static LayoutInflater inflater = null;
	
	public ScheduleAdapter(Activity activity, ArrayList<Schedule> data) {
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
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		
		if (arg1 == null) {
			view = inflater.inflate(R.layout.listview_item_schedule_big, null);
		}
		
		TextView scheduleCourseName = (TextView) view.findViewById(R.id.lbl_schedule_course_name);
		TextView scheduleDetail = (TextView) view.findViewById(R.id.lbl_schedule_detail);
		TextView scheduleInfo = (TextView) view.findViewById(R.id.lbl_schedule_info);
		
		Schedule datum = data.get(arg0);		
		
		scheduleCourseName.setText(datum.getCourse().getName());
		scheduleCourseName.setBackgroundColor(Color.parseColor(datum.getCourse().getMajor().getColor()));
		scheduleDetail.setText("Pertemuan ke-" + datum.getMeetingCount());
		scheduleInfo.setText(datum.getInfo());
		
		return view;
	}
}
