
package repozitorij;

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
public class DokumentXML {
    private DokumentOmotac li = new DokumentOmotac();
    
     public void smjesti(ArrayList<Dokument> dokument){
        li.setDokumenti(new ArrayList<Dokument>());
        for (Dokument o:dokument) li.getDokumenti().add(o);
    }
    
    
    public  boolean smjestiUXML(String path ) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(DokumentOmotac.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller(); 
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
           jaxbMarshaller.marshal(li, new File(path+"/dokumenti.xml"));
            return true;
        } catch(JAXBException ex){
            System.err.println(ex);
            return false;
        }
    }
    
    public ArrayList<Dokument> procitajIzXMLa(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(DokumentOmotac.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        DokumentOmotac emps = (DokumentOmotac) 
            jaxbUnmarshaller.unmarshal( new File(path + "/dokumenti.xml") );
        return (ArrayList<Dokument>) emps.getDokumenti();
    }
    
    
}
