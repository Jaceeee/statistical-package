/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descriptivestatistics;

import java.util.Arrays;

/**
 *
 * @author Juan Carlos
 */

public class GlobalContext {
    public static final GlobalContext gc = new GlobalContext();
    public static boolean groupedChoice = false;
    public static boolean ungroupedChoice = false;
    
    public static boolean modechoice = false;
    public static boolean medianchoice = false;
    public static boolean meanchoice = false;
    
    public static int n;
    public static byte inputType;
    public static String title;
    public static String[] categoricalArray;
    public static Integer[] numberArray;
    public static int counter = 0;
    public static String errorMessage = "";
   
    public static boolean f1,f2,f3,f4;
    public static boolean flagcont;
    public static boolean flagcont2;
    public static boolean openEndedSetting;
    
    public static boolean openEndedOption;
    public static boolean closeEndedOption;
    public static GroupedData[] groupedData;
    public static int frequencySum;
    public static float firstCrossProductResult;
    public static float secondCrossProductResult;
    public static float groupedMean;
    public static float groupedVariance;
    public static float groupedStandardDeviation;
    public static String groupedMode;
    public static int modalCounter;
    
    public static boolean meanOption;
    public static boolean medianOption;
    public static boolean modeOption;
            
    public static boolean openFirstClassInterval;
    public static boolean openLastClassInterval;
    public static boolean groupedOption;
    public static boolean ungroupedOption;
    
    public static float classWidth;
    public static boolean checkback;

    
    private GlobalContext() {
    }
    
    public static GlobalContext getInstance() {
        return GlobalContextHolder.INSTANCE;
    }
    
    private static class GlobalContextHolder {

        private static final GlobalContext INSTANCE = new GlobalContext();
    }
    
    public static boolean mainChoiceSelected() {
        if(!groupedOption && !ungroupedOption) {            
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean openCloseEndedChoiceSelected() {
        if(!openEndedOption && !closeEndedOption) {
            return false;
        } else {
            return true;
        }
    }            
 
    public static double getRange(){
        double min = Double.parseDouble(categoricalArray[0]);
        double max = Double.parseDouble(categoricalArray[n-1]);
        return max - min;
    }
    
    public static void setGroupedDataPlaceholder() {
        groupedData = new GroupedData[n];
        for(int i = 0; i < n; i++){
            groupedData[i] = new GroupedData();
        }
    }
    
    public static void setGroupedDataResults() {
        for(int i = 0; i < n; i++) {
            if(!(i == 0 && openFirstClassInterval || i == n-1 && openLastClassInterval)){
                groupedData[i].setClassMarks(Float.toString((Float.parseFloat(groupedData[i].getLowerClassLimit()) + Float.parseFloat(groupedData[i].getUpperClassLimit())) / 2));
                groupedData[i].setCrossProduct1(Float.toString(Float.parseFloat(groupedData[i].getFrequency()) * Float.parseFloat(groupedData[i].getClassMark())));
                groupedData[i].setCrossProduct2(Float.toString(Float.parseFloat(groupedData[i].getCrossProduct1()) * Float.parseFloat(groupedData[i].getClassMark())));            
            } else {
                if(i == 0){
                    
                }
                groupedData[i].setClassMarks("---");
                groupedData[i].setCrossProduct1("---");
                groupedData[i].setCrossProduct2("---");            
            }
        }
    }
    
    public static void setFirstCrossProductResult() {
        if(!(openFirstClassInterval || openLastClassInterval)){
            firstCrossProductResult = 0;
            for(int i = 0; i < n; i++) {
                firstCrossProductResult += Float.parseFloat(groupedData[i].getCrossProduct1());
            }
            firstCrossProductResult *= firstCrossProductResult;        
        }        
    }
    
    public static void setSecondCrossProductResult() {
        if(!(openFirstClassInterval || openLastClassInterval)){
            secondCrossProductResult = 0;
            for(int i = 0; i < n; i++){
                secondCrossProductResult += Float.parseFloat(groupedData[i].getCrossProduct2());
            }    
        }                
    }
    
    public static void setFrequencySum(){
        frequencySum = 0;
        for(int i = 0; i < n; i++) {
            frequencySum += Float.parseFloat(groupedData[i].getFrequency());
        }
    }
    
    public static void setGroupedMean(){
        if(!(openFirstClassInterval || openLastClassInterval)){
            groupedMean = 0;
            for(int i = 0; i < n; i++) {
                groupedMean += (Float.parseFloat(groupedData[i].getClassMark()) * Float.parseFloat(groupedData[i].getFrequency()));
            }
            groupedMean /= n;
        }
    }
    
    public static void setGroupedVariance() {
        if(!(openFirstClassInterval || openLastClassInterval)){
            groupedVariance = ((secondCrossProductResult * frequencySum) - (firstCrossProductResult)) / (frequencySum * (frequencySum - 1));
        }
    }
    
    public static void setGroupedStandardDeviation() {        
        if(!(openFirstClassInterval || openLastClassInterval)){
            groupedStandardDeviation = (float) Math.sqrt(groupedVariance);
        }
    }
    
    public static void setGroupedMode() {
        String modalClasses = "";
        int modalClass = 0;        
        boolean f1 = false;
        modalCounter = 0;
        for(int i = 1; i < n; i++){
            if(Float.parseFloat(groupedData[i].getFrequency()) != 
                    Float.parseFloat(groupedData[modalClass].getFrequency()) &&
                    f1 == false){
                f1 = true;
            } if(Float.parseFloat(groupedData[i].getFrequency()) > 
                    Float.parseFloat(groupedData[modalClass].getFrequency())){
                modalClass = i;
                modalClasses = Integer.toString(i+1);
                modalCounter = 0;                
            } if(Float.parseFloat(groupedData[i].getFrequency()) == 
                    Float.parseFloat(groupedData[modalClass].getFrequency())) {                                                
                modalCounter++;
            }
        }
        if(f1){
            groupedMode = modalClasses;
        } else {
            groupedMode = "No mode";
        }
    }        
    
    public static String getModalCounter(){
        if(groupedMode.equals("No mode")){
            return "";
        }            
        else if(modalCounter == 1){
            return "unimodal";                    
        }
        else if(modalCounter == 2){
            return "bimodal";
        } else {
            return "multimodal";
        }            
    }
    
    public static void resetMeasureOptions(){
        meanOption = false;
        medianOption = false;
        modeOption = false;
    }
        
    public static void initialize(){
        groupedChoice = false;
        ungroupedChoice = false;
        n = 0;
        inputType = 0;
        title = "";
        checkback = false;
        categoricalArray = new String[0];
        numberArray = new Integer[0];
        counter = 0;
        f2 = f3 = f4 = false;
        flagcont = false; //flag continue if user wants to edit
        flagcont2 = false;
        f1 = true;
        errorMessage = null;
        openEndedSetting = false;
        modechoice = false;
        medianchoice = false;
        meanchoice = false;
    }        
    
    
    public static void newArr(){
        n = counter;
        String[] temp = new String[n];
        Integer[] temp2 = new Integer[n];
        for(int i = 0; i < n; i++){
            temp[i] = categoricalArray[i];
            temp2[i] = numberArray[i];
        }
        numberArray = new Integer[n];
        categoricalArray = new String[n];
        categoricalArray = temp;
        numberArray = temp2;
    }

    public static void setEmptytoZero(){
        for(int i = 0; i < n; i++){
            if(categoricalArray[i].compareTo("") == 0){
                categoricalArray[i] = "0";
            }
        }
    }
    
    public static void sortingIntArr(){
        int[] temp = new int[n];
        for(int i = 0; i < n; i++){
            temp[i] = Integer.parseInt(categoricalArray[i]);
        }
        Arrays.sort(temp);
        for(int i = 0; i < n; i++){
            categoricalArray[i] = Integer.toString(temp[i]);
        }
    }
    
    public static void sortingFloatArr(){
        float[] temp = new float[n];
        for(int i = 0; i < n; i++){
            temp[i] = Float.parseFloat(categoricalArray[i]);
        }
        Arrays.sort(temp);
        for(int i = 0; i < n; i++){
            categoricalArray[i] = Float.toString(temp[i]);
        }
    }
    
    public static float getMean(){
        float sum = 0;
        for(int i = 0; i < n; i++){
            sum += (Float.parseFloat(categoricalArray[i]));
        }
        sum = sum / n;
        return sum;
    }
    
    public static double sampleVariance(){
        float sum = 0;
        float mean = getMean();
        for(int i = 0; i < n; i++){
            sum += (Math.pow((Float.parseFloat(categoricalArray[i]) - mean),2)); 
        }
        sum /= (n - 1);
        return sum;
    }
    
    public static double standardDeviation(){
        float sum = 0;
        float mean = getMean();
        for(int i = 0; i < n; i++){
            sum += Math.pow((Float.parseFloat(categoricalArray[i]) - mean),2); 
        }
        sum /= (n - 1);        
        return Math.sqrt((double) sum);
    }
    
    public static int modeCount(){
        String[] temp = new String[n];
        temp = Arrays.copyOf(categoricalArray,categoricalArray.length);
        int count = 0;
        for(int i = 0; i < n; i++){
            boolean flag = false;
            for(int j = i + 1; j < n; j++){ 
                if(!flag && temp[i].compareTo(temp[j]) == 0){
                    flag = true;
                    temp[j] = "";
                }
            }
            if(flag){
                count++;
            }
        }
        return count;
    }
    
    
    //editting
    public static boolean getInputDisplay(String getdisptext) {
        String[] temp = new String[50];
        String temp2 = "";
        for(int i = 0, j = 0; i < getdisptext.length(); i++){
            if(getdisptext.charAt(i) == '\n'){
                temp[j] = temp2;
                temp2 = "";
            } else {
                temp2 += getdisptext.charAt(i);
            }
        }
        for(int i = 0; i < temp.length; i++){
            if(!Validation.validate(temp[i]))
                return false;
        }
        categoricalArray = temp;
        return true;
    }
    
    
    public static void setUngrouped() {
        ungroupedChoice = (ungroupedChoice) ? false : true;
    }
    
    public static void setGrouped() {
        groupedChoice = (groupedChoice) ? false : true;
    }
    
    static boolean choiceSelected() {
        if(!ungroupedChoice && !groupedChoice) {
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

    public static int getNumOfClasses(){
        return (int)Math.ceil((3.322*(Math.log10(n)) + 1));
    }
    
    public static float getSmallestPlaceValue(float value) {        
        float d = 1;
        boolean t = (value % d) == 0;
        
        while(!t) {
            d *= 0.1;
            t = (value % d) == 0;
        }
        
        return (float) (d * 0.1);
    }
}