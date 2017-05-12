/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datapresentation;

/**
 *
 * @author Juan Carlos
 */
public class Validation {
    public static boolean validate(String s) {
        byte type = GlobalContext.inputType;
        if(type == 1 && GlobalContext.categoricalChoice) {
            return isAlpha(s);
        } else if(type == 2 && GlobalContext.categoricalChoice){
            return isString(s);
        } else if(type == 3) {
            return isNumeric(s);
        } else if(type == 4 && GlobalContext.numericChoice){
            return isFloat(s);
        } else {
            return false;
        }
    }        
    
    public static void setValidation(String s) {
        if(GlobalContext.categoricalChoice){
            if (isAlpha(s));
            else if(isString(s));
            else if(isNumeric(s));
            else GlobalContext.inputType = 5;
        } else {
            if(isNumeric(s));
            else if(isFloat(s));
            else GlobalContext.inputType = 5;
        }
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
            GlobalContext.inputType = 3;
            return true;
        }
        return false;
    }
    
    public static boolean isFloat(String s) {
        if(s.matches("^[0-9]+\\.[0-9]+$")){
            GlobalContext.inputType = 4;
            return true;
        }
        return false;
    }        
    
    public static boolean checkLimit(int n) {
        return (n >= 10) ? true : false;
    }
    
}
