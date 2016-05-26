package korisni;


import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author amel
 */
public class utility {
    
    private static String osnovni="c:/projekti/";
    public static String putZaProjekte="c:/projekti/pdf/";
    public static String putZaXML="c:/projekti/xml/";
    public static String putZaSjednice="c:/projekti/sjednice/";
    public static String putZaRep="c:/projekti/repozitorij/";
    
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
    
    public static String datumZaDirektorij(Date datum){
        Format formater= new SimpleDateFormat("dd-MM-yyyy");
        String path = utility.putZaSjednice + formater.format(datum);
        return path;
    }
    
    public static String datum (Date date){
        Format formater= new SimpleDateFormat("dd-MM-yyyy");
        return formater.format(date);
    }
    
    public static void kreirajDirektorij(String path){
        File theDir = new File(path);
        // if the directory does not exist, create it
        if (!theDir.exists()) {   
            boolean result = false;
            try{
                theDir.mkdir();
                result = true;
                } 
            catch(SecurityException se){
        //handle it
            }        
        if(result) {    
        System.out.println("DIR created");  
            }
        }
    }
    public static void init(){
        kreirajDirektorij(osnovni);
        kreirajDirektorij(putZaProjekte);
        kreirajDirektorij(putZaXML);
        kreirajDirektorij(putZaSjednice);
        kreirajDirektorij(putZaRep);
                
    }
    
    public static boolean brisiFile(String path){
         try{    		
            File file = new File(path);        	
            return file.delete();    		
            }catch(Exception e){return false; } 
    }
    /*
    public void postaviBoju(){
        String currentUrl = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        if (currentUrl.contains("nnv")){
            
        } 
        if (currentUrl.contains("projekti")){
            
        }
        if(currentUrl.contains("nnv")){
            
        }
*/


        
    
    
    
    
}
