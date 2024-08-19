package net.vexmos.hub;

import net.vexmos.hub.listeners.Listeners;
import org.bukkit.plugin.java.JavaPlugin;

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
