package descriptivestatistics;

import javafx.scene.control.cell.TextFieldListCell;
import java.util.Arrays;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.*;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author -
 */
public class MainController implements Initializable {
    Stage main;
    Stage stage;
    Parent root;

    @FXML private Label title;
    @FXML private Label err;
    @FXML private Label outlabel;
    @FXML private Label measuretype;
    @FXML private TextField datainput;
    @FXML private TextField titlefield;    
    @FXML private TextField nfield;
    @FXML private Button backToPackage;
    //@FXML private TextArea datadisp;
    //@FXML private TextArea numberlist;
    @FXML private Button back;
    @FXML private Button proceed;
    @FXML private Button enter;
    @FXML private Button exit;
    @FXML private Button edit;
    
    @FXML private ListView inputlist;
    @FXML private ListView numlist;
    
    ObservableList<String> itemList;
    ObservableList<Integer> indexList;
    
    //    For the menu    
    @FXML private Button proceed1;    
    @FXML private RadioButton groupedDataRadio;
    @FXML private RadioButton ungroupedDataRadio;
    @FXML private Label errorMessage;    
//    For the title template
    @FXML private Button proceed2;
    @FXML private Button back1;
    @FXML private TextField titleField;
    @FXML private TextField inputLimitField;
    @FXML
    private void MainMenu(ActionEvent event) throws IOException{
        main = (Stage) back.getScene().getWindow();                        
        root = FXMLLoader.load(getClass().getResource("MainTemplate.fxml"));
        Scene scene = new Scene(root);
        main.setScene(scene);
        main.show();
        
        GlobalContext.initialize();
    }
      
    @FXML
    private void exitMenu(ActionEvent event) throws IOException{
        System.exit(0);
    }
    
    @FXML
    private void groupedSelected(ActionEvent event) throws IOException{
        if(GlobalContext.groupedOption == false) {
            GlobalContext.groupedOption = true;
            GlobalContext.ungroupedOption = false;
        } else {
            GlobalContext.groupedOption = false;
        }
        proceed.setDisable(false);
        GlobalContext.setGrouped();
    }
    
    @FXML
    private void ungroupedSelected(ActionEvent event) throws IOException{
        if(GlobalContext.ungroupedOption == false) {
            GlobalContext.ungroupedOption = true;
            GlobalContext.groupedOption = false;
        } else {
            GlobalContext.ungroupedOption = false;
        }
        proceed.setDisable(false);
        GlobalContext.setUngrouped();
    }
    
    @FXML
    private void SampleInput(ActionEvent event) throws IOException{
        if(GlobalContext.mainChoiceSelected()) {
            if(GlobalContext.ungroupedChoice){
                main = (Stage) proceed.getScene().getWindow();                        
                root = FXMLLoader.load(getClass().getResource("PrePrimaryInput.fxml"));
                Scene scene = new Scene(root);
                main.setScene(scene);
                main.show();
            } else {            
                stage = (Stage) proceed.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("TitleTemplate.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();                            
            }
        } else {
            errorMessage.setText("Must select an option before proceeding");
        }
        
    }
    
    //get title 
    //get sample size
    
    @FXML
    private void primaryInput(ActionEvent event) throws IOException{
        if(Validation.isNumeric(nfield.getText()) && Validation.checkLimit(Integer.parseInt(nfield.getText()))) {
            GlobalContext.n = Integer.parseInt(nfield.getText());
            GlobalContext.title = titlefield.getText();
            GlobalContext.categoricalArray = new String[GlobalContext.n];
            GlobalContext.counter = 0;
            GlobalContext.numberArray = new Integer[GlobalContext.n];
            
            main = (Stage) proceed.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("DataInput.fxml"));
            Scene scene = new Scene(root);
            main.setScene(scene);
            main.show();
            GlobalContext.f1 = true;
        } else {
            err.setText("Invalid sample size input");
        }   
    }
    
    //terminating character is \n
    @FXML 
    private void primaryInput2(ActionEvent event) throws IOException{
        String text = datainput.getText();
        GlobalContext.f1 = false;
        
        if(GlobalContext.counter == 0 && enter.isPressed()) {
            Validation.setValidation(text);
            GlobalContext.f1 = GlobalContext.inputType < 5;            
        } else {
            GlobalContext.f1 = Validation.validate(text);
        }
        //adding a special character to terminate
        
        System.out.println(GlobalContext.inputType);
        if(GlobalContext.f1) {

           GlobalContext.categoricalArray[GlobalContext.counter] = text;
           GlobalContext.counter++;
           GlobalContext.numberArray[GlobalContext.counter - 1] = GlobalContext.counter;

           try {
               listviewfunc();
           } catch (IOException ex) {
               Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
           }

           if(GlobalContext.counter == GlobalContext.n){
               proceed.setDisable(false); //setting continue to enable to proceed
               datainput.setEditable(false); //no innputs anymore
               edit.setDisable(false); //setting edit to not be disable anymore

               //proceed to primaryInput3
               //inputlist.editableProperty();
               GlobalContext.flagcont = true;
           }
           err.setText("");
        } else {
            if(!GlobalContext.flagcont) { err.setText("Invalid Input"); }
        }
        datainput.setText("");
    }
    
    
    //editting
    @FXML
    private void primaryInput3(ActionEvent event) throws IOException{
        //TODO
        //set TextArea to editable
        if(GlobalContext.flagcont) { 
            inputlist.setCellFactory(TextFieldListCell.forListView());
            inputlist.setEditable(true);
            proceed.setDisable(false); 
        } 
        //System.out.println(edit.is + " hoyy");
        //if(event.getCode().equals())
        
        edit.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                if(!GlobalContext.flagcont){
                    //edit everything once pressed
                    //make a function instead
                    boolean ftemp = false;
                    itemList = inputlist.getItems();
                    itemList.toArray(GlobalContext.categoricalArray);               
    
                    for(int i = 0; i < GlobalContext.n; i++) {
                        if(!Validation.validate(GlobalContext.categoricalArray[i])){
                            ftemp = true;
                            err.setText("Invalid Input");
                        }
                    }
                    
                    if(!ftemp){
                        err.setText("");
                        GlobalContext.flagcont = true;
                        
                        edit.setText("Edit");
                        proceed.setDisable(false);
                        inputlist.setEditable(false);
                        try {
                            primaryInput2(event);
                        } catch (IOException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        GlobalContext.flagcont = true;
                    }
                } else {
                    proceed.setDisable(true);
                    edit.setText("Continue");
                    GlobalContext.flagcont = false;
                }        
            }
        });
           
    }
    //after proceed make function here
    @FXML
    private void measureChoice(ActionEvent event) throws IOException {
        main = (Stage) proceed.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Measure.fxml"));
        Scene scene = new Scene(root);
        main.setScene(scene);
        main.show();
    }
    
    @FXML
    private void MedianSelect(ActionEvent event){
        if(GlobalContext.modechoice && GlobalContext.meanchoice){
            GlobalContext.modechoice = GlobalContext.meanchoice = false;
        }
        proceed.setDisable(false);
        GlobalContext.medianchoice = true;
    }
    
    @FXML
    private void ModeSelect(ActionEvent event){
        if(GlobalContext.medianchoice && GlobalContext.meanchoice){
            GlobalContext.medianchoice = GlobalContext.meanchoice = false;
        }
        proceed.setDisable(false);
        GlobalContext.modechoice  = true;
    }
    
    @FXML
    private void MeanSelect(ActionEvent event){
        if(GlobalContext.medianchoice && GlobalContext.modechoice){
            GlobalContext.medianchoice = GlobalContext.modechoice = false;
        }
        proceed.setDisable(false);
        GlobalContext.meanchoice  = true;
    }
    
    
    @FXML
    private void allSelect(ActionEvent event) {
        GlobalContext.medianchoice = true;
        GlobalContext.meanchoice = true;
        GlobalContext.modechoice = true;
        
        proceed.setDisable(false);
    }
    
    @FXML 
    private void handleOutputAction(ActionEvent event) throws IOException{
        if(GlobalContext.modechoice || GlobalContext.meanchoice || GlobalContext.medianchoice){
            main = (Stage) proceed.getScene().getWindow();
            if(GlobalContext.medianchoice) { GlobalContext.f2 = true; } //median
            if(GlobalContext.modechoice) { GlobalContext.f3 = true; } //mode
            if(GlobalContext.meanchoice) { GlobalContext.f4 = true; } //mean
            
            root = FXMLLoader.load(getClass().getResource("UngroupedMeasures.fxml"));
            if(GlobalContext.medianchoice) { GlobalContext.f2 = false; } //median
            if(GlobalContext.modechoice) { GlobalContext.f3 = false; } //mode
            if(GlobalContext.meanchoice) { GlobalContext.f4 = false; } //mean
            
            Scene scene = new Scene(root);
            main.setScene(scene);
            main.show();
            
        } else {
            err.setText("Select an option before proceeding");
        }
    }

    @FXML
    public void enterListener(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER){
            enter.fire();            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //not yet complete
        if(GlobalContext.f2 || GlobalContext.f3 || GlobalContext.f4){
            if(GlobalContext.inputType == 3) { GlobalContext.sortingIntArr(); }
            else { GlobalContext.sortingFloatArr(); } 
            title.setText(GlobalContext.title);
            try {
                listviewfunc();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //median
//        if(GlobalContext.f2 && GlobalContext.f3 && GlobalContext.f4){
//            measuretype.setText("All");
//            String text = "";
//            int num = GlobalContext.modeCount();
//            switch (num) {
//                case 0:
//                    text = "No mode value - 'no mode'";
//                    break;
//                case 1:
//                    text = Integer.toString(num) + " modes - 'unimodal'";
//                    break;
//                case 2:
//                    text =  Integer.toString(num) + " modes - 'bimodal'";
//                    break;
//                case 3:
//                    text = Integer.toString(num) + " modes - 'multimodal'";
//                    break;
//                default:
//                    break;
//            }
//            float sampvar = (float) GlobalContext.sampleVariance();
//            float standev = (float) GlobalContext.standardDeviation();
//            
//            outlabel.setText("Median: " + String.valueOf((Float.parseFloat(GlobalContext.categoricalArray[GlobalContext.n/2 - 1]) +
//                            Float.parseFloat(GlobalContext.categoricalArray[GlobalContext.n/2 + 1])) / 2)
//            + "\nMode: " + text + "\nMean: " + "Sample Variance = " + sampvar + "\n" +
//                    "Standard Deviation = " + standev
//                    + "\n" + "Mean = " + GlobalContext.getMean());
//        } else if(GlobalContext.f2){        
        String inp = "";
          if(GlobalContext.f2){              
            measuretype.setText("Median");
            if(GlobalContext.counter % 2 != 0) {
                inp +=(GlobalContext.categoricalArray[GlobalContext.n / 2]);
                outlabel.setText((GlobalContext.categoricalArray[GlobalContext.n / 2]));
            } else {
                int low = (GlobalContext.n / 2) - 1;
                int high = (GlobalContext.n / 2) + 1;
                float lownumb;
                float highnumb;
                lownumb = Integer.parseInt(GlobalContext.categoricalArray[low]);
                highnumb = Integer.parseInt(GlobalContext.categoricalArray[high]);
                System.out.println(lownumb + " mao ni si lownumb");
                System.out.println(highnumb + " mao ni si highnnumb");
                inp += String.valueOf(( (float) lownumb +  (float )highnumb) / 2);
                outlabel.setText(String.valueOf(( (float) lownumb +  (float )highnumb) / 2));                
            }
            outlabel.setText(inp);
        } if(GlobalContext.f3){
            inp+='\n';
            measuretype.setText("Mode");
            int num = GlobalContext.modeCount();
            switch (num) {
                case 0:
                    inp +="No mode value - 'no mode'";
                    outlabel.setText("No mode value - 'no mode'");
                    break;
                case 1:
                    inp += num + " modes - 'unimodal'";
                    outlabel.setText(num + " modes - 'unimodal'");
                    break;
                case 2:
                    inp += num + " modes - 'bimodal'";
                    outlabel.setText(num + " modes - 'bimodal'");
                    break;
                case 3:
                    inp += num + " modes - 'multimodal'";
                    outlabel.setText(num + " modes - 'multimodal'");
                    break;
                default:
                    break;
            }
            outlabel.setText(inp);
        } if(GlobalContext.f4){
            inp += "\n";
            measuretype.setText("Mean");
            float sampvar = (float) GlobalContext.sampleVariance();
            float standev = (float) GlobalContext.standardDeviation();
            inp += "Sample Variance = " + sampvar + "\n" +
                    "Standard Deviation = " + standev
                    + "\n" + "Mean = " + GlobalContext.getMean();
            outlabel.setText("Sample Variance = " + sampvar + "\n" +
                    "Standard Deviation = " + standev
                    + "\n" + "Mean = " + GlobalContext.getMean());
            outlabel.setText(inp);
        }
    }
    
    private void listviewfunc() throws IOException{
        itemList = FXCollections.observableArrayList(GlobalContext.categoricalArray);
        inputlist.setItems(itemList);
        
        indexList = FXCollections.observableArrayList(GlobalContext.numberArray);
        numlist.setItems(indexList);
        Node n1 = inputlist.lookup(".scroll-bar");
        if (n1 instanceof ScrollBar) {
            final ScrollBar bar1 = (ScrollBar) n1;
            Node n2 = numlist.lookup(".scroll-bar");
            if (n2 instanceof ScrollBar) {
                final ScrollBar bar2 = (ScrollBar) n2;
                bar1.valueProperty().bindBidirectional(bar2.valueProperty());
            }
        }
    }
    
    
//    jace part        
    
    @FXML
    private void handleProceedAction2(ActionEvent event) throws IOException {
        if(titleField.getText() != "" && inputLimitField.getText() != "" && Integer.parseInt(inputLimitField.getText()) <= 3 && GlobalContext.openCloseEndedChoiceSelected()) {
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
            if(titleField.getText() == ""){
                errorMessage.setText("Data must have title.");
            } else if (Validation.isNumeric(inputLimitField.getText())){
                errorMessage.setText("Input must be an integer or not blank.");
            } else if (Validation.checkGroupedLimit(Integer.parseInt(inputLimitField.getText()))){
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
    
    @FXML
    public void handleBackToPackageAction(ActionEvent event) throws IOException{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/statisticalpackage/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
