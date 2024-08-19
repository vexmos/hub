package net.vexmos.hub.listeners.gui;

import net.vexmos.hub.api.CustomHeads;
import net.vexmos.hub.api.GuiAPI;
import net.vexmos.hub.api.ItemAPI;
import net.vexmos.hub.database.ConnectSpigot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class AjustesGUI implements Listener {

    private GuiAPI gui;
    private Player player;


    public void openAjustesGui(Player player) {

        this.player = player;
        this.gui = new GuiAPI("Ajustes", 6); // 3 rows
        ConnectSpigot db = new ConnectSpigot();

        ItemAPI voarOff = new ItemAPI(Material.FEATHER).setName("§eVoar").addLore("§cFunção desabilitada\n\n§aClique para habilitar.\n\n§9[Essa é uma função VIP]");
        ItemAPI voarOn = new ItemAPI(Material.FEATHER).setName("§eVoar").addLore("§aFunção habilitada\n\n§cClique para desabilitar.");

        ItemAPI mensagemJoinOff = new ItemAPI(Material.WRITTEN_BOOK).setName("§eMensagem de entrada").addLore("\n§eChegue com estilo no servidor!\n\n§cFunção desabilitada\n\n§aClique para habilitar.\n\n§9[Essa é uma função VIP]");

        ItemAPI mensagemJoinOn = new ItemAPI(Material.WRITTEN_BOOK).setName("§eMensagem de entrada").addLore("\n§eChegue com estilo no servidor!\n\n§aFunção habilitada\n\n§cClique para desabilitar.");

        ItemAPI mensagensPrivadasOff = new ItemAPI(Material.LEVER).setName("§eMensagens privadas").addLore("\n§eReceba mensagens privadas.\n\n§cFunção desabilitada\n\n§aClique para habilitar.\n\n§9[Essa é uma função VIP]");

        ItemAPI mensagensPrivadasOn= new ItemAPI(Material.LEVER).setName("§eMensagens privadas").addLore("\n§eReceba mensagens privadas.\n\n§aFunção habilitada\n\n§cClique para desabilitar.");

        String avancarText = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM5YzY3YTlmMTY4NWNkMWRhNDNlODQxZmU3ZWJiMTdmNmFmNmVhMTJhN2UxZjI3MjJmNWU3ZjA4OThkYjlmMyJ9fX0=";

        ItemStack cabecaAvancar = CustomHeads.create(avancarText);
        ItemStack itemAvancar = cabecaAvancar;
        ItemMeta avancar = itemAvancar.getItemMeta();
        ;
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

        if (player.isFlying()) {
            gui.setItem(24, voarOn);
        } else {
            gui.setItem(24, voarOff);
        }
        if (db.hasJoinMessage(player.getName())) {
            gui.setItem(20, mensagemJoinOff);
        } else {
            gui.setItem(20, mensagemJoinOn);
        }
        if (db.isTellEnabled(player.getName())) {
            gui.setItem(22, mensagensPrivadasOff);
        } else {
            gui.setItem(22, mensagensPrivadasOn);
        }
        gui.openInventory(player);

    }

    @EventHandler
    public void mensagementrada(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        ConnectSpigot db = new ConnectSpigot();
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        String displayName = clickedItem.getItemMeta().getDisplayName();
        if (displayName.equals("§eMensagem de entrada")) {
            event.setCancelled(true);
            openAjustesGui(player);

            if(db.hasJoinMessage(player.getName())) {
                db.setJoinMessagePreference(player.getName(), false);
                player.sendMessage("§cSua mensagem de entrada foi desabilitada.");
            } else {
                db.setJoinMessagePreference(player.getName(), true);
                player.sendMessage("§aSua mensagem de entrada foi habilitada.");

            }
        }
    }

    @EventHandler
    public void tellToggle(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        ConnectSpigot db = new ConnectSpigot();
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        String displayName = clickedItem.getItemMeta().getDisplayName();
        if (displayName.equals("§eMensagens privadas")) {
            event.setCancelled(true);
            openAjustesGui(player);

            if(db.isTellEnabled(player.getName())) {
                db.setTellStatus(player.getName(), false);
                player.sendMessage("§cSuas mensagns privadas foram desabilitadas.");
            } else {
                db.setTellStatus(player.getName(), true);
                player.sendMessage("§aSuas mensagens privadas foram habilitadas.");

            }
        }
    }


}
