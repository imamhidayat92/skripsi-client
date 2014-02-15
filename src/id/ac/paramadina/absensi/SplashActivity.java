package id.ac.paramadina.absensi;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SplashActivity extends Activity {
	private static final int SPLASH_TIME_OUT = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		/* Preparing NFC Reader */
		
		NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
		if (mNfcAdapter == null || mNfcAdapter.isEnabled() == false) {
            Toast.makeText(this, "Pastikan perangkat Anda mendukung NFC dan telah diaktifkan!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
		
		/* Load Default Value for Settings */
		
		PreferenceManager.setDefaultValues(this, R.xml.activity_settings, true);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent i = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(i);
				
				finish();
			}
		}, SplashActivity.SPLASH_TIME_OUT);
		
	}
}
