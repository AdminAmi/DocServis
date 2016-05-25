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
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
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
public class DokumentKontroler {
    private  ArrayList<Dokument> dokumenti = new ArrayList();
    private Dokument unos = new Dokument();
    private Dokument selektovani;
    private int selektovaniID;
    private String path,user, imeFajla;   
    private DokumentXML DXML = new DokumentXML();
            
    private Part datoteka;    

    public DokumentKontroler() { 
        try{
            if(dokumenti.isEmpty()) this.setDokumenti(DXML.procitajIzXMLa(path));
        }  catch (JAXBException ex) {}
    }
    public void ucitajDokumente(){
        try {
            this.setDokumenti(DXML.procitajIzXMLa(getPath()));
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Date date = new Date();       
        int i= getDokumenti().size();
        Dokument doc = new Dokument(d.getNaziv(), d.getNazivDatoteke(),user , date);
        doc.setId(generateId());
        this.getDokumenti().add(doc);
        DXML.smjesti(getDokumenti());
        return (DXML.smjestiUXML(getPath()+"/")
                && i!=getDokumenti().size());
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

    public void obrisi(Dokument d){
        String pathFile=getPath()+"/"+d.getNazivDatoteke();
        String pathXML =getPath()+"/";
        if (utility.brisiFile(pathFile)){             
            dokumenti.remove(d);
            DXML.smjesti(getDokumenti());
            boolean b= DXML.smjestiUXML(pathXML);
            utility.poruka("doc", "Uspješno obrisana datoteka");
            if (dokumenti.isEmpty()) {
                if(utility.brisiFile(pathXML+"dokumenti.xml"))                 
                utility.poruka("doc", "Sjednica više nema radnih dokumenata!");
            }
            
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
    
    
}
