/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javafxwfxml;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
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
    
    //Add game: -------------------------
    @FXML private TextField addGameTitleTextArea;
    @FXML private ComboBox addGameMonthComboBox;
    @FXML private ComboBox addGameDayComboBox;
    @FXML private ComboBox addGameYearComboBox;
    @FXML private TextField addGameCostTextArea;
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
    
    
    
    
    //////////////////////////////////////////////////////
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!tabPane.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase(actionToDo)) {
            actionToDo = tabPane.getSelectionModel().getSelectedItem().getText();
            System.out.println("Will perform: " + actionToDo);
        }
        
        addGameMonthComboBox.getItems().addAll(
                "January", "Febuary", "March",
                "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December"
        );
        
        addPlatformMonthComboBox.getItems().addAll(
                "January", "Febuary", "March",
                "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December"
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
        
        //Upcoming:   ------------------
        addUpcomingDayComboBox.getItems().addAll(
                "1","2","3","4","5","6","7","8","9","10",
                "11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30","31"
        );
        
        addUpcomingMonthComboBox.getItems().addAll(
                "January", "Febuary", "March",
                "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December"
        );
        
        addUpcomingYearComboBox.getItems().addAll(
                "1980","1981","1982","1983","1984","1985","1986","1987","1988","1989",
                "1990","1991","1992","1993","1994","1995","1996","1997","1998","1999",
                "2000","2001","2002","2003","2004","2005","2006","2007","2008","2009",
                "2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020"
        );
        
    }
    
    public void setupPlatform() {
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
            addGamePlatformComboBox.getItems().addAll(list);
        } catch(Exception ex) {
            System.out.println("error filling platforms");
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
    private void executeOnClick() throws IOException, SQLException {
        
        switch (actionToDo) {
            case "Add Game":
                String date = String.format("%s-%s-%s", addGameYearComboBox.getSelectionModel().selectedItemProperty(),addGameMonthComboBox.getSelectionModel().selectedItemProperty(),addGameDayComboBox.getSelectionModel().selectedItemProperty());
                String toggle = String.valueOf(addGameIsMultToggleButton.isPressed());
                dbs.addGame(addGameTitleTextArea.getText(),
                            addGameMonthComboBox.getSelectionModel().selectedItemProperty().toString(), 
                            date,
                            addGameCostTextArea.getText(),
                            addGamePlatformComboBox.getSelectionModel().selectedItemProperty().toString(),
                            toggle
                );
                break;
            case "Add Platform":
                 String platformDate = String.format("%s-%s-%s", 
                         addPlatformYearComboBox.getSelectionModel().selectedItemProperty(),
                         addPlatformMonthComboBox.getSelectionModel().selectedItemProperty(),
                             addPlatformDayComboBox.getSelectionModel().selectedItemProperty());
                dbs.addPlatform(addPlatformAbvTextField.getText(), addPlatformNameTextField.getText(), platformDate);
                break;
            case "2":
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
