# <img src="https://raw.githubusercontent.com/islands-wars/guidelines/master/ASSETS/icon.png" width="64"> Islands Wars

![build status](https://github.com/islands-wars/islands/actions/workflows/build.yml/badge.svg)

> Islands Wars is a Minecraft sky block server.

> Main plugin's API for paper.


# Getting Started & Generality
---

These instructions will get you a copy of the project up and running. See deployment for notes on how to deploy the
project on a live system.

### Prerequisites

What things you need to install the software and how to install them

* [JDK] is the Java Development Kit, Islands Wars needs version 21 or higher.

### Git flow

We use git flow as branching model, simply initiate a new feature :

```shell
$> git flow feature start featureName
$> git add file
$> git commit -m "Clear commit message"

$> git flow feature publish featureName
```

And then open a new pull request. In the case that you see an error on production (master branch), you can supply a PR
using hotfix instead of a feature.
Please refer to this [cheatsheet] if you are new to git flow and want to learn the basics.

# Use as dependency
---

To use the islands'api to develop a new plugin, simply add :

```kotlin
maven {
    url = uri("https://maven.pkg.github.com/islands-wars/islands")
    credentials {
        username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
        password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
    }
}
```

And add your credentials inside `gradle.properties` file.

# Run local server
---

List of environment variables needed:

- SERVER_TYPE: HUB
- SERVER_ID: randomuuid
- DEBUG: true

### Using jpenilla run paper plugin

Use gradle task (it will run shadowJar before) :

```shell
$> ./gradlew runServer
```

### Using Docker

Create a Docker configuration to run the services first, then the `shadowJar` task, then the papermc task.
The container files will be mounted to ./papermc/ folder which is ignored by git.

# License
---

> GNU GENERAL PUBLIC LICENSE Version 3

[JDK]: https://www.oracle.com/fr/java/technologies/downloads/#java21

[cheatsheet]: https://danielkummer.github.io/git-flow-cheatsheet/