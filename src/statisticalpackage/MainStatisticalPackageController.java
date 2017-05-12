/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statisticalpackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Juan Carlos
 */
public class MainStatisticalPackageController implements Initializable {
    Parent parent;
    Stage stage;
    @FXML Button exit;
    @FXML Button proceed;    
    @FXML RadioButton lab1;
    @FXML RadioButton lab2;
    @FXML RadioButton lab3;    
    @FXML Label errorMessage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }            
    
    @FXML
    public void handleProceedAction1(KeyEvent event) throws IOException {        
        if(event.getCode() == KeyCode.ENTER) {            
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));        
            Scene scene = new Scene(root);        
            stage.setScene(scene);
            stage.show();    
        }
    }
    
    @FXML
    public void exitAction(ActionEvent event) throws IOException {
        System.exit(0);
    }
    
    @FXML
    public void handleProceedAction2(ActionEvent event) throws IOException {
        if(!lab1.isSelected() && !lab2.isSelected() && !lab3.isSelected()) {
            errorMessage.setText("Must select an option before proceeding");
        } else {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root;
            if(lab1.isSelected()) {
                root = FXMLLoader.load(getClass().getResource("/samplingmethods/FXMLDocument.fxml"));
            } else if(lab2.isSelected()) {            
                root = FXMLLoader.load(getClass().getResource("/datapresentation/MainTemplate.fxml"));        
            } else {
                root = FXMLLoader.load(getClass().getResource("/descriptivestatistics/MainTemplate.fxml"));                    
            }
            Scene scene = new Scene(root);        
            stage.setScene(scene);
            stage.show();
        }
    }
}
