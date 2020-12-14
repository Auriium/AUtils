package com.elytraforce.aUtils.plugin;

import com.elytraforce.aUtils.core.AUtil;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;

public abstract class ABinder extends AbstractModule {
    private final AbstractPlugin plugin;

    public ABinder(AbstractPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract Injector createInjector();

    public abstract void configureSpecific();

    @Override
    public final void configure() {
        this.bind(AbstractPlugin.class).toInstance(this.plugin);

        configureSpecific();
    }
}
