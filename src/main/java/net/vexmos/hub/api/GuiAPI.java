package net.vexmos.hub.api;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiAPI {

    private Inventory inventory;
    private String name;
    private int rows;

    public GuiAPI(String name, int rows) {
        this.name = name;
        this.rows = rows;
        this.inventory = Bukkit.createInventory(null, rows * 9, name);
    }

    public void clearInventorySlots(Inventory gui) {
        for (int i = 1; i <= 35; i++) {
            gui.setItem(i, new ItemStack(Material.AIR)); // Define o item como AIR (vazio)
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setItem(int slot, ItemAPI item) {
        inventory.setItem(slot, item.getItemStack());
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    public void closeInventory(Player player) {
        player.closeInventory();
    }


}