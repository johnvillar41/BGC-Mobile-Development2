package emp.project.softwareengineeringprojectcustomer;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
    private String userEmail;
    private String code;

    public SendEmail(String userEmail, String code) {
        this.userEmail = userEmail;
        this.code = code;
    }

    private static final String MAIN_SMPTP_AUTHENTICATION = "mail.smtp.auth";
    private static final String MAIN_SMPTP_HOST = "mail.smtp.host";
    private static final String MAIN_SMPTP_STARTTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIN_SMPTP_PORT = "mail.smtp.port";

    private static final String TRUE = "true";
    private static final String GMAIL_SMTP = "smtp.gmail.com";
    private static final String PORT_CODE = "587";

    private static final String ACCOUNT_EMAIL_JWCA = "jwca.mcl2020@gmail.com";
    private static final String ACCOUNT_JWCA_PASSWORD = "passwordmcl";

    public void sendMailCode() {
        Properties properties = new Properties();
        properties.put(MAIN_SMPTP_AUTHENTICATION, TRUE);
        properties.put(MAIN_SMPTP_HOST, GMAIL_SMTP);
        properties.put(MAIN_SMPTP_STARTTTLS_ENABLE, TRUE);
        properties.put(MAIN_SMPTP_PORT, PORT_CODE);


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ACCOUNT_EMAIL_JWCA, ACCOUNT_JWCA_PASSWORD);
            }
        });

        Message message = prepareMessage(session, userEmail);
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static final String SUBJECT = "Code for AGT Confirmation Account";

    private Message prepareMessage(Session session, String recicpient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SendEmail.ACCOUNT_EMAIL_JWCA));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recicpient));
            message.setSubject(SUBJECT);
            message.setText(code);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
