# <img src="https://raw.githubusercontent.com/islands-wars/guidelines/master/ASSETS/icon.png" width="64"> Islands Wars

![build status](https://github.com/islands-wars/islands/actions/workflows/build.yml/badge.svg)

> Islands Wars is a Minecraft sky block server.

> Main plugin's API for paper.


# Getting started & generality
---

These instructions will get you a copy of the project up. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them
* [JDK] is the Java Development Kit, Islands Wars need version 21 or higher.

### Git flow

We use git flow as branching-model, simply init a new feature :
```shell
$> git flow feature start featureName
$> git add file
$> git commit -m "Clear commit message"

$> git flow feature publish featureName
```
And then open a new pull request. In the case that you see an error on production (master branch), you can supply a PR using hotfix instead of a feature.
Please refer to this [cheatsheet] if you are new to git flow and wants to learn the basics.

### Run server

Use gradle task :
```shell
$> gradle runServer
```


# License
---

> GNU GENERAL PUBLIC LICENSE Version 3