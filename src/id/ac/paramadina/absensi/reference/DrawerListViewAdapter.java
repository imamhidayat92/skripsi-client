package id.ac.paramadina.absensi.reference;

import id.ac.paramadina.absensi.R;
import id.ac.paramadina.absensi.reference.model.Course;
import id.ac.paramadina.absensi.reference.model.DrawerMenuItem;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class DrawerListViewAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<DrawerMenuItem> data;
	private static LayoutInflater inflater = null;
	
	public DrawerListViewAdapter(Activity activity, ArrayList<DrawerMenuItem> data) {
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
	public int getItemViewType(int position) {
		return super.getItemViewType(position);
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		
		if (arg1 == null) {
			view = inflater.inflate(R.layout.list_drawer_menu_item, null);
		}
		
		TextView menuTitle = (TextView) view.findViewById(R.id.menu_title);
		
		DrawerMenuItem datum = data.get(arg0);
		
		menuTitle.setText(datum.getName());
		
		return view;
	}
	
}
