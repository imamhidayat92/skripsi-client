package id.ac.paramadina.absensi.runner;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;

public class BaseThread extends Thread {
	protected String API_ADDRESS;
	
	protected Activity activity;
	protected ProgressDialog progress;
	
	protected HashMap<String, String> params;
	
	public BaseThread() {} // Empty constructor.
	
	public BaseThread(Activity activity, ProgressDialog progress, HashMap<String, String> params) {
		this.activity = activity;
		this.progress = progress;
		this.params = params;
	}
	
	public void setApiAddress(String url) {
		this.API_ADDRESS = url;
	}
}
