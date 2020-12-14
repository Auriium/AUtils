package com.elytraforce.aUtils.examples.config;

import com.elytraforce.aUtils.config.AConfig;
import com.elytraforce.aUtils.config.BConfig;

public class ExampleAConfig extends BConfig {
    //an example config

    @Override
    public String filePosition() {
        return "sex.yml";
    }

    @ConfigField(location = "database.storage",comment = "do you like guys?")
    public static String pluginTestField = "i like guys";


}
