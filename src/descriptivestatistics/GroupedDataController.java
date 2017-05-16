/*
     * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descriptivestatistics;

/**
 *
 * @author Juan Carlos
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class GroupedDataController implements Initializable {
    Stage stage;
    Parent root;
    int counter;
    
    //    Table template
    @FXML private Label groupedDataTableLabel;
    @FXML private Label errorMessage;
    @FXML private TableView<descriptivestatistics.GroupedData> groupedDataTableView;    
    @FXML private TextField lowerClassLimitInput;
    @FXML private TextField upperClassLimitInput;
    @FXML private TextField frequencyInput;
    @FXML private Button proceed;
    @FXML private Button back;
    //    table columns
    //    Summary Table
    @FXML private TableColumn<descriptivestatistics.GroupedData, String> lowerClassLimit;
    @FXML private TableColumn<descriptivestatistics.GroupedData, String> upperClassLimit;
    @FXML private TableColumn<descriptivestatistics.GroupedData, String> frequency;    
    
    ObservableList<descriptivestatistics.GroupedData> tableList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GlobalContext.setGroupedDataPlaceholder();
        counter = 0;
        tableList = FXCollections.observableArrayList(GlobalContext.groupedData);
        System.out.println(GlobalContext.groupedData.length);
        groupedDataTableView.setItems(tableList);
        groupedDataTableView.setVisible(true);
        groupedDataTableView.setEditable(true);
        groupedDataTableLabel.setText(GlobalContext.title);
        
        lowerClassLimit.setCellValueFactory(new PropertyValueFactory<descriptivestatistics.GroupedData, String>("LowerClassLimit"));        
        upperClassLimit.setCellValueFactory(new PropertyValueFactory<descriptivestatistics.GroupedData, String>("UpperClassLimit"));
        frequency.setCellValueFactory(new PropertyValueFactory<descriptivestatistics.GroupedData, String>("Frequency"));        
        
        lowerClassLimit.setCellFactory(TextFieldTableCell.forTableColumn());
        lowerClassLimit.setOnEditCommit (
            new EventHandler<CellEditEvent<descriptivestatistics.GroupedData, String>>() {
                @Override
                public void handle(CellEditEvent<descriptivestatistics.GroupedData, String> t) {
                    ((descriptivestatistics.GroupedData) t.getTableView().getItems().get (
                        t.getTablePosition().getRow())
                        ).setLowerClassLimit(t.getNewValue());
                }
            }
        );
        
        upperClassLimit.setCellFactory(TextFieldTableCell.forTableColumn());
        upperClassLimit.setOnEditCommit (
            new EventHandler<CellEditEvent<descriptivestatistics.GroupedData, String>>() {
                @Override
                public void handle(CellEditEvent<descriptivestatistics.GroupedData, String> t) {
                    ((descriptivestatistics.GroupedData) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setUpperClassLimit(t.getNewValue());
                }
            }
        );
               
        frequency.setCellFactory(TextFieldTableCell.forTableColumn());
        frequency.setOnEditCommit (
            new EventHandler<CellEditEvent<descriptivestatistics.GroupedData, String>>() {
                @Override
                public void handle(CellEditEvent<descriptivestatistics.GroupedData, String> t) {
                    ((descriptivestatistics.GroupedData) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setFrequency(t.getNewValue());
                }
            }
        );
    }  
    
    @FXML
    public void handleProceedAction(ActionEvent event) throws IOException {
        if (counter < GlobalContext.n){            
            if(lowerClassLimitInput.getText() != "" && upperClassLimitInput.getText() != "" && frequencyInput.getText() != ""){                
                String inputA = lowerClassLimitInput.getText();
                String inputB = upperClassLimitInput.getText();
                String inputC = frequencyInput.getText();                
                if((GlobalContext.inputType == 3 && Validation.isNumeric(inputA) && Validation.isNumeric(inputB) && Validation.isNumeric(inputC)) || 
                        (GlobalContext.inputType == 4 && Validation.isFloat(inputA) && Validation.isFloat(inputB) && Validation.isFloat(inputC))) {
                    GlobalContext.groupedData[counter] = new GroupedData(inputA, 
                                                                inputB, 
                                                                inputC);
                    if(counter == 0) {
                        if(GlobalContext.inputType == 3) {
                            GlobalContext.classWidth = Integer.parseInt(inputB) - Integer.parseInt(inputA);
                        }                                
                        else{
                            String st = String.format("%.2f", Float.parseFloat(inputB) - Float.parseFloat(inputA));
                            GlobalContext.classWidth = Float.parseFloat(st);
                            
                        }
                        counter++;                        
                    } else {
                        String diff;
                        if(GlobalContext.inputType == 3) {
                            diff = String.format("%d", (Integer.parseInt(inputB) - Integer.parseInt(inputA)));
                        } else {
                            diff = String.format("%.2f", (Float.parseFloat(inputB) - Float.parseFloat(inputA)));
                        }                        
                        if((GlobalContext.inputType == 3 && Integer.parseInt(diff) != GlobalContext.classWidth)
                                || (GlobalContext.inputType == 4 && Float.parseFloat(diff) != GlobalContext.classWidth)) {
                            System.out.println(diff + " " + GlobalContext.classWidth);
                            errorMessage.setText("There's something wrong with input.");
                            GlobalContext.groupedData[counter] = new GroupedData("0","0","0");
                        } else {
                            counter++;
                        }                                
                    }                     

                } else {
                    errorMessage.setText("Incompatible input with set input type.");
                }                
                
                tableList = FXCollections.observableArrayList(GlobalContext.groupedData);
                groupedDataTableView.setItems(tableList);                
                lowerClassLimitInput.setText("");
                upperClassLimitInput.setText("");
                frequencyInput.setText("");
                if(counter == GlobalContext.n)
                    proceed.setText("Proceed");
            } else {
                errorMessage.setText("All fields must be filled up.");
            }            
        } else {
            stage = (Stage) proceed.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GroupedDataResults.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    @FXML
    public void enterListener(KeyEvent e){
        if(lowerClassLimitInput.getText() != "" &&
           upperClassLimitInput.getText() != "" &&
           frequencyInput.getText() != "" &&            
           e.getCode() == KeyCode.ENTER){
            
            proceed.fire();
        }
    }
    
    @FXML
    public void handleBackAction(ActionEvent event) throws IOException {
        stage = (Stage) back.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("TitleTemplate.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        GlobalContext.openEndedOption = false;
        GlobalContext.closeEndedOption = false;        
    }
}
