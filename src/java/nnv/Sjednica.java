
package nnv;

import java.util.Date;

/**
 * Klasa koja definiše datum i broj održavanja sjednice NNV
 * @author ami
 */
public class Sjednica {
    private int id;
    private String broj;
    private Date datum;
    private String mapa_za_dokumente;
    private String vrijemePocetka;

    public Sjednica() {
    }

    public Sjednica(String broj, Date datum, String mapa_za_dokumente) {
        this.broj = broj;
        this.datum = datum;
        this.mapa_za_dokumente = mapa_za_dokumente;
    }
    

    /**
     * @return the broj
     */
    public String getBroj() {
        return broj;
    }

    /**
     * @param broj the broj to set
     */
    public void setBroj(String broj) {
        this.broj = broj;
    }

    /**
     * @return the datum
     */
    public Date getDatum() {
        return datum;
    }

    /**
     * @param datum the datum to set
     */
    public void setDatum(Date datum) {
        this.datum = datum;
    }

    /**
     * @return the mapa_za_dokumente
     */
    public String getMapa_za_dokumente() {
        return mapa_za_dokumente;
    }

    /**
     * @param mapa_za_dokumente the mapa_za_dokumente to set
     */
    public void setMapa_za_dokumente(String mapa_za_dokumente) {
        this.mapa_za_dokumente = mapa_za_dokumente;
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

    public String getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(String vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }
    
    
    
    
}
