/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbsemesterproject;

import javax.swing.UIManager;

/**
 *
 * @author jordankidd
 */
public class DBSemesterProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Starting GUI...");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Error setting L&F! " + ex.getMessage());
        }
        
        WelcomeScreen ws = new WelcomeScreen();
        ws.setVisible(true);
    }
    
}
