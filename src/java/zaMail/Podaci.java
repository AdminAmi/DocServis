/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zaMail;

/**
 *
 * @author ami
 */
public class Podaci {
    private String k;
    private String u;
    private String p;

    public Podaci(String k, String u, String p) {
        this.k = k;
        this.u = u;
        this.p = p;
    }

    public Podaci() {
    }
    
    
    

    /**
     * @return the k
     */
    public String getK() {
        return k;
    }

    /**
     * @param k the k to set
     */
    public void setK(String k) {
        this.k = k;
    }

    /**
     * @return the u
     */
    public String getU() {
        return u;
    }

    /**
     * @param u the u to set
     */
    public void setU(String u) {
        this.u = u;
    }

    /**
     * @return the p
     */
    public String getP() {
        return p;
    }

    /**
     * @param p the p to set
     */
    public void setP(String p) {
        this.p = p;
    }
    
    @Override
    public String toString(){
        return "Mail: " + k + "User : " + u;
    }
    
    
}
