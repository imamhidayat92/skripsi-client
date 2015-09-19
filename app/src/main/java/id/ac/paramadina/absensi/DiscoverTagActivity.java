package id.ac.paramadina.absensi;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import id.ac.paramadina.absensi.fetcher.ClassMeetingDataFetcher;
import id.ac.paramadina.absensi.fetcher.DeleteAttendanceDataFetcher;
import id.ac.paramadina.absensi.fetcher.NewAttendanceDataFetcher;
import id.ac.paramadina.absensi.fetcher.UserDataFetcher;
import id.ac.paramadina.absensi.helper.CommonToastMessage;
import id.ac.paramadina.absensi.reference.AsyncTaskListener;
import id.ac.paramadina.absensi.reference.dialog.SimplePromptDialog;
import id.ac.paramadina.absensi.reference.Constants;
import id.ac.paramadina.absensi.reference.adapter.AttendanceListAdapter;
import id.ac.paramadina.absensi.reference.dialog.UserDetailDialog;
import id.ac.paramadina.absensi.reference.enumeration.UserIdentificationField;
import id.ac.paramadina.absensi.reference.model.Attendance;
import id.ac.paramadina.absensi.reference.model.ClassMeeting;
import id.ac.paramadina.absensi.reference.model.User;
import id.ac.paramadina.absensi.reference.spec.NewAttendanceDataSpec;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DiscoverTagActivity extends BaseActivity {

    /* General Data */

    private String classMeetingId;
    private String courseId;
    private String scheduleId;

    /* Activity Data */

	private NfcAdapter mNfcAdapter;
	
	PendingIntent pendingIntent;
	IntentFilter[] filters;
	
	private ArrayList<Attendance> attendanceData;
	private AttendanceListAdapter adapter;
	
	/* Controls */

    private TextView courseTitle;

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

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
		filters = new IntentFilter[1];
		
		filters[0] = new IntentFilter();
		filters[0].addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
		filters[0].addCategory(Intent.CATEGORY_DEFAULT);
		
		/* Initialization */

        this.classMeetingId = getIntent().getExtras().getString("classMeetingId");
        this.courseId = getIntent().getExtras().getString("courseId");
        this.scheduleId = getIntent().getExtras().getString("scheduleId");

        this.courseTitle = (TextView) findViewById(R.id.course_title);
		this.attendanceListView = (ListView) findViewById(R.id.attendance_list);
		
		this.attendanceData = new ArrayList<Attendance>();
		
		this.adapter = new AttendanceListAdapter(this, attendanceData);

        this.attendanceListView.setAdapter(adapter);
        this.attendanceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Attendance attendance = (Attendance) adapterView.getItemAtPosition(i);

                String studentId = attendance.getStudent().getId();
                UserDataFetcher fetcher = new UserDataFetcher(DiscoverTagActivity.this, studentId);

                fetcher.setListener(new AsyncTaskListener<JSONObject>() {
                    @Override
                    public void onPreExecute() {
                        // Do nothing for this time.
                    }

                    @Override
                    public void onPostExecute(JSONObject response) {
                        try {
                            if (response != null) {
                                if (response.has("success") && response.getBoolean("success")) {
                                    User user = User.createInstance(response.getJSONObject("result"));

                                    UserDetailDialog dialog = new UserDetailDialog().setUser(user);
                                    dialog.setListener(new UserDetailDialog.UserDetailDialogListener() {
                                        @Override
                                        public void onCloseButtonClick(DialogFragment dialog) {
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void onRemoveButtonClick(DialogFragment dialog) {
                                            String attendanceId = attendance.getId();
                                            DeleteAttendanceDataFetcher fetcher = new DeleteAttendanceDataFetcher(DiscoverTagActivity.this, attendanceId);

                                            fetcher.setListener(new AsyncTaskListener<JSONObject>() {
                                                @Override
                                                public void onPreExecute() {
                                                    // Do nothing for this time.
                                                }

                                                @Override
                                                public void onPostExecute(JSONObject response) {
                                                    try {
                                                        if (response.has("success") && response.getBoolean("success")) {
                                                            JSONObject rawAttendanceData = response.getJSONObject("result");
                                                            Attendance attendance = Attendance.createInstance(rawAttendanceData);

                                                            Toast.makeText(DiscoverTagActivity.this, "Data kehadiran untuk " + attendance.getStudent().getDisplayName() + " berhasil dihapus.", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                    catch (JSONException e) {
                                                        CommonToastMessage.showErrorGettingDataFromServerMessage(DiscoverTagActivity.this);
                                                    }
                                                }
                                            });

                                            fetcher.fetch();
                                        }
                                    });
                                } else {
                                    CommonToastMessage.showErrorGettingDataFromServerMessage(DiscoverTagActivity.this);
                                }
                            } else {
                                CommonToastMessage.showErrorGettingDataFromServerMessage(DiscoverTagActivity.this);
                            }
                        }
                        catch (JSONException e) {
                            CommonToastMessage.showErrorGettingDataFromServerMessage(DiscoverTagActivity.this);
                        }
                    }
                });

                fetcher.fetch();
            }
        });
        registerForContextMenu(this.attendanceListView);

        this.getClassMeetingData();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_discover_tag, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == attendanceListView.getId()) {
            AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Aksi");
            String[] menuItems = new String[]{};
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemMenuIndex = item.getItemId();
        switch (itemMenuIndex) {
            case 0:

            case 1:
            default:
                break;
        }
        return true;
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
		
		Log.d(Constants.LOGGER_TAG, "New tag discovered: " + tagId);
		
		this.getUserData(tagId, UserIdentificationField.IDENTIFIER);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_manually:
                SimplePromptDialog manualAddDialog = new SimplePromptDialog();
                manualAddDialog
                    .setMessage("")
                    .setListener(new SimplePromptDialog.SimplePromptDialogListener() {
                        @Override
                        public void onPositiveButtonClick(DialogFragment dialog, String value) {
                            getUserData(value, UserIdentificationField.ID_NUMBER);
                        }

                        @Override
                        public void onNegativeButtonClick(DialogFragment dialog) {
                            dialog.dismiss();
                        }
                    });
                manualAddDialog.show(this.getFragmentManager(), "prompt");
        }

        return super.onOptionsItemSelected(item);
    }

    private void getClassMeetingData() {
        ClassMeetingDataFetcher fetcher = new ClassMeetingDataFetcher(this, this.classMeetingId);

        fetcher.setListener(new AsyncTaskListener<JSONObject>() {
            @Override
            public void onPreExecute() {
                // Do nothing for this time.
            }

            @Override
            public void onPostExecute(JSONObject response) {
                if (response != null) {
                    try {
                        if (response.has("success") && response.getBoolean("success")) {
                            ClassMeeting classMeeting = ClassMeeting.createInstance(response);

                            DiscoverTagActivity.this.setDataToView(classMeeting);
                        }
                        else {
                            Toast.makeText(DiscoverTagActivity.this, R.string.data_get_error, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(DiscoverTagActivity.this, R.string.data_parse_error, Toast.LENGTH_LONG);
                    }
                }
                else {
                    Toast.makeText(DiscoverTagActivity.this, "Tidak dapat mengambil data (null).", Toast.LENGTH_LONG).show();
                }
            }
        });

        fetcher.fetch();
    }

    private void setDataToView(ClassMeeting classMeeting) {
        this.courseTitle.setText(classMeeting.getCourse().getName());
    }

    private void getUserData(String id, UserIdentificationField field) {
        NewAttendanceDataSpec spec = new NewAttendanceDataSpec(id, field);
        NewAttendanceDataFetcher fetcher = new NewAttendanceDataFetcher(this, this.classMeetingId, spec);

        fetcher.setListener(new AsyncTaskListener<JSONObject>() {
            @Override
            public void onPreExecute() {
                // Do nothing for this time.
            }

            @Override
            public void onPostExecute(JSONObject response) {
                if (response != null) {
                    try {
                        if (response.has("success") && response.has("result") && response.getBoolean("success")) {
                            Attendance attendance = Attendance.createInstance(response.getJSONObject("result"));
                            DiscoverTagActivity.this.adapter.pushNewEntry(attendance);
                        }
                        else {
                            Toast.makeText(DiscoverTagActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(DiscoverTagActivity.this, "Tidak dapat mengambil data dari server (null). Kabari ini pada pengembang.", Toast.LENGTH_LONG).show();
                }
            }
        });

        fetcher.fetch();
    }
}
