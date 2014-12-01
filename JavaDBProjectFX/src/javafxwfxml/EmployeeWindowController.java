/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javafxwfxml;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

/**
 *
 * @author Jordan
 */
public class EmployeeWindowController implements Initializable {
    
    //Various: -------------------------
    public DatabaseService dbs;
     
    @FXML private TabPane tabPane;
    private String actionToDo;
    @FXML private Button executeButton;
    @FXML private Button resetButton;
    @FXML private MenuItem quitMenuItem;
    @FXML private TextArea changelogTextArea;
    
    //Add game: -------------------------
    @FXML private TextField addGameTitleTextField;
    @FXML private ComboBox addGameMonthComboBox;
    @FXML private ComboBox addGameDayComboBox;
    @FXML private ComboBox addGameYearComboBox;
    @FXML private TextField addGameCostTextField;
    @FXML private ComboBox addGameGenreComboBox;
    @FXML private ComboBox addGamePlatformComboBox;
    @FXML private ToggleButton addGameIsMultToggleButton;
   
    //Add upcoming: ---------------------
    @FXML private TextField addUpcomingTitleTextArea;
    @FXML private ComboBox addUpcomingMonthComboBox;
    @FXML private ComboBox addUpcomingDayComboBox;
    @FXML private ComboBox addUpcomingYearComboBox;
    @FXML private TextField addUpcomingCostTextArea;
    @FXML private ComboBox addUpcomingGenreComboBox;
    @FXML private ComboBox addUpcomingPlatformComboBox;
    @FXML private ToggleButton addUpcomingIsMultToggleButton;
    
    //Add platform: ---------------------
    @FXML private TextField addPlatformNameTextField;
    @FXML private ComboBox addPlatformMonthComboBox;
    @FXML private ComboBox addPlatformDayComboBox;
    @FXML private ComboBox addPlatformYearComboBox;
    @FXML private TextField addPlatformAbvTextField;
    
    //Restock game: ---------------------
    @FXML private TextField restockTitleTextField;
    @FXML private ComboBox restockPlatformComboBox;
    @FXML private TextField restockAmountTextField;
    
    
    
    
    
    
    //  INIT  ////////////////////////////////////////////////////
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!tabPane.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase(actionToDo)) {
            actionToDo = tabPane.getSelectionModel().getSelectedItem().getText();
            System.out.println("Will perform: " + actionToDo);
        }
        // Add Game Items:   ////////////////////////////////////
        addGameMonthComboBox.getItems().addAll(
               "1","2","3","4","5","6","7","8","9","10","11","12"
        );
        addPlatformMonthComboBox.getItems().addAll(
               "1","2","3","4","5","6","7","8","9","10","11","12"
        );
        addPlatformDayComboBox.getItems().addAll(
                "1","2","3","4","5","6","7","8","9","10",
                "11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30","31"
        );
        addGameDayComboBox.getItems().addAll(
                "1","2","3","4","5","6","7","8","9","10",
                "11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30","31"
        );
        addPlatformYearComboBox.getItems().addAll(
                "1980","1981","1982","1983","1984","1985","1986","1987","1988","1989",
                "1990","1991","1992","1993","1994","1995","1996","1997","1998","1999",
                "2000","2001","2002","2003","2004","2005","2006","2007","2008","2009",
                "2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020"
        );
        addGameYearComboBox.getItems().addAll(
                "1980","1981","1982","1983","1984","1985","1986","1987","1988","1989",
                "1990","1991","1992","1993","1994","1995","1996","1997","1998","1999",
                "2000","2001","2002","2003","2004","2005","2006","2007","2008","2009",
                "2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020"
        );
        addGameGenreComboBox.getItems().addAll(
                "Action", "Adventure", "Arcade",
                "Educational", "Indie", "Other",
                "Puzzle", "Role-Playing", "MMORPG",
                "Simulations", "Sports", "Strategy"
        );
        
        //Upcoming:   //////////////////////////////////////////
        addUpcomingDayComboBox.getItems().addAll(
                "1","2","3","4","5","6","7","8","9","10",
                "11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30","31"
        );
        addUpcomingMonthComboBox.getItems().addAll(
                "1","2","3","4","5","6","7","8","9","10","11","12"
        );
        addUpcomingYearComboBox.getItems().addAll(
                "1980","1981","1982","1983","1984","1985","1986","1987","1988","1989",
                "1990","1991","1992","1993","1994","1995","1996","1997","1998","1999",
                "2000","2001","2002","2003","2004","2005","2006","2007","2008","2009",
                "2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020"
        );
    } //  END INIT  ///////////////////////////////////////////
    
    public void setupPlatforms() {
        if(dbs != null) {
            try {
                Statement stmt = dbs.conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT platform_name FROM platforms;");
                ArrayList list = new ArrayList();
                int count = dbs.getResultSetRowCount(rs);
                int i = 0;
                while (rs.next()) {
                    list.add(rs.getString(1));
                    i++;
                }
                addGamePlatformComboBox.getItems().clear();
                addGamePlatformComboBox.getItems().addAll(list);
                restockPlatformComboBox.getItems().clear();
                restockPlatformComboBox.getItems().addAll(list);
            } catch(Exception ex) {
                System.out.println("error filling platforms");
            }
        }
    }
    
    @FXML
    private void updateAction() {
        if (!tabPane.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase(actionToDo)) {
            actionToDo = tabPane.getSelectionModel().getSelectedItem().getText();
            System.out.println("Will perform: " + actionToDo);
            setupPlatforms();
        }
    }
    
    @FXML
    private void resetOnClick() {
        System.out.println("reset click");
    }
    
    @FXML
    private void executeOnClick() throws IOException {
        
        switch (actionToDo) {
            case "Add Game":
                addGame();
                break;
            case "Add Platform":
                 addPlatform();
                break;
            case "Restock Game":
                restockGame();
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                break;
            default:
                throw new IOException("Unknown input");
        }
    }
    
    @FXML
    public void closeEmployeeWindow() {
        System.out.println("Closing from employee window.");
        System.exit(0);
    }
    
    //-----------------------------------------------
    
    private void completePurchase() {
        try {
            
        } catch (Exception ex) {
            System.out.println("Error in restockGame(). " + ex.getMessage());
        }
    }
    
    //------------------------------------------------

    private void addGame() {
        try {
            String date = String.format("%s-%s-%s", 
                    addGameYearComboBox.getValue().toString(),
                    addGameMonthComboBox.getValue().toString(),
                    addGameDayComboBox.getValue().toString()
            );
            dbs.addGame(
                    addGameTitleTextField.getText(),
                    date,
                    addGameCostTextField.getText(),
                    addGameGenreComboBox.getValue().toString(),
                    addGamePlatformComboBox.getValue().toString(),
                    addGameIsMultToggleButton.isSelected()
            );
        } catch (Exception ex) {
            System.out.println("Error in addGame(). " + ex.getMessage());
        }
    }
    
    //-------------------------------------------------
    
    private void addUpcomingGame() {
        try {
            
        } catch (Exception ex) {
            System.out.println("Error in restockGame(). " + ex.getMessage());
        }
    }
   
    //-------------------------------------------------
    
    private void addDLC() {
        try {
            
        } catch (Exception ex) {
            System.out.println("Error in restockGame(). " + ex.getMessage());
        }
    }
    
    //--------------------------------------------------
    
    private void restockGame() {
        try {
            String title = restockTitleTextField.getText();
            String qty = restockAmountTextField.getText();
            String platform = restockPlatformComboBox.getSelectionModel().getSelectedItem().toString();
            
            int result = dbs.updateQty(title, platform, qty);
            if(result == 1) {
                //success:
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + String.format("\nAdded %s copies of %s",qty, title));
            } else {
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText +"\nUpdate failed. Game not found.");
            }
           
            
        } catch (Exception ex) {
            System.out.println("Error in restockGame(). " + ex.getMessage());
        }
    }
    
    //---------------------------------------------------
    
    private void addPlatform() {
        try {
            String platformDate = String.format("%s-%s-%s", 
                    addPlatformYearComboBox.getValue().toString(),
                    addPlatformMonthComboBox.getValue().toString(),
                    addPlatformDayComboBox.getValue().toString());
            dbs.addPlatform(addPlatformAbvTextField.getText(), addPlatformNameTextField.getText(), platformDate);
        } catch (Exception ex){
            System.out.println("Error in addPlatform(). " + ex.getMessage());
        }
    }
 
    //--------------------------------------------------
    
    private void updateGameCost() {
        try {
            
        } catch (Exception ex) {
            System.out.println("Error in restockGame(). " + ex.getMessage());
        }
    }
  
}
