package com.elytraforce.aUtils.core.util;

import com.elytraforce.aUtils.core.plugin.APlugin;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;

public abstract class ABinder extends AbstractModule {
    private final APlugin plugin;

    public ABinder(APlugin plugin) {
        this.plugin = plugin;
    }

    public abstract Injector createInjector();

    public abstract void configureSpecific();

    @Override
    public final void configure() {
        this.bind(APlugin.class).toInstance(this.plugin);

        configureSpecific();
    }
}
