/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.librarystm.controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.librarystm.main.LibraryStm;

/**
 * FXML Controller class
 *
 * @author harsh
 */
public class MemberHistoryFormController implements Initializable {

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
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private TableView<?> tblMemberHistoryView;
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void imgGo_Back_On_Action(MouseEvent event) {
        LibraryStm.navigateToHome(root, (Stage) this.root.getScene().getWindow());
    }

    @FXML
    private void FinfBookInfo_On_Action(KeyEvent event) {
    }

    @FXML
    private void btnMemberSearch_On_Action(ActionEvent event) {
    }

    @FXML
    private void btnCancel_On_Action(ActionEvent event) {
    }
    
}
