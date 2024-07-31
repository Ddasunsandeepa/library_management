/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.librarystm.main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author harsh
 */
public class LibraryStm extends Application {

    public static void navigateTOHome() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        Parent root;
        try {
            root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/librarystm/view/mainform.fxml"));
            Scene mainScene = new Scene(root);
                primaryStage.setTitle("IJSE FX LIBRARY SYSTEM - 2018 : DEP");
                primaryStage.setScene(mainScene);
                primaryStage.setResizable(true);
                primaryStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(LibraryStm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void navigateToHome(Node node,Stage mainStage){
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), node);
            tt.setFromX(0);
            tt.setToX(-node.getScene().getWidth());
            tt.play();
            
            Platform.runLater(()->{
            
                try {
                    Parent root = FXMLLoader.load(LibraryStm.class.getResource("/lk/ijse/librarystm/view/mainform.fxml"));
                    Scene mainScene = new Scene(root);
                    mainStage.setScene(mainScene);
                    
                    TranslateTransition tt1 = new TranslateTransition(Duration.millis(300), root.lookup("AnchorPane"));
                    tt1.setToX(0);
                    tt1.setFromX(-mainScene.getWidth());
                    tt1.play();
                    
                    mainStage.centerOnScreen();
                } catch (IOException ex) {
                    Logger.getLogger(LibraryStm.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
