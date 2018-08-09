package Views;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import application.dbCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import models.AllValidation;
import models.PosHome;
import models.returnTable;

public class ItemsReturnController implements Initializable {
		
		@FXML
		private TableView<returnTable> returnTable;
	
	 	@FXML
	    private TableColumn<returnTable, String> itemName;

	    @FXML
	    private TableColumn<returnTable, String> qty;
	    
	    @FXML
	    private TableColumn<returnTable, String> barcode;
	    
	    @FXML
	    private TextField txtBillNo;

	    @FXML
	    private TextField txtBarcode;

	    @FXML
	    private TextField txtName;

	    @FXML
	    private TextField txtQty;

	    @FXML
	    private TextField txtDiscount;

	    @FXML
	    private TextField txtDate;

	    @FXML
	    private TextField txtTotal;
	
	    public ObservableList<returnTable> list;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		addTable();	
		takeTableValue();
		takeTableValue1();
		txtBillNo.requestFocus();
		
	}
	
	
	//**************Add values to return table on given bill number*********
private void addValueToTable() {
		// TODO Auto-generated method stub
		try {
			
			Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `tems` WHERE billNo='"+txtBillNo.getText()+"' ");
			list=FXCollections.observableArrayList();
			while(rsc.next()){
				
			
				System.out.println("lisstttttttttt");
				
				list.add(new returnTable(rsc.getString("billNo"), rsc.getString("itemCode"),  rsc.getString("rate"), rsc.getString("amount") , rsc.getString("qty"),rsc.getString("name"),rsc.getString("date")));
						
								
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		itemName.setCellValueFactory(new PropertyValueFactory<returnTable, String> ("name"));
		qty.setCellValueFactory(new PropertyValueFactory<returnTable, String> ("qty"));
		barcode.setCellValueFactory(new PropertyValueFactory<returnTable, String> ("itemCode"));
	
	
		returnTable.setItems(list);
		

}


//**************Bill nuber text field key press actions*********	
public void addTable(){
    	
    	
    	this.txtBillNo.setOnKeyPressed(e->{
    		
    		if (e.getCode() == KeyCode.ENTER)  {    	  		
    			try { 
    				
    				if(txtBillNo.getText().isEmpty()){
    					txtBillNo.requestFocus();
    					
    				}
    				
    				else{
    					AllValidation a=new AllValidation();
    					if(a.validateBillNo(txtBillNo.getText())){
    						System.out.println("table");
    						addValueToTable();
    						
    						txtBillNo.requestFocus();
    						
    					}
    					else{
    						
    						Alert a2=new Alert(AlertType.INFORMATION);
        					a2.setHeaderText(null);
        					//a2.initStyle(StageStyle.UNDECORATED);
        					a2.setContentText("Invalid Bill No...");    		    	
        					a2.showAndWait();
        					
    					}
    					
    				
    				}
				} catch (Exception e1) {
					
					
				}
        	}
    		if (e.getCode() == KeyCode.ESCAPE)  { 
    			try { 			
    				
    				//Close the current window(call above method)
    				close();
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    			
    		}
    		if (e.getCode() == KeyCode.TAB)  { 
    			try { 			
    				
    				returnTable.requestFocus();
    				takeTableValue();
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    			
    		}
    	});   	
    	
    }



//**************Take table values*********
public void takeTableValue1() {
	// TODO Auto-generated method stub
	returnTable.setOnMouseClicked(e -> {
		
		returnTable a=returnTable.getItems().get(returnTable.getSelectionModel().getSelectedIndex());
		System.out.println(a.getBillNo());
		System.out.println(a.getName());
		txtBarcode.setText(a.getItemCode());
		txtName.setText(a.getName());
		txtQty.setText(a.getQty());
		txtDate.setText(a.getDate());
		txtTotal.setText(a.getAmount());
		txtDiscount.setText("No Discount");
		
		
	});

}



public void takeTableValue() {
	// TODO Auto-generated method stub
	returnTable.setOnKeyPressed(e -> {
		
		returnTable a=returnTable.getItems().get(returnTable.getSelectionModel().getSelectedIndex());
		System.out.println(a.getBillNo());
		System.out.println(a.getName());
		txtBarcode.setText(a.getItemCode());
		txtName.setText(a.getName());
		txtQty.setText(a.getQty());
		txtDate.setText(a.getDate());
		txtTotal.setText(a.getAmount());
		txtDiscount.setText("No Discount");
		
		if (e.getCode() == KeyCode.ESCAPE)  { 
			try {
			
				
				//Close the current window(call above method)
				close();
				
				
				
				
			} catch(Exception e1) {
				e1.printStackTrace();
			}
			
		}
		
		if (e.getCode() == KeyCode.ENTER)  {    	  		
			try { 
				
				Alert a1=new Alert(AlertType.CONFIRMATION);
		    	a1.setHeaderText(null);
		    	//a1.initStyle(StageStyle.UNDECORATED);
		    	a1.setContentText("Return item "+txtName.getText()+" with qty "+txtQty.getText());    		    	
		    	
		    	Optional<ButtonType> result = a1.showAndWait();
		    	if (result.get() == ButtonType.OK){
		    	    // ... user chose OK
		    		deleteItem();
		    		addValueToTable();
		    		Alert a2=new Alert(AlertType.INFORMATION);
					a2.setHeaderText(null);
					//a2.initStyle(StageStyle.UNDECORATED);
					a2.setContentText("Item Returned..");    
					
					Optional<ButtonType> result1 = a2.showAndWait();
			    	if (result1.get() == ButtonType.OK){
			    		
			    		openBill();
			    		
			    	}
					txtBillNo.requestFocus();
					
					clear();
					
					
			
					
					
		    	} else {
		    	    // ... user chose CANCEL or closed the dialog
		    		Alert a2=new Alert(AlertType.INFORMATION);
					a2.setHeaderText(null);
					//a2.initStyle(StageStyle.UNDECORATED);
					a2.setContentText("Return Canceled..");    		    	
					a2.showAndWait();
		    	}
		    	System.out.println("return");
				
			} catch (Exception e1) {
				//returnTable.requestFocus();
					System.out.println("errrrrrrrrrrrrrrrr");
			}
    	}
		else{
			
			System.out.println("not returned");
		}
	});

}


//**************close current window*********
public void close() {
    Stage stage = (Stage) returnTable.getScene().getWindow();
    stage.close();
}

//**************Clear all feilds*********
public void clear(){
	
	txtBarcode.setText("");
	txtDate.setText("");
	txtDiscount.setText("");
	txtName.setText("");
	txtQty.setText("");
	txtTotal.setText("");
}


//**************Delete returned item from the item  database*********
public void deleteItem() {
	
	try {
		
		Connection c1=dbCon.mycon();
		Statement s1=c1.createStatement();
		s1.executeUpdate("DELETE FROM `tems` WHERE `itemCode`='"+txtBarcode.getText()+"' and `billNo`='"+txtBillNo.getText()+"'");
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e);
	}
	
}




public void returnI() {
	
	Alert a1=new Alert(AlertType.CONFIRMATION);
	a1.setHeaderText(null);
	//a1.initStyle(StageStyle.UNDECORATED);
	a1.setContentText("Return item "+txtName.getText()+" with qty "+txtQty.getText());    		    	
	
	Optional<ButtonType> result = a1.showAndWait();
	if (result.get() == ButtonType.OK){
	    // ... user chose OK
		deleteItem();
		addValueToTable();
		
		
		Alert a2=new Alert(AlertType.INFORMATION);
		a2.setHeaderText(null);
		
		//a2.initStyle(StageStyle.UNDECORATED);
		a2.setContentText("Item Returned..Bill printed");    		    	
		Optional<ButtonType> result1 = a2.showAndWait();
		if (result1.get() == ButtonType.OK){
			openBill();
			System.out.println(txtBarcode.getText());
		}
		txtBillNo.requestFocus();
		
		clear();
		
		

		
		
	} else {
	    // ... user chose CANCEL or closed the dialog
		Alert a2=new Alert(AlertType.INFORMATION);
		a2.setHeaderText(null);
		//a2.initStyle(StageStyle.UNDECORATED);
		a2.setContentText("Return Canceled..");    		    	
		a2.showAndWait();
	}
	
}



public void openBill(){
	try {
		//Open the home window
		FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/ReturnBill.fxml"));
		Parent root1=(Parent) fxmloder.load();
		
		Stage stage =new Stage();
		//stage.initStyle(StageStyle.TRANSPARENT);
		//stage.setMaximized(true);
		stage.setScene(new Scene(root1));
		stage.show();
		
		((ReturnBillController)fxmloder.getController()).setLetter1(txtBillNo.getText(),txtTotal.getText(),txtBarcode.getText(),"EID104",txtName.getText(),txtQty.getText());;
		
		
		//Close the current window(call above method)
		//close();
		
		
		
		
	} catch(Exception e1) {
		e1.printStackTrace();
	}
	
}


}
