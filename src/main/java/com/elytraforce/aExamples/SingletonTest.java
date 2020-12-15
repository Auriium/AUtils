package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.logger.ALogger;
import com.google.inject.Inject;
import com.google.inject.Singleton;


@Singleton
public class SingletonTest {

    @Inject private PluginTest plugin;
    @Inject private ALogger logger;

    private double number;

    @Inject
    public SingletonTest(PluginTest test, ALogger logger) {
        number = Math.random();
        logger.error(number + " buckets of chum in the hall");
    }

    public void call() {
        logger.error(number + " buckets of chum in the hall");
    }
}
