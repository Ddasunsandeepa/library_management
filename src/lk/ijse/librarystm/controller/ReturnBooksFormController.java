/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.librarystm.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * FXML Controller class
 *
 * @author harsh
 */
public class ReturnBooksFormController implements Initializable {

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
    private Label txtWarning;

    private void getReturns() {
        String memberid = txtMemberId.getText();
        String barrow = "select count(returned)as return_count from barrow_detail where memberid=? and returned=1";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement prst = connection.prepareStatement(barrow);
            prst.setObject(1, memberid);
            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                int num = rs.getInt("return_count");
                if (num > 0) {
                    txtWarning.setText(num + ""+" not returned");
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ReturnBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
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
        LibraryStm.navigateToHome(root, (Stage) this.root.getScene().getWindow());
    }

   

    @FXML
    private void btnBookAdd_On_Action(ActionEvent event) {
        String bookid=txtBkId1.getText();
        ArrayList<BookTM> book=new ArrayList<>();
        try {
            String query="select from book where bookid=?";
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement prst=connection.prepareStatement(query);
            prst.setObject(1,bookid);
            ResultSet rs=prst.executeQuery();
            BookTM bks=new BookTM(rs.getString("bookid"),
                    rs.getString("isbn"),rs.getString("name"),rs.getString(("author")),rs.getString(" publisher"));
            book.add(bks);
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReturnBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReturnBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<BookTM> bkts=FXCollections.observableArrayList(book);
        tblBookView.setItems(bkts);
    }

    @FXML
    private void btnCancel_On_Action(ActionEvent event) {
        txtBkId1.setText("");
        txtBkName1.setText("");
        txtMemberId.setText("");
        txtMemberName.setText("");
    }

    @FXML
    private void btnReturn_On_Action(ActionEvent event) {
        String memberid=txtMemberId.getText();
        String bookid=txtBkId1.getText();
        String Query="Update barrow_detail set returned=1 where memberid=? and bookid=?";
            String query="Insert into return_detail(bookid,memberid)values(?,?)";
        try {
           Connection connection=DBConnection.getInstance().getConnection();
           PreparedStatement prst=connection.prepareStatement(query);
           prst.setObject(1,memberid);
           prst.setObject(2,bookid);
           
           PreparedStatement prst1=connection.prepareStatement(Query);
           prst1.setObject(1,memberid);
           prst1.setObject(2,bookid);
           
           int result1=prst1.executeUpdate();
           int result=prst.executeUpdate();
            if (result>0 && result1>0) {
                Alert a=new Alert(Alert.AlertType.ERROR,"values are not inserted properly",ButtonType.OK);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReturnBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReturnBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void member_txtEnter_pressed(KeyEvent event) {
        getReturns();
        if (event.getCode() == KeyCode.ENTER) {
            String memberid=txtMemberId.getText();
            String query = "select name from member where memberid=?";
            try {
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement prst = connection.prepareStatement(query);
                prst.setObject(1, memberid);
                ResultSet rs = prst.executeQuery();
                if (rs.next()) {
                    
                    txtMemberName.setText(rs.getString("name"));

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "No member id exisits", ButtonType.OK);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ReturnBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ReturnBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void txtBookid_onkeyPressed(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER){
             String bookid=txtBkId1.getText();
             String query="select name from book where bookid=?";
             
            try {
                Connection connection=DBConnection.getInstance().getConnection();
                PreparedStatement prst=connection.prepareStatement(query);
                prst.setObject(1,bookid);
                ResultSet rs=prst.executeQuery();
                if(rs.next()){
                    txtBkName1.setText(rs.getString("name"));
                }else{
                    Alert a= new Alert(Alert.AlertType.ERROR, "No such bookid exists", ButtonType.OK);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ReturnBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ReturnBooksFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @FXML
    private void FinfBookInfo_On_Action(KeyEvent event) {
    }

}
