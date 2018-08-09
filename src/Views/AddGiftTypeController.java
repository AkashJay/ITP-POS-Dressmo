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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.AddGiftType;
import models.CustomerTable;
import models.CustomerValidation;
import models.GiftTable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

public class AddGiftTypeController implements Initializable {
	
	
	@FXML
    private DatePicker dateStart;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private JFXTextField txtgiftName;

    @FXML
    private JFXTextField txtGiftAmount;
    
    
    @FXML
    private TableView<GiftTable> giftTable;

    @FXML
    private TableColumn<GiftTable, String> name;

    @FXML
    private TableColumn<GiftTable, String> amount;

    public ObservableList<GiftTable> list;
    
    
    private int minute;
   	private int hour;
   	private int second;
   	private int day;
   	private int month;
   	private int year;
   static LocalDate currentDate;
   int x=0;
   
   

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		AdTableValues();
		takeTableValue();
		datee();
		
	}
	
	
	   //*****************Catch the current Date****************   
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
		        		return;		//only run the current date once and catch the date and return from the method
		        	}
		        	
		        	System.out.println(currentDate);
		    
		    }),
		         new KeyFrame(Duration.seconds(1))
		    );
		    clock.setCycleCount(Animation.INDEFINITE);
		    clock.play();
	   }
	
	
	
	
	//******************Add gift Items****************
	public void addGiftType() {
		AddGiftType g=new AddGiftType();
		
		if( g.name(txtgiftName.getText()) && g.Amount(txtGiftAmount.getText())  && startDate() &&  endDate() && g.validateAmount(txtGiftAmount.getText()) && datecompaire() && dublicateName()){
			
			Alert a1=new Alert(AlertType.CONFIRMATION);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("Do you want to add "+txtgiftName.getText()+" gift type??");  
	    	Optional<ButtonType> result = a1.showAndWait();
	    	if (result.get() == ButtonType.OK){
	    		
	    		try {
					
					
					Connection c1=dbCon.mycon();
					Statement s1=c1.createStatement();
				
					
						//Insert Gift Details to table
						s1.executeUpdate(" INSERT INTO `gifttype`( `name`, `amount`, `startDate`, `endDate`) VALUES ('"+txtgiftName.getText()+"','"+txtGiftAmount.getText()+"','"+dateStart.getValue()+"','"+dateEnd.getValue()+"') ");
												
						Alert a2=new Alert(AlertType.INFORMATION);
				    	a2.setHeaderText(null);
				    	//a1.initStyle(StageStyle.UNDECORATED);
				    	a2.setContentText(txtgiftName.getText()+" Gift type added successfully..");    		    	
				    	a2.showAndWait();
				    	AdTableValues();
				    	clear();
									
					
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				}
	    		
	    		
	    	}
	    	else if (result.get() == ButtonType.CANCEL){
	    		Alert a2=new Alert(AlertType.CONFIRMATION);
		    	a2.setHeaderText(null);
		    	//a1.initStyle(StageStyle.UNDECORATED);
		    	a2.setContentText(txtgiftName.getText()+" Gift type not added...Do u want to clear the fields...");    		    	
		    	Optional<ButtonType> result1 = a2.showAndWait();
		    	if (result1.get() == ButtonType.OK){
		    		
		    		clear();
		    	}
	    		
	    		
	    	}
		}

	}
	
	
	//**************Start date field empty checking validation*********
	public boolean startDate(){
		
		if(dateStart.getValue() == null){
			Alert a1=new Alert(AlertType.WARNING);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("You Entered empty data for Start date field.. \nPlease chosse a correct Start date for the Start date field..");  
	    	a1.showAndWait();
	    	return false;
			
		}
		return true;
		
	}
	
	
	//**************Clear all fields*********
	public void clear(){
		txtGiftAmount.setText("");
		txtgiftName.setText("");
		dateEnd.setValue(null);
		dateStart.setValue(null);
		txtgiftName.setEditable(true);
		txtgiftName.requestFocus();
		
		
	}

	//**************End date field empty checking validation*********
	public boolean endDate(){
		
		if(dateEnd.getValue() == null){
			Alert a1=new Alert(AlertType.WARNING);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("You Entered empty data for end date field.. \nPlease choose a correct end date for the end date field..");  
	    	a1.showAndWait();
	    	return false;
			
		}
		return true;
		
	}
	
	
	//**************Compare Start date < End date validation*********
	public boolean datecompaire(){
		
		LocalDate dS=dateStart.getValue();
		LocalDate dE=dateEnd.getValue();
		
		if(dE.compareTo(dS) > 0){
			return true;
		}
		else{
			Alert a1=new Alert(AlertType.WARNING);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("You entered incorect data for the start date and end date\n End date must be bigger than the start date..");  
	    	a1.showAndWait();
			return false;
		}
		
		
		
	}
	
	
	//**************Add Gift details in the table*********
	public void AdTableValues() {

		try {
			
			Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `gifttype` WHERE 1 ");
			
			list=FXCollections.observableArrayList();
			while(rsc.next()){
				
			
				
				list.add(new GiftTable(rsc.getString("name"), rsc.getString("amount"), rsc.getString("startDate"), rsc.getString("endDate")));
										
								
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	
		name.setCellValueFactory(new PropertyValueFactory<GiftTable, String> ("name"));
		amount.setCellValueFactory(new PropertyValueFactory<GiftTable, String> ("amount"));
		
		
		giftTable.setItems(list);

	}
	
	//**************Take table values of a selected row*********
	public void takeTableValue() {
		
		
		// TODO Auto-generated method stub
		giftTable.setOnMouseClicked(e -> {
			
			txtgiftName.setEditable(false);
			LocalDate dS;
			LocalDate dE;
			GiftTable a=giftTable.getItems().get(giftTable.getSelectionModel().getSelectedIndex());
			txtgiftName.setText(a.getName());
			txtGiftAmount.setText(a.getAmount());
			dS=LocalDate.parse(a.getStartDate());
			dE=LocalDate.parse(a.getEndDate());
			dateEnd.setValue(dE);
			dateStart.setValue(dS);
			
		});
	}
	
	
	
//**************Delete Gift Type*********
public void deleteGift(){
		
		
		if(txtgiftName.getText().isEmpty()){
			Alert a1=new Alert(AlertType.WARNING);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("To remove a Gift type \n Search a Gift type first or give a valid Gift type name in name field");    		    	
	    	a1.showAndWait();
			
		}
		else{
			try {
				
				Alert a1=new Alert(AlertType.CONFIRMATION);
				a1.setHeaderText(null);
				//a1.initStyle(StageStyle.UNDECORATED);
				a1.setContentText("Do you want to Remove "+txtgiftName.getText()+" gift type??");    		    	
				Optional<ButtonType> result = a1.showAndWait();
				if (result.get() == ButtonType.OK){
					if(giftNamecheck()){
						
						Connection cc=dbCon.mycon();
						Statement ss=cc.createStatement();
						ss.executeUpdate(" DELETE FROM `gifttype` WHERE `name`='"+txtgiftName.getText()+"' ");
						
						
						Alert a2=new Alert(AlertType.CONFIRMATION);
				    	a2.setHeaderText(null);
				    	//a1.initStyle(StageStyle.UNDECORATED);
				    	a2.setContentText(txtgiftName.getText()+" gift type is sucessfully removed from the system....\nDo you want to delete the customers related to this gift type");    		    	
				    	Optional<ButtonType> resultaa =a2.showAndWait();
				    	
				    	if(resultaa.get() == ButtonType.OK){
				    		
				    		deletecus();
							Alert a222=new Alert(AlertType.INFORMATION);
					    	a222.setHeaderText(null);
					    	//a1.initStyle(StageStyle.UNDECORATED);
					    	a222.setContentText("Customers related to "+txtgiftName.getText()+" are also deleted.." );    		    	
					    	a222.showAndWait();
							
				    	}
					}
					else{
						Alert a122=new Alert(AlertType.WARNING);
				    	a122.setHeaderText(null);
				    	//a1.initStyle(StageStyle.UNDECORATED);
				    	a122.setContentText("Invalid Gift name...\n"+txtgiftName.getText()+" type of gift not fount..Enter a correct gift name");    		    	
				    	a122.showAndWait();
						
					}
				}
				
				
				
			} catch (Exception e) {
				System.out.println(e);
			}
			
			clear();
			AdTableValues();
			
		}
	}

//**************Check For Gift name Dublication*********
public boolean giftNamecheck(){
	
	try {
		
		Connection cc=dbCon.mycon();
		Statement ss=cc.createStatement();
		ResultSet rs=ss.executeQuery(" SELECT * FROM `gifttype` WHERE `name`='"+txtgiftName.getText()+"' ");
		while(rs.next()){
			return true;
			
		}
		
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	return false;
	
}


//**************Check For Gift name Dublication*********
public boolean dublicateName() {  
	try {
		
		Connection cc=dbCon.mycon();
		Statement ss=cc.createStatement();
		ResultSet rs=ss.executeQuery(" SELECT * FROM `gifttype` WHERE `name`='"+txtgiftName.getText()+"' ");
		while(rs.next()){
			
			Alert a122=new Alert(AlertType.WARNING);
	    	a122.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a122.setContentText(txtgiftName.getText()+" gift type is already in the system..\nGive a different gift name..");    		    	
	    	a122.showAndWait();
			return false;			
		}			
		
	} catch (Exception e){    				
		System.out.println(e);		
	}	
	
	return true;
}


//**************Update gift type details*********
public void updateGift(){
	
	AddGiftType g=new AddGiftType();
	
		g.updateGift(txtgiftName.getText(), txtGiftAmount.getText(),dateStart.getValue(),dateEnd.getValue());
		AdTableValues();
		clear();

}

//**************Gift Expire Warning Method*********
public void cancelGiftAutomatically(){
	
	String expire;
	LocalDate d;
	
try {
	
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


//**************Delete Customers related to expired Gift Type*********
	public void deletecus(){
		String type=txtgiftName.getText()+" "+txtGiftAmount.getText();
		
		try {
			Connection ca1=dbCon.mycon();
			Statement sa1=ca1.createStatement();
			sa1.executeUpdate(" DELETE FROM `gift` WHERE `type`='"+type+"' ");
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	}
