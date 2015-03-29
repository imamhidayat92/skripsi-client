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
import java.net.ProtocolException;
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
	
	public RequestHelper(String rootUrl) {
		this.rootUrl = rootUrl;
		this.urlSuffix = "";
	}
	
	public RequestHelper(String rootUrl, String urlSuffix) {
		this.rootUrl = rootUrl;
		this.urlSuffix = urlSuffix;
	}
	
	public JSONObject get(	String resourceUrl, 
							HashMap<String, String> params, 
							HashMap<String, String> headers) 
	{
		StringBuilder rawData = new StringBuilder();
		
		try {
			int count = 0;
			for (String s : params.keySet()) {
				resourceUrl += count == 0 ? "?" + s + "=" + params.get(s) : "&" + s + "=" + params.get(s);
				count++;
			}
			
			resourceUrl += urlSuffix;
			
			URL url = new URL(this.rootUrl + resourceUrl);

			Log.d("skripsi-client", "Connecting to " + this.rootUrl + resourceUrl);
			
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			urlConnection.setRequestMethod("GET");
			
			if (headers.size() > 0) {
				Set<String> headerKeys = headers.keySet();
				
				for (String s: headerKeys) {
					urlConnection.setRequestProperty(s, headers.get(s));
				}				
			}
			
			urlConnection.connect();
			
			InputStream inputStream = urlConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				rawData.append(line);
			}
			
			reader.close();
			inputStream.close();
			
			urlConnection.disconnect();

			Log.d("skripsi-client", "Obtained data: " + rawData.toString());
			
			JSONObject result = new JSONObject(rawData.toString());
			return result;
		}
		catch (MalformedURLException e) {
			Log.d("skripsi-client", "MalformedURLException: " + e.getMessage());
			return null;
		} 
		catch (ProtocolException e) {
			Log.d("skripsi-client", "ProtocolException: " + e.getMessage());
			return null;
		} 
		catch (IOException e) {
			Log.d("skripsi-client", "IOException: " + e.getMessage());
			return null;
		} 
		catch (JSONException e) {
			Log.d("skripsi-client", "JSONException: " + e.getMessage());
			return null;
		}
	}
	
	public JSONObject get(	String resourceUrl, 
			HashMap<String, String> params) 
	{
		return this.get(resourceUrl, params, new HashMap<String, String>());
	}
	
	public JSONObject post(	String resourceUrl, 
							HashMap<String, String> params, 
							HashMap<String, String> data, 
							HashMap<String, String> headers) 
	{
		StringBuilder rawData = new StringBuilder();
		
		try {
			int count = 0;
			for (String s : params.keySet()) {
				resourceUrl += count == 0 ? "?" + s + "=" + params.get(s) : "&" + s + "=" + params.get(s);
				count++;
			}
			
			resourceUrl += urlSuffix;
			
			URL url = new URL(this.rootUrl + resourceUrl);
			
			Log.d("skripsi-client", "Connecting to " + this.rootUrl + resourceUrl);
			
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");
			
			if (headers.size() > 0) {
				Set<String> headerKeys = headers.keySet();
				
				for (String s: headerKeys) {
					urlConnection.setRequestProperty(s, headers.get(s));
				}				
			}
			
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
			
			reader.close();
			inputStream.close();
			
			urlConnection.disconnect();
			
			Log.d("skripsi-client", "Obtained data: " + rawData.toString());
			
			JSONObject result = new JSONObject(rawData.toString());
			return result;
		}
		catch (MalformedURLException e) {
			Log.d("skripsi-client", "MalformedURLException: " + e.getMessage());
			return null;
		} 
		catch (ProtocolException e) {
			Log.d("skripsi-client", "ProtocolException: " + e.getMessage());
			return null;
		} 
		catch (IOException e) {
			Log.d("skripsi-client", "IOException: " + e.getMessage());
			return null;
		} 
		catch (JSONException e) {
			Log.d("skripsi-client", "JSONException: " + e.getMessage());
			return null;
		}
	}
	
	public JSONObject post(	String resourceUrl, 
			HashMap<String, String> params, 
			HashMap<String, String> data) 
	{
		return this.post(resourceUrl, params, data, new HashMap<String, String>());
	}
}
