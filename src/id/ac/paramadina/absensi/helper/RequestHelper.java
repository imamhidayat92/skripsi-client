package id.ac.paramadina.absensi.helper;

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
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RequestHelper {
	private String rootUrl;
	private String urlSuffix;
	
	public RequestHelper(String rootUrl, String urlSuffix) {
		this.rootUrl = rootUrl;
		this.urlSuffix = urlSuffix;
	}
	
	public JSONObject post(String controller, String action, String[] params, HashMap<String, String> data) {
		StringBuilder rawData = new StringBuilder();
		
		try {
			String strUrl = rootUrl + "/" + controller + "/" + action;
			
			for (String s : params) {
				strUrl += "/" + s;
			}
			
			strUrl += urlSuffix;
			
			URL url = new URL(strUrl);
			
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			urlConnection.setDoOutput(true);
			
			OutputStream outputStream = urlConnection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
			
			Set<String> keys = data.keySet();
			
			int i = 0;
			for (String s : keys) {
				writer.write(s + "=" + URLEncoder.encode(data.get(s), "UTF-8"));
				
				if (i < data.size() -1) {
					writer.write('&'); 
				}
				
				i++;
			}
			
			writer.flush();
			writer.close();
			
			outputStream.close();
			
			urlConnection.connect();
			
			InputStream inputStream = urlConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				rawData.append(line);
			}
			
		} catch (MalformedURLException e1) {
			Log.d("RequestHelper MalformedURLException", e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e) {
			Log.d("RequestHelper IOException", e.getMessage());
			e.printStackTrace();
		}
		
		
		try {
			JSONObject result = new JSONObject(rawData.toString());
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public JSONObject get(String controller, String action, String... params) {
		JSONObject result = null;
		
		return result;
	}
}
