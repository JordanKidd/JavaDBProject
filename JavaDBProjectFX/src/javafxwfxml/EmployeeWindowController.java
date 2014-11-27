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
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

/**
 *
 * @author Jordan
 */
public class EmployeeWindowController implements Initializable {
    
    @FXML
    private TabPane tabPane;
    private String actionToDo;

    @FXML
    private Button executeButton;
   
    @FXML
    private Button resetButton;
   
    @FXML
    private MenuItem quitMenuItem;
    
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!tabPane.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase(actionToDo)) {
            actionToDo = tabPane.getSelectionModel().getSelectedItem().getText();
            System.out.println("Will perform: " + actionToDo);
        }
    }
    
    @FXML
    private void updateAction() {
        if (!tabPane.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase(actionToDo)) {
            actionToDo = tabPane.getSelectionModel().getSelectedItem().getText();
            System.out.println("Will perform: " + actionToDo);
        }
    }
    
    @FXML
    private void resetOnClick() {
        System.out.println("reset click");
    }
    
    @FXML
    private void executeOnClick() {
        System.out.println("exec click");
    }
    
    @FXML
    public void closeEmployeeWindow() {
        System.out.println("Closing from employee window.");
        System.exit(0);
    }
    
    //-----------------------------------------------
    
    private void completePurchase() {
        
    }
    
    //------------------------------------------------

    private void addGame() {
        
    }
    
    //-------------------------------------------------
    
    private void addUpcomingGame() {
        
    }
   
    //-------------------------------------------------
    
    private void addDLC() {
        
    }
    
    //--------------------------------------------------
    
    private void restockGame() {
        
    }
    
    //---------------------------------------------------
    
    private void addPlatform() {
        
    }
 
    //--------------------------------------------------
    
    private void updateGameCost() {
        
    }
  
}
