package net.vexmos.hub.listeners;

import org.bukkit.block.Block;
import net.vexmos.hub.api.LinkWithSystem;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemFramesListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if the action is a right-click on a block
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = (Block) event.getClickedBlock();

            // Check if the clicked block is a barrier
            if (block != null && ((org.bukkit.block.Block) block).getType() == Material.BARRIER) {
                // Perform your custom logic here
                LinkWithSystem link = new LinkWithSystem();
                link.checkDistanceToExperiencia(event.getPlayer());
                link.checkDistanceToEventos(event.getPlayer());
            }
        }
    }

}
