package org.cubewhy

import javax.swing.JOptionPane
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    // lc wrapper
    val mainClassName = System.getProperty("lunarMain")
    if (mainClassName == null) {
        println("Main class not set, did you launch the game from celestial?")
        exitProcess(1)
    }
    println("[CeleWrap] LC Wrapper is working!")
    println("[CeleWrap] LC main class: $mainClassName")
    println("[CeleWrap] Current classpath: ${System.getProperty("java.class.path")}")
    println("[CeleWrap] Getting LC entrypoint via reflection")
    val lunarMain = Class.forName(mainClassName)
    val mainFunction = lunarMain.getDeclaredMethod("main", Array<String>::class.java)
    println("[CeleWrap] LC entrypoint was found!")
    try {
        mainFunction.invoke(lunarMain.getConstructor().newInstance(), args)
    } catch (e: Exception) {
        println("[CeleWrap] Crash detected!")
        println("[CeleWrap] This is not a problem about Celestial or CeleWrap, please DO NOT report this on our official GitHub.")
        val trace = e.stackTraceToString()
        JOptionPane.showMessageDialog(null, trace, "Crash Detected", JOptionPane.ERROR_MESSAGE)
        exitProcess(2)
    }
}