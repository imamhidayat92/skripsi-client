package id.ac.paramadina.absensi.fetcher;

import id.ac.paramadina.absensi.helper.RequestHelper;
import id.ac.paramadina.absensi.reference.spec.AuthenticationDataSpec;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AuthenticationDataFetcher extends BaseFetcher {

	private Activity destinationActivity;
	private AuthenticationDataSpec spec;
	
	public AuthenticationDataFetcher(Activity activity, AuthenticationDataSpec spec) {
		super(activity);	
		this.spec = spec;
		
		this.setResourceUrl("/users/authentication");
	}
	
	public final void fetchAndStartActivity(Activity destinationActivity) {
		this.destinationActivity = destinationActivity;
		this.execute(this.API_ADDRESS, this.API_RESOURCE_URL);
	}
	
	@Override
	protected void onPreExecute() {
		progress.setTitle("Harap Tunggu");
		progress.setMessage("Sedang melakukan autentikasi..");
		progress.show();
	}
	
	@Override
	protected JSONObject doInBackground(String... params) {
		RequestHelper request = new RequestHelper(params[0]);
		JSONObject response = request.post(params[1], this.getRequestParams(), this.spec.toHashMap());
		
		if (response == null) {
			Log.d("skripsi-client", "Got null response from server.");
		}
		
		return response;
	}
	
	@Override
	protected void onPostExecute(JSONObject response) {
		this.progress.dismiss();
		
		try {
			if (response.getBoolean("success")) {
				// Login successful.
				Intent i = new Intent(this.activity, destinationActivity.getClass());
				this.activity.startActivity(i);
				this.activity.finish();
			}
			else {
				// Login failed.
				Toast.makeText(this.activity, "Gagal. Cek kembali identitas Anda.", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// Something bad happened.
			e.printStackTrace();
		}
	}
}
