package id.ac.paramadina.absensi;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.Toast;

public class DiscoverTagActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discover_tag);
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {
			Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
			
			byte[] tagId = tag.getId();
			
			Toast toast = Toast.makeText(this, tagId.toString(), Toast.LENGTH_LONG);
			toast.show();
		}
	}
	
}
