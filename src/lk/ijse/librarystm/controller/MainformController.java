/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.librarystm.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author harsh
 */
public class MainformController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imgBook;
    @FXML
    private ImageView imgReturn;
    @FXML
    private ImageView imgReport;
    @FXML
    private ImageView imgBarrow;
    @FXML
    private ImageView imgMember;
    @FXML
    private Label lbldisplay_Text;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        if(event.getSource() instanceof ImageView){
            ImageView icon = (ImageView) event.getSource();
            
            Parent root = null;
            
            switch(icon.getId()){ 
                case "imgBarrow":
                root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/librarystm/view/BarrowBooksForm.fxml"));
                break;
                case "imgBook":
                root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/librarystm/view/AddBookForm.fxml"));
                break;
                case "imgReturn":
                root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/librarystm/view/ReturnBooksForm.fxml"));
                break;
                case "imgMember":
                root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/librarystm/view/AddMemberForm.fxml"));
                break;
                case "imgReport":
                root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/librarystm/view/MemberHistoryForm.fxml"));
                break;
            }
            
            if (root != null){
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();
                
                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();
                
            }
        }
        
    }   

    @FXML
    private void imgMouseEntered_On_Action(MouseEvent event) {
        if(event.getSource() instanceof ImageView){
            ImageView img = (ImageView) event.getSource();
            
            Parent root = null;
            
            switch(img.getId()){
                case "imgBarrow":
                lbldisplay_Text.setText("BARROWINGS");
                break;
                case "imgBook":
                lbldisplay_Text.setText("ADD BOOK");
                break;
                case "imgReturn":
                lbldisplay_Text.setText("RETURNING");
                break;
                case "imgMember":
                lbldisplay_Text.setText("ADD MEMBER");
                break;
                case "imgReport":
                lbldisplay_Text.setText("MEMBER HISTORY");
                break;
                
            }
        
            
            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), img);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play(); 
            
            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            img.setEffect(glow); 
        }    
            
    }
  
}
