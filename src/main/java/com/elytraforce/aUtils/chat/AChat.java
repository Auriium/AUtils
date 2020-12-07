package com.elytraforce.aUtils.chat;

import com.elytraforce.aUtils.chat.models.AbstractChat;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AChat extends AbstractChat {

    public static String colorString(String string) {
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    public static List<String> colorString(List<String> strings) {
        ArrayList<String> stringList = new ArrayList<>();
        for (String string : strings) { stringList.add(colorString(string)); }

        return stringList;
    }



}
