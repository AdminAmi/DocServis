
package rep;

import Login.KontrolerKorisnik;
import Login.login;
import Login.loginKontroler;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.xml.bind.JAXBException;
import korisni.utility;
import korisni.mailNotifikacija;

/**
 *
 * @author Amel DĹľaniÄ‡ 
 */
@ManagedBean
//@RequestScoped
@ViewScoped
public final class RepozitorijKontroler implements Serializable{
    @ManagedProperty (value = "#{LogIn.korisnik.id}")
    private int id;
    private ArrayList<Repozitorij> repozitoriji = new ArrayList<Repozitorij>();
    private ArrayList<Repozitorij> pretraga = new ArrayList<Repozitorij>();
    private ArrayList<Repozitorij> mojiRep = new ArrayList<Repozitorij>();
    private Repozitorij repozitorij = new Repozitorij();
    private String naslov, mjesec, imeKorisnika;
    private int brojSjednica, ukupniBroj, idK;
    private Repozitorij noviRepozitorij = new Repozitorij();
    private Repozitorij selektovaniRepozitorij = new Repozitorij();
    private String selektovaniID;
    private RepozitorijXML Sxml = new RepozitorijXML();
    private zaXMLUser XMLUser = new zaXMLUser();
    private Login.login korisnik = new login();
    private Login.login korisnik1 = new login();
    private ArrayList<login> korisnici = new ArrayList<login>();
    private ListDataModel<Repozitorij> Pr ;
    private ListDataModel<Repozitorij> Pr1 ;
    
    public RepozitorijKontroler() {
        try {            
//            Login.loginKontroler lk = new loginKontroler();
//            korisnici.add(lk.getUserFromID(getId()));
            if (repozitoriji.isEmpty()) this.setRepozitoriji(Sxml.procitajIzXMLa());
            Collections.sort(repozitoriji, (Repozitorij o1, Repozitorij o2) 
                    -> o2.getDatum().compareTo(o1.getDatum()));
            setUkupniBroj(repozitoriji.size());
            Pr = new ListDataModel<>(repozitoriji);
        } catch (Exception e) {}
    }
    //Moram na isti naÄŤin uraditi kao sa sjednicama da se konstruktor oslobodi
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
        setNoviRepozitorij(getSelektovaniRepozitorij());
        ucitajKorisnike();
    }
    public void azurirajRepozitorij () throws IOException{
        int index=-1;
        Login.loginKontroler lk = new loginKontroler();
        XMLUser.smjesti(korisnici);
        for (int i =0 ; i <getRepozitoriji().size(); i++){
            if(getRepozitoriji().get(i).getId()==selektovaniRepozitorij.getId()) index = i;
        }
        
        if(index!=-1){
        getRepozitoriji().get(index).setOrepozitoriju(selektovaniRepozitorij.getOrepozitoriju());
        getRepozitoriji().get(index).setNaslov(selektovaniRepozitorij.getNaslov());
        getSxml().smjesti(getRepozitoriji());
        boolean flag=getSxml().smjestiUXML() &&
                XMLUser.smjestiUXML(selektovaniRepozitorij.getMapa_za_dokumente());
        utility.setLog(utility.getDatumiVrijeme() + "  "  + 
                 "AŽURIRAN REPOZITORIJ : " + selektovaniRepozitorij.getNaslov() + " " +
                 "OD KORISNIKA : " + korisnik1.getIme()+" " + korisnik1.getPrezime()  + " " +                 
                 " OPIS : "+ selektovaniRepozitorij.getOrepozitoriju()+ " " +
                "KORISNICI : " + korisniciZaLog());
        
        if (!flag) utility.errPoruka("Nije uspjelo ažuriranje!!!", "");
        else utility.infoPoruka("Uspješno izvršeno ažuriranje", mjesec);
        }
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
//            utility.poruka("sjednice","Niste unijeli parametre za pretraĹľivanje");
            utility.warPoruka("Niste unjeli parametre za pretraživanje", "");
        }
        setBrojSjednica(getPretraga().size());
    }
    //Ovu metodu ne koristiti samo extra pažljivo
    public void obrisiRepzitorij (Repozitorij s){ 
        try {
            
            String naziv = s.getNaslov();
            String oRep = s.getOrepozitoriju();
            int i = utility.getFilesCount(Paths.get(s.getMapa_za_dokumente()));
            //Dozvoliti samo sa localhosta
            //Prvo treba obrisati dokument korisnici.xml
            //ulkoliko samo sadrži taj dokument
            if(i<2){
                boolean test = utility.brisiFile(s.getMapa_za_dokumente() + "/korisnici.xml");
                //utility.infoPoruka("Broj fileova u folderu je: " + Integer.toString(i) + Boolean.toString(test), "");
                if( utility.brisiFile(s.getMapa_za_dokumente())){
                    repozitoriji.remove(s);
                    // pretragaRepozitorija();
                    boolean flag=Sxml.smjestiUXML();
                    getSxml().smjesti(getRepozitoriji());
                    boolean zastavica = getSxml().smjestiUXML();
                    if(repozitoriji.isEmpty()){
                        if(utility.brisiFile(utility.putZaRep+"repozitoriji.xml"))
                            utility.poruka("sjednice","Nema nijednog aktivnog repozitorija u bazi!!!");
                    }
                    utility.setLog(utility.getDatumiVrijeme() + "  "  + 
                 "OBRISAN REPOZITORIJ : " + naziv + " " +
                 "OD KORISNIKA : " + korisnik1.getIme() +" "+ korisnik1.getPrezime()  + " " +                 
                 " OPIS : "+oRep + " " + 
                            "KORISNICI : " + korisniciZaLog());
                    utility.poruka("sjednice","Uspješno obrisan repozitorij");
                }
                else {
                    utility.errPoruka("Desila se greška!!!", "");
                }
            }
            else{
                utility.poruka("sjednice", "Direktorij nije prazan");
            }
        } catch (IOException ex) {
            Logger.getLogger(RepozitorijKontroler.class.getName()).log(Level.SEVERE, null, ex);
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
    private String korisniciZaLog(){
        String rez ="[ ", kor="",kraj =" ]" ;
        for(Login.login l: korisnici) kor +=l.getIme() + "  " + l.getPrezime() + " ; ";
        return rez + kor + kraj;
    }
    
    public void dodajKorisnika (login korisnik){
        boolean postoji = false;
        for(login l : korisnici) {
           if( l.getId()==korisnik.getId()) postoji=true;
        }
        if(korisnik.getId()==getId()){
            utility.infoPoruka("Vi ste član repozitorija!", "SjednicaNNV:prK");
        }
        else if(!postoji)  korisnici.add(korisnik);
         
        else {
            utility.warPoruka("Odabrani korisnik je vec odabran u repozitoriju!", "SjednicaNNV:prK");
        }
    }
    public void obrisiKorisnik(login korisnik){
        boolean remove = korisnici.remove(korisnik);
    }
    private void sendEmail(Repozitorij r){
        mailNotifikacija mail = new mailNotifikacija(); 
        ArrayList<String> lista = new ArrayList<>();
        for (int i = 0; i<korisnici.size();i++) {
            if(korisnici.get(i).getEmail()!=null){
                lista.add(korisnici.get(i).getEmail());             
            }
        }
        try{
            mail.posaljiMail(lista, 
                    utility.getPodatke().getU(), 
                    utility.getPodatke().getP(), 
                    utility.getPodatke().getK(),
                    "Dodani ste u repozitorij " +r.getNaslov() ,
                    "Sadržaj repozitorija je " + r.getOrepozitoriju());
            } catch (Exception e){
        utility.errPoruka("Nije poslan mail", "");
        }        
    }
       
    /**
     * Metoda <code>spremiPodatke</code> je zadužena da sve podatke o novom 
     * repozhitoriju pohrani te da onda uključenima u repozitorij javi 
     * da su članovi tog repozitorija     * 
     * @return
     * @throws IOException
     */
    public String spremiPodatke() throws IOException{
        Login.loginKontroler lk = new loginKontroler(); 
        Login.login k = new login();
        k=lk.getUserFromID(id);
        korisnici.add(lk.getUserFromID(id));
        XMLUser.smjesti(korisnici);
//        utility.infoPoruka(String.valueOf(korisnici.size()), "");
//        mailNotifikacija mail = new mailNotifikacija();        
        noviRepozitorij.setMapa_za_dokumente(utility.putZaRep+noviRepozitorij.getNaslov());
        if(dodajRepozitorij(noviRepozitorij) && 
            XMLUser.smjestiUXML(noviRepozitorij.getMapa_za_dokumente()) ){
//            sendEmail(noviRepozitorij);
//          utility.poruka("SjednicaNNV:Unos", "UspjeĹˇno kreiran repositorij");
//            ArrayList<String> lista = new ArrayList<>();
//                for (int i = 0; i<korisnici.size();i++) {
//                    if(korisnici.get(i).getEmail()!=null){
//                    lista.add(korisnici.get(i).getEmail());             
//                    }
//                }
//                 try{
//                    mail.posaljiMail(lista, 
//                            utility.getPodatke().getU(), 
//                            utility.getPodatke().getP(), 
//                            utility.getPodatke().getK(),
//                            "Dodani ste u repozitorij " +noviRepozitorij.getNaslov() ,
//                            "Sadržaj repozitorija je " + noviRepozitorij.getOrepozitoriju());
//                    } catch (Exception e){
//                utility.errPoruka("Nije poslan mail", "");
//                }
            
            
            utility.setLog(utility.getDatumiVrijeme() + "  "  + 
                 "KREIRAN REPOZITORIJ : " + noviRepozitorij.getNaslov() + " " +
                 "OD KORISNIKA : " + k.getIme() +" "+k.getPrezime()  + " " +                 
                 " OPIS : "+ noviRepozitorij.getOrepozitoriju() + " " +
                    "KORISNICI : " + korisniciZaLog());
            utility.infoPoruka("Uspješno kreiran repozitorij!", "");
            korisnici.clear();
           // noviRepozitorij.setOrepozitoriju("");            
            noviRepozitorij = new Repozitorij();
            return "/repozitorij/mojiRepozitoriji";
        } else {
//             utility.poruka("SjednicaNNV:Unos", "Desila se greska");
                utility.errPoruka("Desila se greška prilikom kreiranja novog repozitorija!", "");
                return null;
        }      
       
        
    }
    public void myListenerRep (ActionEvent event){         
        try{
            int i= (int) event.getComponent().getAttributes().get("idKorisnik");            
            setIdK(i);            
           // postaviKorisnika(getIdK());
        } catch (Exception e){} 
     }
     public void postaviKorisnika(int i){
         Login.loginKontroler lk = new loginKontroler();
//         utility.infoPoruka("testiram u PM + " + String.valueOf(i), "");
         korisnik1 = lk.getUserFromID(i);
         dodajKorisnika(korisnik1);
            if (korisnik1 == null) utility.errPoruka("Korisnik == null greška", "");
     }
    public void ucitajKorisnike(){
        try {
            setKorisnici(XMLUser.procitajIzXMLa(getSelektovaniRepozitorij().getMapa_za_dokumente()));
        } catch (JAXBException ex) {
           
        }
    } 
    
    public void mojiRepozitoriji() throws JAXBException{        
        Pr1 = null;
        getMojiRep().clear();
         for (Repozitorij r : getRepozitoriji()){
             setKorisnici(XMLUser.procitajIzXMLa(r.getMapa_za_dokumente() ));
             for(login l: getKorisnici()){                 
                 if(l.getId()==getIdK()) getMojiRep().add(r);
             }
         }         
         Pr1 = new ListDataModel<>(getMojiRep());         
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
    public String getImeKorisnika() {return imeKorisnika;}
    public void setImeKorisnika(String imeKorisnika) { this.imeKorisnika = imeKorisnika; }   
    public int getUkupniBroj() { return ukupniBroj; }
    public void setUkupniBroj(int ukupniBroj) { this.ukupniBroj = ukupniBroj; }
    public ListDataModel<Repozitorij> getPr() { return Pr; }
    public void setPr(ListDataModel<Repozitorij> Pr) { this.Pr = Pr; }
    public ArrayList<Repozitorij> getMojiRep() { return mojiRep;}
    public void setMojiRep(ArrayList<Repozitorij> mojiRep) { this.mojiRep = mojiRep; }
    public int getIdK() {return idK; }
    public void setIdK(int idK) { this.idK = idK; }
    public ListDataModel<Repozitorij> getPr1() {return Pr1; }
    public void setPr1(ListDataModel<Repozitorij> Pr1) {this.Pr1 = Pr1;}
    public int getId() {     return id;   }
    public void setId(int id) {    this.id = id; }
}


