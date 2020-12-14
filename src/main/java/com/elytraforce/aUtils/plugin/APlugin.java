package com.elytraforce.aUtils.plugin;

import com.elytraforce.aUtils.core.AUtil;
import com.google.inject.Inject;
import org.bukkit.plugin.java.JavaPlugin;


public abstract class APlugin extends JavaPlugin implements AbstractPlugin<JavaPlugin> {

    @Inject protected AUtil util;

}

