package id.ac.paramadina.absensi.fetcher;

import java.util.HashMap;

import id.ac.paramadina.absensi.helper.RequestHelper;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class CourseDetailFetcher extends BaseFetcher {
	
	private HashMap<Integer, String> valueMapping;
	
	public CourseDetailFetcher(Activity activity, String courseId, HashMap<Integer, String> valueMapping) {
		super(activity);
		this.valueMapping = valueMapping;
		
		this.setResourceUrl("/courses/" + courseId); 
	}
	
	public void fetch() {
		this.execute(this.API_ADDRESS, this.API_RESOURCE_URL);
	}
	
	@Override
	protected void onPreExecute() {
		progress.setTitle("Harap Tunggu");
		progress.setMessage("Sedang mengambil data mata kuliah..");
		progress.show();
	}
	
	@Override
	protected JSONObject doInBackground(String... params) {
		RequestHelper request = new RequestHelper(params[0]);
		JSONObject response = request.get(params[1], this.getRequestParams());
		
		if (response == null) {
			Log.d("skripsi-client", "Got null response from server.");
		}
		
		return response;
	}
	
	@Override
	protected void onPostExecute(JSONObject response) {
		try {
			if (response.getBoolean("success")) {
				for(int id : this.valueMapping.keySet()) {
					
				}
			}
			else {
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
