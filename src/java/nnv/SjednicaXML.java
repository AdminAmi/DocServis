/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nnv;

import Login.login;
import Login.loginOmotac;
import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import korisni.utility;

/**
 *
 * @author ami
 */
public class SjednicaXML {
    
    private SjednicaOmotac li = new SjednicaOmotac();
    
     public void smjesti(ArrayList<Sjednica> sjednica){
        li.setSjednice(new ArrayList<Sjednica>());
        for (Sjednica o:sjednica) li.getSjednice().add(o);
    }
    
    
    public  boolean smjestiUXML( ) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SjednicaOmotac.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller(); 
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
            //Ovaj dio je samo radi testa ispis u konzoli
            //jaxbMarshaller.marshal(li, System.out);    
            //Upisuje u file iz liste
            jaxbMarshaller.marshal(li, new File(utility.putZaSjednice + "sjednice.xml"));
            return true;
        } catch(JAXBException ex){
            System.err.println(ex);
            return false;
        }
    }
    
    public ArrayList<Sjednica> procitajIzXMLa() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(SjednicaOmotac.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();     
        //Ucitava iz xml filea
        SjednicaOmotac emps = (SjednicaOmotac) 
            jaxbUnmarshaller.unmarshal( new File(utility.putZaSjednice + "sjednice.xml") );
        
        // Ispis iz xml-a na konzolu
        //for(Osoba os : emps.getListaOsoba()){System.out.println(os);}    
        return (ArrayList<Sjednica>) emps.getSjednice();
    }
    
}
