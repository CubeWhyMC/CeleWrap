package org.cubewhy

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    // lc wrapper
    val mainClassName = System.getProperty("lunarMain")
    if (mainClassName == null) {
        println("Main class not set, did you launch the game from celestial?")
        exitProcess(1)
    }
    println("[CeleWrap] LC Wrapper is working!")
    println("[CeleWrap]")
    println("[CeleWrap] LC main class: $mainClassName")
    println("[CeleWrap] Current classpath: ${System.getProperty("java.class.path")}")
    println("[CeleWrap] Getting main function via reflection")
    val lunarMain = Class.forName(mainClassName)
    val mainFunction = lunarMain.getDeclaredMethod("main", Array<String>::class.java)
    mainFunction.invoke(lunarMain.getConstructor().newInstance(), args)
}