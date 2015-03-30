package id.ac.paramadina.absensi.reference.spec;

import java.util.HashMap;

public class UserTagDataSpec extends BaseSpec {
	private String tagId;
	
	public UserTagDataSpec(String tagId) {
		this.tagId = tagId;
	}
	
	public String getTagId() {
		return this.tagId;
	}

	@Override
	public HashMap<String, String> toHashMap() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("identifier", this.tagId);
		return data;
	}
}
