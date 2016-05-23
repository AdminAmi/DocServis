/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nnv;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ami
 */
@XmlRootElement(name = "sjednice")
@XmlAccessorType (XmlAccessType.FIELD)
public class SjednicaOmotac {
    @XmlElement(name = "sjednica")
    private List <Sjednica> sjednice = null;

    /**
     * @return the sjednice
     */
    public List <Sjednica> getSjednice() {
        return sjednice;
    }

    /**
     * @param sjednice the sjednice to set
     */
    public void setSjednice(List <Sjednica> sjednice) {
        this.sjednice = sjednice;
    }
    
    
    
}
