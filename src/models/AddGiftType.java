package models;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.dbCon;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AddGiftType {
	
	public void addType() {
		

	}
	
	
	
	
	public boolean name(String name){
		
		if(name.equals("")){
			Alert a1=new Alert(AlertType.WARNING);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("You Entered empty data for Name field.. \nPlease enter the correct Name for the Name field..");  
	    	a1.showAndWait();
	    	return false;
			
		}
		return true;
		
	}
	
public boolean Amount(String amount){
		
		if(amount.equals("")){
			Alert a1=new Alert(AlertType.WARNING);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("You Entered empty data for Amount field.. \nPlease enter the correct Amount for the amount field..");  
	    	a1.showAndWait();
	    	return false;
			
		}
		return true;
		
	}

public Boolean validateAmount(String ammount) {
	
	Pattern p=Pattern.compile("[0-9]+");
	Matcher m=p.matcher(ammount);
	
	if(m.find() && m.group().equals(ammount)){
		
		return true;
	}
	else{
		Alert a2=new Alert(AlertType.WARNING);
		a2.setHeaderText(null);
		//a2.initStyle(StageStyle.UNDECORATED);
		a2.setContentText("Your Entered data: ("+ammount+") is not correct\nAmount can only have a number(digits)..cam't have characters...");    		    	
		a2.showAndWait();
    
    	
    	return false;
	}
	
	
}

public void updateGift(String name,String amount,LocalDate dS,LocalDate dE){
	
	
	
	if(name.equals("")){
		Alert a1=new Alert(AlertType.CONFIRMATION);
    	a1.setHeaderText(null);
    	//a1.initStyle(StageStyle.UNDECORATED);
    	a1.setContentText("Before updating u have to search the gift..\n please search first and then update the details");    		    	
    	a1.showAndWait();
		
		
	}
	else{
		if(name(name) && Amount(amount) && validateAmount(amount) && datecompaire(dS, dE)){
			
			
			Alert a1=new Alert(AlertType.CONFIRMATION);
	    	a1.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a1.setContentText("Do you want to Update this Gift Type??");    		    	
	    	Optional<ButtonType> result = a1.showAndWait();
	    	if (result.get() == ButtonType.OK){
	    		
	    		try {
					
					
					Connection c1=dbCon.mycon();
					Statement s1=c1.createStatement();
					
					
						
						s1.executeUpdate("UPDATE `gifttype` SET `name`='"+name+"',`amount`='"+amount+"',`startDate`='"+dS+"',`endDate`='"+dE+"' WHERE `name`='"+name+"' ");
						
						
						Alert a2=new Alert(AlertType.INFORMATION);
				    	a2.setHeaderText(null);
				    	//a1.initStyle(StageStyle.UNDECORATED);
				    	a2.setContentText(name+" gift type details are sucessfully updated....");    		    	
				    	a2.showAndWait();
				    	
					
					
					
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				}
	    		
	    		
	    	}
		
		
	}

	
	
	
    	
    	
		
	}
	

	

}


public boolean datecompaire(LocalDate ds,LocalDate de){
	
	
	
	if(de.compareTo(ds) > 0){
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




}
