package as4.dbms;

import java.sql.*;

public class AS4DBMS {
    
    static final String DB_URL = "jdbc:mysql://localhost/lab2";

    public static void main(String[] args) throws SQLException {
        
        //Database:
        String user = "jordan";
        String password = "";
        
        Connection conn = DriverManager.getConnection(DB_URL, user, password);
        
        String sql = "select * from customers;";
        try {
            Statement stmt = conn.createStatement();    //Connection to statement
            ResultSet rs = stmt.executeQuery(sql);      //exec statement -> table (rs)
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();       //Column count 
            String row = "";
            try  {
                for(int i = 1; i < colCount+1; i++) {
                    row += String.format("%-26s", meta.getColumnName(i));
                }
                row = "";       //RESET LINE
                
                while (rs.next()) {
                    for(int i = 1; i < colCount+1; i++) {
                        row += String.format("%-26s", rs.getString(i));
                    }
                    row = "";       //RESET LINE
                }
            } finally {
              rs.close();
            }
            System.out.println();
            stmt.close();    
        }
        catch (SQLException ex) {
            System.out.println("ERROR ON SELECT STATEMENT!");
            System.out.println(ex.toString());
        }
    }
    
}
