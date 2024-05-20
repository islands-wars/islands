version = 0.1

val sourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc.get().destinationDir)
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            groupId = rootProject.group.toString()
            artifactId = project.name
            version = project.version.toString()

            artifact(sourceJar)
            artifact(javadocJar)

            pom {
                name.set(project.name)
                description.set("Utility classes for connecting to islands-wars database")
                url.set("https://github.com/islands-wars/commons")

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
                    connection.set("scm:git:git://github.com/islands-wars/commons.git")
                    developerConnection.set("scm:git:ssh://github.com:islands-wars/commons.git")
                    url.set("https://github.com/islands-wars/commons")
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/islands-wars/commons")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}