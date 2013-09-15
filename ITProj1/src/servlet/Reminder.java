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

import com.google.gson.JsonObject;

import DB.CouchDB;
import Utility.Base64;
import Utility.mail;

public class Reminder extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Reminder() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * response.setContentType("text/html"); PrintWriter out =
		 * response.getWriter(); out.println(
		 * "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		 * out.println("<HTML>");
		 * out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		 * out.println("  <BODY>"); out.print("    This is ");
		 * out.print(this.getClass()); out.println(", using the GET method");
		 * out.println("  </BODY>"); out.println("</HTML>"); out.flush();
		 * out.close();
		 */
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		HttpSession session = request.getSession();
		
		if (session.getAttribute("send") != null) {
			out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1/reminder.html\">");
			out.println("<p>");
			out.println("You can only request a reminder mail for every 5 minutes");
			out.println("</p>");
		} else {
			// email is valid
			session.setAttribute("send", "send");
			if (mail.validEmail(email)) {
				CouchDB db = new CouchDB();
				// the email exists
				if (db.checkUserExist(email)) {
					JsonObject user = db.getUser(email);
					mail.sendPasswdMail(email, Base64.DecoderBase64(user.get("passwd").toString()));
					out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1/reminder.html\">");
					out.println("<p>");
					out.println("A email of your passwd has been sent to you, please check it");
					out.println("</p>");
				}
				// not exists
				else {
					out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1/reminder.html\">");
					out.println("<p>");
					out.println("The email address is not exists, please try another one");
					out.println("</p>");
				}
				// email is invalid
			} else {
				out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1/reminder.html\">");
				out.println("<p>");
				out.println("The email address is invalid, please register again");
				out.println("</p>");

			}
			out.flush();
			out.close();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
