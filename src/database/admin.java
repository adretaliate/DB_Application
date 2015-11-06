package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class admin {
	public static void register(String name, String username, String email, String address,
			Integer contact, String password, String option, String designation){
		Connection connection=null;
		System.out.println("Came here1");
		System.out.println(name+username+email+address+contact+password);
		try{
			System.out.println("Came here1");
			connection=getConnection();
			PreparedStatement pstmt;
			System.out.println("Came here1"+option);
			if(option.equals("admin")){
				System.out.println("Came here");
				pstmt = connection.prepareStatement("insert into admin "
						+ "values (?, ?, ?, ?, ?, ?, ?);");
				System.out.println("Came here");
				pstmt.setString(1, name);
				pstmt.setString(2, username);
				pstmt.setString(3, email);
				pstmt.setString(4, address);
				pstmt.setInt(5, contact);
				pstmt.setString(6, password);
				pstmt.setString(7, designation);
				System.out.println("Came here");
				pstmt.executeUpdate();
				System.out.println("Came here");
			}
			else if(option.equals("transporter")){
				pstmt = connection.prepareStatement("insert into transporter "
						+ "values (?, ?, ?, ?, ?, ?);");
				pstmt.setString(1, name);
				pstmt.setString(2, username);
				pstmt.setString(3, email);
				pstmt.setString(4, address);
				pstmt.setInt(5, contact);;
				pstmt.setString(6, password);
				pstmt.executeQuery();
			}
			
		} catch(SQLException sqle){
			System.out.println("SQL exception during add by admin");
		} finally{
			System.out.println("Came here1");
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
