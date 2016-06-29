/*
 * Teta s.r.o.
 * (c)2013
 */
package eu.cxn.mema.aop;

import eu.cxn.mema.xlo.Xlo;
import javax.script.Invocable;
import javax.script.ScriptException;

/**
 * vraper pro javascriptove aop funkce nekde v JS code musi byt:
 * <pre>
 * {@code
 *   function AopWrapper( f, o ) {
 *      return f(o);
 *   }
 * }
 * </pre>
 * 
 * zadani aspectu:
 * <pre>
 * {@code
 *   aop.aspect("Port.setAccessVlan", null, function(port) {
 *     port.notes().set("-< turn to ACCESS mode after set accessVlan");
 *     port.mode().set( Port.Mode.access );
 *     port.up();
 *   });
 *
 *  // allowedVlans.add() aspect
 *  aop.aspect("Port.allowedVlans.add", null, function(port) {
 *    port.notes().set("-< turn to TRUNK mode after add allowedVlan");
 *    port.mode().set( Port.Mode.trunk );
 *    port.up();
 *  });
 * }
 * </pre>
 *
 * dale pak dobre pouzit pro zadavani aspectu JsStorage v deusExAgent projektu, z package
 * eu.teta.jswarp a v js.sys je aop.js
 *
 *
 *
 * @author kubasek
 */
public class JsAspect extends BaseAspect {

    /**
     * je vzdy pri startu deusexmachiny potreba naplnit
     */
    public static Invocable inveco;

    /**
     * funkce rostrilare zepredu a zezadu..
     */
    protected Object before;
    /**
     * funkce rostrilare zepredu a zezadu..
     */
    protected Object after;

    /**
     * konstruktorek se vsim vsudy !
     *
     * @param identificator
     * @param c
     * @param before
     * @param after
     */
    public JsAspect(String identificator, Class<?> c, Object before, Object after) {
        super(identificator, c);
        this.before = before;
        this.after = after;
    }

    /**
     * wola to wrapper do JS
     *
     * @return
     */
    @Override
    public Object[] before(Object o, Object[] args) {
        if (before != null) {
            try {
                inveco.invokeFunction("AopWrapper", new Object[]{before, o});
            } catch (ScriptException | NoSuchMethodException e) {
                Xlo.err("JsAspect.before: " + o.getClass().getName() + " -> " + e.getMessage());
            }
        }
        return args;
    }

    /**
     * wola to wrapper do JS
     *
     * @param res
     */
    @Override
    public Object after(Object o, Object res, Object[] args) {
        if (after != null) {
            try {
                inveco.invokeFunction("AopWrapper", new Object[]{after, o});
            } catch (ScriptException | NoSuchMethodException e) {
                Xlo.err("JsAspect.before: " + o.getClass().getName() + " -> " + e.getMessage());
            }
        }
        return res;
    }

}
