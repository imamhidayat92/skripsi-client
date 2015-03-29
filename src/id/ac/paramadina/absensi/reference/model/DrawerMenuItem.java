package id.ac.paramadina.absensi.reference.model;

public class DrawerMenuItem {
	public int type;
	public String name;
	public String sectionName;
	public String imageUrl;
	public String email;
	
	public DrawerMenuItem(String name) {
		this.name = name;
		this.type = 0;
	}
	
	public String getName() {
		return this.name;
	}
}
