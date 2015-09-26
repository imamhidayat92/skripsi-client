package id.ac.paramadina.absensi.fetcher;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import id.ac.paramadina.absensi.helper.RequestHelper;
import id.ac.paramadina.absensi.reference.Constants;

public class AttendanceDataFetcher extends BaseFetcher {

    public AttendanceDataFetcher(Activity activity, String classMeetingId) {
        super(activity);

        this.setResourceUrl("/class_meetings/" + classMeetingId + "/attendances");
    }

    @Override
    protected void onPreExecute() {
        progress.setTitle("Harap Tunggu");
        progress.setMessage("Sedang mengambil data kehadiran..");
        progress.show();

        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        RequestHelper request = new RequestHelper(params[0]);
        JSONObject response = request.get(params[1], this.getRequestQueryStrings());

        if (response == null) {
            Log.d(Constants.LOGGER_TAG, "Got null response from server.");
        }

        return response;
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        this.progress.dismiss();

        if (response != null) {
            super.onPostExecute(response);
        }
        else {
            Toast.makeText(this.activity, "Gagal mengambil data dari server. Cek pengaturan aplikasi.", Toast.LENGTH_LONG).show();
        }
    }
}
