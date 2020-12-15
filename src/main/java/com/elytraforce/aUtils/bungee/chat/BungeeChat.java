package com.elytraforce.aUtils.bungee.chat;

import com.elytraforce.aUtils.core.chat.AChat;
import com.elytraforce.aUtils.core.chat.DefaultFontInfo;
import com.google.common.base.Strings;
import com.google.inject.Singleton;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public class BungeeChat implements AChat {
    @Override
    public String colorString(String string) {
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    @Override
    public String colorString(Strings... strings) {
        StringBuffer sb = new StringBuffer();
        Arrays.stream(strings).forEach(s -> {
            sb.append(s);
            sb.append(" ");
        });

        return sb.toString();
    }

    @Override
    public ArrayList<String> colorStrings(String... strings) {
        ArrayList<String> collect = new ArrayList<>();
        Arrays.stream(strings).forEach(s -> {
            collect.add(colorString(s));
        });

        return collect;
    }

    @Override
    public ArrayList<String> colorStrings(List<String> strings) {
        ArrayList<String> collect = new ArrayList<>();
        strings.forEach(s -> {
            collect.add(colorString(s));
        });

        return collect;
    }

    @Override
    public String centerMessage(String message) {
        if (message == null || message.equals("")) return "";
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
            }else if(previousCode){
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int CENTER_PX = 154;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }

        return sb.toString() + message;
    }
}
