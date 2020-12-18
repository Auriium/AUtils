package com.elytraforce.aUtils.core.config;

import com.elytraforce.aUtils.core.chat.AChat;
import com.google.inject.Inject;

public abstract class AMessageConfig extends AConfig {

    @Inject protected AChat chat;

    //Overridable
    @Override
    public String filePosition() {
        return "messages.yml";
    }

    public abstract String getPrefixInternal();

    public String getMessage(String string) {
        return chat.colorString(getPrefixInternal() + string);
    }

    public String getMessageArgs(String string, Object... args) {
        return chat.colorString(getPrefixInternal() + String.format(string,args));
    }
}
