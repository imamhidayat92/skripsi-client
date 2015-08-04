package id.ac.paramadina.absensi.fetcher;

import org.json.JSONObject;

import android.app.Activity;

import id.ac.paramadina.absensi.helper.RequestHelper;

public class ClassMeetingListFetcher extends BaseFetcher {

	public ClassMeetingListFetcher(Activity activity) {
		super(activity);

        this.setResourceUrl("/class_meetings");
	}
	
	@Override
	protected void onPreExecute() {
		this.progress.setTitle("Harap Tunggu");
		this.progress.setMessage("Sedang mengambil data pertemuan..");
		this.progress.show();
		
		super.onPreExecute();
	}

	@Override
	protected JSONObject doInBackground(String... params) {
        RequestHelper request = new RequestHelper(params[0]);
        JSONObject response = request.get(this.getResourceUrl(), this.getRequestQueryStrings());

		return response;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		this.progress.dismiss();

		super.onPostExecute(result);
	}

}
