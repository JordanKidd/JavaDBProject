/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxwfxml;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author jordankidd
 */
public class CustomerWindowController implements Initializable {
    
    private DatabaseService dbs;
    
    @FXML
    private TextArea resultsTextArea;
    
    @FXML
    private MenuItem quitMenuItem;
    
    @FXML
    private ComboBox genreComboBox;
    private String currentGenre;
    
    @FXML
    private ComboBox platformComboBox;
    private String currentPlatform;
    
    @FXML
    private Button searchButton;
    
    @FXML
    private Button resetButton;
    
    @FXML
    private TextField titleTextField;
    
    @FXML
    private Slider minPrice;
    
    @FXML
    private Slider maxPrice;
    
    
    //----------------------------------------------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genreComboBox.getItems().addAll(
                "Action", "Adventure", "Arcade",
                "Educational", "Indie", "Other",
                "Puzzle", "Role-Playing", "MMORPG",
                "Simulations", "Sports", "Strategy"
        );
    }
    
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
                platformComboBox.getItems().clear();
                platformComboBox.getItems().addAll(list);
                
            } catch(Exception ex) {
                System.out.println("error filling platforms");
            }
        }
    }
    
    public DatabaseService getDbs() {
        return this.dbs;
    }
    
    public void setDbs(DatabaseService database) {
        this.dbs = database;
    }
    
    @FXML
    public void updateCurrentGenreChoice() {
        Object val = genreComboBox.getValue();
        currentGenre = val.toString();
    }
    
    @FXML
    public void updateCurrentPlatformChoice() {
        Object val = platformComboBox.getValue();
        currentPlatform = val.toString();
    }
    
    @FXML
    public void searchButtonClick() throws SQLException {
        System.out.println("!!-- Current values: ");
        System.out.println("Title: " + titleTextField.getText());
        System.out.println("Genre: " + currentGenre);
        System.out.println("Platform: " + currentPlatform);
        System.out.println("Min value: " + minPrice.getValue());
        System.out.println("Max value: " + maxPrice.getValue());
        updateResultsTextField();
    }
    
    @FXML
    public void updateResultsTextField() throws SQLException {
    	if(titleTextField.getText().equals("") && currentGenre == null && currentPlatform == null) {
    		resultsTextArea.setText("Invalid query!");
    	} else {
    		if(currentGenre == null) {
    			currentGenre = "";
    		}
    		if(currentPlatform == null) {
    			currentPlatform = "";
    		}
    		try {
    			ResultSet rs = dbs.customerSearch(titleTextField.getText(), currentGenre, currentPlatform, minPrice.getValue(), maxPrice.getValue());
        		ResultSetMetaData rsmd = rs.getMetaData();
        		String str = "";
        	    int columnsNumber = rsmd.getColumnCount();
        	    int count = 0;
        	    str = str + "+++++++++++++++++++++++++++\n";
        	    while(rs.next()) {
        	    	count++;
        	    	str = str + "-------------------------------------\n";
        	        for(int i = 1; i <= columnsNumber; i++) {
        	            String columnValue = rs.getString(i);
        	            str = str + "" + rsmd.getColumnName(i) + ": " + columnValue + "\n";
        	        }
        	        str = str + "-------------------------------------\n";
        	    }
        	    str = str + "+++++++++++++++++++++++++++\n";
        	    str = "Number of entries: " + count + "\n" + str;
        	    resultsTextArea.setText(str);
    		} catch(NullPointerException e) {
    			resultsTextArea.setText("Invalid query!");
    		}
    	}
    }
    
    @FXML
    public void resetButtonClick() {
        titleTextField.setText("");
        genreComboBox.setValue("");
        platformComboBox.setValue("");
        minPrice.setValue(0);
        maxPrice.setValue(0);
        resultsTextArea.setText("");
    }
    
    @FXML
    public void keepMinAlignedWithMax() {
        if(minPrice.getValue() >  maxPrice.getValue()) {
             minPrice.setValue(maxPrice.getValue());
        }
    }
    
    @FXML
    public void closeCustomerWindow() {
        System.out.println("Closing from customer window.");
        System.exit(0);
    }
}
