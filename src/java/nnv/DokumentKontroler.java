/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nnv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
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
    private Dokument unos = new Dokument();
    private Dokument selektovani;
    private int selektovaniID;
    private String path,user, imeFajla;
    protected int id;
    protected DokumentXML DXML = new DokumentXML();
            
    private Part datoteka;    

    public DokumentKontroler() { }
    public void ucitajDokumente(){
        try {
            getDokumenti().clear();
            this.setDokumenti(DXML.procitajIzXMLa(getPath()));           
        } catch (JAXBException ex) {        
        }
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
        
       // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");       
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        int i= getDokumenti().size();
        Dokument doc = new Dokument(d.getNaziv(), d.getNazivDatoteke(),user , date);
        doc.setId(generateId());
        //getDokumenti().clear();
        //this.getDokumenti().add(doc);
        ucitajDokumenteZaAkciju();
        utility.poruka("doc",String.valueOf(getDokumentiNewDel().size()) );
        getDokumentiNewDel().add(doc);
        utility.poruka("doc",String.valueOf(getDokumentiNewDel().size()) );
        DXML.smjesti(getDokumentiNewDel());
        boolean zastavica= DXML.smjestiUXML(getPath()+"/") && i!=getDokumenti().size();
        getDokumentiNewDel().clear();
        ucitajDokumente();        
        return zastavica;
    }
    
    public void myListener (ActionEvent event){
        setPath((String)event.getComponent().getAttributes().get("path"));       
        user = (String)event.getComponent().getAttributes().get("user");
        user += " " + (String)event.getComponent().getAttributes().get("user2");
    }    
    public void pathListener (ActionEvent event){       
        imeFajla = (String)event.getComponent().getAttributes().get("pathDoc");       
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
                utility.poruka("UnosDokumenta:btnSnimiDokument", "Uspješan unos dokumenta!");
            
            
            }
            }
            catch (IOException e) {
                utility.poruka("UnosDokumenta:btnSnimiDokument", "Problem pri prenosu datoteke");
                //projekatUnos.setProjectPath("");  
                // Show faces message?
            }
            }
        }
        
       
        
    }
     
    public void download(Dokument d ) {
        File file = new File(getPath()+"/"+d.getNazivDatoteke());
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
    /*private void obrisiElement(ArrayList<Dokument> array, int id) {
        for (Dokument d: array){
            if (d.getId()==id)
        }
        
    }*/

    public void obrisi(Dokument d){
        int index=-1;
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
            utility.poruka("doc", "Uspješno obrisana datoteka");
            if (getDokumentiNewDel().isEmpty()) {
                if(utility.brisiFile(pathXML+"dokumenti.xml"))                 
                utility.poruka("doc", "Sjednica više nema radnih dokumenata!");
            }
            getDokumentiNewDel().clear();
            ucitajDokumente();
            
        }
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<Dokument> getDokumentiNewDel() {
        return dokumentiNewDel;
    }

    public void setDokumentiNewDel(ArrayList<Dokument> dokumentiNewDel) {
        this.dokumentiNewDel = dokumentiNewDel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
