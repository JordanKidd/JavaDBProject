/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxwfxml;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author jordankidd
 */
public class DatabaseService {
    
    private String user;
    private String password;
    private static final String DB_URL = "jdbc:mysql://localhost/dbproject";
    private Connection conn;
    
    public DatabaseService(String user, String password) {
        //setup with db connect and stuff here
        try
        {
            this.user = user;
            this.password = password;
            this.conn = DriverManager.getConnection(DB_URL, user, password);
        } catch (Exception ex) {
            System.out.println("Error on DatabaseService init! " + ex.getMessage());
        }
    }
    
    public void employeeLogin(String user, String pw) throws IOException {
        //throw new IOException("Oh snap!");
    }
    
    public void addGame() {
        
    }
    
    public void makePurchase() {
        
    }
    
    public void addDLC() {
        
    }
    
    public void addUpcoming() {
        
    }
    
    public void updateQty() {
        
    }
    
    public void addPlatform() {
        
    }
    
    public void updateCost(String title, String platform, float cost) {
        
    }
    
    public void customerSearchByTitle(String title) {
        
    }
    
    public void customerSearchByGenre(String genre) {
        
    }
    
    public void customerSearchByPlatform(String platform) {
        
    }
    
    public void viewUpcoming() {
        
    }
    
    public void reprintReceipt(String purchaseID) {
        
    }
    
    
    
}
