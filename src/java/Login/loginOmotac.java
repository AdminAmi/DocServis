/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amel
 */
@XmlRootElement(name = "korisnici")
@XmlAccessorType (XmlAccessType.FIELD)
public class loginOmotac {    
    @XmlElement(name = "korisnik")
    private List <login> korisnici = null;

    /**
     * @return the korisnici
     */
    public List <login> getKorisnici() {
        return korisnici;
    }

    /**
     * @param korisnici the korisnici to set
     */
    public void setKorisnici(List <login> korisnici) {
        this.korisnici = korisnici;
    }

   
}
