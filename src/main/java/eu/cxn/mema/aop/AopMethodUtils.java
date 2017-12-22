/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;


/**
 *
 * @author kubasek
 */
public class AopMethodUtils {

    /**
     * vrapper bez prohibited name
     */
    public static Stream<Method> methodRetypeCatcher(Class<?> cls, String namePart) {
        return methodRetypeCatcher(cls, namePart, null);
    }

    /**
     * to profrci..
     *
     * @param cls
     * @param namePart
     * @return
     */
    public static Stream<Method> methodRetypeCatcher(Class<?> cls, String namePart, String prohibitedNamePart) {
        List<Method> a = new ArrayList<>();
        for (Method m : cls.getMethods()) {
            String mname = m.getReturnType().getSimpleName();
            if (mname.contains(namePart) && (prohibitedNamePart == null || !mname.contains(prohibitedNamePart))) {
                a.add(m);
            }
        }
        return a.stream();
    }

    /**
     * pole stringu jmen metodek ktere jsou 'possible'
     */
    public static Predicate<Method> methodFilter(final String... names) {
        final String ens = String.join(",", names);
        return m -> { 
                String mn = m.getName();
                for (String s : names) {
                    if (s.equals(mn)) {
                        return true;
                    }
                }
                return false;
            };

    }

    /**
     * to profrci..
     *
     * @param cls
     * @param namePart
     * @return
     */
    public static Stream<Method> methodNameCatcher(Class<?> cls, String namePart) {
        List<Method> a = new ArrayList<>();
        for (Method m : cls.getMethods()) {
            if (m.getName().contains(namePart)) {
                a.add(m);
            }
        }
        return a.stream();
    }

    /**
     * zjisti jestli ma metoda anotaci
     *
     * @param anot
     * @return
     */
    public static Predicate<Method> haveMethodAnotation(final Class<? extends Annotation> anot) {
        return m -> m.getModifiers() > 0 && m.getAnnotation(anot) != null;
        
    }

    /**
     * vhodne k vysrktani metod anotovanych parametrem ze seznamu
     */
    public static Predicate<Method> haveNotMethodAnot(final Class<? extends Annotation> anot) {
        return m -> !(m.getModifiers() > 0 && m.getAnnotation(anot) != null);
    }

}
