package id.ac.paramadina.absensi.fetcher;

import android.app.Activity;

import org.json.JSONObject;

import id.ac.paramadina.absensi.helper.RequestHelper;
import id.ac.paramadina.absensi.reference.spec.NewTeachingReportDataSpec;

public class NewTeachingReportFetcher extends BaseFetcher {

    private String classMeetingId;
    private NewTeachingReportDataSpec spec;

    public NewTeachingReportFetcher(Activity activity, String classMeetingId, NewTeachingReportDataSpec spec) {
        super(activity);

        this.classMeetingId = classMeetingId;
        this.spec = spec;

        this.setResourceUrl("/class_meetings/" + this.classMeetingId + "/teaching_reports");
    }

    @Override
    protected void onPreExecute() {
        this.progress.setTitle("Harap Tunggu");
        this.progress.setMessage("Sedang membuat data laporan mengajar.");
        this.progress.show();

        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        RequestHelper request = new RequestHelper(params[0]);
        JSONObject response = request.post(this.getResourceUrl(), this.getRequestQueryStrings(), this.spec.toHashMap());

        return response;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        this.progress.dismiss();

        super.onPostExecute(result);
    }
}
