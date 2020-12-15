package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.spigot.SpigotPlugin;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class PluginTest extends SpigotPlugin {

    @Inject ALogger logger;
    @Inject SingletonTest test;

    @Override
    public void onEnable() {

        try {

            Injector injector = new BinderTest(this).createInjector();
            injector.injectMembers(this);

            test.call();

            this.logger.error("test of startup with interface sex time");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {

    }
}
