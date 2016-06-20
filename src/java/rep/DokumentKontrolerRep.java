package rep;


import Login.login;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.xml.bind.JAXBException;
import korisni.utility;
import nnv.Dokument;

/**
 *
 * @author ami
 */
@ManagedBean
@ViewScoped
public class DokumentKontrolerRep extends nnv.DokumentKontroler {
    //Treba implementirati rad sa korisnicima
    private boolean imaPristup=false;
    private int brojDoc;
    private Login.login korisnik = new login();
    private ArrayList<login> korisnici = new ArrayList<login>();
    private zaXMLUser xml = new zaXMLUser();

    public DokumentKontrolerRep() {
    super();}
    
    public void postaviZastavicu(){
        try {
            try{
            getDokumenti().clear();            
            this.setDokumenti(DXML.procitajIzXMLa(getPath()));
            this.setPr(new ListDataModel<>(this.getDokumenti()));
                setBrojDoc(getPr().getRowCount());
//             Pr = new ListDataModel<>(dokumenti);
//            utility.infoPoruka("Broj f" + Integer.toString(dokumenti.size()), "");
//             utility.infoPoruka("Broj u listdM" +Integer.toString(getPr().getRowCount()), "");
            } catch (Exception e){}
            korisnici = xml.procitajIzXMLa(getPath());
           //utility.poruka("greska", "Duzina liste korisnici:" + String.valueOf(korisnici.size()));
            for (login a:korisnici){
//                utility.errPoruka("greska","Velicina za provjeru" + String.valueOf(getId()));
//                utility.poruka("greska", "Velicina za provjeru" + String.valueOf(getId()));
//                utility.poruka("greska", "Vrijednost iz liste" + String.valueOf(a.getId()));
                if (getId()==a.getId()){
                    setImaPristup(true);
//                    utility.infoPoruka("Unutar desio se pogodak","");
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
        int i= (int) event.getComponent().getAttributes().get("korisnickiID");
        setId(id);
        } catch (Exception e){} 
     }
     
     public void ObrisiRepDoc(Dokument d){
        boolean a = false;
        int br=0,index=-1;
        getDokumenti().clear(); 
        ucitajDokumenteZaAkciju();        
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
            ucitajDokumente();
            
       
         
     }
        
    
    
    

    public boolean isImaPristup() {
        return imaPristup;
    }

    public void setImaPristup(boolean imaPristup) {
        this.imaPristup = imaPristup;
    }

    /**
     * @return the brojDoc
     */
    public int getBrojDoc() {
        return brojDoc;
    }

    /**
     * @param brojDoc the brojDoc to set
     */
    public void setBrojDoc(int brojDoc) {
        this.brojDoc = brojDoc;
    }
    
    
    
    
    
}

