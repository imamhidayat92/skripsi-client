package id.ac.paramadina.absensi.listener;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.GlobalData;

public class AuthenticationDataListener implements AsyncTaskListener<JSONObject> {

	private Activity activity;
	private Class mainActivityClass;
	
	public AuthenticationDataListener(Activity activity, Class mainActivityClass) {
		this.activity = activity;
		this.mainActivityClass = mainActivityClass;
	}
	
	@Override
	public void onPreExecute() {
		// We don't need to do anything here.
	}

	@Override
	public void onPostExecute(JSONObject response) {
		try {
			if (response.getBoolean("success")) {
				// Login successful.
				SharedPreferences preferences = this.activity.getApplicationContext().getSharedPreferences(GlobalData.PREFERENCE_ID, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				
				JSONObject loginData = response.getJSONObject("result");
				
				editor.putString(GlobalData.PREFERENCE_ACCESS_TOKEN, loginData.getString("token"));
				editor.putString("name", loginData.getString("name"));
				editor.putString("display_name", loginData.getString("display_name"));
				
				editor.commit();
				
				Log.d("skripsi-client", "Login information obtained. Access Token = " + loginData.getString("token"));
				
				Intent i = new Intent(this.activity, mainActivityClass);
				this.activity.startActivity(i);
				this.activity.finish();
			}
			else {
				// Login failed.
				Toast.makeText(this.activity, response.getString("message"), Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// Something bad happened.
			e.printStackTrace();
		}
	}
}
