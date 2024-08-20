package net.vexmos.hub.listeners;

import net.vexmos.hub.listeners.gui.ServerSelectorGUI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.io.IOException;

public class PortalListener implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) throws IOException {
        Player player = event.getPlayer();
        event.setCancelled(true);
        player.teleport(player.getWorld().getSpawnLocation());
        ServerSelectorGUI gui = new ServerSelectorGUI();
        gui.opengui(player);
    }


}

