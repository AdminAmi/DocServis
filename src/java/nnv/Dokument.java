
package nnv;

import java.util.Date;

/**
 *
 * @author ami
 */
public class Dokument {
    private int id, idKorisnik;
    private String Naziv, nazivDatoteke, korisnikUpload;
    private String datumKreiranja;

    public Dokument() {
    }

    public Dokument(String Naziv, String nazivDatoteke, String korisnikUpload, String datumKreiranja, int idKorisnik) {
        this.Naziv = Naziv;
        this.nazivDatoteke = nazivDatoteke;
        this.korisnikUpload = korisnikUpload;
        this.datumKreiranja = datumKreiranja;
        this.idKorisnik = idKorisnik;
    }
    
    public String getNaziv() {return Naziv;}
    public void setNaziv(String Naziv) {this.Naziv = Naziv;}
    public String getNazivDatoteke() {return nazivDatoteke;}
    public void setNazivDatoteke(String nazivDatoteke) {this.nazivDatoteke = nazivDatoteke;}
    public String getKorisnikUpload() {return korisnikUpload;}
    public void setKorisnikUpload(String korisnikUpload) { this.korisnikUpload = korisnikUpload;}
    public String getDatumKreiranja() {return datumKreiranja;}
    public void setDatumKreiranja(String datumKreiranja) { this.datumKreiranja = datumKreiranja;}
    public int getId() { return id;}
    public void setId(int id) {this.id = id;}
    public int getIdKorisnik() { return idKorisnik; }
    public void setIdKorisnik(int idKorisnik) { this.idKorisnik = idKorisnik;}
    
    
    
}
