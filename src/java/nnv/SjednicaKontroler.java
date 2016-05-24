package nnv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.JAXBException;
import korisni.utility;

/**
 *
 * @author Amel Džanić 
 */
@ManagedBean
@ViewScoped
public class SjednicaKontroler {
    private ArrayList<Sjednica> sjednice = new ArrayList<>();
    private ArrayList<Sjednica> pretraga = new ArrayList<>();
    private Sjednica sjednica = new Sjednica();
    private Sjednica novaSjednica = new Sjednica();
    private Sjednica selektovanaSjednica = new Sjednica();
    private String selektovaniID;
    private SjednicaXML Sxml = new SjednicaXML();    

    public SjednicaKontroler() {
        try {
            if (sjednice.isEmpty()) this.setSjednice(Sxml.procitajIzXMLa());
            Collections.sort(sjednice, (Sjednica o1, Sjednica o2) 
                    -> o2.getDatum().compareTo(o1.getDatum()));
        } catch (Exception e) {}
    }
    
    public int generateId(){
        int  temp=-1;  
        if(getSjednice().isEmpty()) return 0;
        for (Sjednica a1 : getSjednice()) {        
        if (a1.getId()>temp) temp=a1.getId();
        }
        return temp+1;
    }
    public Sjednica vratiSjednicuPoBroju(String id){
        if(!sjednice.isEmpty()){
            for (Sjednica s:getSjednice()){
                if(s.getBroj().equals(id)) return s;
            }
        }
        return null;
    }
    public void ucitajSjednicu(){
        setSelektovanaSjednica(vratiSjednicuPoBroju(selektovaniID));
    }
    
    public void obrisiSjednicu (Sjednica s){
        
        if(utility.brisiFile(utility.datumZaDirektorij(s.getDatum()))){
        sjednice.remove(s);
        getSxml().smjesti(getSjednice());   
        boolean test=Sxml.smjestiUXML();
        }
        else{
            utility.poruka("sjednice", "Direktorij nije prazan");
        }
    }
    
    public String dodajSjednicu(){
        
        if(dodajSjednicu(getNovaSjednica())) {
//            try{
//            Collections.sort(sjednice, new Comparator<Sjednica>(){
//                @Override
//                public int compare(Sjednica o1, Sjednica o2) {
//                   return o2.getDatum().compareTo(o1.getDatum());
//                }
//                
//            });
//            } catch (Exception ex){}
            return "/nnv/pregledSjednica";
        }
        else return null;
    }
   
    
     public boolean dodajSjednicu(Sjednica o) {
        if(provjeraSjednice(o)==false) return false;
        utility.kreirajDirektorij(utility.datumZaDirektorij(o.getDatum()));       
        int i = getSjednice().size();
        Sjednica kor = new Sjednica(o.getBroj(),o.getDatum(), utility.datumZaDirektorij(o.getDatum()));
        kor.setId(generateId());
        kor.setVrijemePocetka(o.getVrijemePocetka());
        this.getSjednice().add(kor);
        getSxml().smjesti(getSjednice());     
        return (i!=getSjednice().size() && getSxml().smjestiUXML());
    }
    
    private boolean provjeraSjednice(Sjednica o){
        boolean zastavica = true;
        for(Sjednica a: getSjednice()){
            if(a.getBroj().equals(o.getBroj())) zastavica=false; 
        }
        return zastavica;
    }
    

    /**
     * @return the sjednice
     */
    public ArrayList<Sjednica> getSjednice() {
        return sjednice;
    }

    /**
     * @param sjednice the sjednice to set
     */
    public void setSjednice(ArrayList<Sjednica> sjednice) {
        this.sjednice = sjednice;
    }

    /**
     * @return the pretraga
     */
    public ArrayList<Sjednica> getPretraga() {
        return pretraga;
    }

    /**
     * @param pretraga the pretraga to set
     */
    public void setPretraga(ArrayList<Sjednica> pretraga) {
        this.pretraga = pretraga;
    }

    /**
     * @return the sjednica
     */
    public Sjednica getSjednica() {
        return sjednica;
    }

    /**
     * @param sjednica the sjednica to set
     */
    public void setSjednica(Sjednica sjednica) {
        this.sjednica = sjednica;
    }

    /**
     * @return the Sxml
     */
    public SjednicaXML getSxml() {
        return Sxml;
    }

    /**
     * @param Sxml the Sxml to set
     */
    public void setSxml(SjednicaXML Sxml) {
        this.Sxml = Sxml;
    }

    /**
     * @return the novaSjednica
     */
    public Sjednica getNovaSjednica() {
        return novaSjednica;
    }

    /**
     * @param novaSjednica the novaSjednica to set
     */
    public void setNovaSjednica(Sjednica novaSjednica) {
        this.novaSjednica = novaSjednica;
    }

    /**
     * @return the selektovanaSjednica
     */
    public Sjednica getSelektovanaSjednica() {
        return selektovanaSjednica;
    }

    /**
     * @param selektovanaSjednica the selektovanaSjednica to set
     */
    public void setSelektovanaSjednica(Sjednica selektovanaSjednica) {
        this.selektovanaSjednica = selektovanaSjednica;
    }

    /**
     * @return the selektovaniID
     */
    public String getSelektovaniID() {
        return selektovaniID;
    }

    /**
     * @param selektovaniID the selektovaniID to set
     */
    public void setSelektovaniID(String selektovaniID) {
        this.selektovaniID = selektovaniID;
    }
    
    
    
}
