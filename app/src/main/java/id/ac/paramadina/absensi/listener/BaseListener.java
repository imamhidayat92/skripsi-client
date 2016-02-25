package id.ac.paramadina.absensi.listener;

import org.json.JSONObject;

import id.ac.paramadina.absensi.reference.AsyncTaskListener;

public class BaseListener implements AsyncTaskListener<JSONObject> {
    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(JSONObject result) {

    }

    @Override
    public void onError(String message, Object data) {

    }
}
