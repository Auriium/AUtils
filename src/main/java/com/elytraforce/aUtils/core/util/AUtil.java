package com.elytraforce.aUtils.core.util;

import com.elytraforce.aUtils.core.plugin.APlugin;
import com.google.inject.Inject;
import org.slf4j.Logger;

public abstract class AUtil<T extends APlugin> {

    @Inject protected Logger logger;

    public AUtil() {

    }
}
