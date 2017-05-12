/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statisticalpackage;

/**
 *
 * @author Juan Carlos
 */
public class GlobalContext {
    
    public static boolean f1;
    public static boolean f2;
    
    private GlobalContext() {
    }
    
    public static GlobalContext getInstance() {
        return GlobalContextHolder.INSTANCE;
    }
    
    private static class GlobalContextHolder {

        private static final GlobalContext INSTANCE = new GlobalContext();
    }
    
    public static void initialize(){
        f1 = true;
        f2 = false;        
    }
}
