package rep;


import Login.login;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.xml.bind.JAXBException;
import korisni.utility;
import korisni.mailNotifikacija;
import nnv.Dokument;

/**
 *
 * @author ami
 */
@ManagedBean
@ViewScoped
public class DokumentKontrolerRep extends nnv.DokumentKontroler {    
    private boolean imaPristup=false;

    private  Login.login korisnik = new login();
    private ArrayList<login> korisnici = new ArrayList<login>();
    private  zaXMLUser xml = new zaXMLUser();
    mailNotifikacija mail = new mailNotifikacija();

    public DokumentKontrolerRep() {
    super();}
    
    
    public void postaviZastavicu(){
        try {
            try{
            getDokumenti().clear();            
            this.setDokumenti(DXML.procitajIzXMLa(getPath()));
            this.setPr(new ListDataModel<>(this.getDokumenti()));                
            } catch (Exception e){}
            korisnici = xml.procitajIzXMLa(getPath());           
            for (login a:korisnici){              
                if (getId()==a.getId()){
                    setImaPristup(true);
                }
                }  
        } catch (JAXBException ex) {        
        }
        
    }
    public void ucitajDokumenteRep(){
//        try {
//            getDokumenti().clear();
//            this.setDokumenti(DXML.procitajIzXMLa(getPath()));
//            korisnici = xml.procitajIzXMLa(getPath());
//            utility.poruka("greska", "Duzina liste korisnici:" + String.valueOf(korisnici.size()));
//            for (login a:korisnici){
//                utility.poruka("greska", "Velicina za provjeru" + String.valueOf(getId()));
//                utility.poruka("greska", "Vrijednost iz liste" + String.valueOf(a.getId()));
//                if (getId()==a.getId()){
//                    setImaPristup(true);
//                    utility.poruka("greska", "Unutar desio se pogodak");
//                }
//                }  
//        } catch (JAXBException ex) {        
//        }
    }
     public void myListenerRep (ActionEvent event){
     try{
         String ime ; 
        int i= (int) event.getComponent().getAttributes().get("korisnickiID");
        setId(id);
        } catch (Exception e){} 
     }
     //ovdje za slanje maila radi sve 
     public void snimiStavku() throws IOException{
         String svimailovi="";
         this.snimi();          
         utility.setLog(utility.getDatumiVrijeme() + "  "  + 
                 "REPOZITORIJ : " + getPath() + " " +
                 "KORISNIK : " + getUser() + " " +
                 "AKCIJA : UNOS DOKUMENTA : " + 
                 getUnos().getNazivDatoteke()+
                 " OPIS : "+ getDokumenti().get(getDokumenti().size()-1).getNaziv() );
         /* Netreba mi dok testiram slanje maila
         
         ArrayList<String> lista = new ArrayList<>();
         for (int i = 0; i<korisnici.size();i++) {
             if(korisnici.get(i).getEmail()!=null){
             lista.add(korisnici.get(i).getEmail());
             svimailovi += " " + lista.get(i);
             }
         }
         //lista.add("amel.dzanic@gmail.com");
         utility.infoPoruka("Broj korisnika za slanje: " + lista.size() + "mailovi su :" + svimailovi, "");
         
         try{
         mail.posaljiMail(lista, 
                 utility.getPodatke().getU(),
                 utility.getPodatke().getP(), 
                 utility.getPodatke().getK(), 
                 "Novi sadržaj u repozitoriju: " +getNazivRepozitorija() , 
                 "Sadržaj u repozitoriju je postavio" + getUser()+"\nDatoteka : " + getUnos().getNazivDatoteke());
         } catch (Exception e){
             utility.errPoruka("Nije poslan mail", "");
         }
         */
     }
     
     public void ObrisiRepDoc(Dokument d) throws IOException{
        boolean a = false;
        int br=0,index=-1;
        getDokumenti().clear(); 
        ucitajDokumenteZaAkciju();
        String doc = d.getNazivDatoteke();
        String opis = d.getNaziv();
        String pathFile=getPath()+"/"+d.getNazivDatoteke();
        String pathXML =getPath()+"/";
        
        if (utility.brisiFile(pathFile)){
            for (int j=0;j<getDokumentiNewDel().size();j++){
                if(getDokumentiNewDel().get(j).getId()==d.getId()) index=j; 
            }
            getDokumentiNewDel().remove(index);            
            DXML.smjesti(getDokumentiNewDel());
            boolean b= DXML.smjestiUXML(pathXML);           
            
            if (getDokumentiNewDel().isEmpty()) {
                br++;
                if(utility.brisiFile(pathXML+"dokumenti.xml") )
                    a=true;                                 
                    
            }
            if (br==0 && b) {
                utility.infoPoruka( "Uspješno obrisana datoteka","AAA");
                } else if((b && a) && (br>0) ){
                utility.infoPoruka( "Uspješno obrisana datoteka","");
                utility.infoPoruka( "Repozitorij više nema radnih dokumenata!","");
                } else {
                utility.errPoruka( "Desila se greška","");
                }
            }
            getDokumentiNewDel().clear();
             utility.setLog(utility.getDatumiVrijeme() + "  "  + 
                 "REPOZITORIJ : " + getPath() + " " +
                 "KORISNIK : " + getUser() + " " +
                 "AKCIJA : BRISANJE DOKUMENTA : " + 
                 doc + " OPIS : " + opis);
            ucitajDokumente();      
         
     }    
    

    public boolean isImaPristup() {   return imaPristup;   }
    public void setImaPristup(boolean imaPristup) { this.imaPristup = imaPristup; }  

   
}

