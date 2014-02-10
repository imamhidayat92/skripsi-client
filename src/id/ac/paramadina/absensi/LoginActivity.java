package id.ac.paramadina.absensi;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private NfcAdapter mNfcAdapter;
	
	PendingIntent pendingIntent;
	IntentFilter[] filters;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
		if (mNfcAdapter == null || mNfcAdapter.isEnabled() == false) {
            Toast.makeText(this, "Pastikan perangkat Anda mendukung NFC dan telah diaktifkan!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
		
		/* Handle NFC */
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
		filters = new IntentFilter[1];
		
		filters[0] = new IntentFilter();
		filters[0].addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
		filters[0].addCategory(Intent.CATEGORY_DEFAULT);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mNfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, new String[][]{});
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mNfcAdapter.disableForegroundDispatch(this);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		Toast.makeText(this, "Discovered: " + tagFromIntent.getId().toString(), Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onBackPressed() {
		// Disable back button!
	}
}
