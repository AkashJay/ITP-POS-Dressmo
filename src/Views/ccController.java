package Views;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.dbCon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.AllValidation;
import models.BillPrint;
import models.PosHome;
import models.AllValidation;

public class ccController implements Initializable {

	String totPoints;
	String p;
	double newPoint;
	
	@FXML
    private JFXTextField txtcusID;
	
	String billNo;
	
	
	//**************Setting Bill No and the total points earned for the bill*********
	public void setBillNo(String a,double p) {
		billNo=a;
		newPoint=p;
	
		
	}
	

	//**************Things Happen When Focus on phone number field*********
	 public void focusCustomer(){
	    	
	    	this.txtcusID.setOnKeyPressed(e->{
	    		
	    		if (e.getCode() == KeyCode.ESCAPE)  { 
	    			
	    			try {
	    				close();
	    				
						
					} catch (Exception e2) {
						System.out.println(e2);
						// TODO: handle exception
					}
	    		}
	    		
	    		if (e.getCode() == KeyCode.ENTER)  {    	  		
	    			try { 
	    				if(txtcusID.getText().isEmpty()){
	    					
	    			    	
	    					txtcusID.requestFocus();
	    					
	    				}
	    				else{
	    				
	    					AllValidation a=new AllValidation();
	    					if(a.validatePhone(txtcusID.getText())){
	    						if(a.customerNo(txtcusID.getText())){
	    							
	    							Connection c1=dbCon.mycon();
			    					Statement s1=c1.createStatement();
			    					System.out.println(billNo+txtcusID.getText());
			    					
			    					s1.executeUpdate(" UPDATE `bill` SET `cusNo`='"+txtcusID.getText()+"' WHERE billNo='"+billNo+"' ");
			    					this.addCusPoints(txtcusID.getText());
			    					close();
	    						}
	    						else{
	    							Alert a2=new Alert(AlertType.INFORMATION);
	    							a2.setHeaderText(null);
	    							//a2.initStyle(StageStyle.UNDECORATED);
	    							a2.setContentText("Invalid customer number...");    		    	
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
	 
	 
	//**************SGet Excisting points of the customer*********
	 public double getCusPoints(String cusNumber){
	 		
	 		double points=0;
	 		
	 	try {
	 			
	 			Connection c=dbCon.mycon();
	 			Statement s=c.createStatement();
	 			ResultSet rs=s.executeQuery(" SELECT * FROM `customer` WHERE mobile='"+cusNumber+"' ");
	 		
	 			while(rs.next()){
	 		
	 					p=rs.getString("totalpoints");			
	 			}
	 			points=Double.parseDouble(p);
	 			
	 			
	 		} catch (Exception e) {
	 			System.out.println(e);
	 		}
	 	
	 	return points;
	 		
	 	}
	 	
	 	
	//**************Update customer total points by adding new points*********
	 	public void addCusPoints(String cusNumber){
	 		
	 		double tPoints;
	 		double pp=getCusPoints(cusNumber);
	 		
	 		tPoints=pp+ (this.newPoint*0.02);
	 		
	 		try {
	 			
	 			
	 			
	 			Connection c=dbCon.mycon();
	 			Statement s=c.createStatement();
	 			s.executeUpdate("UPDATE `customer` SET `totalpoints`='"+tPoints+"' WHERE `mobile`='"+cusNumber+"'");

	 			
	 		} catch (Exception e) {
	 			System.out.println(e);
	 		}
	 		
	 	}	

	 
	 	//**************Close the current window*********
	 	@FXML
		public void closeWindow(KeyEvent event){
			//close the login window
					Platform.exit();
		}


	 	//**************Close the current window*********	
	 public void close() {
	        Stage stage = (Stage) txtcusID.getScene().getWindow();
	        stage.close();
	    }
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		focusCustomer();
	}
	
	
}
