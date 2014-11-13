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
        
        //CHANGE TO ROOT / ROOT:
        String user = "jordan";
        String password = "";
        Connection conn;
        
        try {
            conn = DriverManager.getConnection(DB_URL, user, password);
            
            //Input:
            scanner = new Scanner(System.in);
            int userChoice = 0;
            while(true) {
                System.out.println("\nWhat do you want to do?");
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
    
    
    /*  When enter a new customer:
        CNO should be automatically generated. (e.g. the biggest existing number + 1).
        ZIP should exist in ZIPCODES table. If not, add the entry into ZIPCODES by asking for CITY. */
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
    
    
    /*
    When add an order:
    Add to both ORDERS and ORDER_LINE. ONO and RECEIVED should be automatically filled.
    Pay attention to the foreign key constraints on CNO, ENO and PNO.
    Update the part's QUANTITY_ON_HAND.
    The order should be rejected if it makes the updated QUANTITY_ON_HAND  < 0.
    */
    public static void addAnOrder(Connection conn) {
        try {
            System.out.println("Please enter a customer number:");
            scanner.nextLine();
            String customerNum = scanner.nextLine();
            System.out.println("Please enter an employee number:");
            String employeeNum = scanner.nextLine();
            System.out.println("Please enter a part number for the order: ");
            String partNum = scanner.nextLine();
            
            String sql = String.format("INSERT INTO orders(cno, eno) VALUES('%s','%s');", customerNum, employeeNum);
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);
            
            sql = String.format("SELECT * FROM orders WHERE cno='%s' AND eno='%s';", customerNum, employeeNum);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount(); 
            String ono = "";
            while (rs.next()) {
                for(int i = 1; i < colCount; i++) {
                    if (i == 1) {
                       ono = rs.getString(i);
                    }
                }
            }
             
            System.out.println("Quantity requested:");
            String qty = scanner.nextLine();
            
            String getQty = String.format("SELECT qoh FROM parts WHERE pno='%s';", partNum);
            stmt = conn.createStatement();
            ResultSet qtyRS = stmt.executeQuery(getQty);
            meta = qtyRS.getMetaData();
            colCount = meta.getColumnCount(); 
            String qoh = "";
            while (qtyRS.next()) {
                for(int i = 1; i < colCount+1; i++) {
                    qoh = qtyRS.getString(i);
                }
            }
            
            if(Integer.parseInt(qoh) >= Integer.parseInt(qty)) {
                //OK
                sql = String.format("INSERT INTO order_line(ono, pno, qty) VALUES('%s','%s','%s');", ono, partNum, qty);
                stmt = conn.createStatement();
                result = stmt.executeUpdate(sql);
                
                //UPDATE THE AMOUNT
                
                //END UPDATE
            } else {
                //Not enough qty
                sql = String.format("DELETE FROM orders WHERE ono='%s';", ono);
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            
                throw new Exception("Not enough parts in the store to order your quantity requested.");
            }
            
        } catch (Exception e) {
            System.out.println("\n!--- Error in addAnOrder(). " + e.getMessage());
        }
    }
    
    
    /* When remove an order:
    Delete the entry in both the ORDER and the ORDER_LINE tables. 
    However, your code should only have to deal with the ORDER table, the DBMS should handle the ORDER_LINE table automatically.
    NEVER forget to restock parts (update the QUANTITY_ON_HAND attribute) */
    public static void removeAnOrder(Connection conn) {
        try {
            System.out.println("Please enter an order number to delete:");
            scanner.nextLine();
            String orderToRemove = scanner.nextLine();
            
            String sql = String.format("SELECT qty FROM order_line WHERE ono='%s'", orderToRemove);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            String qty = "";
            while (rs.next()) {
                for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
                    qty = rs.getString(i);
                }
            }
            
            //CALL TO RESTOCK HERE!!? qty has amount to adjust ono by (+ value, add order is - value)
            
            sql = String.format("DELETE FROM order_line WHERE ono='%s'", orderToRemove);
            stmt = conn.createStatement();
            int numResult = stmt.executeUpdate(sql);
            
            sql = String.format("DELETE FROM orders WHERE ono='%s';", orderToRemove);
            stmt = conn.createStatement();
            numResult = stmt.executeUpdate(sql);

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
    
    
     /* When printing pending order list:
        Print only pending orders (i.e. orders have not been shipped).
        Print them in the order of received time. */
    public static void printPending(Connection conn) {
        String sql = "SELECT * FROM orders WHERE shipped is NULL ORDER BY received;";
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);      //exec statement -> table (rs)
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();       //Column count 
            String columnHeadings = "";
            try  {
                String row = "";
                
                while (rs.next()) {
                    System.out.println("----Pending Order-----");
                    for(int i = 1; i < colCount; i++) {
                        if(i < 4) {
                            columnHeadings += meta.getColumnName(i) + ",\t";
                        } else {
                            columnHeadings += meta.getColumnName(i) + "\t\t";
                        }
                    }
                    System.out.println(columnHeadings);
                    columnHeadings = "";
                    
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
                    System.out.println("-----Customer-----");
                    printCustomerInfo(conn, customerNum);
                    System.out.println(); //for order and customer spacing
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
    
    
    //Used in printPending()
    public static void printCustomerInfo(Connection conn, String customer) {
        
        String sql = String.format("SELECT * FROM customers WHERE cno='%s';", customer);
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);      
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();       
            
            String columnHeadings = "";
            for(int i = 1; i < colCount; i++) {
                    if(i < 4) {
                        columnHeadings += meta.getColumnName(i) + "\t";
                    } else {
                        columnHeadings += meta.getColumnName(i) + "\t";
                    }
                }
                System.out.println(columnHeadings);
                String row = "";
                while (rs.next()) {
                    int count = 0;
                    for(int i = 1; i < colCount; i++) {
                        if (i < 4) {
                            row += rs.getString(i) + "\t";
                        } else {
                            row += rs.getString(i) + "\t";
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
