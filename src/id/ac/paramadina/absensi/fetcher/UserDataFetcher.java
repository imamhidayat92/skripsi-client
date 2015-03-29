package id.ac.paramadina.absensi.fetcher;

import org.json.JSONObject;

import android.app.Activity;

public class UserDataFetcher extends BaseFetcher {
	
	public UserDataFetcher(Activity activity, String studentId) {
		super(activity);
		
		this.setResourceUrl("/users/" + studentId);
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
