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
@XmlRootElement(name = "dokumenti")
@XmlAccessorType (XmlAccessType.FIELD)
public class DokumentOmotac {
    @XmlElement(name = "sjednica")
    private List <Dokument> dokumenti = null;

    /**
     * @return the dokumenti
     */
    public List <Dokument> getDokumenti() {
        return dokumenti;
    }

    /**
     * @param dokumenti the dokumenti to set
     */
    public void setDokumenti(List <Dokument> dokumenti) {
        this.dokumenti = dokumenti;
    }
    
    
}
