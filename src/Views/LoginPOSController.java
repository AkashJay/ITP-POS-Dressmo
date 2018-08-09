package Views;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.*;

import Views.*;

import application.Main;
import application.dbCon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.LoginPos;

public class LoginPOSController implements Initializable {
	
	
	
	@FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtPassword;
    
    
	@FXML
    private MaterialDesignIconView btn_close;
	
	@FXML
    private StackPane stackePane;
	
	@FXML
    private JFXButton login;
	
	@FXML
    private Label erroeLbl;
	
	String name;
	
	@FXML
	public void closeWindow(MouseEvent event){
		//close the login window
				Platform.exit();
	}
	
	public boolean errorMsg=false;
	
  
	
	
	    public void login(){
	    	
	    	this.txtPassword.setOnKeyPressed(e->{
	    		
	    		if (e.getCode() == KeyCode.ENTER)  {    	  		
	    			try {  				
						LoadPosHome();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        	}
	    	});
	    	
	    	
	    }
	    
	    public void login1(){
	    	
	    	this.txtUserName.setOnKeyPressed(e->{
	    		
	    		if (e.getCode() == KeyCode.ENTER)  {    	  		
	    			try {
	    				
						LoadPosHome();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        	}
	    	});
	    }
	
	/**
     * close the window.
     */
    public void close() {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
    
	@FXML
	public void LoadPosHome() throws ClassNotFoundException{
		LoginPos lp=new LoginPos();
		
		String pwd = txtPassword.getText();
		String id = txtUserName.getText();
		
		
		boolean val=lp.ValidadeEmp(id, pwd);
		System.out.println(val);
		
		if(val){
		
		try {
			//Open the home window
			FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/PosHome.fxml"));
			Parent root1=(Parent) fxmloder.load();
			
			Stage stage =new Stage();
			//stage.initStyle(StageStyle.TRANSPARENT);
			stage.setMaximized(true);
			stage.setScene(new Scene(root1));
			stage.show();
			((PosHomeController)fxmloder.getController()).setCasierID(txtUserName.getText());
			((PosHomeController)fxmloder.getController()).setCasierName(eName());
			
			//Close the current window(call above method)
			close();
			
			this.erroeLbl.setVisible(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		}
		else{
					
			
			if(!txtPassword.getText().equals("") && !txtUserName.getText().isEmpty()){
				this.erroeLbl.setVisible(true);
				
				this.erroeLbl.setText("Login Faield..Enter valid Loging details");
				txtPassword.setText("");
				txtUserName.requestFocus();
			}
			
			else if(txtUserName.getText().isEmpty()){
				this.erroeLbl.setVisible(true);
				this.erroeLbl.setText("Login Faield..Enter the Employee ID..");	
				
				this.txtUserName.requestFocus();
			}
			else if(!txtUserName.getText().isEmpty()){
				this.erroeLbl.setVisible(false);
							
				this.txtPassword.requestFocus();
			}
			
			
			
			
			
			
			
			//JFXDialogLayout content1=new JFXDialogLayout();
		    //content1.setHeading(new Text("Warning"));
		    //content1.setBody(new Text("Please Enter a valid Login.."));
		    //JFXDialog s12=new JFXDialog(stackePane,content1,JFXDialog.DialogTransition.TOP);
			//JFXButton b1=new JFXButton("Okay");
			//b1.setOnAction(new EventHandler<ActionEvent>() {
			//	@Override
			//	public void handle(ActionEvent arg0) {
			//		s12.close();
			//		}
			//});
			//content1.setActions(b1);
			//s12.show();
			//Alert a=new Alert(AlertType.WARNING);
			//a.initStyle(StageStyle.UNDECORATED);
			//a.setContentText("Please enter valid login");
			//a.show();
			System.out.println("login failed");
		}
	}

	
	public String eName() throws ClassNotFoundException{
		
		try {
			
			Connection c=dbCon.mycon();
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery(" SELECT * FROM `add111` WHERE eid='"+txtUserName.getText()+"' ");
			
				while(rs.next()){
					
					name=rs.getString("fullname");
				}
			
		
	
	} catch (SQLException e) {
		System.out.println(e);
	}
		return name;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.erroeLbl.setVisible(false);
		this.login();
		this.login1();
		
		
		
		
		
	}

}
