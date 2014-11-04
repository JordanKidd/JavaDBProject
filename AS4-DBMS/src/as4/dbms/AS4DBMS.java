package as4.dbms;

import java.sql.*;
import java.util.Scanner;

public class AS4DBMS {
    
    static final String DB_URL = "jdbc:mysql://localhost/lab2";

    public static void main(String[] args) throws SQLException {
        
        //Database:
        String user = "jordan";
        String password = "";
        Connection conn = DriverManager.getConnection(DB_URL, user, password);
        
        //Input:
        Scanner scanner = new Scanner(System.in);
        int userChoice = -1;
        while(true) {
            System.out.println("What do you want to do?");
            System.out.println("1. Add a customer");
            System.out.println("2. Add an order");
            System.out.println("3. Remove an order");
            System.out.println("4. Ship an order");
            System.out.println("5. Print pending order list");
            System.out.println("6. Restock parts");
            System.out.println("7. Exit");
            
            userChoice = scanner.nextInt();
            
            switch(userChoice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4: 
                    break;
                case 5: 
                    break;
                case 6: 
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Please pick a number from the menu.");
                    break;
                    
            }
            
        }
    }
    
    public static void runSelect(Connection conn) {
        String sql = "select * from customers;";
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);      //exec statement -> table (rs)
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();       //Column count 
            String columnHeadings = "";
            try  {
                for(int i = 1; i < colCount+1; i++) {
                    columnHeadings += meta.getColumnName(i) + ",";
                }
                
                String[] returned = new String[getResultSetSize(rs)];
                String row = "";
                while (rs.next()) {
                    int count = 0;
                    for(int i = 1; i < colCount + 1; i++) {
                        row += rs.getString(i) + ",";
                    }
                    returned[count] = row;
                    count++;
                    row = "";
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
    
    public static int getResultSetSize(ResultSet rs){ 
        int size = 0;
        try {
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
        }
        catch(Exception ex) {
            return 0;
        }
        return size;
    }
    
}
