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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 *
 * @author jordankidd
 */
public class AboutPageController implements Initializable { 
    
    @FXML
    private Button closeButton;
    @FXML
    private VBox aboutVBox;
    @FXML
    private AnchorPane aboutWindow;
    @FXML
    private Label aboutLabel;
    @FXML
    private TextArea aboutTextArea;

    @FXML
    public void closeAboutPage() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    
}
