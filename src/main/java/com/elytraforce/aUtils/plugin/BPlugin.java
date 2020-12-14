package com.elytraforce.aUtils.plugin;

import com.elytraforce.aUtils.core.AUtil;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.md_5.bungee.api.plugin.Plugin;


public abstract class BPlugin extends Plugin implements AbstractPlugin<Plugin> {

    @Inject protected AUtil util;

}