package net.vexmos.hub.listeners.gui;

import net.vexmos.hub.api.CustomHeads;
import net.vexmos.hub.api.GuiAPI;
import net.vexmos.hub.api.ItemAPI;
import net.vexmos.hub.database.ConnectSpigot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProfileGUI implements Listener {

    private GuiAPI gui;
    private Player player;
    private static ConnectSpigot database;

    public ProfileGUI(ConnectSpigot database) {
        ProfileGUI.database = database;
    }

    public void openguiProfile(Player player) {
        Map<String, String> groupMap = new HashMap<>();
        groupMap.put("diretor", "§4Diretor");
        groupMap.put("dev", "§3Desenvolvedor");
        groupMap.put("admin", "§cAdministrador");
        groupMap.put("mod", "§2Moderador");
        groupMap.put("suporte", "§3Suporte");
        groupMap.put("construtor", "§eConstrutor");
        groupMap.put("emerald", "§aEmerald");
        groupMap.put("diamond", "§bDiamond");
        groupMap.put("gold", "§6Gold");
        groupMap.put("membro", "§7Membro");
        this.player = player;
        this.gui = new GuiAPI("Seu Perfil", 6); // 3 rows
        String cristais = String.valueOf(database.getCristais(player.getName()));
        String group = String.valueOf(database.getPlayerGroup(player.getName()));
        String firstLogin = String.valueOf(database.getFirstLoginDate(player.getName()));
        String groupName = groupMap.getOrDefault(group.toLowerCase(), "§7Membro");

        // Create items for the GUI

        ItemAPI playerInfoItem = new ItemAPI(Material.SKULL_ITEM, 1, (short) 3).setName("§e" + player.getName()).addLore("§8[" + player.getUniqueId().toString() + "]\n\n§fGrupo: " + groupName + "\n\n§fCristais: §e" + cristais + "\n\n§fPrimeiro log-in: §b" + firstLogin).setSkullOwner(player.getName());

        ItemAPI estatisticas = new ItemAPI(Material.BOOK).setName("§eEstatísticas").addLore("§aClique para ver suas estatísticas.");
        ItemAPI ajustes = new ItemAPI(Material.TRIPWIRE_HOOK).setName("§eAjustes").addLore("§aClique para ajustar sua experiência de jogo.");
        ItemAPI tags = new ItemAPI(Material.NAME_TAG).setName("§eCatálogo de Tags").addLore("§aClique aqui para ver suas TAGS.");




        String textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdhNTI1MTJiMzA1N2RiY2YyZjcwNjcxNjI1YTUxMTBhYzVmNjNjMWQyYzBjZGY5OGEyNjFhMTQ3Y2VmNjllYyJ9fX0=";

        // Create the custom head ItemStack
        ItemStack customHead = CustomHeads.create(textureValue);
        ItemStack item = customHead;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aAlterar sua skin");

        meta.setLore(Arrays.asList("§eModifique sua skin com a", "§ediversidade de nosso catálogo."));

        item.setItemMeta(meta);

        String avancarText = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM5YzY3YTlmMTY4NWNkMWRhNDNlODQxZmU3ZWJiMTdmNmFmNmVhMTJhN2UxZjI3MjJmNWU3ZjA4OThkYjlmMyJ9fX0=";

        // Create the custom head ItemStack
        ItemStack cabecaAvancar = CustomHeads.create(avancarText);
        ItemStack itemAvancar = cabecaAvancar;
        ItemMeta avancar = itemAvancar.getItemMeta();
        avancar.setDisplayName("§eAvançar §8[1/1]");

        avancar.setLore(Arrays.asList("§aClique para avançar de página."));

        itemAvancar.setItemMeta(avancar);

        String clanText = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjEyZjNlZmU4NGEwZjY2NDZhODBkNDVjZWZlNDE4ZTE5OWQ5NjE5ZjhjMWZiNWY1YzVjMDA4YzYwMzA1OWFjMyJ9fX0=";

        // Create the custom head ItemStack
        ItemStack cabecaClan = CustomHeads.create(clanText);
        ItemStack itemClan = cabecaClan;
        ItemMeta clanCabeca = itemClan.getItemMeta();
        clanCabeca.setDisplayName("§eClans");

        clanCabeca.setLore(Arrays.asList("§cVocê não participa de nenhum Clan.", "§eClique aqui para entender esse sistema."));

        itemClan.setItemMeta(clanCabeca);





        // Add items to the GUI
        gui.setItem(12, playerInfoItem);
        gui.getInventory().setItem(14, item);
        gui.getInventory().setItem(53, itemAvancar);
        gui.getInventory().setItem(49, itemClan);

        gui.setItem(31, ajustes);
        gui.setItem(29, tags);
        gui.setItem(33, estatisticas);

        // Open the GUI for the player
        gui.openInventory(player);
        Inventory inventory = player.getInventory();


    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) throws IOException {
        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        String serveroff = " §cOcorreu um erro na conexão: §8[O servidor encontra-se OFFLINE]";
        String alreadyconnect = " §cOcorreu um erro na conexão: §8[Você já se encontra nesse servidor]";

        if (clickedItem != null && clickedItem.hasItemMeta()){
            ItemMeta meta = clickedItem.getItemMeta();

            if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§e" + player.getName())){
                e.setCancelled(true);
                player.performCommand("/stats");

            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eEstatísticas")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eAjustes")){
                e.setCancelled(true);
                AjustesGUI ajI = new AjustesGUI();
                ajI.openAjustesGui(player);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eVoar")){
                e.setCancelled(true);
                player.performCommand("fly");
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eLobby #1 §7[CLIQUE]")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eLobby #2 §7[CLIQUE]")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§8- §eColetáveis §7[Clique]")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§8- §eMeu Perfil §7[Clique]")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§8- §eJogadores: §cInvisíveis §7[Clique]")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§8- §eJogadores: §aVisíveis §7[Clique]")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§8- §eMenu de Jogos §7[Clique]")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§8- §eSeletor de Lobby §7[Clique]")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eLobby")){
                e.setCancelled(true);
                if(!(Bukkit.getServerName().contains("Lobby"))) {
                    player.sendMessage("§aConectando...");
                } else {
                    player.sendMessage(alreadyconnect);
                }
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().contains("§ePvP")){
                e.setCancelled(true);
                player.sendMessage(serveroff);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().contains("§eSky Wars")){
                e.setCancelled(true);
                player.sendMessage(serveroff);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().contains("§eBedWars")){
                e.setCancelled(true);
                player.sendMessage(serveroff);

            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().contains("§eRankUP")){
                e.setCancelled(true);
                player.sendMessage(serveroff);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().contains("§6§LⓋ §6Cristais")){
                e.setCancelled(true);
                player.closeInventory();
                String baseMessage = ChatColor.YELLOW + "Adquira " + ChatColor.GOLD + ChatColor.BOLD + "Ⓥ " + ChatColor.GOLD + "Cristais " + ChatColor.RESET + "clicando ";
                String clickableText = "§B§LAQUI";
                String url = "http://loja.vexmos.net";
                String tooltip = ChatColor.GREEN + "Clique aqui!";

                // Send the clickable message to the player
                player.sendMessage(baseMessage + clickableText);

                // You can use a command to open URL (bukkit doesn't support clickable links directly without Bungee)
                player.sendMessage(ChatColor.GRAY + "URL: " + ChatColor.BLUE + url);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().contains("§cVocê não possui nenhum coletavel.")){
                e.setCancelled(true);
                player.closeInventory();
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eCatálogo de Tags")){
                e.setCancelled(true);
                TagGUI tagGUI = new TagGUI();
                tagGUI.openTagsGui(player);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§aAlterar sua skin")){
                e.setCancelled(true);
                SkinGUI skinGui = new SkinGUI();
                skinGui.openSkinsGui(player);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eChapéus")){
                e.setCancelled(true);
                HatsGUI collect = new HatsGUI();
                collect.openHatsMenu(player);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eVoltar §8[2/2]") || meta.getDisplayName().equals(("§eVoltar §8[1/1]"))) {
                e.setCancelled(true);
                if (e.getView().getTitle().equals("Seus Chapéus") || e.getView().getTitle().equals("Seus Efeitos") || e.getView().getTitle().equals("Seus Pets") ) {

                    CollectablesGUI gui = new CollectablesGUI();
                    gui.openCollectablesGui(player);
                } else if(e.getView().getTitle().equals("Catálogo de Tags") || e.getView().getTitle().equals("Ajustes") || e.getView().getTitle().equals("Sua skin")) {
                    openguiProfile(player);
                }
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eVoltar §8[2/2]") || meta.getDisplayName().equals(("§eVoltar §8[1/1]"))) {
                e.setCancelled(true);

            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§ePets")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eEfeitos")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eAvançar §8[1/1]")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eAvançar §8[2/2]")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§eClans")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§6Customizar skin")){
                e.setCancelled(true);

            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§6Biblioteca")){
                e.setCancelled(true);
            } else if (meta != null && meta.hasDisplayName() && meta.getDisplayName().contains("§aSua skin:")){
                e.setCancelled(true);
            }



        }
    }


    @EventHandler
    public void tagSet(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem != null && clickedItem.hasItemMeta()){
            ItemMeta meta = clickedItem.getItemMeta();
            if (meta.hasDisplayName()) {
                String displayName = meta.getDisplayName();
                switch (displayName) {
                    case "§4Diretor":
                        Bukkit.dispatchCommand(player, "tag diretor");
                        break;
                    case "§cAdministrador":
                        player.performCommand("tag admin");
                        break;
                    case "§3Desenvolvedor":
                        player.performCommand("tag dev");
                        break;
                    case "§3Suporte":
                        player.performCommand("tag suporte");
                        break;
                    case "§eConstrutor":
                        player.performCommand("tag construtor");
                        break;
                    case "§aEmerald":
                        player.performCommand("tag emerald");
                        break;
                    case "§2Moderador":
                        player.performCommand("tag mod");
                        break;
                    case "§7Membro":
                        player.performCommand("tag membro");
                        break;
                    case "§6Gold":
                        player.performCommand("tag gold");
                        break;
                    case "§bDiamond":
                        player.performCommand("tag diamond");
                        break;
                    default:
                        return;
                }
                e.setCancelled(true);
                player.closeInventory();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryDrag(InventoryDragEvent event) {
        // Check if the inventory belongs to a player
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            // Check if the player is not in creative mode
            if (player.getGameMode() != org.bukkit.GameMode.CREATIVE) {
                // Cancel the event
                event.setCancelled(true);
            }
        }
    }





    @EventHandler
    public void guiOpenEvent(PlayerInteractEvent event) throws IOException {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if the item is not null and has meta data
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            // Check if the item is a "Perfil" compass
            if (item.getItemMeta().getDisplayName().equals("§8- §eMeu Perfil §7[Clique]")) {
                switch (event.getAction()) {
                    case RIGHT_CLICK_AIR:
                    case RIGHT_CLICK_BLOCK:
                    case LEFT_CLICK_AIR:
                    case LEFT_CLICK_BLOCK:
                        // Trigger the server selector GUI
                        openguiProfile(player);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}