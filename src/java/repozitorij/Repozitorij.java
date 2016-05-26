
package repozitorij;

import java.util.Date;

/**
 * 
 * @author ami
 */
public class Repozitorij {
    private int id;
    private String naslov;
    private Date datum;
    private String mapa_za_dokumente;
    private String orepozitoriju;
    
    public Repozitorij() {}
    public Repozitorij(String naslov, Date datum, String mapa_za_dokumente, String orepozitoriju) {
        this.naslov = naslov;
        this.datum = datum;
        this.mapa_za_dokumente = mapa_za_dokumente;
        this.orepozitoriju = orepozitoriju;
    }
    //get and set methods
    public String getNaslov() {return naslov;}   
    public void setNaslov(String naslov) { this.naslov = naslov;}    
    public Date getDatum() { return datum; }
    public void setDatum(Date datum) { this.datum = datum;}
    public String getMapa_za_dokumente() { return mapa_za_dokumente;}    
    public void setMapa_za_dokumente(String mapa_za_dokumente) {this.mapa_za_dokumente = mapa_za_dokumente;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getOrepozitoriju() {return orepozitoriju;}
    public void setOrepozitoriju(String orepozitoriju) {this.orepozitoriju = orepozitoriju;}
}
