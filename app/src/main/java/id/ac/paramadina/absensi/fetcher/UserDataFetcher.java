package id.ac.paramadina.absensi.fetcher;

import org.json.JSONObject;

import android.app.Activity;

import id.ac.paramadina.absensi.helper.RequestHelper;

public class UserDataFetcher extends BaseFetcher {
	
	public UserDataFetcher(Activity activity, String userId) {
		super(activity);
		
		this.setResourceUrl("/users/" + userId);
	}
	
	@Override
	protected void onPreExecute() {
        this.progress.setTitle("Harap Tunggu");
        this.progress.setMessage("Sedang mengambil data pengguna..");
        this.progress.show();
		
		super.onPreExecute();
	}

	@Override
	protected JSONObject doInBackground(String... params) {
        RequestHelper request = new RequestHelper(this.API_ADDRESS);
        JSONObject response = request.get(this.getResourceUrl(), this.getRequestQueryStrings());

		return response;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
        this.progress.dismiss();

		super.onPostExecute(result);
	}
}
