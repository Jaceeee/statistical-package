/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descriptivestatistics;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Juan Carlos
 */

public class EntryController implements Initializable {
    Stage stage;
    Parent root;
//    For the menu    
    @FXML private Button proceed1;
    @FXML private Button exit;
    @FXML private RadioButton groupedDataRadio;
    @FXML private RadioButton ungroupedDataRadio;
    @FXML private Label errorMessage;    
//    For the title template
    @FXML private Button proceed2;
    @FXML private Button back1;
    @FXML private TextField titleField;
    @FXML private TextField inputLimitField;
    
    @FXML
    private void handleProceedAction1(ActionEvent event) throws IOException {
        if(GlobalContext.mainChoiceSelected()) {
            stage = (Stage) proceed1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("TitleTemplate.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            GlobalContext.groupedOption = false;
            GlobalContext.ungroupedOption = false;
        } else{
            errorMessage.setText("Must select an option before proceeding");
        }
    }
    
    @FXML
    private void handleProceedAction2(ActionEvent event) throws IOException {
        if(titleField.getText() != "" && inputLimitField.getText() != "" && Integer.parseInt(inputLimitField.getText()) >= 3 && GlobalContext.openCloseEndedChoiceSelected()) {
            if(GlobalContext.openEndedOption) {
                List<String> choices = new ArrayList<>();
                choices.add("First");
                choices.add("Last");
                choices.add("Both");                

                ChoiceDialog<String> dialog = new ChoiceDialog<>("First", choices);
                dialog.setTitle("Open Ended Selection");
                dialog.setHeaderText("Which class interval should be open-ended?");
                dialog.setContentText("Select your option:");

                // Traditional way to get the response value.
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    if(result.get().equals("First")){
                        GlobalContext.openFirstClassInterval = true;
                    } else if(result.get().equals("Last")){
                        GlobalContext.openLastClassInterval = true;
                    } else if(result.get().equals("Both")){
                        GlobalContext.openFirstClassInterval = true;
                        GlobalContext.openLastClassInterval = true;
                    }
                }
            }
            
            List<String> choices = new ArrayList<>();
            choices.add("Integer");
            choices.add("Floating point");                     

            ChoiceDialog<String> dialog = new ChoiceDialog<>("Integer", choices);
            dialog.setTitle("Data type selection");
            dialog.setHeaderText("Which data type will you be entering?");
            dialog.setContentText("Select your option:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                if(result.get().equals("Integer")){
                    GlobalContext.inputType = 3;
                } else {
                    GlobalContext.inputType = 4;                
                }
            }
            GlobalContext.title = titleField.getText();
            GlobalContext.n = Integer.parseInt(inputLimitField.getText());
            stage = (Stage) proceed2.getScene().getWindow();
            if(GlobalContext.groupedOption) {
                root = FXMLLoader.load(getClass().getResource("GroupedDataSetEntry.fxml"));           
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            if(titleField.getText() == "") {
                errorMessage.setText("Data must have title.");
            } else if (!Validation.isNumeric(inputLimitField.getText())) {
                errorMessage.setText("Input must be an integer or not blank.");
            } else if (!Validation.checkGroupedLimit(Integer.parseInt(inputLimitField.getText()))) {                
                errorMessage.setText("Data set must contain at least 3 values.");
            }
        }   
    }
    
    @FXML 
    private void handleBackAction1(ActionEvent event) throws IOException {
        GlobalContext.n = 0;
        GlobalContext.title = "";
        GlobalContext.groupedOption = false;
        GlobalContext.ungroupedOption = false;
        stage = (Stage) proceed2.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MainTemplate.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleExitAction(ActionEvent event) throws IOException {
        System.exit(0);
    }
    
    @FXML
    public void enterListener1(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER) {
            proceed1.fire();
        }
    }
    
    @FXML
    public void enterListener2(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER
                && titleField.getText() != ""
                && inputLimitField.getText() != ""
                && GlobalContext.openCloseEndedChoiceSelected()){
            proceed2.fire();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void setOpenEndedChoice() {
        if(GlobalContext.openEndedOption == false){
            GlobalContext.openEndedOption = true;
            GlobalContext.closeEndedOption = false;            
        } else {
            GlobalContext.openEndedOption = false;
        }
    }
    
    @FXML
    private void setCloseEndedChoice() {
        if(GlobalContext.closeEndedOption == false){
            GlobalContext.closeEndedOption = true;
            GlobalContext.openEndedOption = false;            
        } else {
            GlobalContext.closeEndedOption = false;
        }
    }
    
    @FXML
    private void setGroupedChoice() {        
        if(GlobalContext.groupedOption == false) {
            GlobalContext.groupedOption = true;
            GlobalContext.ungroupedOption = false;
        } else {
            GlobalContext.groupedOption = false;
        }
    }    
    @FXML
    private void setUngroupedChoice() {
        if(GlobalContext.ungroupedOption == false) {
            GlobalContext.ungroupedOption = true;
            GlobalContext.groupedOption = false;
        } else {
            GlobalContext.ungroupedOption = false;
        }
    }
}
