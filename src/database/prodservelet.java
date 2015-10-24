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

/**
 * Servlet implementation class prodservelet
 */
@WebServlet("/prodservelet")
public class prodservelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public prodservelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("came to doGet");
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
		else if(action.equals("prev")){
			Cookie[] cookies = request.getCookies();
			System.out.println(action);
			if(cookies!=null){
				for(Cookie cookie:cookies){
					if(cookie.getName().equals("id")){
						Integer id = Integer.parseInt(cookie.getValue());
						if(id>10){
							System.out.println(action);
							String newid = String.valueOf(id-10);
							cookie.setValue(newid);
							response.addCookie(cookie);
						}
					}
				}
			}
			RequestDispatcher requestDispatcher = request
                    .getRequestDispatcher("/loginsuccess.jsp");
            requestDispatcher.forward(request, response);
		}
		else if(action.equals("next")){
			Cookie[] cookies = request.getCookies();
			if(cookies!=null){
				for(Cookie cookie:cookies){
					if(cookie.getName().equals("id")){
						Integer id = Integer.parseInt(cookie.getValue());
						String newid = String.valueOf(id+10);
						Integer check = product.getMaxProductId();
						if(check > id +10){
							cookie.setValue(newid);
							response.addCookie(cookie);
						}
					}
				}
			}
			RequestDispatcher requestDispatcher = request
                    .getRequestDispatcher("/loginsuccess.jsp");
            requestDispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action==null){
			String productID = request.getParameter("productID");
			String sellerID = request.getParameter("addtocart");
			Cookie[] cookies = request.getCookies();
			System.out.println("prodservelet");
			System.out.println(productID);
			System.out.println(sellerID);
			String username=null;
			for(Cookie cookie:cookies){
				if(cookie.getName().equals("username")){
					username=cookie.getValue();
				}
			}
			System.out.println(username);
			product.addToCart(username, productID, sellerID);
			System.out.println(username);
			PrintWriter out=response.getWriter();
			response.sendRedirect("loginsuccess.jsp");  
	//		RequestDispatcher rd = getServletContext().getRequestDispatcher("/loginsuccess.jsp");
	//        out = response.getWriter();
	//        out.println("<html><center><font color=red>Item added to Cart</font></center></html>\n");
	//        rd.include(request, response);
		}
		else if(action.equals("edit_or_delete")){
			System.out.println("link error");
			String edit=request.getParameter("edit");
			String delete=request.getParameter("delete");
			Cookie[] cookies = request.getCookies();
			String username=null;
			if(cookies!=null){
				for(Cookie cookie:cookies){
					if(cookie.getName().equals("username")){
						username=cookie.getValue();
					}
				}
			}
			if(delete!=null){
				String[] split = delete.split(" ");
				product.delete(username, split[1], split[0]);
				response.sendRedirect("cart.jsp");
			}
			else if(edit!=null){
				String[] split = edit.split(" ");
				String quantity = request.getParameter(edit);
				if(Integer.parseInt(quantity)!=0){
					product.updateCart(username, split[1], split[0], Integer.parseInt(quantity));
				}
				else{
					product.delete(username, split[1], split[0]);
				}
				response.sendRedirect("cart.jsp");
			}
		}
	}

}
