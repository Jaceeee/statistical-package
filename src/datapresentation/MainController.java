/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 * errors: non-selective open-ended 
 * errors: purely decimal input
 */

package datapresentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Juan Carlos
 */
public class MainController implements Initializable {    
    Stage main;
    Parent root;
    
//    first template
    @FXML private Button exit1;
    @FXML private Button proceed1;
    @FXML private Button backToPackage;
    @FXML private RadioButton categorical;
    @FXML private RadioButton numeric;
    @FXML private Label errorMessage1;
    @FXML private Label title;
    
//    second template
    @FXML private Button proceed2;
    @FXML private Button back1;
    @FXML private TextField titleField;
    @FXML private TextField nField;    
            
//    third template
    @FXML private Button proceed3;
    @FXML private Button enter;
    @FXML private Button back2;
    @FXML private TextField dataField;    
    @FXML private ListView inputDisplay;
    @FXML private ListView numbers;
    
//    Table template
    @FXML private Label summaryTableLabel;
    @FXML private Label frequencyDistributionTableLabel;
    @FXML private TableView<datapresentation.Data> summaryTableView;
    @FXML private TableView<datapresentation.Data> frequencyDistributionTableView;
    
//    Summary Table
    @FXML private TableColumn<datapresentation.Data, String> valueLabel;
    @FXML private TableColumn<datapresentation.Data, Float> percentage;
    
//    Frequency Distribution    
    @FXML private Button distributionTableSwitch;
    @FXML private TableColumn<datapresentation.Data, String> classLimits;
    @FXML private TableColumn<datapresentation.Data, String> trueClassLimits;
    @FXML private TableColumn<datapresentation.Data, String> midpoints;
    @FXML private TableColumn<datapresentation.Data, Integer> frequency;
    @FXML private TableColumn<datapresentation.Data, Float> frequencyPercentage;
    @FXML private TableColumn<datapresentation.Data, Integer> cumulativeFrequency;
    @FXML private TableColumn<datapresentation.Data, Float> cumulativeFrequencyPercentage;
        
    @FXML private Button proceed4;
    @FXML private Button back3;
    @FXML private Label sampleSizeLabel;
    
    //    Pie Chart
    @FXML private PieChart pieChart;
    @FXML private Label pieChartLabel;        
    
    // Histogram
    private XYChart.Series series1;
    @FXML private BarChart histogram;
    @FXML private Label histChartLabel;                 

//    auxiliary variables    
    ObservableList<String> itemList;
    ObservableList<datapresentation.Data> tableList = FXCollections.observableArrayList();
    
    @FXML
    private void handleInputAction1(ActionEvent event) throws IOException {
        GlobalContext.f1 = false;
        if(GlobalContext.choiceSelected()) {
            main = (Stage) proceed1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("PrimaryInput.fxml"));
            Scene scene = new Scene(root);
            main.setScene(scene);
            main.show();
            GlobalContext.f1 = true;
        } else{
            errorMessage1.setText("Must select an option before proceeding");
        }
    }
    
    @FXML
    private void handleInputAction2(ActionEvent event) throws IOException {
        if(Validation.isNumeric(nField.getText()) && Validation.checkLimit(Integer.parseInt(nField.getText()))) {
            GlobalContext.n = Integer.parseInt(nField.getText());
            GlobalContext.title = titleField.getText();
            GlobalContext.categoricalArray = new String[GlobalContext.n];
            GlobalContext.counter = 0;
            GlobalContext.numberArray = new Integer[GlobalContext.n];
            
            main = (Stage) proceed2.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("SecondaryInput.fxml"));
            Scene scene = new Scene(root);
            main.setScene(scene);
            main.show();                                    
            GlobalContext.f1 = false;
        } else {
            errorMessage1.setText("Wrong input detected");
        }
    }
    
    @FXML 
    private void handleInputAction3(ActionEvent event) throws IOException {
        GlobalContext.f1 = false;
        GlobalContext.f2 = true;
        main = (Stage) proceed3.getScene().getWindow();
        if(GlobalContext.categoricalChoice) {            
            root = FXMLLoader.load(getClass().getResource("SummaryTableOutput.fxml"));            
        } else {
            root = FXMLLoader.load(getClass().getResource("FrequencyDistributionTableOutput.fxml"));            
        }                
        
        Scene scene = new Scene(root);
        main.setScene(scene);
        main.show();
        GlobalContext.f2 = false;        
    }
    
    @FXML   
    private void handleInputAction4(ActionEvent event) throws IOException {
        GlobalContext.f3 = true;
        if(GlobalContext.categoricalChoice){
            main = (Stage) proceed4.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("PieChart.fxml"));
        } else {
            main = (Stage) proceed4.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Histogram.fxml"));
        }
        Scene scene = new Scene(root);
        main.setScene(scene);
        main.show();        
        GlobalContext.f3 = false;
    }
        
    @FXML
    private void handleEnterInputAction(ActionEvent event) throws IOException {
        String text = dataField.getText();
        GlobalContext.f1 = false;
        
        if(GlobalContext.counter == 0) {
            Validation.setValidation(text);
            GlobalContext.f1 = GlobalContext.inputType < 5;
        } else {
            GlobalContext.f1 = Validation.validate(text);
        }
        
        if(GlobalContext.f1) {
            GlobalContext.categoricalArray[GlobalContext.counter] = text;     
            itemList = FXCollections.observableArrayList(GlobalContext.categoricalArray);
            inputDisplay.setItems(itemList);
            
            GlobalContext.numberArray[GlobalContext.counter++] = new Integer(GlobalContext.counter);
            ObservableList<Integer> tmpList = FXCollections.observableArrayList(GlobalContext.numberArray);
            numbers.setItems(tmpList);
            
            if(GlobalContext.counter == GlobalContext.n) {
                dataField.setEditable(false);
                enter.setDisable(true);
            }
            dataField.setText("");
            errorMessage1.setText("");
            
            Node n1 = inputDisplay.lookup(".scroll-bar");
            if (n1 instanceof ScrollBar) {
                final ScrollBar bar1 = (ScrollBar) n1;
                Node n2 = numbers.lookup(".scroll-bar");
                if (n2 instanceof ScrollBar) {
                    final ScrollBar bar2 = (ScrollBar) n2;
                    bar1.valueProperty().bindBidirectional(bar2.valueProperty());                    
                }
            }
        } else {
            errorMessage1.setText("Invalid input");
        }
    }
    
    @FXML
    private void handleChangeInTableInput(ActionEvent event) {        
        GlobalContext.openEndedSetting = (GlobalContext.openEndedSetting) ? false : true;
        GlobalContext.setData(GlobalContext.openEndedSetting);                        
        setFrequencyDistributionTable();
        if(GlobalContext.openEndedSetting){
            distributionTableSwitch.setText("Collapse table");    
        } else {
            distributionTableSwitch.setText("Show open ended distribution");
        }
        
        frequencyDistributionTableView.setItems(tableList);
        frequencyDistributionTableView.setVisible(true);
        frequencyDistributionTableLabel.setText(GlobalContext.title);
    }
            
    @FXML
    public void enterListener(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER){
            enter.fire();            
        }
    }
    
    @FXML
    private void handleBackAction1(ActionEvent event) throws IOException {
        main = (Stage) back1.getScene().getWindow();
        GlobalContext.f1 = false;
        root = FXMLLoader.load(getClass().getResource("MainTemplate.fxml"));
        Scene scene = new Scene(root);
        main.setScene(scene);
        main.show();
        GlobalContext.initialize();        
    }        
    
    @FXML
    private void handleBackAction2(ActionEvent event) throws IOException {
        GlobalContext.n = 0;
        GlobalContext.title = "";
        GlobalContext.f2 = false;
        itemList = null;
        GlobalContext.f1 = false;
        
        main = (Stage) back2.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("PrimaryInput.fxml"));
        Scene scene = new Scene(root);
        main.setScene(scene);
        main.show();
        GlobalContext.f1 = false;
    }
    
    @FXML
    private void handleBackAction3(ActionEvent event) throws IOException {
        GlobalContext.categoricalData = new datapresentation.Data[0];        
        GlobalContext.f1 = false;
        tableList = null;
        main = (Stage) back3.getScene().getWindow();                        
        root = FXMLLoader.load(getClass().getResource("MainTemplate.fxml"));
        Scene scene = new Scene(root);
        main.setScene(scene);
        main.show();
        GlobalContext.initialize();
    }        
    
    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(GlobalContext.f1) {
            title.setText(GlobalContext.title);
        }
        if(GlobalContext.f2) {
            GlobalContext.setData(GlobalContext.openEndedSetting);            
            
            if(GlobalContext.categoricalChoice) {
                setSummaryTable();
                
                summaryTableView.setItems(tableList);
                summaryTableView.setVisible(true);                
                summaryTableLabel.setText(GlobalContext.title);
            }
            else if(GlobalContext.numericChoice) {
                sampleSizeLabel.setText("n = " + GlobalContext.n);
                setFrequencyDistributionTable();                
                
                frequencyDistributionTableView.setItems(tableList);
                frequencyDistributionTableView.setVisible(true);
                frequencyDistributionTableLabel.setText(GlobalContext.title);
            }
        }        
        if(GlobalContext.f3){
            if(GlobalContext.categoricalChoice) {
                ObservableList<PieChart.Data> tempList = FXCollections.observableArrayList();
                for(datapresentation.Data d : GlobalContext.categoricalData) {
                    tempList.add(new PieChart.Data(d.getValueLabel() + "\n" + (d.getPercentage()) + "%", 
                            d.getPercentage()));
                }
                
                pieChart.setData(tempList);
                pieChart.setLegendVisible(false);
                pieChartLabel.setText(GlobalContext.title);
            } else{                                           
                histogram.setCategoryGap(0);
                histogram.setBarGap(0);
                series1 = new XYChart.Series();
                series1.setName(GlobalContext.title);

                ObservableList<XYChart.Data> tempList = FXCollections.observableArrayList();                
                for(datapresentation.Data d : GlobalContext.numericData) {
                   tempList.add(new XYChart.Data(d.getMidpoints(), d.getFrequency()));
                }            
                series1.setData(tempList);                
                                               
                for(datapresentation.Data d : GlobalContext.numericData) {
                    tempList.add(new XYChart.Data(d.getMidpoints(), d.getFrequency()));
                }            
                series1.setData(tempList);                
            
                histogram.getData().addAll(series1);
                histogram.setLegendVisible(false);
                histChartLabel.setText(GlobalContext.title); 
            }
        }         
    }
    
    @FXML
    public void numericSelected() {
        if(GlobalContext.categoricalChoice) {
            GlobalContext.categoricalChoice = false;
        }
        GlobalContext.setNumeric();
    }
    
    @FXML
    public void categoricalSelected() {
        if(GlobalContext.numericChoice) {
            GlobalContext.numericChoice = false;
        }
        GlobalContext.setCategorical();
    }
    
    public void setFrequencyDistributionTable() {
        tableList = FXCollections.observableArrayList(GlobalContext.numericData);
        classLimits.setCellValueFactory(new PropertyValueFactory<datapresentation.Data, String> ("ClassLimits"));
        trueClassLimits.setCellValueFactory(new PropertyValueFactory<datapresentation.Data, String>("TrueClassLimits"));        
        midpoints.setCellValueFactory(new PropertyValueFactory<datapresentation.Data, String>("Midpoints"));
        frequency.setCellValueFactory(new PropertyValueFactory<datapresentation.Data, Integer>("Frequency"));
        frequencyPercentage.setCellValueFactory(new PropertyValueFactory<datapresentation.Data, Float>("FrequencyPercentage"));
        cumulativeFrequency.setCellValueFactory(new PropertyValueFactory<datapresentation.Data, Integer>("CumulativeFrequency"));
        cumulativeFrequencyPercentage.setCellValueFactory(new PropertyValueFactory<datapresentation.Data, Float>("CumulativeFrequencyPercentage"));
    }
    
    public void setSummaryTable() {
        tableList = FXCollections.observableArrayList(GlobalContext.categoricalData);
        valueLabel.setCellValueFactory(new PropertyValueFactory<datapresentation.Data, String> ("ValueLabel"));
        percentage.setCellValueFactory(new PropertyValueFactory<datapresentation.Data, Float> ("Percentage"));
    }
    
    @FXML
    public void handleBackToPackageAction(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/statisticalpackage/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
