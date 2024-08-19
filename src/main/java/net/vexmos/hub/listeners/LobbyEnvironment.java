package net.vexmos.hub.listeners;

import net.vexmos.hub.VexmosHub;
import net.vexmos.hub.api.ItemAPI;
import net.vexmos.hub.api.LinkWithSystem;
import net.vexmos.hub.api.ScoreboardWrapper;
import net.vexmos.hub.database.ConnectSpigot;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class LobbyEnvironment implements Listener {

    private final LinkWithSystem linkWithSystem;
    private static ConnectSpigot database;



    public LobbyEnvironment(LinkWithSystem linkWithSystem, ConnectSpigot database) {
        this.linkWithSystem = linkWithSystem;
        LobbyEnvironment.database = database;
    }

    @EventHandler
    public void firstLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        database.registerFirstLogin(playerName);
        openScore(player);
    }


    @EventHandler
    public void itemsJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        linkWithSystem.teleportToLobby(player);
        boolean isVisible = database.isPlayerVisible(player.getName());


        // Create items
        ItemStack compass = new ItemAPI(Material.COMPASS)
                .setName("§8- §eMenu de Jogos §7[Clique]")
                .addLore("§8Clique para ver os modos de jogo do servidor.")
                .addEnchantment(Enchantment.LURE, 1)
                .build();

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(player.getName()); // Set the owner to the player's name
        skull.setItemMeta(meta);

// You can then add additional metadata like name and lore
        ItemMeta itemMeta = skull.getItemMeta();
        itemMeta.setDisplayName("§8- §eMeu Perfil §7[Clique]");
        itemMeta.setLore(Arrays.asList("§8Clique para ver seu perfil."));
        skull.setItemMeta(itemMeta);

        ItemStack junkBox = new ItemAPI(Material.CHEST)
                .setName("§8- §eColetáveis §7[Clique]")
                .addLore("§8Clique para ver seus coletáveis.")
                .build();

        if (isVisible) {
            ItemStack lightGreenDye = new ItemAPI(Material.INK_SACK, 1, (short) 10) // 10 is for lime dye
                    .setName("§8- §eJogadores: §aVisíveis §7[Clique]")
                    .addLore("§8Clique para desativar os jogadores.")
                    .build();
            player.getInventory().setItem(7, lightGreenDye);
        } else {
            ItemStack redDye = new ItemAPI(Material.INK_SACK, 1, (short) 8) // 8 is for red dye
                    .setName("§8- §eJogadores: §cInvisíveis §7[Clique]")
                    .addLore("§8Clique para desativar os jogadores.")
                    .build();
            player.getInventory().setItem(7, redDye);
        }


        ItemStack netherStar = new ItemAPI(Material.REDSTONE_COMPARATOR) // 2 is for cactus green dye
                .setName("§8- §eSeletor de Lobby §7[Clique]")
                .addLore("§8Clique para selecionar seu lobby.")
                .build();

        // Set items in inventory
        player.getInventory().setItem(0, compass);
        player.getInventory().setItem(1, skull);
        player.getInventory().setItem(4, junkBox);
        player.getInventory().setItem(8, netherStar);
        player.setGameMode(GameMode.ADVENTURE);
        player.setMaxHealth(4);
        player.setHealth(4);
        player.setFoodLevel(20);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        linkWithSystem.teleportToLobby(player);


        // Create items
        ItemStack compass = new ItemAPI(Material.COMPASS)
                .setName("§8- §eMenu de Jogos §7[Clique]")
                .addLore("§8Clique para ver os modos de jogo do servidor.")
                .addEnchantment(Enchantment.LURE, 1)
                .build();

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(player.getName()); // Set the owner to the player's name
        skull.setItemMeta(meta);

// You can then add additional metadata like name and lore
        ItemMeta itemMeta = skull.getItemMeta();
        itemMeta.setDisplayName("§8- §eMeu Perfil §7[Clique]");
        itemMeta.setLore(Arrays.asList("§8Clique para ver seu perfil."));
        skull.setItemMeta(itemMeta);

        ItemStack junkBox = new ItemAPI(Material.CHEST)
                .setName("§8- §eColetáveis §7[Clique]")
                .addLore("§8Clique para ver seus coletáveis.")
                .build();

        ItemStack lightGreenDye1 = new ItemAPI(Material.INK_SACK, 1, (short) 10) // 10 is for lime dye
                .setName("§8- §eJogadores: §aVisíveis §7[Clique]")
                .addLore("§8Clique para desativar os jogadores.")
                .build();

        ItemStack netherStar = new ItemAPI(Material.REDSTONE_COMPARATOR) // 2 is for cactus green dye
                .setName("§8- §eSeletor de Lobby §7[Clique]")
                .addLore("§8Clique para selecionar seu lobby.")
                .build();

        // Set items in inventory
        player.getInventory().setItem(0, compass);
        player.getInventory().setItem(1, skull);
        player.getInventory().setItem(4, junkBox);
        player.getInventory().setItem(7, lightGreenDye1);
        player.getInventory().setItem(8, netherStar);
        player.setGameMode(GameMode.ADVENTURE);
        player.setMaxHealth(4);
        player.setHealth(4);
    }

    public void openScore(Player player) {
        String players = String.valueOf(Bukkit.getOnlinePlayers().size());
        String group = database.getPlayerGroup(player.getName());
        String cristais = String.valueOf(database.getCristais(player.getName()));
        String serverName = Bukkit.getServerName().replaceAll("Lobby", "").trim();

        ScoreboardWrapper sc_wrapper = new ScoreboardWrapper(" §6§lVEXMOS ");
        sc_wrapper.addBlankSpace();

        if (group != null) {
            switch (group) {
                case "diretor":
                    sc_wrapper.addLine(" Grupo: §4DIRETOR");
                    break;
                case "dev":
                    sc_wrapper.addLine(" Grupo: §3DESENVOLVEDOR");
                    break;
                case "admin":
                    sc_wrapper.addLine(" Grupo: §cADMINISTRADOR");
                    break;
                case "mod":
                    sc_wrapper.addLine(" Grupo: §2MODERADOR");
                    break;
                case "construtor":
                    sc_wrapper.addLine(" Grupo: §eCONSTRUTOR");
                    break;
                case "suporte":
                    sc_wrapper.addLine(" Grupo: §3SUPORTE");
                    break;
                case "emerald":
                    sc_wrapper.addLine(" Grupo: §aEMERALD");
                    break;
                case "diamond":
                    sc_wrapper.addLine(" Grupo: §bDIAMOND");
                    break;
                case "gold":
                    sc_wrapper.addLine(" Grupo: §6GOLD");
                    break;
                case "membro":
                    sc_wrapper.addLine(" Grupo: §7MEMBRO");
                    break;
                default:
                    sc_wrapper.addLine(" Grupo: §7MEMBRO");
                    break;
            }
        } else {
            sc_wrapper.addLine(" Grupo: §7MEMBRO");
        }

        sc_wrapper.addBlankSpace();
        sc_wrapper.addLine(" §6§lⓋ§fCristais: §B" + cristais);
        sc_wrapper.addBlankSpace();
        sc_wrapper.addLine(" §fLobby: §e#" + serverName);
        sc_wrapper.addBlankSpace();
        sc_wrapper.addLine(" §fJogadores: §B" + players);
        sc_wrapper.addBlankSpace();
        sc_wrapper.addLine("§6www.vexmos.net");

        player.setScoreboard(sc_wrapper.getScoreboard());
    }





    public void startGlobalScoreboardUpdate() {

        Bukkit.getScheduler().runTaskTimer(VexmosHub.get(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.isOnline()) {


                    openScore(player);


                }
            }
        }, 40L, 80L);
    }



    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void Flight(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        linkWithSystem.checkDistanceToLobby(player);
    }


}


