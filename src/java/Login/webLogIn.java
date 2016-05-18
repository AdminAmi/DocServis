
package Login;

import Login.loginKontroler;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import korisni.utility;

/**
 *
 * @author amel
 */
@SessionScoped
@ManagedBean (name="LogIn")
public class webLogIn {
    private Login.loginKontroler lk = new loginKontroler();    
    private String user, pass;
    private boolean testRegistracije, zastavica=false;
    private String newPass, confirmPass;  
    private int serverResponse = 0;
    private List<String> tipovi = new ArrayList<>();  
    
    

    public webLogIn() {
         setTestRegistracije(false);
         reset();
         getTipovi().add("admin");
         getTipovi().add("korisnik");
         getTipovi().add("guest"); 
    }
    
    private void reset(){
        setUser(""); setPass(""); setNewPass(""); setConfirmPass("");
    }
    
    public String promjenaPass() throws NoSuchAlgorithmException{
        serverResponse++;
        if(lk.getKorisnik().getPass().equals(utility.sha1(getPass()) ) &&
                (getNewPass().equals(getConfirmPass())) ) {
        
            lk.getKorisnik().setPass(newPass);
            lk.azurirajOsobu(lk.getKorisnik(),lk.getKorisnik().getId());
            setZastavica(true);
            utility.poruka("promjenaLozinke:promjenaPass", 
                    "Uspješna promjena zaporke!!!");            
            reset();
            } else {
            setZastavica(false);
            utility.poruka("promjenaLozinke:promjenaPass", 
                    "Neuspješna promjena zaporke!!!"); 
            reset();
        }            
        return null;
    }
    
    
    /**
     * Unosi novog korisnika
     * @return
     * @throws NoSuchAlgorithmException     * 
     */
    public String unesiNovogKorisnika() throws NoSuchAlgorithmException {
        serverResponse++;
        if( getNewPass().equals(getConfirmPass()) && getNewPass().length()>0){
            lk.getNoviKorisnik().setPass(getNewPass());
            lk.dodajOsobu(lk.getNoviKorisnik());            
            utility.poruka("unosNovogKorisnika:btnNoviKorisnik",
                    "Uspješno dodavanje novog korisnika!!!");
            reset();
            
        } else {            
            utility.poruka("unosNovogKorisnika:btnNoviKorisnik",
                    "Unešene vrijednosti za zaporke nisu iste!!!");
            reset();
        }
        return null;
    }
    
    public String registracija(){
        if(lk.LogIN(user, pass)) {
            setTestRegistracije(true);
            reset();
            return "test";
        }
        else {
            setTestRegistracije(false);
            reset();
            utility.poruka("loginForm:myButton","Neuspješna prijava na sistem!!!");           
        }
        return null;
    }
    
    public String logOff(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();        
        lk=null;        
        reset();
        setTestRegistracije(false);
        return "/prijava";        
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
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the testRegistracije
     */
    public boolean isTestRegistracije() {
        return testRegistracije;
    }

    /**
     * @param testRegistracije the testRegistracije to set
     */
    public void setTestRegistracije(boolean testRegistracije) {
        this.testRegistracije = testRegistracije;
    }

    /**
     * @return the newPass
     */
    public String getNewPass() {
        return newPass;
    }

    /**
     * @param newPass the newPass to set
     */
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    /**
     * @return the confirmPass
     */
    public String getConfirmPass() {
        return confirmPass;
    }

    /**
     * @param confirmPass the confirmPass to set
     */
    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }   

    /**
     * @return the zastavica
     */
    public boolean isZastavica() {
        return zastavica;
    }

    /**
     * @param zastavica the zastavica to set
     */
    public void setZastavica(boolean zastavica) {
        this.zastavica = zastavica;
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
     * @return the tipovi
     */
    public List<String> getTipovi() {
        return tipovi;
    }

    /**
     * @param tipovi the tipovi to set
     */
    public void setTipovi(List<String> tipovi) {
        this.tipovi = tipovi;
    }
    
}
