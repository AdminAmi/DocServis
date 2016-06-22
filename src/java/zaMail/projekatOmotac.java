/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zaMail;

import java.util.List;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amel
 */
@XmlRootElement(name = "podaci")
@XmlAccessorType (XmlAccessType.FIELD)
public class projekatOmotac {    
    @XmlElement(name = "podatak")
    private List <Podaci> projekti = null;

    /**
     * @return the projekti
     */
    public List <Podaci> getProjekti() {
        return projekti;
    }

    /**
     * @param projekti the projekti to set
     */
    public void setProjekti(List <Podaci> projekti) {
        this.projekti = projekti;
    }    
}
