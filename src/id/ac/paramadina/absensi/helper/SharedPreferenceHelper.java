package id.ac.paramadina.absensi.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SharedPreferenceHelper {
	public static final String PREFERENCE_FILE_KEY = "id.ac.paramadina.absensi.SETTINGS";
	
	public static String getString(Context context, String key) {
		String result = null;
		
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			preferences.getString(key, result);
		}
		catch (Exception ex) {
			Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		return result;
	}
	
	public static int getInt(Context context, String key) {
		int result = 0;
		
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			preferences.getInt(key, result);
		}
		catch (Exception ex) {
			Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		return result;
	}
	
	public static void putString(Context context, String key, String value) {
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = preferences.edit();
			
			editor.putString(key, value);
			editor.commit();
		}
		catch (Exception ex) {
			Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	public static void putInt(Context context, String key, int value) {
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = preferences.edit();
			
			editor.putInt(key, value);
			editor.commit();
		}
		catch (Exception ex) {
			Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
}
