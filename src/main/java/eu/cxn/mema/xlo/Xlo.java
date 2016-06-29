package eu.cxn.mema.xlo;

//import eu.teta.util.messages.ListenerAsyncRunner;

import org.slf4j.ext.*;
import org.slf4j.ext.XLogger.Level;

/**
 *
 * @author kubasek
 */
public class Xlo {

    /**
     * to je nas logger !
     */
    private static final XLogger log = XLoggerFactory.getXLogger(Xlo.class);

    /**
     * bezici thread muze ukladat informace, ty jsou pak vkladany do zprav z neho generovanych, k
     * tomu mu dopomahej .toString()
     */
    private static final ThreadLocal<XloInfo> infoT = new ThreadLocal<>();

    /**
     * to jsou nase listenery, kazdej rozumnej clovek kterej chce vedeet co se deje, povesi sem svuj
     * listener, napriklad sshw-net takovej listner ma, a propaguje ho jako event-stream na
     * sshw-net/xlogger !!
     */
//    public static ListenerAsyncRunner messengers;
//
//    static {
//        messengers = ListenerAsyncRunner.up();
//    }

    /**
     * pokud ma loger hlasit v jake tride a metode vznikla logovana udalost musi se prokousat
     * call-stackem, nebo se inicializovat v kazde tride ve ktere je volany..., volim stranu zla,
     * tedy jedna trida pro vsechny, pri zmene loggeru vyhoda, staci zmenit inicializaci pouze zde v
     * teto tride!!
     *
     * @return
     */
    public static StackTraceElement who() {
        return who(new Throwable().getStackTrace());
    }

    public static StackTraceElement who(StackTraceElement[] elements) {
        /**
         * entering..
         */
        if (elements != null && elements.length > 1) {
            return elements[1];

        }

        return null;
    }

    /**
     * prohleda seznam listnerenu, jestli nejake jsou.. a posle jim zpravy, obsluha listnereuu bezi
     * v jinem threadu, takze zbytecne nezdruje beh sledovaneho procesu
     */
    public static final void listenerCalls(java.util.logging.Level l, String message, StackTraceElement ste) {
//        if (messengers != null) {
//            if (ste != null) {
//                messengers.xlog(l.getName() + " at " + System.currentTimeMillis() + " [ " + ste.getClassName() + "." + ste.getMethodName() + " ] -> " + message);
//            } else {
//                messengers.xlog(l.getName() + " at " + System.currentTimeMillis() + " -> " + message);
//            }
//        }
    }

    /**
     * nejdriv se to vsechno rozbehne na ruzne levely, tak aby volani bylo jednoduche, nasledne se
     * to zase sebehne sem tak aby to bylo jednoduche ;-)
     *
     * @param l
     * @param message
     * @param e
     * @param ste
     */
    public static final void message(Level l, String message, Throwable e, StackTraceElement ste) {

        /**
         * listenerzzz
         *
         *
         * listenerCalls(l, message, ste);
         *
         */
    }

    /**
     * ziskani thread ulozenych informaci
     *
     * @return
     */
    public static XloInfo getInfoT() {
        return infoT.get();
    }

    /**
     * pokud info objekt v threadu neni, vytvori se ze zadane tridy
     */
    public static XloInfo getInfoT(Class<? extends XloInfo> c) {
        if( getInfoT() == null ) {
            try {
                setInfoT( (XloInfo)c.newInstance());
            } catch ( InstantiationException | IllegalAccessException e ) {
                err( "Nelze vytvorit instanci tridy", e);
            }
        }
        
        return getInfoT();
    }

    /**
     * vlozeni thread infomaci
     */
    public static void setInfoT(XloInfo o) {
        infoT.set(o);
    }

    /**
     * vypis thread informaci
     */
    public static String xloInfo() {
        if (getInfoT() != null) {
            return getInfoT().toString();
        }
        return "";
    }

    /**
     * jestli je zapnuty debug level nebo vyzsi
     */
    public static boolean dbgLevel() {
        return log.isDebugEnabled();
    }

    /**
     * l'informationen
     */
    public static final void info(String message) {
        log.info(message);
        message(Level.INFO, message, null, who());
    }

    /**
     * l'informationen
     */
    public static final void info(String message, Throwable e) {
        log.info(message, e);
        message(Level.INFO, message, e, who());
    }

    /**
     * l'errors
     */
    public static final void err(String message) {
        log.error(message + xloInfo());
        message(Level.ERROR, message, null, who());
    }

    /**
     * l'errors
     */
    public static final void err(String message, Throwable e) {
        log.error(message + xloInfo(), e);
        message(Level.ERROR, message, e, who());
    }

    /**
     * l'warnings
     */
    public static final void war(String message) {
        log.warn(message + xloInfo());
        message(Level.WARN, message, null, who());
    }

    /**
     * l'warnings
     */
    public static final void war(String message, Throwable e) {
        log.warn(message + xloInfo(), e);
        message(Level.WARN, message, e, who());
    }

    /**
     * l'debuger
     */
    public static final void dbg(String message) {
        log.debug(message);
        message(Level.DEBUG, message, null, who());

    }

    /**
     * zkratka, prvni boolean ukazuje jestli se ma vubec logovat, je pomerne dost logovacich
     * zalezitosti v debugu, kdyz by to zavyselo jen na levelu: DEBUG, je to hroziva bramboracka,
     * proto podle toho kde zrovna hledam chybku, zapinam si logovani
     */
    public static final void dbg(boolean l, String message) {
        if (l) {
            log.debug(message);
            message(Level.DEBUG, message, null, who());
        }
    }

    /**
     * l'debuger
     */
    public static final void dbg(String message, Throwable e) {
        log.debug(message, e);
        message(Level.DEBUG, message, e, who());
    }

    /**
     * pouzivam v JS jako break point s vytazenou nejakou hodnotou, jinak to k nicemu neni
     */
    public static final void breaker(Object o) {
        //System.out.println( o.toString());
    }
}
