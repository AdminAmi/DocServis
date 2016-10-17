package korisni;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import zaMail.zaXML;

/**
 *
 * @author amel
 */
public class utility {
    
//    public static String osnovni="c:/projekti/";
    public static String osnovni="c:/test/";
    public static String putZaProjekte=osnovni+"pdf/";
    public static String putZaXML=osnovni+"xml/";
    public static String putZaSjednice=osnovni+"sjednice/";
    public static String putZaRep=osnovni+"repozitorij/";
    public static String putZaLog = osnovni+"log.txt";
    
    
    /**
     * Metoda koja vraća sha1 kriptovani unos
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
        kreirajLog();
                
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

public static zaMail.Podaci getPodatke(){
    ArrayList<zaMail.Podaci> p = new ArrayList<>();
    zaMail.zaXML m = new zaXML();
        try {
            p = m.procitajIzXMLa();
        } catch (JAXBException ex) {}
        return p.get(0);
}

/**
Make an int Month from a date
* Make sure your date is allready made by the format : dd/MM/YYYY
     * @param date
     * @return 
*/
public static int getMonthInt(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
    return Integer.parseInt(dateFormat.format(date));
}

/**
Make an int Year from a date
* Make sure your date is allready made by the format : dd/MM/YYYY
     * @param date
     * @return 
*/
public static int getYearInt(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
    return Integer.parseInt(dateFormat.format(date));
}

public static String getDatumiVrijeme(){
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");    
    Date curr = Calendar.getInstance().getTime();
    return df.format(curr);
}  

    /**
     *
     * @param date string datum
     * @param opcija govori o formatu 0 - datum+vrijeme 1 samo datum
     * @return odgovarajuci objekat datuma
     */
    public static Date getVrijemeFromString(String date, int opcija){
    DateFormat df;
    if(opcija == 0){
        df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    } else {
        df = new SimpleDateFormat("dd/MM/YYYY");
    }
    
    Date startDate = new Date();    
        try {
            startDate = df.parse(date);
        } catch (ParseException ex) {
           
        }
        return startDate;   
   
}
 
 static public void setLog( String aContents) throws  IOException  {
        File aFile = new File(putZaLog); 
        PrintWriter output = new PrintWriter(new FileWriter(aFile,true));
        try {       
            output.printf("%s\r\n", aContents);
        }
        finally {
          output.close();
        }
  }
  public static void kreirajLog() {
      try{
      PrintWriter writer = new PrintWriter(putZaLog, "UTF-8");
      }
      catch (Exception e){
              System.out.println(e.getMessage());
              }
  }
    
    
    
    
}
