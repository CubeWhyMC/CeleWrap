plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.cubewhy.celestial"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "org.cubewhy.CeleWrapKt",
            "Premain-Class" to "org.cubewhy.celestial.AgentKt",
            "Charset" to "UTF-8"
        )
    }
}

kotlin {
    jvmToolchain(17)
}