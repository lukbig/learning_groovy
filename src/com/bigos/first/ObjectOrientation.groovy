package com.bigos.first

import groovy.transform.PackageScope

class ObjectOrientation {
    static void main(String...args) {
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Classes>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Classes>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // defaultowo Groovy automatycznie ustawia pola i metody jako protected, a klasy i konstruktory jako public.
        // Adnotacja @PackageScope pozwala na wyłączenie tego featuru i cofnięcie się do starego zachowania javy.
        // Takiego zachowanie pozwoli na implementacje pakietów jak package scope. Koncepty modularności i
        // heksagonalnej architektury.
        /*
            @PackageScope([CLASS, FIELDS])
            class Foo {                 // class will have package scope protected
                int field1, field2      // package protected
                def method() {}         // public, mozna tez zmienic dodajac: METHODS
         */

        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Constructors>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Positional argument constructor>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // zeby stworzyc positional argument constructor, klasa musi miec zaimplementowany kazdy konstruktor jaki moze
        // byc uzyty w tym przypadku nie ma opcji, zeby uzyc konstruktora z named parameters
        def p1 = new Person1("John", 30)        // konstruktor - old java way
        def p2 = ['Johnathan', 35] as Person1 // użycie konstruktora przez koercję z slowem kluczowym as
        Person1 p3 = ["Michael", 40]            // użycie konstruktora przez koercję w przypisaniu
        /*
            @PackageScope
            class Person1 {
                String name
                int age

                Person1(name, age) {
                    this.age = age
                    this.name = name
                }
            }
        */
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Named argument constructor>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // jesli konstruktor nie jest zadeklarowany, to jest mozliwosc przekazywania parametrów w formie mapy key/value
        def p4 = new Person2()
        def p5 = new Person2(name: "Jack")
        def p6 = new Person2(age: 33)
        def p7 = new Person2(name: "Alex", age: 27)
        /*
            @PackageScope
            class Person2 {
                String name
                int age
            }
        */

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Methods>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Methods>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // metody w groovym mogą być zdefiniowane z return type lub byc bez typu (def keyword). Metody mogą otrzymać
        // nieograniczoną liczbę argumentów, które nie muszą mięć wprost zadeklarowanego typu. Defaultowym visibility
        // modifierem jest public tudziez nie musi być przed deklaracją metody. Metody w groovym zawszę zwracają jakąś
        // wartość dlatego jesli nie ma return to ostatnia wykonana linia zostanie zwrócona.
        /*
            def m1(String param1) {'$param1 is passed.'}
            static String m2(param2) {'$param2 passed.'}
         */
        // tak jak w konstruktorach metody mogą być wołane z named parameters
        // metody mogą mieć defaultowe parametry
        assert foo('John').p2 == 1
        // static def foo(String p1, Integer p2 = 1) {['p1': p1, 'p2': p2]}

        //varargs: mogą być definiowane poprzez (...) lub jako tablica([])
        assert m3("a", "b", "c") == 3
        assert m4(1, 2) == 2
        /*
        static def m3(String... args) {args.length}
        static def m4(Object[] args) {args.length}
        */

        // varargs vs method overloading
        assert m5() == 1
        assert m5(1) == 2
        assert m5(1, 2) == 1
        /*
        static def m5(Object... args) {1}
        static def m5(Object o) {2}
        */

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Pseudo properties>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Pseudo properties>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // mozna uzywac zdefiniowanych getterow i setterow
        def pp = new PseudoProperties()
        pp.name = "Jack"
        assert pp.age == 42
        pp.sth = true
        /*
        class PseudoProperties {
            // pseudo property name
            void setName(String name) {}
            String getName() {}

            //pseudo read-only property age
            int getAge() { 42 }

            //pseudo write-only property
            void setSth(boolean sth) {}
        }
        */
        // accessing properties
        assert pp.properties.keySet().contains("name")
    }
    static def foo(String p1, Integer p2 = 1) {['p1': p1, 'p2': p2]}
    static def m3(String... args) {args.length}
    static def m4(Object[] args) {args.length}
    static def m5(Object... args) {1}
    static def m5(Object o) {2}
}

@PackageScope
class Person1 {
    String name
    int age

    Person1(name, age) {
        this.age = age
        this.name = name
    }
}

@PackageScope
class Person2 {
    String name
    int age
}

@PackageScope
class PseudoProperties {
    // pseudo property name
    void setName(String name) {}
    String getName() {}

    //pseudo read-only property age
    int getAge() { 42 }

    //pseudo write-only property
    void setSth(boolean sth) {}
}