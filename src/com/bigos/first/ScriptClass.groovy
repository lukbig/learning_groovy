package com.bigos.first

import org.codehaus.groovy.runtime.InvokerHelper

class ScriptClass extends Script {
    def run() {
        println "groovy world"
    }

    static void main(String... args) {
        InvokerHelper.runScript(ScriptClass, args)
    }
}
