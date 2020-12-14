package com.elytraforce.aUtils.spigot;

import com.elytraforce.aUtils.core.plugin.APlugin;
import com.elytraforce.aUtils.core.util.AUtil;
import com.google.inject.Inject;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPlugin extends JavaPlugin implements APlugin<JavaPlugin> {
    @Inject protected AUtil util;
}
