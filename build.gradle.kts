plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.cubewhy.celestial"
version = "1.1.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.javassist:javassist:3.30.2-GA")
    implementation("org.ow2.asm:asm-tree:9.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "org.cubewhy.CeleWrapKt",
            "Premain-Class" to "org.cubewhy.CeleAgentKt",
            "Charset" to "UTF-8",
            "Can-Redefine-Classes" to "true",
        )
    }
}

kotlin {
    jvmToolchain(17)
}