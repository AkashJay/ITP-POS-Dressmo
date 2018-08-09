package Views;

import java.io.Closeable;
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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.dbCon;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.AllValidation;
import models.AuditTable;
import models.CustomerTable;
import models.CustomerValidation;
import models.GiftTable;
import models.posTable;
import models.returnTable;


public class CustomerController implements Initializable {
	
	
	@FXML
    private JFXTextField txtFname;

    @FXML
    private JFXTextField txtLname;

    @FXML
    private JFXTextField txtMobileNo;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private DatePicker dob;

    @FXML
    private RadioButton rMale;

    @FXML
    private RadioButton rFemale;
    
    @FXML
    private JFXTextField filterField;
    
    @FXML
    private Label lblPoints;
    
    @FXML
    private TableView<CustomerTable> customerTable2;
    
    @FXML
    private TableColumn<CustomerTable, String> mobile;

    @FXML
    private TableColumn<CustomerTable, String> name;

    @FXML
    private TableColumn<CustomerTable, String> totalpoints;
    
    @FXML
    private TableColumn<CustomerTable, String> email;
    
    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;
    
    @FXML
    private Label lblName;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblBranch;

    @FXML
    private Label lblNic;

    @FXML
    private Label lblPosition;
    
    @FXML
    private JFXButton btn1;

    @FXML
    private JFXButton btn2;
    
    
    @FXML
    private TableColumn<AuditTable, String> id;

    @FXML
    private TableColumn<AuditTable, String> eid;

    @FXML
    private TableColumn<AuditTable, String> ename;

    @FXML
    private TableColumn<AuditTable, String> date;

    @FXML
    private TableColumn<AuditTable, String> loginTime;

    @FXML
    private TableColumn<AuditTable, String> changeTime;

    @FXML
    private TableColumn<AuditTable, String> discription;

    @FXML
    private TableColumn<AuditTable, String> status;
    
    @FXML
    private TableView<AuditTable> auditTable;
    
    String date1;
    String gender;

    public ObservableList<CustomerTable> list;
    public ObservableList<AuditTable> listAdit;
    
    private int minute;
   	private int hour;
   	private int second;
   	private int day;
   	private int month;
   	private int year;
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
   
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		btn1.setDisable(true);
		btn2.setDisable(true);
		lblPoints.setVisible(false);
		addTableValue();
		takeTableValue();
		filter();
		datee();
		addAuditTableValue();
		
		
		
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {            
	        Calendar cal = Calendar.getInstance();
	        second = cal.get(Calendar.SECOND);
	        minute = cal.get(Calendar.MINUTE);
	        hour = cal.get(Calendar.HOUR);
	        day=cal.get(Calendar.DAY_OF_MONTH);
	        month=cal.get(Calendar.MONTH);
	        year=cal.get(Calendar.YEAR);
	        if(hour<10){
	        	lblTime.setText("0"+hour + ":" + (minute) );
		        lblDate.setText(year + "-" + (month+1) + "-" +day );
	        	
	        }
	        else{
	        	
	        	lblTime.setText(hour + ":" + (minute) );
		        lblDate.setText(year + "-" + (month+1) + "-" +day );
	        }
	        
	       
	        
	    }),
	         new KeyFrame(Duration.seconds(1))
	    );
	    clock.setCycleCount(Animation.INDEFINITE);
	    clock.play();
		
		
		
    }

		
		
	
		
	


	
	//**************Check date of birth field empty*********
	public boolean dobEmpty(){
		
		if(dob.getValue() == null){
			Alert a1=new Alert(AlertType.WARNING);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("Input error..\nBirthday field is require..");  
	    	a1.showAndWait();
	    	return false;
			
		}
		return true;
		
	}
	
	
	//**************Register New customer*********
	public void registerCustomer(){
		
		
				
		CustomerValidation c=new CustomerValidation();
		AllValidation a=new AllValidation();
	
		
		
		if( c.nameEmpty(txtFname.getText()) && c.nameEmpty(txtLname.getText()) &&  c.mobileEmpty(txtMobileNo.getText())  && c.nicEmpty(txtNic.getText()) &&  c.emailEmpty(txtEmail.getText()) && this.dobEmpty() && c.addressEmpty(txtAddress.getText()) && c.name(txtLname.getText()) && c.name(txtFname.getText()) && a.validatePhone(txtMobileNo.getText()) && c.email(txtEmail.getText()) && c.nic(txtNic.getText())){
			
			
			Alert a1=new Alert(AlertType.CONFIRMATION);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("Do you want to Register this Customer??");    		    	
	    	Optional<ButtonType> result = a1.showAndWait();
	    	if (result.get() == ButtonType.OK){
	    		
	    		try {
					String fullname=txtFname.getText()+" "+txtLname.getText();
					
					Connection c1=dbCon.mycon();
					Statement s1=c1.createStatement();
					if(c.checkCustomer(txtMobileNo.getText())){
						
						Alert a11=new Alert(AlertType.WARNING);
				    	a11.setHeaderText(null);
				    	//a1.initStyle(StageStyle.UNDECORATED);
				    	a11.setContentText(txtMobileNo.getText()+" customer alredy registered...\n You can't register a customer with existing mobile number.. ");    		    	
				    	a11.showAndWait();
				    	
				    	return;
					}
					else{
						
						s1.executeUpdate(" INSERT INTO `customer`( `address`, `mobile`, `name`, `nic`, `email`, `dateOfBirth`, `gender`,`totalpoints`) VALUES ('"+txtAddress.getText()+"','"+txtMobileNo.getText()+"','"+fullname+"','"+txtNic.getText()+"','"+txtEmail.getText()+"','"+dob.getValue()+"','"+gender+"',0) ");
						System.out.println("customer added");
						
						Alert a2=new Alert(AlertType.INFORMATION);
				    	a2.setHeaderText(null);
				    	//a1.initStyle(StageStyle.UNDECORATED);
				    	AllValidation a11=new AllValidation();
				    	String msg="You have been sucsessfully registered as a loyalty member in Dressmo..\nUse ur moble no : "+txtMobileNo.getText()+" to collect points..\nThank you,Dressmo";
				    	String number=txtMobileNo.getText();
				    	a11.sms(msg, number);
				    	a2.setContentText(txtMobileNo.getText()+" new customer registered successfully..");    		    	
				    	a2.showAndWait();
				    	addTableValue();
				    	
				    	Connection c11=dbCon.mycon();
						Statement s11=c11.createStatement();
						String time=hour+"-"+minute+"-"+second;
						String s="Sucsessfull";
						String dis="New customer registered";
						s11.executeUpdate("INSERT INTO `audit`(`eid`, `ename`, `date`, `loginTime`, `changeTime`, `discription`, `status`) VALUES ('"+EID+"' ,'"+lblName.getText()+"' ,'"+lblDate.getText()+"' ,'"+time+"' ,'"+time+"' ,'"+dis+"','"+s+"')");
				    	clear();
				    	filter();
				    	addAuditTableValue();
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
		    	a2.setContentText(txtMobileNo.getText()+" new customer registration cancled\n Do you want to clear the fields");    		    	
		    	Optional<ButtonType> result1 = a2.showAndWait();
		    	if (result1.get() == ButtonType.OK){
		    		
		    		clear();
		    	}
	    		
	    		
	    	}
	    	
			
		}
		
	
		else{
		
				System.out.println("Not valid");
			
		}

	}
	
	//**************Open add gift type interface*********
	public void openAddGift(ActionEvent e){
		
		try { 
			try {
				
				Connection c11=dbCon.mycon();
				Statement s11=c11.createStatement();
				String time=hour+"-"+minute+"-"+second;
				String s="Sucsessfull";
				String dis="Add gift interface opened..";
				s11.executeUpdate("INSERT INTO `audit`(`eid`, `ename`, `date`, `loginTime`, `changeTime`, `discription`, `status`) VALUES ('"+EID+"' ,'"+lblName.getText()+"' ,'"+lblDate.getText()+"' ,'"+time+"' ,'"+time+"' ,'"+dis+"','"+s+"')");
				addAuditTableValue();
				
			} catch (Exception e1) {
				// TODO: handle exception
				System.out.println(e1);
			}
			
			//Open the home window
			FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/AddGiftType.fxml"));
			Parent root1=(Parent) fxmloder.load();
			
			Stage stage =new Stage();
			//stage.initStyle(StageStyle.TRANSPARENT);
			//((ccController)fxmloder.getController()).setBillNo(txtBillNo.getText(),grosTotal);
		
			stage.setScene(new Scene(root1));
			stage.show();
			//close();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//**************Radio button validation*********
	public void male(ActionEvent e) {
	 	
	 	gender="male";
	 	rFemale.setSelected(false);
	}
	public void female(ActionEvent e) {
	 	
	 	gender="female";
	 	rMale.setSelected(false);
	}
	
	
	
		
	
	//**************Clear all fields*********
	public void clear() {
		// TODO Auto-generated method stub
		
		txtAddress.clear();
		txtEmail.clear();
		txtFname.clear();
		txtLname.clear();
		txtMobileNo.clear();
		txtNic.clear();
		dob.setValue(null);
		rFemale.setSelected(false);
		rMale.setSelected(false);
		lblPoints.setVisible(false);
		txtMobileNo.setEditable(true);
	}
	
	
	
	
	//**************Open Search interface*********
	public void openSearch() {
		try { 
			//Open the home window
			FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/CustomerSearch.fxml"));
			Parent root1=(Parent) fxmloder.load();
			
			Stage stage =new Stage();
			stage.initStyle(StageStyle.TRANSPARENT);
			//((ccController)fxmloder.getController()).setBillNo(txtBillNo.getText(),grosTotal);
		
			stage.setScene(new Scene(root1));
			stage.show();
			
			close();
			
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	
	
	//**************Open selected gift interface*********
	public void openSelectedGift() {
		try { 
			
			
			try {
				
				Connection c11=dbCon.mycon();
				Statement s11=c11.createStatement();
				String time=hour+"-"+minute+"-"+second;
				String s="Sucsessfull";
				String dis="Selected gift history opened..";
				s11.executeUpdate("INSERT INTO `audit`(`eid`, `ename`, `date`, `loginTime`, `changeTime`, `discription`, `status`) VALUES ('"+EID+"' ,'"+lblName.getText()+"' ,'"+lblDate.getText()+"' ,'"+time+"' ,'"+time+"' ,'"+dis+"','"+s+"')");
				addAuditTableValue();
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			//Open the home window
			FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/selectedGift.fxml"));
			Parent root1=(Parent) fxmloder.load();
			
			Stage stage =new Stage();
			//stage.initStyle(StageStyle.TRANSPARENT);
			//((ccController)fxmloder.getController()).setBillNo(txtBillNo.getText(),grosTotal);
		
			stage.setScene(new Scene(root1));
			stage.show();
			
			
			
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	

	
	
	//**************Open common gift interface*********
public void openCommonGift() {
		try { 
			
			try {
				
				Connection c11=dbCon.mycon();
				Statement s11=c11.createStatement();
				String time=hour+"-"+minute+"-"+second;
				String s="Sucsessfull";
				String dis="Common gift interface opened..";
				s11.executeUpdate("INSERT INTO `audit`(`eid`, `ename`, `date`, `loginTime`, `changeTime`, `discription`, `status`) VALUES ('"+EID+"' ,'"+lblName.getText()+"' ,'"+lblDate.getText()+"' ,'"+time+"' ,'"+time+"' ,'"+dis+"','"+s+"')");
				addAuditTableValue();
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			//Open the home window
			FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/CommonGift.fxml"));
			Parent root1=(Parent) fxmloder.load();
			
			Stage stage =new Stage();
			//stage.initStyle(StageStyle.TRANSPARENT);
			//((ccController)fxmloder.getController()).setBillNo(txtBillNo.getText(),grosTotal);
		
			stage.setScene(new Scene(root1));
			stage.show();
			
			
			
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	


//**************Open gift history interface*********
	public void openGiftHistory() {
		try { 
			
			
			try {
				
				Connection c11=dbCon.mycon();
				Statement s11=c11.createStatement();
				String time=hour+"-"+minute+"-"+second;
				String s="Sucsessfull";
				String dis="Gift history interface opened..";
				s11.executeUpdate("INSERT INTO `audit`(`eid`, `ename`, `date`, `loginTime`, `changeTime`, `discription`, `status`) VALUES ('"+EID+"' ,'"+lblName.getText()+"' ,'"+lblDate.getText()+"' ,'"+time+"' ,'"+time+"' ,'"+dis+"','"+s+"')");
				addAuditTableValue();
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			
			//Open the home window
			FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/GiftHistory.fxml"));
			Parent root1=(Parent) fxmloder.load();
			
			Stage stage =new Stage();
			//stage.initStyle(StageStyle.TRANSPARENT);
			//((ccController)fxmloder.getController()).setBillNo(txtBillNo.getText(),grosTotal);
		
			stage.setScene(new Scene(root1));
			stage.show();
			
			
			
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	
	
	//**************Close the current window*********
	public void close() {
	    Stage stage = (Stage) txtFname.getScene().getWindow();
	    stage.close();
	}

	
	
	
	//**************Search customer and set values to text fields*********
	public void searchCustomer(String fname,String lname,String mobile,String nic,String email,LocalDate dob,String address,String gender,String t){
		
		
		//close();
		
		
		lblPoints.setVisible(true);
		lblPoints.setText("Total Points : "+t);
		txtFname.setText(fname);
		txtLname.setText(lname);
		txtMobileNo.setText(mobile);
		txtNic.setText(nic);
		txtEmail.setText(email);
		this.dob.setValue(dob);
		txtAddress.setText(address);
		
		
		if(gender.equals("male")){
			rMale.setSelected(true);
			rFemale.setSelected(false);
		}
		if(gender.equals("female")){
			rMale.setSelected(false);
			rFemale.setSelected(true);
		}
		
		filter();
		
	}
	
	
	
	//**************Add values to customer table*********
	
	public void addTableValue(){
		
		try {
			
			Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `customer` WHERE 1 ");
			
			list=FXCollections.observableArrayList();
			while(rsc.next()){
				System.out.println(rsc.getString("address"));
			
				
				list.add(new CustomerTable(rsc.getString("address"), rsc.getString("mobile"), rsc.getString("name"), rsc.getString("nic"), rsc.getString("email"), rsc.getString("dateOfBirth"), rsc.getString("gender"), rsc.getString("totalpoints")));
										
								
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	
		mobile.setCellValueFactory(new PropertyValueFactory<CustomerTable, String> ("mobile"));
		name.setCellValueFactory(new PropertyValueFactory<CustomerTable, String> ("name"));
		totalpoints.setCellValueFactory(new PropertyValueFactory<CustomerTable, String> ("totalpoints"));
		email.setCellValueFactory(new PropertyValueFactory<CustomerTable, String> ("email"));
		
		customerTable2.setItems(list);
		filter();
		
	}
	
	
	
	//**************Take selected customer details in the table*********
	public void takeTableValue() {
		// TODO Auto-generated method stub
		customerTable2.setOnMouseClicked(e -> {
			
			CustomerTable a=customerTable2.getItems().get(customerTable2.getSelectionModel().getSelectedIndex());
			String mobile1=a.getMobile();
		
			String n="akash";
			LocalDate d;
			
			try {
	        	
	        	Connection cc=dbCon.mycon();
				Statement ss=cc.createStatement();
				ResultSet rsc=ss.executeQuery(" SELECT * FROM `customer` WHERE mobile='"+mobile1+"' ");
				
				while(rsc.next()){
					
					 d=LocalDate.parse(rsc.getString("dateOfBirth"));
					n=rsc.getString("name");
					String[] parts = n.split(" ");
					txtFname.setText(parts[0]); 
					txtLname.setText(parts[1]);
					txtMobileNo.setText(rsc.getString("mobile"));
					txtMobileNo.setEditable(false);
					txtNic.setText(rsc.getString("nic"));
					txtEmail.setText(rsc.getString("email"));
					dob.setValue(d);
					txtAddress.setText(rsc.getString("address"));
					gender=rsc.getString("gender");
					
					if(gender.equals("male")){
						rMale.setSelected(true);
						rFemale.setSelected(false);
					}
					if(gender.equals("female")){
						rMale.setSelected(false);
						rFemale.setSelected(true);
					}
					
					lblPoints.setVisible(true);
					lblPoints.setText("Total Points : "+rsc.getString("totalpoints"));
					addTableValue();
					filter();
					
				
				}
				
				
				
				
			} catch (Exception e1) {
				System.out.println(e1);
			}
			
		});
		
		
	}
	
	
	
	//**************Update customer details*********
	public void updateCus(){
		
		CustomerValidation c=new CustomerValidation();
		c.updateCustomer(txtAddress.getText(), txtMobileNo.getText(), txtFname.getText(), txtLname.getText(), txtNic.getText(), txtEmail.getText(), gender);
		
		
		try {
			Connection c11=dbCon.mycon();
			Statement s11=c11.createStatement();
			String time=hour+"-"+minute+"-"+second;
			String s="Sucsessfull";
			String dis="Customer details updated";
			s11.executeUpdate("INSERT INTO `audit`(`eid`, `ename`, `date`, `loginTime`, `changeTime`, `discription`, `status`) VALUES ('"+EID+"' ,'"+lblName.getText()+"' ,'"+lblDate.getText()+"' ,'"+time+"' ,'"+time+"' ,'"+dis+"','"+s+"')");
			addAuditTableValue();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		addTableValue();
		filter();
	
	}
	
	
	
	//**************Delete customer from the system*********
	public void deleteCustomer(){
		
		
		if(txtMobileNo.getText().isEmpty()){
			Alert a1=new Alert(AlertType.WARNING);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("To remove a customer \n Search a customer first or give a valid customer number");    		    	
	    	a1.showAndWait();
			
		}
		else{
			
			CustomerValidation c=new CustomerValidation();
			c.removeCustomer(txtMobileNo.getText());
			
			try {
				
				Connection c11=dbCon.mycon();
				Statement s11=c11.createStatement();
				String time=hour+"-"+minute+"-"+second;
				String s="Sucsessfull";
				String dis="Customer removed..";
				s11.executeUpdate("INSERT INTO `audit`(`eid`, `ename`, `date`, `loginTime`, `changeTime`, `discription`, `status`) VALUES ('"+EID+"' ,'"+lblName.getText()+"' ,'"+lblDate.getText()+"' ,'"+time+"' ,'"+time+"' ,'"+dis+"','"+s+"')");
				addAuditTableValue();
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			
			clear();
			addTableValue();
			filter();
		}
	}
	
	
	
	
	//**************Filter customer details by mobile number*********
	public void filter(){
		
		  // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<CustomerTable> filteredData = new FilteredList<>(list, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getMobile().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } 
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<CustomerTable> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(customerTable2.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        customerTable2.setItems(sortedData);
		
	}
	
	
	//**************Check expired gifts*********
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
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		}
		
		
	}
	
			
		String EID;	
		
		
		//**************Set logged in employee id*********
	public void setEmpdetails(String id){
		
		EID=id;
		System.out.println(EID+"    vdfvfvdvdfdbdvgfdbfvgv");
		
		try {
			
			Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `add111` WHERE `eid`='"+EID+"' ");
			
			list=FXCollections.observableArrayList();
			while(rsc.next()){
				
				lblName.setText(rsc.getString("fullname"));
				lblAddress.setText(rsc.getString("address"));
				lblBranch.setText("Branch : Malabe");
				lblNic.setText(rsc.getString("nic"));
				lblPosition.setText("Position : "+rsc.getString("position"));
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	//**************Logout from customer interface*********
	public void logOut(){
		
		try { 
			//Open the home window
			FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/LoginPOS.fxml"));
			Parent root1=(Parent) fxmloder.load();
			
			Stage stage =new Stage();
			stage.initStyle(StageStyle.TRANSPARENT);
			//stage.setMaximized(true);
			stage.setScene(new Scene(root1));
			stage.show();
			
			//Close the current window(call above method)
			close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	//**************Add values to audit table*********
public void addAuditTableValue(){
		
		try {
			
			Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `audit` WHERE 1 ");
			System.out.println("Audit");
			listAdit=FXCollections.observableArrayList();
			while(rsc.next()){
				
			
				
				listAdit.add(new AuditTable(rsc.getString("id"), rsc.getString("eid"), rsc.getString("ename"), rsc.getString("date"), rsc.getString("loginTime"), rsc.getString("changeTime"), rsc.getString("discription"), rsc.getString("status")));
										
								
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	
		id.setCellValueFactory(new PropertyValueFactory<AuditTable, String> ("id"));
		eid.setCellValueFactory(new PropertyValueFactory<AuditTable, String> ("eid"));
		ename.setCellValueFactory(new PropertyValueFactory<AuditTable, String> ("ename"));
		date.setCellValueFactory(new PropertyValueFactory<AuditTable, String> ("date"));
		loginTime.setCellValueFactory(new PropertyValueFactory<AuditTable, String> ("loginTime"));
		changeTime.setCellValueFactory(new PropertyValueFactory<AuditTable, String> ("changeTime"));
		discription.setCellValueFactory(new PropertyValueFactory<AuditTable, String> ("discription"));
		status.setCellValueFactory(new PropertyValueFactory<AuditTable, String> ("status"));
		
		auditTable.setItems(listAdit);
	
		
	}


}
