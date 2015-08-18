package id.ac.paramadina.absensi.reference.adapter;

import id.ac.paramadina.absensi.R;
import id.ac.paramadina.absensi.reference.model.TeachingReport;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TeachingReportListAdapter extends BaseAdapter {

	Activity activity;
	ArrayList<TeachingReport> data;
	LayoutInflater inflater = null;
	
	public TeachingReportListAdapter(Activity activity, ArrayList<TeachingReport> data) {
		this.activity = activity;
		this.data = data;
		
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return this.data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return this.data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		
		if (arg1 == null) {
			view = inflater.inflate(R.layout.listview_item_teaching_report, null);
		}

        TextView teachingReportCount = (TextView) view.findViewById(R.id.teaching_report_count);
        TextView teachingReportSubject = (TextView) view.findViewById(R.id.teaching_report_subject);

        TeachingReport report = this.data.get(arg0);

        teachingReportCount.setText("Pertemuan ##");
        teachingReportSubject.setText(report.getSubject());
		
		return view;
	}

}
