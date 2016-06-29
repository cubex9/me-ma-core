/*
 * Teta s.r.o.
 * (c)2013
 */
package eu.cxn.mema.aop;

import eu.cxn.mema.xlo.Xlo;
import java.lang.reflect.Proxy;

/**
 * Aspect oriented programing implementation over Proxy, Proxy cachuje tridy, ne instance objektu,
 * ale jejich code, protazenim pres proxy ziskame informaci, invocationhandler volani, invokaci
 * metody k danemu interface, to se da vyuzit a pred/za vlozit kod, aspect, pravidla pro aplikovani
 * aspektu jsou jednoducha: musi byt deklarovana v zadanem interface ( nebo interface ktery je
 * extendovan ), a vyhovet Regexpove masce jmena metody, specialni moznost je vazat aspect k urite
 * instanci objektu
 * <br />
 * Tato implementace neni z nejrychlejsich moznych variant, zato jednoduse implementovatelna,
 * vyuziva reflexi a to je vzdy zdrzeni, objekty pouzivane pro vypocty nebo jinak casove/procesorove
 * narocne ulohy lze vynechat jednoduse tak ze nejsou pri vytvareni, protazeny pres Proxi, pomoci
 * metody:
 * <pre>
 * {@code
 *    Box box = AopHandler.newInstance( new MyBoxImplementation());
 * }
 * </pre> naopak pokud objekt ma umoznit aplikaci aspektu, musi byt takto vytvoren, to dava moznosi
 * vyberu, napriklad pro hledani cesty mezi dvema Boxi, pouzivame Dijxtra alghoritmus, zavlect do
 * nej reflexi by bylo krajne nevhodne, Boxi jsou z DB nacitane normalne, a teprve po skonceni
 * algoritmu jsou pri predani vysledne cesty provleceny uchem AopHandleru, pohoda :-)
 * <br />
 * Vyhodne lze aop vyuzit v JavaScriptu, ten je cely implementovan pomoci reflexe, dalsi zdrzeni v
 * radu par procent neni jiz katastrofalni a naopak proti jave ( bez lamdy ) se aspekty krasne
 * deklaruji jako anonimni funkce
 * <br />
 * POZOR: proxi pracuje vzdy s Interfacem, Aop nelze pouzit pro Tridy ktere nejsou implementaci
 * nejakeho interface, kdo by vsak takto hanebne, bez rozhrani programoval ? ;-)
 * <br />
 * PS: Profiler funguje na stejnem pryncipu, avsak pouziva prepsani ClassLoaderu, coz neni nemozne
 * zvladnou, na druhou stranu, libi se mi moznost Aop pouzit v projektu jen tam kde se mi to hodi, i
 * kdyz je to samozrejme pracnejsi a je potreba na to myslet.
 *
 * @author kubasek
 */
public class Aop {

    /**
     * in java:
     *
     * <pre>
     * {@code
     *
     *  Aop.aspect( new BaseAspect( mask, cls ) {
     *       public Object after( Object o, Object res, Object[] args ) {
     *           Port p = (Port)o;
     *           p.up();
     *
     *           return res;
     *       }
     *   });
     * }
     * </pre>
     *
     * in jdk8:
     *
     * <pre>
     * {@code
     *   Aop.aspect( new BaseAspect( mas, cls, null, (port,args) -> { port.up(); return res; }));
     * }
     * </pre>
     *
     * in java script:
     * <pre>
     * {@code
     *
     * aop.aspect( "Port.setAccessVlan", function( port ) {
     *    port.up();
     * }
     *
     * }
     * </pre>
     *
     *
     * @param a
     */
    public static void aspect(Aspect a) {
        AopStorage.add(a);
    }

    /**
     * ziska puvodni objekt z proxi handleru, teda pokud objekt je udelany pres proxi
     *
     * @param <T>
     * @param o
     * @return
     */
    public static <T extends AopClass> T deProxy(T o) {

        if (o.isAop() || o.getClass().getName().contains("roxy")) {
            try {
                AopHandler aha = (AopHandler) Proxy.getInvocationHandler(o);
                if (aha != null) {
                    return (T) aha.getObject();
                }
            } catch (IllegalArgumentException e) {
                Xlo.err("Aop.deProxy: " + o.getClass().getName() + " -> " + e.getMessage());
            }
        }

        return (T) o;
    }

}
