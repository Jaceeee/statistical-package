/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descriptivestatistics;

/**
 *
 * @author Juan Carlos
 * 
 */

public class Validation {
    public static boolean validate(String s) {
        byte type = GlobalContext.inputType;
        if(type == 3){
            return isNumeric(s);
        } else if(type == 4){
            return isFloat(s);
        } else {
            return false;
        }
    }        
    
    public static void setValidation(String s) {                
        if(isNumeric(s));
        if(isFloat(s));
    }
    
    public static boolean isAlpha(String s) {
        if(s.matches("^[A-z]$")){
            GlobalContext.inputType = 1;
            return true;
        }
        GlobalContext.errorMessage = "Not a valid alphabetical character.";
        return false;
    }
    
    public static boolean isString(String s) {
        if(s.matches("^[A-z][A-z]+$")){
            GlobalContext.inputType = 2;
            return true;
        }
        GlobalContext.errorMessage = "Not a valid alphabetical character.";
        return false;
    }
    
    public static boolean isNumeric(String s) {
        if(s.matches("^[0-9]+$")){
            System.out.println("Hello, world!");
            GlobalContext.inputType = 3;
            return true;
        } else if(s.matches("^[A-z][A-z]+$")){ return false; } 
       return false;
    }
    
    public static boolean isFloat(String s) {
        if(s.matches("^[0-9]+\\.[0-9]+$")){
            GlobalContext.inputType = 4;
            return true;
        } else if(s.matches("^[A-z][A-z]+$")){ return false; }
        return false;
    }        
    
    public static boolean checkLimit(int n) {
        return (n >= 10) ? true : false;
    }
    
    public static boolean checkGroupedLimit(int n) {
        return (n >= 3) ? true : false;
    }
    
//    for grouped data specific validations
    
    public static boolean checkState() {
        boolean chk = true;
        int standardWidth = Integer.parseInt(GlobalContext.groupedData[0].getUpperClassLimit()) - Integer.parseInt(GlobalContext.groupedData[0].getLowerClassLimit());
        float smallestPlaceValue = GlobalContext.getSmallestPlaceValue(Integer.parseInt(GlobalContext.groupedData[0].getUpperClassLimit())) * 10;
        
        for(int i = 0; i<GlobalContext.n; i++) {
            int currentWidth = Integer.parseInt(GlobalContext.groupedData[i].getUpperClassLimit()) - Integer.parseInt(GlobalContext.groupedData[i].getLowerClassLimit());
            
            if(currentWidth != standardWidth){
                System.out.println("type 1 error");
                chk = false;                
            } 
            
            if(i+1 < GlobalContext.n &&
                    Float.parseFloat(GlobalContext.groupedData[i].getUpperClassLimit()) + 
                    smallestPlaceValue != Float.parseFloat
                    (GlobalContext.groupedData[i+1].getLowerClassLimit())){
                System.out.println("type 2 error");
                chk = false;
            }
        }
        return chk;
    }
}
