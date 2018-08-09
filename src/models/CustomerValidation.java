package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.dbCon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class CustomerValidation {
	
	public boolean name(String name){
		
		Pattern p=Pattern.compile("[a-zA-Z]+");
		Matcher m=p.matcher(name);
		
		if(m.find() && m.group().equals(name) ){
				
				return true;
			}
			else{
				Alert a1=new Alert(AlertType.WARNING);
		    	a1.setHeaderText(null);
		    	//a1.initStyle(StageStyle.UNDECORATED);
		    	a1.setContentText("Your Entered data: ("+name+") is not correct\nPlease enter the name without space");  
		    	a1.showAndWait();
		    	return false;
			}
		
		
	}
	
	public boolean nameEmpty(String name){
		
		if(name.equals("")){
			Alert a1=new Alert(AlertType.WARNING);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("You Entered empty data for Name field.. \nPlease enter the correct name");  
	    	a1.showAndWait();
	    	return false;
			
		}
		return true;
		
	}
	
public boolean mobileEmpty(String mobile){
		
		if(mobile.equals("")){
			Alert a1=new Alert(AlertType.WARNING);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("You Entered empty data for Mobile field.. \nPlease enter the correct mobile number");  
	    	a1.showAndWait();
	    	return false;
			
		}
		return true;
		
}

public boolean emailEmpty(String email){
	
	if(email.equals("")){
		Alert a1=new Alert(AlertType.WARNING);
    	a1.setHeaderText(null);
    	//a1.initStyle(StageStyle.UNDECORATED);
    	a1.setContentText("You Entered empty data for Email field.. \nPlease enter the correct email address");  
    	a1.showAndWait();
    	return false;
		
	}
	return true;
	
}

public boolean nicEmpty(String nic){
	
	if(nic.equals("")){
		Alert a1=new Alert(AlertType.WARNING);
    	a1.setHeaderText(null);
    	//a1.initStyle(StageStyle.UNDECORATED);
    	a1.setContentText("You Entered empty data for NIC field.. \nPlease enter the correct NOC number");  
    	a1.showAndWait();
    	return false;
		
	}
	return true;
	
}

public boolean addressEmpty(String address){
	
	if(address.equals("")){
		Alert a1=new Alert(AlertType.WARNING);
    	a1.setHeaderText(null);
    	//a1.initStyle(StageStyle.UNDECORATED);
    	a1.setContentText("You Entered empty data for Address field.. \nPlease enter the correct address");  
    	a1.showAndWait();
    	return false;
		
	}
	return true;
	
}

//*************Email Validation*******************

public Boolean email(String email) {
	
	Pattern p=Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	Matcher m=p.matcher(email);
	if(m.find() && m.group().equals(email)){
		
		return true;
	}
	else{
		Alert a1=new Alert(AlertType.WARNING);
    	a1.setHeaderText(null);
    	//a1.initStyle(StageStyle.UNDECORATED);
    	a1.setContentText("Your Entered data: ("+email+") is not correct\nPlease enter a valid Email address with correct syntax");  
    	a1.showAndWait();
	
    	return false;
	}

}


//*************NIC Validation*******************
public Boolean nic(String nic) {
	Pattern p=Pattern.compile("^[0-9]{9}[vVxX]$");
	Matcher m=p.matcher(nic);
	
	if(m.find() && m.group().equals(nic)){
		
		return true;
	}
	else{
		
		Alert a1=new Alert(AlertType.WARNING);
    	a1.setHeaderText(null);
    	//a1.initStyle(StageStyle.UNDECORATED);
    	a1.setContentText("Your Entered data: ("+nic+") is not correct\nPlease enter a valid national ID number with 'v' at the end");
    	a1.showAndWait();
    	
    	return false;
	}
	
	
}

public boolean checkCustomer(String id){
	
	try {
		
		Connection cc=dbCon.mycon();
		Statement ss=cc.createStatement();
		ResultSet rsc=ss.executeQuery(" SELECT * FROM `customer` WHERE mobile='"+id+"' ");
		
		while(rsc.next()){
			
			return true;
			
		}
		
	} catch (Exception e) {
		System.out.println(e);
	}
	
	
	return false;
}



public void updateCustomer(String address,String mobile2,String fname,String lname,String nic,String email,String gender){
	
	
	
	CustomerValidation c=new CustomerValidation();
	AllValidation a=new AllValidation();

	
	
	if( c.nameEmpty(fname) && c.nameEmpty(lname) &&  c.mobileEmpty(mobile2)  && c.nicEmpty(nic) &&  c.emailEmpty(email)  && c.addressEmpty(address) && c.name(fname) && c.name(lname) && a.validatePhone(mobile2) && c.email(email) && c.nic(nic)){
		
		
		Alert a1=new Alert(AlertType.CONFIRMATION);
    	a1.setHeaderText(null);
    	//a1.initStyle(StageStyle.UNDECORATED);
    	a1.setContentText("Do you want to Update this Customer??");    		    	
    	Optional<ButtonType> result = a1.showAndWait();
    	if (result.get() == ButtonType.OK){
    		
    		try {
				String fullname=fname+" "+lname;
				
				Connection c1=dbCon.mycon();
				Statement s1=c1.createStatement();
				
				
				ResultSet rs=s1.executeQuery("SELECT * FROM `customer` WHERE `mobile`='"+mobile2+"'");
				
				while(rs.next()){
					
					s1.executeUpdate(" UPDATE `customer` SET `address`='"+address+"',`name`='"+fullname+"',`nic`='"+nic+"',`email`='"+email+"',`gender`='"+gender+"' WHERE `mobile`='"+mobile2+"' ");
					
					
					Alert a2=new Alert(AlertType.INFORMATION);
			    	a2.setHeaderText(null);
			    	//a1.initStyle(StageStyle.UNDECORATED);
			    	a2.setContentText(mobile2+" coustomer details are sucessfully updated....");    		    	
			    	a2.showAndWait();
					
				}
					
					
			    	
			    	Alert a13=new Alert(AlertType.INFORMATION);
			    	a13.setHeaderText(null);
			    	//a1.initStyle(StageStyle.UNDECORATED);
			    	a13.setContentText("Can't update..\nNo customer found as"+fname);    		    	
			    	a13.showAndWait();
			    	return;
				
				
				
			} catch (Exception e) {
				// TODO: handle exception
				
			}
    		
    		
    	}
    	
    	
		
	}
	

	else{
	
			System.out.println("Not valid");
		
	}

}

public void removeCustomer(String mobileNo){
			
	Alert a1=new Alert(AlertType.CONFIRMATION);
	a1.setHeaderText(null);
	//a1.initStyle(StageStyle.UNDECORATED);
	a1.setContentText("Do you want to Remove "+mobileNo+" Customer??");    		    	
	Optional<ButtonType> result = a1.showAndWait();
	if (result.get() == ButtonType.OK){
		
		try {
		
			
			Connection c1=dbCon.mycon();
			Statement s1=c1.createStatement();
			
			
				
				s1.executeUpdate(" DELETE FROM `customer` WHERE `mobile`='"+mobileNo+"' ");
				
				
				Alert a2=new Alert(AlertType.INFORMATION);
		    	a2.setHeaderText(null);
		    	//a1.initStyle(StageStyle.UNDECORATED);
		    	a2.setContentText(mobileNo+" coustomer is sucessfully removed from the system....");    		    	
		    	a2.showAndWait();
		    	
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	


		}

	}
}
