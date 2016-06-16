package korisni;


import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author amel
 */
public class utility {
    
    private static String osnovni="c:/projekti/";
    public static String putZaProjekte=osnovni+"pdf/";
    public static String putZaXML=osnovni+"xml/";
    public static String putZaSjednice=osnovni+"sjednice/";
    public static String putZaRep=osnovni+"repozitorij/";
    
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
    public static void errPoruka(String poruka, String komponenta){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, poruka);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(komponenta, message);
    }
    public static void warPoruka(String poruka, String komponenta){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, poruka, poruka);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(komponenta, message);
    }
    public static void infoPoruka(String poruka, String komponenta){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, poruka, poruka);
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
    
    /**
 * Returns amount of files in the folder
 *
 * @param dir is path to target directory
     * @return 
 *
 * @throws NotDirectoryException if target {@code dir} is not Directory
 * @throws IOException if has some problems on opening DirectoryStream
 */
public static int getFilesCount(Path dir) throws IOException, NotDirectoryException {
    int c = 0;
    if(Files.isDirectory(dir)) {
        try(DirectoryStream<Path> files = Files.newDirectoryStream(dir)) {
            for(Path file : files) {
                if(Files.isRegularFile(file) || Files.isSymbolicLink(file)) {
                    // symbolic link also looks like file
                    c++;
                }
            }
        }
    }
    else
        throw new NotDirectoryException(dir + " is not directory");

    return c;
}
   
public static String getRequestURL()
{
    Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
    if(request instanceof HttpServletRequest)
    {
            return ((HttpServletRequest) request).getRequestURL().toString();
    }else
    {
        return "";
    }
}

public static boolean provjeriLocal(){
        return getRequestURL().contains("localhost");
}


        
    
    
    
    
}
