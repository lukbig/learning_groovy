package com.bigos.first

class Closures1 {
    // syntax
    //    { [closureParameters -> ] statements }
    // examples:
    /*
        { sth++ }
        { -> sth++ }
        { println it }
        { it -> println it }
        { sth -> println sth }
        { int a, String b ->
            println "${a}, ${b}"
        }
     */

    static void main(String...args) {
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Closure as an object>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        def closure1 = { it -> println it }
        assert closure1 instanceof Closure

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Calling Closure>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        def code = {456}
        assert code() == 456
        assert code.call() == 456

        def code1 = {c -> c}
        assert code1(1) == 1
        assert code1.call('a') == 'a'

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Closure implicit parameter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // kiedy closure jawnie nie definiuje parametrów używając '->', closure zawsze definiuje niejawny parametr 'it'
        def greet = {"witaj $it"}
        assert greet("jan") as String == "witaj jan"


        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Closure varargs>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        def concat1 = { String... params -> params.join("")}
        assert concat1('a', 'b', 'c') == 'abc'

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Delegation Strategy>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // in closure:
        // this - odpowiada otaczającej klasie
        // owner - odpowiada otaczającemu obiektowi gdzie closure jest zdefiniowany
        // delegate - odpowiada innemu obiektowi gdzie metody są wołane lub brane propertiasy
        def testThis = { this }
        assert testThis() == this

        //def testOwner = { owner }
        def closure2 = new Closures1()
        assert closure2.testOwner() == closure2

        def testDelegate = {
            { -> delegate }.call()
        }
        assert testDelegate() == testDelegate

        // delegation strategy
        def dStrategy = { param1.toUpperCase() }
        def closure3 = new Closures1(param1: 'ble')
        dStrategy.delegate = closure3
        assert closure3.param1 == 'ble'

        // Different strategies can be set to closure delagate:
        /*
            Closure.OWNER_FIRST, Closure.DELEGATE_FIRST, Closure.OWNER_ONLY, Closure.DELEGATE_ONLY, Closure.TO_SELF
         */

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Closure in GStrings>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        def x = 2
        def y = "x = ${x}"
        assert y as String == "x = 2"

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Closure coercion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // SAM - single abstract method
        // closure can be converted into SAM or interface

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Functional Programming>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // it is possible to set closure parameter
        def closure4 = { int a, char b, String c -> a.toString() + b.toString() + c }
        // set left parameter
        def closure5 = closure4.curry(2)
        assert closure5((char)'a', 'sth') == '2asth'
        // set right parameter
        def closure6 = closure4.rcurry('ble')
        assert closure6(1, (char)'b') == '1bble'
        // index based currying
        def closure7 = closure4.ncurry(1, (char)'c')
        assert closure7(3, 'foo') == '3cfoo'

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Memoization>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // memoization pozwala na cache'owanie rezultatow wywowaln closure
        // example
        // def fib = { long n -> n<2?n:fib(n-1)+fib(n-2) }.memoize()
        // assert fib(25L) == 75025L // fast!
        // alternate methods: memoizeAtMost, memoizeAtLeast, memoizeBetween

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Composite>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        def plus3  = { it + 3 }
        def times4 = { it * 4 }
        def times4plus3 = plus3 << times4
        assert times4plus3(4) == 19
        assert times4plus3(3) == plus3(times4(3))
        // reverse composition
        //assert times4plus3(4) == (plus3 >> times4)(4)

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Trampoline>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // trampoline helps with recursive algorithms
    }

    def testOwner = { owner }
    String param1
}
