package net.vexmos.lobby;

import net.vexmos.lobby.database.SetupConfig;
import net.vexmos.lobby.listeners.Listeners;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class VexmosHub extends JavaPlugin {

    @Override
    public void onEnable() {
        Listeners.setup();
    }

    @Override
    public void onDisable() {

    }

    public static VexmosHub get() {
        return VexmosHub.getPlugin(VexmosHub.class);
    }
}
