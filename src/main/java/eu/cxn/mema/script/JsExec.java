package eu.cxn.mema.script;

import eu.cxn.mema.app.MeMaCore;
import eu.cxn.mema.script.adapters.MeMaApi;
import eu.cxn.mema.skeleton.IScript;
import jdk.nashorn.api.scripting.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.*;

public class JsExec {
    private static final Logger LOG = LoggerFactory.getLogger(JsExec.class);

    private ScriptEngine engine;

    private Invocable invocable;

    private MeMaApi api = MeMaCore.getInstance();

    public JsExec() {

        /*
         * get engine from engine pool
         */
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        engine = scriptEngineManager.getEngineByName("nashorn");
        invocable = (Invocable) engine;


        /* put global bindigs */
        Bindings bindings = engine.createBindings();
        //bindings.put("conf", api);

        engine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);

    }

    /* compile script */
    public CompiledScript compile(IScript script) {
        try {
            return ((Compilable) engine).compile(script.body());
        } catch (ScriptException se) {
            throw new IllegalStateException("JS Compiler error: " + script, se);
        }
    }

    /*
     * binding information to active script context
     */
    private synchronized void bindings2engine(ScriptContext context) {

        Bindings bindings = context.getBindings(ScriptContext.ENGINE_SCOPE);
        if (bindings == null) {
            bindings = engine.createBindings();
        }

        /* vlozime */
        bindings.put("api", api);
//        if (net != null) {
//            bindings.put("network", new NetworkAdapter(api, net));
//        }

        /* vlozit do engine */
        context.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
    }

    /*
     * make the  jso object
     */
    private synchronized JSObject jso(IScript script, String method) throws ScriptException {

        /* pre compile script and evaluate */
        CompiledScript cs = compile(script);
        cs.eval();

        /* need kontext for binding and get JSObject */
        ScriptContext context = cs.getEngine().getContext();

        bindings2engine(context);

        return (JSObject) context.getAttribute(method);
    }


    public Object process(IScript script, String method) {

        try {

            JSObject f = jso(script, method);

//            ((ParaBoxImpl) params).runner(this);
//            ((ParaBoxImpl) params).network(network);

            if (f == null) {
                LOG.error("Problem se spustenim skriptu {}, params={}, nenalezena metoda=process ", script, method);
                return null;
            }

            Object result = null;
            //f.setMember("network", net );

            return f.call(f, script.params());

//            if (params instanceof JSObject) {
//                result = ((JSOParaBox) params).setJso(f).call(null, params);
//            } else {
////                params.getAll().forEach((k, v) -> {
////                    if (k != null && v != null) {
////                        // TODO: zjistit proc mu vadi null hodnota value
////                        f.setMember(k, v);
////                    }
////                });
//
//                /* predavane paramtry do js funkce jsou posunute o jeden 'dozadu' */
//                result = f.call(f, params);
//            }

            //return result;

        } catch (ScriptException se) {
            LOG.error(script.name() + " process error: " + se.getMessage() + ", LINE=" + se.getLineNumber());
            throw new IllegalStateException(script.name() + " process error: " + se.getMessage() + ", LINE=" + se.getLineNumber(), se);
        } catch (Exception e) {
            LOG.error(script.name() + " process error: " + e.getMessage());
            throw new IllegalStateException(script.name() + " process error: " + e.getMessage(), e);
        }
    }


    public Object run(IScript script, String method) {
        return process(script, method);
    }

}
