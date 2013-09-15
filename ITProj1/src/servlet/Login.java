package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DB.CouchDB;
import Utility.Base64;
import Utility.mail;

public class Login extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Login() {
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
		out.print("   This is ");
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
		
		HttpSession session = request.getSession(true);
		/*if(session.getAttribute("email")==null||session.getAttribute("token")==null){
			out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1\">");
			out.println("<p>");
			out.println("please log in first... ");
			out.println("</p>");
		}
		
		
		else{*/
		// get log in information
		String email = request.getParameter("email");
		String passwd = request.getParameter("password");
		String token = Utility.Base64.EncoderBase64(email);
		// check the email
		if (Utility.mail.validEmail(email)) {
			// none cookie, create a new cookie
			CouchDB db = new CouchDB();
			// check the if the input is right
			if(!db.checkUserExist(email)){
				out.println("<meta http-equiv=\"Refresh\" content=\"10;url=http://localhost:8080/InternetProj1\">");
				out.println("<p>");
				out.println("this email has not been registed either!");
				out.println("</p>");
			}
			else{
				if (!db.checkUserPasswd(email, passwd)) {
					out.println("<meta http-equiv=\"Refresh\" content=\"1000;url=http://localhost:8080/InternetProj1\">");
					out.println("<p>");
					out.println("email: 	"+email+"  \npasswdIn: 	"+ passwd);
					if(db.checkUserExist(email)){
						out.println("email in DB: 	"+ db.getUser(email).get("_id")+"  \npassword in DB: 	"+ db.getUser(email).get("passwd")
								+"\ndecoded passwd in DB: 	"+String.valueOf(Base64.DecoderBase64(String.valueOf(db.getUser(email).get("passwd"))))
								+"\ndobbleedcoded passwd: 	"+Base64.EncoderBase64(passwd)
								);
					}
					out.println(" either user name or password is wrong, please check");
					out.println("</p>");
					}
			// 
				else {
					session = request.getSession(true);
					//if (session.getAttribute("email") == null) {
					session.setAttribute( "email", email);
					session.setAttribute( "token", token);
					//}
					out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1/homepage.html\">");
					out.println("<p>");
					out.println("welcome, this page will be redirected to home page, please wait...");
					out.println("</p>");	
				}
			} 
		}
			/*else {
				session.setAttribute( "email", email);
				session.setAttribute( "token", token);
				out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1/homepage.html\">");
				out.println("<p>");
				out.println("welcome, this page will be redirected to home page, please wait...");
				out.println("</p>");
			}*/
		else{
			out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1\">");
			out.println("<p>");
			out.println("invalid email address, please check");
			out.println("</p>");
		}
		//}
		
		/*response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
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
	 * add new cookie for register user
	 * @param email
	 * @param response
	 */
	public void addNewSession(String email, String token, HttpSession session){
		session.setAttribute( "email", email );
		session.setAttribute("token", token);
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
