package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
	public static boolean userexists(String username, String option){
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt;
			if(option.equals("customer")){
				System.out.println("Came here2");
				pstmt=connection.prepareStatement("select * from usr "
						+ "where username=?");
				pstmt.setString(1, username);
				System.out.println("Came here2");
				ResultSet rs=pstmt.executeQuery();
				System.out.println("Came here2");
				if(rs.next()){
					System.out.println("Came here2");
					return true;
				}
				else {
					System.out.println("Came here3");
					return false;
				}
			}
			else if(option.equals("seller")){
				pstmt=connection.prepareStatement("select * from seller "
						+ "where username=?");
				pstmt.setString(1, username);
				ResultSet rs=pstmt.executeQuery();
				if(rs.next()){
					return true;
				}
				else return false;
			}
		} catch(SQLException sqle){
			System.out.println("SQL exception during checking");
		} finally{
			System.out.println("Came here1");
			closeConnection(connection);
		}
		return true;
	}
	public static void register(String name, String username, String email, String address,
			Integer contact, String password, String option){
		Connection connection=null;
		System.out.println("Came here1");
		System.out.println(name+username+email+address+contact+password);
		try{
			System.out.println("Came here1");
			connection=getConnection();
			PreparedStatement pstmt;
			System.out.println("Came here1"+option);
			if(option.equals("customer")){
				System.out.println("Came here");
				pstmt = connection.prepareStatement("insert into usr "
						+ "values (?, ?, ?, ?, ?, ?);");
				System.out.println("Came here");
				pstmt.setString(1, name);
				pstmt.setString(2, username);
				pstmt.setString(3, email);
				pstmt.setString(4, address);
				pstmt.setInt(5, contact);
				pstmt.setString(6, password);
				System.out.println("Came here");
				pstmt.executeUpdate();
				System.out.println("Came here");
			}
			else if(option.equals("seller")){
				pstmt = connection.prepareStatement("insert into seller "
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
			System.out.println("SQL exception during registration");
		} finally{
			System.out.println("Came here1");
			closeConnection(connection);
		}
		
	}
	
	public static boolean checklogin(String username, String password, String option){
		//return true is username and password combination is valid else false;
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt;
			if(option.equals("customer")){
				pstmt = connection.prepareStatement("select * from usr "
						+ "where username=? and passwd=?");
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				ResultSet rs=pstmt.executeQuery();
				if(rs.next()){
					return true;
				}
				else return false;
			}
			else if(option.equals("seller")){
				pstmt = connection.prepareStatement("select * from seller "
						+ "where username=? and passwd=?");
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				pstmt.executeQuery();
				ResultSet rs=pstmt.executeQuery();
				if(rs.next()){
					return true;
				}
				else return false;
			}
			else if(option.equals("transporter")){
				pstmt = connection.prepareStatement("select * from transporter "
						+ "where username=? and passwd=?");
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				pstmt.executeQuery();
				ResultSet rs=pstmt.executeQuery();
				if(rs.next()){
					return true;
				}
				else return false;
			}
			else if(option.equals("admin")){
				pstmt = connection.prepareStatement("select * from admin "
						+ "where username=? and passwd=?");
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				pstmt.executeQuery();
				ResultSet rs=pstmt.executeQuery();
				if(rs.next()){
					return true;
				}
				else return false;
			}
			
		} catch(SQLException sqle){
			System.out.println("SQL exception during registration");
		} finally{
			System.out.println("Came here1");
			closeConnection(connection);
		}
		return false;
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
