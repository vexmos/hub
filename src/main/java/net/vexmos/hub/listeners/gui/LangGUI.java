package net.vexmos.hub.listeners.gui;

import net.vexmos.hub.api.CustomHeads;
import net.vexmos.hub.api.GuiAPI;
import net.vexmos.hub.database.ConnectSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.Arrays;

public class LangGUI implements Listener {

    private GuiAPI gui;
    private Player player;

    ConnectSpigot db = new ConnectSpigot();


    public void openLangGUI(Player player) throws IOException {
        this.player = player;
        if(db.getPlayerLanguage(player.getName()).equals("en_US")) {
            this.gui = new GuiAPI("Change Language", 3); // 3 rows
        }
        if(db.getPlayerLanguage(player.getName()).equals("pt_BR")) {
            this.gui = new GuiAPI("Alterar idioma", 3); // 3 rows
        }

        String ptBR = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY2OGExZmI2YWY4MWIyMzFiYmNjNGRlNWY3Zjk1ODAzYmJkMTk0ZjU4MjdkYTAyN2ZhNzAzMjFjZjQ3YyJ9fX0=";
        String enUS = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmNiYzMyY2IyNGQ1N2ZjZGMwMzFlODUxMjM1ZGEyZGFhZDNlMTkxNGI4NzA0M2JkMDEyNjMzZTZmMzJjNyJ9fX0=";

        ItemStack customHead = CustomHeads.create(ptBR);
        ItemStack item = customHead;
        ItemMeta meta = item.getItemMeta();



        ItemStack customHead2 = CustomHeads.create(enUS);
        ItemStack item2 = customHead2;
        ItemMeta meta2 = item2.getItemMeta();



        if(db.getPlayerLanguage(player.getName()).equals("en_US")) {
            meta.setDisplayName("§eBrazilian Portuguese §8(pt_BR)");
            meta2.setDisplayName("§eUSA English §8(en_US)");
            meta2.setLore(Arrays.asList("§7Brazilian Portuguese has a", "§7melodious accent and grammar adapted to its people.", "", "§cYou are using this language."));
            meta.setLore(Arrays.asList("§7Brazilian Portuguese has a", "§7melodious accent and grammar adapted to its people.", "", "§aClick to apply."));
        }
        if(db.getPlayerLanguage(player.getName()).equals("pt_BR")) {
            meta.setDisplayName("§ePortuguês do Brasil §8(pt_BR)");
            meta2.setDisplayName("§eInglês dos EUA §8(en_US)");
            meta.setLore(Arrays.asList("§7O português do Brasil tem um sotaque melodioso", "§7melodioso e uma gramática adaptada ao seu povo.", "", "§cVocê está usando este idioma."));
            meta2.setLore(Arrays.asList("§7O inglês dos EUA é conhecido por sua", "§7pronúncia clara e pela influência cultural global.", "", "§aClique para aplicar."));
        }
        item.setItemMeta(meta);
        item2.setItemMeta(meta2);
        gui.getInventory().setItem(12, item);
        gui.getInventory().setItem(14, item2);


        gui.openInventory(player);

    }

    @EventHandler
    public void lang(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        String displayName = clickedItem.getItemMeta().getDisplayName();

        if (displayName.equals("§ePortuguês do Brasil §8(pt_BR)") || displayName.equals("§eBrazilian Portuguese §8(pt_BR)")) {
            event.setCancelled(true);
            player.closeInventory();
            if(db.getPlayerLanguage(player.getName()).equals("pt_BR")) {
                player.sendMessage("§cVocê já está utilizando esse idioma.");
                return;
            }
            if(db.getPlayerLanguage(player.getName()).equals("en_US")) {
                player.sendMessage("§aYou have changed your language successfully.");
                player.sendMessage("§8[Você alterou seu idioma com sucesso]");
                db.setPlayerLanguage(player.getName(), "pt_BR");
            }
        }
        if (displayName.equals("§eUSA English §8(en_US)") || displayName.equals("§eInglês dos EUA §8(en_US)")) {
            event.setCancelled(true);
            player.closeInventory();
            if(db.getPlayerLanguage(player.getName()).equals("en_US")) {
                player.sendMessage("§cYou are already using that language.");
                return;
            }
            if(db.getPlayerLanguage(player.getName()).equals("pt_BR")) {
                player.sendMessage("§aVocê alterou seu idioma com sucesso.");
                player.sendMessage("§8[You have changed your language successfully]");
                db.setPlayerLanguage(player.getName(), "en_US");
            }

        }
    }
}
