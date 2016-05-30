package repozitorij;


import Login.login;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.JAXBException;

/**
 *
 * @author ami
 */
@ManagedBean
@ViewScoped
public class DokumentKontrolerRep extends nnv.DokumentKontroler {
    //Treba implementirati rad sa korisnicima
    private boolean imaPristup=false;
    private Login.login korisnik = new login();
    private ArrayList<login> korisnici = new ArrayList<login>();
    private zaXMLUser xml = new zaXMLUser();

    public DokumentKontrolerRep() {}
        
    @PostConstruct
    private void init(){
         boolean zastavica= false;
    
        try {
            korisnici = xml.procitajIzXMLa(getPath());
        } catch (JAXBException ex) {
           
        }
        for (login a:korisnici){
        if (getId()==a.getId()) setImaPristup(true);
        }       
    }
    
    

    public boolean isImaPristup() {
        return imaPristup;
    }

    public void setImaPristup(boolean imaPristup) {
        this.imaPristup = imaPristup;
    }
    
    
    
    
    
}
