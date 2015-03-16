package id.ac.paramadina.absensi.reference;

import java.util.HashMap;

public class GlobalData {
	private static HashMap<String, Object> temporaryObjectHolder;
	
	public static int getDrawerLayoutAdapter() {
		return 0;
	}
	
	public static void putTemporaryObject(String id, Object val) {
		if (temporaryObjectHolder == null) {
			temporaryObjectHolder = new HashMap<String, Object>();
		}
		
		temporaryObjectHolder.put(id, val);
	}
	
	public static Object getTemporaryObject(String id) {
		return temporaryObjectHolder.get(id);
	}
}
