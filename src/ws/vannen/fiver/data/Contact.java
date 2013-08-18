package ws.vannen.fiver.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable{
	
	private String _id;
	private String displayName;
	private String phoneNumber;
	private Boolean alreadyConverted = false;
	private Boolean selectedToProcess = false;
	private PhoneNumberType phoneNumberType = PhoneNumberType.Unknown;
	private String newPhoneNumber = "";
	private String oldPhoneNumber = "";
	
	public enum PhoneNumberType{
		Unknown,
		Emtel,
		Cellplus,
		Mtml
	};
	
	
	public Contact(Parcel in){
		String[] data = new String[6];
		
		in.readStringArray(data);
		this._id = data[0];
		this.displayName = data[1];
		this.phoneNumber = data[2];
		this.selectedToProcess = Boolean.getBoolean(data[3]);
		this.newPhoneNumber = data[4];
		this.phoneNumberType = PhoneNumberType.valueOf(data[5]);
	}
	
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

	public Boolean getSelectedToProcess() {
		return selectedToProcess;
	}

	public void setSelectedToProcess(Boolean selectedToProcess) {
		this.selectedToProcess = selectedToProcess;
	}

	public PhoneNumberType getPhoneNumberType() {
		return phoneNumberType;
	}

	public void setPhoneNumberType(PhoneNumberType phoneNumberType) {
		this.phoneNumberType = phoneNumberType;
	}

	public String getNewPhoneNumber() {
		return newPhoneNumber;
	}

	public void setNewPhoneNumber(String newPhoneNumber) {
		this.newPhoneNumber = newPhoneNumber;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		String[] data = new String[6];
		data[0] = this._id;
		data[1] = this.displayName;
		data[2] = this.phoneNumber;
		data[3] = this.selectedToProcess.toString();
		data[4] = this.newPhoneNumber;
		data[5] = this.phoneNumberType.toString();
		
		dest.writeStringArray(data);
		
	}


	public Boolean getAlreadyConverted() {
		return alreadyConverted;
	}

	public void setAlreadyConverted(Boolean alreadyConverted) {
		this.alreadyConverted = alreadyConverted;
	}




	public String getOldPhoneNumber() {
		return oldPhoneNumber;
	}

	public void setOldPhoneNumber(String oldPhoneNumber) {
		this.oldPhoneNumber = oldPhoneNumber;
	}




	public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {

	    @Override
	    public Contact createFromParcel(Parcel source) {
	        return new Contact(source);
	    }

	    @Override
	    public Contact[] newArray(int size) {
	        return new Contact[size];
	    }


	};
	
	
	

	

}
