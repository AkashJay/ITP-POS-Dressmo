package models;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.Painter;

import com.sun.prism.Graphics;

import application.dbCon;
import javafx.collections.FXCollections;

public class BillPrint {
	String body;
	public void print(String billNO){
		
		PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.defaultPage();
        Paper paper = new Paper();
        paper = new Paper();
        //System.out.println("w"+paper.getWidth()+"H"+paper.getHeight());
        paper.setSize(612,842);
        paper.setImageableArea(30,0,552,396);
        //paper.setSize(420,595);
        //paper.setImageableArea(20,47,380,501);
        
        pageFormat.setPaper(paper);
        pageFormat.setOrientation(PageFormat.PORTRAIT);
      
        String head = String.format("%5s\n\n",   "DRESSMO FASHION");
        String xx = String.format("%5s\n",   "Customer Bill");
        body=String.format("%5s\n",   "");
        
        try {
        	
        	Connection cc=dbCon.mycon();
			Statement ss=cc.createStatement();
			ResultSet rsc=ss.executeQuery(" SELECT * FROM `tems` WHERE billNo='"+billNO+"' ");
			
			while(rsc.next()){
				
				body=body+rsc.getString("itemCode")+" "+rsc.getString("rate")+" "+rsc.getString("qty")+" "+rsc.getString("amount")+"\n";
			
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
        String bill=head+xx+body;
       
      //  printerJob.setPrintable(jTextPane2.getPrintable(null, null), pageFormat);
       // printerJob.setPrintable(new MyPrintable(), pageFormat);
        System.out.println(bill);
        System.out.println("biiiiiiii");
        try{
        printerJob.print();
       // JOptionPane.showMessageDialog(rootPane,"RCPT "+jTextField7.getText()+"Print Completed!");
    }   catch (PrinterException ex) { 
           // Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	

}
