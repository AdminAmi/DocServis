package Login;

import java.io.Serializable;


/**
 *
 * @author amel
 */
public class login implements Serializable{
    private String user,pass, ime, prezime, rola, clanNNV, email;
    private int id;

    public login() {    }

    public login(String user, String pass, String ime, String prezime, String rola) {
        this.user = user;
        this.pass = pass;
        this.ime = ime;
        this.prezime = prezime;
        this.rola = rola;
    }

    public login(String user, String pass, String ime, String prezime, String rola, String clanNNV, String eMail) {
        this.user = user;
        this.pass = pass;
        this.ime = ime;
        this.prezime = prezime;
        this.rola = rola;
        this.clanNNV = clanNNV;
        this.email = eMail;       
    }
    
    public String getUser() {  return user;  }
    public void setUser(String user) {  this.user = user;}
    public String getPass() {   return pass; }
    public void setPass(String pass) {    this.pass = pass; }
    public String getIme() {   return ime; }
    public void setIme(String ime) {   this.ime = ime; }
    public String getPrezime() {   return prezime; }
    public void setPrezime(String prezime) {  this.prezime = prezime; }
    public String getRola() {  return rola;   }
    public void setRola(String rola) {  this.rola = rola; }
    public int getId() {   return id;  }
    public void setId(int id) {  this.id = id;  }
    public String getClanNNV() {   return clanNNV;  }
    public void setClanNNV(String clanNNV) {  this.clanNNV = clanNNV; }
    public String getEmail() {  return email; }
    public void setEmail(String email) {   this.email = email; }
    
    
}
