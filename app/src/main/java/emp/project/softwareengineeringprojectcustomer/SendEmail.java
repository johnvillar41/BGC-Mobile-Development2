package emp.project.softwareengineeringprojectcustomer;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.LoginService;

public class SendEmail {
    private String userEmail;
    private String code;
    public SendEmail(String userEmail,String code) {
        this.userEmail = userEmail;
        this.code = code;
    }



    public void sendMailCode() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");

        String accountEmailJWCA = "jwca.mcl2020@gmail.com";
        String password = "passwordmcl";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(accountEmailJWCA, password);
            }
        });

        Message message = prepareMessage(session, accountEmailJWCA, userEmail);
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Message prepareMessage(Session session, String accountEmailJWCA, String recicpient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(accountEmailJWCA));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recicpient));
            message.setSubject("Code for AGT Confirmation Account");
            message.setText(code);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
