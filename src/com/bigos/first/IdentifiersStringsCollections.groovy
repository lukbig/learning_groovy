package com.bigos.first

class IdentifiersStringsCollections {
    static void main(String... args) {
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Quoted identifiers>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "Quoted identifiers"
        /*
        * pewne "identifiers" zawierają niedozwolone znaki, które są zakazane w javie ale w groovy są dozwolone w nawiasie
        * */
        def map = [:]

        map."an identifier with a space and double quotes" = "ALLOWED"
        map.'with-dash-signs-and-single-quotes' = "ALLOWED"

        assert map."an identifier with a space and double quotes" == "ALLOWED"
        assert map.'with-dash-signs-and-single-quotes' == "ALLOWED"
        // jaki wyjatek rzuca assert? Exception in thread "main" Assertion failed:

        // po kropce wszystkie rodzaje typu String są dozwolone
        map.'single quote'
        map.'double quote'
        map.'''triple'''
        map."""triple"""
        map./slashy string/
        map.$/slashy dollar/$

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Groovy GStrings>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Groovy GStrings>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // GString i String mają różne hashCode() zatem GString nie powinnien być używany jako klucz
        // typ String w 3 ciapkach to java.lang.String ale zachowuje line separatory f.e.
        def str1 = '''\
                        hello
                        kowalski
                         and
                         wisniewski
        '''
        println str1
        println str1.stripMargin('\t').stripIndent()
        /*output:
                        hello
                        kowalski
                         and
                         wisniewski


hello
kowalski
 and
 wisniewski
        */

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<String interpolation>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<String interpolation>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // ${} or prefixed with $ w wyrazeniach z kropką
        def firstName = "james"
        map."Bond-${firstName}" = "James Bond"
        assert map."Bond-james" == "James Bond"

        def sum = "The sum of 2 and 3 is ${2 + 3}"
        assert sum.toString() == "The sum of 2 and 3 is 5"

        def person = [name: "John", age: 50]
        assert "$person.name is $person.age years old." == "John is 50 years old."

        assert '${name}' == "\${name}"

        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<String interpolcation with closure>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<String interpolcation with embedded closure>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        def bezParametrowyClosure = "1 + 2 = ${-> 3}"
        assert bezParametrowyClosure.toString() == "1 + 2 = 3"

        def closureZparametrami = "1 + 2 = ${p -> p << 3}"
        assert closureZparametrami.toString() == "1 + 2 = 3"

        //lazy and eager evaluation
        def number = 1
        def eagerNumber = "${number}"
        def lazyNumber = "${-> number}"

        assert eagerNumber == "1"
        assert lazyNumber == "1"

        number = 2
        assert eagerNumber == "1"
        assert lazyNumber == "2"

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Slashy strings>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Slashy strings>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // slashy strings są przydatne bo nie trzeba uzywac back-slash'a jako escape character totez uzywa sie ich w
        // regular expression
        def regExpPattern = /.file.*/
        assert regExpPattern == '.file.*'
        // tylko slash musi byc poprzedzony escape characterem
        def regExpPattern1 = /this is slash: \//
        assert regExpPattern1 == 'this is slash: /'
        // slashy string are multilines
        def multilineslashy = /one
            two
            three/
        assert multilineslashy.contains("\n")
        //interpolation can be used with slashy strings

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<coercion and cast>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<coercion and cast>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        char char1 = 'a'
        assert char1 instanceof Character
        def char2 = 'a' as char
        assert char2 instanceof Character
        def char3 = (char)'a'
        assert char3 instanceof Character

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Numbers>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Numbers>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // e and E is exponent
        assert 1e3 == 1000
        assert 2E2 == 200
        // ** - power operator
        assert 2 ** 3 == 8

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Lists>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Lists>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        //defining arrayList
        def numbers = [1, 2, 3]
        assert numbers instanceof List
        assert numbers.size() == 3
        assert numbers instanceof ArrayList

        // zrobionie linkedListy przez koercje
        def linkedList = [2, 3] as LinkedList
        assert linkedList instanceof LinkedList

        // do listy mozna odwolywac sie jak elementów tablicy np [0], [-2] - element przedostatni, << left shift dorzuca
        // nowy element do listy
        def letters = ['a', 'b', 'c', 'd']

        assert letters[0] == 'a'
        assert letters[1, 3] == ['b', 'd']
        assert letters[-2] == 'c'
        assert letters[-1] == 'd'
        assert letters[0..1] == ['a', 'b']

        letters << 'e'
        assert letters[-1] == 'e'

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Arrays>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Arrays>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // groovy uzywa takiej samej notacji dla list jak dla tablic, tyle, ze podczas deklaracji tablicy trzeba podac typ
        String[] array1 = ['a', 'b']
        assert array1 instanceof String[]
        def charArray1 = array1 as char[]
        assert charArray1 instanceof char[]

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Maps>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        println "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Maps>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        // sometimes called dictionaries or associative arrays
        def characters = ['a': ((char)'a') as int, 'b': ((char)'b') as int]
        assert characters['a'] == 97
        assert characters.b == 98

        characters['c'] = ((char)'c') as int
        assert characters.c == 99

        // groovy z defaultu tworzy LinkedHashMape
        assert characters instanceof LinkedHashMap
        assert (characters as HashMap) instanceof HashMap
    }
}
