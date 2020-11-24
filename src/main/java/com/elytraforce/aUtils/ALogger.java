package com.elytraforce.aUtils;

import java.util.logging.Level;

public class ALogger {

    public static void logInfo(String text) {
        AUtil.getPlugin().getLogger().log(Level.INFO, text);
    }

    public static void logWarning(String text) {
        AUtil.getPlugin().getLogger().log(Level.WARNING, text);
    }

    public static void logError(String text) {
        AUtil.getPlugin().getLogger().log(Level.SEVERE, text);
    }

}
