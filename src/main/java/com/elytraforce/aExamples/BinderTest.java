package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.plugin.APlugin;
import com.elytraforce.aUtils.core.util.ABinder;

public class BinderTest extends ABinder {
    private PluginTest pluginTest;

    public BinderTest(PluginTest plugin) {
        super(plugin);

        this.pluginTest = plugin;
    }

    @Override
    public void configureSpecific() {
        this.bind(PluginTest.class).toInstance(pluginTest);
    }
}
