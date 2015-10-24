package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class product {
	public static List<String> products(Integer id){
		List<String> prods = new ArrayList<String>();
		Connection connection=null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("select * from productdescription "
					+ "where id>=? and id<=?");
			pstmt.setInt(1, id);
			pstmt.setInt(2, id+10);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				prods.add(rs.getString(3));
			}
		} catch(SQLException sqle){
			System.out.println("SQL exception during registration");
		} finally{
			System.out.println("Came here1");
			closeConnection(connection);
		}
		return prods;
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
