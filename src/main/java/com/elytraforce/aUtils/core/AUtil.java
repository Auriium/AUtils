package com.elytraforce.aUtils.core;

import com.elytraforce.aUtils.plugin.AbstractPlugin;
import com.google.inject.Inject;
import org.slf4j.Logger;

public abstract class AUtil<T> {
    protected AbstractPlugin pluginInstance;
    protected boolean isDebug;
    protected Logger logger;
    protected String pluginName;
}
