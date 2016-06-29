/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.aop;

/**
 * trida ktera implementuje Aop, by mela implementovat toto rozhrani
 *
 * @author kubasek
 */
public interface AopClass {

    /**
     * nastavi ze je objekt delany jako Aop
     *
     * @return
     */
    void aopInstance();

    /**
     * je aop ?
     *
     * @return
     */
    boolean isAop();
}
