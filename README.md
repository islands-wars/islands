# <img src="https://raw.githubusercontent.com/islands-wars/guidelines/master/assets/icon.png" width="64"> Islands Wars - Api


> Islands Wars is a Minecraft multiplayer server network.

> This project is a spigot plugin interface with our infrastructure.

---

# Contributing

Please refer to this [file](https://github.com/islands-wars/guidelines/blob/master/README.md) when contributing to ensure everything is setup correctly.
Don't forget to use our code style and header, write good commit message and use Java conventions.
Ensure your code compile and your tests run.

---

# Debugging and setup

In order to setup a live server, you need to run a few gradle tasks.

- Build the paperclip buildtools to cache dependencies in .m2 according to the project [version](https://github.com/islands-wars/islands/blob/master/gradle.properties#L4)
  You will need for windows user maven and java set to your PATH, clone [paper](https://github.com/PaperMC/Paper) and run ``./paper install``
- To create the serv/ folder, run ```gradlew setupServer```
- Every time you want to start it, run ```gradlew startDevServer```
- You can test your modifications on the fly by running ``gradlew deployPlugin``

---

# Wiki

todo

---

# Javadoc

todo

---

# License

---

> GNU GENERAL PUBLIC LICENSE Version 3, see [LICENSE](https://github.com/islands-wars/islands/blob/master/LICENSE) file.