package models;

import javafx.beans.property.SimpleStringProperty;

public class posTable {
	
	private final SimpleStringProperty id;
	private final SimpleStringProperty itemCode;
	private final SimpleStringProperty itemName;
	private final SimpleStringProperty rate;
	private final SimpleStringProperty qty;
	private final SimpleStringProperty ammount;
	


	public posTable(String id, String itemCode, String itemName, String rate, String qty, String ammount) {
		super();
		this.id = new SimpleStringProperty(id);
		this.itemCode = new SimpleStringProperty(itemCode);
		this.itemName = new SimpleStringProperty(itemName);
		this.rate = new SimpleStringProperty(rate);
		this.qty = new SimpleStringProperty(qty);
		this.ammount = new SimpleStringProperty(ammount);
	}
	
	
	public String getId() {
		return id.get();
	}

	public String getItemCode() {
		return itemCode.get();
	}

	public String getItemName() {
		return itemName.get();
	}

	public String getRate() {
		return rate.get();
	}

	public String getQty() {
		return qty.get();
	}

	public String getAmmount() {
		return ammount.get();
	}
	

}
