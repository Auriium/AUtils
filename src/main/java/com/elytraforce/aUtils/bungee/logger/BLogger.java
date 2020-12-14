package com.elytraforce.aUtils.bungee.logger;

import com.elytraforce.aUtils.util.AUtil;
import com.elytraforce.aUtils.util.BUtil;

public class BLogger {
    public static void error(String string) {
        BUtil.getUtils().error(string);
    }

    public static void info(String string) {
        BUtil.getUtils().info(string);
    }

    public static void warning(String string) {
        BUtil.getUtils().warning(string);
    }

    public static void debug(String string) {
        BUtil.getUtils().debug(string);
    }
}