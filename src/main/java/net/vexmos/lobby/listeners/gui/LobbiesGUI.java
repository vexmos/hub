package net.vexmos.lobby.listeners.gui;

import net.vexmos.lobby.api.CustomHeads;
import net.vexmos.lobby.api.GuiAPI;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LobbiesGUI implements Listener {

    private GuiAPI gui;
    private Player player;



    public void openLobbiesGui(Player player) throws IOException {

        this.player = player;
        this.gui = new GuiAPI("Lobbies", 3); // 3 rows

        String lobby1 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZiYzE4NzcyYWNkMzQzZTZhYjIwMTAzYzRhOWU0MjExNWYyMjQ4NDI3ZWNiZDIwNjcxZDZjZDI0ZWU3ZWY4YyJ9fX0=";
        String lobby2 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWI3ODFjOTUzZjg1ZGY3NzVmOTdjNjRmZWYyZjUwYjczMTM2NjI1NjgyN2UwZjg4YzIyOTcwYzIzMzE3YzY5NSJ9fX0=";

        ItemStack customHead = CustomHeads.create(lobby1);
        ItemStack item = customHead;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§eLobby #1 §7[CLIQUE]");

        meta.setLore(Arrays.asList("§aVocê está AQUI!", "§8[ONLINE]"));

        item.setItemMeta(meta);

        ItemStack customHead2 = CustomHeads.create(lobby2);
        ItemStack item2 = customHead2;
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName("§eLobby #2 §7[CLIQUE]");

        meta2.setLore(Arrays.asList("§cSem possibilidades de conexão.", "§8[OFFLINE]"));

        item2.setItemMeta(meta2);

        gui.getInventory().setItem(12, item);
        gui.getInventory().setItem(14, item2);


        gui.openInventory(player);

    }



    @EventHandler
    public void openLobbies(PlayerInteractEvent event) throws IOException {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if the item is not null and is a compass
        if (item != null && item.getType() == Material.REDSTONE_COMPARATOR) {
            switch (event.getAction()) {
                case RIGHT_CLICK_AIR:
                case RIGHT_CLICK_BLOCK:
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                    // Trigger the server selector GUI
                    openLobbiesGui(player);
                    break;
                default:
                    break;
            }
        }
    }
}