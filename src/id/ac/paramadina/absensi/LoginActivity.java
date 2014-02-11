package id.ac.paramadina.absensi;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	/* Controls */
	private EditText txtUsername;
	private EditText txtPassword;
	private Button btnLogin;
	
	private NfcAdapter mNfcAdapter;
	
	PendingIntent pendingIntent;
	IntentFilter[] filters;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		/* Assigning Control */
		
		txtUsername = (EditText) findViewById(R.id.txt_username);
		txtPassword = (EditText) findViewById(R.id.txt_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String username = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();
				
				Toast.makeText(getApplicationContext(), "Try to login with " + username + " and " + password, Toast.LENGTH_LONG).show();
				
				login(username, password);
			}
		});
		
		/* Preparing NFC Reader */
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
		/* Handle NFC */
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
		filters = new IntentFilter[1];
		
		filters[0] = new IntentFilter();
		filters[0].addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
		filters[0].addCategory(Intent.CATEGORY_DEFAULT);
	}
	
	private void login(String username, String password) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
	
	private void login(String tagId) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
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
	
	final protected static char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	public static String convertBytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		String tagId = convertBytesToHex(tagFromIntent.getId());
		Toast.makeText(this, "Discovered: " + tagId, Toast.LENGTH_LONG).show();
		
		login(tagId);
	}
	
	@Override
	public void onBackPressed() {
		// Disable back button!
	}
}
