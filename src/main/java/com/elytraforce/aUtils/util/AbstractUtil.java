package com.elytraforce.aUtils.util;

import org.slf4j.Logger;

public class AbstractUtil<T> {

    protected T pluginInstance;
    protected boolean isDebug;
    protected Logger logger;
    protected String pluginName;

    public AbstractUtil(T using) {
        this.pluginInstance = using;
    }

    public AbstractUtil<T> withDebug(boolean bool) {
        this.isDebug = bool;
        return this;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public void error(String string) {
        logger.error(string);
    }

    public void warning(String string) {
        logger.warn(string);
    }

    public void info(String string) {
        logger.info(string);
    }

    public void debug(String string) {
        if (isDebug) {
            logger.error(string);
        }
    }

}
