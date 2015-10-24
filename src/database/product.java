package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public static Integer getMaxPackageId()
	{
		Integer num=0;
		Connection connection=null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("select max(packageID) from package");
			ResultSet rs = pstmt.executeQuery();
			num  = rs.getInt(1);
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during getMaxPackageId");
		} finally{
			closeConnection(connection);
		}
		return num;
		
	}
	
	public static Integer getMaxOrderId()
	{
		Integer num=0;
		Connection connection=null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("select max(orderID) from orders");
			ResultSet rs = pstmt.executeQuery();
			num  = rs.getInt(1);
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during getMaxOrderId");
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
			System.out.println(productID);
			pstmt = connection.prepareStatement("select seller.name, price,discount, username from product natural join seller where productID=?");
			pstmt.setInt(1, Integer.parseInt(productID));
			
			ResultSet rs = pstmt.executeQuery();
			System.out.println(productID);
			while(rs.next())
			{
				ArrayList<String> seller= new ArrayList<String>();
				String name,price,discount,username,discounted_price;
				name = rs.getString(1);
				price = rs.getString(2);
				discount = rs.getString(3);
				username = rs.getString(4);
				System.out.println(name);
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
			connection = getConnection();
			
			System.out.println("add to cart");
			System.out.println(customerID);
			System.out.println(productID);
			System.out.println(sellerID);
			PreparedStatement pstmt;
			pstmt =  connection.prepareStatement("select * from cart where customerID=? and sellerID=? and productID =? ");
			pstmt.setString(1,customerID);
			pstmt.setString(2,sellerID);
			pstmt.setInt(3,Integer.parseInt(productID));System.out.println("add to cart");
			ResultSet rs=pstmt.executeQuery(); System.out.println("add to cart");
			
			if(rs.next())
			{
				pstmt = connection.prepareStatement("update cart set cartquantity = cartquantity+1 where customerID=? and sellerID=? and productID =? ");
				pstmt.setString(1,customerID);
				pstmt.setString(2,sellerID);
				pstmt.setInt(3,Integer.parseInt(productID));System.out.println("add to cart");
				pstmt.executeUpdate();
			}
			else 
			{
				pstmt = connection.prepareStatement("insert into cart values(?,?,?,1)");
				pstmt.setString(1,customerID);
				pstmt.setString(2,sellerID);
				pstmt.setInt(3,Integer.parseInt(productID));
				pstmt.executeUpdate();
			}
			
			
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during addToCart");
		} finally{
			closeConnection(connection);
		}
	}
	
	public static ArrayList<ArrayList<String>> getCart(String customerID){
		ArrayList<ArrayList<String>> cart = new ArrayList<ArrayList<String>>();
		Connection connection =null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement(" select * from product natural join productdescription natural join cart where customerID=?");
			pstmt.setString(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				ArrayList<String> item= new ArrayList<String>();
				String productName, discounted_price, cartquantity, price, discount, productid, sellerid;
				productName = rs.getString(7);
				price = rs.getString(3);
				discount = rs.getString(5);
				discounted_price = Double.toString(Double.parseDouble(price)*(1-Double.parseDouble(discount)/100));
				cartquantity = rs.getString(11);
				productid=rs.getString(1);
				sellerid=rs.getString(2);
				
				item.add(productName);
				item.add(discounted_price);
				item.add(cartquantity);
				item.add(productid);
				item.add(sellerid);
				cart.add(item);
			}
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during getCart");
		} finally{
			closeConnection(connection);
		}
		return cart;
	}
 	
	public static void delete(String customerID, String sellerID, String productID){
		Connection connection =null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("delete from cart where "
					+ "customerID=? and sellerID=? and productID=?");
			pstmt.setString(1, customerID);
			pstmt.setString(2, sellerID);
			pstmt.setInt(3,Integer.parseInt(productID));
			pstmt.executeUpdate();
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during delete");
			System.out.println(sqle);
		} finally{
			closeConnection(connection);
		}
	}
	
	public static void updateCart(String customerID, String sellerID, String productID, Integer quantity){
		Connection connection =null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("update cart set "
					+ "cartquantity=? where customerID=? and sellerID=? and productID=?");
			pstmt.setInt(1, quantity);
			pstmt.setString(2, customerID);
			pstmt.setString(3, sellerID);
			pstmt.setInt(4,Integer.parseInt(productID));
			pstmt.executeUpdate();
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during updateCart");
		} finally{
			closeConnection(connection);
		}
	}
	
	public static void insertPackage(Integer packageID, String sellerID, Integer productID, 
			Integer pQuantity){
		Connection connection =null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("select address from seller "
					+ "where username=?");
			pstmt.setString(1, sellerID);
			ResultSet rs=pstmt.executeQuery();
			String location=null;
			while(rs.next()){
				location=rs.getString(1);
			}
			pstmt = connection.prepareStatement("select username, (select count(*) from package where transporter.username = package.transportationID and package.deliveryDate is null) as count from transporter order by count asc limit 1;");
			rs=pstmt.executeQuery();
			String tID=null;
			if(rs.next()){
				tID=rs.getString(1);
			}
			pstmt = connection.prepareStatement("insert into package values"
					+ "(?, ?, ?, ?, ?, null, ?)");
			pstmt.setInt(1, packageID);
			pstmt.setString(2, tID);
			pstmt.setString(3, sellerID);
			pstmt.setInt(4, productID);
			pstmt.setInt(5, pQuantity);
			pstmt.setString(6, location);
			pstmt.executeUpdate();
		}catch(SQLException sqle){
			System.out.println("SQL exception during insertPackage");
		} finally{
			closeConnection(connection);
		}
	}
	
	public static void insertOrder(Integer orderID, Integer packageID, String customerID){
		Connection connection =null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String todaysdate = dateFormat.format(date);
			pstmt = connection.prepareStatement("insert into orders values"
					+ "(?, ?, ?, ?)");
			pstmt.setInt(1, orderID);
			pstmt.setInt(2, packageID);
			pstmt.setString(3, customerID);
			pstmt.setString(4, todaysdate);
			pstmt.executeUpdate();
		}catch(SQLException sqle){
			System.out.println("SQL exception during insertPackage");
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
