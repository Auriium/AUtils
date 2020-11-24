package com.elytraforce.examples;

import com.elytraforce.aUtils.config.AConfig;

public class ExampleAConfig extends AConfig {
    //an example config

    @Override
    public String filePosition() {
        return "sex.yml";
    }

    @ConfigField(location = "database.storage",comment = "do you like guys?")
    public static String pluginTestField = "i like guys";


}
