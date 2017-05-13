/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datapresentation;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 *
 * @author Juan Carlos
 */

public class GlobalContext {
    public static final GlobalContext gc = new GlobalContext();
    public static boolean numericChoice = false;
    public static boolean categoricalChoice = false;
    public static int n;
    public static byte inputType;
    public static String title;
    public static String[] categoricalArray;
    public static float[] numericArray;
    public static Integer[] numberArray;
    public static int counter = 0;
    public static String errorMessage = "";
    public static Data[] categoricalData;
    public static Data[] numericData;
    public static boolean f1,f2,f3,f4;
    public static boolean openEndedSetting;
    public static int fracDigits;    
    private static boolean digitsChk;
    private static float smallestPlaceValue;    
    
    public GlobalContext getInstance(){
        return gc;
    }
    
    public static void initialize(){
        numericChoice = false;
        categoricalChoice = false;
        n = 0;
        inputType = 0;
        title = "";
        categoricalData = new Data[0];
        numericData = new Data[0];
        categoricalArray = new String[0];
        numericArray = new float[0];
        counter = 0;
        f2 = f3 = f4 = false;
        f1 = true;
        errorMessage = null;
        openEndedSetting = false;
        fracDigits = 0;
        digitsChk = false;
        smallestPlaceValue = 1;
    }        
        
    
    public static void setCategorical() {
        categoricalChoice = (categoricalChoice) ? false : true;
    }
    
    public static void setNumeric() {
        numericChoice = (numericChoice) ? false : true;
    }
    
    static boolean choiceSelected() {
        if(!categoricalChoice && !numericChoice) {
            return false;
        } else {
            return true;
        }
    }
    
//    removes duplicates
    public static String[] removeDuplicates() {
        String[] array2 = Arrays.copyOf(categoricalArray, categoricalArray.length);
        String[] array3;

        Arrays.sort(array2);       
        int current = 0;

        for(int i = 0; i<n; i++) {
            if(!array2[i].equals(array2[current])){
                array2[++current] = array2[i];
            }
        }

        array3 = new String[current+1];
        
        for(int i = 0; i < current+1; i++) {
            array3[i] = array2[i];
        }

        return array3;
    }
   
//   returns the float data percentages for the table (categorical data)
    public static Float[] percentageComp(){       
       String[] array2 = categoricalArray;
       Arrays.sort(array2);       
       
       String[] array3 = removeDuplicates();
       Float[] a = new Float[array3.length];              
       
       for(int i = 0, counter; i<array3.length; i++){
           counter = 0;
           for(int j = 0; j<array2.length; j++) {
               if(array2[j].equals(array3[i])) {
                   counter++;
               }
           }
           a[i] = new Float(((float)counter/(float)n));           
       }
       
       return a;
   }
   
    public static void setData(boolean openEndedSetting){
        if(categoricalChoice){
            String[] valueLabels = removeDuplicates();
            Float[] percentages = percentageComp();
            categoricalData = new Data[valueLabels.length];
                        
            for(int i = 0; i < categoricalData.length; i++) {
                categoricalData[i] = new Data(valueLabels[i], percentages[i]*100);
            }
        } else if(numericChoice) {            
            numericArray = new float[n];
            
//            populating the numeric array
            for(int i = 0; i<n; i++){
                numericArray[i] = Float.parseFloat(categoricalArray[i]);
            }                                    
            
            numericData = new Data[getNumOfClasses()];
            
            for(int i = 0; i<getNumOfClasses(); i++){
                String[] dataPiece = combineNumericData(i).split(":");
                if(openEndedSetting && (i == 0 || i == getNumOfClasses() - 1)) { 
                    dataPiece[0] = (i == 0) ? returnLowerOpenEndedData(dataPiece[0])
                            : returnUpperOpenEndedData(dataPiece[0]);
                    dataPiece[1] = "---";
                    dataPiece[2] = "---";
                }
                
                numericData[i] = new Data(dataPiece[0],
                                          dataPiece[1],
                                          dataPiece[2],
                                          Integer.parseInt(dataPiece[3]),
                                          Float.parseFloat(dataPiece[4]),
                                          Integer.parseInt(dataPiece[5]),
                                          Float.parseFloat(dataPiece[6]));
            }
        }
    }
    
    public static int getNumOfClasses(){
        return (int)Math.ceil((3.322*(Math.log10(n)) + 1));
    }
    
    public static float getMinimum(){
        float min = numericArray[0];        
        for(int i = 0; i<n; i++){
            if(numericArray[i] < min){
                min = numericArray[i];
            }
        }
        setSmallestPlaceValue(min);
        return min;
    }
    
    public static float getMaximum(){
        float max = numericArray[0];
        for(int i = 0; i<n; i++){
            if(numericArray[i] > max){
                max = numericArray[i];
            }
        }
        
        return max;
    }
    
    public static float getLowerBound(int i) {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.HALF_UP);        
        fracDigits = numberOfFractionalDigits(getMinimum());
        df.setMaximumFractionDigits(fracDigits);
        return (i != 0) ? Float.parseFloat(df.format(getUpperBound(i-1) + getSmallestPlaceValue() * ((inputType == 4) ? 100 : 10))) : getMinimum();
    }
    
    public static float getUpperBound(int i){
        DecimalFormat df = new DecimalFormat("#.###");        
        df.setRoundingMode(RoundingMode.CEILING);                
        df.setMaximumFractionDigits(fracDigits);        
        return padZeros((float) (getLowerBound(i) + Float.parseFloat(df.format((getClassWidth()) - 10*getSmallestPlaceValue()))));
    }
    
    private static float padZeros(float value) {
        int num = getNumberOfFractionDigits(value);
        String tmp = Float.toString(value);
        if(!checkPointPresence(tmp)){
            tmp += '.';
        }
        if(num < fracDigits) {
            tmp+='0';
            num++;
        }
        return Float.parseFloat(tmp);
    }
    
    private static boolean checkPointPresence(String val) {
        for(int i = 0; i<val.length(); i++){
            if(val.charAt(i) == '.'){
                return true;
            }
        }
        return false;
    }
    private static int getNumberOfFractionDigits(float value) {
        int ct = 0;
        while(value - Math.floor(value) > 0){
            value*=10;
            ct++;
        }        
        return ct;
    }
    private static int numberOfFractionalDigits(float i){
        int ct = 0;
        while(i - Math.floor(i) > 0) {
            i*=10;
            ct++;
        }
        return ct;
    }
    
    public static float getClassWidth() {
//        TODO
//        don't round up class width to nearest whole number
        DecimalFormat df = new DecimalFormat("#.###");              
        return (inputType == 4) ? Float.parseFloat(df.format(((float)getRange() / (float)getNumOfClasses()))) : (float)getRange() / (float)getNumOfClasses();
    }
    
    public static float getRange(){
        return getMaximum() - getMinimum();
    }
        
    public static float getMidpoint(int i) {
        return (getUpperBound(i) + getLowerBound(i)) / 2;
    }
    
    public static void setSmallestPlaceValue(float value) {
        float d = 1;
        boolean t = ((value / d) % 1) == 0;        
        while(!t) {
            d *= 0.1;            
            t = ((value / d) % 1) == 0;            
            if(digitsChk) {
                fracDigits++;
            }
        }                      
        digitsChk = false;
        smallestPlaceValue = (float) (d * 0.1);
    }
    
    public static float getSmallestPlaceValue() {        
        return smallestPlaceValue;
    }        
    
    public static float getTrueLowerClassLimit(int i) {
        float lowerBound = getLowerBound(i);        
        return lowerBound - (getSmallestPlaceValue() * (inputType == 4 ? 50 : 5));
    }
    
    public static float getTrueUpperClassLimit(int i) {
        float upperBound = getUpperBound(i);
        return upperBound + (getSmallestPlaceValue() * (inputType == 4 ? 50 : 5));
    }
    
    public static int getFrequency(float lowerClassLimit, float upperClassLimit) {
        int frequency = 0;
        
        for(int i = 0; i<n; i++){
            if(numericArray[i] >= lowerClassLimit && numericArray[i] < upperClassLimit) {
                frequency++;
            }
        }
        
        return frequency;
    }
    
    public static float getFrequencyPercentage(int frequency) {        
        return ((float)frequency / (float)n);
    }
    
    public static int getCumulativeFrequency(int i) {
        if(i == 0) {
            return getFrequency(getTrueLowerClassLimit(i), getTrueUpperClassLimit(i));
        } else {
            return getCumulativeFrequency(i - 1) + getFrequency(getTrueLowerClassLimit(i), getTrueUpperClassLimit(i));
        }                
    }
    
    public static float getCumulativeFrequencyPercentage(int i) {        
        return ((float)getCumulativeFrequency(i)/(float)n);
    }
    
    public static String combineNumericData(int i) {
        // class limits
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.HALF_UP);
        
        String comb = String.format("%s-%s", padZeros(Float.parseFloat(df.format(getLowerBound(i)))), padZeros(Float.parseFloat(df.format(getUpperBound(i))))) + ":";
        // true class limits
        comb += String.format("%s-%s", df.format(getTrueLowerClassLimit(i)), df.format(getTrueUpperClassLimit(i))) + ":";
        // midpoint
        comb += String.format("%s", df.format(getMidpoint(i))) + ":";
        // frequency
        comb += String.format("%d", getFrequency(getTrueLowerClassLimit(i),getTrueUpperClassLimit(i))) + ":";
        // percentage
        comb += String.format("%.2f", getFrequencyPercentage(getFrequency(getTrueLowerClassLimit(i),getTrueUpperClassLimit(i))) * 100) + ":";        
        // cumulative frequency
        comb += String.format("%d", getCumulativeFrequency(i)) + ":";
        // cumulative frequency percentage
        comb += String.format("%.2f", getCumulativeFrequencyPercentage(i) * 100);
        
        return comb;
    }
    
    public static String returnLowerOpenEndedData(String i){                
        String[] a = i.split("-");
        return a[1] + " and below";
    }
    
    public static String returnUpperOpenEndedData(String i) {        
        String[] a = i.split("-");
        return a[0] + " and above";
    }
    
    public static String roundUp(float i, String format){
        DecimalFormat df = new DecimalFormat(format);
        df.setRoundingMode(RoundingMode.CEILING);
                
        Double d = new Double(i);
        return df.format(d);
        //use ceil
    }
}