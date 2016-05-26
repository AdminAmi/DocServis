
package repozitorij;

import java.util.Date;

/**
 *
 * @author ami
 */
public class Dokument {
    private int id;
    private String Naziv, nazivDatoteke, korisnikUpload;
    private Date datumKreiranja;

    public Dokument() {
    }

    public Dokument(String Naziv, String nazivDatoteke, String korisnikUpload, Date datumKreiranja) {
        this.Naziv = Naziv;
        this.nazivDatoteke = nazivDatoteke;
        this.korisnikUpload = korisnikUpload;
        this.datumKreiranja = datumKreiranja;
    }
    
    

    /**
     * @return the Naziv
     */
    public String getNaziv() {
        return Naziv;
    }

    /**
     * @param Naziv the Naziv to set
     */
    public void setNaziv(String Naziv) {
        this.Naziv = Naziv;
    }

    /**
     * @return the nazivDatoteke
     */
    public String getNazivDatoteke() {
        return nazivDatoteke;
    }

    /**
     * @param nazivDatoteke the nazivDatoteke to set
     */
    public void setNazivDatoteke(String nazivDatoteke) {
        this.nazivDatoteke = nazivDatoteke;
    }

    /**
     * @return the korisnikUpload
     */
    public String getKorisnikUpload() {
        return korisnikUpload;
    }

    /**
     * @param korisnikUpload the korisnikUpload to set
     */
    public void setKorisnikUpload(String korisnikUpload) {
        this.korisnikUpload = korisnikUpload;
    }

    /**
     * @return the datumKreiranja
     */
    public Date getDatumKreiranja() {
        return datumKreiranja;
    }

    /**
     * @param datumKreiranja the datumKreiranja to set
     */
    public void setDatumKreiranja(Date datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
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
    
    
    
}
