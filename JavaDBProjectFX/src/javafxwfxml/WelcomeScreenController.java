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
    
    private DatabaseService dbs;
    
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
    private MenuItem customerMenuItem;
    
    
    @FXML
    private void loginButtonClick() {
        System.out.println("login button clicked"); 
        String user = employeeIdTB.getText();
        String pw = employeePwTB.getText();
        try {
            dbs.employeeLogin(user, pw);
            System.out.println("!! - User credentials accepted! Now showing employee window.");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeWindow.fxml"));
            Parent root = (Parent) loader.load();
            EmployeeWindowController empWindowCon = (EmployeeWindowController) loader.getController();
            empWindowCon.dbs = dbs;
            empWindowCon.userId = user;
            empWindowCon.setupPlatforms();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (Exception ex1) {
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindowAlert.fxml"));
                Parent root = (Parent) loader.load();
                LoginAlertController alertCont = (LoginAlertController) loader.getController();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                alertCont.loginStage = stage;
                stage.show();
    
            } catch (Exception ex2) {
                System.out.println("Error on showing login window alert. " + ex2.getMessage());
            }
        }
    }
    
    @FXML
    private void openCustomerWindow() {
        System.out.println("Customer window load...");
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerWindow.fxml"));
            Parent root = (Parent) loader.load();
            CustomerWindowController cwCont = (CustomerWindowController) loader.getController();
            cwCont.setDbs(dbs);
            Stage stage = new Stage();
            stage.centerOnScreen();
            stage.setTitle("Customer Tool");
            stage.setScene(new Scene(root));
            
            Stage customerStage = (Stage) loginButton.getScene().getWindow();
            customerStage.hide();
            
            stage.show();
        } catch (IOException e) {
            System.out.println("error on customer window launch.");
        }
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
        System.out.println("Starting connection to the database....");
        try {
            dbs = new DatabaseService("jordan", "");
        } catch (Exception ex) {
            System.out.println("Error on connection to database and creation of service object! " + ex.getMessage());
        }
    }    
    
}
