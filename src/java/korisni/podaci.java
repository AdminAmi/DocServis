/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisni;
import java.util.ArrayList;
import zaMail.*;

/**
 *
 * @author ami
 */
public class podaci {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Podaci p = new Podaci("infodocservis@gmail.com", "infodocservis", "");
        zaXML xml = new zaXML();
        ArrayList <Podaci> pod = new ArrayList();
        pod.add(p);
        xml.smjesti(pod);
        if (xml.smjestiUXML()){
            System.out.println("Uspješan unos!");
        }
        System.out.println(utility.getPodatke().toString());
        
        
    }
    
}
