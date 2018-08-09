package models;

import javafx.beans.property.SimpleStringProperty;

public class CustomerTable {
	
	private final SimpleStringProperty address;
	private final SimpleStringProperty mobile;
	private final SimpleStringProperty name;
	private final SimpleStringProperty nic;
	private final SimpleStringProperty email;
	private final SimpleStringProperty dateOfBirth;
	private final SimpleStringProperty gender;
	private final SimpleStringProperty totalpoints;
	
	
	
	public String getAddress() {
		return address.get();
	}


	public String getMobile() {
		return mobile.get();
	}


	public String getName() {
		return name.get();
	}


	public String getNic() {
		return nic.get();
	}


	public String getEmail() {
		return email.get();
	}


	public String getDateOfBirth() {
		return dateOfBirth.get();
	}


	public String getGender() {
		return gender.get();
	}


	public String getTotalpoints() {
		return totalpoints.get();
	}


	
	
	
	public CustomerTable(String address, String mobile, String name, String nic, String email, String dateOfBirth,
			String gender, String totalpoints) {
		super();
		this.address = new SimpleStringProperty(address);
		this.mobile = new SimpleStringProperty(mobile);
		this.name = new SimpleStringProperty(name);
		this.nic = new SimpleStringProperty(nic);
		this.email = new SimpleStringProperty(email);
		this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
		this.gender = new SimpleStringProperty(gender);
		this.totalpoints = new SimpleStringProperty(totalpoints);
	}

}
