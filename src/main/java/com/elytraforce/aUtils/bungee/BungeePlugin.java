package com.elytraforce.aUtils.bungee;

import com.elytraforce.aUtils.core.plugin.APlugin;
import net.md_5.bungee.api.plugin.Plugin;
public abstract class BungeePlugin extends Plugin implements APlugin<Plugin> {

    protected boolean debug;

    public boolean isDebug() { return this.debug; }

    @Override
    public Plugin getPlugin() {
        return this;
    }
}
