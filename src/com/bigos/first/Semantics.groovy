package com.bigos.first

class Semantics {
    static void main(String...args1) {
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<example of for in loops>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<iterate over range>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        def x = 0
        for(i in 0..3) {
            x += i
        }
        assert x == 6
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<iterate over list>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        for(i in [1, 2, 3]) {
            x += i
        }
        assert x == 12
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<iterate over array>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        for(i in (1..3).toArray()) {
            x += i
        }
        assert x == 18
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<iterate over map>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        def map = ["a": 1, "b": 2, "c": 3]
        for(e in map) {
            x += e.value
        }
        assert x == 24

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<groovy assertion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //assert 1 + 1 == 3 : "Incorrect result"

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<groovy GPath>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //GPath is path expression language, który pozwala na identyfikowanie kawałków ustrukturyzowanych danych.
        // (Obiekty grafowe czy xml)

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Map to type coercion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        def map1
        map1 = [
            i: 10,
            hasNext: { map1.i > 0 },
            next: { map1.i-- }
        ]
        def iter = map1 as Iterator
        //while(iter.hasNext()) {
        //  println iter.next()
        //}
        assert iter.next() == 10

        /*interface Y {
            String f()
            void g(int s)
        }*/
        def y = [
            f: { "f" }
        ] as Y
        assert y.f() == "f"
        // y.g() - methodMissingException
        // y.g(4) - UnsupportedOperationException
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<String to enum coercion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        /*
        enum Sex {
            MALE, FEMALE
        }
        */
        Sex sex = 'FEMALE'
        assert sex == Sex.FEMALE

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Custom type coercion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // jest mozliwe, zeby zdefiniowac customową koercje dla klasy implementując metode asType
        /*
        class Polar {
            double r
            double phi

            def asType(Class target) {
                if(target == Cartesian) {
                    return new Cartesian(x: r*Math.cos(phi), y: r*Math.sin(phi))
                }
            }
        }

        class Cartesian {
            double x, y
            double x, y
        }

        ////////////////////////////////////////////////
        asType can be defined outside the class too:
        Polar.metaClass.asType = { Class target ->
            if (target == Cartesian) {
                return new Cartesian(x: r*cos(phi), y: r*sin(phi)
            }
        }
        */
        def sigma = 1E-16
        def polar = new Polar(r: 2.0, phi: Math.PI/2)
        def cartesian = polar as Cartesian
        assert Math.abs(cartesian.x - sigma) < sigma

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Optionality>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // optional parentheses jesli istnieje conajmniej jeden parametr i nie ma wieloznaczności to mozna pominąć nawiasy
        println "sth"
        // def min = Math.min 2 1

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Groovy truth>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // jak true sa ewaluowane:
        // - niepuste kolekcje i tablice
        // - matchery jesli maja przynajmniej jednego matcha
        // - iteratory jesli maja next
        // - niepuste mapy
        // - niepuste lancuchy znaków xD
        // - niezerowe liczby
        // - non-null object references
        // jesli obiekt ma sie evaluowac do true lub false to trzeba zaimplementować metode: asBoolean()
    }

    interface Y {
        String f()
        void g(int s)
    }

    static enum Sex {
        MALE, FEMALE
    }
}

class Polar {
    double r
    double phi

    def asType(Class target) {
        if(target == Cartesian) {
            return new Cartesian(x: r*Math.cos(phi), y: r*Math.sin(phi))
        }
    }
}

class Cartesian {
    double x, y
}
