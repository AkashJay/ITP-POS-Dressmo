package Views;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sound.midi.Soundbank;
import javax.swing.text.TabableView;

import org.controlsfx.control.textfield.TextFields;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.teknikindustries.bulksms.SMS;

import application.dbCon;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.AllValidation;
import models.BillPrint;
import models.CustomerTable;
import models.LoginPos;
import models.PosHome;
import models.StockPos;
import models.posTable;
import models.returnTable;

public class PosHomeController implements Initializable {
	
	

	   @FXML
	    private JFXTextField txtBillNo;

	    @FXML
	    private JFXTextField txtDate;

	    @FXML
	    private JFXTextField txtCashier;

	    @FXML
	    private JFXTextField txtBarCode;

	    @FXML
	    private JFXTextField txtQty;

	    @FXML
	    private JFXTextField txtRate;

	    @FXML
	    private JFXTextField txtSystemIP;

	    @FXML
	    private JFXComboBox<?> comboPayMode;

	    @FXML
	    private JFXTextField txtTotalItem;

	    @FXML
	    private JFXTextField txtTotalQty;

	    @FXML
	    private JFXTextField txtSubTotal;

	    @FXML
	    private JFXTextField txtItemDisAmt;

	    @FXML
	    private JFXTextField txtBillDisPr;

	    @FXML
	    private JFXTextField txtBillAmt;

	    @FXML
	    private JFXTextField txtGrandTot;

	    @FXML
	    private JFXTextField txtTax;

	    @FXML
	    private JFXTextField txtNetTotal;

	    @FXML
	    private JFXTextField txtCashRcvd;

	    @FXML
	    private JFXTextField txtBalance;

	    @FXML
	    private Button btnSave;

	    @FXML
	    private Button btnView;

	    @FXML
	    private ImageView btnRemove;

	    @FXML
	    private Button btnSearch;

	    @FXML
	    private Button btnLastBill;

	    @FXML
	    private Button btnHome;

	    @FXML
	    private Button btnLogout;
    
	    @FXML
	    private Label lblDate;

	    @FXML
	    private Label lblTime;
	    
	    @FXML
	    private Label warning;

	    
	    @FXML
	    private TableView<posTable> table;

	    @FXML
	    private TableColumn<posTable, String> id;

	    @FXML
	    private TableColumn<posTable, String> itemCode;

	    @FXML
	    private TableColumn<posTable, String> itemName;

	    @FXML
	    private TableColumn<posTable, String> rate;

	    @FXML
	    private TableColumn<posTable, String> qty;

	    @FXML
	    private TableColumn<posTable, String> ammount;

	    @FXML
	    private Label lblinfo;

	    @FXML
	    private JFXTextField txtcusID;
	    
	    private String ttItemCode;
	    private String ttName;
	    private String ttAmount;
	    private double ttRate;
	    private String ttid;
	    private String ttQty;
	    
	    public ObservableList<posTable> list;

	    private String rate2;
	    private double rate1;
	    private int qty2;
	    public static double netTotal=0;
	    double grosTotal=0;
	    private String ttItmCode;
	    String iqty;
		String iamount;
		String name;
		String ratea;
		int totalItems=0;
		int totalQty=0;
		
		double cusPoints=0;
    
    @FXML
    public void next(KeyEvent e) {
    	
    	if (e.getCode() == KeyCode.ENTER)  {    	  		
    		this.txtBarCode.requestFocus();
    	} 	    	
    }
    
    public void AddvaluesTable(String itmCode, double netAmount,int qtyt,double grosstotal) throws ClassNotFoundException{
    	ttItmCode=itmCode;
    	try {
    		
    		Connection c=dbCon.mycon();
			Statement s=c.createStatement();
			ResultSet rs=s.executeQuery(" SELECT * FROM `items` WHERE itemcode='"+ttItmCode+"' ");
			list=FXCollections.observableArrayList();
			while(rs.next()){
			
				String code=rs.getString(1);
				 name=rs.getString(2);
				 ratea=rs.getString(6);
				
				iqty=Double.toString(netAmount);
				totalQty=totalQty+qtyt;
				iamount=Integer.toString(qtyt);
				
				
				
				//list.add(new posTable("1", code,  name, ratea , iqty,iamount ));
						
								
			}
			
			
    		totalItems=totalItems+1;
    		Connection c1=dbCon.mycon();
			Statement s1=c1.createStatement();
			s1.executeUpdate("INSERT INTO `tems`( `itemCode`, `billNo`, `amount`, `qty`, `rate`, `name`, `date`) VALUES ('"+txtBarCode.getText()+"','"+txtBillNo.getText()+"','"+netAmount+"','"+qtyt+"','"+ratea+"','"+name+"','"+lblDate.getText()+"') ");
			
			System.out.println("data entered");
			
			Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `tems` WHERE billNo='"+txtBillNo.getText()+"' ");
			list=FXCollections.observableArrayList();
			while(rsc.next()){
				
			
				
				String gTotal=Double.toString(grosstotal);
				list.add(new posTable(rsc.getString("id"), rsc.getString("itemCode"),  rsc.getString("name"), rsc.getString("rate") , rsc.getString("qty"),rsc.getString("amount") ));
						
								
			}
			String toti=Integer.toString(totalItems);
			String totQ=Integer.toString(totalQty);
			String grostot=Double.toString(grosstotal);
			txtTotalItem.setText(toti);
			txtItemDisAmt.setText("No discount item");
			txtBillDisPr.setText("0.00%");
			txtBillAmt.setText("RS 0.00");
			txtTax.setText("No tax");
			txtGrandTot.setText("RS "+grostot);
			txtTotalQty.setText(totQ);
			txtSubTotal.setText("RS "+grostot);
			
	
    	} catch (SQLException e) {
    		System.out.println(e);
    	}
   
    	
    	
		id.setCellValueFactory(new PropertyValueFactory<posTable, String> ("id"));
		itemCode.setCellValueFactory(new PropertyValueFactory<posTable, String> ("itemCode"));
		itemName.setCellValueFactory(new PropertyValueFactory<posTable, String> ("itemName"));
		rate.setCellValueFactory(new PropertyValueFactory<posTable, String> ("rate"));
		qty.setCellValueFactory(new PropertyValueFactory<posTable, String> ("qty"));
		ammount.setCellValueFactory(new PropertyValueFactory<posTable, String> ("ammount"));
		 //for(posTable model : list) {
	            //System.out.println(model.gettItemCode());
	           //// System.out.println(model.gettName());
	           /// System.out.println(model.gettAmmount());
	           // System.out.println(model.gettRate());
	           // System.out.println(model.getTid());
	       // }
		table.setItems(list);
		System.out.println("ddddddddddddddddddddd");
    	
    	
    	
    }
    
public void printBill(){
    	
    	this.txtBalance.setOnKeyPressed(e->{
    		
    		if (e.getCode() == KeyCode.SPACE)  {    	  		
    			try { 
    				String bill=txtBillNo.getText();
    				Connection c1=dbCon.mycon();
					Statement s1=c1.createStatement();
					
					s1.executeUpdate(" UPDATE `bill` SET `noOfItem`='"+txtTotalItem.getText()+"',`totakQty`='"+txtTotalQty.getText()+"',`cashRecived`='"+txtCashRcvd.getText()+"',`cashier`='"+lblEID.getText()+"',`total`='"+txtNetTotal.getText()+"',`date`='"+lblDate.getText()+"',`time`='"+lblTime.getText()+"' WHERE billNo='"+bill+"' ");
    				System.out.println("bill added");
					Alert a1=new Alert(AlertType.CONFIRMATION);
    		    	a1.setHeaderText(null);
    		    	//a1.initStyle(StageStyle.UNDECORATED);
    		    	a1.setContentText("Do you want to print bill??");    		    	
    		    	//Optional<ButtonType>aa=a1.showAndWait();
    		    	//BillPrint b=new BillPrint();
    		    	//b.print(txtBillNo.getText());
    		
    		    	
    		    	Optional<ButtonType> result = a1.showAndWait();
    		    	if (result.get() == ButtonType.OK){
    		    			try {
    		    				Connection cc=dbCon.mycon();
    	    					Statement ss=cc.createStatement();
    	    					ResultSet rsc=ss.executeQuery(" SELECT * FROM `bill` WHERE billNo='"+txtBillNo.getText()+"' ");
    	    					
    	    					String contact;
    	    					AllValidation a=new AllValidation();
    	    					while(rsc.next()){
    	    						
    	    						String M="Dear customer,\nLast bill number : "+txtBillNo.getText()+" \nAnd the total balance is "+txtNetTotal.getText()+"\nNumber of items : "+txtTotalItem.getText()+"\nNo.of total Qty : "+txtQty.getText()+"\nThank you..\nDressmo";
    	    						String cusN=rsc.getString("cusNo");
    	    						if(rsc.getString("cusNo") != null){
    	    							System.out.println(cusN);
    	    							a.sms(M,rsc.getString("cusNo"));
    	    							
    	    						}
    	    						
    	    						
    	    						
    	    						
    	    						Alert a2=new Alert(AlertType.INFORMATION);
    	        					a2.setHeaderText(null);
    	        					//a2.initStyle(StageStyle.UNDECORATED);
    	        					a2.setContentText("Transaction Done..Bill details are  send to customer "+rsc.getString("cusNo"));    		    	
    	        					Optional<ButtonType> result1 = a2.showAndWait();
    	            		    	if (result1.get() == ButtonType.OK){
    	            		    		
    	            		    	//	Connection cc1=dbCon.mycon();
    	            				//	Statement ss1=cc1.createStatement();
    	            					//ss1.executeUpdate("UPDATE `items` SET`quantity`= WHERE `itemcode`");    
    	            		    		
    	            		    		
    	            		    		openBill();
    	            		    	}
    	        					ccController c=new ccController();
    	        					
    	        					PosHome p=new PosHome();
    	        					
    	        					txtBillNo.setText(p.getBillNo());
    	        					clear();
    	        					txtBarCode.requestFocus();
    	        					return;
    	    					}
    	    					Alert a2=new Alert(AlertType.INFORMATION);
    	    					a2.setHeaderText(null);
    	    					//a2.initStyle(StageStyle.UNDECORATED);
    	    					a2.setContentText("Transaction Done..Bill printed..\nNext bill..");    		    	
    	    					a2.showAndWait();
    	    					
    	    					ccController c=new ccController();
    	    					
    	    					PosHome p=new PosHome();
    	    					
    	    					txtBillNo.setText(p.getBillNo());
    	    					clear();
    	    					txtBarCode.requestFocus();
							} catch (Exception e2) {
								// TODO: handle exception
								System.out.println(e2);
							}
    		    		
    					
    					
    					
    					
    		    	} else {
    		    	    // ... user chose CANCEL or closed the dialog
    		    		Alert a2=new Alert(AlertType.INFORMATION);
    					a2.setHeaderText(null);
    					//a2.initStyle(StageStyle.UNDECORATED);
    					a2.setContentText("Bill Canceled..");    		    	
    					a2.showAndWait();
    		    	}
    	
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
   
    				
    				if (e.getCode() == KeyCode.F12)  { 
    	    			
    	    			try {
    	    				//Open the home window
    	    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/ItemReturn2.fxml"));
    	    				Parent root1=(Parent) fxmloder.load();
    	    				
    	    				Stage stage =new Stage();
    	    				stage.initStyle(StageStyle.TRANSPARENT);
    	    				//stage.setMaximized(true);
    	    				stage.setScene(new Scene(root1));
    	    				stage.show();
    	    				
    	    			
    	    				
    	    			} catch(Exception e1) {
    	    				e1.printStackTrace();
    	    			}
    	    	    		}
    	    		
    	    		if (e.getCode() == KeyCode.F6)  { 
    	    			
    	    			try {
    	    				txtQty.requestFocus();
    	    			
    	    				
    	    			} catch(Exception e1) {
    	    				e1.printStackTrace();
    	    			}
    	    	    		}

    	    		if (e.getCode() == KeyCode.F5)  { 
    	    			
    	    			try {
    	    				txtBarCode.requestFocus();
    	    			
    	    				
    	    			} catch(Exception e1) {
    	    				e1.printStackTrace();
    	    			}
    	    	    		}
    	    		
    	    		
    	    		
    	    	
    	        	
    	    		if (e.getCode() == KeyCode.F2)  {    	  		
    	    			try { 
    	    				txtCashRcvd.requestFocus();
    					} catch (Exception e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
    	        	}
    	    		
    	    		if (e.getCode() == KeyCode.F1)  {    	  		
    	    			try { 
    	    				//Open the home window
    	    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/cc.fxml"));
    	    				Parent root1=(Parent) fxmloder.load();
    	    				
    	    				Stage stage =new Stage();
    	    				stage.initStyle(StageStyle.TRANSPARENT);
    	    				((ccController)fxmloder.getController()).setBillNo(txtBillNo.getText(),grosTotal);
    	    			
    	    				stage.setScene(new Scene(root1));
    	    				stage.show();
    						
    					} catch (Exception e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
    	        	}
    	    		
    	    		
    	    		if (e.getCode() == KeyCode.DELETE)  {    	  		
    	    			try { 
    	    				
    	    				Alert a1=new Alert(AlertType.CONFIRMATION);
    	    		    	a1.setHeaderText(null);
    	    		    	//a1.initStyle(StageStyle.UNDECORATED);
    	    		    	a1.setContentText("Do you want to cancel the bill??");    		    	
    	    		    	//Optional<ButtonType>aa=a1.showAndWait();
    	    		    	//BillPrint b=new BillPrint();
    	    		    	//b.print(txtBillNo.getText());
    	    		
    	    		    	Optional<ButtonType> result = a1.showAndWait();
    	    		    	if (result.get() == ButtonType.OK){
    	    		    	    // ... user chose OK
    	    		    		
    	    		    		Alert a2=new Alert(AlertType.INFORMATION);
    	    					a2.setHeaderText(null);
    	    					//a2.initStyle(StageStyle.UNDECORATED);
    	    					a2.setContentText("Bill canceled...");    		    	
    	    					a2.showAndWait();
    	    					deleteItem();
    	    					PosHome p=new PosHome();
    	    					txtBillNo.setText(p.getBillNo());
    	    					clear();
    	    					
    	    					txtBarCode.requestFocus();
    	    					
    	    					
    	    		    	}
    					} catch (Exception e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
    	        	}
    	    		if (e.getCode() == KeyCode.ESCAPE)  {    	  		
    	    			try { 
    	    				//Open the home window
    	    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/LoginPOS.fxml"));
    	    				Parent root1=(Parent) fxmloder.load();
    	    				
    	    				Stage stage =new Stage();
    	    				stage.initStyle(StageStyle.TRANSPARENT);
    	    				//stage.setMaximized(true);
    	    				stage.setScene(new Scene(root1));
    	    				stage.show();
    	    				
    	    				//Close the current window(call above method)
    	    				close();
    					} catch (Exception e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
    	        	}
				
    	    		if (e.getCode() == KeyCode.F11)  {
    	    			
    	    			clear();
    	    		}
    		
    	});   	
    	
    }
    
    public void focusQTY(){
    	
    	this.txtBarCode.setOnKeyPressed(e->{
    		
    		
    		if (e.getCode() == KeyCode.F12)  { 
    			
    			try {
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/ItemReturn2.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				//stage.setMaximized(true);
    				stage.setScene(new Scene(root1));
    				stage.show();
    				
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}
    		
    		if (e.getCode() == KeyCode.F6)  { 
    			
    			try {
    				txtQty.requestFocus();
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}

    		if (e.getCode() == KeyCode.F5)  { 
    			
    			try {
    				txtBarCode.requestFocus();
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}
    		
    		
    		
    		if (e.getCode() == KeyCode.ENTER)  {    	  		
    			try { 
    				
    				String itemName;
    				Connection c=dbCon.mycon();
    		        Statement s=c.createStatement();
    		        ResultSet rs=s.executeQuery("select * from items where `itemcode`='"+txtBarCode.getText()+"'");
    		      
    		        	
    		        		if(rs.next()){
    		        			System.out.println("item Found");
    		        		}
    		        		else{
    		        			Alert a1=new Alert(AlertType.WARNING);
			    		    	a1.setHeaderText(null);
			    		    	//a1.initStyle(StageStyle.UNDECORATED);
			    		    	a1.setContentText("No Item Found!!! Enter correct item code or name");
			    		    	a1.showAndWait();
			    		    	txtBarCode.requestFocus();
			    		    	return;
    		        		}
    		        		
    		        		
    				if(txtBarCode.getText().isEmpty()){
    					
    			    	
    					txtBarCode.requestFocus();
    				}
    				else{
    					String iCode=txtBarCode.getText();
    					PosHome p=new PosHome();
        				rate2=p.getRate(iCode);
        				txtRate.setText(rate2);
    					txtQty.requestFocus();
    				}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		if (e.getCode() == KeyCode.F2)  {    	  		
    			try { 
    				txtCashRcvd.requestFocus();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		if (e.getCode() == KeyCode.F1)  {    	  		
    			try { 
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/cc.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				((ccController)fxmloder.getController()).setBillNo(txtBillNo.getText(),grosTotal);
    			
    				stage.setScene(new Scene(root1));
    				stage.show();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		
    		if (e.getCode() == KeyCode.DELETE)  {    	  		
    			try { 
    				
    				Alert a1=new Alert(AlertType.CONFIRMATION);
    		    	a1.setHeaderText(null);
    		    	//a1.initStyle(StageStyle.UNDECORATED);
    		    	a1.setContentText("Do you want to cancel the bill??");    		    	
    		    	//Optional<ButtonType>aa=a1.showAndWait();
    		    	//BillPrint b=new BillPrint();
    		    	//b.print(txtBillNo.getText());
    		
    		    	Optional<ButtonType> result = a1.showAndWait();
    		    	if (result.get() == ButtonType.OK){
    		    	    // ... user chose OK
    		    		
    		    		Alert a2=new Alert(AlertType.INFORMATION);
    					a2.setHeaderText(null);
    					//a2.initStyle(StageStyle.UNDECORATED);
    					a2.setContentText("Bill canceled...");    		    	
    					a2.showAndWait();
    					deleteItem();
    					PosHome p=new PosHome();
    					txtBillNo.setText(p.getBillNo());
    					clear();
    					
    					txtBarCode.requestFocus();
    					
    					
    		    	}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		if (e.getCode() == KeyCode.ESCAPE)  {    	  		
    			try { 
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/LoginPOS.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				//stage.setMaximized(true);
    				stage.setScene(new Scene(root1));
    				stage.show();
    				
    				//Close the current window(call above method)
    				close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		

    		
    		if (e.getCode() == KeyCode.F11)  {
    			
    			clear();
    		}
    		
    		
    	});   	
    	
    }
    
    
    
    public void focusItemCode(){
    	
    	
    	 this.txtQty.setOnKeyPressed(e->{
    		
    		if (e.getCode() == KeyCode.ENTER)  {   	  		
    			try { 
    				
    				if(txtQty.getText().isEmpty()){
    					txtQty.requestFocus();
    					System.out.println("focused");
    				}
    				
    				else{
    					netTotal=0;
    					
    					String iCode=txtBarCode.getText();
        				
        				rate1=Double.parseDouble(rate2);
        				qty2=Integer.parseInt(txtQty.getText());
        				netTotal=(rate1*qty2);
        				grosTotal=grosTotal+netTotal;
        				String netT;
        				netT=Double.toString(grosTotal);
        				
        				txtNetTotal.setText(netT);
        				AddvaluesTable(iCode,netTotal,qty2,grosTotal);
        				
        		
        				lblinfo.setVisible(true);
        				warning.setVisible(false);
        				txtQty.setText("");
        				txtRate.setText("");
        				txtBarCode.setText("");
    					txtBarCode.requestFocus();
    				}
				} catch (Exception e1) {
					
					lblinfo.setVisible(false);
					warning.setText("Warning!! Qty can't have string");
					warning.setVisible(true);
			    	
			    	txtQty.setText("");
				}
        	}
    		
    		if (e.getCode() == KeyCode.F5)  { 
    			
    			try {
    				txtBarCode.requestFocus();
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}
    		
    		if (e.getCode() == KeyCode.F6)  { 
    			
    			try {
    				txtQty.requestFocus();
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}
    		
    		if (e.getCode() == KeyCode.DELETE)  {    	  		
    			try { 
    				
    				Alert a1=new Alert(AlertType.CONFIRMATION);
    		    	a1.setHeaderText(null);
    		    	//a1.initStyle(StageStyle.UNDECORATED);
    		    	a1.setContentText("Do you want to cancel the bill??");    		    	
    		    	//Optional<ButtonType>aa=a1.showAndWait();
    		    	//BillPrint b=new BillPrint();
    		    	//b.print(txtBillNo.getText());
    		
    		    	Optional<ButtonType> result = a1.showAndWait();
    		    	if (result.get() == ButtonType.OK){
    		    	    // ... user chose OK
    		    		
    		    		Alert a2=new Alert(AlertType.INFORMATION);
    					a2.setHeaderText(null);
    					//a2.initStyle(StageStyle.UNDECORATED);
    					a2.setContentText("Bill canceled...");    		    	
    					a2.showAndWait();
    					deleteItem();
    					PosHome p=new PosHome();
    					txtBillNo.setText(p.getBillNo());
    					clear();
    					
    					txtBarCode.requestFocus();
    					
    					
    		    	}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		if (e.getCode() == KeyCode.F2)  {    	  		
    			try { 
    				txtCashRcvd.requestFocus();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		if (e.getCode() == KeyCode.F1)  {    	  		
    			try { 
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/cc.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				((ccController)fxmloder.getController()).setBillNo(txtBillNo.getText(),grosTotal);
    			
    				stage.setScene(new Scene(root1));
    				stage.show();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		
    	
    		if (e.getCode() == KeyCode.ESCAPE)  {    	  		
    			try { 
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/LoginPOS.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				//stage.setMaximized(true);
    				stage.setScene(new Scene(root1));
    				stage.show();
    				
    				//Close the current window(call above method)
    				close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		if (e.getCode() == KeyCode.F12)  { 
    			
    			try {
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/ItemReturn2.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				//stage.setMaximized(true);
    				stage.setScene(new Scene(root1));
    				stage.show();
    				
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}
    		
    		if (e.getCode() == KeyCode.F11)  {
    			
    			clear();
    		}
    	}); 
    	
    	
    	
    }
    @FXML
    public JFXTextField txtCustomer;
    
    @FXML
    private Label lblEID;
    
   
    
    public void setCasierID(String a){
    
    	lblEID.setText(a);
    }
    public void setCasierName(String a){
    	
    	txtCashier.setText(a);
    }
    
    public void FocusCustomer(){
    	
    	this.txtCashRcvd.setOnKeyPressed(e->{
    		
   
    		
    	
    		
    		if (e.getCode() == KeyCode.ENTER)  {  
    			
    			txtBalance.setEditable(false);
    			try { 
    				String balance;
    				double cash=Double.parseDouble(txtCashRcvd.getText());
    				double total=Double.parseDouble(txtNetTotal.getText());
    				cusPoints=total*0.02;
    				double bal=cash-total;
    				balance=Double.toString(bal);
    				txtBalance.setText(balance);
    				
    				
					System.out.println("sucess");
					double bal1=Double.parseDouble(txtBalance.getText());
					if(bal1<0){
						
						Alert a1=new Alert(AlertType.WARNING);
	    		    	a1.setHeaderText(null);
	    		    	//a1.initStyle(StageStyle.UNDECORATED);
	    		    	a1.setContentText("Need more "+txtBalance.getText());
	    		    	a1.showAndWait();
	    		    	txtCashRcvd.requestFocus();
					}
					else{
						Connection c1=dbCon.mycon();
    					Statement s1=c1.createStatement();
    					
    					s1.executeUpdate(" INSERT INTO `bill`(`billNo`) VALUES ('"+txtBillNo.getText()+"')");
						txtBalance.requestFocus();
						
					}
					
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch blocktxtba
					txtBalance.requestFocus();
					
					e1.printStackTrace();
				}
        	}
    		

    		
    		if (e.getCode() == KeyCode.F5)  { 
    			
    			try {
    				txtBarCode.requestFocus();
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}
    		
    		if (e.getCode() == KeyCode.F6)  { 
    			
    			try {
    				txtQty.requestFocus();
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}
    		
    		if (e.getCode() == KeyCode.DELETE)  {    	  		
    			try { 
    				
    				Alert a1=new Alert(AlertType.CONFIRMATION);
    		    	a1.setHeaderText(null);
    		    	//a1.initStyle(StageStyle.UNDECORATED);
    		    	a1.setContentText("Do you want to cancel the bill??");    		    	
    		    	//Optional<ButtonType>aa=a1.showAndWait();
    		    	//BillPrint b=new BillPrint();
    		    	//b.print(txtBillNo.getText());
    		
    		    	Optional<ButtonType> result = a1.showAndWait();
    		    	if (result.get() == ButtonType.OK){
    		    	    // ... user chose OK
    		    		
    		    		Alert a2=new Alert(AlertType.INFORMATION);
    					a2.setHeaderText(null);
    					//a2.initStyle(StageStyle.UNDECORATED);
    					a2.setContentText("Bill canceled...");    		    	
    					a2.showAndWait();
    					deleteItem();
    					PosHome p=new PosHome();
    					txtBillNo.setText(p.getBillNo());
    					clear();
    					
    					txtBarCode.requestFocus();
    					
    					
    		    	}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		if (e.getCode() == KeyCode.F2)  {    	  		
    			try { 
    				txtCashRcvd.requestFocus();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		if (e.getCode() == KeyCode.F1)  {    	  		
    			try { 
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/cc.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				((ccController)fxmloder.getController()).setBillNo(txtBillNo.getText(),grosTotal);
    			
    				stage.setScene(new Scene(root1));
    				stage.show();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		
    	
    		if (e.getCode() == KeyCode.ESCAPE)  {    	  		
    			try { 
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/LoginPOS.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				//stage.setMaximized(true);
    				stage.setScene(new Scene(root1));
    				stage.show();
    				
    				//Close the current window(call above method)
    				close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		if (e.getCode() == KeyCode.F12)  { 
    			
    			try {
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/ItemReturn2.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				//stage.setMaximized(true);
    				stage.setScene(new Scene(root1));
    				stage.show();
    				
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}
    		
    		if (e.getCode() == KeyCode.F11)  {
    			
    			clear();
    		}
    	});   	
    	
    }
    

	
    
    @FXML
	public void LoadReturnHome(ActionEvent event){
		
		try {
			//Open the home window
			FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/ItemReturn2.fxml"));
			Parent root1=(Parent) fxmloder.load();
			
			Stage stage =new Stage();
			stage.initStyle(StageStyle.TRANSPARENT);
			//stage.setMaximized(true);
			stage.setScene(new Scene(root1));
			stage.show();
			
		
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    public void deleteItem() {
    	
    	try {
    		
    		Connection c1=dbCon.mycon();
    		Statement s1=c1.createStatement();
    		s1.executeUpdate("DELETE FROM `tems` WHERE `billNo`='"+txtBillNo.getText()+"'");
    		
    	} catch (Exception e) {
    		// TODO: handle exception
    		System.out.println(e);
    	}
    	
    }

    private int minute;
	private int hour;
	private int second;
	private int day;
	private int month;
	private int year;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		focusQTY();
		focusItemCode();
		FocusCustomer();
		printBill();
		takeTableValue();
		
		txtBillNo.setEditable(false);
		warning.setVisible(false);
		
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {            
	        Calendar cal = Calendar.getInstance();
	        second = cal.get(Calendar.SECOND);
	        minute = cal.get(Calendar.MINUTE);
	        hour = cal.get(Calendar.HOUR);
	        day=cal.get(Calendar.DAY_OF_MONTH);
	        month=cal.get(Calendar.MONTH);
	        year=cal.get(Calendar.YEAR);
	        lblTime.setText(hour + ":" + (minute) + ":" + second);
	        lblDate.setText((month+1) + "/" + day + "/" + year);
	        txtDate.setText((month+1) + "/" + day + "/" + year);
	        
	    }),
	         new KeyFrame(Duration.seconds(1))
	    );
	    clock.setCycleCount(Animation.INDEFINITE);
	    clock.play();
	    		
	    setBillNo();
	    
	    
	    try {
	    	
			ObservableList data = FXCollections.observableArrayList();
			Connection c=dbCon.mycon();
	        Statement s=c.createStatement();
	        ResultSet rs=s.executeQuery("select * from items");
	        
	        
	     
	        	while(rs.next()){
	        		data.add(rs.getString("itemcode"));
	        		//data.add(rs.getString("itemname"));
	        	}
	        	
	        	
	        		TextFields.bindAutoCompletion(txtBarCode,data);
	        	
	        	
	      
	        
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   
	   
	}
    
	
	public void setBillNo(){
		
		PosHome p=new PosHome();
		this.txtBillNo.setText(p.getBillNo());
	}
	
	/**
     * close the window.
     */
    public void close() {
        Stage stage = (Stage) txtBarCode.getScene().getWindow();
        stage.close();
    }
    
    
    public void openCustomer(ActionEvent e){
    	try {
			//Open the home window
    		FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/Customer.fxml"));
			Parent root1=(Parent) fxmloder.load();
			
			Stage stage =new Stage();
			//stage.initStyle(StageStyle.TRANSPARENT);
			stage.setMaximized(true);
			stage.setScene(new Scene(root1));
			stage.show();
			((CustomerController)fxmloder.getController()).setEmpdetails(lblEID.getText());
			
			
			//Close the current window(call above method)
			close();
			
			
			
			
		} catch(Exception e1) {
			e1.printStackTrace();
		}
    	
    }
    
    public void clearF(){
    	txtBarCode.setText("");
    	txtQty.setText("");
    	txtRate.setText("");
    	
    }
    public void clear(){
    	
    	txtQty.setText("");
		txtRate.setText("");
		txtCashRcvd.setText("");
		txtBalance.setText("");
		txtNetTotal.setText("");
		txtTotalItem.setText("");
		txtTotalQty.setText("");
		txtSubTotal.setText("");
		txtItemDisAmt.setText("");
		txtBillDisPr.setText("");
		txtBillAmt.setText("");
		txtTax.setText("");
		txtGrandTot.setText("");
		table.setItems(null);
		totalItems=0;
		totalQty=0;
    }
   
    
    public void takeTableValue() {
		// TODO Auto-generated method stub
    	table.setOnKeyPressed(e->{
	if (e.getCode() == KeyCode.F5)  { 
    			
    			try {
    				txtBarCode.requestFocus();
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}
    		
    		if (e.getCode() == KeyCode.F6)  { 
    			
    			try {
    				txtQty.requestFocus();
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}
    		
    		if (e.getCode() == KeyCode.DELETE)  {    	  		
    			try { 
    				
    				Alert a1=new Alert(AlertType.CONFIRMATION);
    		    	a1.setHeaderText(null);
    		    	//a1.initStyle(StageStyle.UNDECORATED);
    		    	a1.setContentText("Do you want to cancel the bill??");    		    	
    		    	//Optional<ButtonType>aa=a1.showAndWait();
    		    	//BillPrint b=new BillPrint();
    		    	//b.print(txtBillNo.getText());
    		
    		    	Optional<ButtonType> result = a1.showAndWait();
    		    	if (result.get() == ButtonType.OK){
    		    	    // ... user chose OK
    		    		
    		    		Alert a2=new Alert(AlertType.INFORMATION);
    					a2.setHeaderText(null);
    					//a2.initStyle(StageStyle.UNDECORATED);
    					a2.setContentText("Bill canceled...");    		    	
    					a2.showAndWait();
    					deleteItem();
    					PosHome p=new PosHome();
    					txtBillNo.setText(p.getBillNo());
    					clear();
    					
    					txtBarCode.requestFocus();
    					
    					
    		    	}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		if (e.getCode() == KeyCode.F2)  {    	  		
    			try { 
    				txtCashRcvd.requestFocus();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		if (e.getCode() == KeyCode.F1)  {    	  		
    			try { 
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/cc.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				((ccController)fxmloder.getController()).setBillNo(txtBillNo.getText(),grosTotal);
    			
    				stage.setScene(new Scene(root1));
    				stage.show();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		
    	
    		if (e.getCode() == KeyCode.ESCAPE)  {    	  		
    			try { 
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/LoginPOS.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				//stage.setMaximized(true);
    				stage.setScene(new Scene(root1));
    				stage.show();
    				
    				//Close the current window(call above method)
    				close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    		
    		if (e.getCode() == KeyCode.F12)  { 
    			
    			try {
    				//Open the home window
    				FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/ItemReturn2.fxml"));
    				Parent root1=(Parent) fxmloder.load();
    				
    				Stage stage =new Stage();
    				stage.initStyle(StageStyle.TRANSPARENT);
    				//stage.setMaximized(true);
    				stage.setScene(new Scene(root1));
    				stage.show();
    				
    			
    				
    			} catch(Exception e1) {
    				e1.printStackTrace();
    			}
    	    		}
    		
    		if (e.getCode() == KeyCode.F11)  {
    			
    			clear();
    		}
    
    		
    	});
		table.setOnMouseClicked(e -> {
			
			posTable a=table.getItems().get(table.getSelectionModel().getSelectedIndex());
			System.out.println(a.getId()+" "+a.getItemCode());
			Alert a2=new Alert(AlertType.CONFIRMATION);
	    	a2.setHeaderText(null);
	    	//a1.initStyle(StageStyle.UNDECORATED);
	    	a2.setContentText("Do you want to remove item ( "+a.getItemCode()+" ) with qty ( "+a.getQty()+" ) from the bill");    		    	
	    	Optional<ButtonType> result = a2.showAndWait();
	    	if (result.get() == ButtonType.OK){
	    		
	    		try {
					
					
					Connection c1=dbCon.mycon();
					Statement s1=c1.createStatement();
					s1.executeUpdate("DELETE FROM `tems` WHERE  `id`='"+a.getId()+"'");
						
						Alert a11=new Alert(AlertType.WARNING);
				    	a11.setHeaderText(null);
				    	//a1.initStyle(StageStyle.UNDECORATED);
				    	a11.setContentText("Item removed Sucsessfully....");    		    	
				    	a11.showAndWait();
				    	
				    	
				    	
						
						Connection cc=dbCon.mycon();
						Statement ss=cc.createStatement();
						ResultSet rsc=ss.executeQuery(" SELECT * FROM `tems` WHERE billNo='"+txtBillNo.getText()+"' ");
						list=FXCollections.observableArrayList();
						while(rsc.next()){
							
						
							
							
							list.add(new posTable(rsc.getString("id"), rsc.getString("itemCode"),  rsc.getString("name"), rsc.getString("rate") , rsc.getString("qty"),rsc.getString("amount") ));
									
											
						}
						
						
						id.setCellValueFactory(new PropertyValueFactory<posTable, String> ("id"));
						itemCode.setCellValueFactory(new PropertyValueFactory<posTable, String> ("itemCode"));
						itemName.setCellValueFactory(new PropertyValueFactory<posTable, String> ("itemName"));
						rate.setCellValueFactory(new PropertyValueFactory<posTable, String> ("rate"));
						qty.setCellValueFactory(new PropertyValueFactory<posTable, String> ("qty"));
						ammount.setCellValueFactory(new PropertyValueFactory<posTable, String> ("ammount"));
						table.setItems(list);
						txtBarCode.requestFocus();
				    	
				    	return;
					
					
					
					
					
				} catch (Exception e1) {
					// TODO: handle exception
					System.out.println(e1);
				}
	    		
	    		
	    	}
	    	
			
			
		});
		
		
	}
    
    
    
    
    public void openBill(){
    	try {
			//Open the home window
    		FXMLLoader fxmloder=new FXMLLoader(getClass().getResource("../Views/Bill.fxml"));
			Parent root1=(Parent) fxmloder.load();
			
			Stage stage =new Stage();
			//stage.initStyle(StageStyle.TRANSPARENT);
			//stage.setMaximized(true);
			stage.setScene(new Scene(root1));
			stage.show();
			String point=Double.toString(cusPoints);
			((BillController)fxmloder.getController()).setLetter(txtBillNo.getText(),txtNetTotal.getText(),txtCashRcvd.getText(),txtBalance.getText(),point,lblEID.getText());;
			
			
			//Close the current window(call above method)
			//close();
			
			
			
			
		} catch(Exception e1) {
			e1.printStackTrace();
		}
    	
    }
    
    
}
    
    
    


