package com.elytraforce.aUtils.core.command;

import java.util.UUID;

/**
 * Wrapper containing something that can send commands and be interacted with.
 */
public interface ASenderWrapper<T> {

    public String getName();
    public UUID getUUID();
    public boolean hasPermission(String perm);

    public boolean isConsole();
    public boolean equals(ASenderWrapper sender);

    public void sendMessage(String string);

    public T getWrapped();
}
