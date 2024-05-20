import java.util.*

plugins {
    id("xyz.jpenilla.run-paper") version "2.2.0"
    id("java")
    id("maven-publish")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    group = "fr.islandswars"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven {
            url = uri("https://maven.pkg.github.com/islands-wars/commons")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    }
}

version = "0.1.3"

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