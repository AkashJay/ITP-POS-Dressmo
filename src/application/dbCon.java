package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbCon {
	
	 public static Connection mycon() throws ClassNotFoundException,SQLException{
	        
	        Class.forName("com.mysql.jdbc.Driver");
	        Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/dressmo","root","");
	        return c;
	    } 

}
