package Login;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import korisni.utility;



/**
 *
 * @author amel
 */
//@RequestScoped
//@SessionScoped
@ViewScoped
@ManagedBean 
public class WebKorisnik implements Serializable{
    private Login.loginKontroler lk = new loginKontroler();
    private login selektovaniKorisnik = new login();
    private int selektovaniID, serverResponse;
    private String imePretraga, selektovaniTip;
    public WebKorisnik() { }    
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
            return null;//"/test";
        }else {
            utility.poruka("editKorisnik:btnBrKorisnik", "Neuspješno brisanje korisnika");
            return null;
        }  
    }

    public Login.loginKontroler getLk() { return lk; }
    public void setLk(Login.loginKontroler lk) { this.lk = lk; }   
    public String getImePretraga() {return imePretraga; }
    public void setImePretraga(String imePretraga) {this.imePretraga = imePretraga;}
    public login getSelektovaniKorisnik() {return selektovaniKorisnik;}
    public void setSelektovaniKorisnik(login selektovaniKorisnik) { this.selektovaniKorisnik = selektovaniKorisnik; }
    public int getSelektovaniID() {return selektovaniID; }
    public void setSelektovaniID(int selektovaniID) {this.selektovaniID = selektovaniID;}
    public int getServerResponse() {return serverResponse;}
    public void setServerResponse(int serverResponse) {this.serverResponse = serverResponse;}
    public String getSelektovaniTip() {return selektovaniTip;}
    public void setSelektovaniTip(String selektovaniTip) { this.selektovaniTip = selektovaniTip;} 
}
