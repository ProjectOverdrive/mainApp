/**
 * This code taken from: "http://www.codejava.net/java-ee/javamail/send-e-mail-in-plain-text-using-javamail"
 */

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Email {

    public void sendPlainTextEmail(String toAddress, String subject, String message)
            throws MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("projectoverdrive2017", "s!3Y3f2c");
            }
        };

        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress("projectoverdrive2017"));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // set plain text message
        msg.setText(message);

        // sends the e-mail
        Transport.send(msg);

    }

    /**
     * Test the send e-mail method
     */
    public static void main(String[] args) {
        // outgoing message information
        String mailTo = "ccross07@gmail.com";
        String subject = "Hello my friend";
        String message = "Hi guy, Hope you are doing well, Fellow Colten.";

        Email mailer = new Email();

        try {
            mailer.sendPlainTextEmail(mailTo, subject, message);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
    }
    
    //TODO: remove extra main method
}
