package com.bigos.first

import java.util.regex.Matcher
import java.util.regex.Pattern

class Operators {
    static void main(String... args) {
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Elvis Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Elvis Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // elvis operator jest skrotem od ternary operator: displayName = user.name != null ? user.name : 'Anonymous'
        // w elvis operator brana jest testowana wartosc jesli nie jest false
        def user = [name: null]
        def displayName = user.name ?: 'Anonymous'
        assert displayName == 'Anonymous'

        user.name = 'John'
        displayName = user.name ?: 'Anonymous'
        assert displayName == 'John'

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Object Operators>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Object Operators>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Safe navigation Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // safe navigation operator jest używany w celu uniknięcia NullPointerException
        // jesli chcemy wywolac na obiekcie metodę ale obiekt jest nullem to zamiast NullPointer'a otrzymamy null
        def person = Person.find {it -> it.name == 'abc'}
        def personId = person?.id
        assert personId == null

        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Direct field access Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // jesli chcemy dostac sie go gettera klasy Person to: person.name
        // jesli chcemy sie dostac do pola klasy Person to: person.@name
        def person1 = new Person()
        person1.name = "John"
        // direct field access operator
        assert person1.name == person1.@name

        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Method Pointer Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // mehthod pointer operator (.&) sluzy to przechowywania referencji metody w zmiennej, typem takiej referencji
        // jest groovy.lang.Closure
        def str = "example example"
        def m1 = str.&toUpperCase
        assert m1() == str.toUpperCase()


        /*
        static def transform(List elements, Closure action) {
            def result = []
            elements.each {
                result << action(it)
            }
            result
        }
        static int power2(int n) {
            return n * n
        }
        */
        List intList = [1, 2, 3, 4]
        def action1 = this.&power2
        assert transform(intList, action1) == [1, 4, 9, 16]

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Regular Expression Operators>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Regular Expression Operators>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Pattern Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // pattern operator (~) dostarcza prosty sposób do stworzenia java.util.regex.Pattern
        def p = ~/foo/
        assert p instanceof Pattern

        // find operator (=~) buduje java.util.regex.Matcher
        def txt = "ala ma kota"
        def m = txt =~ /ala/
        assert m instanceof Matcher
        // jest jeszcze match operator (==~)

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Other Operators>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Other Operators>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Spread Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // spread operator (*.) jest uzywany to wywolania akcji na wszystkich obiektach zagregowanego obiektu
        // jest ekwiwalentem wywolania akcji na kazdym elemencie, a potem zebranie wszystkich elemntów w listę
        // operator ten jest nullo odporny
        // operator ten
        def persons = [
                new Person(id: 1, name: "John"),
                null,
                new Person(id: 2, name: "Johnathan")
        ]
        assert persons*.id == [1L, null, 2L]
        assert null*.id == null

        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Spread Method arguments>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // ładowanie listy jako parametry funkcji
        def fargs = [4, 5, 6]
        assert f1(*fargs) == 26
        assert f1(*[4], 5, 6) == 26
        // spread list elements
        def middle = [4, 5]
        def outside = [1, 2, 3, *middle, 6]
        assert outside == [1, 2, 3, 4, 5, 6]
        // spread map (*:)
        def kv1 = [c: 3, d: 4]
        def map = [a: 1, b: 2, *:kv1]
        assert map == [a: 1, b: 2, c: 3, d: 4]

        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Range Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // range (..) mozna budowac na dowolonych obiektach implementujących comparable, mających metody next() i previous()
        def range = 0..3
        assert range.collect() == [0, 1, 2, 3]
        assert (0..<3).collect() == [0, 1, 2]
        assert range instanceof List
        assert range.size() == 4

        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Spaceship Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // spaceship operator (<=>) oddelegowuje do compareTo
        assert (12 <=> 12) == 0
        assert (1 <=> 10) == -1
        assert (10 <=> 1) == 1

        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Membership Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // membership operator (in) jest ekwiwalentem użycia metody isCase lub metody contains z List
        def names = ["Phil", "John", "Johnathan"]
        assert "John" in names

        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Identity Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // w groovym uzywanie operatora == oznacza wolanie metody equals (uwaga w Javie porównuje sie obiekty)
        def l1 = ['abc']
        def l2 = ['abc']
        assert l1 == l2
        assert !l1.is(l2)

        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Coercion Operator>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // coercion operator (as) jest wariantem cast. Koercja zwraca nowy obiekt jezeli ten obiekt jest innego typu.
        // zwyczajna koercja może być zaimplementowana przez metodę asType(Class target)
        Integer x = 1
        String s = x as String
        assert s instanceof String
        // String s = (String) x - rzuci ClassCastException
    }

    static int f1(int x, int y, int z) {
        x*y+z
    }

    static def transform(List elements, Closure action) {
        def result = []
        elements.each {
            result << action(it)
        }
        result
    }

    static int power2(int n) {
        return n * n
    }
}

class Person {
    String name
    Long id
}
