/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import projekti.ProjekatBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amel
 */
@XmlRootElement(name = "projekti")
@XmlAccessorType (XmlAccessType.FIELD)
public class projekatOmotac {    
    @XmlElement(name = "projekat")
    private List <ProjekatBean> projekti = null;

    /**
     * @return the projekti
     */
    public List <ProjekatBean> getProjekti() {
        return projekti;
    }

    /**
     * @param projekti the projekti to set
     */
    public void setProjekti(List <ProjekatBean> projekti) {
        this.projekti = projekti;
    }    
}
