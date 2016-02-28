package id.ac.paramadina.absensi;

import id.ac.paramadina.absensi.fetcher.AuthenticationDataFetcher;
import id.ac.paramadina.absensi.listener.AuthenticationDataListener;
import id.ac.paramadina.absensi.reference.spec.AuthenticationDataSpec;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {
	
	/* Controls */
	
	private EditText txtUsername;
	private EditText txtPassword;
	private Button btnLogin;
	
	private NfcAdapter mNfcAdapter;
	
	PendingIntent pendingIntent;
	IntentFilter[] filters;
	
	/* Event Listener */
	
	private OnClickListener btnLoginOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String email = txtUsername.getText().toString();
			String password = txtPassword.getText().toString();
			
			AuthenticationDataSpec spec = new AuthenticationDataSpec(email, password);
			
			AuthenticationDataListener taskListener = new AuthenticationDataListener(LoginActivity.this, MainActivity.class);
			
			AuthenticationDataFetcher fetcher = new AuthenticationDataFetcher(LoginActivity.this, spec);
			fetcher.setListener(taskListener);
			fetcher.stripAuthenticationData();
			fetcher.fetch();
		}
	};

	/* Utility Methods */

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

	/* Overridden Methods */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		/* Assigning Control */
		
		txtUsername = (EditText) findViewById(R.id.txt_email);
		txtPassword = (EditText) findViewById(R.id.txt_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
		
		btnLogin.setOnClickListener(btnLoginOnClickListener);
		
		/* Preparing NFC Reader */
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
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
		String tagId = convertBytesToHex(tagFromIntent.getId());
		
		AuthenticationDataSpec spec = new AuthenticationDataSpec(tagId);
		
		AuthenticationDataListener taskListener = new AuthenticationDataListener(this, MainActivity.class);
		AuthenticationDataFetcher fetcher = new AuthenticationDataFetcher(this, spec);
		fetcher.setListener(taskListener);
		fetcher.fetch();
	}
	
	@Override
	public void onBackPressed() {
		// Disable back button!
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.login, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
		}
	}
}
