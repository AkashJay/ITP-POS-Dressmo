package Views;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;

import application.dbCon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ReturnBillController implements Initializable {

String billNo;
	
	@FXML
    private JFXTextArea text;
	
	 String date;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
	
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
	
	@FXML
	public void setLetter1(String billNo,String total,String itemcode,String eid,String name,String qty) {
		// TODO Auto-generated method stub
		 billNo=billNo;
	
		 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			String today = dateFormat.format(date);
			
		String s1=String.format("%60s\n\n","\n---------------------DRESSMO SHOPPING---------------------");
		String s4=String.format("%5s\n","		Anagarika Dharmapala Mawatha, Matara");
		String s6=String.format("%5s\n","			     http://www.dressmo.lk");
		String s5=String.format("%5s\n\n","			         TEL : 0415 708 700");
		String s2=String.format("%5s\n","Date : "+today+"            				Cashier : "+eid);
		String s18=String.format("%5s\n","Bill ID : "+billNo);
		String s19=String.format("%5s\n","\n**********************RETURN RECIPT**********************");

		
		String s8=String.format("%5s","------------------------------------------------------------------\n");
		String s7="";
		
			
			
				
				s7=String.format("%5s",name+"\n		"+itemcode+"		"+qty+"		"+total+"\n------------------------------------------------------------------\n");
			
			
			
		
		
		String s9=String.format("%5s\n","\nTotal Price					   : "+total);
		
		String s13=String.format("%5s\n\n","------------------------------------------------------------------");
		
		String s14=String.format("%5s\n","Exchange Is Possible Withn 7 Days Of Purchase.Items need to be original condition and accompaired with the recipt\n		Items marked [NR] are non returnable");
		String s15=String.format("%5s\n","Get Your Dressmo Loyalty Card Today and Get Rewarded..");
		String s16=String.format("%5s\n","\n	    THANK YOU FOR SHOPPING @ DRESSMO");
		String s17=String.format("%5s\n","	    ********************************************");
		text.setText(s1+s4+s6+s5+s2+s18+s19+s8+s7+s9+s13+s14+s15+s16+s17);
		
	}
	
	public void printnow() {	
	print(text);
	close();
		
	}
	
	
	public void close() {
        Stage stage = (Stage) text.getScene().getWindow();
        stage.close();
    }
	
	

}
