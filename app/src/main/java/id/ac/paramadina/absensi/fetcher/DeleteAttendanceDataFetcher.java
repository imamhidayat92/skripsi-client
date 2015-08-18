package id.ac.paramadina.absensi.fetcher;

import android.app.Activity;

import org.json.JSONObject;

import id.ac.paramadina.absensi.helper.RequestHelper;

public class DeleteAttendanceDataFetcher extends BaseFetcher {

    public DeleteAttendanceDataFetcher(Activity activity, String attendanceId) {
        super(activity);

        this.setResourceUrl("/attendances/" + attendanceId);
    }

    @Override
    protected void onPreExecute() {
        this.progress.setTitle("Harap Tunggu");
        this.progress.setMessage("Sedang menghapus data pertemuan..");
        this.progress.show();

        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        RequestHelper request = new RequestHelper(params[0]);
        JSONObject response = request.delete(params[1], this.getRequestQueryStrings());

        return response;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        this.progress.dismiss();

        super.onPostExecute(result);
    }
}
