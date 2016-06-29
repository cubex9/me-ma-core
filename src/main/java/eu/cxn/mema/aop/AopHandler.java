/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.aop;

import java.lang.reflect.*;
import java.util.stream.Stream;

/**
 * AopHandler implementation
 *
 * <pre>
 * {@literal public class Neco {
 *
 * private Integer something;
 *
 * public void setSomething( String something ) {
 * this.something = something;
 * }
 * }
 *
 * Neco = (Neco)AopHandler.newInstance( Neco.class );
 *
 * }
 * </pre>
 *
 * @author kubasek
 */
public class AopHandler implements InvocationHandler {

    /**
     * puvodni objekt
     */
    private Object obj;

    /**
     * tesne pred invokaci se zapne, jelikos invokace okamzite rekurzivne vola tu samou..., tam se
     * hnedka schodi
     */
    private Boolean isin = false;

    /**
     * globani zapnuti/vypnuti celeho aop, newInstance pak vraci puvodni objekty bez invoke handleru
     */
    public static boolean ok = true;

    /**
     * nova instance objektu a poveseni invoke handleru na nej, tim se pri volani metody, bude
     * evokovat
     *
     * @param obj
     * @return
     */
    public static Object newInstance(Object obj) {

        /**
         * kontroluje aby nedaval tridu dvakrat do handleru
         */
        Class c = obj.getClass();
        /**
         * jestli je to zaple a neni to uz v proxy
         */
        if (ok && !c.getName().contains("roxy")) {

            /**
             * aopizing !
             */
            if (obj instanceof AopClass) {
                ((AopClass) obj).aopInstance();
            }

            return Proxy.newProxyInstance(c.getClassLoader(), c.getInterfaces(), new AopHandler(obj));
        }
        return obj;
    }

    /**
     * construcorek
     *
     * @param obj
     */
    private AopHandler(Object obj) {
        this.obj = obj;
    }

    /**
     * constructorek n.2
     */
    private AopHandler() {
    }

    /**
     * set objec for
     *
     * @param obj
     */
    public void setObject(Object obj) {
        this.obj = obj;
    }

    /**
     * vrati objekt
     */
    public Object getObject() {
        return obj;
    }

    /**
     * invocation handlerek
     *
     * @param proxy
     * @param m
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method m, Object[] args)
            throws Throwable {
        /**
         * jinak by se to volalo dokola rekurziiivne
         */
        if (isin) {
            isin = false;
            return null;
        }

        /**
         * pokusi se najit aop event pro dany objekt a metodu
         */
        Stream<Aspect> aopes = AopStorage.get(proxy, m);
        /**
         * vysledek volane metody, muze projit after metodou
         */
        Object result = null;
        /**
         * before muze menit vstupni argumenty metody
         */
        Object[] aopArgs = null;

        try {
            //System.err.println("AOP: before method " + m.getName());
            /**
             * zavola aop before metodku
             */
            if (aopes != null) {
                for (Aspect aope : aopes) {
                    /**
                     * se musej resit ty proklaty deti :-)
                     */
                    if (aope.getChildCall() != null) {
                        /**
                         * kdyz mam dite, jsem rodic, moje after/before se nevola jen nastavuji
                         * objekt pro dite
                         */
                        aope.getChildCall().setParrentObject(obj);
                    } else {
                        /**
                         * jsem li dite a objekt je nastaven, volaaam
                         */
                        if (aope.isChild() && aope.getParrentObject() != null) {
                            aopArgs = aope.before(aope.getParrentObject(), args);
                        } else {
                            aopArgs = aope.before(obj, args);
                        }
                    }
                }
            }
            /**
             * invokace/volani vlastni metody + nastaveni antirecursive call TODO synchronize pro
             * isin
             */
            result = m.invoke(obj, aopArgs == null ? args : aopArgs);

            /**
             * chybicky
             */
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new RuntimeException("AOP: unexpected invocation exception: " + e.getMessage());
        } finally {
            //System.err.println("AOP: after method " + m.getName());
            /**
             * zavola aop after metodku
             */
            if (aopes != null) {
                for (Aspect aope : aopes) {
                    /**
                     * se musej resit ty proklaty deti :-)
                     */
                    if (aope.getChildCall() == null) {
                        /**
                         * jsem-li dite, a muj parrent objekt byl nastaven, volam se, pote parrent
                         * objekt vynuluju, je jen pro jedno volani, jeden aspect
                         */
                        if (aope.isChild()) {
                            if (aope.getParrentObject() != null) {
                                result = aope.after(aope.getParrentObject(), result, args);
                                aope.setParrentObject(null);
                            }
                        } else {
                            /**
                             * normalni volani, nemam dite, a nejsem dite
                             */
                            result = aope.after(obj, result, args);
                        }
                    }
                    /**
                     * mamli dite, tady nedelam nic, parrentObject nastavil before
                     */

                }
            }

            isin = false;
        }
        return result;
    }
}
