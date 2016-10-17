/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nnv;

import Login.login;
import Login.zaXML;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import korisni.mailNotifikacija;
import korisni.utility;

/**
 *
 * @author ami
 */
@ManagedBean
@ViewScoped
//@RequestScoped
public class DokumentKontroler {
    protected  ArrayList<Dokument> dokumenti = new ArrayList();
    protected  ArrayList<Dokument> dokumentiNewDel = new ArrayList();
    private ArrayList<login> korisnici = new ArrayList<login>();
    protected Dokument unos = new Dokument();
    private Dokument selektovani;
    private int selektovaniID, IDKor;
    private String path,user, imeFajla, nazivRepozitorija, brSjednice, datSjednice, vrSjednice;
    protected int id;    
    protected DokumentXML DXML = new DokumentXML();
    private ListDataModel<Dokument> Pr ;
    private zaXML xml = new zaXML();
            
    protected Part datoteka;    

    public DokumentKontroler() { }
    public void ucitajDokumente(){
        try {
            getDokumenti().clear();
            this.setDokumenti(DXML.procitajIzXMLa(getPath())); 
            Pr = new ListDataModel<>(dokumenti);             
        } catch (JAXBException ex) {
             //utility.errPoruka("Nije došlo do učitavanja iz baze!", "");
        }
    }
    public int getBrojac (){
        return getDokumenti().size();
    }
    public void ucitajDokumenteZaAkciju(){
        try {
            getDokumentiNewDel();
            this.setDokumentiNewDel(DXML.procitajIzXMLa(getPath()));           
        } catch (JAXBException ex) {        
        }
    }
    
    public int generateId(){
        int  temp=-1;  
        if(getDokumenti().isEmpty()) return 0;
        for (Dokument a1 : getDokumenti()) {        
            if (a1.getId()>temp) temp=a1.getId();
        }
    return temp+1;    
    }
     
    public boolean dodajDokument(Dokument d){
        String s = utility.getDatumiVrijeme();        
        int i= getDokumenti().size();
        Dokument doc = new Dokument(d.getNaziv(), d.getNazivDatoteke(), getUser(), s, IDKor);
        doc.setId(generateId());
        //getDokumenti().clear();
        //this.getDokumenti().add(doc);
        ucitajDokumenteZaAkciju();
//        utility.poruka("doc",String.valueOf(getDokumentiNewDel().size()) );
        getDokumentiNewDel().add(doc);
        snimi();
//        utility.infoPoruka(getPath()+"/", "");
//        utility.poruka("doc",String.valueOf(getDokumentiNewDel().size()) );
//        try (InputStream input = getDatoteka().getInputStream()){
//             Files.copy(input, new File(getPath()+"/", getDatoteka().getSubmittedFileName()).toPath());
//             
//        } catch (Exception e){}
        DXML.smjesti(getDokumentiNewDel());
        boolean zastavica= DXML.smjestiUXML(getPath()+"/") ;
        getDokumentiNewDel().clear();
        ucitajDokumente(); 
        if(zastavica) utility.infoPoruka("Uspješan unos dokumenta!", "");
        else utility.errPoruka("Greška pri unosu dokumenta", "");
        return zastavica;
    }
    
    public void myListener (ActionEvent event){
        setPath((String)event.getComponent().getAttributes().get("path"));       
        setUser((String)event.getComponent().getAttributes().get("user"));
        setUser(getUser() + " " + (String)event.getComponent().getAttributes().get("user2"));
        IDKor = (int)event.getComponent().getAttributes().get("idK");
        setNazivRepozitorija((String)event.getComponent().getAttributes().get("rep"));
        
    } 
    public void logListener (ActionEvent event){
        setUser((String)event.getComponent().getAttributes().get("user"));
        setUser(getUser() + " " + (String)event.getComponent().getAttributes().get("user2"));
    }
    public void pathListener (ActionEvent event){       
        imeFajla = (String)event.getComponent().getAttributes().get("pathDoc");       
    } 
    public void sjednicaListener (ActionEvent event){       
        brSjednice =(String)event.getComponent().getAttributes().get("brojSjednice");
        datSjednice = (String)utility.datum((Date)event.getComponent().getAttributes().get("datumSjednice"));
        vrSjednice = (String)event.getComponent().getAttributes().get("vrijemeSjednice");        
    } 
    public void nnvListener (ActionEvent event){
        this.myListener(event);
        this.sjednicaListener(event);
    }
   
    public void snimi(){        
        if(unos == null) {
            utility.poruka("UnosDokumenta:btnSnimiDokument", "Nije došlo do inicijalizacije objekta");
            
        } 
        if (unos.getNaziv().length()!=0){
            if(getDatoteka()!=null){
            unos.setNazivDatoteke(getDatoteka().getSubmittedFileName());
            try (InputStream input = getDatoteka().getInputStream()) {
                Files.copy(input, new File(getPath(), 
                        getDatoteka().getSubmittedFileName()).toPath());
                if(dodajDokument(unos)) {
                    
                unos.setNaziv("");
//                utility.poruka("UnosDokumenta:btnSnimiDokument", "Uspješan unos dokumenta!");
            
            
            }
               
            }
            catch (IOException e) {
//                utility.poruka("UnosDokumenta:btnSnimiDokument", "Problem pri prenosu datoteke");
                //projekatUnos.setProjectPath("");  
                // Show faces message?
            }
            }
            else {
            utility.warPoruka("Niste unijeli datoteku", "");
        }    
        }
       
        
    }
     
    public void download(Dokument d ) {
        utility.infoPoruka(getPath()+"/"+d.getNazivDatoteke(), "");
        File file = new File(getPath()+"/"+d.getNazivDatoteke());
        utility.infoPoruka(getPath()+"/"+d.getNazivDatoteke(), "");
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  

        response.setHeader("Content-Disposition", "attachment;filename=" + d.getNazivDatoteke() );  
        response.setContentLength((int) file.length());  
        ServletOutputStream out = null;  
        try {  
            FileInputStream input = new FileInputStream(file);  
            byte[] buffer = new byte[1024];  
            out = response.getOutputStream();  
            int i = 0;  
            while ((i = input.read(buffer)) != -1) {  
            out.write(buffer);  
            out.flush();  
        }  
        FacesContext.getCurrentInstance().getResponseComplete();  
        } catch (IOException err) {  
            err.printStackTrace();  
        } finally {  
        try {  
            if (out != null) {  
                out.close();  
            }  
        } catch (IOException err) {  
            err.printStackTrace();  
        }  
    } 
    }
    

    /**
     * Briše dokument i tačku sa sjednice 
     * @param d - je unos koji se briše
     * @throws IOException
     */
    public void obrisi(Dokument d) throws IOException{
        int index=-1;
        getDokumenti().clear(); 
        ucitajDokumenteZaAkciju();
        String datoteka  =  d.getNazivDatoteke();
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
//            utility.poruka("doc", "Uspješno obrisana datoteka");
            utility.infoPoruka("Uspješno obrisana datoteka!", "");
            
            if (getDokumentiNewDel().isEmpty()) {
                if(utility.brisiFile(pathXML+"dokumenti.xml")) {}                
//                utility.poruka("doc", "Sjednica više nema radnih dokumenata!");
                    utility.infoPoruka("Sjednica više nema radnih dokumenata!", "");
            }
            getDokumentiNewDel().clear();
            utility.setLog(utility.getDatumiVrijeme() + "  "  + 
                 "NNV br: " + brSjednice + " od " + datSjednice +
                 " KORISNIK : " + getUser() + " " +
                 " AKCIJA [BRISANJE DOKUMENTA] : " + datoteka +
                 " OPIS : "+ opis );
            ucitajDokumente();
            
        }
    }

    /**
     * Metoda koja šalje mail cklanovima NNV-a i obavještava ih da su 
     * materijali za datu sjednicu
     * spremni za preuzimanje na doc servisu
     */
    public void posaljiMail(){
        ucitajKorisnike();
        mailNotifikacija mail = new mailNotifikacija(); 
        ArrayList<String> lista = new ArrayList<>();
        for (int i = 0; i<getKorisnici().size();i++) {
            if(getKorisnici().get(i).getClanNNV().equals("DA") && getKorisnici().get(i).getEmail()!=null ){
                lista.add(getKorisnici().get(i).getEmail());             
            }
        }
        try{     
            
            mail.posaljiMail(lista, 
                    utility.getPodatke().getU(), 
                    utility.getPodatke().getP(), 
                    utility.getPodatke().getK(),
                    "Materijali za sjednicu NNV-a ( "+datSjednice+" - "+vrSjednice + " h )" ,
                    "<h3>Poštovani korisnici docServisa!</h3><br/>Materijali za sjednicu NNV broj: <b><i>" + 
                            brSjednice +"</i></b> koja je zakazana za: <b><i>" +
                            datSjednice+ "</i></b> sa početkom u: <b><i>"+ 
                            vrSjednice +" h</i></b>, su dostupni na servisu.<br/>LP, docSERVIS<br/><br/>"
                            +"<hr/><i><sub>Na ovaj mail ne odgovarajte - on je mašinski generisan!!! "
                            + "Za sve nejasnoće obratite se sistem administratoru!!<sub></i>");            
            utility.infoPoruka("Mail je uspješno poslan! ("+ brSjednice + "/" + datSjednice +"/" + vrSjednice+ ")","");
            
            } catch (Exception e){
        utility.errPoruka("Nije poslan mail", "");
        }        
        
    }
     public void ucitajKorisnike(){
        getKorisnici().clear();
        try {
         this.setKorisnici(xml.procitajIzXMLa());        
        } catch (JAXBException ex) { }
    }
     
     public void snimiTacku() throws IOException{
         this.snimi();          
         utility.setLog(utility.getDatumiVrijeme() + "  "  + 
                 "NNV br: " + brSjednice + " od " + datSjednice +
                 " KORISNIK : " + getUser() + " " +
                 " AKCIJA [UNOS DOKUMENTA] : " + 
                 getUnos().getNazivDatoteke()+
                 " OPIS : "+ getDokumenti().get(getDokumenti().size()-1).getNaziv() );
     }
    //GETTER I SEETER
    public Dokument getUnos() { return unos;}   
    public void setUnos(Dokument unos) {this.unos = unos;}   
    public Dokument getSelektovani() {return selektovani;}   
    public void setSelektovani(Dokument selektovani) {this.selektovani = selektovani;}
    public int getSelektovaniID() {return selektovaniID;}   
    public void setSelektovaniID(int selektovaniID) {this.selektovaniID = selektovaniID;}    
    public Part getDatoteka() {return datoteka;}    
    public void setDatoteka(Part datoteka) {this.datoteka = datoteka;}
    public ArrayList<Dokument> getDokumenti() { return dokumenti; }
    public void setDokumenti(ArrayList<Dokument> dokumenti) {this.dokumenti = dokumenti; }
    public String getPath() {   return path; }
    public void setPath(String path) {    this.path = path;  }
    public ArrayList<Dokument> getDokumentiNewDel() {    return dokumentiNewDel;  }
    public void setDokumentiNewDel(ArrayList<Dokument> dokumentiNewDel) {   this.dokumentiNewDel = dokumentiNewDel; }
    public int getId() {   return id;  }
    public void setId(int id) {  this.id = id; }
    public ListDataModel<Dokument> getPr() {  return Pr;  }
    public void setPr(ListDataModel<Dokument> Pr) {   this.Pr = Pr; }
    public String getUser() {  return user;  }
    public void setUser(String user) {  this.user = user;}
    public String getNazivRepozitorija() {  return nazivRepozitorija;  }
    public void setNazivRepozitorija(String nazivRepozitorija) {  this.nazivRepozitorija = nazivRepozitorija; }
    public ArrayList<login> getKorisnici() {   return korisnici; }
    public void setKorisnici(ArrayList<login> korisnici) {   this.korisnici = korisnici; }
  
    
    
}
