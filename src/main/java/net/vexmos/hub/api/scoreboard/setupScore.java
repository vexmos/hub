package net.vexmos.hub.api.scoreboard;

import net.vexmos.hub.api.scoreboard.type.Scoreboard;
import net.vexmos.hub.api.scoreboard.type.SimpleScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class setupScore {

    private static Plugin instance;

    public static Plugin getPluginInstance() {
        return instance;
    }

    public static Scoreboard createScoreboard(Player holder) {
        return new SimpleScoreboard(holder);
    }

    public static void setPluginInstance(Plugin instance) {
        if (setupScore.instance != null) return;
        setupScore.instance = instance;
    }
}