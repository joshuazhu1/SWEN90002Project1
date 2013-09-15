package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Utility.mail;

import DB.CouchDB;

/*
 * The Register Servlet, /Register, takes a single parameter, email, which is the email address of the user
 wanting to register.
 The servlet should respond appropriately to the following conditions:
 the email address is invalid, meaning that it is not a proper email address or that the application does
 not allow email addresses of that kind
 ，
 the email address is already registered
 ： the user should ask for a Reminder Email if the password has been forgotten
 ，
 the email address has already been sent a Confirmation Email, but the confirmation has not yet been
 clicked
 if the registration request is at least 5 minutes after the previous registration request then the
 confirmation email will be sent again
 ：
 ，
 sending of the Confirmation Email resulted in an error from the email system and the user should try
 to register again
 */
public class Register extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5141118340311165188L;

	/**
	 * Constructor of the object.
	 */
	public Register() {
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
		HttpSession session = request.getSession(true);
		// check the email
		if (mail.validEmail(email)) {
			// none cookie, create a new cookie
			CouchDB db = new CouchDB();
			// check the if the email already exists
			if (db.checkUserExist(email)) {
				out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1\">");
				out.println("<p>");
				out.println("The email address is already exists, please try another one");
				out.println("</p>");
			}
			// email not exists
			else {
				if (session.getAttribute("email") == null) {
					addNewSession(email, Utility.Base64.EncoderBase64(email),session);	
					String token = Utility.Base64.EncoderBase64(email);
					addNewSession(email, token,session);
					mail.sendConMail(email, token);
					out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1\">");
					out.println("<p>");
					out.println("A confirmation mail has already been sent to you, please check it");
					out.println("</p>");	
				}
				// find if the session exists
				else {
					out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1\">");
					out.println("<p>");
					out.println("This email was just used to registration but have not yet confirmed\n");
					out.println("Please check your email and confirm the email!");
					out.println("</p>");
				}
			}
		} else {
			out.println("<meta http-equiv=\"Refresh\" content=\"5;url=http://localhost:8080/InternetProj1\">");
			out.println("<p>");
			out.println("The email address is invalid, please register again");
			out.println("</p>");
			
		}
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
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {

	}

}
