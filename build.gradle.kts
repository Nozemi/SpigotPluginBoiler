import kr.entree.spigradle.kotlin.spigot
import de.undercouch.gradle.tasks.download.Download

val spigotVersion: String by project
val pluginApiVersion: String by project
val pluginVersion: String by project
val pluginName: String by project
val pluginNameLower = pluginName.toLowerCase()

plugins {
    id("java")
    id("kr.entree.spigradle") version "1.2.4"
    id("de.undercouch.download") version "4.0.4"
}

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit:junit:4.12")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.0.rc1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.0.rc1")

    implementation("org.reflections:reflections:0.9.12")

    compileOnly(spigot(spigotVersion))
}

spigot {
    authors = listOf("Nozemi")
    name = pluginName
    depends = listOf()
    version = pluginVersion
    apiVersion = pluginApiVersion
    load = kr.entree.spigradle.attribute.Load.STARTUP
    commands {
        create(pluginNameLower) {
            usage = "/<command>"
            description = "Gets the list of commands available for the plugin."
        }
    }
    permissions {}
}

tasks {
    register<Jar>("fatJar") {
        group = "spigot-plugin"

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from(configurations.runtimeClasspath.get()
                .onEach { println("add from dependencies: ${it.name}") }
                .map { if (it.isDirectory) it else zipTree(it) })

        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
        from(sourcesMain.output)
    }

    register<Download>("downloadSpigotServer") {
        group = "spigot-testserver"

        src("https://cdn.getbukkit.org/spigot/spigot-$spigotVersion.jar")
        dest("./testserver/spigot-$spigotVersion.jar")
        overwrite(false)
    }

    register<Copy>("setupTestServer") {
        group = "spigot-testserver"

        dependsOn(":downloadSpigotServer")

        from(file("./config/devserver"))
        into(file("./testserver"))
    }

    register<Copy>("copyPluginToTestServer") {
        group = "spigot-testserver"

        //dependsOn(":cleanProject", ":fatJar", ":setupTestServer")
        dependsOn(":fatJar")

        from(file("./build/libs/$pluginName.jar"))
        into(file("./testserver/plugins"))
    }

    register<JavaExec>("cleanRunTestServer") {
        group = "spigot-testserver"

        dependsOn(":copyPluginToTestServer", ":downloadSpigotServer", ":setupTestServer")

        classpath(files("./testserver/spigot-$spigotVersion.jar"))
        workingDir("./testserver/")
        standardInput = System.`in`
    }

    register<JavaExec>("runTestServer") {
        group = "spigot-testserver"

        dependsOn(":setupTestServer", ":copyPluginToTestServer")

        classpath(files("./testserver/spigot-$spigotVersion.jar"))
        workingDir("./testserver/")
        standardInput = System.`in`
    }

    register<Delete>("cleanProject") {
        group = "spigot-plugin"

        dependsOn(":clean")

        delete(file("./testserver"))
    }
}