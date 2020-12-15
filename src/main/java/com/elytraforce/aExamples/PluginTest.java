package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.spigot.SpigotPlugin;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class PluginTest extends SpigotPlugin {

    @Inject ALogger logger;
    @Inject SingletonTest test;
    @Inject ConfigTest config;
    @Inject CommandTest command;

    @Override
    public String getPluginName() {
        return "PluginTest";
    }

    @Override
    public void onEnable() {

        try {

            Injector injector = new BinderTest(this).createInjector();
            injector.injectMembers(this);

            test.call();

            this.logger.error("test of startup with interface sex time | i coupled my pp with your gf's anal port");

            this.debug = true;

            logger.debug("CHEESE MAN IS COMING");
            logger.error("HE SEES EVERYTHING");

            config.create().load();

            logger.error(config.godIsHere);

            this.getCommand("testCommand").setExecutor(command);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {

    }
}
