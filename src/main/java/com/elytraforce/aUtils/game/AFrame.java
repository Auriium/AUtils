package com.elytraforce.aUtils.game;

import org.bukkit.scheduler.BukkitTask;

import java.util.List;

@SuppressWarnings("unused")
public abstract class AFrame {

    public List<APlayerInterface> players;

    public abstract BukkitTask lobbyTickingStage();
    public abstract BukkitTask preRoundStage();
    public abstract BukkitTask inRoundStage();
    public abstract BukkitTask postRoundStage();

    public abstract void addPlayer(APlayerInterface player);
    public abstract void removePlayer(APlayerInterface player);

}
