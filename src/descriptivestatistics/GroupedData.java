/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descriptivestatistics;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Juan Carlos
 */
public class GroupedData {
    private final SimpleStringProperty lowerClassLimit;
    private final SimpleStringProperty upperClassLimit;
    private final SimpleStringProperty frequency;    
    private final SimpleStringProperty classMark;
//    cross product between f and classMark
    private final SimpleStringProperty crossProduct1;
//    cross product between f and square of the classMark
    private final SimpleStringProperty crossProduct2;
    
    public GroupedData(){
        lowerClassLimit = new SimpleStringProperty("0");
        upperClassLimit = new SimpleStringProperty("0");
        frequency = new SimpleStringProperty("0");
        classMark = new SimpleStringProperty("0");
        crossProduct1 = new SimpleStringProperty("0");
        crossProduct2 = new SimpleStringProperty("0");
    }
    
    public GroupedData(String lcl, String ucl, String freq) {
        lowerClassLimit = new SimpleStringProperty(lcl);
        upperClassLimit = new SimpleStringProperty(ucl);
        frequency = new SimpleStringProperty(freq);
        classMark = new SimpleStringProperty("0");
        crossProduct1 = new SimpleStringProperty("0");
        crossProduct2 = new SimpleStringProperty("0");
    }
    
    public GroupedData(String lcl, String ucl, int freq, float cm, float cp1, float cp2) {
        lowerClassLimit = new SimpleStringProperty(lcl);
        upperClassLimit = new SimpleStringProperty(ucl);
        frequency = new SimpleStringProperty(Integer.toString(freq));
        classMark = new SimpleStringProperty(Float.toString(cm));
        crossProduct1 = new SimpleStringProperty(Float.toString(cp1));
            crossProduct2 = new SimpleStringProperty(Float.toString(cp2));
    }
    
    public String getLowerClassLimit() {
        return lowerClassLimit.get();        
    }
    
    public String getUpperClassLimit(){
        return upperClassLimit.get();
    }
    
    public String getFrequency(){
        return frequency.get();
    }
    
    public String getClassMark() {
        return classMark.get();
    }
    
    public String getCrossProduct1() {
        return crossProduct1.get();
    }
    
    public String getCrossProduct2() {
        return crossProduct2.get();
    }
    
    public void setLowerClassLimit(String lcl) {
        lowerClassLimit.set(lcl);
    }
    
    public void setUpperClassLimit(String ucl) {
        upperClassLimit.set(ucl);
    }
    
    public void setFrequency(String f) {
        frequency.set(f);
    }
    
    public void setClassMarks(String cm) {
        classMark.set(cm);
    }
    
    public void setCrossProduct1(String cp1){
        crossProduct1.set(cp1);
    }
    
    public void setCrossProduct2(String cp2){
        crossProduct2.set(cp2);
    }
    @Override
    public String toString(){
        return "lower class limit: " + lowerClassLimit.get() 
                + "\nupper class limit: " + upperClassLimit.get() 
                + "\nfrequency: " + frequency.get() 
                + "\nclass marks: " + classMark.get()
                + "\ncross product 1: " + crossProduct1.get()
                + "\ncross product 2: " + crossProduct2.get();
    }
}
