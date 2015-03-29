package id.ac.paramadina.absensi.fetcher;

import java.util.HashMap;

import id.ac.paramadina.absensi.reference.GlobalData;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

public abstract class BaseFetcher extends AsyncTask<String, Void, JSONObject> {
	protected Activity activity;
	protected ProgressDialog progress;
	protected SharedPreferences preferences;
	
	protected final String API_ACCESS_TOKEN;
	protected final String API_ADDRESS;
	
	protected String API_RESOURCE_URL;
	
	public BaseFetcher(Activity activity) {
		this.activity = activity;
		this.progress = new ProgressDialog(this.activity);
		
		preferences = this.activity.getSharedPreferences(GlobalData.PREFERENCE_ID, Context.MODE_PRIVATE);
	
		this.API_ACCESS_TOKEN = preferences.getString(GlobalData.PREFERENCE_ACCESS_TOKEN, null);
		this.API_ADDRESS = preferences.getString(GlobalData.PREFERENCE_API_ADDRESS, null);
	}
	
	protected final String getResourceUrl() {
		return this.API_RESOURCE_URL;
	}
	
	protected final void setResourceUrl(String resourceUrl) {
		this.API_RESOURCE_URL = resourceUrl;
	}
	
	protected final HashMap<String, String> getRequestParams() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("access_token", this.API_ACCESS_TOKEN);
		return params;
	}
}
