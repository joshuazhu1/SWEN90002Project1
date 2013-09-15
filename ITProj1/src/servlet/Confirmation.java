package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Utility.mail;

import DB.CouchDB;

public class Confirmation extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6357922239051642353L;

	/**
	 * Constructor of the object.
	 */
	public Confirmation() {
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
		System.out.println();
		String email = request.getParameter("email");
		String token = request.getParameter("token");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		if(session.isNew()){
			out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1\">");
			out.println("<p>");
			out.println("Confirmation is overtime, you will be redirect to register page");
			out.println("</p>");
		}
		else{
			out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1\">");
			out.println("<p>");
			out.println("token in session: " +String.valueOf(session.getAttribute("token"))+ "email in session: "+String.valueOf(session.getAttribute("email")));
			out.println("token: " +token+ "email: "+email);
			out.println("</p>");
			if (session.getAttribute("token").equals(token) &&
				session.getAttribute("email").equals(email)) {
					CouchDB db = new CouchDB();
					String orignalPasswd = (String) session.getAttribute("token");
					//String encodedPasswd = Utility.MD5Tools.EncoderByMd5(orignalPasswd);
					db.createUser(email, orignalPasswd);//store the encoded password in DB
					mail.sendPasswdMail(email, orignalPasswd);//provide user with original password
					out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1\">");
					out.println("<p>");
					out.println("Confirmantion successful, a password email has been sent to you, please check it. You will be redirect to register page");
					out.println("</p>");
			}
			else {
				System.out.println("asdasd");
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
