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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author jordankidd
 */
public class CustomerWindowController implements Initializable {
    
    public DatabaseService dbs;
    
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
        //TODO
        genreComboBox.getItems().addAll(
                "", //FOR NO SPECIFC GENRE
                "Action",
                "Adventure",
                "Arcade",
                "Educational",
                "Indie",
                "Puzzle",
                "Role-Playing",
                "MMORPG",
                "Simulations",
                "Sports",
                "Strategy"
        );
        
        platformComboBox.getItems().addAll(
                "", //FOR NO SPECIFC GENRE
                "Super Nintendo",
                "Gamecube",
                "Wii",
                "Wii U",
                "Playstation",
                "Playstation 2",
                "Playstation 3",
                "Playstation 4",
                "Xbox",
                "Xbox 360",
                "Xbox One"
        );
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
    public void searchButtonClick() {
        System.out.println("!!-- Current values: ");
        System.out.println("Title: " + titleTextField.getText());
        System.out.println("Genre: " + currentGenre);
        System.out.println("Platform: " + currentPlatform);
        System.out.println("Min value: " + minPrice.getValue());
        System.out.println("Max value: " + maxPrice.getValue());
    }
    
    @FXML
    public void resetButtonClick() {
        titleTextField.setText("");
        genreComboBox.setValue("");
        platformComboBox.setValue("");
        minPrice.setValue(0);
        maxPrice.setValue(0);
    }
    
    @FXML
    public void closeCustomerWindow() {
        // get a handle to the stage
        System.out.println("Closing from customer window.");
        System.exit(0);
    }
}
