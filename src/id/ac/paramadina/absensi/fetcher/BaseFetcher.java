package id.ac.paramadina.absensi.fetcher;

import java.util.HashMap;

import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.GlobalData;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

public abstract class BaseFetcher extends AsyncTask<String, Void, JSONObject> {
	
	protected boolean needAuthenticationData = true;
	
	protected Activity activity;
	protected ProgressDialog progress;
	protected SharedPreferences preferences;
	protected SharedPreferences userPreferences;
	
	protected String API_ACCESS_TOKEN;
	protected String API_ADDRESS;
	protected String API_RESOURCE_URL;
	
	private AsyncTaskListener<JSONObject> listener = null;
	
	public BaseFetcher(Activity activity) {
		this.activity = activity;
		this.progress = new ProgressDialog(this.activity);

		this.preferences = PreferenceManager.getDefaultSharedPreferences(this.activity.getApplicationContext());
		this.userPreferences = this.activity.getSharedPreferences(GlobalData.PREFERENCE_ID, Context.MODE_PRIVATE);
		
		this.API_ACCESS_TOKEN = userPreferences.getString(GlobalData.PREFERENCE_ACCESS_TOKEN, null);
		this.API_ADDRESS = preferences.getString(GlobalData.PREFERENCE_API_ADDRESS, null);
		
		Log.d("skripsi-client", "Initializing fetcher. Access Token = " + this.API_ACCESS_TOKEN +
				                ", API Address = " + this.API_ADDRESS);
	}
	
	public BaseFetcher(Activity activity, AsyncTaskListener<JSONObject> listener) {
		this(activity);
		this.listener = listener;
	}
	
	public final void stripAuthenticationData() {
		this.needAuthenticationData = false;
	}
	
	protected final String getResourceUrl() {
		return this.API_RESOURCE_URL;
	}
	
	protected final void setResourceUrl(String resourceUrl) {
		this.API_RESOURCE_URL = resourceUrl;
	}
	
	protected final HashMap<String, String> getRequestParams() {
		HashMap<String, String> params = new HashMap<String, String>();
		if (this.needAuthenticationData) {
			params.put("access_token", this.API_ACCESS_TOKEN);
		}
		return params;
	}
	
	public final void setListener(AsyncTaskListener<JSONObject> listener) {
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		if (listener != null) {
			listener.onPreExecute();
		}
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		if (listener != null) {
			listener.onPostExecute(result);
		}
	}
}
