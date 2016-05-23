/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nnv;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.JAXBException;
import korisni.utility;

/**
 *
 * @author ami
 */
@ManagedBean
@ViewScoped
public class SjednicaKontroler {
    private ArrayList<Sjednica> sjednice = new ArrayList<>();
    private ArrayList<Sjednica> pretraga = new ArrayList<>();
    private Sjednica sjednica = new Sjednica();
    private Sjednica novaSjednica = new Sjednica();
    private SjednicaXML Sxml = new SjednicaXML();

    public SjednicaKontroler() {
        try {
            if (sjednice.isEmpty()) this.setSjednice(Sxml.procitajIzXMLa());
        } catch (JAXBException ex) {}
    }
    
    public int generateId(){
        int  temp=-1;  
        if(sjednice.isEmpty()) return 0;
        for (Sjednica a1 : sjednice) {        
        if (a1.getId()>temp) temp=a1.getId();
        }
        return temp+1;
    }
    
    public void dodajSjednicu(){
        if(dodajSjednicu(getNovaSjednica())){}
    }
    
     public boolean dodajSjednicu(Sjednica o) {
        utility.kreirajDirektorij(utility.datumZaDirektorij(o.getDatum()));       
        int i = sjednice.size();
        Sjednica kor = new Sjednica(o.getBroj(), o.getDatum(), utility.datumZaDirektorij(o.getDatum()));
        kor.setId(generateId());
        this.sjednice.add(kor);
        Sxml.smjesti(sjednice);     
        return (i!=sjednice.size() && Sxml.smjestiUXML());
    }
    

    /**
     * @return the sjednice
     */
    public ArrayList<Sjednica> getSjednice() {
        return sjednice;
    }

    /**
     * @param sjednice the sjednice to set
     */
    public void setSjednice(ArrayList<Sjednica> sjednice) {
        this.sjednice = sjednice;
    }

    /**
     * @return the pretraga
     */
    public ArrayList<Sjednica> getPretraga() {
        return pretraga;
    }

    /**
     * @param pretraga the pretraga to set
     */
    public void setPretraga(ArrayList<Sjednica> pretraga) {
        this.pretraga = pretraga;
    }

    /**
     * @return the sjednica
     */
    public Sjednica getSjednica() {
        return sjednica;
    }

    /**
     * @param sjednica the sjednica to set
     */
    public void setSjednica(Sjednica sjednica) {
        this.sjednica = sjednica;
    }

    /**
     * @return the Sxml
     */
    public SjednicaXML getSxml() {
        return Sxml;
    }

    /**
     * @param Sxml the Sxml to set
     */
    public void setSxml(SjednicaXML Sxml) {
        this.Sxml = Sxml;
    }

    /**
     * @return the novaSjednica
     */
    public Sjednica getNovaSjednica() {
        return novaSjednica;
    }

    /**
     * @param novaSjednica the novaSjednica to set
     */
    public void setNovaSjednica(Sjednica novaSjednica) {
        this.novaSjednica = novaSjednica;
    }
    
    
    
}
