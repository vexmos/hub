package net.vexmos.hub.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ManagementListeners implements Listener {
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        Player p = event.getPlayer();
        if (!(p.getGameMode() == GameMode.CREATIVE)) {
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasDisplayName()) {
                    String displayName = meta.getDisplayName();
                    if (displayName.equals("§8- §eMeu Perfil §7[Clique]") ||
                            displayName.equals("§8- §eColetáveis §7[Clique]") ||
                            displayName.equals("§8- §eJogadores: §aVisíveis §7[Clique]") ||
                            displayName.equals("§8- §eJogadores: §cInvisíveis §7[Clique]") ||
                            displayName.equals("§8- §eSeletor de Lobby §7[Clique]") ||
                            displayName.equals("§8- §eMenu de Jogos §7[Clique]")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

//
//    @EventHandler
//    public void clickLobby(InventoryClickEvent event) {
//        if (event.getCurrentItem() != null) {
//            ItemStack item = event.getCurrentItem();
//            if (item.hasItemMeta()) {
//                ItemMeta meta = item.getItemMeta();
//                if (meta.hasDisplayName()) {
//                    String displayName = meta.getDisplayName();
//                    if (displayName.equals("§8- §eMeu Perfil §7[Clique]") ||
//                            displayName.equals("§8- §eColetáveis §7[Clique]") ||
//                            displayName.equals("§8- §eJogadores: §aVisíveis §7[Clique]") ||
//                            displayName.equals("§8- §eJogadores: §cInvisíveis §7[Clique]") ||
//                            displayName.equals("§8- §eSeletor de Lobby §7[Clique]") ||
//                            displayName.equals("§8- §eMenu de Jogos §7[Clique]")) {
//                        event.setCancelled(true);
//                    }
//                }
//            }
//        }
//    }
}

