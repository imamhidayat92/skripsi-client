package id.ac.paramadina.absensi.reference.adapter;

import id.ac.paramadina.absensi.R;
import id.ac.paramadina.absensi.reference.GlobalData;
import id.ac.paramadina.absensi.reference.model.Attendance;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AttendanceListAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<Attendance> data;
	private static LayoutInflater inflater = null;

    private final String API_ADDRESS;

	public AttendanceListAdapter(Activity activity, ArrayList<Attendance> data) {
		this.activity = activity;
		this.data = data;
		
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.activity.getApplicationContext());
        this.API_ADDRESS = preferences.getString(GlobalData.PREFERENCE_API_ADDRESS, null);
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
			view = inflater.inflate(R.layout.listview_item_attendance, null);
		}
		
		TextView attendanceUserName = (TextView) view.findViewById(R.id.lbl_attendance_user_name);
		TextView attendanceUserIdNumber = (TextView) view.findViewById(R.id.lbl_attendance_user_idnumber);
		TextView attendanceCreated = (TextView) view.findViewById(R.id.lbl_attendance_created);
		ImageView displayPicture = (ImageView) view.findViewById(R.id.img_avatar);

		Attendance datum = data.get(arg0);		
		
		attendanceUserName.setText(datum.getStudent().getName());
		attendanceUserIdNumber.setText(datum.getStudent().getIdNumber());
		attendanceCreated.setText(datum.getCreated().getTime().toString());

        if (datum.getStudent().getDisplayPicture() != null) {
            Glide.with(this.activity)
                .load(this.API_ADDRESS + datum.getStudent().getDisplayPicture())
                .centerCrop()
                .crossFade()
                .into(displayPicture);
        }
        else {
            Glide.with(this.activity)
                .load(R.drawable.ic_launcher)
                .centerCrop()
                .crossFade()
                .into(displayPicture);
        }

		return view;
	}

}
