package rep;


import Login.login;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.xml.bind.JAXBException;
import korisni.utility;

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
    
    public void postaviZastavicu(){
        try {
            getDokumenti().clear();
            this.setDokumenti(DXML.procitajIzXMLa(getPath()));
            korisnici = xml.procitajIzXMLa(getPath());
           // utility.poruka("greska", "Duzina liste korisnici:" + String.valueOf(korisnici.size()));
            for (login a:korisnici){
                //utility.poruka("greska", "Velicina za provjeru" + String.valueOf(getId()));
                //utility.poruka("greska", "Vrijednost iz liste" + String.valueOf(a.getId()));
                if (getId()==a.getId()){
                    setImaPristup(true);
                    //utility.poruka("greska", "Unutar desio se pogodak");
                }
                }  
        } catch (JAXBException ex) {        
        }
        
    }
    public void ucitajDokumenteRep(){
//        try {
//            getDokumenti().clear();
//            this.setDokumenti(DXML.procitajIzXMLa(getPath()));
//            korisnici = xml.procitajIzXMLa(getPath());
//            utility.poruka("greska", "Duzina liste korisnici:" + String.valueOf(korisnici.size()));
//            for (login a:korisnici){
//                utility.poruka("greska", "Velicina za provjeru" + String.valueOf(getId()));
//                utility.poruka("greska", "Vrijednost iz liste" + String.valueOf(a.getId()));
//                if (getId()==a.getId()){
//                    setImaPristup(true);
//                    utility.poruka("greska", "Unutar desio se pogodak");
//                }
//                }  
//        } catch (JAXBException ex) {        
//        }
    }
     public void myListenerRep (ActionEvent event){
     try{
        int i= (int) event.getComponent().getAttributes().get("korisnickiID");
        setId(id);
        } catch (Exception e){} 
     }
     
        
    @PostConstruct
    private void init(){
//         boolean zastavica= false;
//    
//        try {
//            korisnici = xml.procitajIzXMLa(getPath());
//        } catch (JAXBException ex) {
//           
//        }
//        for (login a:korisnici){
//        if (getId()==a.getId()) setImaPristup(true);
//        }       
    }
    
    

    public boolean isImaPristup() {
        return imaPristup;
    }

    public void setImaPristup(boolean imaPristup) {
        this.imaPristup = imaPristup;
    }
    
    
    
    
    
}

