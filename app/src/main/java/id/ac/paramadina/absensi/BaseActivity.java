package id.ac.paramadina.absensi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import id.ac.paramadina.absensi.reference.Constants;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.d(Constants.LOGGER_TAG, "Uncaught Exception: " + Thread.currentThread().getStackTrace()[2], paramThrowable);
            }
        });
    }
}
