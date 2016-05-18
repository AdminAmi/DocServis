/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import projekti.projekatOmotac;
import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import projekti.ProjekatBean;
import korisni.utility;

/**
 *
 * @author amel
 */
public class zaXML {
    
    private projekatOmotac li = new projekatOmotac();
            
/*  koristen materijal sa stranice  
    http://howtodoinjava.com/jaxb/jaxb-exmaple-marshalling-and-unmarshalling-list-or-set-of-objects/
*/

    public zaXML() {
    }
    
    
    public void smjesti(ArrayList<ProjekatBean> projekat){
        li.setProjekti(new ArrayList<ProjekatBean>());
        for (ProjekatBean o:projekat) li.getProjekti().add(o);
    }
    
    
    public  boolean smjestiUXML( ) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(projekatOmotac.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller(); 
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
            //Ovaj dio je samo radi testa ispis u konzoli
            //jaxbMarshaller.marshal(li, System.out);    
            //Upisuje u file iz liste
            jaxbMarshaller.marshal(li, new File(utility.putZaXML + "baza.xml"));
            return true;
        } catch(JAXBException ex){
            System.out.println(ex);
            return false;
        }
    }
    
    public ArrayList<ProjekatBean> procitajIzXMLa() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(projekatOmotac.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();     
        //Ucitava iz xml filea
        projekatOmotac emps = (projekatOmotac) 
            jaxbUnmarshaller.unmarshal( new File(utility.putZaXML + "baza.xml") );
        
        // Ispis iz xml-a na konzolu
        //for(Osoba os : emps.getListaOsoba()){System.out.println(os);}    
        return (ArrayList<ProjekatBean>) emps.getProjekti();
    }
    
}
