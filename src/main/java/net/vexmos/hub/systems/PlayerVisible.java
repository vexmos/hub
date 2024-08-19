package net.vexmos.hub.systems;

import net.vexmos.hub.database.ConnectSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerVisible implements Listener {

    private static ConnectSpigot database;
    private Map<UUID, Long> cooldowns = new HashMap<>();

    public PlayerVisible(ConnectSpigot database) {
        PlayerVisible.database = database;
    }

    @EventHandler
    public void notJoinVisibility(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        boolean isVisible = database.isPlayerVisible(player.getName());
        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            boolean isVisibleOthers = database.isPlayerVisible(otherPlayer.getName());
            if (!(isVisibleOthers)) {
                player.hidePlayer(player);
            }
        }
        if (isVisible) {
            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                player.showPlayer(otherPlayer);
            }
        } else {
            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(otherPlayer);
            }
        }
    }

    @EventHandler
    public void notJoinVisibility(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean isVisible = database.isPlayerVisible(player.getName());
        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            boolean isVisibleOthers = database.isPlayerVisible(otherPlayer.getName());
            if (!(isVisibleOthers)) {
                player.hidePlayer(player);
            }
        }
        if (isVisible) {
            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                player.showPlayer(otherPlayer);
            }
        } else {
            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(otherPlayer);
            }
        }
    }

    @EventHandler
    public void changeVisibility(PlayerInteractEvent event) throws IOException {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        boolean isVisible = database.isPlayerVisible(player.getName());
        // Verifica se o item não é nulo e se o clique foi com o botão direito
        if (item != null && event.getAction().toString().contains("RIGHT_CLICK")) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                // Verifica se o nome do item contém "§aVisíveis"
                if (meta.getDisplayName().contains("§aVisíveis")) {
                    // Verifica se o cooldown não está ativo
                    if (!cooldowns.containsKey(player.getUniqueId()) || cooldowns.get(player.getUniqueId()) + 3000 < System.currentTimeMillis()) {
                        // Troca o item para "§cInvisíveis"
                        if (isVisible) {
                            ItemStack newItem = new ItemStack(Material.INK_SACK, 1, (short) 8); // Light gray dye
                            ItemMeta newMeta = newItem.getItemMeta();
                            newMeta.setDisplayName("§8- §eJogadores: §cInvisíveis §7[Clique]");
                            newMeta.setLore(Collections.singletonList("§8Clique para ativar os jogadores."));
                            newItem.setItemMeta(newMeta);
                            player.sendMessage("§eOs jogadores agora estão §cINVISÍVEIS§e.");

                            player.getInventory().setItemInHand(newItem);
                            player.updateInventory();
                            database.setPlayerVisible(player.getName(), false);
                            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                                if (!otherPlayer.equals(player)) { // Garante que o jogador não seja escondido de si mesmo
                                    player.hidePlayer(otherPlayer);
                                }
                            }
                            // Adiciona o cooldown
                            cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
                        }
                    } else {
                        player.sendMessage("§cEspere alguns instantes para fazer isto novamente.");
                    }

                    // Verifica se o nome do item contém "§cInvisíveis"
                } else if (meta.getDisplayName().contains("§cInvisíveis")) {
                    // Verifica se o cooldown não está ativo
                    if (!cooldowns.containsKey(player.getUniqueId()) || cooldowns.get(player.getUniqueId()) + 3000 < System.currentTimeMillis()) {
                        if (!(isVisible)) {
                            ItemStack newItem = new ItemStack(Material.INK_SACK, 1, (short) 10); // Light green dye
                            ItemMeta newMeta = newItem.getItemMeta();
                            newMeta.setDisplayName("§8- §eJogadores: §aVisíveis §7[Clique]");
                            newMeta.setLore(Collections.singletonList("§8Clique para desativar os jogadores."));
                            newItem.setItemMeta(newMeta);
                            player.sendMessage("§eOs jogadores agora estão §aVISÍVEIS§e.");

                            player.getInventory().setItemInHand(newItem);
                            player.updateInventory();
                            database.setPlayerVisible(player.getName(), true);
                            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                                if (!otherPlayer.equals(player)) { // Garante que o jogador não seja escondido de si mesmo
                                    player.showPlayer(otherPlayer);
                                }
                            }
                            // Adiciona o cooldown
                            cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
                        }
                    } else {
                        player.sendMessage("§cEspere alguns instantes para fazer isto novamente.");
                    }
                }
            }
        }
    }





}


