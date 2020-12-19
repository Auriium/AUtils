package com.elytraforce.aUtils.core.logger;

/**
 * Interface representing a logger that can log, warn, and debug information from a string.
 * Mainly used to demonstrate how binding works to ElytraForce newbie devs as slf4j fills the
 * logging role much better than ALogger typically.
 */
public interface ALogger {

    public void log(String string);
    public void info(String string);
    public void warning(String string);
    public void error(String string);
    public void debug(String string);

}

