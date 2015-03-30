package id.ac.paramadina.absensi.reference.spec;

import id.ac.paramadina.absensi.reference.enumeration.AuthenticationType;

import java.util.HashMap;

public class AuthenticationDataSpec extends BaseSpec {
	private AuthenticationType type;
	private String identifier;
	private String email;
	private String password; 
	
	public AuthenticationDataSpec(String email, String password) {
		this.email = email;
		this.password = password;
		
		this.type = AuthenticationType.DEFAULT;
	}
	
	public AuthenticationDataSpec(String identifier) {
		this.identifier = identifier;
		
		this.type = AuthenticationType.TAG;
	}
	
	public HashMap<String, String> toHashMap() {
		HashMap<String, String> data = new HashMap<String, String>();
		switch (this.type) {
			case DEFAULT:
				data.put("email", this.email);
				data.put("password", this.password);
				break;
			case TAG:
				data.put("identifier", this.identifier);
				break;
		}
		return data;
	}
}
