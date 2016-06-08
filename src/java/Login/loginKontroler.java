
package Login;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import korisni.utility;

/**
 *
 * @author amel
 */
public class loginKontroler {
    private ArrayList<login> korisnici = new ArrayList<>();
    private ArrayList<login> pretraga = new ArrayList<>();
    private login korisnik = new login();
    private login noviKorisnik = new login();
    private zaXML xml = new zaXML();
    
    public loginKontroler(){  ucitajKorisnike();}
   
    public void ucitajKorisnike(){
        getKorisnici().clear();
        try {
         this.setKorisnici(xml.procitajIzXMLa());        
        } catch (JAXBException ex) { }
    }
    public login getUserFromID(int id){
        if(!korisnici.isEmpty()){
            for(login selektovan: korisnici){if (selektovan.getId()==id) return selektovan;}
        }
        return null;
    }       
    public void pretragaPoImenu(String ime){
        getKorisnici().clear();
        ucitajKorisnike();
        if (ime==null || ime.length()==0) return ;
        for(login kor:korisnici) {if (kor.getIme().contains(ime)) getPretraga().add(kor);}
    }
    public int generateId(){
        int  temp=-1;  
        if(korisnici.isEmpty()) return 0;
        for (login a1 : korisnici) {        
        if (a1.getId()>temp) temp=a1.getId();
    }
    return temp+1;    
    }
    public boolean dodajOsobu(login o) throws NoSuchAlgorithmException{        
        ucitajKorisnike();
        int i = korisnici.size();
        login kor = new login(o.getUser(),utility.sha1(o.getPass()) , 
                o.getIme(), o.getPrezime(), o.getRola(),o.getClanNNV(),o.getEmail());
        kor.setId(generateId());
        this.korisnici.add(kor);
        xml.smjesti(korisnici);     
        return (i!=korisnici.size() && xml.smjestiUXML());
    }
    
    public boolean azurirajOsobu(login log,int id) throws NoSuchAlgorithmException{        
        ucitajKorisnike();
        korisnici.get(id).setIme(log.getIme());
        korisnici.get(id).setPrezime(log.getPrezime());
        korisnici.get(id).setRola(log.getRola());
        korisnici.get(id).setUser(log.getUser());
        korisnici.get(id).setClanNNV(log.getClanNNV());
        korisnici.get(id).setEmail(log.getEmail());
        if(log.getPass().length()>0) 
            korisnici.get(id).setPass(utility.sha1(log.getPass()));
        xml.smjesti(korisnici); 
        return xml.smjestiUXML();
    }
    
    
    public boolean obrisiOsobu(login log){       
       ucitajKorisnike();
       int index = -1;
       utility.poruka("", "Ušao u formu za brisanje: " +String.valueOf(log.getId()));
       for (int i=0 ;i<getKorisnici().size();i++){
           if(getKorisnici().get(i).getId()==log.getId()) index=log.getId();
       }
       korisnici.remove(index);        
       xml.smjesti(korisnici);
       return xml.smjestiUXML();      
   }
    
    public boolean LogIN(String user, String pass){
        if (user == null || user.length()==0 || pass==null || pass.length()==0) {
            return false;
        } else {
            for (login a1 : korisnici) {
                try {
//                    if (a1.getUser().contains(user) && a1.getPass().contains(utility.sha1(pass))){
                    if(a1.getUser().equals(user)&& a1.getPass().equals(utility.sha1(pass))){
                        setKorisnik(a1);
                        return true;
                    }
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(loginKontroler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;                
    }
    //getter and setter
    public ArrayList<login> getKorisnici() {    return korisnici;  }
    public void setKorisnici(ArrayList<login> korisnici) {  this.korisnici = korisnici; }
    public login getKorisnik() { return korisnik; }
    public void setKorisnik(login korisnik) {  this.korisnik = korisnik; }
    public login getNoviKorisnik() { return noviKorisnik; }
    public void setNoviKorisnik(login noviKorisnik) { this.noviKorisnik = noviKorisnik;}
    public ArrayList<login> getPretraga() {return pretraga; }
    public void setPretraga(ArrayList<login> pretraga) { this.pretraga = pretraga;}
    
}
