import java.util.*

plugins {
    id("xyz.jpenilla.run-paper") version "2.2.0"
    id("java")
}

allprojects {
    apply(plugin = "java")

    group = "fr.islandswars"


    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    }
}

version = "0.2"

val mergedJar by configurations.creating<Configuration> {
    isCanBeResolved = true
    isCanBeConsumed = false
    isVisible = false
}

dependencies {
    mergedJar(project(":core"))
}

tasks.jar {
    dependsOn(mergedJar)

    manifest {
        attributes["Main-Class"] = "fr.islandswars.core.IslandsCore"
    }

    from({
        mergedJar
                .filter {
                    it.name.endsWith("jar") && it.path.contains(rootDir.path)
                }
                .map {
                    logger.lifecycle("depending on $it")
                    zipTree(it)
                }
    })
}

tasks {
    runServer {
        dependsOn(jar)
        environment("DEBUG", "true")
        environment("SERVER_TYPE", "HUB")
        environment("SERVER_ID", UUID.randomUUID())
        minecraftVersion("1.20.4")
    }
}