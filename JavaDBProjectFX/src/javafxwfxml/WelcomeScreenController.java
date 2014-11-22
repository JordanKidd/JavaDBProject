/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxwfxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author jordankidd
 */
public class WelcomeScreenController implements Initializable {
    
    @FXML
    private Button loginButton;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private TextField employeeIdTB;
    @FXML
    private PasswordField employeePwTB;
    @FXML
    private Label employeeIdLabel;
    @FXML
    private Label employeePwLabel;
    
    @FXML
    private void loginButtonClick() {
        System.out.println("login button clicked");
    }
    
    @FXML
    private void launchAboutWindow() {
        System.out.println("About page load...");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AboutWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("About");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("error on aboutwindow launch.");
        }
    }

    public void closeWelcomePage() {
        // get a handle to the stage
        System.out.println("close welcome page");
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}