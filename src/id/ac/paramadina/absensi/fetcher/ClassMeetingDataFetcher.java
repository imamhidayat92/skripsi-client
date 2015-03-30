package id.ac.paramadina.absensi.fetcher;

import org.json.JSONObject;

import android.app.Activity;

public class ClassMeetingDataFetcher extends BaseFetcher {
	public ClassMeetingDataFetcher(Activity activity) {
		super(activity);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
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
