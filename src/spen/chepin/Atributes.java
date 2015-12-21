/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spen.chepin;

/**
 *
 * @author 123
 */
public class Atributes {
    public boolean P,M,C,T,initial;
    public int count;
    
    public Atributes(boolean P)
    {
        M=false;
        this.P=P;
        C=false;
        T=true;
        count=0;
        initial=false;
    }
}
