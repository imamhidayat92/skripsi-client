package id.ac.paramadina.absensi;

import id.ac.paramadina.absensi.helper.RequestHelper;

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
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
					
//					final StringBuilder rawData = new StringBuilder();
//					
//					try {
//						URL url = new URL("http://192.168.88.28/upm/api/users/authenticate.json");
//						
//						HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//						
//						urlConnection.setDoOutput(true);
//						
//						OutputStream outputStream = urlConnection.getOutputStream();
//						BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//						
//						writer.write("email=" + URLEncoder.encode(email, "UTF-8") + "&password=" + password);
//						writer.flush();
//						writer.close();
//						
//						outputStream.close();
//						
//						urlConnection.connect();
//						
//						InputStream inputStream = urlConnection.getInputStream();
//						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//						
//						String line = null;
//						while ((line = reader.readLine()) != null) {
//							rawData.append(line);
//						}
//						
//					} catch (MalformedURLException e1) {
//						Log.d("Exception", e1.getMessage());
//						e1.printStackTrace();
//					} catch (IOException e) {
//						Log.d("Exception", e.getMessage());
//						e.printStackTrace();
//					}						
					
					RequestHelper request = new RequestHelper("http://192.168.88.28/upm/api", ".json");
					
					HashMap<String, String> data = new HashMap<String, String>();
					data.put("email", email);
					data.put("password", password);
					
					final JSONObject result = request.post("users", "authenticate", new String[]{}, data);
					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							progress.dismiss();
							
							try {
								if (result.getInt("code") == 0) {
									Intent i = new Intent(getApplicationContext(), MainActivity.class);
									startActivity(i);
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
