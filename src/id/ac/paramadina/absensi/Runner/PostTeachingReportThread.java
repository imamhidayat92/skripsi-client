package id.ac.paramadina.absensi.Runner;

import org.json.JSONObject;

import id.ac.paramadina.absensi.Helper.RequestHelper;

public class PostTeachingReportThread extends BaseThread {
	
	private final String RESOURCE_URL = "/teaching_reports";
	
	@Override
	public void run() {
		RequestHelper request = new RequestHelper(this.API_ADDRESS);
		JSONObject response = request.post(resourceUrl, params, data, headers);
	}
}
