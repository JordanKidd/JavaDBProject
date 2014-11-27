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
    private Group addGameGroup;
    
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
        //todo
        actionToDo = tabPane.getSelectionModel().getSelectedItem().getText();
        System.out.println("Will perform: " + actionToDo);
    }
    
    @FXML
    private void updateAction() {
        actionToDo = tabPane.getSelectionModel().getSelectedItem().getText();
        System.out.println("Will perform: " + actionToDo);
    }
    
    private void resetToBlank() {
        //reset form back to blank
    }
    
    @FXML
    private void resetOnClick() {
        System.out.println("reset click");
        resetToBlank();
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
    
    private void showPurchase() {
        System.out.println("Showing purchase");
        addGameGroup.setVisible(false);
    }
    
    //------------------------------------------------
    
    private void addGame() {
        
    }
    
    private void showAddGame() {
        System.out.println("Showing add game");
        addGameGroup.setVisible(true);
    }
    
    //-------------------------------------------------
    
    private void addUpcomingGame() {
        
    }
    
    private void showAddUpcomingGame() {
        System.out.println("Showing add upcoming");
    }
    
    //-------------------------------------------------
    
    private void addDLC() {
        
    }
    
    private void showAddDLC() {
        System.out.println("Showing add dlc");
    }
    
    //--------------------------------------------------
    
    private void restockGame() {
        
    }
    
    private void showRestockGame() {
        System.out.println("Showing show restock");
    }
    
    //---------------------------------------------------
    
    private void addPlatform() {
        
    }
    
    private void showAddPlatform() {
        System.out.println("Showing add platform");
    }
    
    //--------------------------------------------------
    
    private void updateGameCost() {
        
    }
    
    private void showUpdateGameCost() {
        System.out.println("Showing update game cost");
    }
}
