package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DB.CouchDB;

public class Reset extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Reset() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String email;
		String token;
		HttpSession session = request.getSession(true);
		if(session.getAttribute("email")==null||session.getAttribute("token")==null){
			out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1\">");
			out.println("<p>");
			out.println("please log in first... ");
			out.println("</p>");
		}
		else{
			email = session.getAttribute("email").toString();
			token = session.getAttribute("token").toString();
			String originalPasswd = request.getParameter("oPasswd");
			String newPasswd = request.getParameter("nPasswd");
			
			CouchDB db = new CouchDB();
			if(db.checkUserPasswd(email, originalPasswd)){
				db.deleteUser(email);
				db.createUser(email, newPasswd);
			
				session = request.getSession(true);
				//if (session.getAttribute("email") == null) {
					session.setAttribute( "email", email);
					session.setAttribute( "token", token);
					//}
					out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1/homepage.html\">");
					out.println("<p>");
					out.println("you have changed your pass word successfully, this page will be redirected to home page, please wait...");
					out.println("</p>");
			}
			else{
				session = request.getSession(true);
				//if (session.getAttribute("email") == null) {
					session.setAttribute( "email", email);
					session.setAttribute( "token", token);
					//}
					out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1/reset.html\">");
					out.println("<p>");
					out.println("please input correct old pass word first!");
					out.println("</p>");
				
			}
			
		}
		
		
		
		/*out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");*/
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
