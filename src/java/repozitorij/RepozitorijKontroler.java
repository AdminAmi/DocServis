package repozitorij;

import java.util.ArrayList;
import java.util.Collections;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import korisni.utility;

/**
 *
 * @author Amel Džanić 
 */
@ManagedBean
@ViewScoped
public final class RepozitorijKontroler {
    private ArrayList<Repozitorij> repozitoriji = new ArrayList<>();
    private ArrayList<Repozitorij> pretraga = new ArrayList<>();
    private Repozitorij repozitorij = new Repozitorij();
    private String godina, mjesec;
    private int brojSjednica;
    private Repozitorij noviRepzitorij = new Repozitorij();
    private Repozitorij selektovaniRepozitorij = new Repozitorij();
    private String selektovaniID;
    private RepozitorijXML Sxml = new RepozitorijXML();    

    public RepozitorijKontroler() {
        try {
            if (repozitoriji.isEmpty()) this.setRepozitoriji(Sxml.procitajIzXMLa());
            Collections.sort(repozitoriji, (Repozitorij o1, Repozitorij o2) 
                    -> o2.getDatum().compareTo(o1.getDatum()));
        } catch (Exception e) {}
    }
    
    public int generateId(){
        int  temp=-1;  
        if(getRepozitoriji().isEmpty()) return 0;
        for (Repozitorij a1 : getRepozitoriji()) {        
        if (a1.getId()>temp) temp=a1.getId();
        }
        return temp+1;
    }
    public Repozitorij vratiSjednicuPoBroju(String id){
        if(!repozitoriji.isEmpty()){
            for (Repozitorij s:getRepozitoriji()){
                if(s.getNaslov().equals(id)) return s;
            }
        }
        return null;
    }
    public void ucitajSjednicu(){
        setSelektovaniRepozitorij(vratiSjednicuPoBroju(selektovaniID));
    }
    
    public void pretragaSjednica (){
        getPretraga().clear();
        if(getMjesec().length()==0 && getGodina().length()!=0){
            for (Repozitorij a1:getRepozitoriji()){
                if(utility.datum(a1.getDatum()).contains(godina)) getPretraga().add(a1);
            }
        } 
        if(getGodina().length()==0 && getMjesec().length()!=0){
            for (Repozitorij a1:getRepozitoriji()){
                if(utility.datum(a1.getDatum()).contains(mjesec)) getPretraga().add(a1);
            }
        }
        if(getMjesec().length()!=0 && getMjesec().length()!=0){
            for (Repozitorij a1:getRepozitoriji()){
            if(utility.datum(a1.getDatum()).contains(mjesec)
                    && utility.datum(a1.getDatum()).contains(godina) ) getPretraga().add(a1);
            }            
        }
        if(getMjesec().length()==0 && getMjesec().length()==0){
            utility.poruka("sjednice","Niste unijeli parametre za pretraživanje");
            
        }
        setBrojSjednica(getPretraga().size());
    }
    
    public void obrisiSjednicu (Repozitorij s){        
        if(utility.brisiFile(utility.datumZaDirektorij(s.getDatum()))){
            repozitoriji.remove(s);
            pretragaSjednica();
            boolean flag=Sxml.smjestiUXML();        
            getSxml().smjesti(getRepozitoriji());
            if(repozitoriji.isEmpty()){
                if(utility.brisiFile(utility.putZaSjednice+"sjednice.xml"))
                    utility.poruka("sjednice","Nema nijedne sjednice NNV-a!");
            }
            utility.poruka("sjednice","Uspješno obrisana sjednica");        
        }
        else{
            utility.poruka("sjednice", "Direktorij nije prazan");
        }
    }
    
     public void obrisiSjednicuPretraga (Repozitorij s){ 
         obrisiSjednicu(s); 
         getPretraga().remove(s);
         setBrojSjednica(getPretraga().size());
                               
    }
    
    public String dodajSjednicu(){
        
        if(dodajSjednicu(getNoviRepzitorij())) {
           // return "/nnv/pregledSjednica";
           utility.poruka("SjednicaNNV", "Uspješan unos sjednice");
           return null;
        }
        else{
            utility.poruka("SjednicaNNV", "Neuspješan unos sjednice");
            return null;
        }
    }
   
    
     public boolean dodajSjednicu(Repozitorij o) {
        if(provjeraSjednice(o)==false) return false;
        utility.kreirajDirektorij(utility.datumZaDirektorij(o.getDatum()));       
        int i = getRepozitoriji().size();
        Repozitorij kor = new Repozitorij(o.getNaslov(),o.getDatum(), utility.datumZaDirektorij(o.getDatum()),o.getOrepozitoriju());
        kor.setId(generateId());        
        this.getRepozitoriji().add(kor);
        getSxml().smjesti(getRepozitoriji());     
        return (i!=getRepozitoriji().size() && getSxml().smjestiUXML());
    }
    
    private boolean provjeraSjednice(Repozitorij o){
        boolean zastavica = true;
        for(Repozitorij a: getRepozitoriji()){
            if(a.getNaslov().equals(o.getNaslov())) zastavica=false; 
        }
        return zastavica;
    }
    //get and set
    public ArrayList<Repozitorij> getRepozitoriji() { return repozitoriji;  }
    public void setRepozitoriji(ArrayList<Repozitorij> repozitoriji) { this.repozitoriji = repozitoriji;}
    public ArrayList<Repozitorij> getPretraga() {return pretraga;}
    public void setPretraga(ArrayList<Repozitorij> pretraga) {this.pretraga = pretraga;}
    public Repozitorij getRepozitorij() {return repozitorij;}
    public void setRepozitorij(Repozitorij repozitorij) {this.repozitorij = repozitorij; }
    public RepozitorijXML getSxml() { return Sxml;}
    public void setSxml(RepozitorijXML Sxml) {this.Sxml = Sxml;}
    public Repozitorij getNoviRepzitorij() {return noviRepzitorij;}
    public void setNoviRepzitorij(Repozitorij noviRepzitorij) { this.noviRepzitorij = noviRepzitorij;}
    public Repozitorij getSelektovaniRepozitorij() {return selektovaniRepozitorij;}
    public void setSelektovaniRepozitorij(Repozitorij selektovaniRepozitorij) {this.selektovaniRepozitorij = selektovaniRepozitorij;}
    public String getSelektovaniID() {return selektovaniID;}
    public void setSelektovaniID(String selektovaniID) {this.selektovaniID = selektovaniID;}
    public String getGodina() {return godina;}
    public void setGodina(String godina) {this.godina = godina;}
    public int getBrojSjednica() {return brojSjednica;}
    public void setBrojSjednica(int brojSjednica) {this.brojSjednica = brojSjednica;}
    public String getMjesec() {return mjesec;}
    public void setMjesec(String mjesec) {this.mjesec = mjesec;}
}
