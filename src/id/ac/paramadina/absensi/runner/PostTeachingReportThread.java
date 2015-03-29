package id.ac.paramadina.absensi.runner;

import org.json.JSONObject;

import id.ac.paramadina.absensi.helper.RequestHelper;

public class PostTeachingReportThread extends BaseThread {
	
	private final String RESOURCE_URL = "/teaching_reports";
	
	@Override
	public void run() {
		RequestHelper request = new RequestHelper(this.API_ADDRESS);
		// JSONObject response = request.post(RESOURCE_URL, params, data, headers);
	}
}
