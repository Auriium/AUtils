package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.command.ACommandManager;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.spigot.SpigotPlugin;
import com.google.inject.Inject;

public class PluginTest extends SpigotPlugin {

    @Inject private ALogger logger;
    @Inject private SingletonTest test;
    @Inject private ConfigTest config;
    @Inject private ASplitTest cm;
    @Inject private AValueTest cm2;
    @Inject private ACommandManager commandManager;

    @Override
    public String getPluginName() {
        return "PluginTest";
    }

    @Override
    public void onEnable() {

        try {

            new BinderTest(this).createInjector().injectMembers(this);

            test.call();

            this.logger.error("test of startup with interface sex time | i coupled my pp with your gf's anal port");

            this.debug = true;

            logger.debug("CHEESE MAN IS COMING");
            logger.error("HE SEES EVERYTHING");

            config.create().load();

            logger.error(config.godIsHere);

            commandManager.registerCommand(cm);
            commandManager.registerCommand(cm2);



        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {

    }
}
