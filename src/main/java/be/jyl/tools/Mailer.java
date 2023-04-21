package be.jyl.tools;

import be.jyl.entities.ArticlesRentals;
import be.jyl.entities.Rentals;
import be.jyl.managedBeans.ArticleBean;
import jdk.nashorn.internal.objects.NativeMath;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.omg.CORBA.WStringSeqHelper;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailer {
    private static Logger log = Logger.getLogger(Mailer.class);
    private static String text;

    public static void sendMailReminder(Rentals rentals, String path) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        String mailAccount = "rentschoolsystem@gmail.com";
        String password = "ckabuqdkilalkfbq";
        String recipient = rentals.getBorrower().getEmail();


        log.log(Level.INFO, "Setting mail session.");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailAccount,password);
            }
        });
        log.log(Level.INFO, "Setting mail content.");
        String subject = "RAPPEL: Retard Location";
        text = "Bonjour "+rentals.getBorrower().getFirstname()+",\r\n" +
                "\r\n" +
                "Nous tenons à vous rappeler que vous n'avez toujours pas rendu le matériel que vous avez loué:\r\n";

        // Générer la liste des articles loués
        for(ArticlesRentals articles: rentals.getRentalsArticlesByIdRental()){
            text += "- " + articles.getArticlesByIdArticle().getArticleName() +
                    " (" + articles.getArticlesByIdArticle().getCategoryByIdCategory().getCategoryName() + ") RefSN: " +
                    articles.getArticlesByIdArticle().getRefSn() +
                    "\r\n";
        }

        text += "\r\nS'il vous plait veuillez retourner ce matériel le plus rapidement possible afin d'éviter une facturation de celui-ci.\r\n";
        log.log(Level.INFO, "Prepare mail to : " + recipient);
        Message message = prepareMessage(session, mailAccount, recipient, path, subject, text, false);
        log.log(Level.INFO, "Send Mail.");
        Transport.send(message);
    }

    public static void sendCustomReminder(Rentals rentals, String path, String textContent) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        String mailAccount = "rentschoolsystem@gmail.com";
        String password = "ugdqisktljnvuwgw";
        String recipient = rentals.getBorrower().getEmail();


        log.log(Level.INFO, "Setting mail session.");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailAccount,password);
            }
        });
        log.log(Level.INFO, "Setting mail content.");
        String subject = "RAPPEL: Retard Location";
        text = textContent;
        log.log(Level.INFO, "Prepare mail to : " + recipient);
        Message message = prepareMessage(session, mailAccount, recipient, path, subject, text, false);
        log.log(Level.INFO, "Send Mail.");
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String mailAccount, String recipient, String path, String subject, String text, boolean pdf) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);

            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();

            textBodyPart.setText(text);
            if (pdf) {
                MimeBodyPart pdfAttachment = new MimeBodyPart();
                pdfAttachment.attachFile(path);
                emailContent.addBodyPart(pdfAttachment);
            }
            emailContent.addBodyPart(textBodyPart);

            message.setContent(emailContent);
            return message;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public String getMessageContent() {
        return text;
    }
}