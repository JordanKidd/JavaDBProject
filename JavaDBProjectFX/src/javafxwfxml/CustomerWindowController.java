/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxwfxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author jordankidd
 */
public class CustomerWindowController implements Initializable {
    
    public DatabaseService dbs;
    
    @FXML
    private MenuItem quitMenuItem;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
    
    @FXML
    public void closeCustomerWindow() {
        // get a handle to the stage
        System.out.println("Closing from customer window.");
        System.exit(0);
    }
}
