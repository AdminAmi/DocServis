
package nnv;

import java.util.Date;

/**
 * Klasa koja definiše datum i broj održavanja sjednice NNV
 * Koristiće se i za odluke NNV-a
 * @author ami
 */
public class Sjednica {
    private int id;
    private String broj;
    private Date datum;
    private String mapa_za_dokumente;
    private String vrijemePocetka;
    private String opis;
    public Sjednica() {    }
    public Sjednica(String broj, Date datum, String mapa_za_dokumente) {
        this.broj = broj;
        this.datum = datum;
        this.mapa_za_dokumente = mapa_za_dokumente;
    }
    public String getBroj() {    return broj; }
    public void setBroj(String broj) {   this.broj = broj; }
    public Date getDatum() {    return datum;  }
    public void setDatum(Date datum) {  this.datum = datum;  }
    public String getMapa_za_dokumente() { return mapa_za_dokumente; }
    public void setMapa_za_dokumente(String mapa_za_dokumente) {  this.mapa_za_dokumente = mapa_za_dokumente;  }
    public int getId() {  return id; }
    public void setId(int id) {   this.id = id; }
    public String getVrijemePocetka() { return vrijemePocetka; }
    public void setVrijemePocetka(String vrijemePocetka) { this.vrijemePocetka = vrijemePocetka;  }
    public String getOpis() {  return opis; }
    public void setOpis(String opis) { this.opis = opis; }
    
    
    
    
}
