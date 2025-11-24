

import java.util.Optional;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
public class EmailSender {

    private final String host;

    private  Optional<Session> createSession(String email,String pw) throws  NullPointerException{
        int port = 587;
        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
                prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);
        Optional<Session> session;
        Session sess =   Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, pw);
            }
        });
        session = Optional.ofNullable(sess);
        if(session.isEmpty()) throw new NullPointerException("failed to create session");
       return session;
    }     


    public EmailSender(String host){
        this.host = host;
    }

    public boolean sendEmail(String from, String to,String subject,String body){
    
        try{    
          Session sess = createSession(from,"blank").get();
          MimeMessage mess = new MimeMessage(sess);
          try{
             mess.setFrom(new InternetAddress(from));
             mess.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
             mess.setSubject(subject);
             mess.setText(body);
             Transport.send(mess);
             return true;
          }
          catch(MessagingException e){
            System.out.println(e.getMessage());
            return false;
          }
       }
       catch(NullPointerException e){

           System.err.println(e.getMessage());
           return false;
       }
    }
   
}
