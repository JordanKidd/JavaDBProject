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
import javafx.stage.Stage;

/**
 *
 * @author Jordan
 */
public class LoginAlertController implements Initializable {
    
    @FXML
    private Button okButton;
    
    @FXML
    public Stage loginStage;
    
    @FXML
    private void OkClicked() {
        System.out.println("Clicked alert button.");
        loginStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
    
}
