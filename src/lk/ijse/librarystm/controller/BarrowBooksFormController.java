/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.librarystm.controller;

import com.itextpdf.awt.geom.misc.Messages;
import com.jfoenix.controls.JFXTextField;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.librarystm.db.DBConnection;
import lk.ijse.librarystm.main.LibraryStm;
import lk.ijse.librarystm.util.tblmodel.BookTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author harsh
 */
public class BarrowBooksFormController implements Initializable {

    @FXML
    private JFXTextField txtMemberId;
    @FXML
    private JFXTextField txtMemberName;
    @FXML
    private JFXTextField txtBkId1;
    @FXML
    private JFXTextField txtBkName1;
    @FXML
    private TableView<BookTM> tblBookView;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private AnchorPane root;
    @FXML
    private Label lblwarning;
    private  int count=0;
    private ArrayList<BookTM> book=new ArrayList<>();
    @FXML
    private JFXTextField txtbarrow;
    @FXML
    private JFXTextField txtdate;
    @FXML
    private JFXTextField txtReturned;
    
    java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
    private void loadBoooks(){
        String bookid=txtBkId1.getText();
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement prst=connection.prepareStatement("Select from book where bookid=?");
            prst.setObject(1,bookid);
            ResultSet rs=prst.executeQuery();
            
            if (rs.next()) {
                BookTM BT=new BookTM(rs.getString("bookid"),
                rs.getString("isbn"),rs.getString(" name"),rs.getString("author"),rs.getString("publisher"));
                book.add(BT);
                tblBookView.setItems(FXCollections.observableArrayList(book));
            }
             

             

            
                    } catch (ClassNotFoundException ex) {
            Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       tblBookView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookID"));
        tblBookView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookName"));
        tblBookView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        tblBookView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblBookView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("publisher"));
        
        
    }    

    @FXML
    private void imgGo_Back_On_Action(MouseEvent event) {
        LibraryStm.navigateToHome(root, (Stage)this.root.getScene().getWindow());
    }

    
    @FXML
    private void btnBookAdd_On_Action(ActionEvent event) {
        
       
       
        String  query= "SELECT * from book where bookid = ?";
        try {
            
            PreparedStatement pre = DBConnection.getInstance().getConnection().prepareStatement(query);
            pre.setString(1, txtBkId1.getText());
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                if(count<=5)
            
            {
                
                BookTM books = new BookTM();
                books.setBookID(txtBkId1.getText());
                books.setBookName(rs.getString("name"));
                books.setISBN(rs.getString("isbn"));
                books.setAuthor(rs.getString("author"));
                books.setPublisher(rs.getString("publisher"));
                book.add(books);
                }
            }
            tblBookView.setItems(FXCollections.observableArrayList(book));
        } catch (Exception e) {
        }
        
        
    }

    @FXML
    private void btnCancel_On_Action(ActionEvent event) {
        txtBkId1.setText("");
        txtBkName1.setText("");
    }

    @FXML
    private void btnBarrow_On_Action(ActionEvent event) {
        
      if(count<=5){
          try {
              Connection connection=DBConnection.getInstance().getConnection();
              PreparedStatement prst=connection.prepareStatement("Insert into barrow_detail values(?,?,?,?,?)");
              prst.setObject(1,txtbarrow.getText());
              prst.setObject(2,txtBkId1.getText());
              prst.setObject(3,txtMemberId.getText());
              prst.setObject(4,date);
              prst.setObject(5,txtReturned.getText());
              
              int affected=prst.executeUpdate();
              
              if (affected == 0){
                new Alert(Alert.AlertType.ERROR, "Failed to place the order", ButtonType.OK).show();
                return;
            }
                      } catch (ClassNotFoundException ex) {
              Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
          } catch (SQLException ex) {
              Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
    }

    @FXML
    private void FinfBookInfo_On_Action(KeyEvent event) {
    }

    
        
    
private void showlabel(){
        try {
            String id=txtMemberId.getText();
            Connection connection;
            connection=DBConnection.getInstance().getConnection();
            PreparedStatement prst=connection.prepareStatement("Select returned from  barrow_detail where memberid=?");
            prst.setObject(1, id);
            ResultSet rs=prst.executeQuery();
            if(rs.next()) {
                    if(rs.getInt("returned")==1){
                
                lblwarning.setText("book is borrowed");
                    }
            }else{
                lblwarning.setText("book is not borrowed");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
}

    @FXML
    private void enter_press(KeyEvent event) {
        String bookid=txtBkId1.getText();
        if (event.getCode() == KeyCode.ENTER){
            try {
                Connection connection=DBConnection.getInstance().getConnection();
                String guery="select name from book where bookid=?";
                PreparedStatement prst=connection.prepareStatement(guery);
                prst.setObject(1, bookid);
                ResultSet rs=prst.executeQuery();
                if (rs.next()) {
                    txtBkName1.setText(rs.getString("name"));
                    
                }else{
                    Alert a=new Alert(Alert.AlertType.ERROR,"no Name",ButtonType.OK);
                    a.show();;
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @FXML
    private void report_barrowings(ActionEvent event) {
        try {
            InputStream resourceAsStream=getClass().getResourceAsStream("/Report/Barrowings.jasper");
            HashMap map=new HashMap();
           // map.put("total",800.00); if parameters are added
            JasperPrint jasp = JasperFillManager.fillReport(resourceAsStream,map,DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasp);
            JasperPrintManager.printReport(jasp,false);
            JasperExportManager.exportReportToPdf(jasp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnSearch_action(MouseEvent event) {
        
        String memberid=txtMemberId.getText();
        
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement prst=connection.prepareStatement("select name from member where memberid=?");
            prst.setObject(1,memberid);
            ResultSet rs=prst.executeQuery();
            if(rs.next()){
                txtMemberName.setText(rs.getString("name"));
            }else
            {
                Alert a=new Alert(Alert.AlertType.ERROR,"NO such member",ButtonType.OK);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BarrowBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showlabel();
    }
}