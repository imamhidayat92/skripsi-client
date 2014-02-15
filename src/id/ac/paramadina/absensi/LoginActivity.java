package id.ac.paramadina.absensi;

import id.ac.paramadina.absensi.helper.RequestHelper;
import id.ac.paramadina.absensi.helper.SharedPreferenceHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
		
		txtUsername = (EditText) findViewById(R.id.txt_email);
		txtPassword = (EditText) findViewById(R.id.txt_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String email = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();
				
				login(email, password);
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
	
	private void login(final String email, final String password) {
		final ProgressDialog progress = ProgressDialog.show(this, "Melakukan Autentikasi", "Harap tunggu..", true, false);
		
		new Thread(
			new Runnable() {
				
				@Override
				public void run() {
					SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					String address = preference.getString("settings_api_address", "?");
					
					RequestHelper request = new RequestHelper(address, ".json");
					
					HashMap<String, String> data = new HashMap<String, String>();
					data.put("email", email);
					data.put("password", password);
					
					final JSONObject result = request.post("users", "authenticate", new String[]{}, data, new HashMap<String, String>());
					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							progress.dismiss();
							
							try {
								if (result.getInt("code") == 0) {
									initializeSettings(result.getJSONObject("response"));
									
									Intent i = new Intent(getApplicationContext(), MainActivity.class);
									startActivity(i);
									
									finish();
								}
								else {
									Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
							}
														
						}
					});
					
				}
			}
		).start();		
	}
	
	private void login(final String tagId) {
		final ProgressDialog progress = ProgressDialog.show(this, "Melakukan Autentikasi", "Harap tunggu..", true, false);
		
		new Thread(
			new Runnable() {
				
				@Override
				public void run() {
					SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					String address = preference.getString("settings_api_address", "?");
					
					RequestHelper request = new RequestHelper(address, ".json");
					
					HashMap<String, String> data = new HashMap<String, String>();
					data.put("identification_number", tagId);
					
					final JSONObject result = request.post("users", "authenticate", new String[]{}, data, new HashMap<String, String>());
					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							progress.dismiss();
							
							try {
								if (result.getInt("code") == 0) {
									initializeSettings(result.getJSONObject("response"));
									
									Intent i = new Intent(getApplicationContext(), MainActivity.class);
									startActivity(i);
									
									finish();
								}
								else {
									Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
							}
														
						}
					});
					
				}
			}
		).start();
	}
	
	private void initializeSettings(JSONObject loginData) {
		try {
			SharedPreferences preferences = getApplicationContext().getSharedPreferences("id.ac.paramadina.absensi.SETTINGS", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			
			editor.putString("id_user", loginData.getString("id_user"));
			editor.putString("id_lecturer", loginData.getString("id_lecturer"));
			editor.putString("access_token", loginData.getString("access_token"));
			editor.putString("name", loginData.getString("name"));
			
			editor.commit();
		} catch (JSONException e) {
			Log.d("LoginActivity initializeSettings JSONException", e.getMessage());
			e.printStackTrace();
		} 
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
		
		login(tagId);
	}
	
	@Override
	public void onBackPressed() {
		// Disable back button!
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		
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
