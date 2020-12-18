package com.elytraforce.aUtils.core.command;

import java.util.UUID;

public interface ACommandSender {

    public String getName();
    public UUID getUUID();
    public boolean hasPermission(String perm);

    public boolean isConsole();

    public void sendMessage(String string);
}
