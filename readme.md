# AUtils

Powerful utilities for plugin makers

# Features
- Hikari Database wrapper with async functionality
- EZ configs with annotations
- Math, Location, Reflection, utilities
- Particle animations, Chat formatter, Titles and Actionbars
- Item serializer (to Base64, readableString is planned)

# Dependencies
- Google GUICE

# How to use
First, make sure your plugin extends either SpigotPlugin or BungeePlugin, NOT Plugin or JavaPlugin
Next, you need a class that extends ABinder.
Finally, activate your injector and add your plugin to it!
If you need to bind more classes, override configureSpecific.

```
Injector injector = new BinderTest(this).createInjector();
injector.injectMembers(this);
```

# Examples
- For examples please check the package com.elytraforce.aExamples 
for a default plugin showcasing how to use dependency injection with
aUtils, as well as showcasing some of the features that aUtils has to offer.

# Maven integration

C'mon, you should know what to do with these

```
    <repository>
        <id>repsy</id>
        <name>autils-repo</name>
        <url>https://repo.repsy.io/mvn/{MY REPSY USERNAME}/{MY REPOSITORY NAME}</url>
    </repository>

    <dependency>
        <groupId>com.elytraforce</groupId>
        <artifactId>aUtils</artifactId>
        <version>2.0-SNAPSHOT</version>
    </dependency>

```