/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxwfxml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
        try {
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
        } catch (Exception e) {
            return 0;
        }
        return size;
    }

    public void employeeLogin(String user, String pw) throws SQLException {
        String sql = String.format("SELECT * FROM employees WHERE employeeID='%s' AND password='%s';", user, pw);
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(sql);
        if (getResultSetRowCount(rs) != 1) {
            throw new SQLException("ID / PW combination not found.");
        }
    }

    public int addGame(String title, String date, String cost, String genre, String platform, boolean multiplayer) throws SQLException {

        String qty = "0";
        String mult = "F";
        if (multiplayer) {
            mult = "T";
        }
        String sql = String.format("INSERT INTO games VALUES('%s','%s','%s','%s','%s','%s','%s');", title, platform, date, cost, genre, mult, qty);
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql);

    }

    public int addDLC(String gameName, String dlcName, String date, String cost, String platform) throws SQLException {

        String sql = String.format("INSERT INTO dlc VALUES('%s','%s','%s','%s','%s');", gameName, platform, dlcName, date, cost);
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql);
    }

    public int addUpcoming(String title, String date, String cost, String genre, String platform, boolean mult) throws SQLException {
        String multStr = "F";
        if (mult) {
            multStr = "T";
        }

        String sql = "INSERT INTO upcoming VALUES(?,?,?,?,?,?);";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, title);
        st.setString(2, platform);

        if (cost.equals("")) {
            st.setNull(3, java.sql.Types.DECIMAL);
        } else {
            st.setString(3, cost);
        }

        if (genre.equals("")) {
            st.setNull(4, java.sql.Types.VARCHAR);
        } else {
            st.setString(4, genre);
        }

        st.setString(5, multStr);

        if (date.equals("")) {
            st.setNull(6, java.sql.Types.DATE);
        } else {
            st.setString(6, date);
        }

        return st.executeUpdate();
    }

    public int restockGame(String title, String platform, String qty) throws SQLException {
        String sql = String.format("UPDATE games SET qty='%s' WHERE game_title='%s' AND platform_name='%s'", qty, title, platform);
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql);
    }

    public int addPlatform(String abv, String name, String date) throws SQLException {
        String sql = String.format("INSERT INTO platforms VALUES('%s','%s','%s');", name, abv, date);
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql);
    }

    public int updateCost(String title, String platform, String newCost) throws SQLException {
        String sql = String.format("UPDATE games SET cost='%s' WHERE game_title='%s' AND platform_name='%s'", newCost, title, platform);
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql);
    }

    public ResultSet customerSearch(String title, String genre, String platform, double min, double max) throws SQLException {
        String sql = "SELECT * FROM games WHERE ";
        if ((!(title.equals(""))) && (!(genre.equals(""))) && (!(platform.equals("")))) {
            sql = sql + String.format("game_title LIKE '%%%s%%' AND genre='%s' AND platform_name='%s' AND cost>'%f' AND cost<'%f';", title, genre, platform, min, max);
        } else if ((!(title.equals(""))) && (!(genre.equals(""))) && platform.equals("")) {
            sql = sql + String.format("game_title LIKE '%%%s%%' AND genre='%s' AND cost>'%f' AND cost<'%f';", title, genre, min, max);
        } else if ((!(title.equals(""))) && genre.equals("") && (!(platform.equals("")))) {
            sql = sql + String.format("game_title LIKE ''%%%s%%' AND platform_name='%s' AND cost>'%f' AND cost<'%f';", title, platform, min, max);
        } else if ((!(title.equals(""))) && genre.equals("") && platform.equals("")) {
            sql = sql + String.format("game_title LIKE '%%%s%%' AND cost>'%f' AND cost<'%f';", title, min, max);
        } else if (title.equals("") && (!(genre.equals(""))) && (!(platform.equals("")))) {
            sql = sql + String.format("genre='%s' AND platform_name='%s' AND cost>'%f' AND cost<'%f';", genre, platform, min, max);
        } else if (title.equals("") && (!(genre.equals(""))) && platform.equals("")) {
            sql = sql + String.format("genre='%s' AND cost>'%f' AND cost<'%f';", genre, min, max);
        } else if (title.equals("") && genre.equals("") && (!(platform.equals("")))) {
            sql = sql + String.format("platform_name='%s';", platform);
        }
        System.out.println("SQL: " + sql);
        Statement stmt = conn.createStatement();
        if ((!(sql.equals("SELECT * FROM games WHERE ")))) {
            return stmt.executeQuery(sql);
        } else {
            return null;
        }
    }

    public void viewUpcoming() throws SQLException {
        String sql = String.format("SELECT * from upcoming");
        Statement stmt = conn.createStatement();
        stmt.executeQuery(sql);
    }

    public void reprintReceipt(String purchaseID) throws SQLException {
        String sql = String.format("SELECT purchase_id FROM purchase_items WHERE purchase_id='%s'", purchaseID);
        Statement stmt = conn.createStatement();
        stmt.executeQuery(sql);
    }
}
