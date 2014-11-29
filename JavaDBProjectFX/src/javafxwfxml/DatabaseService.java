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
    public Connection conn;
    
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
    
	public int getResultSetRowCount(ResultSet rs) {
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
            throw new SQLException("ID / PW combination not found.");
        }
    }
    
    public void addGame(String title, String date, String cost, String genre, String platform, boolean multiplayer) throws SQLException {
        try {
            String qty = "0";
            String mult = "F";
            if (multiplayer) {
                mult = "T";
            }
            String sql = String.format("INSERT INTO games VALUES('%s','%s','%s','%s','%s','%s','%s');", title, platform, date, cost, genre, mult, qty);
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.executeUpdate(sql);
        } catch(Exception ex) {
            System.out.print("failed to add game");
        }
    }
    
    public void makePurchase() {
        
    }
    
    public void addDLC() {
        
    }
    
    public void addUpcoming() {
        
    }
    
    public void updateQty() {
        
    }
    
    public void addPlatform(String abv, String name, String date) throws SQLException {
        String sql = String.format("INSERT INTO platforms VALUES('%s','%s','%s');", name, abv, date);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
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
