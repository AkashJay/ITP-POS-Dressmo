package models;

import javafx.beans.property.SimpleStringProperty;

public class GiftTable {
	
	private final SimpleStringProperty name;
	private final SimpleStringProperty amount;
	private final SimpleStringProperty startDate;
	private final SimpleStringProperty endDate;
	
	
	public GiftTable(String name, String amount, String startDate, String endDate) {
		super();
		this.name = new SimpleStringProperty(name);
		this.amount =new SimpleStringProperty(amount) ;
		this.startDate = new SimpleStringProperty(startDate);
		this.endDate = new SimpleStringProperty(endDate);
	}


	public String getName() {
		return name.get();
	}


	public String getAmount() {
		return amount.get();
	}


	public String getStartDate() {
		return startDate.get();
	}


	public String getEndDate() {
		return endDate.get();
	}

}
