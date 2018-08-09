package Views;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.dbCon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.AllValidation;

public class CustomerSearchController implements Initializable {
	
	
	@FXML
    private JFXTextField txtcusMobile;
	
	String fname;
	String lname;
	String mobile;
	String nic;             
	String email;
	String dob;
	String address;
	String gender;
	String totpoints;
	LocalDate d;
	
	
public boolean customerNo(String no){
	
	String n="akash";
		
		try {
        	
        	Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `customer` WHERE mobile='"+no+"' ");
			
			while(rsc.next()){
				
				 d=LocalDate.parse(rsc.getString("dateOfBirth"));
				n=rsc.getString("name");
				String[] parts = n.split(" ");
				fname = parts[0]; 
				lname = parts[1];
				mobile=rsc.getString("mobile");
				nic=rsc.getString("nic");
				email=rsc.getString("email");
				//dob=rsc.getString("dateOfBirth");
				address=rsc.getString("address");
				gender=rsc.getString("gender");
				totpoints=rsc.getString("totalpoints");
				
				return true;
			
			}
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
		
		
	}


public void close() {
    Stage stage = (Stage) txtcusMobile.getScene().getWindow();
    stage.close();
}


public void focusCustomer(){
	
	this.txtcusMobile.setOnKeyPressed(e->{
		
		if (e.getCode() == KeyCode.ESCAPE)  { 
			
			try {
				home();
				close();
				
				
				
				
			} catch (Exception e2) {
				System.out.println(e2);
				// TODO: handle exception
			}
		}
		
		if (e.getCode() == KeyCode.ENTER)  {    	  		
			try { 
				if(txtcusMobile.getText().isEmpty()){
					
			    	
					txtcusMobile.requestFocus();
					
				}
				else{
				
					AllValidation a=new AllValidation();
					if(a.validatePhone(txtcusMobile.getText())){
						if(customerNo(txtcusMobile.getText())){
							
							FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/Customer.fxml"));
		    				Parent root1=(Parent) fxmloder.load();
		    				
		    				Stage stage =new Stage();
		    				//stage.initStyle(StageStyle.TRANSPARENT);
		    				((CustomerController)fxmloder.getController()).searchCustomer(fname, lname, mobile, nic, email, d, address, gender,totpoints);;
		    			
		    				stage.setScene(new Scene(root1));
		    				stage.show();
							
							
							
	    					close();
						}
						else{
							Alert a2=new Alert(AlertType.INFORMATION);
							a2.setHeaderText(null);
							//a2.initStyle(StageStyle.UNDECORATED);
							a2.setContentText("Invalid customer number...\n"+txtcusMobile.getText()+" is not a registered customer..");    		    	
							a2.showAndWait();
						}
						
						
					}
					
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
	});   	
	
}


@Override
public void initialize(URL location, ResourceBundle resources) {
	// TODO Auto-generated method stub
	focusCustomer();
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


}
