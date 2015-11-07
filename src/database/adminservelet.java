package database;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class adminservelet
 */
@WebServlet("/adminservelet")
public class adminservelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminservelet() {
        super();
        // TODO Auto-generated constructor stub
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getParameter("action");
		if(action.equals("add")){
			System.out.println("came to admin");
			response.sendRedirect("addbyadmin.jsp");
		}
		else if(action.equals("addbyadmin")){
			String name=request.getParameter("name");
			String username=request.getParameter("username");
			String email=request.getParameter("email");
			String address=request.getParameter("address");
			String contact=request.getParameter("contact");
			String password=request.getParameter("password");
			String repassword=request.getParameter("repassword");
			String option=request.getParameter("option");
			String designation=request.getParameter("designation");
			PrintWriter out = response.getWriter();
			boolean userexists=true;
			System.out.println(action);
			if(username!=null && !username.isEmpty() && option!=null && name!=null && address!=null && password!=null && !name.isEmpty() && !address.isEmpty() && !password.isEmpty()){
				userexists=login.userexists(username, option);
				if(!userexists){
					if(isInteger(contact)){
						Integer con=Integer.parseInt(contact);
						if(password.equals(repassword)){
							admin.register(name, username, email, address, con, password, option, designation);
							RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");
				            out = response.getWriter();
				            out.println("<html><center><font color=red>New User successfully registered</font></center></html>\n");
				            rd.include(request, response);
						}
						else{
							RequestDispatcher rd = getServletContext().getRequestDispatcher("/addbyadmin.jsp");
				            out = response.getWriter();
				            out.println("<html><center><font color=red>Passwords do not match</font></center></html>\n");
				            rd.include(request, response);
						}
					}
					else{
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/addbyadmin.jsp");
			            out = response.getWriter();
			            out.println("<html><center><font color=red>Fill in proper details</font></center></html>\n");
			            rd.include(request, response);
					}
				}
				else{
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/addbyadmin.jsp");
		            out = response.getWriter();
		            out.println("<html><center><font color=red>UserName already in use</font></center></html>\n");
		            rd.include(request, response);
				}
			}
			else{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/addbyadmin.jsp");
	            out = response.getWriter();
	            out.println("<html><center><font color=red>Complete the form</font></center></html>\n");
	            rd.include(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action.equals("approve_or_reject")){
			String reject = request.getParameter("reject");
			String approve = request.getParameter("approve");
			if(reject!=null){
				System.out.println(reject);
				admin.deleteApprove(reject);
				response.sendRedirect("admin.jsp");
			}
			if(approve!=null){
				System.out.println("came here 3");
				String[] split = approve.split(" ");
				System.out.println(split[0] + split[1] + split[2]);
				String discount = request.getParameter(approve);
				admin.approve(split[0], split[1], split[2], discount, split[3]);
				response.sendRedirect("admin.jsp");
			}
		}
	}

}
