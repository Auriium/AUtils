package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.config.AConfig;
import com.google.inject.Singleton;


@Singleton
public class ConfigTest extends AConfig {

    @Override
    public String filePosition() {
        return "cheeseman.yml";
    }

    @AField(location="mysql.sex_hole")
    public String godIsHere = "default value";
}
