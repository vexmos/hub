package net.vexmos.lobby.listeners.gui;

import net.vexmos.lobby.api.CustomHeads;
import net.vexmos.lobby.api.GuiAPI;
import net.vexmos.lobby.api.ItemAPI;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class ServerSelectorGUI implements Listener {

    private GuiAPI gui;
    private Player player;

    public void opengui(Player player) throws IOException {
        String pastebinRawUrl = "https://pastebin.com/raw/qkmcFNAw";
        URL url = new URL(pastebinRawUrl);
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        StringBuilder content = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();

        String[] lines = content.toString().split("\n");
        Map<String, String> versions = new HashMap<>();

        for (String lineContent : lines) {
            String[] keyValue = lineContent.split(" = ");
            if (keyValue.length == 2) {
                versions.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        String pvpVersion = versions.get("pvp");
        String lobbyVersion = versions.get("lobby");
        String skywarsVersion = versions.get("skywars");
        String duelsVersion = versions.get("duels");
        String bedwarsVersion = versions.get("bedwars");

        this.player = player;
        this.gui = new GuiAPI("Menu de Jogos", 3); // 3 rows


        // Create items for the GUI
        ItemAPI server1Item = new ItemAPI(Material.DIAMOND_SWORD).setName("§ePvP §8[" + pvpVersion + "]").addLore("§aClique para conectar-se\n\n§fJogadores: §b0");
        ItemAPI server2Item = new ItemAPI(Material.BOOKSHELF).setName("§eLobby").addLore("§aClique para conectar-se");
        ItemAPI server3Item = new ItemAPI(Material.BED).setName("§eBedWars §8[" + bedwarsVersion + "]").addLore("§aClique para conectar-se");
        ItemAPI server4Item = new ItemAPI(Material.DIAMOND_PICKAXE).setName("§eRankUP §8[" + bedwarsVersion + "]").addLore("§aClique para conectar-se");




        String textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTc5ODBiOTQwYWY4NThmOTEwOTQzNDY0ZWUwMDM1OTI4N2NiMGI1ODEwNjgwYjYwYjg5YmU0MjEwZGRhMGVkMSJ9fX0=";

        // Create the custom head ItemStack
        ItemStack customHead = CustomHeads.create(textureValue);
        ItemStack item = customHead;
        ItemMeta meta = item.getItemMeta();
        server4Item.addEnchantment(Enchantment.ARROW_FIRE, 1);
        meta.setDisplayName("§eSky Wars §8[" + skywarsVersion + "]");

        meta.setLore(Arrays.asList("§aClique para conectar-se", "", "§fJogadores: §b0"));

        item.setItemMeta(meta);

        String avancarText = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM5YzY3YTlmMTY4NWNkMWRhNDNlODQxZmU3ZWJiMTdmNmFmNmVhMTJhN2UxZjI3MjJmNWU3ZjA4OThkYjlmMyJ9fX0=";

        // Create the custom head ItemStack
        ItemStack cabecaAvancar = CustomHeads.create(avancarText);
        ItemStack itemAvancar = cabecaAvancar;
        ItemMeta avancar = itemAvancar.getItemMeta();
        avancar.setDisplayName("§eAvançar §8[1/1]");

        avancar.setLore(Arrays.asList("§aClique para avançar de página."));

        itemAvancar.setItemMeta(avancar);





        // Add items to the GUI
        gui.setItem(11, server1Item);
        gui.setItem(9, server2Item);
        gui.setItem(13, server3Item);
        gui.setItem(14, server4Item);
        gui.getInventory().setItem(12, item);
        gui.getInventory().setItem(26, itemAvancar);

        // Open the GUI for the player
        gui.openInventory(player);
        Inventory inventory = player.getInventory();


    }



    @EventHandler
    public void openServers(PlayerInteractEvent event) throws IOException {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if the item is not null and is a compass
        if (item != null && item.getType() == Material.COMPASS) {
            switch (event.getAction()) {
                case RIGHT_CLICK_AIR:
                case RIGHT_CLICK_BLOCK:
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                    // Trigger the server selector GUI
                    opengui(player);
                    break;
                default:
                    break;
            }
        }
    }
}