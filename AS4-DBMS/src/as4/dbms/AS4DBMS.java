package as4.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            
            String zipCheck = String.format("SELECT * FROM zipcodes WHERE zip='%s'", customerZip);
            Statement stmt1 = conn.createStatement();
            ResultSet rs = stmt1.executeQuery(zipCheck);

            if (getResultSetRowCount(rs) == 0) {
                //no zip found
                System.out.println("Please enter a city name for the new zipcode:");
                String zipCityName = scanner.nextLine();
                String sql3 = String.format("INSERT INTO zipcodes(zip, city) VALUES ('%s','%s');", customerZip, zipCityName);
                Statement stmt3 = conn.createStatement();
                int result3 = stmt3.executeUpdate(sql3);
            } 
            
            System.out.println("Please enter a customer phone number:");
            String customerNumber = scanner.nextLine();

            String sql = String.format("INSERT INTO customers (cname, street, zip, phone) VALUES ('%s','%s','%s','%s');", customerName, customerStreet, customerZip, customerNumber);
            Statement stmt2 = conn.createStatement();
            int result2 = stmt2.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("\n!--- Error in addACustomer(). " + e.getMessage());
        }
    }
    
    public static void addAnOrder(Connection conn) {
        try {
            System.out.println("Please enter a customer number:");
            scanner.nextLine();
            String customerNum = scanner.nextLine();
            System.out.println("Please enter an employee number:");
            String employeeNum = scanner.nextLine();
            
            String sql = String.format("INSERT INTO orders(cno, eno) VALUES('%s','%s');", customerNum, employeeNum);
            //String sql2 = String.format("INSERT INTO order_line() VALUES('%s', '%s')");, );
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("\n!--- Error in addAnOrder(). " + e.getMessage());
        }
    }
    
    public static void removeAnOrder(Connection conn) {
        try {
            System.out.println("Please enter an order number to delete:");
            scanner.nextLine();
            String orderToRemove = scanner.nextLine();
            
            String sql = String.format("DELETE FROM orders WHERE ono='%s';", orderToRemove);
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("\n!--- Error in removeAnOrder(). " + e.getMessage());
        }
    }
    
    public static void shipAnOrder(Connection conn) {
        try {
            System.out.println("Please enter an unique order number to ship:");
            scanner.nextLine();
            String orderToShip = scanner.nextLine();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date now = new Date();
            String dateTime = dateFormat.format(now);
            
            String sql = String.format("UPDATE orders SET shipped = '%s' WHERE ono='%s';", dateTime, orderToShip);
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("\n!--- Error in shipAnOrder(). " + e.getMessage());
        }
    }
    
    public static void printPending(Connection conn) {
        String sql = "SELECT * FROM orders WHERE shipped is NULL;";
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);      //exec statement -> table (rs)
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();       //Column count 
            String columnHeadings = "";
            try  {
                for(int i = 1; i < colCount; i++) {
                    if(i < 4) {
                        columnHeadings += meta.getColumnName(i) + ",\t";
                    } else {
                        columnHeadings += meta.getColumnName(i) + "\t\t";
                    }
                }
                System.out.println(columnHeadings);
                String row = "";
                while (rs.next()) {
                    int count = 0;
                    String customerNum = "";
                    for(int i = 1; i < colCount; i++) {
                        if (i == 2) {
                           customerNum = rs.getString(i);
                        }
                        if (i < 4) {
                            row += rs.getString(i) + ",\t";
                        } else {
                            row += rs.getString(i) + "\t\t";
                        }
                    }
                    System.out.println(row);
                    printCustomerInfo(conn, customerNum);
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
            System.out.println("\n!--- Error on printPending()! " + ex.getMessage());
        }
    }
    
    public static void printCustomerInfo(Connection conn, String customer) {
        
        String sql = String.format("SELECT * FROM customers WHERE cno='%s';", customer);
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);      //exec statement -> table (rs)
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();       //Column count 
            
            String columnHeadings = "";
            for(int i = 1; i < colCount; i++) {
                    if(i < 4) {
                        columnHeadings += "\t" + meta.getColumnName(i) + "\t";
                    } else {
                        columnHeadings += "\t" + meta.getColumnName(i) + "\t";
                    }
                }
                System.out.println(columnHeadings);
                String row = "";
                while (rs.next()) {
                    int count = 0;
                    for(int i = 1; i < colCount; i++) {
                        if (i < 4) {
                            row += "\t" + rs.getString(i) + "\t";
                        } else {
                            row += "\t"+ rs.getString(i) + "\t";
                        }
                    }
                    System.out.println(row);
                    count++;
                    row = "";
                }

        } catch(Exception ex) {
            System.out.println("\n!--- Error on printCustomerInfo()! " + ex.getMessage());
        }
        
    }
    
    public static void restockParts(Connection conn) {
        
    }
}
