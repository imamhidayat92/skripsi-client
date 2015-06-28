package id.ac.paramadina.absensi.fetcher;

import org.json.JSONObject;

import android.app.Activity;

public class ClassMeetingListFetcher extends BaseFetcher {

	public ClassMeetingListFetcher(Activity activity) {
		super(activity);
	}
	
	@Override
	protected void onPreExecute() {
		this.progress.setTitle("Harap Tunggu");
		this.progress.setMessage("Sedang mengambil data pertemuan..");
		this.progress.show();
		
		super.onPreExecute();
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
