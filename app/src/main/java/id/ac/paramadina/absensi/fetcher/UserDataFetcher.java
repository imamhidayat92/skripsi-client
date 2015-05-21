package id.ac.paramadina.absensi.fetcher;

import org.json.JSONObject;

import android.app.Activity;

public class UserDataFetcher extends BaseFetcher {
	
	public UserDataFetcher(Activity activity, String studentId) {
		super(activity);
		
		this.setResourceUrl("/users/" + studentId);
	}
	
	public void fetch() {
		
	}
	
	@Override
	protected void onPreExecute() {
		progress.setTitle("Harap Tunggu");
		progress.setMessage("Sedang mengambil data pengguna..");
		progress.show();
		
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
