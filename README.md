# Spigot Plugin Boiler
This is supposed to serve as a default starting point for any Spigot plugin.

#### Setting up
To set up the project, you can download your own Spigot server JAR file and put it in [config/devserver](/config/devserver). This way you won't have to deal with the 20 seconds waiting time for the auto-downloaded one.

You should also configure [gradle.properties](/gradle.properties) and [settings.gradle.kts](/settings.gradle.kts) to be appropriate with your own plugin.

#### Testing
There are a few Gradle tasks that serves for testing purposes. I've made a [config/devserver](/config/devserver) folder that contains everything needed to get a testing environment up and running.

It's better to download your own Spigot server JAR file, but it will also fetch the specified Spigot version for you.

If you download your own, just put it in the [config/devserver](/config/devserver) folder with the appropriate name, and it'll be copied and executed instead of the downloaded one.

Once everything is done and you need to start testing your plugin. You can just run the tasks under `spigot-testserver` to do what you need. You can also run these tasks with debug mode to get a debugger for your plugin code.
