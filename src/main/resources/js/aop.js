/**
 *  AOP
 *  implementace aop do js
 */
function aopEx() {

    this.aspect = aspect;

    function aspect( s, b, a ) {
        JsStorage.aspect( s, b, a );
    }
}

// aop globalni promenna
var aop = new aopEx();

// wrapper pro volani aop method
function AopWrapper( f, o ) {
    f(o);
}

