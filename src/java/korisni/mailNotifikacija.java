
package korisni;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ami
 */
public class mailNotifikacija {
    
    /**
     * Metoda koja šalje eMail grupi korisnika.
     * @param email Lista mejlova na koju se šalje obavijest
     * @param eMuser korisnik emaila sa kojeg se šalje username
     * @param eMpass passwor za moju identidikaciju
     * @param eMadresa moj email sa kojeg šaljem i vršim identifikaciju
     * @param eMnaslov naslov eMaila
     * @param eMsadrzaj sadržaj eMaila
     */
    public void posaljiMail(ArrayList<String> email, 
                    String eMuser,
                    String eMpass,
                    String eMadresa,
                    String eMnaslov,
                    String eMsadrzaj) {
 
          // For establishment of email client with
          // Google's gmail use below properties.
          // For TLS Connection use below properties
          // Create a Properties object
          Properties props = new Properties();
          /*
          // these properties are required
          // providing smtp auth property to true
          props.put("mail.smtp.auth", "true");
          // providing tls enability
          props.put("mail.smtp.starttls.enable", "true");
          // providing the smtp host i.e gmail.com
          props.put("mail.smtp.host", "smtp.gmail.com");
          // providing smtp port as 587
          props.put("mail.smtp.port", "587");
         */
          // For SSL Connection use below properties

            props.put("mail.smtp.host", "smtp.gmail.com");
           props.put("mail.smtp.socketFactory.port", "465");
           props.put("mail.smtp.socketFactory.class",
             "javax.net.ssl.SSLSocketFactory");
           props.put("mail.smtp.auth", "true");
           props.put("mail.smtp.port", "465");

          final String Username = eMuser;
          final String Password = eMpass;
          final String fromEmailAddress = eMadresa;
          ArrayList< String> emails = (ArrayList< String >) email;
          final String subject = eMnaslov;
          final String textMessage = eMsadrzaj;
          Session session = Session.getDefaultInstance(props,
            new LoginAuthenticator(Username,Password));

          try {

           // Create a Message object using the session created above
           Message message = new MimeMessage(session);

           // setting email address to Message from where message is being sent
           message.setFrom(new InternetAddress(fromEmailAddress));

           // setting the email addressess to which user wants to send message
           message.setRecipients(Message.RecipientType.BCC, getEmailsList(emails));

           // setting the subject for the email
           message.setSubject(subject);

           // setting the text message which user wants to send to recipients
          // message.setText(textMessage);
           message.setContent(textMessage, "text/html; charset=utf-8");
           // Using the Transport class send() method to send message
           Transport.send(message);

           System.out.println("\nYour Message delivered successfully ....");

          } catch (MessagingException e) {

           throw new RuntimeException(e);

          } 
 }
 
 // This method takes a list of email addresses and
 // returns back an array of Address by looping the
 // list one by one and storing it into Address[]
 private Address[] getEmailsList(ArrayList< String > emails) {
 
  Address[] emaiAddresses = new Address[emails.size()];
 
  for (int i =0;i < emails.size();i++) {
   try {
    emaiAddresses[i] = new InternetAddress(emails.get(i));
   }
   catch (AddressException e) {
 
    e.printStackTrace();
   }
  }
  return emaiAddresses;
 }
 
 // This method prompts user for email group to which he
 // wants to send message
 /*
 public List< String > getEmails() {
  ArrayList< String > emails = new ArrayList< String >();
 
  int counter = 1;
  String address = "";
  Scanner scanner = new Scanner(System.in);
 
  // looping inifinitely times as long as user enters
  // emails one by one
  // the while loop breaks when user types done and
  // press enter.
  while(true) {
 
   System.out.println("Enter E-Mail : " + counter);
   address = scanner.next();
 
   if(address.equalsIgnoreCase("Done")){
    break;
   }
   else {
    emails.add(address);
    counter++;
   }
  }
 
  return emails;
 }
*/
}

 
// Creating a class for Username and Password authentication
// provided by the user.
class LoginAuthenticator extends Authenticator {
 PasswordAuthentication authentication = null;
 
 public LoginAuthenticator(String username, String password) {
  authentication = new PasswordAuthentication(username,password);
 }
 
 @Override
 protected PasswordAuthentication getPasswordAuthentication() {
  return authentication;
 }
}
    

