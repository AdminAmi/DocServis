/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisni;

//import kontroler.ProjekatKontroler;

import projekti.ProjekatBean;
import projekti.ProjekatKontroler;
import projekti.zaXML;


/**
 *
 * @author amel
 */
public class testProjekat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProjekatKontroler pk = new ProjekatKontroler();
        zaXML xml = new zaXML();
        System.out.println(pk.getProjekti().size());
        ProjekatBean pb = new ProjekatBean();
       // System.out.println(pk.generateId());
       // pb.setId(pk.generateId());
        pb.setProjectTitle("Testiram");
        //pk.getProjekti().add(pb);
        System.out.println(pk.dodajProjekat(pb));
//        xml.smjesti(pk.getProjekti());
//        System.out.println(xml.smjestiUXML());
//         TODO code application logic here
    }
    
}
