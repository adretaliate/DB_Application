package database;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.seller;
/**
 * Servlet implementation class sellerServlet
 */
@WebServlet("/sellerServlet")
public class sellerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sellerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action.equals("logout")){
			Cookie[] cookies = request.getCookies();
			if(cookies!=null){
				for(Cookie cookie:cookies){
					if(cookie.getName().equals("username")){
						cookie.setMaxAge(0);
						cookie.setValue(null);
						response.addCookie(cookie);
					}
					if(cookie.getName().equals("id")){
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
			PrintWriter out=response.getWriter();
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/loginpage.jsp");
	        out = response.getWriter();
	        out.println("<html><center><font color=red>Logged Out</font></center></html>\n");
	        rd.include(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		String username = null;
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies){
			if(cookie.getName().equals("username")){
				username=cookie.getValue();
			}
		}
		
		System.out.println(action);
		if(action.equals("editStock"))
		{
			System.out.println("dfkdjf");
			String edit = request.getParameter("edit");
			//System.out.println(edit);
			if(edit!=null)
			{
				
				String productId = edit;
				System.out.println("price="+edit);
				System.out.println("quantity="+edit);
				String changePrice = request.getParameter("price="+edit);
				String changeQuantity = request.getParameter("quantity="+edit);
				System.out.println("before edit: "+changePrice+" "+changeQuantity);
				seller.editProduct(username,Integer.parseInt(productId),Double.parseDouble(changePrice),Integer.parseInt(changeQuantity));
				System.out.println("after edit");
				response.sendRedirect("seller.jsp");
			}
		}
	}

}
