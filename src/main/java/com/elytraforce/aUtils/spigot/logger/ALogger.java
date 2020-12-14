package com.elytraforce.aUtils.spigot.logger;

import com.elytraforce.aUtils.util.AUtil;

public class ALogger {
    public static void error(String string) {
        AUtil.getUtils().error(string);
    }

    public static void info(String string) {
        AUtil.getUtils().info(string);
    }

    public static void warning(String string) {
        AUtil.getUtils().warning(string);
    }

    public static void debug(String string) {
        AUtil.getUtils().debug(string);
    }
}
