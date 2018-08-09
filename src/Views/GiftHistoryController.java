package Views;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.dbCon;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import models.AllValidation;
import models.CustomerTable;
import models.CustomerValidation;
import models.giftedTable;
public class GiftHistoryController implements Initializable {
	
	
	@FXML
    private TitledPane giftedTable;

    @FXML
    private TableView<giftedTable> giftTable;

    @FXML
    private TableColumn<giftedTable, String> mobileNo;

    @FXML
    private TableColumn<giftedTable, String> date;

    @FXML
    private TableColumn<giftedTable, String> type;

    @FXML
    private JFXTextField filterField;

    @FXML
    private JFXTextField txtname;

    @FXML
    private JFXTextField txtMobile;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private JFXTextField txtType;
    
    
    public ObservableList<giftedTable> list;
    
    
    private int minute;
   	private int hour;
   	private int second;
   	private int day;
   	private int month;
   	private int year;
   static LocalDate currentDate;
   int x=0;
   
   public void datee(){
	   
	   Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {            
	        Calendar cal = Calendar.getInstance();
	        second = cal.get(Calendar.SECOND);
	        minute = cal.get(Calendar.MINUTE);
	        hour = cal.get(Calendar.HOUR);
	        day=cal.get(Calendar.DAY_OF_MONTH);
	        month=cal.get(Calendar.MONTH);
	        year=cal.get(Calendar.YEAR);
	      
	        	currentDate=LocalDate.parse(year + "-" + (month+1) + "-" +day);
	        	x++;
	        	if(x==1){
	        		cancelGiftAutomatically();
	        	}
	        	else if(x==2){
	        		return;
	        	}
	        	
	        
	    
	    }),
	         new KeyFrame(Duration.seconds(1))
	    );
	    clock.setCycleCount(Animation.INDEFINITE);
	    clock.play();
   }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		addTableValue();
		takeTableValue();
		edidable();
		filter();
		datee();
	}
	

	
	//**************Add values to gifted customer table*********
public void addTableValue(){
		
		try {
			
			Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `gift` WHERE 1 ");
			
			list=FXCollections.observableArrayList();
			while(rsc.next()){
				
				list.add(new giftedTable(rsc.getString("cname"), rsc.getString("type"), rsc.getString("mobileNo"), rsc.getString("date"), rsc.getString("email"), rsc.getString("nic")));
				
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	
		mobileNo.setCellValueFactory(new PropertyValueFactory<giftedTable, String> ("mobileNo"));
		date.setCellValueFactory(new PropertyValueFactory<giftedTable, String> ("date"));
		type.setCellValueFactory(new PropertyValueFactory<giftedTable, String> ("type"));
		
		giftTable.setItems(list);
		edidable();
		
	}


//**************Take table values*********
public void takeTableValue() {
			// TODO Auto-generated method stub
			giftTable.setOnMouseClicked(e -> {
				
				giftedTable a=giftTable.getItems().get(giftTable.getSelectionModel().getSelectedIndex());
				txtEmail.setText(a.getEmail());
				txtMobile.setText(a.getMobileNo());
				txtname.setText(a.getCname());
				txtNic.setText(a.getNic());
				txtType.setText(a.getType());
				edidable();
		
			});
		
}
	

//**************Clear all fields*********
public void clear(){
			
			txtEmail.setText("");
			txtMobile.setText("");
			txtname.setText("");
			txtNic.setText("");
			txtType.setText("");
			edidable();
			
}


//**************GDisable editable in the text fileds*********
public void edidable(){
			
			txtEmail.setEditable(false);
			txtMobile.setEditable(false);
			txtname.setEditable(false);
			txtNic.setEditable(false);
			txtType.setEditable(false);
			
}
		
		
	
//**************Delete customer gift*********		
public void deleteGift(){
	
			
			if(!txtname.getText().equals("")){
				
				Alert a1=new Alert(AlertType.CONFIRMATION);
		    	a1.setHeaderText(null);
		    	//a1.initStyle(StageStyle.UNDECORATED);
		    	a1.setContentText("Do you want to delete ( "+txtname.getText()+" ) Customers gift of ( "+txtType.getText()+" ) ??");    		    	
		    	Optional<ButtonType> result = a1.showAndWait();
		    	if (result.get() == ButtonType.OK){
		    		
		    		try {
						
						Connection c1=dbCon.mycon();
						Statement s1=c1.createStatement();
						s1.executeUpdate(" DELETE FROM `gift` WHERE `mobileNo`='"+txtMobile.getText()+"' ");
							System.out.println("customer added");
							
							Alert a2=new Alert(AlertType.INFORMATION);
					    	a2.setHeaderText(null);
					    	//a1.initStyle(StageStyle.UNDECORATED);
					    	a2.setContentText("( "+txtMobile.getText()+") Gift removed successfully..\nMessage sent sucssefully to ( "+txtMobile.getText()+") informing that the gift was cancled??");    		    	
					    	Optional<ButtonType> result11 = a2.showAndWait();
					    	
					    	
					    	addTableValue();
					    	clear();
					    	
					
						
						
						
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e);
					}
		    		
		    		
		    	}
		    	else if (result.get() == ButtonType.CANCEL){
		    		Alert a2=new Alert(AlertType.CONFIRMATION);
			    	a2.setHeaderText(null);
			    	//a1.initStyle(StageStyle.UNDECORATED);
			    	a2.setContentText(txtMobile.getText()+" canceling of gift process cancled\n Do you want to clear the fields");    		    	
			    	Optional<ButtonType> result1 = a2.showAndWait();
			    	if (result1.get() == ButtonType.OK){
			    		
			    		clear();
			    	}
		    		
		    		
		    	}
			}
			else{
				Alert a3=new Alert(AlertType.INFORMATION);
		    	a3.setHeaderText(null);
		    	//a1.initStyle(StageStyle.UNDECORATED);
		    	a3.setContentText("To cancel / delete a gift offered\nplease select a row u want to cancle from the table...");    		    	
		    	a3.showAndWait();
				
			}


		}

//**************Filter table data*********
public void filter(){
			
			  // 1. Wrap the ObservableList in a FilteredList (initially display all data).
	        FilteredList<giftedTable> filteredData = new FilteredList<>(list, p -> true);

	        // 2. Set the filter Predicate whenever the filter changes.
	        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
	            filteredData.setPredicate(person -> {
	                // If filter text is empty, display all persons.
	                if (newValue == null || newValue.isEmpty()) {
	                    return true;
	                }

	                // Compare first name and last name of every person with filter text.
	                String lowerCaseFilter = newValue.toLowerCase();

	                if (person.getMobileNo().toLowerCase().contains(lowerCaseFilter)) {
	                    return true; // Filter matches first name.
	                } 
	                return false; // Does not match.
	            });
	        });

	        // 3. Wrap the FilteredList in a SortedList. 
	        SortedList<giftedTable> sortedData = new SortedList<>(filteredData);

	        // 4. Bind the SortedList comparator to the TableView comparator.
	        sortedData.comparatorProperty().bind(giftTable.comparatorProperty());

	        // 5. Add sorted (and filtered) data to the table.
	        giftTable.setItems(sortedData);
			
		}
		
		
public void cancelGiftAutomatically(){
			
			String expire;
			LocalDate d;
			
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Connection cc=dbCon.mycon();
				Statement ss=cc.createStatement();
				ResultSet rsc=ss.executeQuery(" SELECT * FROM `gifttype` WHERE 1 ");
				
				
				while(rsc.next()){
				
					
					d=LocalDate.parse(rsc.getString("endDate"));
					if(currentDate.compareTo(d) > 0){
						
						Alert a11=new Alert(AlertType.WARNING);
				    	a11.setHeaderText(null);
				    	//a1.initStyle(StageStyle.UNDECORATED);
				    	a11.setContentText("Warning Gift Expired\n( Gift Type ->  "+rsc.getString("name")+" ) --- ( End Date -> "+rsc.getString("endDate")+" ) Gift type expired..Remove the gift type from the table");    		    	
				    	a11.show();
					
					}	
					else{
						
						System.out.println("not expired");
					}
									
				}
				
			} catch (Exception e) {
				System.out.println(e);
				System.out.println();
			}
			
			
		}
		
		

}
