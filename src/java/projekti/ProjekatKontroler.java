/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author amel
 */
public class ProjekatKontroler {
    private zaXML xml = new zaXML();
    private ArrayList<ProjekatBean> projekti = new ArrayList<>();
    private ArrayList<ProjekatBean> pretraga = new ArrayList<>();
    private ProjekatBean projekatUnos = new ProjekatBean();
    private ListDataModel<ProjekatBean> Pr ;
    private ListDataModel<ProjekatBean> Pr1;//za pretragu
    
    
    
    public ProjekatKontroler() {
         try {
            if(projekti.isEmpty()) this.setProjekti(xml.procitajIzXMLa());
            Pr = new ListDataModel<>(projekti);            
        } catch (JAXBException ex) { }
    }
    
    public int generateId(){
        int  temp=-1;  
        if(projekti.isEmpty()) return 0;
        for (ProjekatBean a1 : projekti) {        
            if (a1.getId()>temp) temp=a1.getId();
        }
    return temp+1;    
    }
    
    /**
     *
     * @param pb
     * @return
     */
    public boolean dodajProjekat(ProjekatBean pb){
//        ProjekatBean smjesti = new ProjekatBean();
        int i= projekti.size();  
       // int id=this.generateId();
        pb.setId(generateId());
//        smjesti.setAmmountOfDonors(pb.getAmmountOfDonors());
//        smjesti.setCost(pb.getCost());
//        smjesti.setDonorsOfProject(pb.getDonorsOfProject());
//        smjesti.setFileName(pb.getFileName());
//        smjesti.setLocation(pb.getLocation());
//        smjesti.setParticipants(pb.getParticipants());
//        smjesti.setProjectDuration(pb.getProjectDuration());
//        smjesti.setProjectPath(pb.getProjectPath());
//        smjesti.setProjectTitle(pb.getProjectTitle());
//        smjesti.setRoleInProject(pb.getRoleInProject());
        this.projekti.add(pb);
        xml.smjesti(projekti);        
        return (xml.smjestiUXML()&& i!=projekti.size());
    }
    
    /**
     *
     * @param selektovaniProjekat 
     * @param id 
     * @return
     */
    public boolean azuirirajProjekat(ProjekatBean selektovaniProjekat, int id){
        projekti.get(id).setAmmountOfDonors(selektovaniProjekat.getAmmountOfDonors());
        projekti.get(id).setCost(selektovaniProjekat.getCost());
        projekti.get(id).setDonorsOfProject(selektovaniProjekat.getDonorsOfProject());
        projekti.get(id).setFileName(selektovaniProjekat.getFileName());
        projekti.get(id).setLocation(selektovaniProjekat.getLocation());
        projekti.get(id).setObjectives(selektovaniProjekat.getObjectives());
        projekti.get(id).setParticipants(selektovaniProjekat.getParticipants());
        projekti.get(id).setProjectDuration(selektovaniProjekat.getProjectDuration());
        projekti.get(id).setProjectPath(selektovaniProjekat.getProjectPath());
        projekti.get(id).setProjectTitle(selektovaniProjekat.getProjectTitle());
        projekti.get(id).setRoleInProject(selektovaniProjekat.getRoleInProject());
        xml.smjesti(projekti);        
        return xml.smjestiUXML();       
        
    }
    
    public ProjekatBean vratiProjektaPoID(int id){
        if(!projekti.isEmpty()){
            for(ProjekatBean selektovan: projekti){if (selektovan.getId()==id) return selektovan;}
        }
        return null;
    }
    
   
    public boolean obrisiProjekat(ProjekatBean k){
        int pogodak=-1;
        for (ProjekatBean a1 : projekti) {        
            if (a1.getId()==k.getId()) pogodak=a1.getId();        
        }
        if(pogodak!=-1)projekti.remove(pogodak); 
        xml.smjesti(projekti);        
        return (pogodak!=-1 && xml.smjestiUXML());
}
     public void nadjiProjekat(String naziv){
        for (ProjekatBean a1 : projekti) {        
            if (a1.getFileName().contains(naziv)) getPretraga().add(a1);        
        }
        
}
     public void izlistaj(){
        for (ProjekatBean a1 : projekti) {        
            getPretraga().add(a1);
        }
     }
    
    
    
//getter  & setter
    public ArrayList<ProjekatBean> getProjekti() {   return projekti; }
    public void setProjekti(ArrayList<ProjekatBean> projekti) {   this.projekti = projekti; }
    public ArrayList<ProjekatBean> getPretraga() {  return pretraga; }
    public void setPretraga(ArrayList<ProjekatBean> pretraga) {   this.pretraga = pretraga;  }
    public ProjekatBean getProjekatUnos() {    return projekatUnos;  }
    public void setProjekatUnos(ProjekatBean projekatUnos) {   this.projekatUnos = projekatUnos; }
    public ListDataModel<ProjekatBean> getPr() {   return Pr;  }
    public void setPr(ListDataModel<ProjekatBean> Pr) { this.Pr = Pr; }

    /**
     * @return the Pr1
     */
    public ListDataModel<ProjekatBean> getPr1() {
        return Pr1;
    }

    /**
     * @param Pr1 the Pr1 to set
     */
    public void setPr1(ListDataModel<ProjekatBean> Pr1) {
        this.Pr1 = Pr1;
    }

   
     
    
}
