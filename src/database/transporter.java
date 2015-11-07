package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class transporter {
	public static void delivered(String packageid){
		Connection connection =null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("update package set deliverydate = ? where packageID = ?");
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String todaysdate = dateFormat.format(date);
			pstmt.setDate(1, date);
			pstmt.setInt(2, Integer.parseInt(packageid));
			pstmt.executeUpdate();
			pstmt = connection.prepareStatement("update package set currentlocation="
					+ "(select address from orders inner join usr ON (orders.customerID = usr.username) "
					+ "where orders.packageID = ?) where packageID = ?");
			pstmt.setInt(2, Integer.parseInt(packageid));
			pstmt.setInt(1, Integer.parseInt(packageid));
			pstmt.executeUpdate();
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during delivered");
			System.out.println(sqle);
		} finally{
			closeConnection(connection);
		}
	}
	
	public static void newlocation(String packageid, String newlocation){
		Connection connection =null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("select usr.address from package natural join orders inner join usr ON (usr.username = orders.customerID) where packageid=?;");
			pstmt.setInt(1, Integer.parseInt(packageid));
			ResultSet rs = pstmt.executeQuery();
			String address=null;
			if(rs.next()){
				address=rs.getString(1);
			}
			if(address.equals(newlocation)){
				delivered(packageid);
			}
			else{
				pstmt = connection.prepareStatement("update package set currentlocation = ? where packageid = ?");
				pstmt.setString(1, newlocation);
				pstmt.setInt(2, Integer.parseInt(packageid));
				pstmt.executeUpdate();
			}
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during newlocation");
			System.out.println(sqle);
		} finally{
			closeConnection(connection);
		}
	}
	
	public static ArrayList<ArrayList<String>> getPendingProducts(String username){
		ArrayList<ArrayList<String>> pendingproducts = new ArrayList<ArrayList<String>>();
		Connection connection =null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement(" select productdescription.name, package.packagequantity, seller.address, package.currentlocation,packageID,usr.name,usr.contact from productdescription natural join orders natural join package inner join seller ON (seller.username = package.sellerID) inner join usr ON (usr.username = orders.customerID) where transportationid = ? and package.deliverydate is null;");
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				ArrayList<String> pack= new ArrayList<String>();
				String productName, quantity, address, currentlocation, packageid, usrname, usrcontact;
				productName = rs.getString(1);
				quantity = rs.getString(2);
				address = rs.getString(3);
				currentlocation = rs.getString(4);
				packageid = rs.getString(5);
				usrname = rs.getString(6);
				usrcontact = rs.getString(7);
				
				pack.add(productName);
				pack.add(quantity);
				pack.add(address);
				pack.add(currentlocation);
				pack.add(packageid);
				pack.add(usrname);
				pack.add(usrcontact);
				pendingproducts.add(pack);
			}
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during getPendingProducts");
		} finally{
			closeConnection(connection);
		}
		return pendingproducts;
	}
	
	public static ArrayList<ArrayList<String>> getShippedProducts(String username){
		ArrayList<ArrayList<String>> shippedproducts = new ArrayList<ArrayList<String>>();
		Connection connection =null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement(" select productdescription.name, package.packagequantity, seller.address, package.currentlocation"
					+ " from productdescription natural join package inner join seller ON (seller.username = package.sellerID) "
					+ "where transportationid = ? and package.deliverydate is not null;");
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				ArrayList<String> pack= new ArrayList<String>();
				String productName, quantity, address, currentlocation;
				productName = rs.getString(1);
				quantity = rs.getString(2);
				address = rs.getString(3);
				currentlocation = rs.getString(4);
				
				pack.add(productName);
				pack.add(quantity);
				pack.add(address);
				pack.add(currentlocation);
				shippedproducts.add(pack);
			}
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during getShippedProducts");
			System.out.println(sqle);
		} finally{
			closeConnection(connection);
		}
		return shippedproducts;
	}
	
	static Connection getConnection() {
		String dbURL = "jdbc:postgresql://10.105.1.12/cs387";
        String dbUser = "db130050060";
        String dbPass = "db130050060";
        Connection connection=null;
        try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
        } catch(ClassNotFoundException cnfe){
        	System.out.println("JDBC Driver not found");
        } catch(SQLException sqle){
        	System.out.println("Error in getting connetcion from the database");
        }
        
        return connection;
	}
	
	static void closeConnection(Connection connection) {
		try{
			connection.close();
		} catch(SQLException sqle) {
			System.out.println("Error in close database connetcion");
		}
	}
}
