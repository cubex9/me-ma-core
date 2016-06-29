/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.aop;

import java.lang.reflect.Method;

/**
 * invokace methody vola tyto funkce
 *
 * @author kubasek
 */
public interface Aspect {

    /**
     * muze byt vazano primo na objekt
     *
     * @return
     */
    Object getObject();

    /**
     * pro ktere interfacy se bude metoda volat
     *
     * @return
     */
    Class<?>[] getInterfaces();

    /**
     * vraci identifikator eventu, resp. pri ktere metode a objektu se ma volat, muze byt treba .*
     *
     * @return
     */
    String getIdentificator();

    /**
     * hledac
     *
     * @param o
     * @param m
     * @return
     */
    boolean isIt(Object o, Method m);

    /**
     * vola se pred zavolanim metody
     *
     * @param o target object ktere patri volana metoda
     * @param args argumenty metody
     * @return vraci vstupni parametry, timto se daji pozmenit, protoze az pote co se zavola before,
     * vola se invokace vlastni metody
     */
    Object[] before(Object o, Object... args);

    /**
     * vola se po zavolani metody
     *
     * @param o target object ktere patri volana metoda
     * @param result
     * @param args argumenty metody
     * @return result puvodni metody muze byt timto zmeny
     */
    Object after(Object o, Object result, Object... args);

    /**
     * child call, pri volani this aspectu se nevola before and after, ale vytvori se novy aspect,
     * ten kdyz se zavola, vola se s objektem this a nasledne po probehnuti je znicen
     *
     * @param child
     */
    void childCall(Aspect child);

    /**
     * getChild call
     *
     * @return
     */
    Aspect getChildCall();

    /**
     * dite musi znat sveho rodice no
     *
     * @return
     */
    Aspect getParrent();

    /**
     *
     * @param parrent
     */
    void setParrent(Aspect parrent);

    /**
     * je dite ?
     *
     * @return
     */
    boolean isChild();

    /**
     * nastavi parrent object
     *
     * @param parrent
     */
    void setParrentObject(Object parrent);

    /**
     * vrati parrent object
     *
     * @return
     */
    Object getParrentObject();
}
