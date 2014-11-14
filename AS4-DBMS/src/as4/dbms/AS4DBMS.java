/*  CREATED BY: Jordan Kidd
    AS4: CS4430 - DBMS
    DUE: Thursday, Nov 13 @ 11:59PM  */

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

    public static Scanner scanner =  new Scanner(System.in);

	public static void main(String[] args) throws SQLException {

		String user = "root";
		String password = "root";
		Connection conn = null;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/lab2", user, password);
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

				String userChoiceString = scanner.next();
				try {
					userChoice = Integer.parseInt(userChoiceString.trim());
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
						System.out.println("\n!--- Please pick a number from the menu.");
					}
				} catch(Exception e) {
					System.out.println("\n!---Error! Please enter an integer...");
				}
			}
		} catch(Exception e) {
			System.out.println("\n!--- Error! Has the DB been started? Message: " + e.getMessage());
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


	private static void printCustomerInfo(Connection conn, String customer) {
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


	private static void addACustomer(Connection conn) {
		try {
			System.out.println("Please enter a customer name: ");
			scanner.nextLine();
			String customerName = scanner.nextLine();
			System.out.println("Please enter a customer street: ");
			String customerStreet = scanner.nextLine();
			System.out.println("Please enter a customer zip: ");
			String customerZIP = scanner.nextLine();
			String customerZIPCheck = String.format("SELECT * FROM zipcodes WHERE zip='%s'", customerZIP);
			Statement statement1 = conn.createStatement();
			ResultSet rs = statement1.executeQuery(customerZIPCheck);
			if(getResultSetRowCount(rs) == 0) {
				System.out.println("Please enter a city name for the new zipcode: ");
				String zipCityName = scanner.nextLine();
				String sql2 = String.format("INSERT INTO zipcodes(zip, city) VALUES ('%s','%s');", customerZIP, zipCityName);
				Statement statement2 = conn.createStatement();
				statement2.executeUpdate(sql2);
			}
			System.out.println("Please enter a customer phone number: ");
			String customerPhoneNumber = scanner.nextLine();
			String sql3 = String.format("INSERT INTO customers (cname, street, zip, phone) VALUES ('%s','%s','%s','%s');", customerName, customerStreet, customerZIP, customerPhoneNumber);
			Statement statement3 = conn.createStatement();
			statement3.executeUpdate(sql3);
		} catch(Exception e) {
			System.out.println("\n!--- Error in addACustomer(). " + e.getMessage());
		}
	}


	private static void addAnOrder(Connection conn) {
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
				//UPDATE THE AMOUNT3
                updateParts(conn, partNum, "-" + Integer.parseInt(qty));
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


	private static void removeAnOrder(Connection conn) {
		try {
			System.out.println("Please enter an order number to delete: ");
			scanner.nextLine();
			String orderToRemove = scanner.nextLine();
			String sql = String.format("SELECT qty FROM order_line WHERE ono='%s';", orderToRemove);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			String quantity = "";
			while(rs.next()) {
				for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
					quantity = rs.getString(i);
				}
			}
			sql = String.format("SELECT pno FROM order_line WHERE ono='%s'", orderToRemove);
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			String pno = "";
			while(rs.next()) {
				for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
					pno = rs.getString(i);
				}
			}

			updateParts(conn, pno, quantity);
			sql = String.format("DELETE FROM order_line WHERE ono='%s';", orderToRemove);
			statement = conn.createStatement();
			statement.executeUpdate(sql);
			sql = String.format("DELETE FROM orders WHERE ono='%s';", orderToRemove);
			statement = conn.createStatement();
			statement.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("\n!--- Error in removeAnOrder(). " + e.getMessage());
		}
	}


	private static void shipAnOrder(Connection conn) {
		try {
			System.out.println("Please enter a unique order number to ship: ");
			scanner.nextLine();
			String orderToShip = scanner.nextLine();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date now = new Date();
			String dateTime = dateFormat.format(now);
			String sql = String.format("UPDATE orders SET shipped = '%s' WHERE ono='%s';", dateTime, orderToShip);
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("\n!--- Error in shipAnOrder(). " + e.getMessage());
		}
	}


	private static void printPending(Connection conn) {
		String sql = "SELECT * FROM orders WHERE shipped is NULL ORDER BY received;";
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql); //exec statement -> table (rs)
			ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount(); //Column count
			String columnHeadings = "";
			try {
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


	private static void restockParts(Connection conn) {
		try {
			System.out.println("Please enter the unique part number: ");
			String pno = scanner.next();
			System.out.println("Please enter the quantity difference: ");
			String quantity = scanner.next();
			updateParts(conn, pno, quantity);
		} catch(Exception e) {
			System.out.println("\n!--- Error on restockParts(). " + e.getMessage());
		}
	}


	private static void updateParts(Connection conn, String pno, String quantity) {
		try {
			int quantityInt = (int) Integer.parseInt(quantity);
			String sql = String.format("SELECT qoh FROM parts WHERE pno='%s'", pno);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			rs = statement.executeQuery(sql);
			if(getResultSetRowCount(rs) == 0) {
				throw new Exception("PNO not found in parts table");
			}
			String currentQuantity = "";
			while(rs.next()) {
				for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
					currentQuantity = rs.getString(i);
				}
			}
			int currentQuantityInt = (int) Integer.parseInt(currentQuantity);
			quantityInt = currentQuantityInt + quantityInt;
			sql = String.format("UPDATE parts SET qoh = '%s' WHERE pno='%s';", quantityInt, pno);
			statement = conn.createStatement();
			statement.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("\n!--- Error on restockParts(). " + e.getMessage());
		}
	}

}
