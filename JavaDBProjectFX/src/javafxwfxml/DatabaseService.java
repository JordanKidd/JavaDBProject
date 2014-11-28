/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxwfxml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jordankidd
 */
public class DatabaseService {
    
    private String user;
    private String password;
    private static final String DB_URL = "jdbc:mysql://localhost/VideoGameDB";
    private Connection conn;
    
    public DatabaseService(String user, String password) {
        //setup with db connect and stuff here
        try
        {
            this.user = "root";
            this.password = "";
            this.conn = DriverManager.getConnection(DB_URL, this.user, this.password);
        } catch (Exception ex) {
            System.out.println("Error on DatabaseService init! " + ex.getMessage());
        }
    }
    
	private static int getResultSetRowCount(ResultSet rs) {
		int size = 0;
		try {
			rs.last();
			size = rs.getRow();
			rs.beforeFirst();
		} catch(Exception e) {
			return 0;
		}
		return size;
	}
    
    public void employeeLogin(String user, String pw) throws SQLException {
        String sql = String.format("SELECT * FROM employees WHERE employeeID='%s' AND password='%s';", user, pw);
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(sql);
        if(getResultSetRowCount(rs) != 1) {
            throw new SQLException("ID not found.");
        }
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
