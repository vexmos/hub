package net.vexmos.lobby.listeners.gui;

import net.vexmos.lobby.api.CustomHeads;
import net.vexmos.lobby.api.GuiAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TagGUI implements Listener  {

    private GuiAPI gui;
    private Player player;


    public static ItemStack createCustomHead(String texture, String displayName) {
        // Cria o item da cabeça customizada
        ItemStack head = CustomHeads.create(texture);
        // Pega o ItemMeta da cabeça
        ItemMeta meta = head.getItemMeta();

        if (meta != null) {
            // Define o nome de exibição da cabeça
            meta.setDisplayName(displayName);
            head.setItemMeta(meta);
        }

        return head;
    }


    public void openTagsGui(Player player) {



        this.player = player;
        this.gui = new GuiAPI("Catálogo de Tags", 6); // 3 rows


        // avancar
        String avancarText = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM5YzY3YTlmMTY4NWNkMWRhNDNlODQxZmU3ZWJiMTdmNmFmNmVhMTJhN2UxZjI3MjJmNWU3ZjA4OThkYjlmMyJ9fX0=";

        ItemStack cabecaAvancar = CustomHeads.create(avancarText);
        ItemStack itemAvancar = cabecaAvancar;
        ItemMeta avancar = itemAvancar.getItemMeta();;
        avancar.setDisplayName("§eAvançar §8[2/2]");

        avancar.setLore(Arrays.asList("§aClique para avançar de página."));

        itemAvancar.setItemMeta(avancar);
        gui.getInventory().setItem(53, itemAvancar);

        String backText = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWExZWYzOThhMTdmMWFmNzQ3NzAxNDUxN2Y3ZjE0MWQ4ODZkZjQxYTMyYzczOGNjOGE4M2ZiNTAyOTdiZDkyMSJ9fX0=";
        // Create the custom head ItemStack
        ItemStack cabecaVoltar = CustomHeads.create(backText);
        ItemStack itemVoltar = cabecaVoltar;
        ItemMeta voltar = itemVoltar.getItemMeta();
        voltar.setDisplayName("§eVoltar §8[2/2]");

        voltar.setLore(Arrays.asList("§aClique para voltar de página."));

        itemVoltar.setItemMeta(voltar);
        gui.getInventory().setItem(45, itemVoltar);

        // tags
        String dHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE5M2RjMGQ0YzVlODBmZjlhOGEwNWQyZmNmZTI2OTUzOWNiMzkyNzE5MGJhYzE5ZGEyZmNlNjFkNzEifX19";
        String aHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTY3ZDgxM2FlN2ZmZTViZTk1MWE0ZjQxZjJhYTYxOWE1ZTM4OTRlODVlYTVkNDk4NmY4NDk0OWM2M2Q3NjcyZSJ9fX0=";
        String cHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJlOTgzZWM0NzgwMjRlYzZmZDA0NmZjZGZhNDg0MjY3NjkzOTU1MWI0NzM1MDQ0N2M3N2MxM2FmMThlNmYifX19";
        String mHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDljNDVhMjRhYWFiZjQ5ZTIxN2MxNTQ4MzIwNDg0OGE3MzU4MmFiYTdmYWUxMGVlMmM1N2JkYjc2NDgyZiJ9fX0= ";
        String sHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2U0MWM2MDU3MmM1MzNlOTNjYTQyMTIyODkyOWU1NGQ2Yzg1NjUyOTQ1OTI0OWMyNWMzMmJhMzNhMWIxNTE3In19fQ==";
        String eHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGJiMjczN2VjYmY5MTBlZmUzYjI2N2RiN2Q0YjMyN2YzNjBhYmM3MzJjNzdiZDBlNGVmZjFkNTEwY2RlZiJ9fX0=";
        String gHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWNhM2YzMjRiZWVlZmI2YTBlMmM1YjNjNDZhYmM5MWNhOTFjMTRlYmE0MTlmYTQ3NjhhYzMwMjNkYmI0YjIifX19";






        // TAGS
        ItemStack itemDiretor = createCustomHead(dHead, "§4Diretor");
        List<String> loreDiretor = Arrays.asList("§eClique para selecionar a tag §4Diretor");

        ItemStack itemDev = createCustomHead(dHead, "§3Desenvolvedor");
        List<String> loreDev = Arrays.asList("§eClique para selecionar a tag §3Desenvolvedor");

        ItemStack itemAdmin = createCustomHead(aHead, "§cAdministrador");
        List<String> loreAdmin = Arrays.asList("§eClique para selecionar a tag §cAdministrador");

        ItemStack itemMod = createCustomHead(mHead, "§2Moderador");
        List<String> loreMod = Arrays.asList("§eClique para selecionar a tag §2Moderador");

        ItemStack itemSuporte = createCustomHead(sHead, "§3Suporte");
        List<String> loreSuporte = Arrays.asList("§eClique para selecionar a tag §3Suporte");

        ItemStack itemGold = createCustomHead(gHead, "§6Gold");
        List<String> loreGold = Arrays.asList("§eClique para selecionar a tag §6Gold");

        ItemStack itemDiamond = createCustomHead(dHead, "§bDiamond");
        List<String> loreDiamond = Arrays.asList("§eClique para selecionar a tag §bDiamond");

        ItemStack itemEmerald = createCustomHead(eHead, "§aEmerald");
        List<String> loreEmerald = Arrays.asList("§eClique para selecionar a tag §aEmerald");

        ItemStack itemMembro = createCustomHead(mHead, "§7Membro");
        List<String> loreMembro = Arrays.asList("§eClique para selecionar a tag §7Membro");

        ItemStack itemConstrutor = createCustomHead(cHead, "§eConstrutor");
        List<String> loreConstrutor = Arrays.asList("§eClique para selecionar a tag §eConstrutor");
        itemDiretor.getItemMeta().setLore(loreDiretor);
        itemDev.getItemMeta().setLore(loreDev);
        itemAdmin.getItemMeta().setLore(loreAdmin);
        itemMod.getItemMeta().setLore(loreMod);
        itemSuporte.getItemMeta().setLore(loreSuporte);
        itemGold.getItemMeta().setLore(loreGold);
        itemDiamond.getItemMeta().setLore(loreDiamond);
        itemEmerald.getItemMeta().setLore(loreEmerald);
        itemMembro.getItemMeta().setLore(loreMembro);
        itemConstrutor.getItemMeta().setLore(loreConstrutor);

        if(player.isOp()) {
            gui.getInventory().setItem(11, itemDiretor);
            gui.getInventory().setItem(12, itemDev);
            gui.getInventory().setItem(13, itemAdmin);
            gui.getInventory().setItem(14, itemConstrutor);
            gui.getInventory().setItem(15, itemMod);
            gui.getInventory().setItem(20, itemSuporte);
            gui.getInventory().setItem(21, itemEmerald);
            gui.getInventory().setItem(22, itemDiamond);
            gui.getInventory().setItem(23, itemGold);
            gui.getInventory().setItem(24, itemMembro);
            gui.openInventory(player);
            return;
        }
        if(player.hasPermission("group.diretor")) {
            gui.getInventory().setItem(11, itemDiretor);
            gui.getInventory().setItem(12, itemDev);
            gui.getInventory().setItem(13, itemAdmin);
            gui.getInventory().setItem(14, itemConstrutor);
            gui.getInventory().setItem(15, itemMod);
            gui.getInventory().setItem(20, itemSuporte);
            gui.getInventory().setItem(21, itemEmerald);
            gui.getInventory().setItem(22, itemDiamond);
            gui.getInventory().setItem(23, itemGold);
            gui.getInventory().setItem(24, itemMembro);

        } else if (player.hasPermission("group.dev")) {
            gui.getInventory().setItem(11, itemDev);
            gui.getInventory().setItem(12, itemAdmin);
            gui.getInventory().setItem(13, itemConstrutor);
            gui.getInventory().setItem(14, itemMod);
            gui.getInventory().setItem(25, itemSuporte);
            gui.getInventory().setItem(20, itemEmerald);
            gui.getInventory().setItem(21, itemDiamond);
            gui.getInventory().setItem(22, itemGold);
            gui.getInventory().setItem(23, itemMembro);

        } else if (player.hasPermission("group.admin")) {
            gui.getInventory().setItem(11, itemAdmin);
            gui.getInventory().setItem(12, itemConstrutor);
            gui.getInventory().setItem(13, itemMod);
            gui.getInventory().setItem(14, itemSuporte);
            gui.getInventory().setItem(15, itemEmerald);
            gui.getInventory().setItem(20, itemDiamond);
            gui.getInventory().setItem(21, itemGold);
            gui.getInventory().setItem(22, itemMembro);

        } else if (player.hasPermission("group.mod")) {
            gui.getInventory().setItem(11, itemMod);
            gui.getInventory().setItem(12, itemSuporte);
            gui.getInventory().setItem(13, itemEmerald);
            gui.getInventory().setItem(14, itemDiamond);
            gui.getInventory().setItem(15, itemGold);
            gui.getInventory().setItem(20, itemMembro);
        } else if (player.hasPermission("group.suporte")) {
            gui.getInventory().setItem(11, itemSuporte);
            gui.getInventory().setItem(12, itemEmerald);
            gui.getInventory().setItem(13, itemDiamond);
            gui.getInventory().setItem(14, itemGold);
            gui.getInventory().setItem(15, itemMembro);
        } else if (player.hasPermission("group.gold")) {
            gui.getInventory().setItem(11, itemGold);
            gui.getInventory().setItem(12, itemMembro);

        } else if (player.hasPermission("group.emerald")) {
            gui.getInventory().setItem(11, itemEmerald);
            gui.getInventory().setItem(12, itemDiamond);
            gui.getInventory().setItem(13, itemGold);
            gui.getInventory().setItem(14, itemMembro);
        } else if (player.hasPermission("group.membro")) {
            gui.getInventory().setItem(22, itemMembro);
        } else if (player.hasPermission("group.construtor")) {
            gui.getInventory().setItem(11, itemConstrutor);
            gui.getInventory().setItem(12, itemMod);
            gui.getInventory().setItem(13, itemSuporte);
            gui.getInventory().setItem(14, itemEmerald);
            gui.getInventory().setItem(15, itemDiamond);
            gui.getInventory().setItem(20, itemGold);
            gui.getInventory().setItem(21, itemMembro);
        } else if (player.hasPermission("group.diamond")) {
            gui.getInventory().setItem(11, itemDiamond);
            gui.getInventory().setItem(12, itemGold);
            gui.getInventory().setItem(13, itemMembro);
        }



        // Open the GUI for the player
        gui.openInventory(player);


    }



    private ItemStack createCustomHead(String texture, String name, String... lore) {
        ItemStack item = CustomHeads.create(texture); // Método para obter a cabeça customizada
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }



    }

