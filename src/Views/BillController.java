package Views;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;

import application.dbCon;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class BillController implements Initializable {
	
	String billNo;
	
	@FXML
    private JFXTextArea text;
	
	 String date;
	  
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
		
	}

	
	
	
	
	
	
	//**************Print Bill*********
	@FXML
	private void print(Node node) {
	    System.out.println("Creating a printer job...");

	    PrinterJob job = PrinterJob.createPrinterJob();
	    if (job != null) {
	      System.out.println(job.jobStatusProperty().asString());

	      boolean printed = job.printPage(node);
	      if (printed) {
	        job.endJob();
	      } else {
	        System.out.println("Printing failed.");
	      }
	    } else {
	      System.out.println("Could not create a printer job.");
	    }
	  }
	
	
	//**************Setting the template of the Bill*********
	@FXML
	public void setLetter(String billNo,String total,String cash,String balance,String points,String eid) {
		// TODO Auto-generated method stub
		 billNo=billNo;
	
		 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			String today = dateFormat.format(date);
			
		String s1=String.format("%60s\n\n","\n---------------------DRESSMO SHOPPING---------------------");
		String s4=String.format("%5s\n","		Anagarika Dharmapala Mawatha, Matara");
		String s6=String.format("%5s\n","			     http://www.dressmo.lk");
		String s5=String.format("%5s\n\n","			         TEL : 0415 708 700");
		String s2=String.format("%5s\n","Date : "+today+"            				Cashier :"+eid);
		String s18=String.format("%5s\n","Bill ID : "+billNo);
		String s3=String.format("%5s\n","");
		
		String s8=String.format("%5s","------------------------------------------------------------------\n");
		String s7="";
		try {
			
			Connection c=dbCon.mycon();
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery("SELECT * FROM `tems` WHERE `billNo`='"+billNo+"'");
			while(rs.next()){
				
				s7=s7+String.format("%5s",rs.getString("name")+"\n		"+rs.getString("itemCode")+"		"+rs.getString("qty")+"		"+rs.getString("amount")+"\n------------------------------------------------------------------\n");
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		String s9=String.format("%5s\n","\nTotal Price					   : "+total);
		String s10=String.format("%5s\n","Cash Recived					   : "+cash);
		String s11=String.format("%5s\n","Remaning Bal					   : "+balance);
		String s12=String.format("%5s\n","Total Points					   : "+points);
		String s13=String.format("%5s\n\n","------------------------------------------------------------------");
		
		String s14=String.format("%5s\n","Exchange Is Possible Withn 7 Days Of Purchase.Items need to be original condition and accompaired with the recipt\n		Items marked [NR] are non returnable");
		String s15=String.format("%5s\n","Get Your Dressmo Loyalty Card Today and Get Rewarded..");
		String s16=String.format("%5s\n","\n	    THANK YOU FOR SHOPPING @ DRESSMO");
		String s17=String.format("%5s\n","	    ********************************************");
		text.setText(s1+s4+s6+s5+s2+s18+s3+s8+s7+s9+s10+s11+s12+s13+s14+s15+s16+s17);
		
	}
	
	
	//**************Bill Print method*********
	public void printnow() {	
	print(text);
	close();
		
	}
	
	//**************Close the current window*********
	public void close() {
        Stage stage = (Stage) text.getScene().getWindow();
        stage.close();
    }
	
	
	
	
}
