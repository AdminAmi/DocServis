package Login;


/**
 *
 * @author amel
 */
public class login {
    private String user,pass, ime, prezime, rola, clanNNV, email;
    private int id;

    public login() {
    }

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
     * @return the ime
     */
    public String getIme() {
        return ime;
    }

    /**
     * @param ime the ime to set
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * @return the prezime
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * @param prezime the prezime to set
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * @return the rola
     */
    public String getRola() {
        return rola;
    }

    /**
     * @param rola the rola to set
     */
    public void setRola(String rola) {
        this.rola = rola;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the clanNNV
     */
    public String getClanNNV() {
        return clanNNV;
    }

    /**
     * @param clanNNV the clanNNV to set
     */
    public void setClanNNV(String clanNNV) {
        this.clanNNV = clanNNV;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

   
    
    
    
}
