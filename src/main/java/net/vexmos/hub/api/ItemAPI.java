package net.vexmos.hub.api;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemAPI {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemAPI(Material material) {
        this(material, 1, (short) 0);
    }

    public ItemAPI(Material material, int amount, short durability) {
        this.itemStack = new ItemStack(material, amount, durability);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemAPI setName(String name) {
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemAPI setLore(List<String> lore) {
        List<String> formattedLore = new ArrayList<>();
        for (String line : lore) {
            if (line.contains("\n")) {
                String[] splitLines = line.split("\n");
                for (String splitLine : splitLines) {
                    formattedLore.add(splitLine);
                }
            } else {
                formattedLore.add(line);
            }
        }
        itemMeta.setLore(formattedLore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemAPI addLore(String line) {
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        if (line.contains("\n")) {
            String[] splitLines = line.split("\n");
            for (String splitLine : splitLines) {
                lore.add(splitLine);
            }
        } else {
            lore.add(line);
        }
        return setLore(lore);
    }

    public ItemAPI addEnchantments(Map<Enchantment, Integer> enchantments) {
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            itemStack.addUnsafeEnchantment(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public ItemAPI addEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemAPI setSkullTexture(String texture) {
        if (itemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta skullMeta = (SkullMeta) itemMeta;
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", texture));

            try {
                Object skull = itemMeta.getClass().getMethod("getHandle").invoke(itemMeta);
                Field profileField = skull.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skull, profile);
            } catch (Exception e) {
                e.printStackTrace();
            }

            itemStack.setItemMeta(skullMeta);
        }
        return this;
    }

    public ItemAPI setSkullOwner(String owner) {
        if (itemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta skullMeta = (SkullMeta) itemMeta;
            skullMeta.setOwner(owner);
            itemStack.setItemMeta(skullMeta);
        }
        return this;
    }



    public ItemStack build() {
        return itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}