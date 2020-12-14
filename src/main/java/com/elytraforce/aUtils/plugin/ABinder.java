package com.elytraforce.aUtils.plugin;

import com.elytraforce.aUtils.util.AUtil;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class ABinder extends AbstractModule {
    private final AbstractPlugin plugin;
    private final AUtil util;

    public ABinder(AbstractPlugin plugin, AUtil util) {
        this.plugin = plugin;
        this.util = util;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        this.bind(AbstractPlugin.class).toInstance(this.plugin);
        this.bind(AUtil.class).toInstance(util);
    }
}
