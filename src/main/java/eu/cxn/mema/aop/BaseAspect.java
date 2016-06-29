/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.aop;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.regex.Pattern;

/**
 *
 * @author kubasek
 */
public class BaseAspect implements Aspect {

    /**
     * concrete object of this event
     */
    private Object concrete;

    /**
     * tridy/interfacy pro ktere ma byt event volany
     */
    private HashSet<Class<?>> c;

    /**
     * identifikator funkce
     */
    private String identificator;

    /**
     * hledaci pattern
     */
    private Pattern patti;

    /**
     * parent call
     */
    private Aspect child;

    /**
     * parrent if ai child, ai hava
     */
    private Aspect parrent;

    /**
     * parrental object
     */
    protected Object parrentO;

    /**
     * yap
     *
     * @param identificator
     * @param c
     */
    public BaseAspect(String identificator, Class<?>... c) {
        this.identificator = identificator;
        this.c = interfacesTree(c, null);
    }

    /**
     * event se bude tykat jen metod kokretniho objektu
     *
     * @param concrete
     * @param identificator
     */
    public BaseAspect(Object concrete, String identificator) {
        this.concrete = concrete;
        this.c = interfacesTree(concrete.getClass().getInterfaces(), null);
        this.identificator = identificator;
    }

    /**
     * read, interfaces tree jelikos totis vraci akorat primo implementovane interfacy a uz ne
     * interfacy extendovane interfacem..
     *
     * @param cin
     * @param r
     * @return
     */
    public static HashSet<Class<?>> interfacesTree(Class<?>[] cin, HashSet<Class<?>> r) {

        /**
         * na zacatku je praaazdnyyy
         */
        HashSet<Class<?>> res = r == null ? new LinkedHashSet<Class<?>>() : r;
        /**
         * z kazdeho iface ziskame interfacy a dame si repete
         */
        for (Class<?> one : cin) {

            /**
             * pokud v seznamu jeste neni
             */
            if (!res.contains(one)) {
                res.add(one);
                res = interfacesTree(one.getInterfaces(), res);
            }
        }

        return res;
    }

    /**
     * nemusi byt ale muze..
     *
     * @return
     */
    @Override
    public Object getObject() {
        return concrete;
    }

    /**
     * pro ktere interfacy se bude metoda volat
     *
     * @return
     */
    @Override
    public Class<?>[] getInterfaces() {
        return null; //c.toArray();
    }

    /**
     * vraci identifikator eventu, resp. pri ktere metode a objektu se ma volat, muze byt treba .*
     *
     * @return
     */
    @Override
    public String getIdentificator() {
        return identificator;
    }

    /**
     * child call, pri volani this aspectu se nevola before and after, ale vytvori se novy aspect,
     * ten kdyz se zavola, vola se s objektem this a nasledne po probehnuti je znicen
     *
     * @param child
     */
    @Override
    public void childCall(Aspect child) {
        /**
         * provazeme rucicky, deti a rodicky
         */
        this.child = child;
        this.child.setParrent(this);
    }

    /**
     * getChild call
     */
    @Override
    public Aspect getChildCall() {
        return child;
    }

    /**
     * je dite ?
     *
     * @return
     */
    @Override
    public boolean isChild() {
        return parrent != null;
    }

    /**
     * nastavi parrent object
     *
     * @param parrent
     */
    @Override
    public void setParrentObject(Object parrent) {
        this.parrentO = parrent;
    }

    /**
     * vrati parrent object
     *
     * @return
     */
    @Override
    public Object getParrentObject() {
        return parrentO;
    }

    /**
     *
     * @return
     */
    @Override
    public Aspect getParrent() {
        return parrent;
    }

    /**
     *
     * @param parrent
     */
    @Override
    public void setParrent(Aspect parrent) {
        this.parrent = parrent;
    }

    /**
     * vola se pred zavolanim metody
     *
     * @param o target object ktere patri volana metoda
     * @param args argumenty metody
     * @return
     */
    @Override
    public Object[] before(Object o, Object... args) {
        return args;
    }

    /**
     * vola se po zavolani metody
     *
     * @param o target object ktere patri volana metoda
     * @param args argumenty metody
     * @return
     */
    @Override
    public Object after(Object o, Object result, Object... args) {
        return result;
    }

    /**
     * jestli event odpovida pravidluuum
     *
     * @param o
     * @param m
     * @return
     */
    @Override
    public boolean isIt(Object o, Method m) {

        boolean res = false;

        /**
         * porovnani konkretnich objektu objektu jako takovych
         */
        if (concrete != null) {
            res = concrete.equals(o);
        } else {
            /**
             * metoda je definovana v nejakem rozhrani
             */
            Class<?> methodInterface = m.getDeclaringClass();
            res = c.contains(methodInterface);
        }

        /**
         * druha faze, nalezeni metody
         */
        return res ? isItMethode(m) : false;
    }

    /**
     * zjisti metodu
     *
     * @param m
     * @return
     */
    public boolean isItMethode(Method m) {

        if (patti == null) {
            patti = Pattern.compile(identificator);
        }

        /**
         * najde to, pokud mozno
         */
        return patti.matcher(m.getName()).find();
    }
}
