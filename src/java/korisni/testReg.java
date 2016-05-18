/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisni;

import Login.login;
import Login.loginKontroler;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author amel
 */
public class testReg {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Login.loginKontroler lk = new loginKontroler();
    
        String oldPass ="a", pass="a" , newPass ="R";
        login userFromID = lk.getUserFromID(2);
       
//        System.out.println(Objects.equals(userFromID.getPass(), utility.sha1(oldPass)));
//        System.out.println(userFromID.getPass());
//        System.out.println(utility.sha1(oldPass));
        
        System.out.println(userFromID.getPass().equals(utility.sha1(oldPass)));
        System.out.println(userFromID.getPass());
        System.out.println(utility.sha1(oldPass));        
        
    }
}
