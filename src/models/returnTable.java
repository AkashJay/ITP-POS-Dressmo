package models;

import javafx.beans.property.SimpleStringProperty;

public class returnTable {
	private final SimpleStringProperty billNo;
	private final SimpleStringProperty itemCode;
	private final SimpleStringProperty rate;
	private final SimpleStringProperty amount;
	private final SimpleStringProperty qty;
	private final SimpleStringProperty name;
	private final SimpleStringProperty date;
	
	
	public String getBillNo() {
		return billNo.get();
	}


	public String getItemCode() {
		return itemCode.get();
	}


	public String getRate() {
		return rate.get();
	}


	public String getAmount() {
		return amount.get();
	}


	public String getQty() {
		return qty.get();
	}


	public String getName() {
		return name.get();
	}


	public String getDate() {
		return date.get();
	}


	public returnTable(String billNo, String itemCode, String rate, String amount, String qty, String name,	String date) {
		super();
		this.billNo = new SimpleStringProperty(billNo);
		this.itemCode = new SimpleStringProperty(itemCode);
		this.rate = new SimpleStringProperty(rate);
		this.amount =new SimpleStringProperty(amount); 
		this.qty = new SimpleStringProperty(qty);
		this.name = new SimpleStringProperty(name);
		this.date = new SimpleStringProperty(date);
	}

}
