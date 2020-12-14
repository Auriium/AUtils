package com.elytraforce.aUtils.plugin;

import com.google.inject.Singleton;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
public abstract class APlugin extends JavaPlugin implements AbstractPlugin<JavaPlugin> {

}

