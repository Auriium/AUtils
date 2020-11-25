# AUtils

Powerful utilities for plugin makers

# Features
- Hikari Database wrapper with async functionality
- EZ configs with annotations
- Math, Location, Reflection, utilities
- Particle animations, Chat formatter, Titles and Actionbars
- Item serializer (to Base64, readableString is planned)

# How to use
Simply create a new instance of AUtil and pass your main class on startup.
From there on out its as simple as using the various static utils!

# Usage
Shade and relocate AUtils, then

`new AUtils(your main class)` on startup. You don't need to cache it if you shaded and relocated.

Congrats! You can now make static calls to every single method!

# Maven integration

C'mon, you should know what to do with these

```
    <repository>
        <id>repsy</id>
        <name>My Private Maven Repositoty on Repsy</name>
        <url>https://repo.repsy.io/mvn/{MY REPSY USERNAME}/{MY REPOSITORY NAME}</url>
    </repository>

    <dependency>
        <groupId>com.elytraforce</groupId>
        <artifactId>aUtils</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

```