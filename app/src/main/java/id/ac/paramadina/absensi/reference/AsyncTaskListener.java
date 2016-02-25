package id.ac.paramadina.absensi.reference;

public interface AsyncTaskListener<T> {
	public void onPreExecute();
	public void onPostExecute(T result);
    public void onError(String message, Object data);
}
