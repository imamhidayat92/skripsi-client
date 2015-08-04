package id.ac.paramadina.absensi.reference.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import id.ac.paramadina.absensi.R;
import id.ac.paramadina.absensi.reference.model.ClassMeeting;

public class ClassMeetingListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<ClassMeeting> data;

    private static LayoutInflater inflater;

    public ClassMeetingListAdapter(Activity activity, ArrayList<ClassMeeting> data) {
        this.activity = activity;
        this.data = data;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View argView, ViewGroup viewGroup) {
        View view = argView;

        if (view == null) {
            view = inflater.inflate(R.layout.listview_item_schedule, null);
        }

        return view;
    }
}
