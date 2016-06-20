package nnv;

import java.util.ArrayList;
import java.util.Collections;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;
import korisni.utility;

/**
 *
 * @author Amel Džanić 
 */
@ManagedBean
@ViewScoped
public final class SjednicaKontroler {
    private ArrayList<Sjednica> sjednice = new ArrayList<>();
    private ArrayList<Sjednica> pretraga = new ArrayList<>();
    private Sjednica sjednica = new Sjednica();
    private String godina, mjesec;
    private int brojSjednica;
    private Sjednica novaSjednica = new Sjednica();
    private Sjednica selektovanaSjednica = new Sjednica();
    private String selektovaniID;
    private SjednicaXML Sxml = new SjednicaXML(); 
    private ListDataModel<Sjednica> Pr ;
    private ListDataModel<Sjednica> Pr1 ;

    public SjednicaKontroler() {
        try {
            if (sjednice.isEmpty()) this.setSjednice(Sxml.procitajIzXMLa());
            Collections.sort(sjednice, (Sjednica o1, Sjednica o2) 
                    -> o2.getDatum().compareTo(o1.getDatum()));
            Pr= new ListDataModel<>(sjednice);
        } catch (Exception e) {}
    }
    
    
    public int generateId(){
        int  temp=-1; 
        if (getSjednice()==null) return 0;
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
    //Provjeriti za mjesec ne šlaga
    public void pretragaSjednica (){
        getPretraga().clear();
        if(getMjesec().length()==0 && getGodina().length()!=0){
            for (Sjednica a1:getSjednice()){
                if(utility.datum(a1.getDatum()).contains(godina)) getPretraga().add(a1);
            }
        } 
        else if(getGodina().length()==0 && getMjesec().length()!=0){
            for (Sjednica a1:getSjednice()){
                if(utility.datum(a1.getDatum()).contains(mjesec)) getPretraga().add(a1);
            }
        }
        else if(getMjesec().length()!=0 && getMjesec().length()!=0){
            for (Sjednica a1:getSjednice()){
            if(utility.datum(a1.getDatum()).contains(mjesec)
                    && utility.datum(a1.getDatum()).contains(godina) ) getPretraga().add(a1);
            }            
        }
        else if(getMjesec().length()==0 && getMjesec().length()==0){
//            utility.poruka("sjednice","Niste unijeli parametre za pretraživanje");
             utility.warPoruka("Niste unijeli parametre za pretraživanje!", "");
            
        }
        setBrojSjednica(getPretraga().size());
        setPr1(new ListDataModel<>(getPretraga()));
    }
    
    public void obrisiSjednicu (Sjednica s){        
        if(utility.brisiFile(utility.datumZaDirektorij(s.getDatum()))){
            sjednice.remove(s);
            //pretragaSjednica();
            boolean flag=Sxml.smjestiUXML();        
            getSxml().smjesti(getSjednice());
            if(sjednice.isEmpty()){
                if(utility.brisiFile(utility.putZaSjednice+"sjednice.xml"))
                    utility.infoPoruka("Nema nijedne sjednice NNV-a!","");
            }
//            utility.poruka("sjednice","Uspješno obrisana sjednica");
                 utility.infoPoruka("Uspješno obrisana sjednica!","");
        }
        else{
//            utility.poruka("sjednice", "Direktorij nije prazan");
                utility.warPoruka("Direktorij nije prazan!", "");
        }
    }
    
     public void obrisiSjednicuPretraga (Sjednica s){ 
         obrisiSjednicu(s); 
         getPretraga().remove(s);
         setBrojSjednica(getPretraga().size());
                               
    }
    
    public String dodajSjednicu(){
        
        if(dodajSjednicu(getNovaSjednica())) {
           // return "/nnv/pregledSjednica";
//           utility.poruka("SjednicaNNV", "Uspješan unos sjednice");
            utility.infoPoruka("Uspješan unos sjednice!", "");
           return null;
        }
        else{
//            utility.poruka("SjednicaNNV", "Neuspješan unos sjednice");
            utility.errPoruka("Neuspješan unos sjednice!", "");
            return null;
        }
    }
   
    
     public boolean dodajSjednicu(Sjednica o) {
        if(provjeraSjednice(o)==false) return false;
        int i;
        utility.kreirajDirektorij(utility.datumZaDirektorij(o.getDatum())); 
        if (getSjednice()==null) i = 0 ;
        else{
        i = getSjednice().size();
        }
        Sjednica kor = new Sjednica(o.getBroj(),o.getDatum(), utility.datumZaDirektorij(o.getDatum()));
        kor.setId(generateId());
        kor.setVrijemePocetka(o.getVrijemePocetka());
        this.getSjednice().add(kor);
        getSxml().smjesti(getSjednice());     
        return (i!=getSjednice().size() && getSxml().smjestiUXML());
    }
    
    private boolean provjeraSjednice(Sjednica o){
        try{
        boolean zastavica = true;
        for(Sjednica a: getSjednice()){
            if(a.getBroj().equals(o.getBroj())) zastavica=false; 
        }
        return zastavica;
        } catch (Exception e){}
        return true;
    }
    

    //getter & setter
    public ArrayList<Sjednica> getSjednice() { return sjednice; }
    public void setSjednice(ArrayList<Sjednica> sjednice) { this.sjednice = sjednice; }
    public ArrayList<Sjednica> getPretraga() {return pretraga;}
    public void setPretraga(ArrayList<Sjednica> pretraga) {this.pretraga = pretraga;}
    public Sjednica getSjednica() {return sjednica;}
    public void setSjednica(Sjednica sjednica) {this.sjednica = sjednica;}
    public SjednicaXML getSxml() {return Sxml;}
    public void setSxml(SjednicaXML Sxml) {this.Sxml = Sxml; }
    public Sjednica getNovaSjednica() { return novaSjednica; }
    public void setNovaSjednica(Sjednica novaSjednica) { this.novaSjednica = novaSjednica;}
    public Sjednica getSelektovanaSjednica() { return selektovanaSjednica; }
    public void setSelektovanaSjednica(Sjednica selektovanaSjednica) { this.selektovanaSjednica = selektovanaSjednica;}
    public String getSelektovaniID() { return selektovaniID; }
    public void setSelektovaniID(String selektovaniID) {this.selektovaniID = selektovaniID; }
    public String getGodina() { return godina;}
    public void setGodina(String godina) { this.godina = godina; }
    public int getBrojSjednica() { return brojSjednica; }
    public void setBrojSjednica(int brojSjednica) {this.brojSjednica = brojSjednica; }
    public String getMjesec() { return mjesec;  }
    public void setMjesec(String mjesec) {    this.mjesec = mjesec;  }

    /**
     * @return the Pr
     */
    public ListDataModel<Sjednica> getPr() {
        return Pr;
    }

    /**
     * @param Pr the Pr to set
     */
    public void setPr(ListDataModel<Sjednica> Pr) {
        this.Pr = Pr;
    }

    /**
     * @return the Pr1
     */
    public ListDataModel<Sjednica> getPr1() {
        return Pr1;
    }

    /**
     * @param Pr1 the Pr1 to set
     */
    public void setPr1(ListDataModel<Sjednica> Pr1) {
        this.Pr1 = Pr1;
    }
    
    
    
}
