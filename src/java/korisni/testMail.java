/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisni;

import java.util.ArrayList;



/**
 *
 * @author ami
 */
public class testMail {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         ArrayList<String> lista = new ArrayList<>();
         lista.add("amel.dzanic@gmail.com");
         lista.add("a_dzanic@hotmsail.com");
         
         mailNotifikacija m = new  mailNotifikacija();
         m.posaljiMail(lista, 
                 utility.getPodatke().getU(),
                 utility.getPodatke().getP(), 
                 utility.getPodatke().getK(), "Novi sadržaj u repozitoriju", "Testiramo");
        
    }
    
}
