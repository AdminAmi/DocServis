/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rep;

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
public class RepozitorijXML {
    
    private RepozitorijOmotac li = new RepozitorijOmotac();
    
     public void smjesti(ArrayList<Repozitorij> sjednica){
        li.setRepozitoriji(new ArrayList<Repozitorij>());
        for (Repozitorij o:sjednica) li.getRepozitoriji().add(o);
    }
    
    
    public  boolean smjestiUXML( ) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RepozitorijOmotac.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller(); 
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
            //Ovaj dio je samo radi testa ispis u konzoli
            //jaxbMarshaller.marshal(li, System.out);    
            //Upisuje u file iz liste
            jaxbMarshaller.marshal(li, new File(utility.putZaRep + "repozitoriji.xml"));
            return true;
        } catch(JAXBException ex){
            System.err.println(ex);
            return false;
        }
    }
    
    public ArrayList<Repozitorij> procitajIzXMLa() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RepozitorijOmotac.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();     
        //Ucitava iz xml filea
        RepozitorijOmotac emps = (RepozitorijOmotac) 
            jaxbUnmarshaller.unmarshal( new File(utility.putZaRep + "repozitoriji.xml") );
        
        // Ispis iz xml-a na konzolu
        //for(Osoba os : emps.getListaOsoba()){System.out.println(os);}    
        return (ArrayList<Repozitorij>) emps.getRepozitoriji();
    }
    
}
