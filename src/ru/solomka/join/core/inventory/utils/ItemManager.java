package ru.solomka.join.core.inventory.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class ItemManager {

    public static void setItemInv(ItemStack item, Inventory inventory,
                           int startSlot, int finalSlot) {
        if(inventory == null) return;
        for (int i = startSlot; i < finalSlot; i++) {
            if (inventory.getItem(i) != null) {
                i++;
            }
            inventory.setItem(i, item);
        }
    }

    public static @NotNull ItemStack setItemMeta(@NotNull ItemStack item, String name, @NotNull List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        lore.replaceAll(s -> net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', s));
        meta.setLore(lore);
        meta.setDisplayName(translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return item;
    }
}