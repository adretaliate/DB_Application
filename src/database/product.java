package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class product {
	public static ArrayList<ArrayList<String>> products(Integer id){
		ArrayList<ArrayList<String>> prods = new ArrayList<ArrayList<String>>();
		
		Connection connection=null;
		try{
			connection = getConnection();
			PreparedStatement pstmt,pstmt1;
			pstmt = connection.prepareStatement("select * from productdescription "
					+ "where id>=? and id<=?");
			pstmt.setInt(1, id);
			pstmt.setInt(2, id+10);
			
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				ArrayList<String> prod = new ArrayList<String>();
				System.out.println(rs.getString(2));
				pstmt1 = connection.prepareStatement("select min(price) from product where productID=?");
				pstmt1.setInt(1, Integer.parseInt(rs.getString(1)));
				ResultSet rs1 = pstmt1.executeQuery();
				rs1.next();
				System.out.println(rs1.getString(1));
				prod.add(rs.getString(1)); prod.add(rs.getString(2));prod.add(rs1.getString(1));
				prods.add(prod);
			}
			
		} catch(SQLException sqle){
			System.out.println("SQL exception during product fetching");
		} finally{
			System.out.println("Came here1");
			closeConnection(connection);
		}
		return prods;
	}
	
	public static Integer getMaxProductId()
	{
		Integer num=0;
		Connection connection=null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("select max(id) from productDescription");
			ResultSet rs = pstmt.executeQuery();
			num  = rs.getInt(1);
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during getMaxProductId");
		} finally{
			closeConnection(connection);
		}
		return num;
		
	}
	
	public static ArrayList<ArrayList<String>> getSellers(String productID)
	{
		ArrayList<ArrayList<String>> sellers = new ArrayList<ArrayList<String>>();
		Connection connection =null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("select seller.name, product.price, product.discount, seller.username from product natural join seller where productID=?");
			pstmt.setString(1,productID);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				ArrayList<String> seller= new ArrayList<String>();
				String name,price,discount,username,discounted_price;
				name = rs.getString(1);
				price = rs.getString(2);
				discount = rs.getString(3);
				username = rs.getString(4);
				discounted_price = Double.toString(Double.parseDouble(price)*(1-Double.parseDouble(discount)/100));
				
				seller.add(name);
				seller.add(price);
				seller.add(discount);
				seller.add(discounted_price);
				seller.add(username);
				
				sellers.add(seller);
				
				
			}
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during getSellers");
		} finally{
			closeConnection(connection);
		}
		return sellers;
	}
	
	public static void addToCart(String customerID, String productID, String sellerID)
	{
		Connection connection =null;
		try{
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("insert into cart values(?,?,?,1) On conflict (customerID, productID,sellerID) DO Update SET quantity = cart.quantity+1");
			pstmt.setString(1,customerID);
			pstmt.setString(2,sellerID);
			pstmt.setString(3,productID);
			pstmt.executeUpdate();
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during getSellers");
		} finally{
			closeConnection(connection);
		}
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
