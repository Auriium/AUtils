package com.elytraforce.aUtils.bungee;

import com.elytraforce.aUtils.core.plugin.APlugin;
import net.md_5.bungee.api.plugin.Plugin;
public class BungeePlugin extends Plugin implements APlugin<Plugin> {

    @Override
    public Plugin getPlugin() {
        return this;
    }
}
