package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class admin {
	public static ArrayList<ArrayList<String>> ApproveProducts(){
		ArrayList<ArrayList<String>> approve = new ArrayList<ArrayList<String>>();
		Connection connection=null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("select * from ApproveProduct");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String checknew = rs.getString(4);
				if(checknew.equals("Y")){
					String name, features, sellerid, price, quantity, sellername=null, sellerusername=null, contact=null;
					ArrayList<String> prod = new ArrayList<String>();
					name = rs.getString(1);
					features = rs.getString(2);
					sellerid = rs.getString(3);
					price = rs.getString(4);
					quantity = rs.getString(6);
					String pendingid= rs.getString(7);
					pstmt = connection.prepareStatement("select name, username, contact from seller where username=?");
					pstmt.setString(1, sellerid);
					ResultSet rs1 = pstmt.executeQuery();
					if(rs1.next()){
						sellername = rs1.getString(1);
						sellerusername = rs1.getString(2);
						contact = rs1.getString(3);
					}
					
					prod.add(name);
					prod.add(features);
					prod.add(price);
					prod.add(quantity);
					prod.add(sellername);
					prod.add(sellerusername);
					prod.add(contact);
					String productid = rs.getString(8);
					prod.add(productid);
					prod.add(checknew);
					prod.add(pendingid);
					
					approve.add(prod);
					
				}
				else if(checknew.equals("N")){
					String productid=rs.getString(8);
					pstmt = connection.prepareStatement("select name, features from productdescription where productid=cast(? as integer)");
					pstmt.setString(1, productid);
					ResultSet rs2 = pstmt.executeQuery();
					String name=null, features=null, sellerid=null, price, quantity, sellername=null, sellerusername=null, contact=null;
					if(rs2.next()){
						name = rs2.getString(1);
						features = rs2.getString(2);
					}
					ArrayList<String> prod = new ArrayList<String>();
					sellerid = rs.getString(3);
					price = rs.getString(4);
					quantity = rs.getString(6);
					pstmt = connection.prepareStatement("select name, username, contact from seller where username=?");
					pstmt.setString(1, sellerid);
					ResultSet rs1 = pstmt.executeQuery();
					if(rs1.next()){
						sellername = rs1.getString(1);
						sellerusername = rs1.getString(2);
						contact = rs1.getString(3);
					}
					
					prod.add(name);
					prod.add(features);
					prod.add(price);
					prod.add(quantity);
					prod.add(sellername);
					prod.add(sellerusername);
					prod.add(contact);
					prod.add(productid);
					prod.add(checknew);
					
					approve.add(prod);
				}
			}
		} catch(SQLException sqle){
			System.out.println("SQL exception during add by admin");
		} finally{
			System.out.println("Came here1");
			closeConnection(connection);
		}
		return approve;
	}
	
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
	
	public static void deleteApprove(String pendingid){
		Connection connection=null;
		try{
			System.out.println("Came here1");
			connection=getConnection();
			PreparedStatement pstmt;
			
			pstmt = connection.prepareStatement("delete from ApproveProduct where pendingproductid=cast(? as numeric)");
			pstmt.setString(1, pendingid);
			pstmt.executeUpdate();
			
		} catch(SQLException sqle){
			System.out.println("SQL exception during deleteapprove");
			System.out.println(sqle);
		} finally{
			System.out.println("Came here1");
			closeConnection(connection);
		}
	}
	
	public static Integer getMaxProductId()
	{
		Integer num=0;
		Connection connection=null;
		try{
			connection = getConnection();
			PreparedStatement pstmt;
			pstmt = connection.prepareStatement("select max(productID) from productDescription");
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())num  = rs.getInt(1);
			
		}catch(SQLException sqle){
			System.out.println("SQL exception during getMaxProductId");
		} finally{
			closeConnection(connection);
		}
		return num;
		
	}
	
	public static void approve(String productid, String username, String checknew, String discount, String pendingid){
		Connection connection=null;
		System.out.println("Came here2");
		try{
			System.out.println("Came here2");
			System.out.println(checknew);
			connection=getConnection();
			PreparedStatement pstmt;
			
			if(checknew.equals("N")){
				pstmt = connection.prepareStatement("select * from ApproveProduct where sellerid=? and productid=cast(? as numeric)");
				pstmt.setString(1, username);
				pstmt.setString(2, productid);
				ResultSet rs = pstmt.executeQuery();
				String price = null;
				String quantity = null;
				System.out.println(productid);
				if(rs.next()){
					price = rs.getString(5);
					quantity = rs.getString(6);
				}
				
				pstmt = connection.prepareStatement("insert into product values(cast(? as integer),cast(? as money),cast(? as numeric),cast(? as numeric),?)");
				pstmt.setString(1, productid);
				pstmt.setString(2, price);
				pstmt.setString(3, quantity);
				pstmt.setString(4, discount);
				pstmt.setString(5, username);
				pstmt.executeUpdate();
				
				deleteApprove(pendingid);
				
			}
			else if(checknew.equals("Y")){
				
				pstmt = connection.prepareStatement("select name, features, price, quantity from ApproveProduct where pendingproductid=cast(? as integer)");
				pstmt.setString(1, pendingid);
				ResultSet rs = pstmt.executeQuery();
				String name=null, features=null, price=null, quantity=null;
				if(rs.next()){
					name=rs.getString(1);
					features=rs.getString(2);
					price=rs.getString(3);
					quantity=rs.getString(4);
				}
				PreparedStatement pstmt1, pstmt2;
				productid=Integer.toString(getMaxProductId()+1);
				pstmt1 = connection.prepareStatement("insert into productdescription values(cast(? as integer), ?, ?, null)");
				pstmt1.setString(1, productid);
				pstmt1.setString(2, name);
				pstmt1.setString(3, features);
				
				pstmt1.executeUpdate();
				
				pstmt2 = connection.prepareStatement("insert into product values(cast(? as integer),cast(? as money),cast(? as numeric),cast(? as numeric),?)");
				pstmt2.setString(1, productid);
				pstmt2.setString(2, price);
				pstmt2.setString(3, quantity);
				pstmt2.setString(4, discount);
				pstmt2.setString(5, username);
				pstmt2.executeUpdate();
				
				deleteApprove(pendingid);
			}
			
		} catch(SQLException sqle){
			System.out.println("SQL exception during approve");
			System.out.println(sqle);
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
