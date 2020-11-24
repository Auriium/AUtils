package com.elytraforce.aUtils.chat;

import com.elytraforce.aUtils.particles.AParticle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AChat {

    public static String colorString(String string) {
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    public static List<String> colorString(List<String> strings) {
        ArrayList<String> stringList = new ArrayList<>();
        for (String string : strings) { stringList.add(colorString(string)); }

        AParticle.simple(null, Particle.BLOCK_DUST);

        return stringList;


    }

}
