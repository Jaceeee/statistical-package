/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datapresentation;

import java.text.DecimalFormat;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Juan Carlos
 */
public class Data {
//    Summary Table
    private final SimpleStringProperty valueLabel;
    private final SimpleFloatProperty percentage;
    
//    Frequency Distribution Table
    private final SimpleStringProperty classLimits;
    private final SimpleStringProperty trueClassLimits;
    private final SimpleStringProperty midpoints;
    private final SimpleIntegerProperty frequency;
    private final SimpleFloatProperty frequencyPercentage;
    private final SimpleIntegerProperty cumulativeFrequency;
    private final SimpleFloatProperty cumulativeFrequencyPercentage;
    
    public Data(String valueLabel, Float percentage){        
        this.valueLabel = new SimpleStringProperty(valueLabel);
        this.percentage = new SimpleFloatProperty(percentage);
        this.classLimits = null;
        this.trueClassLimits = null;
        this.midpoints = null;
        this.frequency = null;
        this.frequencyPercentage = null;
        this.cumulativeFrequency = null;
        this.cumulativeFrequencyPercentage = null;
    }
    
    public Data(String classLimits, String trueClassLimits, String midpoints, Integer frequency, 
            Float frequencyPercentage, Integer cumulativeFrequency, Float cumulativeFrequencyPercentage) {
        
        this.valueLabel = null;
        this.percentage = null;
        this.classLimits = new SimpleStringProperty(classLimits);
        this.trueClassLimits = new SimpleStringProperty(trueClassLimits);
        this.midpoints = new SimpleStringProperty(midpoints);
        this.frequency = new SimpleIntegerProperty(frequency);
        this.frequencyPercentage = new SimpleFloatProperty(frequencyPercentage);
        this.cumulativeFrequency = new SimpleIntegerProperty(cumulativeFrequency);
        this.cumulativeFrequencyPercentage = new SimpleFloatProperty(cumulativeFrequencyPercentage);
    }
    
    public Data(){        
        this.valueLabel = null;
        this.percentage = null;
        this.classLimits = null;
        this.trueClassLimits = null;
        this.midpoints = null;
        this.frequency = null;
        this.frequencyPercentage = null;
        this.cumulativeFrequency = null;
        this.cumulativeFrequencyPercentage = null;        
    }
    
    public String getValueLabel(){
        return valueLabel.get();
    }
    
    public float getPercentage(){
        DecimalFormat percentage = new DecimalFormat("#.00");
        return Float.parseFloat(percentage.format(this.percentage.get()));
    }        
    
    public String getClassLimits() {
        return classLimits.get();
    }
    
    public String getTrueClassLimits() {
        return trueClassLimits.get();
    }
    
    public String getMidpoints() {
        return midpoints.get();
    }
    
    public int getFrequency(){
        return frequency.get();
    }
    
    public float getFrequencyPercentage() {
        DecimalFormat percentage = new DecimalFormat("#.00");
//        return Float.parseFloat(percentage.format(this.percentage.get()));
        return Float.parseFloat(percentage.format(frequencyPercentage.get()));
    }
    
    public int getCumulativeFrequency() {
        return cumulativeFrequency.get();
    }
    
    public float getCumulativeFrequencyPercentage() {
        DecimalFormat percentage = new DecimalFormat("#.00");
        return Float.parseFloat(percentage.format(cumulativeFrequencyPercentage.get()));
    }        
}
