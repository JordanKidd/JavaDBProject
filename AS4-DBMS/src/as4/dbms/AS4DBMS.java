package as4.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AS4DBMS {
    
    static final String DB_URL = "jdbc:mysql://localhost/lab2";
    static Scanner scanner;

    public static void main(String[] args) throws SQLException {
        
        //Database:
        String user = "jordan";
        String password = "";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, user, password);
            
            //Input:
            scanner = new Scanner(System.in);
            int userChoice = 0;
            while(true) {
                System.out.println("What do you want to do?");
                System.out.println("1. Add a customer");
                System.out.println("2. Add an order");
                System.out.println("3. Remove an order");
                System.out.println("4. Ship an order");
                System.out.println("5. Print pending order list");
                System.out.println("6. Restock parts");
                System.out.println("7. Exit");
                System.out.print("--> ");
                String stringUserChoice = scanner.next();

                try {
                    userChoice = Integer.parseInt(stringUserChoice.trim());
                    switch(userChoice) {
                        case 1:
                            addACustomer(conn);
                            break;
                        case 2:
                            addAnOrder(conn);
                            break;
                        case 3:
                            removeAnOrder(conn);
                            break;
                        case 4: 
                            shipAnOrder(conn);
                            break;
                        case 5: 
                            printPending(conn);
                            break;
                        case 6: 
                            restockParts(conn);
                            break;
                        case 7:
                            System.out.println("\nGoodbye.");
                            return;
                        default:
                            System.out.println("\nPlease pick a number from the menu.");
                            break;  
                    }
                } catch(Exception e) {
                    System.out.println("\nError! Please enter an integer...");
                }
            } //end while loop
        } catch(Exception e) {
            System.out.println("\nError on connection! Has the DB been started? Message: " + e.getMessage());
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
                
                String[] returned = new String[getResultSetRowCount(rs)];
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
    
    public static int getResultSetRowCount(ResultSet rs){ 
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
    
    public static void addACustomer(Connection conn) {
        try {
            System.out.println("Please enter a customer name:");
            scanner.nextLine();
            String customerName = scanner.nextLine();
            System.out.println("Please enter a customer street:");
            String customerStreet = scanner.nextLine();
            System.out.println("Please enter a customer zip:");
            String customerZip = scanner.nextLine();
            System.out.println("Please enter a customer phone number:");
            String customerNumber = scanner.nextLine();

            String sql = String.format("INSERT INTO customers (cname, street, zip, phone) VALUES ('%s','%s','%s','%s');", customerName, customerStreet, customerZip, customerNumber);
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);


        } catch (Exception e) {
            System.out.println("\n error in addACustomer(). " + e.getMessage());
        }
    }
    
    public static void addAnOrder(Connection conn) {
        
    }
    
    public static void removeAnOrder(Connection conn) {
        
    }
    
    public static void shipAnOrder(Connection conn) {
        
    }
    
    public static void printPending(Connection conn) {
        
    }
    
    public static void restockParts(Connection conn) {
        
    }
}
