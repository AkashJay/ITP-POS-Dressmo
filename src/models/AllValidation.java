package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.teknikindustries.bulksms.SMS;

import application.dbCon;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AllValidation {
	String totPoints;
	
		//Phone Validation
		public Boolean validatePhone(String phnNO) {
		
		Pattern p=Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");
		Matcher m=p.matcher(phnNO);
		
		if(m.find() && m.group().equals(phnNO)){
			
			return true;
		}
		else{
			Alert a2=new Alert(AlertType.WARNING);
			a2.setHeaderText(null);
			//a2.initStyle(StageStyle.UNDECORATED);
			a2.setContentText("Your Entered data: ("+phnNO+") is not correct\nPlease enter a valid phone number which contain 10 digits");    		    	
			a2.showAndWait();
	    
	    	
	    	return false;
		}
		
		
	}
		
		
	public boolean customerNo(String no){
		
		try {
        	
        	Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `customer` WHERE mobile='"+no+"' ");
			
			while(rsc.next()){
				totPoints=rsc.getString("totalpoints");
				return true;
			
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
		
		
	}
	
	public boolean validateBillNo(String no){
		
		try {
        	
        	Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `bill` WHERE billNo='"+no+"' ");
			
			while(rsc.next()){
				
				return true;
			
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
		
		
	}
	
	//*************************Send SMS*************************
	 public void sms(String msg,String number) {
		 int a=2;
		 char no[]=new char[11];
		 no[0]='9';
		 no[1]='4';
		 
		 for(int i=1;i<number.length();i++){
			 
			 no[a]=number.charAt(i);
			 a++;
		 }
		 String num=String.valueOf(no);
		
		 
		try {
			 SMS sms=new SMS();
			 sms.SendSMS("dressmo99 ", "shalika93", msg, num, "https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");
			
			
			
		} catch (Exception e2) {
			System.out.println(e2);
			
		}
	}
	 
	 private String user = "dressmo99@gmail.com";
	    private String pass= "shalika93";
	    
	    
	    
//*************************Send SMS*************************
public void Gmail(String ms,String cusEmail){
		Properties prop = new Properties();
	    prop.put("mail.smtp.ssl.trust","smtp.gmail.com");
	    prop.put("mail.smtp.auth",true);
	    prop.put("mail.smtp.starttls.enable",true);
	    prop.put("mail.smtp.host","smtp.gmail.com");
	    prop.put("mail.smtp.port","587");

	    Session ses = Session.getInstance(prop,new javax.mail.Authenticator()
	            {
	            protected javax.mail.PasswordAuthentication getPasswordAuthentication()
	            {
	            return new javax.mail.PasswordAuthentication(user,pass);

	            }

	            });
	    try
	    {
	    Message msg = new MimeMessage(ses);
	    msg.setFrom(new InternetAddress("no-reply@gmail.com"));

	    msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(cusEmail));
	    msg.setSubject("New gift");
	    msg.setText(ms);
	    Transport.send(msg);

	    System.out.print("Message Sent");

	    }
	    catch(Exception e)
	    {    System.out.print(e);

	    }
 }
		

}
