/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Stream;

/**
 * implementace obsluhy zadanych eventu,
 *
 * @author kubasek
 */
public class AopStorage {

    /**
     * nekde musi byt vsechny ulozene
     */
    public static LinkedHashSet<Aspect> eventitiez;

    /**
     * jen jednou pro celou applikaci samozrejme
     */
    static {
        eventitiez = new LinkedHashSet<>();
    }

    /**
     * nalezne ten spravny event
     *
     * @param concrete
     * @param m
     * @return
     */
    public static Stream<Aspect> get(Object concrete, Method m) {
        List<Aspect> res = new ArrayList<>();
        // TODO: todle bude potrebovat hoooodne optimalizovat, inteligente indextation
        for (Aspect a : eventitiez) {
            if (a.isIt(concrete, m)) {
                res.add(a);
            }
        }

        return !res.isEmpty() ? res.stream() : null;
    }

    /**
     * vymaze...
     *
     * @param aope
     */
    public static void remove(Aspect aope) {
        if (aope != null) {
            eventitiez.remove(aope);
        }
    }

    /**
     * prida
     *
     * @param aope
     */
    public static void add(Aspect aope) {
        if (aope != null) {
            eventitiez.add(aope);
        }
    }

    /**
     *
     * @param aopes
     */
    public static void add(Aspect... aopes) {
        for (Aspect a : aopes) {
            add(a);
        }
    }
}
