package net.vexmos.lobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FlyListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (!player.hasPermission("group.dev") ||
                !player.hasPermission("group.diretor") ||
                !player.hasPermission("group.mod") ||
                !player.hasPermission("group.admin") ||
                !player.hasPermission("group.suporte") ||
                !player.hasPermission("group.construtor") ||
                !player.hasPermission("group.emerald") ||
                !player.hasPermission("group.diamond") ||
                !player.hasPermission("group.gold")) {

            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                return;
            }
            return;
        }

        if (!player.getAllowFlight()) {
            player.setAllowFlight(true);
            return;
        }
        player.setAllowFlight(true);
    }

}
