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
    }

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