/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descriptivestatistics;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;  
import javafx.stage.Stage;

/**
 *
 * @author Juan Carlos
 */
public class MeasuresController implements Initializable {   
    Stage stage;
    Parent root;
    @FXML private Label meanLabel;    
    @FXML private Label modeLabel;
    @FXML private Label medianLabel;
    
    @FXML private Button proceed1;
    @FXML private Button proceed2;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(GlobalContext.meanOption){
            if(GlobalContext.closeEndedOption)
                meanLabel.setText("Mean = " + GlobalContext.groupedMean + "\n" + 
                                            "Variance = " + GlobalContext.groupedVariance + "\n" +
                                            "Standard Deviation = " + GlobalContext.groupedStandardDeviation);
            else {
                meanLabel.setText("Cannot compute, open-ended setting.");
            }
        }
        
        if(GlobalContext.modeOption) {
            modeLabel.setText("Modal Class: " + "\n" + GlobalContext.groupedMode + "\n " +
                GlobalContext.getModalCounter());
        }
        
        if(GlobalContext.medianOption) {
            medianLabel.setVisible(true);
        }
        
        System.out.println(GlobalContext.meanOption + "\n" 
                        + GlobalContext.medianOption + "\n"
                        + GlobalContext.modeOption);
    }
    
    @FXML
    public void backToTableAction() throws IOException {
        stage = (Stage) proceed1.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("GroupedDataResults.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void backToMainMenuAction() throws IOException {
        stage = (Stage) proceed2.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MainTemplate.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        
}
