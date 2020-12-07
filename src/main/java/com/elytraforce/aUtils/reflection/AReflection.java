package com.elytraforce.aUtils.reflection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.CompletableFuture;

public class AReflection {

    public static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    public static final String CRAFTBUKKIT = "org.bukkit.craftbukkit." + VERSION + '.';
    public static final String NMS = "net.minecraft.server." + VERSION + '.';

    private static final MethodHandle PLAYER_CONNECTION;
    private static final MethodHandle GET_HANDLE;
    private static final MethodHandle SEND_PACKET;

    static {
        Class<?> entityPlayer = getNMSClass("EntityPlayer");
        Class<?> craftPlayer = getCraftClass("entity.CraftPlayer");
        Class<?> playerConnection = getNMSClass("PlayerConnection");

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle sendPacket = null;
        MethodHandle getHandle = null;
        MethodHandle connection = null;
        try {
            connection = lookup.findGetter(entityPlayer, "playerConnection", playerConnection);
            getHandle = lookup.findVirtual(craftPlayer, "getHandle", MethodType.methodType(entityPlayer));
            sendPacket = lookup.findVirtual(playerConnection, "sendPacket", MethodType.methodType(void.class, getNMSClass("Packet")));
        } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        PLAYER_CONNECTION = connection;
        SEND_PACKET = sendPacket;
        GET_HANDLE = getHandle;
    }

    private AReflection() {
    }

    @Nullable
    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName(NMS + name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static CompletableFuture<Void> sendPacket(Player player, Object... packets) {
        return CompletableFuture.runAsync(() -> {
            try {
                Object handle = GET_HANDLE.invoke(player);
                Object connection = PLAYER_CONNECTION.invoke(handle);

                // Checking if the connection is not null is enough. There is no need to check if the player is online.
                if (connection != null) {
                    for (Object packet : packets) SEND_PACKET.invoke(connection, packet);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    @Nullable
    public static Class<?> getCraftClass(String name) {
        try {
            return Class.forName(CRAFTBUKKIT + name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
