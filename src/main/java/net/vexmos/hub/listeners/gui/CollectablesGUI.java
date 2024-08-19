package net.vexmos.hub.listeners.gui;

import net.vexmos.hub.api.CustomHeads;
import net.vexmos.hub.api.GuiAPI;
import net.vexmos.hub.api.ItemAPI;
import net.vexmos.hub.database.ConnectSpigot;
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

public class CollectablesGUI implements Listener {



    private GuiAPI gui;
    private Player player;

    // CHECAR GRUPO DO JOGADOR
    private static final List<String> REQUIRED_PERMISSIONS = Arrays.asList(
            "diretor", "admin", "dev", "mod", "emerald", "gold", "diamond"
    );

    private boolean hasRequiredPermission(CommandSender sender) {
        ConnectSpigot db = new ConnectSpigot();
        String group = db.getPlayerGroup(player.getName());
        for (String groups : REQUIRED_PERMISSIONS) {
            if (group.equals(groups)) {
                return true;
            }
        }
        return false;
    }



    public void openCollectablesGui(Player player) {
        this.player = player;
        this.gui = new GuiAPI("Coletáveis", 4); // 3 rows
        ConnectSpigot db = new ConnectSpigot();
        String group = db.getPlayerGroup(player.getName());
        ItemAPI chapeus = new ItemAPI(Material.CHAINMAIL_HELMET).setName("§eChapéus").addLore("§aClique para ver seus chapéus.");
        ItemAPI pets = new ItemAPI(Material.BONE).setName("§ePets").addLore("§aClique para ver seus pets.");
        ItemAPI efeitos = new ItemAPI(Material.FIREBALL).setName("§eEfeitos").addLore("§aClique para ver seus efeitos.");


        // Adicionar opção de adquirir cristais
        String top = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTVmZDY3ZDU2ZmZjNTNmYjM2MGExNzg3OWQ5YjUzMzhkNzMzMmQ4ZjEyOTQ5MWE1ZTE3ZThkNmU4YWVhNmMzYSJ9fX0=";
        List<String> loreTop = Arrays.asList(" §8- §aClique AQUI para adquirir cristais", " §8- §aVocê pode adquirir diversas coisas com isso, hein...", "", "", "§bloja.vexmos.net");
        ItemStack customHead = CustomHeads.create(top);
        ItemStack item = customHead;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6§LⓋ §6Cristais");
        meta.setLore(loreTop);
        item.setItemMeta(meta);
        gui.getInventory().setItem(4, item);

        String avancarText2 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM5YzY3YTlmMTY4NWNkMWRhNDNlODQxZmU3ZWJiMTdmNmFmNmVhMTJhN2UxZjI3MjJmNWU3ZjA4OThkYjlmMyJ9fX0=";

        // Create the custom head ItemStack
        ItemStack cabecaAvancar2 = CustomHeads.create(avancarText2);
        ItemStack itemAvancar2 = cabecaAvancar2;
        ItemMeta avancar2 = itemAvancar2.getItemMeta();
        avancar2.setDisplayName("§eAvançar §8[1/1]");

        avancar2.setLore(Arrays.asList("§aClique para avançar de página."));

        itemAvancar2.setItemMeta(avancar2);

        gui.setItem(20, chapeus);
        gui.setItem(22, pets);
        gui.setItem(24, efeitos);
        gui.getInventory().setItem(8, itemAvancar2);

        gui.openInventory(player);
    }





    @EventHandler
    public void openCollectables(PlayerInteractEvent event) throws IOException {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if the item is not null and is a compass
        if (item != null && item.getType() == Material.CHEST) {
            switch (event.getAction()) {
                case RIGHT_CLICK_AIR:
                case RIGHT_CLICK_BLOCK:
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                    // Trigger the server selector GUI
                    openCollectablesGui(player);
                    break;
                default:
                    break;
            }
        }
    }
}