package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;

import javax.sound.midi.Soundbank;

import application.dbCon;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PosHome {

	
	String billNo;
	String ranNo;
	int m1=0;
	private String itmCode;
	private String rate;
	
	


	public void AddValuesToTable(String itemCode) throws ClassNotFoundException{
		
		
		
	}
	
	
	public String getRate(String itemCode) throws ClassNotFoundException{
		
		itmCode=itemCode;
		
		try {
			
			Connection c=dbCon.mycon();
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery(" SELECT * FROM `items` WHERE itemcode='"+itmCode+"' ");
			
			while(rs.next()){
				rate=rs.getString("sellingprice");
		
			}
	
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return rate;
	}
	
	
	public String getBillNo(){

		
		Random rand = new Random();

		   int  n = rand.nextInt(50) + 1;
		   
		   
		   ranNo=Integer.toString(n);
		   
		   
		   Date d=new Date();
           String da=d.toString();
           String ar[]=da.split(" ");
           String m=ar[1];
           
           if(m=="Jan"){
        		   
        		 m1=1;  
           }
           if(m.equals("Sep")){
    		   
      		 m1=9;  
         }
           String date="B"+ar[2]+m1+"-"+ranNo;
           billNo=date; 
	   	   		
		return billNo;
	}

}
