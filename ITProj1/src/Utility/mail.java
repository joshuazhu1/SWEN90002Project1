package Utility;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.internet.*;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;


public class mail {
	
	
	
	/**
	 * create and send mail
	 * @param email
	 * @param token
	 * @param subject
	 * @param content
	 */
	public static void sendMail(String email, String token, String subject, String content){
		final String username = "yanghongnan7@gmail.com";
		final String password = "yhn12345";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.ssl.trust", "smtp.unimelb.edu.au");
		//props.put("mail.smtp.ssl.trust", "smtp.google.com");
		props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.smtp.host", "smtp.unimelb.edu.au");
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Proj1@Group40.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(content);
			Transport.send(message);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Send confirmation mail
	 * @param email
	 * @param token
	 */
	public static void sendConMail(String email, String token){
		String subject = "Register confirmation mail";
		String content = "Hi, you has registered your email address. \n" +
		"Click  the following link to confirm registration:\n"
		+"http://localhost:8080/InternetProj1/servlet/Confirmation?token="+token+"&email="+email;
		sendMail(email,token,subject,content);
		
	}

	/**
	 * Send passwd mail
	 * @param email
	 * @param passwd
	 */
	public static void sendPasswdMail(String email, String passwd){
		String subject = "Passwd mail";
		String content = "The password for your proj1 account is:\n"+passwd +
		"\nKeep it secret, keep it safe.";
		sendMail(email,passwd,subject,content);	
	}
	
	/**
	 * check whether the email is valid
	 * @param email
	 * @return
	 */
	public static boolean validEmail(String email) {
		String regEmail = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		Pattern pat = Pattern.compile(regEmail);
		Matcher mat = pat.matcher(email);
		return mat.find();
		//return true;
	}
}
