package repozitorij;

import Login.login;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import korisni.utility;

/**
 *
 * @author Amel Džanić 
 */
@ManagedBean
//@RequestScoped
@ViewScoped
public final class RepozitorijKontroler {
    
    private ArrayList<Repozitorij> repozitoriji = new ArrayList<Repozitorij>();
    private ArrayList<Repozitorij> pretraga = new ArrayList<Repozitorij>();
    private Repozitorij repozitorij = new Repozitorij();
    private String naslov, mjesec, imeKorisnika;
    private int brojSjednica;
    private Repozitorij noviRepozitorij = new Repozitorij();
    private Repozitorij selektovaniRepozitorij = new Repozitorij();
    private String selektovaniID;
    private RepozitorijXML Sxml = new RepozitorijXML();
    private zaXMLUser XMLUser = new zaXMLUser();
    private Login.login korisnik = new login();
    private ArrayList<login> korisnici = new ArrayList<login>();
    //private ArrayList<login> selektovani = new ArrayList<login>();
   
    
    public RepozitorijKontroler() {
        try {
            if (repozitoriji.isEmpty()) this.setRepozitoriji(Sxml.procitajIzXMLa());
            Collections.sort(repozitoriji, (Repozitorij o1, Repozitorij o2) 
                    -> o2.getDatum().compareTo(o1.getDatum()));
        } catch (Exception e) {}
    }
    //Moram na isti način uraditi kao sa sjednicama da se konstruktor oslobodi
    public int generateId(){
        int  temp=-1;  
        if(getRepozitoriji().isEmpty()) return 0;
        for (Repozitorij a1 : getRepozitoriji()) {        
        if (a1.getId()>temp) temp=a1.getId();
        }
        return temp+1;
    }
    public Repozitorij vratiRepPoImenu(String id){
        if(!repozitoriji.isEmpty()){
            for (Repozitorij s:getRepozitoriji()){
                if(s.getNaslov().equals(id)) return s;
            }
        }
        return null;
    }
    public void ucitajRepozitorij(){
        setSelektovaniRepozitorij(vratiRepPoImenu(selektovaniID));
    }
    //Ova metoda će biti samo za pretragu imena repozitorija
    public void pretragaRepozitorija (){
        getPretraga().clear();
        if( getNaslov().length()!=0){
            for (Repozitorij a1:getRepozitoriji()){
                if(a1.getNaslov().contains(naslov)) getPretraga().add(a1);
            }
        }
        if(getNaslov().length()==0 ){
            utility.poruka("sjednice","Niste unijeli parametre za pretraživanje");            
        }
        setBrojSjednica(getPretraga().size());
    }
    
    public void obrisiRepzitorij (Repozitorij s){        
        if(utility.brisiFile(utility.datumZaDirektorij(s.getDatum()))){
            repozitoriji.remove(s);
            pretragaRepozitorija();
            boolean flag=Sxml.smjestiUXML();        
            getSxml().smjesti(getRepozitoriji());
            if(repozitoriji.isEmpty()){
                if(utility.brisiFile(utility.putZaRep+"repozitoriji.xml"))
                    utility.poruka("sjednice","Nema nijednog repozitorija!");
            }
            utility.poruka("sjednice","Uspješno obrisan repozitorij");        
        }
        else{
            utility.poruka("sjednice", "Direktorij nije prazan");
        }
    }
    
     public void obrisiRepozitorijPretraga (Repozitorij s){ 
         obrisiRepzitorij(s); 
         getPretraga().remove(s);
         setBrojSjednica(getPretraga().size());
                               
    }
    
    public String dodajRepozitorij(){        
        if(dodajRepozitorij(getNoviRepozitorij())) {           
           utility.poruka("SjednicaNNV:Unos", "Uspješan unos repozitorija");
           return null;
        }
        else{
            utility.poruka("SjednicaNNV:Unos", "Neuspješan unos repozitorija");
            return null;
        }
    }
    
     public boolean dodajRepozitorij(Repozitorij o) {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        utility.kreirajDirektorij(utility.putZaRep+o.getNaslov()); 
        o.setDatum(date);
        //if(provjeraSjednice(o)==false) return false;
        int i = getRepozitoriji().size();
        Repozitorij kor = new Repozitorij(o.getNaslov(),o.getDatum(), o.getMapa_za_dokumente(),o.getOrepozitoriju());
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
    
    public void dodajKorisnika (login korisnik){
        korisnici.add(korisnik);
    }
    public void obrisiKorisnik(login korisnik){
        boolean remove = korisnici.remove(korisnik);
    }
            
    public void spremiPodatke(){
        XMLUser.smjesti(korisnici);
        noviRepozitorij.setMapa_za_dokumente(utility.putZaRep+noviRepozitorij.getNaslov());
        if(dodajRepozitorij(noviRepozitorij) && 
                XMLUser.smjestiUXML(noviRepozitorij.getMapa_za_dokumente()) ){
            utility.poruka("SjednicaNNV:Unos", "Uspješno kreiran repositorij");
            korisnici.clear();
           // noviRepozitorij.setOrepozitoriju("");            
            noviRepozitorij = new Repozitorij();
            return;
        } else {
             utility.poruka("SjednicaNNV:Unos", "Desila se greska");
        }      
       
        
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
    public Repozitorij getNoviRepozitorij() {return noviRepozitorij;}
    public void setNoviRepozitorij(Repozitorij noviRepozitorij) { this.noviRepozitorij = noviRepozitorij;}
    public Repozitorij getSelektovaniRepozitorij() {return selektovaniRepozitorij;}
    public void setSelektovaniRepozitorij(Repozitorij selektovaniRepozitorij) {this.selektovaniRepozitorij = selektovaniRepozitorij;}
    public String getSelektovaniID() {return selektovaniID;}
    public void setSelektovaniID(String selektovaniID) {this.selektovaniID = selektovaniID;}
    public String getNaslov() {return naslov;}
    public void setNaslov(String naslov) {this.naslov = naslov;}
    public int getBrojSjednica() {return brojSjednica;}
    public void setBrojSjednica(int brojSjednica) {this.brojSjednica = brojSjednica;}
    public String getMjesec() {return mjesec;}
    public void setMjesec(String mjesec) {this.mjesec = mjesec;}
    public Login.login getKorisnik() {return korisnik;}
    public void setKorisnik(Login.login korisnik) {this.korisnik = korisnik;}
    public ArrayList<login> getKorisnici() {return korisnici;}
    public void setKorisnici(ArrayList<login> korisnici) {this.korisnici = korisnici;}

    public String getImeKorisnika() {
        return imeKorisnika;
    }

    public void setImeKorisnika(String imeKorisnika) {
        this.imeKorisnika = imeKorisnika;
    }
}
