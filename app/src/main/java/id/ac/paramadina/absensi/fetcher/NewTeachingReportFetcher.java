package id.ac.paramadina.absensi.fetcher;

import android.app.Activity;

import org.json.JSONObject;

import id.ac.paramadina.absensi.reference.spec.TeachingReportDataSpec;

public class NewTeachingReportFetcher extends BaseFetcher {

    private String classMeetingId;
    private TeachingReportDataSpec spec;

    public NewTeachingReportFetcher(Activity activity, String classMeetingId, TeachingReportDataSpec spec) {
        super(activity);

        this.classMeetingId = classMeetingId;
        this.spec = spec;

        this.setResourceUrl("/class_meetings/" + this.classMeetingId + "/teaching_reports");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }
}
