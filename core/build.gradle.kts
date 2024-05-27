import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    implementation(project(":api"))
    compileOnly("org.apache.logging.log4j:log4j-core:2.17.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.lettuce:lettuce-core:6.3.2.RELEASE")
    implementation("fr.islandswars:commons:0.2.7")
}

val sourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc.get().destinationDir)
}

tasks.named<ShadowJar>("shadowJar") {
    dependsOn("jar")

    dependencies {
        exclude(dependency("org.apache.logging.log4j:"))
    }
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            groupId = rootProject.group.toString()
            artifactId = project.name
            version = "0.1.1"

            artifact(sourceJar)
            artifact(javadocJar)

            pom {
                name.set(project.name)
                description.set("Utility classes for connecting to islands-wars database")
                url.set("https://github.com/islands-wars/islands")

                licenses {
                    license {
                        name.set("The GNU General Public License, Version 3.0")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.html#license-text")
                    }
                }
                developers {
                    developer {
                        id.set("Xharos")
                        name.set("Burgaud Valentin")
                        email.set("jangliu@islandswars.fr")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/islands-wars/islands.git")
                    developerConnection.set("scm:git:ssh://github.com:islands-wars/islands.git")
                    url.set("https://github.com/islands-wars/islands")
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/islands-wars/islands")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}