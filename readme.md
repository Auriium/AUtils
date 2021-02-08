# AUtils

Powerful utilities for plugin makers who aren't defined by the type of server

# Features
- AFeatures are interfaces and therefore support both bungee/spigot unless otherwise specified
- ADatakore - powerful HikariCP wrapper using CompletableFutures
- AConfig - powerful abstract configuration powered by annotations
- AMath and ALocations let developers quickly handle math related issues
- ALogger for simple logging 
- AItemBuilder and Serializer for handling items spigot-side
- APlugins wrapping bungee and spigot plugins respectively
- ABinder to handle binding of dependencies to their respective instances and implementations
- AChat for chat related things (Actionbars, Titles, and Colorstrings)
- Leaf Framework, a ridiculously powerful and simple command framework that
 automatically handles tabcomplete, arguments, senders, bungee/spigot support, variables, subcommands,
 all without touching a single string array. (Documentation coming soon!)
- *Inappropriate comments regarding the cheese man and other misuse of java naming conventions and debugging*

# Dependencies
- Google GUICE
- A working brain


# How to use
First, make sure your plugin extends either SpigotPlugin or BungeePlugin, NOT Plugin or JavaPlugin
Next, you need a class that extends ABinder.
Finally, create an injector via ABinder#createInjector and add your plugin instance to it! (<this> call)
If you need to bind more classes, override configureSpecific in your binder.

```
class extends SpigotPlugin {
    @Override
    onEnable() {
        Injector injector = new BinderTest(this).createInjector();
        injector.injectMembers(this);
    }
}
```

# Examples
- For examples please check the package com.elytraforce.aExamples 
for a default plugin showcasing how to use dependency injection with
aUtils, as well as showcasing some of the features that aUtils has to offer.

https://github.com/Auriium/AUtils/wiki

# Maven integration

C'mon, you should know what to do with these (Add dependency to dependencyManagement, repository to repositories)

```
    <dependency>
        <groupId>com.elytraforce</groupId>
        <artifactId>aUtils</artifactId>
        <version>2.1-SNAPSHOT</version>
    </dependency>

    <repository>
        <id>repsy</id>
        <url>https://repo.repsy.io/mvn/elytraforce/default</url>
    </repository>

```


