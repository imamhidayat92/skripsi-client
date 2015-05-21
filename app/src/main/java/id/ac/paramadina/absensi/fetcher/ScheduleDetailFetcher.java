package id.ac.paramadina.absensi.fetcher;

import id.ac.paramadina.absensi.helper.RequestHelper;

import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class ScheduleDetailFetcher extends BaseFetcher {
	
	public ScheduleDetailFetcher(Activity activity, String scheduleId) {
		super(activity);
		
		this.setResourceUrl("/schedules/" + scheduleId); 
	}
	
	@Override
	protected void onPreExecute() {
		progress.setTitle("Harap Tunggu");
		progress.setMessage("Sedang mengambil data mata kuliah..");
		progress.show();
		
		super.onPreExecute();
	}
	
	@Override
	protected JSONObject doInBackground(String... params) {
		RequestHelper request = new RequestHelper(params[0]);
		JSONObject response = request.get(params[1], this.getRequestQueryStrings());
		
		if (response == null) {
			Log.d("skripsi-client", "Got null response from server.");
		}
		
		return response;
	}
	
	@Override
	protected void onPostExecute(JSONObject response) {
		this.progress.dismiss();
		
		if (response != null) {
			super.onPostExecute(response);
		}
		else {
			Toast.makeText(this.activity, "Gagal mengambil data dari server. Coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
		}
	}
}
