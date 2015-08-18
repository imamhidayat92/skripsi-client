package id.ac.paramadina.absensi.fetcher;

import android.app.Activity;

import org.json.JSONObject;

public class TeachingReportDetailFetcher extends BaseFetcher {

    public TeachingReportDetailFetcher(Activity activity, String teachingReportId) {
        super(activity);

        this.setResourceUrl("/teaching_reports/" + teachingReportId);
    }

    @Override
    protected void onPreExecute() {
        this.progress.setTitle("Harap Tunggu");
        this.progress.setMessage("Sedang mengambil data laporan mengajar..");
        this.progress.show();

        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        this.progress.dismiss();

        super.onPostExecute(result);
    }
}
