package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.dbCon;
import javafx.fxml.FXML;




public class LoginPos {
	
	private String cashEid;
	private String cashEid1;
	private String cashPassword;
	private String position;
	
	
    
	public void setEmpDetails() throws ClassNotFoundException{
		
		
		
	}	
	
	public boolean ValidadeEmp(String empID,String empPwd) throws ClassNotFoundException{
		cashPassword="Admin";
		cashEid=empID;
		String cash="cashier";
		System.out.println(empID);
			try {
			
					Connection c=dbCon.mycon();
					Statement s=c.createStatement();
					ResultSet rs=s.executeQuery(" SELECT * FROM `add111` WHERE eid='"+cashEid+"' AND position='"+cash+"'");
					if(cashPassword.equals(empPwd)){
						while(rs.next()){
							cashEid1=rs.getString("eid");
							position=rs.getString("position");
							System.out.println("lllllllllllllllllllllllllllll");
							System.out.println(cashEid);
							return true;
						}
					}
					else{
						
						return false;
					}
			
			} catch (SQLException e) {
				System.out.println(e);
			}

		
			
			return false;
		
	}
	
	

}
