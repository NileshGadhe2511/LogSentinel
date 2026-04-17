package com.nilesh.logsentinel.alert;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.Authenticator; // Ensure this is here
import jakarta.mail.PasswordAuthentication; // And this
import java.util.Properties;



public class EmailAlerter implements AlertManager {
    private final String username = "gadhenilesh210@gmail.com"; // Your Gmail
    private final String password = "uarrczpxiggckogd";   // NOT your login password
    private final String recipient = "nileshgadhe999@gmail.com";

    @Override
    public void sendAlert(String keyword, String context) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS encryption

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("🚨 LogSentinel: " + keyword + " Detected!");
            
            String emailContent = "Critical log entry found:\n\n" + 
                                  "Keyword: " + keyword + "\n" +
                                  "Log Content:\n" + context;
            
            message.setText(emailContent);

            Transport.send(message);
            System.out.println("✅ Email alert sent successfully!");

        } catch (MessagingException e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
        }
    }
}