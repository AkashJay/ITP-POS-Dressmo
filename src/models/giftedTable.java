package models;

import javafx.beans.property.SimpleStringProperty;

public class giftedTable {
	
	private final SimpleStringProperty cname;
	private final SimpleStringProperty type;
	private final SimpleStringProperty mobileNo;
	private final SimpleStringProperty date;
	private final SimpleStringProperty email;
	private final SimpleStringProperty nic;
	
	
	public giftedTable(String cname, String type, String mobileNo, String date, String email, String nic) {
		super();
		this.cname = new SimpleStringProperty(cname);
		this.type = new SimpleStringProperty(type);
		this.mobileNo = new SimpleStringProperty(mobileNo);
		this.date = new SimpleStringProperty(date);
		this.email = new SimpleStringProperty(email);
		this.nic = new SimpleStringProperty(nic);
	}


	public String getCname() {
		return cname.get();
	}


	public String getType() {
		return type.get();
	}


	public String getMobileNo() {
		return mobileNo.get();
	}


	public String getDate() {
		return date.get();
	}


	public String getEmail() {
		return email.get();
	}


	public String getNic() {
		return nic.get();
	}
	
	

}
