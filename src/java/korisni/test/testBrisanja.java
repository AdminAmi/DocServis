/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisni.test;

import java.io.IOException;
import java.util.Date;
import korisni.utility;

/**
 *
 * @author ami
 */
public class testBrisanja {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Date d = new Date();
        String dat = utility.getDatumiVrijeme();
        d = utility.getVrijemeFromString(dat, 0);
        System.out.println(utility.getMonthInt(d));
        System.out.println(d.toString());
    }
    
}
