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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 *
 * @author Juan Carlos
 */
public class GroupedDataResultsController implements Initializable {
    Stage stage;
    Parent root;
    //    Table template
    @FXML private Label groupedDataTableLabel;
    @FXML private Label firstCrossProductTotal;
    @FXML private Label secondCrossProductTotal;
    @FXML private Label errorMessage;
    @FXML private TableView<descriptivestatistics.GroupedData> groupedDataTableView;    
    @FXML private TextField lowerClassLimitInput;
    @FXML private TextField upperClassLimitInput;
    @FXML private TextField frequencyInput;
    @FXML private Button proceed;
    @FXML private Button edit;
    @FXML private Button back;    
    //    table columns
    //    Summary Table
    @FXML private TableColumn<descriptivestatistics.GroupedData, String> lowerClassLimit;
    @FXML private TableColumn<descriptivestatistics.GroupedData, String> upperClassLimit;
    @FXML private TableColumn<descriptivestatistics.GroupedData, String> frequency;
    @FXML private TableColumn<descriptivestatistics.GroupedData, Float> classMark;
    @FXML private TableColumn<descriptivestatistics.GroupedData, Float> crossProduct1;
    @FXML private TableColumn<descriptivestatistics.GroupedData, Float> crossProduct2;
    
    ObservableList<descriptivestatistics.GroupedData> tableList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(GlobalContext.openFirstClassInterval) {
            GlobalContext.groupedData[0].setLowerClassLimit("\u2264");
        }
        
        if(GlobalContext.openLastClassInterval) {
            GlobalContext.groupedData[0].setUpperClassLimit(                    
                    GlobalContext.groupedData[GlobalContext.n - 1].getLowerClassLimit());
            GlobalContext.groupedData[0].setLowerClassLimit("\u2265");
        }   
        
        tableList = FXCollections.observableArrayList(GlobalContext.groupedData);        
        GlobalContext.setGroupedDataResults();
        groupedDataTableView.setItems(tableList);
        groupedDataTableView.setVisible(true);        
        groupedDataTableView.setEditable(true);

        lowerClassLimit.setCellValueFactory(new PropertyValueFactory<descriptivestatistics.GroupedData, String>("LowerClassLimit"));        
        upperClassLimit.setCellValueFactory(new PropertyValueFactory<descriptivestatistics.GroupedData, String>("UpperClassLimit"));
        frequency.setCellValueFactory(new PropertyValueFactory<descriptivestatistics.GroupedData, String>("Frequency"));
        classMark.setCellValueFactory(new PropertyValueFactory<descriptivestatistics.GroupedData, Float>("ClassMark"));
        crossProduct1.setCellValueFactory(new PropertyValueFactory<descriptivestatistics.GroupedData, Float>("CrossProduct1"));
        crossProduct2.setCellValueFactory(new PropertyValueFactory<descriptivestatistics.GroupedData, Float>("CrossProduct2"));        
        GlobalContext.setFirstCrossProductResult();
        firstCrossProductTotal.setText(Float.toString((float) Math.sqrt(GlobalContext.firstCrossProductResult)));
        GlobalContext.setSecondCrossProductResult();
        secondCrossProductTotal.setText(Float.toString(GlobalContext.secondCrossProductResult));
        GlobalContext.setFrequencySum();                
        GlobalContext.setGroupedVariance();
        GlobalContext.setGroupedStandardDeviation();
        GlobalContext.setGroupedMean();
        GlobalContext.setGroupedMode();
        GlobalContext.resetMeasureOptions();
    }            
    
    @FXML
    public void handleProceedAction(ActionEvent event) throws IOException {        
        if(Validation.checkState()) {
            GlobalContext.setFirstCrossProductResult();
            firstCrossProductTotal.setText(Float.toString((float) Math.sqrt(GlobalContext.firstCrossProductResult)));
            GlobalContext.setSecondCrossProductResult();
            secondCrossProductTotal.setText(Float.toString(GlobalContext.secondCrossProductResult));
            GlobalContext.setFrequencySum();                
            GlobalContext.setGroupedVariance();
            GlobalContext.setGroupedStandardDeviation();
            GlobalContext.setGroupedMean();
            GlobalContext.setGroupedMode();
            GlobalContext.resetMeasureOptions();
            List<String> choices = new ArrayList<>();
            choices.add("Mean");
            choices.add("Median");
            choices.add("Mode");
            choices.add("All");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("Mean", choices);
            dialog.setTitle("Measures of Central Tendency");
            dialog.setHeaderText("Mean, Median and/or Mode?");
            dialog.setContentText("Select your option:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                if(result.get().equals("Mean")){
                    GlobalContext.meanOption = true;
                } else if(result.get().equals("Median")){
                    GlobalContext.medianOption = true;
                } else if(result.get().equals("Mode")){
                    GlobalContext.modeOption = true;
                } else {
                    GlobalContext.meanOption = true;
                    GlobalContext.medianOption = true;
                    GlobalContext.modeOption = true;
                } 

                stage = (Stage) proceed.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("MeasuresTemplate.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                errorMessage.setVisible(false);
            }
        } else {
            errorMessage.setVisible(true);
            errorMessage.setText("There's something wrong with input.");
        }
    }
    
    @FXML
    public void handleBackAction(ActionEvent event) throws IOException {
        stage = (Stage) back.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("GroupedDataSetEntry.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
//    this is the code for editing grouped data
    @FXML 
    public void handleEditDialogOption(ActionEvent event) throws IOException {
        errorMessage.setVisible(false);
        Dialog<descriptivestatistics.GroupedData> dialog = new Dialog<>();
        dialog.setTitle("Editing Table");
        dialog.setHeaderText("Enter the following information, and make sure they're inputted properly.");
        dialog.setResizable(true);

        // Set the button types.
        ButtonType finishButtonType = new ButtonType("Finish", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(finishButtonType, ButtonType.CANCEL);

//        creating the fields and labels
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField dataIndex = new TextField();
        dataIndex.setPromptText("Select which index");
        TextField lowerClassLimit = new TextField();
        lowerClassLimit.setPromptText("Lower Class Limit:");
        TextField upperClassLimit = new TextField();
        upperClassLimit.setPromptText("Upper Class Limit:");
        TextField frequency = new TextField();
        frequency.setPromptText("Frequency:");

        grid.add(new Label("Data Index"), 0, 0);
        grid.add(dataIndex, 1, 0);
        grid.add(new Label("Lower Class Limit:"), 0, 1);
        grid.add(lowerClassLimit, 1, 1);
        grid.add(new Label("Upper Class Limit:"), 0, 2);
        grid.add(upperClassLimit, 1, 2);
        grid.add(new Label("Frequency:"), 0, 3);
        grid.add(frequency, 1, 3);
        dialog.getDialogPane().setContent(grid);        
        dialog.setResultConverter(new Callback<ButtonType, descriptivestatistics.GroupedData>() {
            @Override
            public descriptivestatistics.GroupedData call(ButtonType b) {
                if (b == finishButtonType) {
                    return new descriptivestatistics.GroupedData(lowerClassLimit.getText(), upperClassLimit.getText(), frequency.getText());                    
                }
                return null;
            }
        });       
        Optional result = dialog.showAndWait(); 
        if(result.isPresent()){            
            int index = Integer.parseInt(dataIndex.getText()) - 1;
            if(index < 0 || index >= GlobalContext.n){
                errorMessage.setVisible(true);
                errorMessage.setText("That index doesn't exist.");
            } else {
                GlobalContext.groupedData[index].setLowerClassLimit(lowerClassLimit.getText());
                GlobalContext.groupedData[index].setUpperClassLimit(upperClassLimit.getText());
                GlobalContext.groupedData[index].setFrequency(frequency.getText());
                GlobalContext.setGroupedDataResults();
                groupedDataTableView.setVisible(true);
                groupedDataTableView.setEditable(true);
                System.out.println(tableList.get(index));
                groupedDataTableView.refresh();
            }            
        }   
    }            
}