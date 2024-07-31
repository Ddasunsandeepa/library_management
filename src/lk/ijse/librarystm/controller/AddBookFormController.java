/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.librarystm.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.InputStream;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.librarystm.db.DBConnection;
import lk.ijse.librarystm.main.LibraryStm;
import lk.ijse.librarystm.util.tblmodel.BookTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author harsh
 */
public class AddBookFormController implements Initializable {

    @FXML
    private JFXTextField txtBkId;
    @FXML
    private JFXTextField txtBkName;
    @FXML
    private JFXTextField txtBkIsbn;
    @FXML
    private JFXTextField txtAuthor;
    @FXML
    private JFXTextField txtPublisher;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private TableView<BookTM> tblBookView;
    @FXML
    private AnchorPane root;
    @FXML
    private Button Remove_btn;

    private  void loadBooks(){
        try {
            Connection connection=null;
            connection=DBConnection.getInstance().getConnection();
            PreparedStatement prst=connection.prepareStatement("Select * from book");
            ResultSet rs=prst.executeQuery();
            ArrayList<BookTM> albook = new ArrayList<>();
            while(rs.next()){
                BookTM book=new BookTM(rs.getString(1),
                rs.getString(3),
                rs.getString(2),rs.getString(4),
                rs.getString(5));
               albook.add(book);
            }
            ObservableList<BookTM> olBK = FXCollections.observableArrayList(albook);

            tblBookView.setItems(olBK);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddBookFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddBookFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblBookView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookID"));
        tblBookView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookName"));
        tblBookView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        tblBookView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblBookView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("publisher"));
        loadBooks();
    }    

    @FXML
    private void imgGo_Back_On_Action(MouseEvent event) {
        LibraryStm.navigateToHome(root, (Stage) this.root.getScene().getWindow());
    }

    

    @FXML
    private void btnSave_On_Action(ActionEvent event) {
        try {
            Connection connection=null;
            String id=txtBkId.getText();
            String isbn=txtBkIsbn.getText();
            String name=txtBkName.getText();
            String author=txtAuthor.getText();
            String publisher=txtPublisher.getText();
            
            connection=DBConnection.getInstance().getConnection();
            PreparedStatement prst=connection.prepareStatement("Insert into book values(?,?,?,?,?)");
            prst.setString(1,id);
            prst.setString(2,isbn);
            prst.setString(3,name);
            prst.setString(4,author);
            prst.setString(5, publisher);
            prst.execute();
            loadBooks();
            
             } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddBookFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddBookFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnCancel_On_Action(ActionEvent event) {
        txtAuthor.setText("");
        txtBkId.setText("");
        txtBkName.setText("");
        txtPublisher.setText("");
        txtBkIsbn.setText("");
    }

    @FXML
    private void btnSearch_On_Action(ActionEvent event) {
        Connection coonection = null;
        String name = txtBkName.getText();
        String query = "select * from book where name = '"+name+"'";
        try {
            Statement stmt = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                txtBkId.setText(rs.getString("bookid"));
                txtBkIsbn.setText(rs.getString("isbn"));
                txtAuthor.setText(rs.getString("author"));
                txtPublisher.setText(rs.getString("author"));
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"There is no such a book",ButtonType.OK);
                alert.show();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddBookFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddBookFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Remove_btn(MouseEvent event) {
        if(tblBookView.getSelectionModel().getSelectedItem()==null){
            return;
        }else{
            String id=tblBookView.getSelectionModel().getSelectedItem().getBookID();
        try {
            Connection connection=null;
            connection=DBConnection.getInstance().getConnection();
            PreparedStatement prst=connection.prepareStatement("Delete  from book where bookid=?");
            prst.setObject(1, id);
           int i= prst.executeUpdate();
           if(i<0){
               Alert a=new  Alert(Alert.AlertType.ERROR,"not deleted",ButtonType.OK);
               a.show();
           }else{
           Alert a=new Alert(Alert.AlertType.INFORMATION,"Deleted succesfully",ButtonType.OK);
                        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddBookFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddBookFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        loadBooks();
        
    }

    @FXML
    private void sql_report(ActionEvent event) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/Report/firstreport.jasper");
            HashMap map=new HashMap();
            JasperPrint jasp = JasperFillManager.fillReport(resourceAsStream,map,DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddBookFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddBookFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(AddBookFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
}
