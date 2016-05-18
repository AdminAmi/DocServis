package Login;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import korisni.utility;



/**
 *
 * @author amel
 */
//@RequestScoped
@SessionScoped
@ManagedBean 
public class WebKorisnik {
    private Login.loginKontroler lk = new loginKontroler();
    private login selektovaniKorisnik = new login();
    private int selektovaniID, serverResponse;
    private String imePretraga, selektovaniTip;
    

    public WebKorisnik() {
    }
    
    @PostConstruct
    public void init() { 
        
        
    //inicijalizacijski kod za objekat bolje nego konstruiktor
    }
    @PreDestroy
    public void shutdown() {
    // shutdown code
    }
    public void ucitajOsobu(){
        selektovaniKorisnik=lk.getUserFromID(getSelektovaniID());
        setSelektovaniTip(selektovaniKorisnik.getRola());
              
    }
    
    public void pretraga(){
        resetLista();
        lk.pretragaPoImenu(getImePretraga());
        setImePretraga("");
    }
    public void resetLista(){lk.getPretraga().clear();}
    
    public void azurirajKorisnika() throws NoSuchAlgorithmException{ 
        selektovaniKorisnik.setRola(selektovaniTip);
        if(lk.azurirajOsobu(selektovaniKorisnik,selektovaniID)){
            utility.poruka("editKorisnik:btnAzKorisnik", "Uspješno ažuriranje korisnika");
        }
        else {utility.poruka("editKorisnik:btnAzKorisnik", "Neuspješao ažuriranje korisnika");}
    }
    
    public String obrisiKorisnika(){
        if(lk.obrisiOsobu(selektovaniKorisnik)){
            utility.poruka("editKorisnik:btnBrKorisnik", "Uspješno brisanje korisnika");
            return "main";
        }else {
            utility.poruka("editKorisnik:btnBrKorisnik", "Neuspješno brisanje korisnika");
            return null;
        }  
    }

    /**
     * @return the lk
     */
    public Login.loginKontroler getLk() {
        return lk;
    }

    /**
     * @param lk the lk to set
     */
    public void setLk(Login.loginKontroler lk) {
        this.lk = lk;
    }

    /**
     * @return the imePretraga
     */
    public String getImePretraga() {
        return imePretraga;
    }

    /**
     * @param imePretraga the imePretraga to set
     */
    public void setImePretraga(String imePretraga) {
        this.imePretraga = imePretraga;
    }

    /**
     * @return the selektovaniKorisnik
     */
    public login getSelektovaniKorisnik() {
        return selektovaniKorisnik;
    }

    /**
     * @param selektovaniKorisnik the selektovaniKorisnik to set
     */
    public void setSelektovaniKorisnik(login selektovaniKorisnik) {
        this.selektovaniKorisnik = selektovaniKorisnik;
    }

    /**
     * @return the selektovaniID
     */
    public int getSelektovaniID() {
        return selektovaniID;
    }

    /**
     * @param selektovaniID the selektovaniID to set
     */
    public void setSelektovaniID(int selektovaniID) {
        this.selektovaniID = selektovaniID;
    }

    /**
     * @return the serverResponse
     */
    public int getServerResponse() {
        return serverResponse;
    }

    /**
     * @param serverResponse the serverResponse to set
     */
    public void setServerResponse(int serverResponse) {
        this.serverResponse = serverResponse;
    }

    /**
     * @return the selektovaniTip
     */
    public String getSelektovaniTip() {
        return selektovaniTip;
    }

    /**
     * @param selektovaniTip the selektovaniTip to set
     */
    public void setSelektovaniTip(String selektovaniTip) {
        this.selektovaniTip = selektovaniTip;
    }
    
    

    

}
