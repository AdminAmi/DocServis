/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repozitorij;

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
 * @author amel
 */
public class zaXMLUser {
    private String greska ="";
    
    private loginOmotac li = new loginOmotac();
            
/*  koristen materijal sa stranice  
    http://howtodoinjava.com/jaxb/jaxb-exmaple-marshalling-and-unmarshalling-list-or-set-of-objects/
*/

    public zaXMLUser() {
    }
    
    
    public void smjesti(ArrayList<login> korisnik){
        li.setKorisnici(new ArrayList<login>());
        for (login o:korisnik) li.getKorisnici().add(o);
    }
    
    
    public  boolean smjestiUXML(String path ) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(loginOmotac.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller(); 
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
            //Ovaj dio je samo radi testa ispis u konzoli
            //jaxbMarshaller.marshal(li, System.out);    
            //Upisuje u file iz liste
            jaxbMarshaller.marshal(li, new File(path + "/korisnici.xml"));
            return true;
        } catch(JAXBException ex){
            
            utility.poruka("SjednicaNNV:Unos", ex.getMessage() +"Neznam");
           
            return false;
        }
    }
    
    public ArrayList<login> procitajIzXMLa(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(loginOmotac.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();     
        //Ucitava iz xml filea
        loginOmotac emps = (loginOmotac) 
            jaxbUnmarshaller.unmarshal( new File(path + "/korisnici.xml") );
        
        // Ispis iz xml-a na konzolu
        //for(Osoba os : emps.getListaOsoba()){System.out.println(os);}    
        return (ArrayList<login>) emps.getKorisnici();
    }

    public String getGreska() {
        return greska;
    }

    public void setGreska(String greska) {
        this.greska = greska;
    }
    
}
