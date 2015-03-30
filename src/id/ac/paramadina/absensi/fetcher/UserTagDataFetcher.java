package id.ac.paramadina.absensi.fetcher;

import id.ac.paramadina.absensi.helper.RequestHelper;
import id.ac.paramadina.absensi.reference.spec.UserTagDataSpec;

import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class UserTagDataFetcher extends BaseFetcher {
	
	private UserTagDataSpec spec;	
	
	public UserTagDataFetcher(Activity activity, UserTagDataSpec spec) {
		super(activity);
		this.spec = spec;
	}

	@Override
	protected void onPreExecute() {
		this.progress.setTitle("Harap Tunggu");
		this.progress.setMessage("Sedang mengambil data identitas pemilik tag..");
		this.progress.show();
		
		super.onPreExecute();
	}
	
	@Override
	protected JSONObject doInBackground(String... params) {
		RequestHelper request = new RequestHelper(params[0]);
		JSONObject response = request.post(params[1], this.getRequestQueryStrings(), this.spec.toHashMap());
		
		if (response == null) {
			Log.d("skripsi-client", "Got null response from server.");
		}
		
		return response;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		this.progress.dismiss();
		
		super.onPostExecute(result);
	}
}
