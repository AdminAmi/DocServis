/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repozitorij;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ami
 */
@XmlRootElement(name = "repozitoriji")
@XmlAccessorType (XmlAccessType.FIELD)
public class RepozitorijOmotac {
    @XmlElement(name = "repozitorij")
    private List <Repozitorij> repozitoriji = null;
    public List <Repozitorij> getRepozitoriji() { return repozitoriji; }
    public void setRepozitoriji(List <Repozitorij> repozitoriji) { this.repozitoriji = repozitoriji; }   
    
}
