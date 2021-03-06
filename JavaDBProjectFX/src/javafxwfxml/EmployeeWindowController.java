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
import javafx.scene.control.ComboBox;
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
    public String userId;

    @FXML
    private TabPane tabPane;
    private String actionToDo;
    @FXML
    private TextArea changelogTextArea;

    //Add game: -------------------------
    @FXML
    private TextField addGameTitleTextField;
    @FXML
    private ComboBox addGameMonthComboBox;
    @FXML
    private ComboBox addGameDayComboBox;
    @FXML
    private ComboBox addGameYearComboBox;
    @FXML
    private TextField addGameCostTextField;
    @FXML
    private ComboBox addGameGenreComboBox;
    @FXML
    private ComboBox addGamePlatformComboBox;
    @FXML
    private ToggleButton addGameIsMultToggleButton;

    //Add upcoming: ---------------------
    @FXML
    private TextField addUpcomingTitleTextField;
    @FXML
    private ComboBox addUpcomingMonthComboBox;
    @FXML
    private ComboBox addUpcomingDayComboBox;
    @FXML
    private ComboBox addUpcomingYearComboBox;
    @FXML
    private TextField addUpcomingCostTextField;
    @FXML
    private ComboBox addUpcomingGenreComboBox;
    @FXML
    private ComboBox addUpcomingPlatformComboBox;
    @FXML
    private ToggleButton addUpcomingIsMultToggleButton;

    //Add platform: ---------------------
    @FXML
    private TextField addPlatformNameTextField;
    @FXML
    private ComboBox addPlatformMonthComboBox;
    @FXML
    private ComboBox addPlatformDayComboBox;
    @FXML
    private ComboBox addPlatformYearComboBox;
    @FXML
    private TextField addPlatformAbvTextField;

    //Restock game: ---------------------
    @FXML
    private TextField restockTitleTextField;
    @FXML
    private ComboBox restockPlatformComboBox;
    @FXML
    private TextField restockAmountTextField;

    //Adjust Cost: ----------------------
    @FXML
    private TextField adjustPriceTitleTextField;
    @FXML
    private TextField adjustPriceCostTextField;
    @FXML
    private ComboBox adjustPricePlatformComboBox;

    //Add DLC: -------------------------
    @FXML
    private TextField addDLCTitleTextField;
    @FXML
    private TextField addDLCGameTextField;
    @FXML
    private ComboBox addDLCMonthComboBox;
    @FXML
    private ComboBox addDLCDayComboBox;
    @FXML
    private ComboBox addDLCYearComboBox;
    @FXML
    private TextField addDLCCostTextField;
    @FXML
    private ComboBox addDLCPlatformComboBox;

    //  INIT  ////////////////////////////////////////////////////
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!tabPane.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase(actionToDo)) {
            actionToDo = tabPane.getSelectionModel().getSelectedItem().getText();
            System.out.println("Will perform: " + actionToDo);
        }
        // Add Game Items:   ////////////////////////////////////
        addGameMonthComboBox.getItems().addAll(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
        );
        addPlatformMonthComboBox.getItems().addAll(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
        );
        addDLCMonthComboBox.getItems().addAll(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
        );
        addPlatformDayComboBox.getItems().addAll(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        );
        addDLCDayComboBox.getItems().addAll(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        );
        addGameDayComboBox.getItems().addAll(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        );
        addPlatformYearComboBox.getItems().addAll(
                "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989",
                "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999",
                "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
                "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"
        );
        addDLCYearComboBox.getItems().addAll(
                "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989",
                "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999",
                "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
                "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"
        );
        addGameYearComboBox.getItems().addAll(
                "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989",
                "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999",
                "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
                "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"
        );
        addGameGenreComboBox.getItems().addAll(
                "Action", "Adventure", "Arcade",
                "Educational", "Indie", "Other",
                "Puzzle", "Role-Playing", "MMORPG",
                "Simulations", "Sports", "Strategy"
        );

        //Upcoming:   //////////////////////////////////////////
        addUpcomingDayComboBox.getItems().addAll(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        );
        addUpcomingMonthComboBox.getItems().addAll(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
        );
        addUpcomingYearComboBox.getItems().addAll(
                "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989",
                "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999",
                "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
                "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"
        );
        addUpcomingGenreComboBox.getItems().addAll(
                "Action", "Adventure", "Arcade",
                "Educational", "Indie", "Other",
                "Puzzle", "Role-Playing", "MMORPG",
                "Simulations", "Sports", "Strategy"
        );

    } //  END INIT  ///////////////////////////////////////////

    public void setupPlatforms() {
        if (dbs != null) {
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
                adjustPricePlatformComboBox.getItems().clear();
                adjustPricePlatformComboBox.getItems().addAll(list);
                addDLCPlatformComboBox.getItems().clear();
                addDLCPlatformComboBox.getItems().addAll(list);
                addUpcomingPlatformComboBox.getItems().clear();
                addUpcomingPlatformComboBox.getItems().addAll(list);
                changelogTextArea.setText("Hello " + userId + ".\n");
            } catch (Exception ex) {
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
            case "Adjust Price":
                updateGameCost();
                break;
            case "DLC":
                addDLC();
                break;
            case "Add Upcoming":
                addUpcomingGame();
                break;
            default:
                throw new IOException("Unknown input on tab switch!");
        }
    }

    @FXML
    public void closeEmployeeWindow() {
        System.out.println("Closing from employee window.");
        System.exit(0);
    }

    //------------------------------------------------
    private void addGame() {
        try {
            String date = String.format("%s-%s-%s",
                    addGameYearComboBox.getValue().toString(),
                    addGameMonthComboBox.getValue().toString(),
                    addGameDayComboBox.getValue().toString()
            );
            int result = dbs.addGame(
                    addGameTitleTextField.getText(),
                    date,
                    addGameCostTextField.getText(),
                    addGameGenreComboBox.getValue().toString(),
                    addGamePlatformComboBox.getValue().toString(),
                    addGameIsMultToggleButton.isSelected()
            );
            if (result == 1) {
                //success:
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + String.format("\nAdded %s game for %s", addGameTitleTextField.getText(), addGamePlatformComboBox.getValue().toString()));
            } else {
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + String.format("\nFailed to add %s game!", addGameTitleTextField.getText()));
            }
        } catch (Exception ex) {
            System.out.println("Error in addGame(). " + ex.getMessage());
            String oldText = changelogTextArea.getText();
            changelogTextArea.setText(oldText + String.format("\nFailed to add %s game!", addGameTitleTextField.getText()));
        }
    }

    //-------------------------------------------------
    private void addUpcomingGame() {
        try {
            String upcomingTitle = addUpcomingTitleTextField.getText();
            Object month = addUpcomingMonthComboBox.getSelectionModel().getSelectedItem();
            Object year = addUpcomingYearComboBox.getSelectionModel().getSelectedItem();
            Object day = addUpcomingDayComboBox.getSelectionModel().getSelectedItem();
            String date = "";
            if (month != null && day != null && year != null) {
                date = String.format("%s-%s-%s", year.toString(), month.toString(), day.toString());
            }
            String cost = addUpcomingCostTextField.getText();
            if (cost.equals("")) {
                cost = "";
            }
            Object genre = addUpcomingGenreComboBox.getSelectionModel().getSelectedItem();
            if (genre == null) {
                genre = "";
            }
            Object platform = addUpcomingPlatformComboBox.getSelectionModel().getSelectedItem();
            if (platform == null) {
                platform = "";
            }

            boolean mult = addUpcomingIsMultToggleButton.isSelected();

            int result = dbs.addUpcoming(upcomingTitle, date, cost, genre.toString(), platform.toString(), mult);

            if (result == 1) {
                //success:
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + String.format("\nAdded upcoming %s for %s", upcomingTitle, platform));
            } else {
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + String.format("\nFailed to add %s!", upcomingTitle));
            }

        } catch (Exception ex) {
            System.out.println("Error in addUpcomingGame(). " + ex.getMessage());
            String oldText = changelogTextArea.getText();
            changelogTextArea.setText(oldText + String.format("\nFailed to add %s!", addUpcomingTitleTextField.getText()));
        }
    }

    //-------------------------------------------------
    private void addDLC() {
        try {
            String dlcTitle = addDLCTitleTextField.getText();
            String gamePair = addDLCGameTextField.getText();
            String month = addDLCMonthComboBox.getSelectionModel().getSelectedItem().toString();
            String day = addDLCDayComboBox.getSelectionModel().getSelectedItem().toString();
            String year = addDLCYearComboBox.getSelectionModel().getSelectedItem().toString();
            String date = String.format("%s-%s-%s", year, month, day);
            String cost = addDLCCostTextField.getText();
            String platform = addDLCPlatformComboBox.getSelectionModel().getSelectedItem().toString();

            int result = dbs.addDLC(
                    gamePair,
                    dlcTitle,
                    date,
                    cost,
                    platform);

            if (result == 1) {
                //success:
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + String.format("\nAdded %s DLC for %s", dlcTitle, gamePair));
            } else {
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + String.format("\nFailed to add %s DLC!", dlcTitle));
            }

        } catch (Exception ex) {
            System.out.println("Error in addDLC(). " + ex.getMessage());
            String oldText = changelogTextArea.getText();
            changelogTextArea.setText(oldText + String.format("\nFailed to add %s DLC!", addDLCTitleTextField.getText()));
        }
    }

    //--------------------------------------------------
    private void restockGame() {
        try {
            String title = restockTitleTextField.getText();
            String qty = restockAmountTextField.getText();
            String platform = restockPlatformComboBox.getSelectionModel().getSelectedItem().toString();

            int result = dbs.restockGame(title, platform, qty);
            if (result == 1) {
                //success:
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + String.format("\nAdded %s copies of %s", qty, title));
            } else {
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + "\nQty update failed. Game / platform combo not found.");
            }

        } catch (Exception ex) {
            System.out.println("Error in restockGame(). " + ex.getMessage());
            String oldText = changelogTextArea.getText();
            changelogTextArea.setText(oldText + "\nQty update failed. Game / platform combo not found.");
        }
    }

    //---------------------------------------------------
    private void addPlatform() {
        try {
            String platformDate = String.format("%s-%s-%s",
                    addPlatformYearComboBox.getValue().toString(),
                    addPlatformMonthComboBox.getValue().toString(),
                    addPlatformDayComboBox.getValue().toString());
            int result = dbs.addPlatform(addPlatformAbvTextField.getText(), addPlatformNameTextField.getText(), platformDate);

            if (result == 1) {
                //success:
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + String.format("\nAdded the %s (%s) platform.", addPlatformNameTextField.getText(), addPlatformAbvTextField.getText()));
            } else {
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + "\nPlatform creation failed!");
            }

        } catch (Exception ex) {
            System.out.println("Error in addPlatform(). " + ex.getMessage());
            String oldText = changelogTextArea.getText();
            changelogTextArea.setText(oldText + "\nPlatform creation failed!");
        }
    }

    //--------------------------------------------------
    private void updateGameCost() {
        try {
            String title = adjustPriceTitleTextField.getText();
            String platform = adjustPricePlatformComboBox.getSelectionModel().getSelectedItem().toString();
            String newCost = adjustPriceCostTextField.getText();

            int result = dbs.updateCost(title, platform, newCost);

            if (result == 1) {
                //success:
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + String.format("\nCost of %s is now: %s", title, newCost));
            } else {
                String oldText = changelogTextArea.getText();
                changelogTextArea.setText(oldText + String.format("\nCost update for %s failed. Game / platform combo not found.", adjustPriceTitleTextField.getText()));
            }

        } catch (Exception ex) {
            System.out.println("Error in updateGameCost(). " + ex.getMessage());
            String oldText = changelogTextArea.getText();
            changelogTextArea.setText(oldText + String.format("\nCost update for %s failed. Game / platform combo not found.", adjustPriceTitleTextField.getText()));
        }
    }

}
