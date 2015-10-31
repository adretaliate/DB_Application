package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.NumberFormat;
import java.util.Locale;

public class seller {

	public static Double moneyToDouble(String money){
		String[] arr = money.split(" ");
		String number = arr[1];
		String d = number.replaceAll(",", "");
		return Double.parseDouble(d);
	}
	
	public static ArrayList<ArrayList<String>> getProducts(String sellerid)
	{
		Connection connection =null;
		ArrayList<ArrayList<String>> products = new ArrayList<ArrayList<String>>();
		try{
			connection = getConnection();
			PreparedStatement pstmt,pstmt1;
			pstmt = connection.prepareStatement("select productid, name, price, quantity from product natural join productdescription where sellerid=? ");
			pstmt.setString(1, sellerid);
			ResultSet rs = pstmt.executeQuery();	
			while(rs.next())
			{
				ArrayList<String> product = new ArrayList<String>();
				product.add(rs.getString(1));
				product.add(rs.getString(2));
				product.add(rs.getString(3));
				product.add(rs.getString(4));
				products.add(product);
			}
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during checkReview");
			System.out.println(sqle);
		} finally{
			closeConnection(connection);
		}
		
		return products;
	}
	
	public static void editProduct(String sellerid, Integer productId, Double price, Integer quantity)
	{
		Connection connection =null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
//			NumberFormat Rupee = NumberFormat.getCurrencyInstance(Locale.);
			pstmt = connection.prepareStatement("update product set price="+price.toString()+", quantity=? where sellerid=? and productid=?");
//			pstmt.setString(1, price.toString());
			pstmt.setInt(1, quantity);
			pstmt.setString(2, sellerid);
			pstmt.setInt(3, productId);
			pstmt.executeUpdate();
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during editProduct");
			System.out.println(sqle);
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
