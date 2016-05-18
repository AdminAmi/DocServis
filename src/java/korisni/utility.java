package korisni;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
/**
 *
 * @author amel
 */
public class utility {
    
    public static String putZaProjekte="c:/projekti/pdf/";
    public static String putZaXML="c:/projekti/xml/";
    
    /**
     *
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }

    /**
     * Prosljeđuje poruku na akciju određene web komponente
     * @param komponenta
     * @param poruka
     */
    public static void poruka(String komponenta, String poruka){
        FacesMessage message = new FacesMessage(poruka);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(komponenta, message);
    }
    
    
    
}
