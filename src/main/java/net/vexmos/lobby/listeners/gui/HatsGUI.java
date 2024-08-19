package net.vexmos.lobby.listeners.gui;

import net.vexmos.lobby.api.CustomHeads;
import net.vexmos.lobby.api.GuiAPI;
import net.vexmos.lobby.api.ItemAPI;
import net.vexmos.lobby.database.ConnectSpigot;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HatsGUI implements Listener {

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

    public void openHatsMenu(Player player) {
        this.player = player;
        this.gui = new GuiAPI("Seus Chapéus", 5); // 3 rows
        gui.clearInventorySlots(gui.getInventory());
        ConnectSpigot db = new ConnectSpigot();
        String group = db.getPlayerGroup(player.getName());
        ItemAPI chapeu1 = new ItemAPI(Material.LEATHER_HELMET).setName("§eThomas Shelby").addLore("\n§eUm chapéu para um mafioso...\n\n§8- §bRaridade: §7Comum\n\n§aClique para vestir.");
        ItemAPI chapeu1Usando = new ItemAPI(Material.LEATHER_HELMET).setName("§eThomas Shelby").addLore("\n§eUm chapéu para um mafioso...\n\n§8- §bRaridade: §7Comum\n\n§cClique para remover.");




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

        String backText = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWExZWYzOThhMTdmMWFmNzQ3NzAxNDUxN2Y3ZjE0MWQ4ODZkZjQxYTMyYzczOGNjOGE4M2ZiNTAyOTdiZDkyMSJ9fX0=";
        // Create the custom head ItemStack
        ItemStack cabecaVoltar = CustomHeads.create(backText);
        ItemStack itemVoltar = cabecaVoltar;
        ItemMeta voltar = itemVoltar.getItemMeta();
        voltar.setDisplayName("§eVoltar §8[2/2]");

        voltar.setLore(Arrays.asList("§aClique para voltar de página."));

        itemVoltar.setItemMeta(voltar);
        gui.getInventory().setItem(0, itemVoltar);

        if(!(isWearingSpecificHat(player, "§eThomas Shelby"))) {
            gui.setItem(20, chapeu1);
        } else {
            gui.setItem(20, chapeu1Usando);
        }

        gui.getInventory().setItem(8, itemAvancar2);

        gui.openInventory(player);
    }

    @EventHandler
    public void onJoinHat(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        p.getInventory().setHelmet(new ItemStack(Material.AIR));
    }

    private boolean isWearingSpecificHat(Player player, String hatName) {
        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet == null || !helmet.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = helmet.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals(hatName);
    }

    @EventHandler
    public void chapeus(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        String displayName = clickedItem.getItemMeta().getDisplayName();
        if (displayName.equals("§eThomas Shelby")) {
            if(event.getView().getTitle().equals("Seus Chapéus")) {
                openHatsMenu(player);
            }
            event.setCancelled(true);
            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
            ItemMeta meta = helmet.getItemMeta();
            meta.setDisplayName("§eThomas Shelby");
            meta.setLore(Collections.singletonList("§cClique para remover."));
            helmet.setItemMeta(meta);

            // Verificar se o jogador já está usando o chapéu
            if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().equals(helmet)) {
                player.getInventory().setHelmet(new ItemStack(Material.AIR));
                player.sendMessage("§cVocê removeu o chapéu " + displayName + "§a!");
            } else {

                player.getInventory().setHelmet(helmet); // Adiciona o chapéu
                player.sendMessage("§aVocê vestiu o chapéu " + displayName + "§a!");
            }
        }
    }
}
