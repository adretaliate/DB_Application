package database;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class transporterservelet
 */
@WebServlet("/transporterservelet")
public class transporterservelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public transporterservelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getParameter("action");
		if(action.equals("editlocation")){
			System.out.println(action);
			String deliver = request.getParameter("deliver");
			String changelocation = request.getParameter("changecurrentlocation");
			System.out.println(changelocation);
			Cookie[] cookies = request.getCookies();
			String username=null;
			if(cookies!=null){
				for(Cookie cookie:cookies){
					if(cookie.getName().equals("username")){
						username=cookie.getValue();
					}
				}
			}
			if(deliver!=null){
				String packageid = deliver;
				transporter.delivered(packageid);
			}
			if(changelocation!=null){
				String packageid = changelocation;
				String newlocation = request.getParameter(changelocation);
				System.out.println(newlocation);
				transporter.newlocation(packageid, newlocation);
			}
			response.sendRedirect("transporter.jsp");
		}
	}

}
