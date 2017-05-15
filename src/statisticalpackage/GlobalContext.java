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
    
    private GlobalContext() {
    }
    
    public static GlobalContext getInstance() {
        return GlobalContextHolder.INSTANCE;
    }
    
    private static class GlobalContextHolder {

        private static final GlobalContext INSTANCE = new GlobalContext();
    }
    
    public static void initialize(){                
    }
}
