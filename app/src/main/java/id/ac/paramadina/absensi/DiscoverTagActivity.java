package id.ac.paramadina.absensi;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import id.ac.paramadina.absensi.fetcher.UserTagDataFetcher;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.adapter.AttendanceAdapter;
import id.ac.paramadina.absensi.reference.model.Attendance;
import id.ac.paramadina.absensi.reference.model.Major;
import id.ac.paramadina.absensi.reference.model.Student;
import id.ac.paramadina.absensi.reference.model.User;
import id.ac.paramadina.absensi.reference.spec.UserTagDataSpec;
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

    /* General Data */

    private String scheduleId;
    private String classMeetingId;

    /* Activity Data */

	private NfcAdapter mNfcAdapter;
	
	PendingIntent pendingIntent;
	IntentFilter[] filters;
	
	private ArrayList<Attendance> attendanceData;
	private AttendanceAdapter adapter;
	
	/* Controls */
	
	private ListView attendanceListView;
	
	/* Event Listener */

	
	
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
		
		/* Initialization */
		
		this.attendanceListView = (ListView) findViewById(R.id.attendance_list);
		
		this.attendanceData = new ArrayList<Attendance>();
		
		this.adapter = new AttendanceAdapter(this, attendanceData);
		
		attendanceListView.setAdapter(adapter);
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
		
		try {
			UserTagDataSpec spec = new UserTagDataSpec(tagId);
			UserTagDataFetcher fetcher = new UserTagDataFetcher(this, this.classMeetingId, spec);
			JSONObject response = fetcher.fetchAndGet();
			
			if (response.getBoolean("success")) {
                User student = User.createInstance(response);

                Attendance attendance = new Attendance();
                attendance.setStudent(student);
			}
			else {
				Toast.makeText(this, response.getString("message"), Toast.LENGTH_LONG).show();
			}
		} catch (InterruptedException e) {
			Toast.makeText(this, "Gagal mengambil data. Cobalah beberapa saat lagi.", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (ExecutionException e) {
			Toast.makeText(this, "Gagal mengambil data. Cobalah beberapa saat lagi.", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (JSONException e) {
			Toast.makeText(this, "Gagal mengolah data dari server.", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	};
}
