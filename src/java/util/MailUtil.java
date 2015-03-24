/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author vishnu-pt517
 */
public class MailUtil {

    /*This method is no longer supported*/
    public static boolean SendMailOld(String to, String messageS, String subject) {

        String from = "";
        String host = "smtp";

        // Get system properties
        Properties properties = System.getProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", 465);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(messageS);

            // Send message
            //Transport.send(message);
            try {
                Transport transport = session.getTransport("smtps");
                transport.connect(host, 25, from, "");
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (Exception e) {
                System.out.println("Error in inner try: " + e.getMessage());
            }
            System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }

    public void SendMail(String to, String messageS, String subject) {
        sendMailViaThread mail = new sendMailViaThread();
        mail.messageS = messageS;
        mail.subject = subject;
        mail.to = to;
        mail.start();

        /*Properties props = new Properties();
         props.put("mail.smtp.host", "smtp.zoho.com");
         props.put("mail.smtp.socketFactory.port", "465");
         props.put("mail.smtp.socketFactory.class",
         "javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.port", "465");


         Session session = Session.getDefaultInstance(props,
         new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
         return new PasswordAuthentication(user, pass);
         }
         });

         try {

         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(user));
         message.setRecipients(Message.RecipientType.TO,
         InternetAddress.parse(to));
         message.setSubject(subject);
         message.setText(messageS);

         Transport.send(message);

         System.out.println("Done");

         } catch (MessagingException e) {
         System.out.println(e.getMessage());
         }*/
    }

    class sendMailViaThread extends Thread {

        String to;
        String messageS;
        String subject;

        @Override
        public void run() {
            SendMail();
        }

        public void SendMail() {
            Properties props = new Properties();
            props.put("mail.smtp.host", MAIL.getHost().trim());
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", MAIL.getPort());

            String user = MAIL.getUsername().trim();
            String pass = MAIL.getPassword().trim();

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user, pass);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(messageS);

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getNextException());
            }
        }

    }

}
