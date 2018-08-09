package Views;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.AllValidation;
import models.CustomerTable;
import models.CustomerValidation;


public class selectedGiftController implements Initializable {
	
	

    @FXML
    private TableView<CustomerTable> GiftPointTable1;

    @FXML
    private TableColumn<CustomerTable, String> mobile;

    @FXML
    private TableColumn<CustomerTable, String> name;

    @FXML
    private TableColumn<CustomerTable, String> totalpoints;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtMobile;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private DatePicker dateTill;
    
    @FXML
    private ComboBox<String> comboGiftType;
    
    
    public ObservableList<CustomerTable> list;
    public ObservableList<String> listCombo;

    
    private int minute;
	private int hour;
	private int second;
	private int day;
	private int month;
	private int year;
	String date;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		addTableValue();
		takeTableValue();
		addComboboxValue();
		editable();
		datee();
		
			
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
	}
	
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
	
public void addTableValue(){
		
		try {
			
			Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `customer` "
											+ "WHERE `mobile`NOT IN (SELECT `mobileNo` FROM `gift` WHERE 1) "
											+ "ORDER BY `totalpoints` DESC ");
			
			list=FXCollections.observableArrayList();
			
			while(rsc.next()){
			
				list.add(new CustomerTable(rsc.getString("address"), rsc.getString("mobile"), rsc.getString("name"), rsc.getString("nic"), rsc.getString("email"), rsc.getString("dateOfBirth"), rsc.getString("gender"), rsc.getString("totalpoints")));
					
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	
		mobile.setCellValueFactory(new PropertyValueFactory<CustomerTable, String> ("mobile"));
		name.setCellValueFactory(new PropertyValueFactory<CustomerTable, String> ("name"));
		totalpoints.setCellValueFactory(new PropertyValueFactory<CustomerTable, String> ("totalpoints"));
		addComboboxValue();
		editable();
		GiftPointTable1.setItems(list);
		
	}

public void editable() {
	txtEmail.setEditable(false);
	txtName.setEditable(false);
	txtMobile.setEditable(false);
	txtNic.setEditable(false);

}



public void takeTableValue() {
	// TODO Auto-generated method stub
	GiftPointTable1.setOnMouseClicked(e -> {
		
		CustomerTable a=GiftPointTable1.getItems().get(GiftPointTable1.getSelectionModel().getSelectedIndex());
		txtName.setText(a.getName());
		txtMobile.setText(a.getMobile());
		txtEmail.setText(a.getEmail());
		txtNic.setText(a.getNic());
		
	});	
		
}

public void clear(){
	txtEmail.setText("");
	txtMobile.setText("");
	txtName.setText("");
	txtNic.setText("");
	comboGiftType.setItems(null);
	addComboboxValue();
}

public void addGift(){
	
	CustomerValidation c=new CustomerValidation();
	AllValidation a=new AllValidation();
	if(!txtName.getText().equals("") ){
	
			if(this.typenull(comboGiftType.getValue())){
				Alert a1=new Alert(AlertType.CONFIRMATION);
		    	a1.setHeaderText(null);
		    	//a1.initStyle(StageStyle.UNDECORATED);
		    	a1.setContentText("Do you want to Gift this ("+comboGiftType.getValue()+") gift to customer ( "+txtName.getText()+" ) with mobile no ( "+txtMobile.getText()+" )");    		    	
		    	Optional<ButtonType> result = a1.showAndWait();
		    	if (result.get() == ButtonType.OK){
		    		
		    		try {
						
						
						Connection c1=dbCon.mycon();
						Statement s1=c1.createStatement();
						if(this.checkgiftedCustomer(txtMobile.getText())){
							
							Alert a11=new Alert(AlertType.WARNING);
					    	a11.setHeaderText(null);
					    	//a1.initStyle(StageStyle.UNDECORATED);
					    	a11.setContentText("( "+txtName.getText()+" ) customer alredy gifted a gift...\n Check it before giving another gift.. ");    		    	
					    	a11.showAndWait();
					    	
					    	return;
						}
						else{
							
							s1.executeUpdate(" INSERT INTO `gift`( `cname`, `type`, `mobileNo`, `date`,  `email`, `nic`) VALUES ('"+txtName.getText()+"','"+comboGiftType.getValue()+"','"+txtMobile.getText()+"','"+date+"','"+txtEmail.getText()+"','"+txtNic.getText()+"') ");
							
							String msgg="Dear customer,\nYou have recived a gift of "+comboGiftType.getValue()+"\nGift details are also send to your email\nGet your gift from our show room..\n Thank you\nDressmo";
							String msgg1="Dear customer,\nYou have recived a gift of "+comboGiftType.getValue()+"\nGift details are also send to your mobile number too\nGet your gift from our show room..\n Thank you\nDressmo";
							AllValidation a111=new AllValidation();
							
							a111.sms(msgg, txtMobile.getText());
							a111.Gmail(msgg1, txtEmail.getText());
							
							Alert a2=new Alert(AlertType.INFORMATION);
					    	a2.setHeaderText(null);
					    	//a1.initStyle(StageStyle.UNDECORATED);
					    	a2.setContentText("( "+txtMobile.getText()+" ) ( "+txtName.getText()+" ) Gift details is send to both of his/her email and phone number ");    		    	
					    	a2.showAndWait();
					    	addTableValue();
					    	clear();
					    	
					    	;
						}
						
						
						
					} catch (Exception e) {
						// TODO: handle exception
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
			    		clear();
			    		
			    	}
		    		
		    		
		    	}
				
			}
		
		
		
    	
		
	}
	

	else{
	
		Alert a2=new Alert(AlertType.INFORMATION);
    	a2.setHeaderText(null);
    	//a1.initStyle(StageStyle.UNDECORATED);
    	a2.setContentText("To add a gift to a customer\nplease select a customer from the table..");    		    	
    	 a2.showAndWait();
		
	}
	
	
}

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

public boolean checkgiftedCustomer(String mobileno){
	
	try {
		
		Connection cc=dbCon.mycon();
		Statement ss=cc.createStatement();
		ResultSet rsc=ss.executeQuery(" SELECT * FROM `gift` WHERE `mobileNo`='"+mobileno+"' ");
		
		
		while(rsc.next()){
			
		System.out.println(rsc.getString("mobileNo"));
			return true;
			
							
		}
		
	} catch (Exception e) {
		System.out.println(e);
	}
	
	return false;
	
}


public void home(){
	
	try {
		
		FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/Customer.fxml"));
		Parent root1=(Parent) fxmloder.load();
		
		Stage stage =new Stage();
		//stage.initStyle(StageStyle.TRANSPARENT);
		
	
		stage.setScene(new Scene(root1));
		stage.show();
		close();
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e);
	}
	
}

public void giftHistory(){
	
	try {
		
		FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/GiftHistory.fxml"));
		Parent root1=(Parent) fxmloder.load();
		
		Stage stage =new Stage();
		//stage.initStyle(StageStyle.TRANSPARENT);
		
	
		stage.setScene(new Scene(root1));
		stage.show();
		close();
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e);
	}
	
}

public void close() {
    Stage stage = (Stage) txtEmail.getScene().getWindow();
    stage.close();
}

static LocalDate currentDate;
int x=0;

public void datee(){
	   
	   Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {            
	        Calendar cal = Calendar.getInstance();
	        second = cal.get(Calendar.SECOND);
	        minute = cal.get(Calendar.MINUTE);
	        hour = cal.get(Calendar.HOUR);
	        day=cal.get(Calendar.DAY_OF_MONTH);
	        month=cal.get(Calendar.MONTH);
	        year=cal.get(Calendar.YEAR);
	      
	        	currentDate=LocalDate.parse(year + "-" + (month+1) + "-" +day);
	        	x++;
	        	if(x==1){
	        		cancelGiftAutomatically();
	        	}
	        	else if(x==2){
	        		return;
	        	}
	        	
	        	
	    
	    }),
	         new KeyFrame(Duration.seconds(1))
	    );
	    clock.setCycleCount(Animation.INDEFINITE);
	    clock.play();
}


public void cancelGiftAutomatically(){
	
	String expire;
	LocalDate d;
	
try {
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Connection cc=dbCon.mycon();
		Statement ss=cc.createStatement();
		ResultSet rsc=ss.executeQuery(" SELECT * FROM `gifttype` WHERE 1 ");
		
		
		while(rsc.next()){
		
			
			d=LocalDate.parse(rsc.getString("endDate"));
			if(currentDate.compareTo(d) > 0){
				
				Alert a11=new Alert(AlertType.WARNING);
		    	a11.setHeaderText(null);
		    	//a1.initStyle(StageStyle.UNDECORATED);
		    	a11.setContentText("Warning Gift Expired\n( Gift Type ->  "+rsc.getString("name")+" ) --- ( End Date -> "+rsc.getString("endDate")+" ) Gift type expired..Remove the gift type from the table");    		    	
		    	a11.show();
			
			}	
			else{
				
				System.out.println("not expired");
			}
							
		}
		
	} catch (Exception e) {
		System.out.println(e);
		System.out.println();
	}
	
	
}

}
