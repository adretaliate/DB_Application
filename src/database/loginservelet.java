package database;

import java.io.IOException;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.login;
/**
 * Servlet implementation class loginservelet
 */
@WebServlet("/loginservelet")
public class loginservelet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public loginservelet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	public boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getParameter("action");
		if(action.equals("register")){
			String name=request.getParameter("name");
			String username=request.getParameter("username");
			String email=request.getParameter("email");
			String address=request.getParameter("address");
			String contact=request.getParameter("contact");
			String password=request.getParameter("password");
			String repassword=request.getParameter("repassword");
			String option=request.getParameter("option");
			PrintWriter out = response.getWriter();
			boolean userexists=true;
			System.out.println(action);
			if(username!=null && !username.isEmpty() && option!=null && name!=null && address!=null && password!=null && !name.isEmpty() && !address.isEmpty() && !password.isEmpty()){
				userexists=login.userexists(username, option);
				if(!userexists){
					if(contact.matches("[0-9]+") && contact.length()<=11 && contact.length()>=10){
						//Integer con=Integer.parseInt(contact);
						
						if(password.equals(repassword)){
							login.register(name, username, email, address, contact, password, option);
							RequestDispatcher rd = getServletContext().getRequestDispatcher("/loginpage.jsp");
				            out = response.getWriter();
				            out.println("<html><center><font color=red>New User successfully registered</font></center></html>\n");
				            rd.include(request, response);
						}
						else{
							RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
				            out = response.getWriter();
				            out.println("<html><center><font color=red>Passwords do not match</font></center></html>\n");
				            rd.include(request, response);
						}
					}
					else{
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
			            out = response.getWriter();
			            out.println("<html><center><font color=red>Fill in proper details</font></center></html>\n");
			            rd.include(request, response);
					}
				}
				else{
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
		            out = response.getWriter();
		            out.println("<html><center><font color=red>UserName already in use</font></center></html>\n");
		            rd.include(request, response);
				}
			}
			else{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
	            out = response.getWriter();
	            out.println("<html><center><font color=red>Complete the form</font></center></html>\n");
	            rd.include(request, response);
			}
		}
		else if(action.equals("loginpage")){
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String option=request.getParameter("option");
			PrintWriter out = response.getWriter();
			System.out.println("came to loginservelet");
			if(username!=null && !username.isEmpty() && password!=null 
					&& !password.isEmpty() && option!=null && !option.isEmpty()){
				boolean checklogin = login.checklogin(username, password, option);
				System.out.println(checklogin);
				if(checklogin){
					Cookie login = new Cookie("username", username);
		            // setting cookie to expiry in 60 mins
		            login.setMaxAge(60 * 60);
		            response.addCookie(login);
		            Cookie id = new Cookie("id", "1");
		            // setting cookie to expiry in 60 mins
		            id.setMaxAge(60 * 60);
		            response.addCookie(id);
		            
		            Cookie type = new Cookie("type", option);
		            type.setMaxAge(60 * 60);
		            response.addCookie(type);
		            
		            if(option.equals("customer")) response.sendRedirect("loginsuccess.jsp");
		            else if(option.equals("seller")) response.sendRedirect("seller.jsp");
		            else if(option.equals("transporter")) response.sendRedirect("transporter.jsp");
		            else if(option.equals("admin")) response.sendRedirect("admin.jsp");
					out.println("<html><center><font color=red>logged in</font></center></html>\n");
				}
				else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/loginpage.jsp");
		            out = response.getWriter();
		            out.println("<html><center><font color=red>Wrong username/password combination</font></center></html>\n");
		            rd.include(request, response);
				}
			}
			else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/loginpage.jsp");
	            out = response.getWriter();
	            out.println("<html><center><font color=red>Fill in the details to LogIn</font></center></html>\n");
	            rd.include(request, response);
			}
		}
	}
}
