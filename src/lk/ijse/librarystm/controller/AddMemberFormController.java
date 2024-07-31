/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.librarystm.controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.librarystm.db.DBConnection;
import lk.ijse.librarystm.main.LibraryStm;
import lk.ijse.librarystm.util.tblmodel.MemberTM;

/**
 * FXML Controller class
 *
 * @author harsh
 */
public class AddMemberFormController implements Initializable {

    @FXML
    private JFXTextField txtMemberId;
    @FXML
    private JFXTextField txtMemberName;
    @FXML
    private JFXTextField txtMemberNic;
    @FXML
    private JFXTextField txtMemberAddress;
    @FXML
    private JFXTextField txtContactNo;
    @FXML
    private Button btnSave;
    @FXML
    private TableView<MemberTM> tblMemberView;
    @FXML
    private AnchorPane root;
    
    private ArrayList<MemberTM> members = new ArrayList<>();
    @FXML
    private Button btnRemove;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblMemberView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberID"));
        tblMemberView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblMemberView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("NIC"));
        tblMemberView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        tblMemberView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("address"));
        
        loadMemberTable();
    }


    private void loadMemberTable(){
        Connection connection = null;
        try {
            connection  = DBConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            String query = "Select * from member order by memberid ASC;";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                MemberTM membertm = new MemberTM(rs.getString("memberid"),rs.getString("name"),rs.getString("nic"),rs.getString("contactno"),rs.getString("address"));
                members.add(membertm);
            }
            tblMemberView.setItems(FXCollections.observableArrayList(members));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AddMemberFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void imgGo_Back_On_Action(MouseEvent event) {
        LibraryStm.navigateToHome(root, (Stage) this.root.getScene().getWindow());
    }
    
    

    

    @FXML
    private void btnMemberAdd_On_Action(ActionEvent event) {
        Connection connection = null;
        String memberID = txtMemberId.getText();
        String memberName  = txtMemberName.getText();
        String NIC = txtMemberNic.getText();
        String contactNo = txtContactNo.getText();
        String address = txtMemberAddress.getText();
        try {
            connection = DBConnection.getInstance().getConnection();
            String query = "select memberid from member where memberid =?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setObject(1, memberID);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                Alert alert = new Alert(Alert.AlertType.WARNING,"this memberid is already in the database...",ButtonType.OK);
                alert.show();
            }else{
                String query1 = "insert into member values(?,?,?,?,?)";
                PreparedStatement pstm1 = connection.prepareStatement(query1);
                pstm1.setObject(1, memberID);
                pstm1.setObject(2, NIC);
                pstm1.setObject(3, memberName);
                pstm1.setObject(4, address);
                pstm1.setObject(5, contactNo);
                
                int rs1 = pstm1.executeUpdate();
                if(rs1>0){
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION,"member has been successfully updated",ButtonType.OK);
                    alert1.show();
                    loadMemberTable();
                }
                
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddMemberFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddMemberFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void btnRemove_On_Action(ActionEvent event) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AddMemberFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(tblMemberView.getSelectionModel().getSelectedItem()== null){
            return;
        }else{
            try {
                String  memberid = tblMemberView.getSelectionModel().getSelectedItem().getMemberID();
                String query = "delete from member where memberid ='"+memberid+"'";
                Statement stmt = connection.createStatement();
                int rs3 = stmt.executeUpdate(query);
                if(rs3>0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"member has been successfully removed...",ButtonType.OK);
                    alert.show();
                    
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(AddMemberFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadMemberTable();
    }
    

    @FXML
    private void FinfBookInfo_On_Action(KeyEvent event) {
    }

    
    
}
