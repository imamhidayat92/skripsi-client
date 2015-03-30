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
import java.net.URLConnection;
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
		
		URL url = null;
		HttpURLConnection urlConnection = null;
		
		try {
			int count = 0;
			for (String s : params.keySet()) {
				resourceUrl += count == 0 ? "?" + s + "=" + params.get(s) : "&" + s + "=" + params.get(s);
				count++;
			}
			
			resourceUrl += urlSuffix;
			
			url = new URL(this.rootUrl + resourceUrl);

			Log.d("skripsi-client", "(GET) Connecting to " + this.rootUrl + resourceUrl);
			
			urlConnection = (HttpURLConnection) url.openConnection();
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
			return this.getErrorStreamResponse(urlConnection);
		} 
		catch (ProtocolException e) {
			Log.d("skripsi-client", "ProtocolException: " + e.getMessage());
			return this.getErrorStreamResponse(urlConnection);
		} 
		catch (IOException e) {
			Log.d("skripsi-client", "IOException: " + e.getMessage());
			return this.getErrorStreamResponse(urlConnection);
		} 
		catch (JSONException e) {
			Log.d("skripsi-client", "JSONException: " + e.getMessage());
			return this.getErrorStreamResponse(urlConnection);
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
		
		URL url = null;
		HttpURLConnection urlConnection = null;
		
		try {
			int count = 0;
			for (String s : params.keySet()) {
				resourceUrl += count == 0 ? "?" + s + "=" + params.get(s) : "&" + s + "=" + params.get(s);
				count++;
			}
			
			resourceUrl += urlSuffix;
			
			url = new URL(this.rootUrl + resourceUrl);
			
			Log.d("skripsi-client", "(POST) Connecting to " + this.rootUrl + resourceUrl);
			
			urlConnection = (HttpURLConnection) url.openConnection();
			
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
			StringBuilder strPostedData = new StringBuilder();
			
			int i = 0;
			for (String s : keys) {
				writer.write(s + "=" + URLEncoder.encode(data.get(s), "UTF-8"));
				strPostedData.append(s + "=" + data.get(s));
				
				if (i < data.size() -1) {
					writer.write('&'); 
					strPostedData.append('&');
				}
				
				i++;
			}
			
			writer.flush();
			writer.close();
			
			outputStream.close();
			
			Log.d("skripsi-client", "Posted data: " + strPostedData.toString());
			
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
			return this.getErrorStreamResponse(urlConnection);
		} 
		catch (ProtocolException e) {
			Log.d("skripsi-client", "ProtocolException: " + e.getMessage());
			return this.getErrorStreamResponse(urlConnection);
		} 
		catch (IOException e) {
			Log.d("skripsi-client", "IOException: " + e.getMessage());
			return this.getErrorStreamResponse(urlConnection);
		} 
		catch (JSONException e) {
			Log.d("skripsi-client", "JSONException: " + e.getMessage());
			return this.getErrorStreamResponse(urlConnection);
		}
	}
	
	public JSONObject post(	String resourceUrl, 
			HashMap<String, String> params, 
			HashMap<String, String> data) 
	{
		return this.post(resourceUrl, params, data, new HashMap<String, String>());
	}
	
	private JSONObject getErrorStreamResponse(HttpURLConnection connection) {
		InputStream errorStream = connection.getErrorStream();
		BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
		
		StringBuilder rawData = new StringBuilder();
		String line = null;
		
		try {
			while((line = errorReader.readLine()) != null) {
				rawData.append(line);
			}
			
			errorStream.close();
			errorReader.close();
			connection.disconnect();
			
			JSONObject response = new JSONObject(rawData.toString());
			return response;
		} catch (IOException e) {
			Log.d("skripsi-client", "Error while reading error stream.");
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			Log.d("skripsi-client", "Error while parsing JSON. Raw data content: " + rawData.toString());
			e.printStackTrace();
			return null;
		}
	}
}
