/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import korisni.utility;

/**
 *
 * @author ami
 */
@SessionScoped
@ManagedBean (name="LogIn")
public class KontrolerKorisnik {
    private boolean localHost;
    private login korisnik = new login();
//    private Login.loginKontroler lk = new loginKontroler();    
    private String user, pass;
    private boolean testRegistracije, zastavica=false;
    private String newPass, confirmPass;  
    private int serverResponse = 0;
    private List<String> tipovi = new ArrayList<>();
    private List<String> bool = new ArrayList<>(); 
     private login noviKorisnik = new login();

    public KontrolerKorisnik() {
         setTestRegistracije(false);
         setLocalHost(utility.provjeriLocal());
         reset();
         getTipovi().add("admin");
         getTipovi().add("korisnik");
         getTipovi().add("guest"); 
         getBool().add("DA");
         getBool().add("NE");
    }
    
    private void reset(){
        setUser(""); setPass(""); setNewPass(""); setConfirmPass("");
    }
    
  
    
    private boolean LogIN(String user, String pass){
         Login.loginKontroler lk = new loginKontroler();
         
        if (user == null || user.length()==0 || pass==null || pass.length()==0) {
            lk=null;
            return false;
        } else {
            for (login a1 : lk.getKorisnici()) {
                try {
//                    if (a1.getUser().contains(user) && a1.getPass().contains(utility.sha1(pass))){
                    if(a1.getUser().equals(user)&& a1.getPass().equals(utility.sha1(pass))){
                        setKorisnik(a1);
                        lk=null;
                        return true;
                    }
                } catch (NoSuchAlgorithmException ex) {
                }
            }
        }
        return false;                
    }
    
    public String promjenaPass() throws NoSuchAlgorithmException{
        Login.loginKontroler lk = new loginKontroler();
        serverResponse++;
        if(lk.getKorisnik().getPass().equals(utility.sha1(getPass()) ) &&
                (getNewPass().equals(getConfirmPass())) ) {
        
            lk.getKorisnik().setPass(newPass);
            lk.azurirajOsobu(lk.getKorisnik(),lk.getKorisnik().getId());
            setZastavica(true);
//            utility.poruka("promjenaLozinke:promjenaPass", "Uspješna promjena lozinke!!!");
            utility.infoPoruka("Uspješna promjena lozinke!", "");
            reset();
            } else {
            setZastavica(false);
//            utility.poruka("promjenaLozinke:promjenaPass","Neuspješna promjena lozinke!!!");
            utility.errPoruka("Desila se greška! Lozinka nije promjenjena", "");
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
        Login.loginKontroler lk = new loginKontroler();        
        if( getNewPass().equals(getConfirmPass()) && getNewPass().length()>0){
            getNoviKorisnik().setPass(getNewPass());
            
            lk.dodajOsobu(getNoviKorisnik());            
//            utility.poruka("unosNovogKorisnika:btnNoviKorisnik","Uspješno dodavanje novog korisnika!!!");
            utility.infoPoruka("Uspješno dodavanje novog korisnika!", "");
            reset();
            
        } else {            
//            utility.poruka("unosNovogKorisnika:btnNoviKorisnik","Unešene vrijednosti za zaporke nisu iste!!!");
            utility.errPoruka("Unešene vrijednosti za lozinke nisu iste!!!", "");
            reset();
        }
        return null;
    }
    
    public String registracija() throws IOException{  
       
        if(LogIN(user, pass)) {
            setTestRegistracije(true);
            reset();
            utility.setLog(utility.getDatumiVrijeme() + "  "  + 
                 "PRIJAVA KORISNIKA : " + korisnik.getIme() + " " + korisnik.getPrezime());
            return "test?faces-redirect=true";
        }
        else {
            setTestRegistracije(false);
            reset();
            utility.poruka("loginForm:myButton","Neuspješna prijava na sistem!!!");           
        }
        return null;
    }
    
    public String logOff() throws IOException{
       
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();             
        reset();
        setTestRegistracije(false);     
        utility.setLog(utility.getDatumiVrijeme() + "  "  + 
                 "ODJAVA KORISNIKA : " + korisnik.getIme() + " " + korisnik.getPrezime());
        return "/prijava?faces-redirect=true";        
    }
    //get & set
//    public Login.loginKontroler getLk() {  return lk;  }
//    public void setLk(Login.loginKontroler lk) {   this.lk = lk;  }
    public String getUser() {  return user; }
    public void setUser(String user) { this.user = user; }
    public String getPass() {return pass;}
    public void setPass(String pass) { this.pass = pass;}
    public boolean isTestRegistracije() {  return testRegistracije;  }
    public void setTestRegistracije(boolean testRegistracije) {  this.testRegistracije = testRegistracije; }
    public String getNewPass() { return newPass; }
    public void setNewPass(String newPass) { this.newPass = newPass; }
    public String getConfirmPass() {   return confirmPass; }
    public void setConfirmPass(String confirmPass) { this.confirmPass = confirmPass; }  
    public boolean isZastavica() { return zastavica; }
    public void setZastavica(boolean zastavica) {this.zastavica = zastavica; }
    public int getServerResponse() { return serverResponse; }
    public void setServerResponse(int serverResponse) {this.serverResponse = serverResponse; }
    public List<String> getTipovi() {return tipovi;}
    public void setTipovi(List<String> tipovi) { this.tipovi = tipovi;}
    public boolean isLocalHost() {return localHost; }
    public void setLocalHost(boolean localHost) { this.localHost = localHost;}

    /**
     * @return the korisnik
     */
    public login getKorisnik() {
        return korisnik;
    }

    /**
     * @param korisnik the korisnik to set
     */
    public void setKorisnik(login korisnik) {
        this.korisnik = korisnik;
    }

    /**
     * @return the noviKorisnik
     */
    public login getNoviKorisnik() {
        return noviKorisnik;
    }

    /**
     * @param noviKorisnik the noviKorisnik to set
     */
    public void setNoviKorisnik(login noviKorisnik) {
        this.noviKorisnik = noviKorisnik;
    }

    /**
     * @return the bool
     */
    public List<String> getBool() {
        return bool;
    }

    /**
     * @param bool the bool to set
     */
    public void setBool(List<String> bool) {
        this.bool = bool;
    }
}
