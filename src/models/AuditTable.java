package models;

import javafx.beans.property.SimpleStringProperty;

public class AuditTable {
	
	private final SimpleStringProperty id;
	private final SimpleStringProperty eid;
	private final SimpleStringProperty ename;
	private final SimpleStringProperty date;
	private final SimpleStringProperty loginTime;
	private final SimpleStringProperty changeTime;
	private final SimpleStringProperty discription;
	private final SimpleStringProperty status;
	
	
	public AuditTable(String id, String eid, String ename, String date, String loginTime, String changeTime,
			String discription, String status) {
		super();
		this.id = new SimpleStringProperty(id);
		this.eid = new SimpleStringProperty(eid);
		this.ename = new SimpleStringProperty(ename);
		this.date = new SimpleStringProperty(date);
		this.loginTime = new SimpleStringProperty(loginTime);
		this.changeTime = new SimpleStringProperty(changeTime);
		this.discription = new SimpleStringProperty(discription);
		this.status = new SimpleStringProperty(status);
	}


	public String getId() {
		return id.get();
	}


	public String getEid() {
		return eid.get();
	}


	public String getEname() {
		return ename.get();
	}


	public String getDate() {
		return date.get();
	}


	public String getLoginTime() {
		return loginTime.get();
	}


	public String getChangeTime() {
		return changeTime.get();
	}


	public String getDiscription() {
		return discription.get();
	}


	public String getStatus() {
		return status.get();
	}
	
	
	
	
}
