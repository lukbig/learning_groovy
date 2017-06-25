package com.bigos.first

class Traits {
    // Traits jest konstrukcją strukturalną, która pozwala na:
    // 1. kompozycję zachowań
    // 2. implementacje w runtimie dla interfaców
    // 3. nadpisywanie zachowań
    // 4. kompatybilność ze statyczną kompilacją i sprawdzaniem
    // trait może być uważany jako interface z defaultową implementacją

    trait SwimmingAbility {
        int age
        String swim() {
            return "I am swimming."
        }
        abstract String name()
        def whoAmI() {
            return this
        }
        // dynamic behaviour
        String speak() {
            blow()
        }
    }

    // mogą być używane jak zwykle interfejsy
    // class Fish implements SwimmingAbility {}
    // trait może mieć prywatne metody
    // trait może mieć metody abstrakcyjne, które muszą być zaimplementowane
    // trait moze implementowac interfejsy
    // trait moze miec propertiasy
    // trait moze miec prywatne pola, publiczne pola(dostanie sie od nich przez '_' - underscore)
    // trait wspiera multiple inheritance (wiele traitow) - ale jesli jest konflikt to trzeba go rozwiązać

    // trait wspiera dynamiczny kod czyli w ciele trait moze byc metoda, która dopiero zostanie zaimplementowana
    // w super klasie
    /* trait SwimmingAbility {
        ...
           String speak() {
                blow()
            }
       }
       //////////////////////
       class Fish implements Traits.SwimmingAbility {
            String methodMissing(String name, args) {
               return "${name.capitalize()}!"
            }
       }
       /////////////////////
       assert f2.speak() == "Blow!"
    */

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Dynamic part 2 (methods and properties)
    trait DynamicObj {
        private Map propertiasy = [:]

        def methodMissing(String name, args) {
            return name.toUpperCase()
        }
        def propertyMissing(String property) {
            return propertiasy[property]
        }
        void setProperty(String property, Object obj) {
            propertiasy[property] = obj
        }
    }

    static void main(String...args) {
        def f1 = new Fish()
        assert f1.swim() ==  "I am swimming."
        assert f1.name() == "My name is the real slim shady."
        // in trait 'this' means implementing instance
        assert f1.whoAmI().is(f1)

        // DefaultGroovyMethod.is() is
        // Identity check. Since == is overridden in Groovy with the meaning of equality
        // we need some fallback to check for object identity.

        // trait properties example:
        def f2 = new Fish(age: 1)
        assert f2.age == 1

        //testing dynamic part 1
        assert f2.speak() == "Blow!"

        // testing dynamic part 2
        def d = new Dynamic()
        assert d.foo == null
        d.foo = "sth"
        assert d.foo == "sth"
        assert d.someMethod() == "SOMEMETHOD"

        // using/implementing trait in Runtime
        def t = new Traits() as SwimmingAbility
        assert t.swim() == "I am swimming."
    }
}

class Fish implements Traits.SwimmingAbility {
    String name() {
        return "My name is the real slim shady."
    }

    // dynamic part
    String methodMissing(String name, args) {
        return "${name.capitalize()}!"
    }
}

class Dynamic implements Traits.DynamicObj {}