/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.cxn.mema.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * par pomocnych metodek pro babrani se v reflexi
 *
 * @author kubasek
 */
public class AopUtils {

    protected static HashMap<String, Annotation> anotkaCache;

    /**
     * hleda anotaci hluboko az v interfacech tridy
     *
     * @param <A>
     * @param clazz
     * @param annotationType
     * @return
     */
    public static <A extends Annotation> A deepAnnotation(Class<?> clazz, Class<A> annotationType) {

        if (anotkaCache == null) {
            anotkaCache = new HashMap<>();
        }

        String key = clazz.getName() + "@" + annotationType.getName();
        if (anotkaCache.containsKey(key)) {
            return (A) anotkaCache.get(key);
        } else {

            /**
             * kdyz ji ma primo 'zadavatel' je to easy
             */
            A annotation;
            if ((annotation = clazz.getAnnotation(annotationType)) == null) {

                /**
                 * interfaaacy by ji taky mohli mit
                 */
                for (Class<?> ifc : clazz.getInterfaces()) {
                    annotation = deepAnnotation(ifc, annotationType);
                    if (annotation != null) {
                        anotkaCache.put(key, annotation);
                        return annotation;
                    }
                }

                /**
                 * vsechny mozny tridy na ktere lze castovat
                 */
                if (!Annotation.class.isAssignableFrom(clazz)) {
                    for (Annotation ann : clazz.getAnnotations()) {
                        if ((annotation = deepAnnotation(ann.annotationType(), annotationType)) != null) {
                            anotkaCache.put(key, annotation);
                            return annotation;
                        }
                    }
                }

                /**
                 * supper class v neposledni rade, je na rade
                 */
                Class<?> superClass = clazz.getSuperclass();
                if (superClass == null || superClass == Object.class) {
                    return null;
                }

                /**
                 * sjede se niiiz
                 */
                annotation = deepAnnotation(superClass, annotationType);

            }

            if (annotation != null) {
                anotkaCache.put(key, annotation);
            }

            return annotation;
        }
    }

    /**
     * hleda anotaci hluboko az v interfacech tridy
     *
     * @param <A>
     * @param clazz
     * @param annotationType
     * @return
     */
    public static <A extends Annotation> A deepAnnotation2(Class<?> clazz, Class<A> annotationType) {

        /**
         * kdyz ji ma primo 'zadavatel' je to easy
         */
        A annotation = clazz.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }

        /**
         * interfaaacy by ji taky mohli mit
         */
        for (Class<?> ifc : clazz.getInterfaces()) {
            annotation = deepAnnotation2(ifc, annotationType);
            if (annotation != null) {
                return annotation;
            }
        }

        /**
         * vsechny mozny tridy na ktere lze castovat
         */
        if (!Annotation.class.isAssignableFrom(clazz)) {
            for (Annotation ann : clazz.getAnnotations()) {
                annotation = deepAnnotation2(ann.annotationType(), annotationType);
                if (annotation != null) {
                    return annotation;
                }
            }
        }

        /**
         * supper class v neposledni rade, je na rade
         */
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null || superClass == Object.class) {
            return null;
        }
        return deepAnnotation2(superClass, annotationType);
    }

    /**
     * pokus ku zrychleni nacitani metod, ono jich je suma sumarum asi 8tis
     */
    protected static HashMap<String, Method> methodcache;

    /**
     * najde metodu podle jmena
     *
     * @param c
     * @param methodName
     * @return
     */
    public static Method findMethod(Class<?> c, String methodName) {

        if (methodcache == null) {
            methodcache = new HashMap<>();
        }

        String key = c.getName() + "." + methodName;
        if (methodcache.containsKey(key)) {
            return methodcache.get(key);
        } else {
            Method[] mm = c.getMethods();
            for (Method mf : mm) {
                if (mf.getName().compareTo(methodName) == 0) {
                    methodcache.put(key, mf);
                    return mf;
                }
            }
        }

        return null;
    }
    
    /**
     * najde metodu podle jmena, kdyz ji nenajde 'napoprve', skusi si pridat pred jmeno
     * 'get'
     *
     * @param c
     * @param methodName
     * @return
     */
    public static Method findAdvanceMethod(Class<?> c, String methodName) {

        if (methodcache == null) {
            methodcache = new HashMap<>();
        }

        String key = c.getName() + "." + methodName;
        if (methodcache.containsKey(key)) {
            return methodcache.get(key);
        } else {
            Method[] mm = c.getMethods();
            for (Method mf : mm) {
                if (mf.getName().compareTo(methodName) == 0) {
                    methodcache.put(key, mf);
                    return mf;
                }
            }
        }

        return null;
    }

    /**
     * najde metodu podle navratoveho typu, nebo dokonce genericke definice navratoveho typu,
     * napriklad ElementList<Port>
     *
     * @param c
     * @param reType
     * @param gType
     * @return
     */
    public static Method findMethodByRetType(Class<?> c, Class<?> reType, Class<?> gType) {

        Method[] mm = c.getMethods();
        for (Method mf : mm) {

            if (reType.equals(mf.getReturnType())) {
                /**
                 * do dalsiho kola dolezou return typy
                 */
                if (gType != null) {
                    if (gType.equals(genericReturnType(mf))) {
                        return mf;
                    } else {
                        /**
                         * se pokracuje dal, dokud nenajde, nebo skonci
                         */
                        continue;
                    }
                }

                return mf;
            }
        }

        return null;
    }

    /**
     * pokusi se zjistit genericky typ navratoveho typu, pokud teda existuje no
     *
     * @param m
     */
    public static Class<?> genericReturnType(Method m) {

        /**
         * genericky typ si ziskame, mel by to byt ten zpravnyyy na indexu 0
         */
        Type type = m.getGenericReturnType();
        if (type != null) {
            try {
                Type[] t = ((ParameterizedType) type).getActualTypeArguments();
                if (t != null && t.length > 0) {
                    return (Class<?>) t[0];
                }
            } catch (Exception e) {
            }
        }

        /**
         * podle toho jestli bylo :word, vytvorit parsrovaci entitu, typ pretypujem na classu, byla
         * pakarna todle zjistit !! guglygugly..
         */
        return null;

    }

    /**
     * ziska genericky super type tridy
     */
    public static Class<?> genericSuperType(Class<?> c) {
        return (Class<?>) ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
}
