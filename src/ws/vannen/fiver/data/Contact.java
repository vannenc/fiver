package ws.vannen.fiver.data;

public class Contact {
	
	private String _id;
	private String displayName;
	private String phoneNumber;
	
	public Contact(String id, String displayName, String phoneNumber){
		this._id = id;
		this.displayName = displayName;
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	

	

}
