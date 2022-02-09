
import java.util.Date;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {

	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	
	public static void sendEmail(String toEmail, String subject, String body){
		try
	    {
		  	
		  final String fromEmail = "easybankingreminder@gmail.com"; //requires valid gmail id
		  final String password = "Descarcare2."; // correct password for gmail id
		 
		  Authenticator auth = new Authenticator() {
				//override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
		  };
		  
		  Properties props = new Properties();
		  props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		  props.put("mail.smtp.port", "587"); //TLS Port
		  props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		  props.put("mail.smtp.auth", "true"); //enable authentication
		  props.put("mail.smtp.user", fromEmail);
		  props.put("mail.smtp.proxy.user", fromEmail);
		  props.put("mail.smtp.proxy.password", password);
		  Session session = Session.getInstance(props, auth);
		  session.setDebug(true);
		  try{
			MimeMessage msg = new MimeMessage(session);
			
			//set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(fromEmail, "EasyBanking"));

			msg.setReplyTo(InternetAddress.parse(fromEmail, false));

			msg.setSubject(subject, "UTF-8");

			msg.setText(body, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(msg);  

			System.out.println("EMail Sent Successfully!!");
			}catch(MessagingException e){
				e.printStackTrace();
			}
		
		}
		catch (Exception e) {
		e.printStackTrace();
		}
	}
}