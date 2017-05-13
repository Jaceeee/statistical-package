/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statisticalpackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Juan Carlos
 */

// which parts should the user be allowed to edit in lab3?
public class StatisticalPackage extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {        
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));        
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.show();
        stage.setTitle("3SC Statistical Package");
    }

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }    
}
