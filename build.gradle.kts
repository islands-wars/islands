import java.util.*

plugins {
    id("xyz.jpenilla.run-paper") version "2.2.0"
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
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

dependencies {
    implementation(project(":core"))
}

tasks {
    runServer {
        dependsOn(shadowJar)
        environment("DEBUG", "true")
        environment("SERVER_TYPE", "HUB")
        environment("SERVER_ID", UUID.randomUUID())
        minecraftVersion("1.20.4")
    }
}