package net.vexmos.hub;

import net.vexmos.hub.api.scoreboard.setupScore;
import net.vexmos.hub.listeners.Listeners;
import net.vexmos.hub.listeners.ScoreboardListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class VexmosHub extends JavaPlugin {

    @Override
    public void onEnable() {
        Listeners.setup();
        setupScore.setPluginInstance(VexmosHub.get());
        new ScoreboardListener();
    }

    @Override
    public void onDisable() {

    }

    public static VexmosHub get() {
        return VexmosHub.getPlugin(VexmosHub.class);
    }
}
