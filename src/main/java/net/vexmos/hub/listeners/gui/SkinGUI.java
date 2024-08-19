package net.vexmos.hub.listeners.gui;

import net.vexmos.hub.api.CustomHeads;
import net.vexmos.hub.api.GuiAPI;
import net.vexmos.hub.api.ItemAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.Arrays;

public class SkinGUI implements Listener {

    private GuiAPI gui;
    private Player player;



    public void openSkinsGui(Player player) throws IOException {

        this.player = player;
        this.gui = new GuiAPI("Sua skin", 6); // 3 rows

        ItemAPI playerInfoItem = new ItemAPI(Material.SKULL_ITEM, 1, (short) 3).setName("§aSua skin: §b" + player.getName()).addLore("\n§8- §7Fonte: §eConta").setSkullOwner(player.getName());
        ItemAPI biblioteca = new ItemAPI(Material.BOOKSHELF).setName("§6Biblioteca").addLore("\n§eTenha acesso a uma biblioteca\n§erepleta de skins!");
        ItemAPI customizar = new ItemAPI(Material.NAME_TAG).setName("§6Customizar skin").addLore("\n§eCustomize com um uma que você deseja!\n\n§9[Essa é uma função VIP]");

        String avancarText = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM5YzY3YTlmMTY4NWNkMWRhNDNlODQxZmU3ZWJiMTdmNmFmNmVhMTJhN2UxZjI3MjJmNWU3ZjA4OThkYjlmMyJ9fX0=";

        ItemStack cabecaAvancar = CustomHeads.create(avancarText);
        ItemStack itemAvancar = cabecaAvancar;
        ItemMeta avancar = itemAvancar.getItemMeta();;
        avancar.setDisplayName("§eAvançar §8[2/2]");

        avancar.setLore(Arrays.asList("§aClique para avançar de página."));

        itemAvancar.setItemMeta(avancar);
        gui.getInventory().setItem(50, itemAvancar);

        String backText = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWExZWYzOThhMTdmMWFmNzQ3NzAxNDUxN2Y3ZjE0MWQ4ODZkZjQxYTMyYzczOGNjOGE4M2ZiNTAyOTdiZDkyMSJ9fX0=";
        // Create the custom head ItemStack
        ItemStack cabecaVoltar = CustomHeads.create(backText);
        ItemStack itemVoltar = cabecaVoltar;
        ItemMeta voltar = itemVoltar.getItemMeta();
        voltar.setDisplayName("§eVoltar §8[2/2]");

        voltar.setLore(Arrays.asList("§aClique para voltar de página."));

        itemVoltar.setItemMeta(voltar);
        gui.getInventory().setItem(48, itemVoltar);
        gui.setItem(13, playerInfoItem);
        gui.setItem(30, customizar);
        gui.setItem(32, biblioteca);

        gui.openInventory(player);

    }




}