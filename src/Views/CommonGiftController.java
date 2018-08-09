package Views;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.dbCon;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import models.AllValidation;
import models.CustomerTable;
import models.CustomerValidation;
import models.giftedTable;

public class CommonGiftController implements Initializable {
	
	
	  @FXML
	    private TitledPane customerTable;

	    @FXML
	    private TableView<CustomerTable> GiftPointTable1;

	    @FXML
	    private TableColumn<CustomerTable, String> mobile;

	    @FXML
	    private TableColumn<CustomerTable, String> name;

	    @FXML
	    private TableColumn<CustomerTable, String> totalpoints;

	    @FXML
	    private JFXTextField txtgiftNo;

	    @FXML
	    private ComboBox<String> comboGiftType;
	    
	    
	    public ObservableList<CustomerTable> list;
	    public ObservableList<String> listCombo;

	    
	    String date;
	    private int minute;
		private int hour;
		private int second;
		private int day;
		private int month;
		private int year;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {            
	        Calendar cal = Calendar.getInstance();
	        second = cal.get(Calendar.SECOND);
	        minute = cal.get(Calendar.MINUTE);
	        hour = cal.get(Calendar.HOUR);
	        day=cal.get(Calendar.DAY_OF_MONTH);
	        month=cal.get(Calendar.MONTH);
	        year=cal.get(Calendar.YEAR);
	        
	        date=(month+1) + "/" + day + "/" + year;
	        
	        
	    }),
	         new KeyFrame(Duration.seconds(1))
	    );
	    clock.setCycleCount(Animation.INDEFINITE);
	    clock.play();
		
		
	addComboboxValue();
	selectedGiftController s=new selectedGiftController();
	s.datee();
	//	addTableValue();
		//search();
	
		
	}
	
	
	
	//**************Add gift types to combo box*********
	public void addComboboxValue() {
		try {
			
			Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `gifttype` WHERE 1 ");
			
			
			listCombo=FXCollections.observableArrayList();
			while(rsc.next()){
			
				listCombo.add(rsc.getString("name")+" "+rsc.getString("amount"));
					
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		comboGiftType.setItems(listCombo);

	}
	
	//**************Search top 10,5,25,50 customers*********
public void search(){
	
	if(txtgiftNo.getText().isEmpty()){
		
		Alert a2=new Alert(AlertType.WARNING);
		a2.setHeaderText(null);
		//a2.initStyle(StageStyle.UNDECORATED);
		a2.setContentText("Enter from  range of 10,15,25,50..\nOther ranges are not allowed...");    		    	
		a2.showAndWait();
		return ;
	}
	else{
		int a=Integer.parseInt(txtgiftNo.getText());
		// a=txtgiftNo.getText();
	    	
	    			if(a==10 || a==15 || a==25 || a==50){
	    				
	    				addTableValue();
	    				
	    			}
	    			else{
	    				Alert a2=new Alert(AlertType.WARNING);
	    				a2.setHeaderText(null);
	    				//a2.initStyle(StageStyle.UNDECORATED);
	    				a2.setContentText("Search can only done in range of 10,15,25,50..\nOther ranges are not allowed...");    		    	
	    				a2.showAndWait();
	    				
	    				
	    			}
	   
		
	}
	
    	
}
	
//**************Add table values for the entered search amount*********	
public void addTableValue(){
		
		try {
			
			int a=Integer.parseInt(txtgiftNo.getText());
			int b=1;
			if(a==10){
				Connection cc=dbCon.mycon();
				Statement ss=cc.createStatement();
				ResultSet rsc=ss.executeQuery(" SELECT * FROM `customer` "
												+ "WHERE `mobile`NOT IN (SELECT `mobileNo` FROM `gift` WHERE 1) "
												+ "ORDER BY `totalpoints` DESC LIMIT 10 ");
				list=FXCollections.observableArrayList();
				
				while(rsc.next()){
				
					list.add(new CustomerTable(rsc.getString("address"), rsc.getString("mobile"), rsc.getString("name"), rsc.getString("nic"), rsc.getString("email"), rsc.getString("dateOfBirth"), rsc.getString("gender"), rsc.getString("totalpoints")));
						
				}
				
			}
			if(a==15){
				Connection cc=dbCon.mycon();
				Statement ss=cc.createStatement();
				ResultSet rsc=ss.executeQuery(" SELECT * FROM `customer` "
												+ "WHERE `mobile`NOT IN (SELECT `mobileNo` FROM `gift` WHERE 1) "
												+ "ORDER BY `totalpoints` DESC LIMIT 15 ");
				list=FXCollections.observableArrayList();
				
				while(rsc.next()){
				
					list.add(new CustomerTable(rsc.getString("address"), rsc.getString("mobile"), rsc.getString("name"), rsc.getString("nic"), rsc.getString("email"), rsc.getString("dateOfBirth"), rsc.getString("gender"), rsc.getString("totalpoints")));
						
				}
				
			}
			if(a==25){
				Connection cc=dbCon.mycon();
				Statement ss=cc.createStatement();
				ResultSet rsc=ss.executeQuery(" SELECT * FROM `customer` "
												+ "WHERE `mobile`NOT IN (SELECT `mobileNo` FROM `gift` WHERE 1) "
												+ "ORDER BY `totalpoints` DESC LIMIT 25 ");
				list=FXCollections.observableArrayList();
				
				while(rsc.next()){
				
					list.add(new CustomerTable(rsc.getString("address"), rsc.getString("mobile"), rsc.getString("name"), rsc.getString("nic"), rsc.getString("email"), rsc.getString("dateOfBirth"), rsc.getString("gender"), rsc.getString("totalpoints")));
						
				}
				
			}
			if(a==50){
				Connection cc=dbCon.mycon();
				Statement ss=cc.createStatement();
				ResultSet rsc=ss.executeQuery(" SELECT * FROM `customer` "
												+ "WHERE `mobile`NOT IN (SELECT `mobileNo` FROM `gift` WHERE 1) "
												+ "ORDER BY `totalpoints` DESC LIMIT 50 ");
				
				list=FXCollections.observableArrayList();
				
				while(rsc.next()){
				
					list.add(new CustomerTable(rsc.getString("address"), rsc.getString("mobile"), rsc.getString("name"), rsc.getString("nic"), rsc.getString("email"), rsc.getString("dateOfBirth"), rsc.getString("gender"), rsc.getString("totalpoints")));
						
				}
				
			}
						
			
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	
		mobile.setCellValueFactory(new PropertyValueFactory<CustomerTable, String> ("mobile"));
		name.setCellValueFactory(new PropertyValueFactory<CustomerTable, String> ("name"));
		totalpoints.setCellValueFactory(new PropertyValueFactory<CustomerTable, String> ("totalpoints"));
	//	addComboboxValue();
	//	editable();
		GiftPointTable1.setItems(list);
		
	}


//**************Add gift to all selected customers*********
public void addGift(){
	
	CustomerValidation c=new CustomerValidation();
	AllValidation a=new AllValidation();
	if(!txtgiftNo.getText().equals("") ){
	
			if(this.typenull(comboGiftType.getValue())){
				Alert a1=new Alert(AlertType.CONFIRMATION);
		    	a1.setHeaderText(null);
		    	//a1.initStyle(StageStyle.UNDECORATED);
		    	a1.setContentText("Do you want to Gift this ("+comboGiftType.getValue()+") gift to all top "+txtgiftNo.getText()+" customers");    		    	
		    	Optional<ButtonType> result = a1.showAndWait();
		    	if (result.get() == ButtonType.OK){
		    		
		    		try {
		    			
		    							
						Connection c1=dbCon.mycon();
						Statement s1=c1.createStatement();
						
							//s1.executeUpdate(" INSERT INTO `gift`( `cname`, `type`, `mobileNo`, `date`,  `email`, `nic`) VALUES ('"+txtName.getText()+"','"+comboGiftType.getValue()+"','"+txtMobile.getText()+"','"+date+"','"+txtEmail.getText()+"','"+txtNic.getText()+"') ");
							//
							//String msgg="Dear customer,\nYou have recived a gift of "+comboGiftType.getValue()+"\nGift details are also send to your email\nGet your gift from our show room..\n Thank you\nDressmo";
							//String msgg1="Dear customer,\nYou have recived a gift of "+comboGiftType.getValue()+"\nGift details are also send to your mobile number too\nGet your gift from our show room..\n Thank you\nDressmo";
							AllValidation a111=new AllValidation();
							
							//a111.sms(msgg, txtMobile.getText());
							//a111.Gmail(msgg1, txtEmail.getText());
							
							//Alert a2=new Alert(AlertType.INFORMATION);
					    	//a2.setHeaderText(null);
					    	//a1.initStyle(StageStyle.UNDECORATED);
					    	//a2.setContentText("( "+txtMobile.getText()+" ) ( "+txtName.getText()+" ) Gift details is send to both of his/her email and phone number ");    		    	
					    	//a2.showAndWait();
					    	//addTableValue();
					    	//clear();
					    	//
						
								List<String> columnData = new ArrayList<>();
								for (CustomerTable item : GiftPointTable1.getItems()) {
								    columnData.add(mobile.getCellObservableValue(item).getValue());
								}
							
									
							if(!columnData.isEmpty()){
								Connection cc=dbCon.mycon();
								Statement ss=cc.createStatement();
								ResultSet rss;
								int x=0;
								for (CustomerTable item : GiftPointTable1.getItems()) {
									//System.out.println(columnData.get(x)+"akash");
									x++;
									
									
									rss=ss.executeQuery("SELECT * FROM `customer` WHERE `mobile`='"+columnData.get(x)+"' ORDER BY `totalpoints` DESC");
									while(rss.next()){
										s1.executeUpdate(" INSERT INTO `gift`( `cname`, `type`, `mobileNo`, `date`,  `email`, `nic`) VALUES ('"+rss.getString("name")+"','"+comboGiftType.getValue()+"','"+rss.getString("mobile")+"','"+date+"','"+rss.getString("email")+"','"+rss.getString("nic")+"') ");
										
										
										
										String msgg="Dear customer,\nYou have recived a gift of "+comboGiftType.getValue()+"\nGift details are also send to your email\nGet your gift from our show room..\n Thank you\nDressmo";
										String msgg1="Dear customer,\nYou have recived a gift of "+comboGiftType.getValue()+"\nGift details are also send to your mobile number too\nGet your gift from our show room..\n Thank you\nDressmo";
										
										
										a111.sms(msgg, rss.getString("mobile"));
										a111.Gmail(msgg1, rss.getString("email"));
										
										Alert a2=new Alert(AlertType.INFORMATION);
								    	a2.setHeaderText(null);
								    	//a1.initStyle(StageStyle.UNDECORATED);
								    	a2.setContentText("( "+rss.getString("mobile")+" ) ( "+rss.getString("name")+" ) Gift details is send to both of his/her email and phone number ");    		    	
								    	a2.showAndWait();
								    	addTableValue();
									}
									
							    	
								}
								
							}
							else{
								Alert a11=new Alert(AlertType.WARNING);
			    		    	a11.setHeaderText(null);
			    		    	//a1.initStyle(StageStyle.UNDECORATED);
			    		    	a11.setContentText("No data in the table..\nSearch for the customers to gift..");
			    		    	a11.showAndWait();
							}
							
											
						
						
					} catch (Exception e) {
						// TODO: handle exception
						GiftPointTable1.setItems(null);
						Alert a11=new Alert(AlertType.WARNING);
	    		    	a11.setHeaderText(null);
	    		    	//a1.initStyle(StageStyle.UNDECORATED);
	    		    	a11.setContentText("Gift details are send to all the customers mobile and email..\nThank you..");
	    		    	a11.showAndWait();
						
						System.out.println(e);
					}
		    		
		    		
		    	}
		    	else if (result.get() == ButtonType.CANCEL){
		    		Alert a2=new Alert(AlertType.CONFIRMATION);
			    	a2.setHeaderText(null);
			    	//a1.initStyle(StageStyle.UNDECORATED);
			    	a2.setContentText("Customer gift cancled..\nDo you want to clear all the fields..");    		    	
			    	Optional<ButtonType> result1 = a2.showAndWait();
			    	if (result1.get() == ButtonType.OK){
			    		//clear();
			    		
			    	}
		    		
		    		
		    	}
				
			}
		
		
	}
	

	else{
		
		Alert a2=new Alert(AlertType.INFORMATION);
    	a2.setHeaderText(null);
    	//a1.initStyle(StageStyle.UNDECORATED);
    	a2.setContentText("To add a gift to a customer\nplease search for the customers first...");    		    	
    	 a2.showAndWait();
		
	}
	
	
}


//**************Gift type select validation*********
public boolean typenull(String ty){
	if(ty==null){
  		Alert a2=new Alert(AlertType.WARNING);
    	a2.setHeaderText(null);
    	//a1.initStyle(StageStyle.UNDECORATED);
    	a2.setContentText("You didn't chosse gift type Please select a gift type..");    		    	
    	a2.showAndWait();
		return false;
	}
	return true;
	
}







}
