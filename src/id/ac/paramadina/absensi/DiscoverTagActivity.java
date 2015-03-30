package id.ac.paramadina.absensi;

import org.json.JSONException;
import org.json.JSONObject;

import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.model.Major;
import id.ac.paramadina.absensi.reference.model.Student;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class DiscoverTagActivity extends Activity {
	
	private NfcAdapter mNfcAdapter;
	
	PendingIntent pendingIntent;
	IntentFilter[] filters;
	
	/* Controls */
	
	private ListView studentsListView;
	
	/* Event Listener */
	
	private class UserTagDataListener implements AsyncTaskListener<JSONObject> {

		@Override
		public void onPreExecute() {
			// Do nothing here.
		}

		@Override
		public void onPostExecute(JSONObject response) {
			try {
				if (response.getBoolean("success")) {
					JSONObject studentData = response.getJSONObject("result");
					JSONObject majorData = studentData.getJSONObject("major");
					
					Student student = new Student(
							studentData.getString("_id"), 
							studentData.getString("display_name"), 
							new Major(majorData.getString("name"), majorData.getString("color")));
				}
				else {
					
				}
			} catch (JSONException e) {
				// Something bad happened.
				e.printStackTrace();
			}
		}
		
	}
	
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
		setContentView(R.layout.activity_discover_tag);
		
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
		
		Log.d("skripsi-client", "New tag discovered: " + tagId);
		
		Toast toast = Toast.makeText(this, tagId.toString(), Toast.LENGTH_LONG);
		toast.show();
	};
}
