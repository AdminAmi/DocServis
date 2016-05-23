/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nnv;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;
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
    private String path,user;
    private  SjednicaKontroler SK ;
    private Login.loginKontroler LK;
    private DokumentXML DXML = new DokumentXML();
            
    private Part datoteka;    

    public DokumentKontroler() {
    }
    
     public int generateId(){
        int  temp=-1;  
        if(dokumenti.isEmpty()) return 0;
        for (Dokument a1 : dokumenti) {        
            if (a1.getId()>temp) temp=a1.getId();
        }
    return temp+1;    
    }
     
    public boolean dodajDokument(Dokument d){
        
        int i = dokumenti.size();
        Dokument doc = new Dokument(d.getNaziv(), d.getNazivDatoteke(),user , d.getDatumKreiranja());
        doc.setId(generateId());
        this.dokumenti.add(doc);
        DXML.smjesti(dokumenti);
        return (DXML.smjestiUXML(path+"/")
                && i!=dokumenti.size());
    }
    
    public void myListener (ActionEvent event){
        path = (String)event.getComponent().getAttributes().get("path");
        user = (String)event.getComponent().getAttributes().get("user");
    }
    
    
    
     public void snimi(){
        if(unos == null) {
            //utility.poruka("UnosDokumenta:btnSnimiDokument", "Nije došlo do inicijalizacije objekta");
            
        }
        if(getDatoteka()!=null){
            unos.setNazivDatoteke(getDatoteka().getSubmittedFileName());
            try (InputStream input = getDatoteka().getInputStream()) {
                Files.copy(input, new File(path, 
                        getDatoteka().getSubmittedFileName()).toPath());
                if(dodajDokument(unos)) {
                //reset();
                utility.poruka("UnosDokumenta:btnSnimiDokument", "Uspješan unos Projekta");
            
            
        }
            }
            catch (IOException e) {
                utility.poruka("UnosDokumenta:btnSnimiDokument", "Problem pri prenosu datoteke");
                //projekatUnos.setProjectPath("");  
                // Show faces message?
            }
        }
       
        
    }

    /**
     * @return the unos
     */
    public Dokument getUnos() {
        return unos;
    }

    /**
     * @param unos the unos to set
     */
    public void setUnos(Dokument unos) {
        this.unos = unos;
    }

    /**
     * @return the selektovani
     */
    public Dokument getSelektovani() {
        return selektovani;
    }

    /**
     * @param selektovani the selektovani to set
     */
    public void setSelektovani(Dokument selektovani) {
        this.selektovani = selektovani;
    }

    /**
     * @return the selektovaniID
     */
    public int getSelektovaniID() {
        return selektovaniID;
    }

    /**
     * @param selektovaniID the selektovaniID to set
     */
    public void setSelektovaniID(int selektovaniID) {
        this.selektovaniID = selektovaniID;
    }

    /**
     * @return the datoteka
     */
    public Part getDatoteka() {
        return datoteka;
    }

    /**
     * @param datoteka the datoteka to set
     */
    public void setDatoteka(Part datoteka) {
        this.datoteka = datoteka;
    }
    
    
}
